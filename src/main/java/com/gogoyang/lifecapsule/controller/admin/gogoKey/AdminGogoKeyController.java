package com.gogoyang.lifecapsule.controller.admin.gogoKey;

import com.gogoyang.lifecapsule.business.admin.gogoKey.IAdminGogoKeyBusinessService;
import com.gogoyang.lifecapsule.controller.vo.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/gogokey")
public class AdminGogoKeyController {
    private final IAdminGogoKeyBusinessService iAdminGogoKeyBusinessService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public AdminGogoKeyController(IAdminGogoKeyBusinessService iAdminGogoKeyBusinessService) {
        this.iAdminGogoKeyBusinessService = iAdminGogoKeyBusinessService;
    }

    /**
     * 创建一个触发器模板
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("createGogoPublicKey")
    public Response createGogoPublicKey(@RequestBody AdminGogoKeyRequest request,
                                        HttpServletRequest httpServletRequest) {
        Response response = new Response();
        try {
            String token = httpServletRequest.getHeader("token");
            Map in = new HashMap();
            in.put("token", token);
            in.put("title", request.getTitle());
            in.put("params", request.getParams());
            in.put("description", request.getDescription());
            in.put("url", request.getUrl());

            iAdminGogoKeyBusinessService.createGogoPublicKey(in);
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

    @ResponseBody
    @PostMapping("/listGogoPublicKey")
    public Response listGogoPublicKey() {
        Response response = new Response();
        try {
            Map out = iAdminGogoKeyBusinessService.listGogoPublicKey();
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

    @ResponseBody
    @PostMapping("getGogoPublicKey")
    public Response getGogoPublicKey(@RequestBody AdminGogoKeyRequest request,
                                     HttpServletRequest httpServletRequest) {
        Response response = new Response();
        try {
            String token = httpServletRequest.getHeader("token");
            Map in = new HashMap();
            in.put("token", token);
            in.put("gogoPublicKeyId", request.getGogoPublicKeyId());

            Map out = iAdminGogoKeyBusinessService.getGogoPublicKey(in);
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

    @ResponseBody
    @PostMapping("/updateGogoPublicKey")
    public Response updateGogoPublicKey(@RequestBody AdminGogoKeyRequest request,
                                        HttpServletRequest httpServletRequest) {
        Response response = new Response();
        try {
            String token = httpServletRequest.getHeader("token");
            Map in = new HashMap();
            in.put("gogoPublicKeyId", request.getGogoPublicKeyId());
            in.put("token", token);
            in.put("type", request.getType());
            in.put("title", request.getTitle());
            in.put("params", request.getParams());
            iAdminGogoKeyBusinessService.updateGogoPublicKey(in);
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

    @ResponseBody
    @PostMapping("/deleteGogoPublicKey")
    public Response deleteGogoPublicKey(@RequestBody AdminGogoKeyRequest request,
                                        HttpServletRequest httpServletRequest) {
        Response response = new Response();
        try {
            String token = httpServletRequest.getHeader("token");
            Map in = new HashMap();
            in.put("token", token);
            in.put("gogoPublicKeyId", request.getGogoPublicKeyId());
            iAdminGogoKeyBusinessService.deleteGogoPublicKey(in);
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
