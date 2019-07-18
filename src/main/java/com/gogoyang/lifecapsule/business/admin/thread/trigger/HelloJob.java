package com.gogoyang.lifecapsule.business.admin.thread.trigger;

import com.gogoyang.lifecapsule.meta.gogoKey.entity.GogoKey;
import com.gogoyang.lifecapsule.meta.gogoKey.entity.KeyParams;
import com.gogoyang.lifecapsule.meta.gogoKey.service.IGogoKeyService;
import com.gogoyang.lifecapsule.utility.GogoTools;
import lombok.Data;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;


@Data
public class HelloJob implements Job {
    @Autowired
    private IGogoKeyService iGogoKeyService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        /**
         * 读取所有gogokey
         * 检查gogokey是否满足条件
         * 满足条件就处理
         *
         */
        try {
            System.out.println("开始检查");
            List<GogoKey> gogoKeyList = iGogoKeyService.listGogoKey();
            for (int i = 0; i < gogoKeyList.size(); i++) {
                for (int k = 0; k < gogoKeyList.get(i).getParams().size(); k++) {
                    KeyParams key = gogoKeyList.get(i).getParams().get(k);
                    if (key.getType().equals("datetime")) {
                        Date theDate = new Date();
                        theDate = GogoTools.formatStrUTCToDateStr(key.getValue().toString());
                        if (theDate.after(new Date())) {
                            System.out.println("成功触发了一个笔记");
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}