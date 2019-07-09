package com.gogoyang.lifecapsule.controller.gogoKey;

import com.gogoyang.lifecapsule.business.gogoKey.IGogoKeyBusinessService;
import com.gogoyang.lifecapsule.controller.vo.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/gogoKey")
public class gogoKeyController {
    private final IGogoKeyBusinessService iGogoKeyBusinessService;

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public gogoKeyController(IGogoKeyBusinessService iGogoKeyBusinessService) {
        this.iGogoKeyBusinessService = iGogoKeyBusinessService;
    }

    @ResponseBody
    @PostMapping("/createGogoKey")
    public Response createGogoKey(@RequestBody GogoKeyRequest request,
                                  HttpServletRequest httpServletRequest) {
        Response response = new Response();
        try {
            Map in = new HashMap();
            in.put("name", request.getName());
            in.put("type", request.getType());
            in.put("triggerTime", request.getTriggerTime());
            in.put("url", request.getUrl());
            in.put("params", request.getParams());
            in.put("description", request.getDescription());

            Map out = iGogoKeyBusinessService.createGogoKey(in);
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
}
