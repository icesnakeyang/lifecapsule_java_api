package com.gogoyang.lifecapsule.business.gogoKey;

import java.util.Map;

public interface IGogoKeyBusinessService {
    Map createGogoKey(Map in) throws Exception;

    Map listGogoKey(Map in) throws Exception;
}
