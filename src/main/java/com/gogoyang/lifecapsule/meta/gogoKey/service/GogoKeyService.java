package com.gogoyang.lifecapsule.meta.gogoKey.service;

import com.gogoyang.lifecapsule.meta.gogoKey.dao.mapper.GogoKeyMapper;
import com.gogoyang.lifecapsule.meta.gogoKey.dao.repository.IGogoKeyRepository;
import com.gogoyang.lifecapsule.meta.gogoKey.entity.GogoKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GogoKeyService implements IGogoKeyService{
    private final IGogoKeyRepository iGogoKeyRepository;
    private final GogoKeyMapper gogoKeyMapper;

    @Autowired
    public GogoKeyService(IGogoKeyRepository iGogoKeyRepository,
                          GogoKeyMapper gogoKeyMapper) {
        this.iGogoKeyRepository = iGogoKeyRepository;
        this.gogoKeyMapper = gogoKeyMapper;
    }

    @Override
    public void createGogoKey(GogoKey gogoKey) throws Exception {
        gogoKeyMapper.createGogoKey(gogoKey);
        iGogoKeyRepository.createGogoKey(gogoKey);
    }

    @Override
    public List<GogoKey> listGogokey() throws Exception {
        Query query=Query(Criteria.where())
        List<GogoKey> list=
        return null;
    }
}
