package com.crio.starter.repositoryservices;

import java.util.List;
import java.util.Optional;
import com.crio.starter.model.MemesEntity;
import com.crio.starter.repository.MemesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class MemesRepositoryServiceImpl implements MemesRepositoryService{

    @Autowired
    MemesRepository memesRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public MemesEntity save(MemesEntity entity) {
      return memesRepository.save(entity);
    }

    @Override
    public List<MemesEntity> getMemes() {
      Query query = new Query();
      query.limit(100);
      query.with(Sort.by(Sort.Direction.DESC,"id"));
      return mongoTemplate.find(query, MemesEntity.class);
    }

    @Override
    public Optional<MemesEntity> getMemesById(String id) { 
      return memesRepository.findById(id);
    }
    
}
