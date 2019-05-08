package com.gogoyang.lifecapsule;

import com.gogoyang.lifecapsule.meta.category.entity.NoteCategory;
import com.gogoyang.lifecapsule.meta.category.service.ICategoryService;
import com.gogoyang.lifecapsule.meta.note.entity.NoteInfo;
import com.gogoyang.lifecapsule.meta.note.service.INoteService;
import com.gogoyang.lifecapsule.meta.user.entity.UserInfo;
import com.gogoyang.lifecapsule.meta.user.service.IUserInfoService;
import com.gogoyang.lifecapsule.utility.GogoTools;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LifecapsuleApplicationTests {
    @Autowired
    private INoteService iNoteService;
    @Autowired
    private IUserInfoService iUserInfoService;

    @Autowired
    private ICategoryService iCategoryService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void createNote() {
        NoteInfo noteInfo = new NoteInfo();
        noteInfo.setTitle("杨超越");
        noteInfo.setDetail("村花你最棒");
        noteInfo.setCreatedTime(new Date());
        noteInfo.setUserId("12345");
        try {
            noteInfo.setNoteId(GogoTools.UUID().toString());
            iNoteService.createNote(noteInfo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void createUser() {
        UserInfo userInfo = new UserInfo();
        userInfo.setCreatedTime(new Date());
        String password = "33455432ccdeeDD";
        try {
            password = GogoTools.encoderByMd5(password);
            userInfo.setPassword(password);
            userInfo.setStatus(1);
            userInfo.setToken(GogoTools.UUID().toString());
            userInfo.setTokenTime(new Date());
            userInfo.setUserId(GogoTools.UUID().toString());
            userInfo.setPhone("test2");
            userInfo.setEmail("test2@test.com");
            iUserInfoService.createUser(userInfo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void getUser() {
        try {
            String password = "123456";
            password = GogoTools.encoderByMd5(password);
            UserInfo userInfo = iUserInfoService.getUserByPhonePassword("13438010117", password);
            if (userInfo != null) {
                System.out.print(userInfo.getPhone());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void getUserEamil() {
        try {
            String password = "33455432ccdeeDD";
            password = GogoTools.encoderByMd5(password);
            UserInfo userInfo = iUserInfoService.getUserByEmailPassword("test2@test.com", password);
            if (userInfo != null) {

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testCreateNoteCategory() {
        try {
            NoteCategory category = new NoteCategory();
            category.setCategoryId(GogoTools.UUID().toString());
            category.setCategoryName("test category 1");
            category.setUserId("12346");
            iCategoryService.createCategory(category);
            System.out.println();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
