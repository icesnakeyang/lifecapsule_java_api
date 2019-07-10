package com.gogoyang.lifecapsule.business.trigger;

import com.gogoyang.lifecapsule.meta.gogoKey.entity.GogoKey;
import com.gogoyang.lifecapsule.meta.gogoKey.entity.GogoPublicKey;
import com.gogoyang.lifecapsule.meta.gogoKey.entity.KeyParams;
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
        //公共触发器模板的uuid
        String gogoPublicKeyId = in.get("gogoPublicKeyId").toString();
        //用户设置的触发器参数
        List<KeyParams> paramList = (List<KeyParams>) in.get("params");
        String noteId = (String) in.get("noteId");
        String remark = (String) in.get("remark");
        String triggerName = (String) in.get("triggerName");
        String gogoKeyId = (String) in.get("gogoKeyId");

        UserInfo userInfo = iUserInfoService.getUserByUserToken(token);
        if (userInfo == null) {
            throw new Exception("10003");
        }

        Trigger trigger = null;
        if (triggerId == null) {
            //没有触发器，创建一个
            trigger.setCreatedTime(new Date());
            trigger.setName(triggerName);
            trigger.setNoteId(noteId);
            trigger.setRemark(remark);
            trigger.setTriggerId(GogoTools.UUID().toString());
            iTriggerService.createTrigger(trigger);
        } else {
            trigger = iTriggerService.getTriggerByTriggerId(triggerId);
            if (trigger == null) {
                throw new Exception("10017");
            }
            trigger.setName(triggerName);
            trigger.setRemark(remark);
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
        if (gogoKeyId != null) {
            gogoKey = iGogoKeyService.getGogoKey(gogoKeyId);
        }
        if (gogoKey == null) {
            gogoKey = new GogoKey();
            gogoKey.setGogoKeyId(GogoTools.UUID().toString());
            gogoKey.setTriggerId(trigger.getTriggerId());
        }
        GogoPublicKey gogoPublicKey = iGogoKeyService.getGogoPublicKey(gogoPublicKeyId);
        if (gogoPublicKey == null) {
            throw new Exception("10001");
        }
        gogoKey.setTitle(gogoPublicKey.getTitle());
        gogoKey.setType(gogoPublicKey.getType());
        gogoKey.setGogoPublicKeyId(gogoPublicKeyId);
        gogoKey.setParams(paramList);
        iGogoKeyService.createGogoKey(gogoKey);
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
