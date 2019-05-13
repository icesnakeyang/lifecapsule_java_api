package com.gogoyang.lifecapsule.business.trigger;

import com.gogoyang.lifecapsule.meta.condition.entity.Condition;
import com.gogoyang.lifecapsule.meta.condition.service.IConditionService;
import com.gogoyang.lifecapsule.meta.email.entity.RecipientEmail;
import com.gogoyang.lifecapsule.meta.email.service.IRecipientEmailService;
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
import com.sun.tools.javap.TypeAnnotationWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class TriggerBusinessService implements ITriggerBusinessService {
    private final IUserInfoService iUserInfoService;
    private final INoteService iNoteService;
    private final ITriggerService iTriggerService;
    private final IRecipientService iRecipientService;
    private final IRecipientEmailService iRecipientEmailService;
    private final IConditionService iConditionService;

    @Autowired
    public TriggerBusinessService(IUserInfoService iUserInfoService,
                                  INoteService iNoteService,
                                  ITriggerService iTriggerService,
                                  IRecipientService iRecipientService,
                                  IRecipientEmailService iRecipientEmailService,
                                  IConditionService iConditionService) {
        this.iUserInfoService = iUserInfoService;
        this.iNoteService = iNoteService;
        this.iTriggerService = iTriggerService;
        this.iRecipientService = iRecipientService;
        this.iRecipientEmailService = iRecipientEmailService;
        this.iConditionService = iConditionService;
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

        //检查登录用户
        UserInfo userInfo = iUserInfoService.getUserByUserToken(token);
        if (userInfo == null) {
            throw new Exception("10003");
        }

        //检查当前编辑的笔记
        NoteInfo noteInfo = iNoteService.getNoteTinyByNoteId(noteId);
        if (noteInfo == null) {
            throw new Exception("10004");
        }

        //检查当前编辑的笔记的创建人是否当前登录用户
        if (!noteInfo.getUserId().equals(userInfo.getUserId())) {
            throw new Exception("10011");
        }

        //检查当前编辑的接收人是否已经有上级触发器
        //如果没有，就创建一个上级触发器
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
            ArrayList<RecipientEmail> emailList = new ArrayList<>();
            RecipientEmail recipientEmail = new RecipientEmail();
            recipientEmail.setRecipientId(recipient.getRecipientId());
            recipientEmail.setEmailId(GogoTools.UUID().toString());
            recipientEmail.setCreatedTime(new Date());
            recipientEmail.setEmail(email);
            emailList.add(recipientEmail);
            recipient.setEmailList(emailList);
            iRecipientEmailService.addEmail(recipientEmail);
        }
        if (phone != null) {
            ArrayList<String> phoneList = new ArrayList<>();
            phoneList.add(phone);
            recipient.setPhoneList(phoneList);
        }
        recipient.setRecipientName(name);
        recipient.setRemark(remark);
        recipient.setTriggerId(trigger.getTriggerId());
        recipient.setCreatedTime(new Date());
        iRecipientService.createRecipient(recipient);

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
         * 读取所有触发条件
         */
        ArrayList<Condition> conditionList = iConditionService.listConditionByTriggerId(triggerId);

        /**
         * 读取所有接收人
         */
        List<Recipient> recipientList = iRecipientService.listRecipientByTriggerId(triggerId);

        Map out = new HashMap();
        out.put("trigger", trigger);
        out.put("recipientList", recipientList);
        out.put("conditionList", conditionList);
        return out;
    }

    /**
     * 根据接收人id，读取接收人信息
     *
     * @param in
     * @return
     * @throws Exception
     */
    @Override
    public Map getRecipientByRecipientId(Map in) throws Exception {
        String token = in.get("token").toString();
        String recipientId = in.get("recipientId").toString();

        //首先检查当前登录用户是否存在
        UserInfo userInfo = iUserInfoService.getUserByUserToken(token);
        if (userInfo == null) {
            throw new Exception("10003");
        }

        //根据接班人id查询接收人
        Recipient recipient = iRecipientService.getRecipientByRecipientId(recipientId);
        if (recipient == null) {
            throw new Exception("10019");
        }

        //查询接收人的email
        ArrayList<RecipientEmail> emails = iRecipientEmailService.listRecipientEmailByRecipientId(recipientId);
        if (emails.size() > 0) {
            recipient.setEmailList(emails);
        }


        Trigger trigger = iTriggerService.getTriggerByTriggerId(recipient.getTriggerId());
        if (trigger == null) {
            throw new Exception("10017");
        }
        NoteInfo noteInfo = iNoteService.getNoteTinyByNoteId(trigger.getNoteId());
        if (noteInfo == null) {
            throw new Exception("10004");
        }
        if (!noteInfo.getUserId().equals(userInfo.getUserId())) {
            throw new Exception("10011");
        }

        Map out = new HashMap();
        out.put("recipient", recipient);
        return out;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addEmail(Map in) throws Exception {
        String token = in.get("token").toString();
        String recipientId = in.get("recipientId").toString();
        String email = in.get("email").toString();

        //检查登录用户
        UserInfo userInfo = iUserInfoService.getUserByUserToken(token);
        if (userInfo == null) {
            throw new Exception("10003");
        }
        //检查接收人
        Recipient recipient = iRecipientService.getRecipientByRecipientId(recipientId);
        if (recipient == null) {
            throw new Exception("10019");
        }
        //检查接收人的触发器
        Trigger trigger = iTriggerService.getTriggerByTriggerId(recipient.getTriggerId());
        if (trigger == null) {
            throw new Exception("10017");
        }
        //检查触发器的笔记
        NoteInfo noteInfo = iNoteService.getNoteTinyByNoteId(trigger.getNoteId());
        if (noteInfo == null) {
            throw new Exception("10004");
        }
        //检查笔记是否为当前用户创建
        if (!noteInfo.getUserId().equals(userInfo.getUserId())) {
            throw new Exception("10011");
        }

        /**
         * 保存email
         */
        RecipientEmail recipientEmail = new RecipientEmail();
        recipientEmail.setCreatedTime(new Date());
        recipientEmail.setEmail(email);
        recipientEmail.setEmailId(GogoTools.UUID().toString());
        recipientEmail.setRecipientId(recipientId);
        iRecipientEmailService.addEmail(recipientEmail);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addCondition(Map in) throws Exception {
        String token = in.get("token").toString();
        String triggerId = in.get("triggerId").toString();
        String conditionName = in.get("conditionName").toString();
        String conditionKey = in.get("conditionKey").toString();
        Date conditionTime = (Date) in.get("conditionTime");
        String remark = (String) in.get("remark");

        if (conditionTime == null) {
            throw new Exception("10020");
        }

        UserInfo userInfo = iUserInfoService.getUserByUserToken(token);
        if (userInfo == null) {
            throw new Exception("10003");
        }
        Trigger trigger = iTriggerService.getTriggerByTriggerId(triggerId);
        if (trigger == null) {
            throw new Exception("10017");
        }
        NoteInfo noteInfo = iNoteService.getNoteTinyByNoteId(trigger.getNoteId());
        if (noteInfo == null) {
            throw new Exception("10004");
        }
        if (!noteInfo.getUserId().equals(userInfo.getUserId())) {
            throw new Exception("10011");
        }

        Condition condition = new Condition();
        condition.setConditionId(GogoTools.UUID().toString());
        condition.setKey(conditionKey);
        condition.setName(conditionName);
//        condition.setRemark();
        condition.setTriggerId(trigger.getTriggerId());
        condition.setValue(conditionTime.toString());
        condition.setRemark(remark);
        iConditionService.createCondition(condition);
    }

    /**
     * 根据conditionId查询触发条件信息
     * @param in
     * @return
     * @throws Exception
     */
    @Override
    public Map getConditionByConditionId(Map in) throws Exception {
        String token=in.get("token").toString();
        String conditionId=in.get("conditionId").toString();

        UserInfo userInfo=iUserInfoService.getUserByUserToken(token);
        if(userInfo==null){
            throw new Exception("10003");
        }

        Condition condition=iConditionService.getConditionByConditionId(conditionId);
        if(condition==null){
            throw new Exception("10021");
        }
        Trigger trigger=iTriggerService.getTriggerByTriggerId(condition.getTriggerId());
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
        out.put("condition", condition);
        return out;
    }
}
