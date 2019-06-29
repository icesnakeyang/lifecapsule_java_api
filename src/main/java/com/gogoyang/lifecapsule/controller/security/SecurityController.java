package com.gogoyang.lifecapsule.controller.security;

import com.gogoyang.lifecapsule.business.security.ISecurityBusinessService;
import com.gogoyang.lifecapsule.controller.vo.Response;
import com.gogoyang.lifecapsule.utility.GogoTools;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/security")
public class SecurityController {
    private final ISecurityBusinessService iSecurityBusinessService;

    public SecurityController(ISecurityBusinessService iSecurityBusinessService) {
        this.iSecurityBusinessService = iSecurityBusinessService;
    }

    @ResponseBody
    @GetMapping("/requestRSAPublicKey")
    public Response requestRSAPublicKey() {
        Response response = new Response();
        try {
            Map in = new HashMap();

            Map keyPairRSAMap = GogoTools.generateRSAKey();
            String keyToken = GogoTools.UUID().toString();
            in.put("privateKey", keyPairRSAMap.get("privateKey"));
            in.put("keyToken", keyToken);
            iSecurityBusinessService.saveRSAKey(in);

            Map out = new HashMap();
            out.put("publicKey", keyPairRSAMap.get("publicKey"));
            out.put("keyToken", keyToken);
            response.setData(out);
        } catch (Exception ex) {
            try {
                response.setCode(Integer.parseInt(ex.getMessage()));
            }catch (Exception ex2){
                response.setCode(10001);
            }
        }
        return response;
    }
}
