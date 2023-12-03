package com.crio.starter.controller;

import lombok.extern.log4j.Log4j2;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.crio.starter.model.MemesEntity;
import com.crio.starter.service.MemesService;
import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable; 
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController; 

@RestController
@Log4j2
@RequestMapping(MemesController.MEMES_API_ENDPOINT)
public class MemesController {
    
    public static final String MEMES_API_ENDPOINT = "/memes"; 
    public static final String MEMES_API = "/xmem";
    static int count = 0;

    @Autowired
    MemesService memesService;

    @Autowired
    MongoClient mongoClient2;

    @PostMapping
    public ResponseEntity<HashMap> postMemes(@RequestBody MemesEntity entity){
        log.info("postMemes called with {}", entity);
        if(entity.getName() == null && entity.getUrl() == null && entity.getCaption() == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        
        //check wheather payload already present in database.
        boolean result = memesService.isPayloadExist(entity); 
        
        if(result == true){
        String id = memesService.saveEntity(entity);
        log.info("postMemes returned {}", new ResponseEntity<String>(id, HttpStatus.OK));
        HashMap<String, String> map = new HashMap<>();
        map.put("id", id);
        return new ResponseEntity<>(map, HttpStatus.OK);
        }
        else return new ResponseEntity<>( HttpStatus.CONFLICT);
    }

    @GetMapping
    public ResponseEntity<List<MemesEntity>> getLatestMemes(){
       // if(memesService.isCollectionEmpty()) return new ResponseEntity<List<MemesEntity>>(HttpStatus.OK);
        
        List<MemesEntity> memes = memesService.getMemesEntities();
        log.info("getLatestMemes returned {}", new ResponseEntity<List<MemesEntity>>(memes, HttpStatus.OK) );
        return new ResponseEntity<List<MemesEntity>>(memes , HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<MemesEntity> getMemesById(@PathVariable("id") String id){
        
        Optional<MemesEntity> entity = memesService.getMemesEntityById(id);
        if(entity.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            log.info("getMemesById returned {}", new ResponseEntity<MemesEntity>((MemesEntity) entity.get(), HttpStatus.OK) );
            return new ResponseEntity<MemesEntity>((MemesEntity) entity.get(), HttpStatus.OK);
        }
    }
}
