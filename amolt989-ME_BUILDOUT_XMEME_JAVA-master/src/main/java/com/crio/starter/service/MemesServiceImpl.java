package com.crio.starter.service;

import java.util.List;
import java.util.Optional;
import com.crio.starter.model.MemesEntity;
import com.crio.starter.repository.MemesRepository;
import com.crio.starter.repositoryservices.MemesRepositoryService;
import com.mongodb.client.MongoClient;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemesServiceImpl implements MemesService {

    private String name;
    private String url;
    private String caption;
    private long countName;
    private long countUrl;
    private long countCaption;

    @Autowired
    MemesRepository memesRepository;

    @Autowired
    MemesRepositoryService memesRepositoryService;

    @Autowired
    MongoClient mongoClient2;

    @Override
    public String saveEntity(MemesEntity entity) {
        return memesRepositoryService.save(entity).getId(); 
    }

    @Override
    public boolean isPayloadExist(MemesEntity entity) {
        name = entity.getName();
        url = entity.getUrl();
        caption = entity.getCaption();

        countName =  ( mongoClient2.getDatabase("greetings")               
                                  .getCollection("memes"))
                                  .countDocuments(new Document("name", name));
        countUrl =  ( mongoClient2.getDatabase("greetings")               
                                  .getCollection("memes"))
                                  .countDocuments(new Document("url", url));
        countCaption =  ( mongoClient2.getDatabase("greetings")               
                                  .getCollection("memes"))
                                  .countDocuments(new Document("caption", caption));  

        if(countName > 0 && countUrl > 0 && countCaption > 0){
            return false;
        }
        return true;
    }

    public boolean isCollectionEmpty(){
       long documentCount = mongoClient2.getDatabase("greetings").getCollection("memes").countDocuments();
       if(documentCount > 0) return false;
       else return true;
    }

    @Override
    public List<MemesEntity> getMemesEntities() { 
        return memesRepositoryService.getMemes();
    }

    @Override
    public Optional<MemesEntity> getMemesEntityById(String id) {
        return memesRepositoryService.getMemesById(id);
    }  
}
