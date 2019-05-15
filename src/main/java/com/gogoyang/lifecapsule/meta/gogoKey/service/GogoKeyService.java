package com.gogoyang.lifecapsule.meta.gogoKey.service;

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

    @Autowired
    public GogoKeyService(IGogoKeyRepository iGogoKeyRepository) {
        this.iGogoKeyRepository = iGogoKeyRepository;
    }

    @Override
    public void createGogoKey(GogoKey gogoKey) throws Exception {
        iGogoKeyRepository.createGogoKey(gogoKey);
    }

    @Override
    public List<GogoKey> listGogokey() throws Exception {
        Query query=Query(Criteria.where())
        List<GogoKey> list=
        return null;
    }
}
