package com.gogoyang.lifecapsule.business.trigger;

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
    private final IGogoKeyService iGogoKeyService;

    @Autowired
    public TriggerBusinessService(IUserInfoService iUserInfoService,
                                  INoteService iNoteService,
                                  ITriggerService iTriggerService,
                                  IRecipientService iRecipientService,
                                  IGogoKeyService iGogoKeyService) {
        this.iUserInfoService = iUserInfoService;
        this.iNoteService = iNoteService;
        this.iTriggerService = iTriggerService;
        this.iRecipientService = iRecipientService;
        this.iGogoKeyService = iGogoKeyService;
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

        int eof = 0;
        if (phone == null || phone.equals("")) {
            eof++;
        }
        if (email == null || email.equals("")) {
            eof++;
        }
        if (address == null || address.equals("")) {
            eof++;
        }
        if (eof == 3) {
            throw new Exception("10022");
        }

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
        /**
         * 首先根据noteId读取trigger，检查是否已经有trigger
         * 如果有trigger就修改，没有就新新增
         */
        trigger = iTriggerService.getTriggerByNoteId(noteId);
        if (trigger == null) {
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
        recipient.setAddress(address);
        recipient.setEmail(email);
        recipient.setPhone(phone);
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
         * 读取触发条件
         */
        GogoKey gogoKey = iGogoKeyService.getGogoKeyByTriggerId(triggerId);
        trigger.setGogoKey(gogoKey);

        /**
         * 读取所有接收人
         */
        List<Recipient> recipientList = iRecipientService.listRecipientByTriggerId(triggerId);
        trigger.setRecipientList(recipientList);

        Map out = new HashMap();
        out.put("trigger", trigger);
        return out;
    }

    /**
     * 根据noteId查询trigger
     *
     * @param in
     * @return
     * @throws Exception
     */
    @Override
    public Map getTriggerByNoteId(Map in) throws Exception {
        String token = in.get("token").toString();
        String noteId = in.get("noteId").toString();

        //检查当前用户是否登录
        UserInfo userInfo = iUserInfoService.getUserByUserToken(token);
        if (userInfo == null) {
            throw new Exception("10003");
        }

        //检查noteId是否有效
        NoteInfo noteInfo = iNoteService.getNoteTinyByNoteId(noteId);
        if (noteInfo == null) {
            throw new Exception("10004");
        }

        //检查note是否当前用户的
        if (!noteInfo.getUserId().equals(userInfo.getUserId())) {
            throw new Exception("10011");
        }

        Map out = new HashMap();
        //note是否有Trigger
        Trigger trigger = iTriggerService.getTriggerByNoteId(noteId);
        if (trigger != null) {
            //读取gogoKey
            GogoKey gogoKey = iGogoKeyService.getGogoKeyByTriggerId(trigger.getTriggerId());

            if (gogoKey != null) {
                trigger.setGogoKey(gogoKey);
            }
            ArrayList<Recipient> recipients = iRecipientService.listRecipientByTriggerId(trigger.getTriggerId());
            trigger.setRecipientList(recipients);
            out.put("trigger", trigger);
        }

        return out;
    }

    /**
     * 删除一个触发器trigger
     *
     * @param in
     * @throws Exception
     */
    @Override
    public void deleteTrigger(Map in) throws Exception {
        String token = in.get("token").toString();
        String triggerId = in.get("triggerId").toString();

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
            throw new Exception("10018");
        }

        if (!userInfo.getUserId().equals(noteInfo.getUserId())) {
            throw new Exception("10011");
        }

        iTriggerService.deleteTrigger(triggerId);
        iRecipientService.deleteRecipientByTriggerId(triggerId);
        GogoKey gogoKey = iGogoKeyService.getGogoKeyByTriggerId(triggerId);
        if (gogoKey != null) {
            iGogoKeyService.deleteGogoKeyByTriggerId(triggerId);
            iGogoKeyService.deleteKeyParamsByGogokeyId(gogoKey.getGogoKeyId());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveTrigger(Map in) throws Exception {
        String token = in.get("token").toString();
        GogoKey gogoKey = (GogoKey) in.get("gogoKey");
        String triggerId = (String) in.get("triggerId");
        String remark = (String) in.get("remark");
        String noteId = (String) in.get("noteId");

        UserInfo userInfo = iUserInfoService.getUserByUserToken(token);
        if (userInfo == null) {
            throw new Exception("10003");
        }

        Trigger trigger = null;
        if (triggerId != null) {
            /**
             * 如果有triggerId，就读取trigger，再修改
             */
            trigger = iTriggerService.getTriggerByTriggerId(triggerId);
            trigger.setRemark(remark);
            iTriggerService.updateTrigger(trigger);
            /**
             * 把trigger的gogoKey删除，再新增一个
             */
            iGogoKeyService.deleteGogoKeyByTriggerId(triggerId);
            gogoKey.setTriggerId(triggerId);
            gogoKey.setCreatedTime(new Date());
            gogoKey.setGogoKeyId(GogoTools.UUID().toString());
            gogoKey.setKeyStatus("active");
            iGogoKeyService.createGogoKey(gogoKey);
        } else {
            /**
             * 如果没有triggerId，就新建一个trigger
             */
            trigger = new Trigger();
            trigger.setTriggerId(GogoTools.UUID().toString());
            trigger.setGogoKey(gogoKey);
            trigger.setRemark(remark);
            trigger.setNoteId(noteId);
            trigger.setCreatedTime(new Date());
            iTriggerService.createTrigger(trigger);
            //新增一个gogoKey
            gogoKey.setTriggerId(trigger.getTriggerId());
            gogoKey.setCreatedTime(new Date());
            gogoKey.setGogoKeyId(GogoTools.UUID().toString());
            gogoKey.setKeyStatus("active");
            iGogoKeyService.createGogoKey(gogoKey);
        }
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
    public void saveGogoKey(Map in) throws Exception {
        //用户token
        String token = in.get("token").toString();
        //用户触发器Id
        String triggerId = (String) in.get("triggerId");
        //用户设置的触发器参数
        List<KeyParam> keyParams = (List<KeyParam>) in.get("keyParams");
        String gogoKeyId = (String) in.get("gogoKeyId");
        String triggerName = (String) in.get("triggerName");
        String noteId = (String) in.get("noteId");
        String triggerRemark = (String) in.get("triggerRemark");
        String gogoKeyTitle = (String) in.get("title");
        String gogoKeyDescription = (String) in.get("description");

        //检查用户是否登录
        UserInfo userInfo = iUserInfoService.getUserByUserToken(token);
        if (userInfo == null) {
            throw new Exception("10003");
        }

        Trigger trigger = null;
        if (triggerId != null) {
            trigger = iTriggerService.getTriggerByTriggerId(triggerId);
        }
        if (trigger == null) {
            //没有触发器，创建一个
            trigger = new Trigger();
            trigger.setCreatedTime(new Date());
            if (triggerName != null) {
                trigger.setName(triggerName);
                trigger.setRemark(triggerRemark);
            } else {
                trigger.setName(gogoKeyTitle);
                trigger.setRemark(gogoKeyDescription);
            }
            trigger.setNoteId(noteId);
            trigger.setTriggerId(GogoTools.UUID().toString());
            iTriggerService.createTrigger(trigger);
        } else {
            trigger.setRemark(triggerRemark);
            trigger.setName(triggerName);
            iTriggerService.updateTrigger(trigger);
        }

        NoteInfo noteInfo = iNoteService.getNoteTinyByNoteId(trigger.getNoteId());
        if (noteInfo == null) {
            throw new Exception("10004");
        }
        if (!noteInfo.getUserId().equals(userInfo.getUserId())) {
            throw new Exception("10011");
        }

        GogoKey gogoKey = null;
        if (trigger != null) {
            gogoKey = iGogoKeyService.getGogoKeyByTriggerId(trigger.getTriggerId());
        }
        if (gogoKey == null) {
            gogoKey = new GogoKey();
            gogoKey.setGogoKeyId(GogoTools.UUID().toString());
            gogoKey.setTriggerId(trigger.getTriggerId());
            gogoKey.setCreatedTime(new Date());
            gogoKey.setTitle(gogoKeyTitle);
            gogoKey.setDescription(gogoKeyDescription);
            gogoKey.setKeyParams(keyParams);
            gogoKey.setKeyStatus("userKey");
            iGogoKeyService.createGogoKey(gogoKey);
        } else {
            gogoKey.setKeyParams(keyParams);
            gogoKey.setDescription(gogoKeyDescription);
            gogoKey.setTitle(gogoKeyTitle);
            iGogoKeyService.updateGogoKey(gogoKey);
        }
    }

    @Override
    public Map getGogoKeyByTriggerId(Map in) throws Exception {
        String token = in.get("token").toString();
        String triggerId = in.get("triggerId").toString();

        UserInfo userInfo = iUserInfoService.getUserByUserToken(token);
        if (userInfo == null) {
            throw new Exception("10003");
        }

        GogoKey gogoKey = iGogoKeyService.getGogoKeyByTriggerId(triggerId);
        if (gogoKey == null) {
            throw new Exception("10021");
        }

        Map out = new HashMap();
        out.put("gogoKey", gogoKey);

        return out;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map updateRecipient(Map in) throws Exception {
        String token = in.get("token").toString();
        String recipientId = in.get("recipientId").toString();
        String name = (String) in.get("name");
        String phone = (String) in.get("phone");
        String email = (String) in.get("email");
        String address = (String) in.get("address");
        String remark = (String) in.get("remark");

        int eof = 0;
        if (phone == null || phone.equals("")) {
            eof++;
        }
        if (email == null || email.equals("")) {
            eof++;
        }
        if (address == null || address.equals("")) {
            eof++;
        }
        if (eof == 3) {
            throw new Exception("10022");
        }

        //检查登录用户
        UserInfo userInfo = iUserInfoService.getUserByUserToken(token);
        if (userInfo == null) {
            throw new Exception("10003");
        }

        //检查当前编辑的recipient是否是当前用户创建的
        Recipient recipient = iRecipientService.getRecipientByRecipientId(recipientId);
        if (recipient == null) {
            throw new Exception("10019");
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
            throw new Exception("10023");
        }

        //修改接收人 recipient
        recipient.setAddress(address);
        recipient.setEmail(email);
        recipient.setPhone(phone);
        recipient.setRecipientName(name);
        recipient.setRemark(remark);
        recipient.setCreatedTime(new Date());
        iRecipientService.updateRecipient(recipient);

        Map out = new HashMap();
        out.put("trigger", trigger);
        out.put("recipient", recipient);
        return out;
    }

    /**
     * 删除一个接收人
     *
     * @param in
     * @throws Exception
     */
    @Override
    public void deleteRecipient(Map in) throws Exception {
        String token = in.get("token").toString();
        String recipientId = in.get("recipientId").toString();

        Recipient recipient = iRecipientService.getRecipientByRecipientId(recipientId);
        if (recipient == null) {
            throw new Exception("10019");
        }

        UserInfo userInfo = iUserInfoService.getUserByUserToken(token);
        if (userInfo == null) {
            throw new Exception("10003");
        }

        Trigger trigger = iTriggerService.getTriggerByTriggerId(recipient.getTriggerId());
        if (trigger == null) {
            throw new Exception("10017");
        }

        NoteInfo noteInfo = iNoteService.getNoteTinyByNoteId(trigger.getNoteId());
        if (noteInfo == null) {
            throw new Exception("10004");
        }

        if (!userInfo.getUserId().equals(noteInfo.getUserId())) {
            throw new Exception("10023");
        }

        iRecipientService.deleteRecipient(recipientId);
    }
}
