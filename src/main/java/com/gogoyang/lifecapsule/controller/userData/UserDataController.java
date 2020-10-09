package com.gogoyang.lifecapsule.controller.userData;

import com.gogoyang.lifecapsule.business.userData.IUserDataBusinessService;
import com.gogoyang.lifecapsule.controller.vo.Response;
import com.gogoyang.lifecapsule.utility.GogoTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/userData")
public class UserDataController {
    private final IUserDataBusinessService iUserDataBusinessService;

    private Logger logger= LoggerFactory.getLogger(getClass());

    public UserDataController(IUserDataBusinessService iUserDataBusinessService) {
        this.iUserDataBusinessService = iUserDataBusinessService;
    }

    /**
     * 用户给笔记添加一个api服务
     * 用户可在自己的程序里用api来调用笔记的内容
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/convertToApi")
    public Response convertToApi(@RequestBody UserDataRequest request,
                                 HttpServletRequest httpServletRequest){
        Response response=new Response();
        Map in=new HashMap();
        try {
            String token=httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("noteId", request.getNoteId());

            Map out=iUserDataBusinessService.convertToApi(in);
            response.setData(out);
        }catch (Exception ex){
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            }catch (Exception ex2){
                response.setCode(10001);
                logger.error(ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 用户读取笔记的api服务信息
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/getNoteApi")
    public Response getNoteApi(@RequestBody UserDataRequest request,
                                 HttpServletRequest httpServletRequest){
        Response response=new Response();
        Map in=new HashMap();
        try {
            String token=httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("noteId", request.getNoteId());

            Map out=iUserDataBusinessService.getNoteApi(in);
            response.setData(out);
        }catch (Exception ex){
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            }catch (Exception ex2){
                response.setCode(10001);
                logger.error(ex.getMessage());
            }
        }
        return response;
    }

    /**
     * 通过api获取note
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/getUserDataApi")
    public Response getUserDataApi(@RequestBody UserDataRequest request,
                                          HttpServletRequest httpServletRequest) {
        Response response = new Response();
        try {
            String token = httpServletRequest.getHeader("token");
            Map in = new HashMap();
            in.put("dataToken", request.getDataToken());
            in.put("token", token);

            //客户端加密的内容密文
            in.put("encryptKey", request.getEncryptKey());
            //RSA公钥的token
            in.put("keyToken", request.getKeyToken());

            Map out = iUserDataBusinessService.getUserDataApi(in);
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
     * 通过api获取note（不加密）
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/getUserDataApi2")
    public Response getUserDataApi2(@RequestBody UserDataRequest request,
                                          HttpServletRequest httpServletRequest) {
        Response response = new Response();
        try {
            String token = httpServletRequest.getHeader("token");
            Map in = new HashMap();
            in.put("dataToken", request.getDataToken());
            in.put("token", token);

            Map out = iUserDataBusinessService.getUserDataApi2(in);
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
}
