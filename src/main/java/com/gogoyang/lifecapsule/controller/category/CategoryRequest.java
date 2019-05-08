package com.gogoyang.lifecapsule.controller.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {
    /**
     * 分类id
     */
    private String categoryId;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 用户id
     */
    private String userId;
    ////////////////////////////////////////////////////////////////////////////////////

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
