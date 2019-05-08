package com.gogoyang.lifecapsule.business.note;

import com.gogoyang.lifecapsule.meta.note.entity.NoteDetail;
import com.gogoyang.lifecapsule.meta.note.entity.NoteInfo;
import com.gogoyang.lifecapsule.meta.note.service.INoteService;
import com.gogoyang.lifecapsule.meta.user.entity.UserInfo;
import com.gogoyang.lifecapsule.meta.user.service.IUserInfoService;
import com.gogoyang.lifecapsule.utility.GogoTools;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NoteBusinessService implements INoteBusinessService {
    private final INoteService iNoteService;
    private final IUserInfoService iUserInfoService;

    @Autowired
    public NoteBusinessService(INoteService iNoteService, IUserInfoService iUserInfoService) {
        this.iNoteService = iNoteService;
        this.iUserInfoService = iUserInfoService;
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
        String categoryId = in.get("categoryId").toString();

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

        NoteInfo noteInfo = new NoteInfo();
        noteInfo.setUserId(userInfo.getUserId());
        noteInfo.setNoteId(GogoTools.UUID().toString());
        noteInfo.setCreatedTime(new Date());
        noteInfo.setDetail(detail);
        noteInfo.setTitle(title);
        noteInfo.setCategoryId(categoryId);
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
        String token = (String) in.get("token");
        String title = in.get("title").toString();
        String detail = in.get("detail").toString();
        String noteId = in.get("noteId").toString();

        if (token == null) {
            throw new Exception("10010");
        }

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
        iNoteService.updateNote(updateNote);

        Map out = new HashMap();
        out.put("note", updateNote);
        return out;
    }
}
