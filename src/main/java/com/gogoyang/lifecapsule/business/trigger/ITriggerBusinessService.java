package com.gogoyang.lifecapsule.business.trigger;

import java.util.Map;

public interface ITriggerBusinessService {
    /**
     * 创建一个新的笔记接收人
     * @param in
     * @return
     * @throws Exception
     */
    Map createRecipient(Map in) throws Exception;
}
