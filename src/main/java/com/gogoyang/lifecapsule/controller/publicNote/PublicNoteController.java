package com.gogoyang.lifecapsule.controller.publicNote;

import com.gogoyang.lifecapsule.business.publicNote.IPublicNoteBusinessService;
import com.gogoyang.lifecapsule.controller.vo.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/publicNote")
public class PublicNoteController {
    private final IPublicNoteBusinessService iPublicNoteBusinessService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public PublicNoteController(IPublicNoteBusinessService iPublicNoteBusinessService) {
        this.iPublicNoteBusinessService = iPublicNoteBusinessService;
    }

    @ResponseBody
    @PostMapping("/publishNote")
    public Response publishNote(@RequestBody PublicNoteRequest request,
                                HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("title", request.getTitle());
            in.put("content", request.getContent());
            in.put("noteId", request.getNoteId());
            iPublicNoteBusinessService.publishNote(in);
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
    @PostMapping("/listPublicNote")
    public Response listPublicNote(@RequestBody PublicNoteRequest request,
                                   HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            Map out = iPublicNoteBusinessService.listPublicNote(in);
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
    @GetMapping("/getPublicNote/{noteId}")
    public Response getPublicNote(@PathVariable("noteId") String noteId) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            in.put("noteId", noteId);
            Map out = iPublicNoteBusinessService.getPublicNote(in);
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
