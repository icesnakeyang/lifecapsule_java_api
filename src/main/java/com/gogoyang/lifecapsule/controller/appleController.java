package com.gogoyang.lifecapsule.controller;

import com.gogoyang.lifecapsule.controller.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apple")
public class appleController {
    private final IAppleService iAppleService;

    @Autowired
    public appleController(IAppleService iAppleService) {
        this.iAppleService = iAppleService;
    }

    @ResponseBody
    @GetMapping("/listApple")
    public Response listApple(){
        Response response=new Response();
        try {
            Map out=new HashMap();
            List<Tank> appleList=iAppleService.listApple();
            out.put("appleList", appleList);
            response.setData(out);
        }catch (Exception ex){

        }
        return response;
    }
}
