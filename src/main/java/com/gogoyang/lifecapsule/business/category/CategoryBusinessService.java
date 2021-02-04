package com.gogoyang.lifecapsule.business.category;

import com.gogoyang.lifecapsule.business.common.ICommonService;
import com.gogoyang.lifecapsule.meta.category.entity.NoteCategory;
import com.gogoyang.lifecapsule.meta.category.service.ICategoryService;
import com.gogoyang.lifecapsule.meta.note.service.INoteService;
import com.gogoyang.lifecapsule.meta.user.entity.UserInfo;
import com.gogoyang.lifecapsule.meta.user.service.IUserInfoService;
import com.gogoyang.lifecapsule.utility.GogoTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryBusinessService implements ICategoryBusinessService {
    private final ICategoryService iCategoryService;
    private final IUserInfoService iUserInfoService;
    private final INoteService iNoteService;
    private final ICommonService iCommonService;

    @Autowired
    public CategoryBusinessService(ICategoryService iCategoryService,
                                   IUserInfoService iUserInfoService,
                                   INoteService iNoteService,
                                   ICommonService iCommonService) {
        this.iCategoryService = iCategoryService;
        this.iUserInfoService = iUserInfoService;
        this.iNoteService = iNoteService;
        this.iCommonService = iCommonService;
    }

    /**
     * 用户创建一个分类
     *
     * @param in
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map createCategory(Map in) throws Exception {
        String categoryName = in.get("categoryName").toString();
        String token = in.get("token").toString();
        String noteType=(String) in.get("noteType");

        if (categoryName.equals("")) {
            throw new Exception("10013");
        }

        /**
         * 检查用户登录
         */
        UserInfo userInfo = iUserInfoService.getUserByUserToken(token);
        if (userInfo == null) {
            throw new Exception("10003");
        }

        /**
         * 验证category是否已经创建了
         */
        NoteCategory category = iCategoryService.getCategoryByCategoryName(categoryName, userInfo.getUserId());
        if (category != null) {
            throw new Exception("10012");
        }

        /**
         * 创建分类
         */
        category = new NoteCategory();
        category.setCategoryId(GogoTools.UUID().toString());
        category.setUserId(userInfo.getUserId());
        category.setCategoryName(categoryName);
        category.setNoteType(noteType);
        iCategoryService.createCategory(category);

        Map out = new HashMap();
        out.put("category", category);
        return out;
    }

    /**
     * 读取用户的分类列表
     *
     * @param in
     * @return
     * @throws Exception
     */
    @Override
    public Map listCategory(Map in) throws Exception {
        String token = in.get("token").toString();

        /**
         * 检查用户登录
         */
        UserInfo userInfo = iUserInfoService.getUserByUserToken(token);
        if (userInfo == null) {
            throw new Exception("10003");
        }

        List<NoteCategory> categoryList = iCategoryService.listCategory(userInfo.getUserId());

        Map out = new HashMap();
        out.put("categoryList", categoryList);
        return out;
    }

    /**
     * 用户修改笔记分类名称
     *
     * @param in
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map updateCategory(Map in) throws Exception {
        String token = in.get("token").toString();
        String categoryId = in.get("categoryId").toString();
        String categoryName = in.get("categoryName").toString();

        /**
         * 验证用户
         */
        UserInfo userInfo = iUserInfoService.getUserByUserToken(token);
        if (userInfo == null) {
            throw new Exception("10003");
        }

        NoteCategory noteCategory = iCategoryService.getCategoryByCategoryId(categoryId);
        if (noteCategory == null) {
            throw new Exception("10015");
        }

        /**
         * 检查分类是否为当前用户
         */
        if (!noteCategory.getUserId().equals(userInfo.getUserId())) {
            throw new Exception("10014");
        }

        Map out = new HashMap();
        /**
         * 修改分类
         */
        if (!noteCategory.getCategoryName().equals(categoryName)) {
            noteCategory.setCategoryName(categoryName);
            iCategoryService.updateCategory(noteCategory);
        }
        out.put("category", noteCategory);
        return out;
    }

    /**
     * 删除一个笔记分类
     *
     * @param in
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteCategory(Map in) throws Exception {
        String token = in.get("token").toString();
        String categoryId = in.get("categoryId").toString();

        /**
         * 检查用户
         */
        UserInfo userInfo = iUserInfoService.getUserByUserToken(token);
        if (userInfo == null) {
            throw new Exception("10003");
        }

        NoteCategory noteCategory = iCategoryService.getCategoryByCategoryId(categoryId);
        if (noteCategory == null) {
            throw new Exception("10015");
        }

        /**
         * 检查分类是否为该用户创建
         */
        if (!noteCategory.getUserId().equals(userInfo.getUserId())) {
            throw new Exception("10014");
        }

        /**
         * 检查分类下面是否有笔记
         */
        Integer totalNotes = iNoteService.countNoteByCategoryId(categoryId);
        if (totalNotes > 0) {
            throw new Exception("10016");
        }

        /**
         * 删除分类
         */
        iCategoryService.deleteCategory(categoryId);
    }

    /**
     * 获取分类详情
     *
     * @param in
     * @return
     * @throws Exception
     */
    @Override
    public Map getCategory(Map in) throws Exception {
        String token = in.get("token").toString();
        String categoryId = (String) in.get("categoryId");
        String categoryName = (String) in.get("categoryName");

        /**
         * 检查用户
         */
        UserInfo userInfo = iCommonService.getUserByToken(token);
        NoteCategory noteCategory = null;
        if (categoryId != null && categoryId != "") {
            noteCategory = iCategoryService.getCategoryByCategoryId(categoryId);
        } else {
            if (categoryName != null && categoryName != "") {
                noteCategory = iCategoryService.getCategoryByCategoryName(categoryName, userInfo.getUserId());
            } else {
                throw new Exception("neither categoryName nor categoryId are all miss");
            }
        }
        if (noteCategory == null) {
            throw new Exception("10015");
        }
        Map out = new HashMap();
        out.put("category", noteCategory);
        return out;
    }
}
