package com.gogoyang.lifecapsule.controller.category;

import com.gogoyang.lifecapsule.business.category.ICategoryBusinessService;
import com.gogoyang.lifecapsule.controller.vo.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final ICategoryBusinessService iCategoryBusinessService;

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public CategoryController(ICategoryBusinessService iCategoryBusinessService) {
        this.iCategoryBusinessService = iCategoryBusinessService;
    }

    /**
     * 用户创建笔记分类
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/createCategory")
    public Response createCategory(@RequestBody CategoryRequest request,
                                   HttpServletRequest httpServletRequest) {
        Response response = new Response();
        try {
            String token = httpServletRequest.getHeader("token");
            Map in = new HashMap();
            in.put("categoryName", request.getCategoryName());
            in.put("token", token);

            Map out = iCategoryBusinessService.createCategory(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                logger.error(ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 读取用户的分类列表
     *
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/listCategory")
    public Response listCategory(HttpServletRequest httpServletRequest) {
        Response response = new Response();
        try {
            String token = httpServletRequest.getHeader("token");
            Map in = new HashMap();
            in.put("token", token);

            Map out = iCategoryBusinessService.listCategory(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.getInteger(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                logger.error(ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 用户修改笔记分类名称
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/updateCategory")
    public Response updateCategory(@RequestBody CategoryRequest request,
                                   HttpServletRequest httpServletRequest) {
        Response response = new Response();
        try {
            String token = httpServletRequest.getHeader("token");
            Map in = new HashMap();
            in.put("categoryId", request.getCategoryId());
            in.put("categoryName", request.getCategoryName());
            in.put("token", token);

            Map out = iCategoryBusinessService.updateCategory(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                logger.error(ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 删除一个笔记分类
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/deleteCategory")
    public Response deleteCategory(@RequestBody CategoryRequest request,
                                   HttpServletRequest httpServletRequest) {
        Response response = new Response();
        try {
            String token = httpServletRequest.getHeader("token");
            Map in = new HashMap();
            in.put("token", token);
            in.put("categoryId", request.getCategoryId());

            iCategoryBusinessService.deleteCategory(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                logger.error(ex.getMessage());
            }
        }
        return response;
    }
}
