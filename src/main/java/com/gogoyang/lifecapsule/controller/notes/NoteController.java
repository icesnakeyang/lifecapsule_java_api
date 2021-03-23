package com.gogoyang.lifecapsule.controller.notes;

import com.gogoyang.lifecapsule.business.note.INoteBusinessService;
import com.gogoyang.lifecapsule.business.userData.IUserDataBusinessService;
import com.gogoyang.lifecapsule.controller.vo.Response;
import com.gogoyang.lifecapsule.utility.GogoTools;
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
            in.put("keyToken", request.getKeyToken());

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

    @ResponseBody
    @PostMapping("/listNote")
    public Response listNote(@RequestBody NoteRequest request,
                             HttpServletRequest httpServletRequest) {
        Response response = new Response();
        try {
            String token = httpServletRequest.getHeader("token");
            Map in = new HashMap();
            in.put("token", token);
            in.put("categoryId", request.getCategoryId());
            in.put("pageIndex", request.getPageIndex());
            in.put("pageSize", request.getPageSize());
            in.put("idc", request.getIdc());

            Map out = iNoteBusinessService.listNote(in);
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

            String strAesKey = GogoTools.generateAESKey();

            String token = httpServletRequest.getHeader("token");
            Map in = new HashMap();
            in.put("noteId", request.getNoteId());
            in.put("token", token);
            in.put("encryptKey", request.getEncryptKey());
            in.put("keyToken", request.getKeyToken());
            in.put("detail", request.getDetail());

            in.put("encryptKey", request.getEncryptKey());
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
            iNoteBusinessService.updateNote(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                logger.error("updateNote error:" + ex.getMessage());
            }
        }
        return response;
    }

    @ResponseBody
    @PostMapping("/deleteNote")
    public Response deleteNote(@RequestBody NoteRequest request,
                               HttpServletRequest httpServletRequest) {
        Response response = new Response();
        try {
            String token = httpServletRequest.getHeader("token");
            Map in = new HashMap();
            in.put("token", token);
            in.put("noteId", request.getNoteId());

            iNoteBusinessService.deleteNoteByNoteId(in);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
            }
        }
        return response;
    }

    /**
     * 用户查询一个笔记的简要信息
     * 不包括笔记内容，所以不需要加密
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/getNoteTiny")
    public Response getNoteTiny(@RequestBody NoteRequest request,
                                HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("noteId", request.getNoteId());

            Map out = iNoteBusinessService.getNoteTiny(in);
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
     * 把一个note移动到另一个category分类里
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/moveNoteCategory")
    public Response moveNoteCategory(@RequestBody NoteRequest request,
                                     HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("noteId", request.getNoteId());
            in.put("categoryId", request.getCategoryId());

            iNoteBusinessService.moveNoteCategory(in);
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
