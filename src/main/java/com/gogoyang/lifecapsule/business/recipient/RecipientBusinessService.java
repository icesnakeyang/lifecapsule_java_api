package com.gogoyang.lifecapsule.business.recipient;

import com.gogoyang.lifecapsule.meta.note.entity.NoteInfo;
import com.gogoyang.lifecapsule.meta.note.service.INoteService;
import com.gogoyang.lifecapsule.meta.recipient.entity.Recipient;
import com.gogoyang.lifecapsule.meta.recipient.entity.RecipientPerson;
import com.gogoyang.lifecapsule.meta.recipient.service.IRecipientService;
import com.gogoyang.lifecapsule.meta.user.entity.UserInfo;
import com.gogoyang.lifecapsule.meta.user.service.IUserInfoService;
import com.gogoyang.lifecapsule.utility.GogoTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecipientBusinessService implements IRecipientBusinessService {
    private final IUserInfoService iUserInfoService;
    private final IRecipientService iRecipientService;
    private final INoteService iNoteService;

    @Autowired
    public RecipientBusinessService(IRecipientService iRecipientService,
                                    IUserInfoService iUserInfoService,
                                    INoteService iNoteService) {
        this.iRecipientService = iRecipientService;
        this.iUserInfoService = iUserInfoService;
        this.iNoteService = iNoteService;
    }

    /**
     * 创建一个触发条件和接收人
     *
     * @param in
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map createRecipientPerson(Map in) throws Exception {
        String token = in.get("token").toString();
        String noteId = in.get("noteId").toString();
        String recipientId = (String) in.get("recipientId");
        String email = (String) in.get("email");
        String recipientName = (String) in.get("recipientName");
        String personName = (String) in.get("personName");
        String phone = (String) in.get("phone");
        String address = (String) in.get("address");
        String remark = (String) in.get("remark");

        /**
         * 校验当前登录用户
         */
        UserInfo userInfo = iUserInfoService.getUserByUserToken(token);
        if (userInfo == null) {
            throw new Exception("10003");
        }

        /**
         * 校验要设置的笔记
         */
        NoteInfo noteInfo = iNoteService.getNoteTinyByNoteId(noteId);
        if (noteInfo == null) {
            throw new Exception("10004");
        }

        /**
         * 校验笔记是否当前用户创建的
         */
        if (!noteInfo.getUserId().equals(userInfo.getUserId())) {
            throw new Exception("10011");
        }

        /**
         * 保存接收人
         * 1、检查是否有recipient
         * 2、有就检查recipient是否为当前noteId
         * 3、没有就创建一个recipient
         * 3、创建，或者保存recipientPerson
         */

        Recipient recipient = null;
        if (recipientId == null) {
            recipient = new Recipient();
            recipient.setUserId(userInfo.getUserId());
            recipient.setNoteId(noteId);
            recipient.setRecipientId(GogoTools.UUID().toString());
            if (recipientName == null) {
                recipientName = personName;
            }
            recipient.setRecipientName(recipientName);
            iRecipientService.saveRecipient(recipient);
            recipientId = recipient.getRecipientId();
        } else {
            //检查触发器是否存在
            recipient = iRecipientService.getRecipientByRecipientId(recipientId);
            if (recipient == null) {
                throw new Exception("10017");
            }
            //检查触发器是否属于当前编辑的笔记
            if (!recipient.getNoteId().equals(noteId)) {
                throw new Exception("10018");
            }
        }

        //新增一个接收人
        RecipientPerson recipientPerson = new RecipientPerson();
        recipientPerson.setRecipientId(recipientId);
        recipientPerson.setPersonId(GogoTools.UUID().toString());
        if (email != null) {
            ArrayList<String> emailList = new ArrayList<>();
            emailList.add(email);
            recipientPerson.setEmailList(emailList);
        }

        if (phone != null) {
            ArrayList<String> phoneList = new ArrayList<>();
            phoneList.add(phone);
            recipientPerson.setPhoneList(phoneList);
        }
        if (address != null) {
            ArrayList<String> addressList = new ArrayList<>();
            addressList.add(address);
            recipientPerson.setAddressList(addressList);
        }
        recipientPerson.setPersonName(personName);
        recipientPerson.setRemark(remark);

        iRecipientService.saveRecipientPerson(recipientPerson);

        /**
         * 把触发器，和接收人都返回给前端
         */
        Map out = new HashMap();
        out.put("recipient", recipient);
        out.put("recipientPerson", recipientPerson);
        return out;
    }

    @Override
    public Map listRecipientPersonByRecipientId(Map in) throws Exception {
        String token = in.get("token").toString();
        String recipientId = in.get("recipientId").toString();

        UserInfo userInfo = iUserInfoService.getUserByUserToken(token);
        if (userInfo == null) {
            throw new Exception("10003");
        }

        Map recipientPersonMap = iRecipientService.listRecipientPersonByRecipientId(recipientId);

        return recipientPersonMap;
    }

    @Override
    public Map listRecipientByNoteId(Map in) throws Exception {
        String token = in.get("token").toString();
        String noteId = in.get("noteId").toString();

        /**
         * 验证登录用户
         */
        UserInfo userInfo = iUserInfoService.getUserByUserToken(token);
        if (userInfo == null) {
            throw new Exception("10003");
        }

        /**
         * 验证要查询的笔记
         */
        NoteInfo noteInfo = iNoteService.getNoteTinyByNoteId(noteId);
        if (noteInfo == null) {
            throw new Exception("10004");
        }
        /**
         * 检查笔记是否为当前用户的
         */
        if (!noteInfo.getUserId().equals(userInfo.getUserId())) {
            throw new Exception("10011");
        }

        List<Recipient> recipientList = iRecipientService.listRecipientByNoteId(noteId);

        Map out = new HashMap();
        out.put("recipientList", recipientList);
        return out;
    }

    @Override
    public Map getRecipientByRecipientId(Map in) throws Exception {
        String token = in.get("token").toString();
        String recipientId = in.get("recipientId").toString();

        UserInfo userInfo = iUserInfoService.getUserByUserToken(token);
        if (userInfo == null) {
            throw new Exception("10003");
        }

        Recipient recipient = iRecipientService.getRecipientByRecipientId(recipientId);

        Map out = new HashMap();
        out.put("recipient", recipient);
        return out;
    }

    @Override
    public Map getPersonByPersonId(Map in) throws Exception {
        String token = in.get("token").toString();
        String personId = in.get("personId").toString();
        RecipientPerson person = iRecipientService.getPersonByPersonId(personId);
        Map out = new HashMap();
        out.put("person", person);
        return out;
    }
}
