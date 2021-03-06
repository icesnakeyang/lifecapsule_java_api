package com.gogoyang.lifecapsule.business.admin.backJob;

import com.gogoyang.lifecapsule.business.admin.email.IAdminEmailBusinessService;
import com.gogoyang.lifecapsule.meta.gogoKey.entity.GogoKey;
import com.gogoyang.lifecapsule.meta.gogoKey.entity.KeyParam;
import com.gogoyang.lifecapsule.meta.gogoKey.service.IGogoKeyService;
import com.gogoyang.lifecapsule.meta.note.entity.NoteInfo;
import com.gogoyang.lifecapsule.meta.note.service.INoteService;
import com.gogoyang.lifecapsule.meta.recipient.entity.Recipient;
import com.gogoyang.lifecapsule.meta.recipient.service.IRecipientService;
import com.gogoyang.lifecapsule.meta.trigger.entity.Trigger;
import com.gogoyang.lifecapsule.meta.trigger.service.ITriggerService;
import com.gogoyang.lifecapsule.meta.user.entity.UserInfo;
import com.gogoyang.lifecapsule.meta.user.service.IUserInfoService;
import com.gogoyang.lifecapsule.utility.GogoTools;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.*;

public class GogoKeyProcessor extends QuartzJobBean {
    @Autowired
    private IGogoKeyService iGogoKeyService;
    @Autowired
    private ITriggerService iTriggerService;
    @Autowired
    private INoteService iNoteService;
    @Autowired
    private IRecipientService iRecipientService;
    @Autowired
    private IAdminEmailBusinessService iAdminEmailBusinessService;
    @Autowired
    private IUserInfoService iUserInfoService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("Trigger processor started");
        try {
            /**
             * 这里应该读取所有的trigger
             */
            List<GogoKey> gogoKeys = iGogoKeyService.listGogoKeyAll();
            for (int i = 0; i < gogoKeys.size(); i++) {
                GogoKey theGogoKey = iGogoKeyService.getGogoKeyByGogoKeyId(gogoKeys.get(i).getGogoKeyId());
                Trigger theTrigger = iTriggerService.getTriggerByTriggerId(theGogoKey.getTriggerId());
                if (theTrigger == null) {
                    continue;
                }
                NoteInfo theNote = iNoteService.getNoteTinyByNoteId(theTrigger.getNoteId());
                for (int k = 0; k < theGogoKey.getKeyParams().size(); k++) {
                    KeyParam theKeyParam = theGogoKey.getKeyParams().get(k);
                    if (theKeyParam.getParam().equals("datetime")) {
                        //时间触发器，处理当前时间
                        Date theDate = new Date();
                        String dateValue = (String) theKeyParam.getValue();
                        Date valueTime = GogoTools.formatStrUTCToDateStr(dateValue);
                        if (GogoTools.compare_date(theDate, valueTime)) {
                            //设置时间大于当前时间，时间触发满足
                            logger.info(theNote.getTitle() + " trigger triggered");
                            UserInfo theUser = iUserInfoService.getUserByUserId(theNote.getUserId());
                            ArrayList<Recipient> recipients = iRecipientService.listRecipientByTriggerId(theTrigger.getTriggerId());
                            int cc = 0;
                            //读取接收人信息，发送邮件
                            for (int r = 0; r < recipients.size(); r++) {
                                Map in = new HashMap();
                                if (recipients.get(r).getEmail() == null) {
                                    continue;
                                }
                                if (recipients.get(r).getEmail().equals("")) {
                                    continue;
                                }
                                in.put("recipientEmail", recipients.get(r).getEmail());
                                in.put("subject", theUser.getEmail() + " send you a mail from LifeCapsule");
                                in.put("context", theNote.getTitle() + ", register to check the mail");
                                Map result = iAdminEmailBusinessService.sendEmail(in);
                                //如果发送成功，"send"为true
                                if ((Boolean) result.get("send")) {
                                    cc++;
                                }
                            }
                            /**
                             * recipient.size就是用户指定的所有接收人，如果cc=recipient.size就是所有接收人都发送成功了。
                             * 如果其中失败，则所有接收人都会在下次检测时再次重新发送，直接所有人都收到为止。
                             */
                            if (cc == recipients.size()) {
                                logger.info("send success");
                                iGogoKeyService.setGogoKeyTriggered(theGogoKey.getGogoKeyId());
                            } else {
                                logger.info("send failed");
                            }
                            /**
                             * 邮件发送成功，并不代表用户就一定会收到邮件，邮件可能被发送服务器，或者接收人的邮件服务器当垃圾邮件拦截掉。
                             */
                        }
                    }
                }
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }
}
