package com.crio.starter.service;

import java.util.List;
import java.util.Optional;
import com.crio.starter.model.MemesEntity;
import org.springframework.stereotype.Service;

@Service
public interface MemesService {
    public String saveEntity(MemesEntity entity);

    public boolean isPayloadExist(MemesEntity entity); 

    public boolean isCollectionEmpty();

    public List<MemesEntity> getMemesEntities();

    public Optional<MemesEntity> getMemesEntityById(String id);
    
}
