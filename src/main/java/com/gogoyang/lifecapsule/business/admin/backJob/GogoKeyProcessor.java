package com.gogoyang.lifecapsule.business.admin.backJob;

import com.gogoyang.lifecapsule.business.gogoKey.IGogoKeyBusinessService;
import com.gogoyang.lifecapsule.meta.gogoKey.entity.GogoKey;
import com.gogoyang.lifecapsule.meta.gogoKey.service.IGogoKeyService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;

public class GogoKeyProcessor extends QuartzJobBean {
    @Autowired
    private IGogoKeyService iGogoKeyService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("我来了");
        try {
            /**
             * 这里应该读取所有的trigger
             */
            List<GogoKey> gogoKeys = iGogoKeyService.listGogoKeyAll();
            for (int i = 0; i < gogoKeys.size(); i++) {
                logger.info(gogoKeys.get(i).getTitle());
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }
}
