package com.gogoyang.lifecapsule.business.category;

import java.util.Map;

public interface ICategoryBusinessService {
    /**
     * 用户创建一个分类
     * @param in
     * @return
     * @throws Exception
     */
    Map createCategory(Map in) throws Exception;

    /**
     * 读取用户的分类列表
     * @param in
     * @return
     * @throws Exception
     */
    Map listCategory(Map in) throws Exception;

    /**
     * 用户修改笔记分类名称
     * @param in
     * @return
     * @throws Exception
     */
    Map updateCategory(Map in) throws Exception;

    /**
     * 删除一个笔记分类
     * @param in
     * @return
     * @throws Exception
     */
    void deleteCategory(Map in) throws Exception;
}
