package com.gogoyang.lifecapsule.meta.category.service;

import com.gogoyang.lifecapsule.meta.category.dao.mapper.CategoryMapper;
import com.gogoyang.lifecapsule.meta.category.entity.NoteCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryService implements ICategoryService {
    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryService(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    /**
     * 用户创建一个新的笔记分类
     *
     * @param category
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createCategory(NoteCategory category) throws Exception {
        categoryMapper.createCategory(category);
    }

    /**
     * 根据用户和分类名称查询分类
     *
     * @param categoryName
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public NoteCategory getCategoryByCategoryName(String categoryName, String userId) throws Exception {
        Map qIn = new HashMap();
        qIn.put("userId", userId);
        qIn.put("categoryName", categoryName);
        NoteCategory noteCategory = categoryMapper.getCategoryByCategoryName(qIn);
        return noteCategory;
    }

    /**
     * 读取用户的所有笔记分类
     *
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public List<NoteCategory> listCategory(String userId) throws Exception {
        List<NoteCategory> categoryList = categoryMapper.listCategory(userId);
        return categoryList;
    }


    /**
     * 通过分类id查询分类
     *
     * @param categoryId
     * @return
     * @throws Exception
     */
    @Override
    public NoteCategory getCategoryByCategoryId(String categoryId) throws Exception {
        NoteCategory noteCategory = categoryMapper.getCategoryByCategoryId(categoryId);
        return noteCategory;
    }

    /**
     * 修改分类
     * @param noteCategory
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateCategory(NoteCategory noteCategory) throws Exception {
        categoryMapper.updateCategory(noteCategory);
    }

    /**
     * 根据分类id删除一条分类
     * @param categoryId
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteCategory(String categoryId) {
        categoryMapper.deleteCategory(categoryId);
    }
}
