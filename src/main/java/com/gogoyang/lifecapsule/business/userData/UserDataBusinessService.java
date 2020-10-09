package com.gogoyang.lifecapsule.business.userData;

import com.gogoyang.lifecapsule.business.common.ICommonService;
import com.gogoyang.lifecapsule.meta.note.entity.NoteInfo;
import com.gogoyang.lifecapsule.meta.note.service.INoteService;
import com.gogoyang.lifecapsule.meta.user.entity.UserInfo;
import com.gogoyang.lifecapsule.meta.userData.entity.UserData;
import com.gogoyang.lifecapsule.meta.userData.service.IUserDataService;
import com.gogoyang.lifecapsule.utility.GogoTools;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserDataBusinessService implements IUserDataBusinessService{
    private final IUserDataService iUserDataService;
    private final ICommonService iCommonService;
    private final INoteService iNoteService;

    public UserDataBusinessService(IUserDataService iUserDataService,
                                   ICommonService iCommonService,
                                   INoteService iNoteService) {
        this.iUserDataService = iUserDataService;
        this.iCommonService = iCommonService;
        this.iNoteService = iNoteService;
    }

    @Override
    public Map convertToApi(Map in) throws Exception {
        String token=in.get("token").toString();
        String noteId=in.get("noteId").toString();

        /**
         * 操作用户
         */
        UserInfo userInfo=iCommonService.getUserByToken(token);

        NoteInfo noteInfo=iCommonService.getNoteByNoteId(noteId,userInfo.getUserId());

        UserData userData=new UserData();
        userData.setDataId(GogoTools.UUID().toString());
        userData.setDataToken(GogoTools.UUID().toString());
        userData.setDataTokenTime(new Date());
        userData.setNoteId(noteInfo.getNoteId());
        iUserDataService.createUserData(userData);

        Map out=new HashMap();
        out.put("dataToken", userData.getDataToken());
        out.put("userToken", userInfo.getToken());

        return out;

    }

    @Override
    public Map getNoteApi(Map in) throws Exception {
        String token=in.get("token").toString();
        String noteId=in.get("noteId").toString();

        UserInfo userInfo=iCommonService.getUserByToken(token);
        NoteInfo noteInfo=iCommonService.getNoteByNoteId(noteId, userInfo.getUserId());

        Map qIn=new HashMap();
        qIn.put("noteId", noteId);
        UserData userData=iUserDataService.getUserData(qIn);

        Map out=new HashMap();
        ArrayList list=new ArrayList();
        Map map=new HashMap();
        map.put("param","token");
        map.put("value", token);
        list.add(map);
        if(userData!=null){
            map=new HashMap();
            map.put("param", "dataToken");
            map.put("value",userData.getDataToken());
            list.add(map);
        }
        out.put("userDataParams", list);
        return out;
    }

    /**
     * 通过api获取用户的note内容
     * @param in
     * @return
     * @throws Exception
     */
    @Override
    public Map getUserDataApi(Map in) throws Exception {
        String token=in.get("token").toString();
        String dataToken=in.get("dataToken").toString();
        String encryptKey=in.get("encryptKey").toString();
        String keyToken=in.get("keyToken").toString();

        UserInfo userInfo=iCommonService.getUserByToken(token);

        /**
         * 1、用户需要自己创建一个AES私钥
         * 2、用户需要请求一个RSA公钥，然后用这个公钥来加密AES私钥
         * 3、用户将加密好的AES私钥同公钥Token，keyToken，以及要请求的dataToken一并上传到服务器
         * 4、首先使用keyToken读取RSA私钥，用该私钥去解密用户的AES私钥
         * 5、用dataToken获取用户的noteId，获取note
         * 6、用AES私钥加密note的AES秘钥，同note内容一起返回给用户
         * 7、用户用这个AES来解密返回的加密note，获得note内容
         */
        String strAESKey = iCommonService.takeNoteAES(keyToken, encryptKey);

        System.out.println("用户上传的AES Key:"+strAESKey);

        //查询note内容
        Map qIn=new HashMap();
        qIn.put("dataToken", dataToken);
        UserData userData=iUserDataService.getUserData(qIn);
        NoteInfo noteInfo=iNoteService.getNoteDetailByNoteId(userData.getNoteId());

        //用AES秘钥加密笔记内容的AES秘钥
        String data = noteInfo.getUserEncodeKey();
        System.out.println("笔记的Key："+data);
        String outCode = GogoTools.encryptAESKey(data, strAESKey);
        System.out.println("加密后的笔记Key："+outCode);
        noteInfo.setUserEncodeKey(outCode);

        Map out = new HashMap();
        out.put("note", noteInfo);
        return out;
    }

    @Override
    public Map getUserDataApi2(Map in) throws Exception {
        String token=in.get("token").toString();
        String dataToken=in.get("dataToken").toString();

        //查询note内容
        Map qIn=new HashMap();
        qIn.put("dataToken", dataToken);
        UserData userData=iUserDataService.getUserData(qIn);
        NoteInfo noteInfo=iNoteService.getNoteDetailByNoteId(userData.getNoteId());

        Map out = new HashMap();
        out.put("note", noteInfo);
        return out;
    }
}
