package com.gogoyang.lifecapsule.meta.category.dao.mapper;

import com.gogoyang.lifecapsule.meta.category.entity.NoteCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CategoryMapper {

    /**
     * 创建一个笔记分类
     * @param category
     * @return
     */
    void createCategory(NoteCategory category);

    /**
     * 根据用户和分类名称查询分类
     * @param qIn
     * @return
     */
    NoteCategory getCategoryByCategoryName(Map qIn);

    /**
     * 读取用户的所有笔记分类
     * @param userId
     * @return
     */
    List<NoteCategory> listCategory(String userId);

    /**
     * 通过分类id查询分类
     * @param categoryId
     * @return
     */
    NoteCategory getCategoryByCategoryId(String categoryId);

    /**
     * 修改分类
     * @param noteCategory
     */
    void updateCategory(NoteCategory noteCategory);

    /**
     * 根据分类id删除一条分类
     * @param categoryId
     */
    void deleteCategory(String categoryId);
}
