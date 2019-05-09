package com.gogoyang.lifecapsule.controller.recipient;

import com.gogoyang.lifecapsule.business.recipient.IRecipientBusinessService;
import com.gogoyang.lifecapsule.controller.vo.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/recipient")
public class RecipientController {
    private final IRecipientBusinessService iRecipientBusinessService;

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public RecipientController(IRecipientBusinessService iRecipientBusinessService) {
        this.iRecipientBusinessService = iRecipientBusinessService;
    }

    /**
     * 创建一条触发条件和接收人
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/createRecipientPerson")
    public Response createRecipientPerson(@RequestBody RecipientRequest request,
                                          HttpServletRequest httpServletRequest) {
        Response response = new Response();
        try {
            String token = httpServletRequest.getHeader("token");
            Map in = new HashMap();
            in.put("noteId", request.getNoteId());
            in.put("recipientId", request.getRecipientId());
            in.put("recipientName", request.getRecipientName());
            in.put("personName", request.getPersonName());
            in.put("phone", request.getPhone());
            in.put("email", request.getEmail());
            in.put("address", request.getAddress());
            in.put("token", token);
            in.put("remark", request.getRemark());

            Map out = iRecipientBusinessService.createRecipientPerson(in);
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
    @PostMapping("/getRecipientByRecipientId")

    public Response getRecipientByRecipientId(@RequestBody RecipientRequest request,
                                              HttpServletRequest httpServletRequest) {
        Response response = new Response();
        try {
            String token = httpServletRequest.getHeader("token");
            Map in = new HashMap();
            in.put("token", token);
            in.put("recipientId", request.getRecipientId());

            Map out = iRecipientBusinessService.getRecipientByRecipientId(in);
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
     * 根据笔记id，读取触发设置
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/listRecipientByNoteId")
    public Response listRecipientByNoteId(@RequestBody RecipientRequest request,
                                          HttpServletRequest httpServletRequest) {
        Response response = new Response();
        try {
            String token = httpServletRequest.getHeader("token");
            Map in = new HashMap();
            in.put("noteId", request.getNoteId());
            in.put("token", token);

            Map out = iRecipientBusinessService.listRecipientByNoteId(in);
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
    @PostMapping("/listRecipientPersonByRecipientId")
    public Response listRecipientPersonByRecipientId(@RequestBody RecipientRequest request,
                                                     HttpServletRequest httpServletRequest) {
        Response response = new Response();
        try {
            String token = httpServletRequest.getHeader("token");
            Map in = new HashMap();
            in.put("token", token);
            in.put("recipientId", request.getRecipientId());

            Map out = iRecipientBusinessService.listRecipientPersonByRecipientId(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                logger.error(ex2.getMessage());
            }
        }
        return response;
    }

    @ResponseBody
    @PostMapping("/getPersonByPersonId")
    public Response getPersonByPersonId(@RequestBody RecipientRequest request,
                                        HttpServletRequest httpServletRequest) {
        Response response = new Response();
        try {
            String token = httpServletRequest.getHeader("token");
            Map in = new HashMap();
            in.put("token", token);
            in.put("personId", request.getPersonId());

            Map out = iRecipientBusinessService.getPersonByPersonId(in);
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
