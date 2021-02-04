package com.gogoyang.lifecapsule.business.note.creativeNote;

import com.gogoyang.lifecapsule.meta.category.entity.NoteCategory;
import com.gogoyang.lifecapsule.meta.category.service.ICategoryService;
import com.gogoyang.lifecapsule.meta.note.entity.CreativeNote;
import com.gogoyang.lifecapsule.meta.note.entity.NoteInfo;
import com.gogoyang.lifecapsule.meta.note.service.ICreativeNoteService;
import com.gogoyang.lifecapsule.meta.note.service.INoteService;
import com.gogoyang.lifecapsule.meta.security.service.ISecurityService;
import com.gogoyang.lifecapsule.meta.user.entity.UserInfo;
import com.gogoyang.lifecapsule.meta.user.service.IUserInfoService;
import com.gogoyang.lifecapsule.utility.GogoTools;
import com.gogoyang.lifecapsule.utility.constant.NoteType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class CreativeNoteBService implements ICreativeNoteBService{
    private final ISecurityService iSecurityService;
    private final IUserInfoService iUserInfoService;
    private final ICategoryService iCategoryService;
    private final INoteService iNoteService;
    private final ICreativeNoteService iCreativeNoteService;

    public CreativeNoteBService(ISecurityService iSecurityService,
                                IUserInfoService iUserInfoService,
                                ICategoryService iCategoryService,
                                INoteService iNoteService,
                                ICreativeNoteService iCreativeNoteService) {
        this.iSecurityService = iSecurityService;
        this.iUserInfoService = iUserInfoService;
        this.iCategoryService = iCategoryService;
        this.iNoteService = iNoteService;
        this.iCreativeNoteService = iCreativeNoteService;
    }

    @Override
    public void saveCreativeNote(Map in) throws Exception {
        String token = (String) in.get("token");
        String detail1 = (String) in.get("detail1");
        String detail2 = (String) in.get("detail2");
        String detail3 = (String) in.get("detail3");
        String detail4 = (String) in.get("detail4");
        String noteId=(String)in.get("noteId");
        String categoryId = (String) in.get("categoryId");
        String encryptKey = in.get("encryptKey").toString();
        String keyToken = in.get("keyToken").toString();

        /**
         * 根据keyToken读取私钥
         * 用私钥解开用户用公钥加密的用户AES私钥
         */
        String privateKey = iSecurityService.getRSAKey(keyToken);
        String strAESKey = GogoTools.decryptRSAByPrivateKey(encryptKey, privateKey);
        iSecurityService.deleteRSAKey(keyToken);

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
         * 保存笔记
         * 4个方拖延子笔记，仍然需要一个note父笔记
         */
        NoteInfo noteInfo=null;
        ArrayList creativeNotes=new ArrayList();
        if(noteId!=null){
            /**
             * 读取原笔记
             */
            noteInfo=iNoteService.getNoteTinyByNoteId(noteId);
            if(noteInfo==null){
                throw new Exception("10004");
            }
            Map qIn=new HashMap();
            qIn.put("noteId", noteId);
            creativeNotes =iCreativeNoteService.listCreativeNote(qIn);

            CreativeNote creativeNote1=new CreativeNote();
            creativeNote1.setCreativeType(NoteType.CREATIVE1.toString());
            creativeNote1.setNoteId(noteInfo.getNoteId());
            iCreativeNoteService.createCreativeNote(creativeNote1);
        }else {
            /**
             * 新增
             */
            noteInfo = new NoteInfo();
            noteInfo.setNoteId(GogoTools.UUID().toString());
            noteInfo.setUserId(userInfo.getUserId());
            noteInfo.setCreatedTime(new Date());
            /**
             * 如果没有分类id，就使用该用户的默认分类id
             */
            if (categoryId == null) {
                NoteCategory category = iCategoryService.getCategoryByCategoryName("Default", userInfo.getUserId());
                categoryId = category.getCategoryId();
            }
            noteInfo.setCategoryId(categoryId);
            //保存用户的AES私钥
            noteInfo.setUserEncodeKey(strAESKey);
            iNoteService.createNote(noteInfo);

            creativeNotes=new ArrayList<CreativeNote>();
        }

        /**
         * 昨天高兴的事
         */


        creativeNote.setCreativeNoteId(GogoTools.UUID().toString());
        creativeNote.setCreativeType();



        Map out = new HashMap();
        out.put("note", noteInfo);
        return out;
    }
}
