package com.gogoyang.lifecapsule.controller.trigger;

import com.gogoyang.lifecapsule.business.trigger.ITriggerBusinessService;
import com.gogoyang.lifecapsule.controller.vo.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/trigger")
public class TriggerController {
    private final ITriggerBusinessService iTriggerBusinessService;

    Logger logger = LoggerFactory.getLogger(getClass());

    public TriggerController(ITriggerBusinessService iTriggerBusinessService) {
        this.iTriggerBusinessService = iTriggerBusinessService;
    }

    /**
     * 创建一个接收人
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/createRecipient")
    public Response createRecipient(@RequestBody TriggerRequest request,
                                    HttpServletRequest httpServletRequest) {
        Response response = new Response();
        try {
            String token = httpServletRequest.getHeader("token");
            Map in = new HashMap();
            in.put("token", token);
            in.put("noteId", request.getNoteId());
            in.put("triggerId", request.getTriggerId());
            in.put("name", request.getRecipientName());
            in.put("phoen", request.getPhone());
            in.put("email", request.getEmail());
            in.put("address", request.getAddress());
            in.put("remark", request.getRemark());

            Map out = iTriggerBusinessService.createRecipient(in);
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
