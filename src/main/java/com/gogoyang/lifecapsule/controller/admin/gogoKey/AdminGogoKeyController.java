package com.gogoyang.lifecapsule.controller.admin.gogoKey;

import com.gogoyang.lifecapsule.business.admin.gogoKey.IAdminGogoKeyBusinessService;
import com.gogoyang.lifecapsule.controller.vo.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
            in.put("type", request.getType());
            in.put("params", request.getParams());

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
}
