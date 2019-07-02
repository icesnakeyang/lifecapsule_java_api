package com.gogoyang.lifecapsule.controller.notes;

import com.gogoyang.lifecapsule.business.note.INoteBusinessService;
import com.gogoyang.lifecapsule.controller.vo.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/note")
public class NoteController {
    private final INoteBusinessService iNoteBusinessService;

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public NoteController(INoteBusinessService iNoteBusinessService) {
        this.iNoteBusinessService = iNoteBusinessService;
    }

    /**
     * 创建一个笔记
     * 用户需post提交title，detail
     * 用户token通过header提交
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/createNote")
    public Response createNote(@RequestBody NoteRequest request,
                               HttpServletRequest httpServletRequest) {
        Response response = new Response();
        try {
            String token = httpServletRequest.getHeader("token");
            Map in = new HashMap();
            in.put("title", request.getTitle());
            in.put("detail", request.getDetail());
            in.put("token", token);
            in.put("categoryId", request.getCategoryId());
            in.put("encryptKey", request.getEncryptKey());


            Map out = iNoteBusinessService.createNote(in);
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
     * 根据用户token查询用户的笔记列表
     * Post参数里要设定分页
     * token放在header里
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/listNoteByUserToken")
    public Response listNoteByUserToken(@RequestBody NoteRequest request,
                                        HttpServletRequest httpServletRequest) {
        Response response = new Response();
        try {
            String token = httpServletRequest.getHeader("token");
            Map in = new HashMap();
            in.put("pageSize", request.getPageSize());
            in.put("pageIndex", request.getPageIndex());
            in.put("token", token);

            Map out = iNoteBusinessService.listNoteByUserToken(in);
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
    @PostMapping("/listNoteByCategory")
    public Response listNoteByCategory(@RequestBody NoteRequest request,
                                       HttpServletRequest httpServletRequest) {
        Response response = new Response();
        try {
            String token = httpServletRequest.getHeader("token");
            Map in = new HashMap();
            in.put("token", token);
            in.put("categoryId", request.getCategoryId());
            in.put("pageIndex", request.getPageIndex());
            in.put("pageSize", request.getPageSize());

            Map out = iNoteBusinessService.listNoteByCategory(in);
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
    @PostMapping("/getNoteDetailByNoteId")
    public Response getNoteDetailByNoteId(@RequestBody NoteRequest request,
                                          HttpServletRequest httpServletRequest) {
        Response response = new Response();
        try {
            String token = httpServletRequest.getHeader("token");
            Map in = new HashMap();
            in.put("noteId", request.getNoteId());
            in.put("token", token);
            in.put("encryptKey", request.getEncryptKey());
            in.put("keyToken", request.getKeyToken());
            in.put("detail", request.getDetail());

            Map out = iNoteBusinessService.getNoteDetailByNoteId(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                logger.error(ex.getMessage());
                response.setCode(10001);
            }
        }
        return response;
    }

    /**
     * 修改笔记
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/updateNote")
    public Response updateNote(@RequestBody NoteRequest request,
                               HttpServletRequest httpServletRequest) {
        Response response = new Response();
        try {
            String token = httpServletRequest.getHeader("token");
            Map in = new HashMap();
            in.put("noteId", request.getNoteId());
            in.put("title", request.getTitle());
            in.put("detail", request.getDetail());
            in.put("encryptKey", request.getEncryptKey());
            in.put("keyToken", request.getKeyToken());
            in.put("token", token);
            Map out = iNoteBusinessService.updateNote(in);
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
