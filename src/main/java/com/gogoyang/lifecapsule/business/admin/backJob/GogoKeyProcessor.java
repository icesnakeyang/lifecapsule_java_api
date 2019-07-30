package com.gogoyang.lifecapsule.business.admin.backJob;

import com.gogoyang.lifecapsule.meta.gogoKey.entity.GogoKey;
import com.gogoyang.lifecapsule.meta.gogoKey.entity.KeyParam;
import com.gogoyang.lifecapsule.meta.gogoKey.service.IGogoKeyService;
import com.gogoyang.lifecapsule.meta.note.entity.NoteInfo;
import com.gogoyang.lifecapsule.meta.note.service.INoteService;
import com.gogoyang.lifecapsule.meta.trigger.entity.Trigger;
import com.gogoyang.lifecapsule.meta.trigger.service.ITriggerService;
import com.gogoyang.lifecapsule.utility.GogoTools;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;
import java.util.List;

public class GogoKeyProcessor extends QuartzJobBean {
    @Autowired
    private IGogoKeyService iGogoKeyService;
    @Autowired
    private ITriggerService iTriggerService;
    @Autowired
    private INoteService iNoteService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("我来了");
        try {
            /**
             * 这里应该读取所有的trigger
             */
            List<GogoKey> gogoKeys = iGogoKeyService.listGogoKeyAll();
            for (int i = 0; i < gogoKeys.size(); i++) {
                GogoKey theGogoKey = iGogoKeyService.getGogoKeyByGogoKeyId(gogoKeys.get(i).getGogoKeyId());
                Trigger theTrigger = iTriggerService.getTriggerByTriggerId(theGogoKey.getTriggerId());
                NoteInfo theNote = iNoteService.getNoteTinyByNoteId(theTrigger.getNoteId());
                for (int k = 0; k < theGogoKey.getKeyParams().size(); k++) {
                    KeyParam theKeyParam = theGogoKey.getKeyParams().get(k);
                    if (theKeyParam.getParam().equals("datetime")) {
                        //时间触发器，检查当前时间是否已经符合条件
                        Date theDate = new Date();
                        String dateValue = (String) theKeyParam.getValue();
                        Date valueTime = GogoTools.formatStrUTCToDateStr(dateValue);
                        if (GogoTools.compare_date(theDate, valueTime)) {
                            logger.info(theNote.getTitle() + " 被触发了");
                            iGogoKeyService.setGogoKeyTriggered(theGogoKey.getGogoKeyId());
                        }
                    }
                }
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }
}
