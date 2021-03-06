package com.gogoyang.lifecapsule.business.trigger;

import com.gogoyang.lifecapsule.business.common.ICommonService;
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
    private final ICommonService iCommonService;

    @Autowired
    public TriggerBusinessService(IUserInfoService iUserInfoService,
                                  INoteService iNoteService,
                                  ITriggerService iTriggerService,
                                  IRecipientService iRecipientService,
                                  IGogoKeyService iGogoKeyService,
                                  ICommonService iCommonService) {
        this.iUserInfoService = iUserInfoService;
        this.iNoteService = iNoteService;
        this.iTriggerService = iTriggerService;
        this.iRecipientService = iRecipientService;
        this.iGogoKeyService = iGogoKeyService;
        this.iCommonService = iCommonService;
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

    /**
     * 修改trigger的remark
     * 如果没有trigger就创建一个
     *
     * @param in
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveTriggerRemark(Map in) throws Exception {
        String token = in.get("token").toString();
        String triggerId = (String) in.get("triggerId");
        String noteId = in.get("noteId").toString();
        String remark = in.get("remark").toString();

        UserInfo userInfo = iCommonService.getUserByToken(token);

        NoteInfo noteInfo = iCommonService.getNoteByNoteId(noteId, userInfo.getUserId());

        Trigger trigger = iTriggerService.getTriggerByNoteId(noteId);
        if (trigger == null) {
            if (triggerId != null) {
                /**
                 * 用户提交的triggerId无效，终止操作
                 */
                throw new Exception("10025");
            }
            /**
             * 该笔记还没有trigger，创建一个
             */
            trigger = new Trigger();
            trigger.setCreatedTime(new Date());
            trigger.setNoteId(noteInfo.getNoteId());
            trigger.setRemark(remark);
            trigger.setTriggerId(GogoTools.UUID().toString());
            iTriggerService.createTrigger(trigger);
        } else {
            if (!trigger.getTriggerId().equals(triggerId)) {
                /**
                 * 用户提交的triggerId和note的trigger不一致
                 */
                throw new Exception("10025");
            }
            trigger.setRemark(remark);
            iTriggerService.updateTriggerRemark(trigger);
        }
    }

    /**
     * 保存一个接收人
     * 自动校验，自动判断新增还是修改
     *
     * @param in
     * @return
     * @throws Exception
     */
    @Override
    public Map saveRecipient(Map in) throws Exception {
        String token = in.get("token").toString();
        String noteId = (String) in.get("noteId");
        String triggerId = (String) in.get("triggerId");
        String name = in.get("name").toString();
        String phone = (String) in.get("phone");
        String email = (String) in.get("email");
        String address = (String) in.get("address");
        String remark = (String) in.get("remark");
        String recipientId = (String) in.get("recipientId");

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
        UserInfo userInfo = iCommonService.getUserByToken(token);

        //检查当前编辑的笔记
        NoteInfo noteInfo = iCommonService.getNoteByNoteId(noteId);

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
        if (triggerId != null && trigger != null) {
            if (!trigger.getTriggerId().equals(triggerId)) {
                throw new Exception("10025");
            }
        }
        if (trigger == null) {
            //先创建一个触发器trigger
            trigger = new Trigger();
            trigger.setTriggerId(GogoTools.UUID().toString());
            trigger.setCreatedTime(new Date());
            trigger.setNoteId(noteId);
            iTriggerService.createTrigger(trigger);
        } else {
            if (recipientId != null) {
                /**
                 * 已存在recipient，检查是否属于当前的trigger
                 */
                int cc = 0;
                ArrayList<Recipient> recipientArrayList = iRecipientService.listRecipientByTriggerId(triggerId);
                if (recipientArrayList.size() > 0) {
                    for (int i = 0; i < recipientArrayList.size(); i++) {
                        if (recipientArrayList.get(i).getRecipientId().equals(recipientId)) {
                            cc++;
                        }
                    }
                }
                if (cc == 0) {
                    throw new Exception("10026");
                }
                //删除原有的接收人，再新增
                iRecipientService.deleteRecipient(recipientId);
            }
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

    /**
     * 保存gogoKey
     * 检查，校验，删除旧的，增加新的
     *
     * @param in
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveGogoKey(Map in) throws Exception {
        //用户token
        String token = in.get("token").toString();
        //用户触发器Id
        String triggerId = (String) in.get("triggerId");
        //用户设置的触发器参数
        List<KeyParam> keyParams = (List<KeyParam>) in.get("keyParams");
        //公共模板id
        String gogoPublicKeyId = (String) in.get("gogoPublicKeyId");
        //笔记id
        String noteId = (String) in.get("noteId");
        //检查用户是否登录
        UserInfo userInfo = iCommonService.getUserByToken(token);
        //检查笔记
        NoteInfo noteInfo = iCommonService.getNoteByNoteId(noteId, userInfo.getUserId());
        //根据笔记读取触发器
        Trigger trigger = iTriggerService.getTriggerByNoteId(noteInfo.getNoteId());
        if (trigger == null) {
            if (triggerId != null) {
                //用户上传一个无效triggerId
                throw new Exception("10025");
            }
            //创建一个trigger
            trigger = new Trigger();
            trigger.setCreatedTime(new Date());
            trigger.setNoteId(noteInfo.getNoteId());
            trigger.setTriggerId(GogoTools.UUID().toString());
            iTriggerService.createTrigger(trigger);
        } else {
            if (!triggerId.equals(trigger.getTriggerId())) {
                //用户上传的triggerId无效
                throw new Exception("10025");
            }
        }
        /**
         * 检查publicKey
         * 用户的触发器只能从publicKey里面选择，没有publicKey，就不能创建gogoKey
         */
        GogoKey publicKey = iCommonService.getPublicKey(gogoPublicKeyId);
        /**
         * 这里已经有了trigger，如果gogokey已存在，先删除，然后直接增加gogoKey
         */
        GogoKey gogoKey = iGogoKeyService.getGogoKeyByTriggerId(trigger.getTriggerId());
        if (gogoKey != null) {
            iGogoKeyService.deleteGogoKeyByTriggerId(trigger.getTriggerId());
        }
        //增加gogokey
        gogoKey = new GogoKey();
        gogoKey.setGogoKeyId(GogoTools.UUID().toString());
        gogoKey.setTriggerId(trigger.getTriggerId());
        gogoKey.setCreatedTime(new Date());
        gogoKey.setPublicKeyId(publicKey.getPublicKeyId());
        gogoKey.setTitle(publicKey.getTitle());
        gogoKey.setDescription(publicKey.getDescription());
        gogoKey.setKeyParams(keyParams);
        iGogoKeyService.createGogoKey(gogoKey);
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
