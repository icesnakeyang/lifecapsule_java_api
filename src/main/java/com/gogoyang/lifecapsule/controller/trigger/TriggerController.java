package com.gogoyang.lifecapsule.controller.trigger;

import com.gogoyang.lifecapsule.business.trigger.ITriggerBusinessService;
import com.gogoyang.lifecapsule.controller.vo.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
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
     *
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
            in.put("phone", request.getPhone());
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

    /**
     * 修改接收人信息
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/updateRecipient")
    public Response updateRecipient(@RequestBody TriggerRequest request,
                                    HttpServletRequest httpServletRequest) {
        Response response = new Response();
        try {
            String token = httpServletRequest.getHeader("token");
            Map in = new HashMap();
            in.put("token", token);
            in.put("recipientId", request.getRecipientId());
            in.put("name", request.getRecipientName());
            in.put("phone", request.getPhone());
            in.put("email", request.getEmail());
            in.put("address", request.getAddress());
            in.put("remark", request.getRemark());

            Map out = iTriggerBusinessService.updateRecipient(in);
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
     * 删除一个接收人
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/deleteRecipient")
    public Response deleteRecipient(@RequestBody TriggerRequest request,
                                    HttpServletRequest httpServletRequest) {
        Response response = new Response();
        try {
            String token = httpServletRequest.getHeader("token");
            Map in = new HashMap();
            in.put("token", token);
            in.put("recipientId", request.getRecipientId());
            iTriggerBusinessService.deleteRecipient(in);
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
     * 根据触发器id，查询所有的接收人
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/listRecipientByTriggerId")
    public Response listRecipientByTriggerId(@RequestBody TriggerRequest request,
                                             HttpServletRequest httpServletRequest) {
        Response response = new Response();
        try {
            String token = httpServletRequest.getHeader("token");
            Map in = new HashMap();
            in.put("token", token);
            in.put("triggerId", request.getTriggerId());

            Map out = iTriggerBusinessService.listRecipientByTriggerId(in);
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
    public Response getRecipientByRecipientId(@RequestBody TriggerRequest request,
                                              HttpServletRequest httpServletRequest) {
        Response response = new Response();
        try {
            String token = httpServletRequest.getHeader("token");
            Map in = new HashMap();
            in.put("token", token);
            in.put("recipientId", request.getRecipientId());

            Map out = iTriggerBusinessService.getRecipientByRecipientId(in);
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
    @PostMapping("/saveGogoKey")
    public Response saveGogoKey(@RequestBody TriggerRequest request,
                                HttpServletRequest httpServletRequest) {
        Response response = new Response();
        try {
            String token = httpServletRequest.getHeader("token");
            Map in = new HashMap();
            in.put("token", token);
            in.put("triggerId", request.getTriggerId());
            in.put("gogoPublicKeyId", request.getGogoPublicKeyId());
            in.put("params", request.getParams());
            in.put("gogoKeyId", request.getGogoKeyId());
            in.put("triggerName", request.getTriggerName());
            in.put("noteId", request.getNoteId());
            in.put("triggerRemark", request.getTriggerRemark());
            iTriggerBusinessService.saveGogoKey(in);
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
    @PostMapping("getGogoKeyByTriggerId")
    public Response getGogoKeyByTriggerId(@RequestBody TriggerRequest request,
                                          HttpServletRequest httpServletRequest) {
        Response response = new Response();
        try {
            String token = httpServletRequest.getHeader("token");
            Map in = new HashMap();
            in.put("token", token);
            in.put("triggerId", request.getTriggerId());
            Map out = iTriggerBusinessService.getGogoKeyByTriggerId(in);
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
     * 根据笔记id查询所有的触发器
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/listTriggerByNoteId")
    public Response listTriggerByNoteId(@RequestBody TriggerRequest request,
                                        HttpServletRequest httpServletRequest) {
        Response response = new Response();
        try {
            String token = httpServletRequest.getHeader("token");
            Map in = new HashMap();
            in.put("token", token);
            in.put("noteId", request.getNoteId());

            Map out = iTriggerBusinessService.listTriggerByNoteId(in);
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
     * 根据noteId查询trigger
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/getTriggerByNoteId")
    public Response getTriggerByNoteId(@RequestBody TriggerRequest request,
                                       HttpServletRequest httpServletRequest) {
        Response response = new Response();
        try {
            String token = httpServletRequest.getHeader("token");
            Map in = new HashMap();
            in.put("token", token);
            in.put("noteId", request.getNoteId());

            Map out = iTriggerBusinessService.getTriggerByNoteId(in);

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
    @PostMapping("/getTriggerByTriggerId")
    public Response getTriggerByTriggerId(@RequestBody TriggerRequest request,
                                          HttpServletRequest httpServletRequest) {
        Response response = new Response();
        try {
            String token = httpServletRequest.getHeader("token");
            Map in = new HashMap();
            in.put("token", token);
            in.put("triggerId", request.getTriggerId());

            Map out = iTriggerBusinessService.getTriggerByTriggerId(in);
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
