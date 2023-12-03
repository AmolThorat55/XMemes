package com.crio.starter.repository;

import java.util.Optional;
import com.crio.starter.model.MemesEntity;
import org.springframework.data.mongodb.repository.MongoRepository; 
import org.springframework.stereotype.Repository;

@Repository
public interface MemesRepository extends MongoRepository<MemesEntity, String>{
    public MemesEntity save(MemesEntity entity) ;

    public Optional<MemesEntity> findById(String id);
}
