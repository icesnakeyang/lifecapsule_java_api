package com.gogoyang.lifecapsule.meta.category.service;

import com.gogoyang.lifecapsule.meta.category.entity.NoteCategory;

import java.util.List;

public interface ICategoryService {
    /**
     * 用户创建一个新的笔记分类
     *
     * @param category
     * @throws Exception
     */
    void createCategory(NoteCategory category) throws Exception;

    /**
     * 根据用户和分类名称查询分类
     *
     * @param categoryName
     * @return
     * @throws Exception
     */
    NoteCategory getCategoryByCategoryName(String categoryName, String userId) throws Exception;

    /**
     * 读取用户的所有笔记分类
     * @param userId
     * @return
     * @throws Exception
     */
    List<NoteCategory> listCategory(String userId) throws Exception;

    /**
     * 通过分类id查询分类
     * @param categoryId
     * @return
     * @throws Exception
     */
    NoteCategory getCategoryByCategoryId(String categoryId) throws Exception;

    /**
     * 修改分类
     * @param noteCategory
     * @throws Exception
     */
    void updateCategory(NoteCategory noteCategory) throws Exception;

    /**
     * 根据分类id删除一条分类
     * @param categoryId
     */
    void deleteCategory(String categoryId);
}
