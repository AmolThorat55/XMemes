package com.crio.starter.model;

import lombok.AllArgsConstructor;  
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// Java class that maps to Mongo collection.
@Data
@Document(collection = "memes")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true) 
public class MemesEntity {
    
    @Id
    private String id;
    private String name;
    private String caption;
    private String url;
}
