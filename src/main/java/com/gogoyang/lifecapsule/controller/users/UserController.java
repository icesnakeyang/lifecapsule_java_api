package com.gogoyang.lifecapsule.controller.users;

import com.gogoyang.lifecapsule.business.login.ILoginBusinessService;
import com.gogoyang.lifecapsule.business.register.IRegisterBusinessService;
import com.gogoyang.lifecapsule.business.user.profile.IUserProfileBusinessService;
import com.gogoyang.lifecapsule.controller.vo.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * UserController is use to provide api to access all functions that use to operate user
 * Api address: host:port/user/
 * All request params will be accepted by vo class such as UserRequest
 * All input and out put data to active with business layer need to be put into map
 * All controller will return a Response json data, contains code and data
 * Client will compare the code to know the result of process, if code = 0 then success, others code can check the error code table
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private final ILoginBusinessService iLoginBusinessService;
    private final IRegisterBusinessService iRegisterBusinessService;
    private final IUserProfileBusinessService iUserProfileBusinessService;

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public UserController(ILoginBusinessService iLoginBusinessService,
                          IRegisterBusinessService iRegisterBusinessService,
                          IUserProfileBusinessService iUserProfileBusinessService) {
        this.iLoginBusinessService = iLoginBusinessService;
        this.iRegisterBusinessService = iRegisterBusinessService;
        this.iUserProfileBusinessService = iUserProfileBusinessService;
    }

    /**
     * 用户登录
     *
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/loginUser")
    public Response loginUser(@RequestBody UserRequest request) {
        Response response = new Response();
        try {
            Map in = new HashMap();
            /**
             * 这里是前端需要在post提交的参数
             * 前端自行判断用户是通过phone还是email登录，然后填写正确的参数
             * password是必填，前端只需提交用户填写的密码，由business层行加密验证
             * phone和email必须填其中一项
             */
            in.put("phone", request.getPhone());
            in.put("email", request.getEmail());
            in.put("password", request.getPassword());
            in.put("keyToken", request.getKeyToken());

            Map out = iLoginBusinessService.loginUser(in);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode((Integer.parseInt(ex.getMessage())));
            } catch (Exception ex2) {
                logger.error(ex.getMessage());
                response.setCode(10001);
            }
        }
        return response;
    }

    /**
     * 用户注册
     * 用户可任意使用phone，或者email注册
     *
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/registerme")
    public Response registerMe(@RequestBody UserRequest request) {
        Response response = new Response();
        try {
            Map in = new HashMap();
            in.put("phone", request.getPhone());
            in.put("email", request.getEmail());
            in.put("password", request.getPassword());
            in.put("keyToken", request.getKeyToken());
            Map out = iRegisterBusinessService.registerMe(in);
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
     * 创建一个临时用户，实现新用户的自动注册登录
     *
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/createNewUser")
    public Response createNewUser(@RequestBody UserRequest request) {
        Response response = new Response();
        try {
            Map in = new HashMap();
            in.put("deviceId", request.getDeviceId());
            Map out = iRegisterBusinessService.createBlankUser(in);
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
     * 临时用户登录
     *
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/loginBlankUser")
    public Response loginBlankUser(HttpServletRequest httpServletRequest) {
        Response response = new Response();
        try {
            Map in = new HashMap();
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            Map out = iLoginBusinessService.loginBlankUser(in);
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
     * 重新申请一个用户token
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/resignUserToken")
    public Response resignUserToken(@RequestBody UserRequest request,
                                    HttpServletRequest httpServletRequest) {
        Response response = new Response();
        Map in = new HashMap();
        try {
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            iLoginBusinessService.resignUserToken(in);
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
    @PostMapping("/saveNickname")
    public Response saveNickname(@RequestBody UserRequest request,
                                 HttpServletRequest httpServletRequest) {
        Response response = new Response();
        try {
            Map in = new HashMap();
            String token = httpServletRequest.getHeader("token");
            in.put("token", token);
            in.put("nickname", request.getNickname());
            iUserProfileBusinessService.saveNickname(in);
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
    @PostMapping("/saveLoginPassword")
    public Response saveLoginPassword(@RequestBody UserRequest request,
                                      HttpServletRequest httpServletRequest) {
        Response response = new Response();
        try {
            String token = httpServletRequest.getHeader("token");
            Map in = new HashMap();
            in.put("token", token);
            in.put("password", request.getPassword());
            iUserProfileBusinessService.savePassword(in);
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
     * 用户输入需要绑定的手机号码
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/bindPhone1")
    public Response bindPhone1(@RequestBody UserRequest request,
                               HttpServletRequest httpServletRequest) {
        Response response = new Response();
        try {
            String token = httpServletRequest.getHeader("token");
            Map in = new HashMap();
            in.put("token", token);
            in.put("phone", request.getPhone());
            iUserProfileBusinessService.bindPhone1(in);
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
     * 用户输入收到的手机验证码
     * @param request
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/bindPhone2")
    public Response bindPhone2(@RequestBody UserRequest request,
                               HttpServletRequest httpServletRequest) {
        Response response = new Response();
        try {
            String token = httpServletRequest.getHeader("token");
            Map in = new HashMap();
            in.put("token", token);
            in.put("code", request.getPhone());
            iUserProfileBusinessService.bindPhone2(in);
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
