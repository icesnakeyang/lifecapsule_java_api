package com.gogoyang.lifecapsule.business.note;

import com.gogoyang.lifecapsule.meta.category.entity.NoteCategory;
import com.gogoyang.lifecapsule.meta.category.service.ICategoryService;
import com.gogoyang.lifecapsule.meta.note.entity.NoteDetail;
import com.gogoyang.lifecapsule.meta.note.entity.NoteInfo;
import com.gogoyang.lifecapsule.meta.note.service.INoteService;
import com.gogoyang.lifecapsule.meta.security.entity.SecurityKey;
import com.gogoyang.lifecapsule.meta.security.service.ISecurityService;
import com.gogoyang.lifecapsule.meta.user.entity.UserInfo;
import com.gogoyang.lifecapsule.meta.user.service.IUserInfoService;
import com.gogoyang.lifecapsule.utility.GogoTools;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.spec.KeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NoteBusinessService implements INoteBusinessService {
    private final INoteService iNoteService;
    private final IUserInfoService iUserInfoService;
    private final ICategoryService iCategoryService;
    private final ISecurityService iSecurityService;

    @Autowired
    public NoteBusinessService(INoteService iNoteService,
                               IUserInfoService iUserInfoService,
                               ICategoryService iCategoryService,
                               ISecurityService iSecurityService) {
        this.iNoteService = iNoteService;
        this.iUserInfoService = iUserInfoService;
        this.iCategoryService = iCategoryService;
        this.iSecurityService = iSecurityService;
    }

    /**
     * 创建一个笔记
     *
     * @param in
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map createNote(Map in) throws Exception {
        String title = in.get("title").toString();
        String token = (String) in.get("token");
        String detail = in.get("detail").toString();
        String categoryId = (String) in.get("categoryId");
        String encryptKey = in.get("encryptKey").toString();

        /**
         * 根据token取用户信息
         */
        if (token == null) {
            throw new Exception("10010");
        }
        UserInfo userInfo = iUserInfoService.getUserByUserToken(token);
        if (userInfo == null) {
            throw new Exception("10003");
        }

        /**
         * 如果没有分类id，就使用该用户的默认分类id
         */
        if (categoryId == null) {
            NoteCategory category = iCategoryService.getCategoryByCategoryName("Default", userInfo.getUserId());
            categoryId = category.getCategoryId();
        }
        NoteInfo noteInfo = new NoteInfo();
        noteInfo.setUserId(userInfo.getUserId());
        noteInfo.setNoteId(GogoTools.UUID().toString());
        noteInfo.setCreatedTime(new Date());
        noteInfo.setDetail(detail);
        noteInfo.setTitle(title);
        noteInfo.setCategoryId(categoryId);
        noteInfo.setUserEncodeKey(encryptKey);
        iNoteService.createNote(noteInfo);

        Map out = new HashMap();
        out.put("note", noteInfo);
        return out;
    }

    /**
     * 查询用户的笔记列表
     *
     * @param in
     * @return
     * @throws Exception
     */
    @Override
    public Map listNoteByUserToken(Map in) throws Exception {
        String token = (String) in.get("token");
        //第几页
        Integer pageIndex = (Integer) in.get("pageIndex");
        //每页有几条数据
        Integer pageSize = (Integer) in.get("pageSize");

        /**
         * if no token, means user didn't sign in.
         */
        if (token == null) {
            throw new Exception("10010");
        }

        /**
         * load user
         */
        UserInfo userInfo = iUserInfoService.getUserByUserToken(token);
        if (userInfo == null) {
            //token verify error
            throw new Exception("10003");
        }

        /**
         * calculate page
         * pageIndex start from 1
         */
        Integer offset = (pageIndex - 1) * pageSize;

        List<NoteInfo> noteInfoList = iNoteService.listNoteByUserId(userInfo.getUserId(), offset, pageSize);

        Map out = new HashMap();
        out.put("noteList", noteInfoList);
        return out;
    }

    /**
     * 查询笔记分类下的笔记
     *
     * @param in
     * @return
     * @throws Exception
     */
    @Override
    public Map listNoteByCategory(Map in) throws Exception {
        String token = in.get("token").toString();
        String categoryId = in.get("categoryId").toString();
        Integer pageIndex = (Integer) in.get("pageIndex");
        Integer pageSize = (Integer) in.get("pageSize");

        UserInfo userInfo = iUserInfoService.getUserByUserToken(token);
        if (userInfo == null) {
            throw new Exception("10003");
        }

        Integer offset = (pageIndex - 1) * pageSize;

        List<NoteInfo> noteInfoList = iNoteService.listNoteByCategory(categoryId, userInfo.getUserId(), offset, pageSize);

        Map out = new HashMap();
        out.put("noteList", noteInfoList);
        return out;
    }

    @Override
    public Map getNoteDetailByNoteId(Map in) throws Exception {
        String token = (String) in.get("token");
        String noteId = in.get("noteId").toString();
        String encryptKey = in.get("encryptKey").toString();
        String keyToken = (String) in.get("keyToken");
        String detail = (String) in.get("detail");

        String decryptKey = encryptKey;

        //读取生成的RSA私钥
        String privateKey = iSecurityService.getRSAKey(keyToken);
        //用私钥解密用户上传的AES秘钥
        String strAESKey = GogoTools.decryptRSAByPrivateKey(encryptKey, privateKey);
        //用AES加密note的AES，发送回前端


        byte[] encryptBytes = Base64.decode(detail);


//        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
////        Cipher cipher = Cipher.getInstance("AES");
//        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
//        byte[] decryptBytes = cipher.doFinal(encryptBytes);
//
//
//        String outStr = new String(decryptBytes);
//
//        GogoTools.decryptAESKey(detail,encryptKey);


        /**
         * 1、检查token，查询登录用户
         * 2、根据noteId，查询note详情
         * 3、比较note的userId是否是登录用户，如果不是，返回错误
         * 4、如果note是当前用户创建的，返回note
         */

        if (token == null) {
            throw new Exception("10010");
        }

        UserInfo userInfo = iUserInfoService.getUserByUserToken(token);
        if (userInfo == null) {
            throw new Exception("10003");
        }

        //查询note简介
        NoteInfo noteInfo = iNoteService.getNoteDetailByNoteId(noteId);
        if (noteInfo == null) {
            throw new Exception("10004");
        }

        if (!noteInfo.getUserId().equals(userInfo.getUserId())) {
            throw new Exception("10011");
        }

        //用AES秘钥加密笔记内容的AES秘钥

//        String data="hellow girls";
        String data=noteInfo.getUserEncodeKey();
//        String key="6twpxnxaunl7wsnv";
        String key=strAESKey;
        String iv=strAESKey;
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");//"算法/模式/补码方式"
        int blockSize = cipher.getBlockSize();

        byte[] dataBytes = data.getBytes("UTF-8");//如果有中文，记得加密前的字符集
        int plaintextLength = dataBytes.length;
        if (plaintextLength % blockSize != 0) {
            plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
        }
        byte[] plaintext = new byte[plaintextLength];
        System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
        SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
        byte[] encrypted = cipher.doFinal(plaintext);
        String outCode= Base64.encode(encrypted);

        noteInfo.setUserEncodeKey(outCode);
        Map out = new HashMap();
        out.put("note", noteInfo);
        return out;
    }

    /**
     * 修改笔记
     *
     * @param in
     * @return
     * @throws Exception
     */
    @Override
    public Map updateNote(Map in) throws Exception {
        String token = in.get("token").toString();
        String noteId = in.get("noteId").toString();
        String title = in.get("title").toString();
        String detail = in.get("detail").toString();
        String encryptKey = in.get("encryptKey").toString();
        String keyToken = in.get("keyToken").toString();
        /**
         * 根据keyToken读取私钥
         */
        String privateKey = iSecurityService.getRSAKey(keyToken);
        String strAESKey = GogoTools.decryptRSAByPrivateKey(encryptKey, privateKey);

        if (token == null) {
            throw new Exception("10010");
        }
//
        UserInfo userInfo = iUserInfoService.getUserByUserToken(token);
        if (userInfo == null) {
            throw new Exception("10003");
        }

        /**
         * 读取note，判断是否为当前user创建
         */
        NoteInfo noteInfo = iNoteService.getNoteTinyByNoteId(noteId);
        if (noteInfo == null) {
            throw new Exception("10004");
        }
        if (!noteInfo.getUserId().equals(userInfo.getUserId())) {
            //不是自己创建的note，不能修改
            throw new Exception("10011");
        }

        /**
         * 修改note, 只能修改title,detail
         */
        NoteInfo updateNote = new NoteInfo();
        updateNote.setDetail(detail);
        updateNote.setTitle(title);
        updateNote.setNoteId(noteId);
        updateNote.setUserEncodeKey(strAESKey);
        iNoteService.updateNote(updateNote);

        Map out = new HashMap();
        out.put("note", updateNote);
        return out;
    }
}
