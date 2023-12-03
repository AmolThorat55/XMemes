package com.crio.starter.repositoryservices;

import java.util.List;
import java.util.Optional;
import com.crio.starter.model.MemesEntity;
import org.springframework.stereotype.Service;

@Service
public interface MemesRepositoryService {
    
    public MemesEntity save(MemesEntity entity);

    public List<MemesEntity> getMemes();

    public Optional<MemesEntity> getMemesById(String id);
    
}
