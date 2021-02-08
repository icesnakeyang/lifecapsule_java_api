package com.gogoyang.lifecapsule.controller.notes.creativeNote;

import com.gogoyang.lifecapsule.business.note.creativeNote.ICreativeNoteBService;
import com.gogoyang.lifecapsule.controller.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/note/creative")
public class CreativeNoteController {
    private final ICreativeNoteBService iCreativeNoteBService;

    public CreativeNoteController(ICreativeNoteBService iCreativeNoteBService) {
        this.iCreativeNoteBService = iCreativeNoteBService;
    }

    /**
     * 创建一个防拖延笔记
     * 用户需post提交title，detail
     * 用户token通过header提交
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/saveCreativeNote")
    public Response saveCreativeNote(@RequestBody CreativeNoteRequest request,
                                     HttpServletRequest httpServletRequest) {
        Response response = new Response();
        try {
            String token = httpServletRequest.getHeader("token");
            Map in = new HashMap();
            in.put("detail1", request.getDetail1());
            in.put("detail2", request.getDetail2());
            in.put("detail3", request.getDetail3());
            in.put("detail4", request.getDetail4());
            in.put("token", token);
            in.put("noteId", request.getNoteId());
            in.put("categoryId", request.getCategoryId());
            in.put("encryptKey", request.getEncryptKey());
            in.put("keyToken", request.getKeyToken());
            in.put("tasks", request.getTasks());
            in.put("noteTitle", request.getNoteTitle());

            Map out=iCreativeNoteBService.saveCreativeNote(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("saveCreativeNote error:" + ex.getMessage());
            }
        }
        return response;

    }

    /**
     * 读取防拖延笔记列表
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/getCreativeNote")
    public Response getCreativeNote(@RequestBody CreativeNoteRequest request,
                                     HttpServletRequest httpServletRequest) {
        Response response = new Response();
        try {
            String token = httpServletRequest.getHeader("token");
            Map in = new HashMap();
            in.put("token", token);
            in.put("noteId", request.getNoteId());
            in.put("encryptKey", request.getEncryptKey());
            in.put("keyToken", request.getKeyToken());

            Map out=iCreativeNoteBService.getCreativeNote(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            } catch (Exception ex2) {
                response.setCode(10001);
                log.error("getCreativeNote error:" + ex.getMessage());
            }
        }
        return response;
    }
}
