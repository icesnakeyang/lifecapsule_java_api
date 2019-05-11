package com.gogoyang.lifecapsule.business.trigger;

import com.gogoyang.lifecapsule.meta.note.entity.NoteInfo;
import com.gogoyang.lifecapsule.meta.note.service.INoteService;
import com.gogoyang.lifecapsule.meta.recipient.entity.Recipient;
import com.gogoyang.lifecapsule.meta.recipient.service.IRecipientService;
import com.gogoyang.lifecapsule.meta.trigger.entity.Trigger;
import com.gogoyang.lifecapsule.meta.trigger.service.ITriggerService;
import com.gogoyang.lifecapsule.meta.user.entity.UserInfo;
import com.gogoyang.lifecapsule.meta.user.service.IUserInfoService;
import com.gogoyang.lifecapsule.utility.GogoTools;
import com.sun.org.apache.xml.internal.resolver.readers.ExtendedXMLCatalogReader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class TriggerBusinessService implements ITriggerBusinessService {
    private final IUserInfoService iUserInfoService;
    private final INoteService iNoteService;
    private final ITriggerService iTriggerService;
    private final IRecipientService iRecipientService;

    public TriggerBusinessService(IUserInfoService iUserInfoService,
                                  INoteService iNoteService,
                                  ITriggerService iTriggerService,
                                  IRecipientService iRecipientService) {
        this.iUserInfoService = iUserInfoService;
        this.iNoteService = iNoteService;
        this.iTriggerService = iTriggerService;
        this.iRecipientService = iRecipientService;
    }

    /**
     * 创建添加一个接收人
     *
     * @param in
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map createRecipient(Map in) throws Exception {
        String token = in.get("token").toString();
        String noteId = (String) in.get("noteId");
        String triggerId = (String) in.get("triggerId");
        String name = in.get("name").toString();
        String phone = (String) in.get("phone");
        String email = (String) in.get("email");
        String address = (String) in.get("address");
        String remark = (String) in.get("remark");

        UserInfo userInfo = iUserInfoService.getUserByUserToken(token);
        if (userInfo == null) {
            throw new Exception("10003");
        }

        NoteInfo noteInfo = iNoteService.getNoteTinyByNoteId(noteId);
        if (noteInfo == null) {
            throw new Exception("10004");
        }

        if (!noteInfo.getUserId().equals(userInfo.getUserId())) {
            throw new Exception("10011");
        }

        Trigger trigger = null;
        if (triggerId != null) {
            //有触发器id，读取触发器
            trigger = iTriggerService.getTriggerByTriggerId(triggerId);
            if (trigger == null) {
                throw new Exception("10017");
            }
            //触发器是否属于当前的笔记
            if (!trigger.getNoteId().equals(noteInfo.getNoteId())) {
                throw new Exception("10018");
            }
        } else {
            //先创建一个触发器trigger
            trigger = new Trigger();
            trigger.setTriggerId(GogoTools.UUID().toString());
            trigger.setCreatedTime(new Date());
            trigger.setName(name);
            trigger.setNoteId(noteId);
            trigger.setRemark(remark);
            iTriggerService.createTrigger(trigger);
        }

        //创建一个接收人 recipient
        Recipient recipient = new Recipient();
        recipient.setRecipientId(GogoTools.UUID().toString());
        if (address != null) {
            ArrayList<String> addressList = new ArrayList<>();
            addressList.add(address);
            recipient.setAddressList(addressList);
        }
        if (email != null) {
            ArrayList<String> emailList = new ArrayList<>();
            emailList.add(email);
            recipient.setEmailList(emailList);
        }
        if (phone != null) {
            ArrayList<String> phoneList = new ArrayList<>();
            phoneList.add(phone);
            recipient.setPhoneList(phoneList);
        }
        recipient.setName(name);
        recipient.setRemark(remark);
        recipient.setTriggerId(trigger.getTriggerId());
        recipient.setCreatedTime(new Date());
        iRecipientService.saveRecipient(recipient);

        Map out = new HashMap();
        out.put("trigger", trigger);
        out.put("recipient", recipient);
        return out;
    }

    /**
     * 根据笔记id查询所有的触发器
     *
     * @param in
     * @return
     * @throws Exception
     */
    @Override
    public Map listTriggerByNoteId(Map in) throws Exception {
        String token = in.get("token").toString();
        String noteId = in.get("noteId").toString();

        UserInfo userInfo = iUserInfoService.getUserByUserToken(token);
        if (userInfo == null) {
            throw new Exception("10003");
        }

        NoteInfo noteInfo = iNoteService.getNoteTinyByNoteId(noteId);
        if (noteInfo == null) {
            throw new Exception("10004");
        }

        if (!noteInfo.getUserId().equals(userInfo.getUserId())) {
            throw new Exception("10011");
        }

        ArrayList<Trigger> triggerList = iTriggerService.listTriggerByNoteId(noteId);

        Map out = new HashMap();
        out.put("triggerList", triggerList);
        return out;
    }

    @Override
    public Map listRecipientByTriggerId(Map in) throws Exception {
        String token = in.get("token").toString();
        String triggerId = in.get("triggerId").toString();
        List<Recipient> recipientList = iRecipientService.listRecipientByTriggerId(triggerId);

        Map out = new HashMap();
        out.put("recipientList", recipientList);
        return out;
    }

    /**
     * 根据触发器id，查询触发器配置信息
     * 包括：所有的触发条件，和所有的接收人
     *
     * @param in
     * @return
     * @throws Exception
     */
    @Override
    public Map getTriggerByTriggerId(Map in) throws Exception {
        String token = in.get("token").toString();
        String triggerId = in.get("triggerId").toString();

        /**
         * 检查当前用户
         */
        UserInfo userInfo = iUserInfoService.getUserByUserToken(token);
        if (userInfo == null) {
            throw new Exception("10003");
        }

        /**
         * 查询触发器
         */
        Trigger trigger = iTriggerService.getTriggerByTriggerId(triggerId);
        if (trigger == null) {
            throw new Exception("10017");
        }
        /**
         * 检查触发器的笔记
         */
        NoteInfo noteInfo = iNoteService.getNoteTinyByNoteId(trigger.getNoteId());
        if (noteInfo == null) {
            throw new Exception("10004");
        }
        /**
         * 检查笔记是否当前用户创建的
         */
        if (!noteInfo.getUserId().equals(userInfo.getUserId())) {
            throw new Exception("10011");
        }

        /**
         * 读取所有接收人
         */
        List<Recipient> recipientList = iRecipientService.listRecipientByTriggerId(triggerId);

        Map out = new HashMap();
        out.put("trigger", trigger);
        out.put("recipientList", recipientList);

        return out;
    }

    @Override
    public Map getRecipientByRecipientId(Map in) throws Exception {
        String token=in.get("token").toString();
        String recipientId=in.get("recipientId").toString();

        UserInfo userInfo=iUserInfoService.getUserByUserToken(token);
        if(userInfo==null){
            throw new Exception("10003");
        }

        Recipient recipient=iRecipientService.getRecipientByRecipientId(recipientId);
        if(recipient==null){
            throw new Exception("10019");
        }

        Trigger trigger=iTriggerService.getTriggerByTriggerId(recipient.getTriggerId());
        if(trigger==null){
            throw new Exception("10017");
        }
        NoteInfo noteInfo=iNoteService.getNoteTinyByNoteId(trigger.getNoteId());
        if(noteInfo==null){
            throw new Exception("10004");
        }
        if(!noteInfo.getUserId().equals(userInfo.getUserId())){
            throw new Exception("10011");
        }

        Map out=new HashMap();
        out.put("recipient", recipient);
        return out;
    }
}
