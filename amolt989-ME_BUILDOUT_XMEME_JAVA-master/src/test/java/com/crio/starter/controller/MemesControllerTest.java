package com.crio.starter.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import com.crio.starter.App;
import com.crio.starter.model.MemesEntity;
import com.crio.starter.service.MemesService;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.UriComponentsBuilder;

@AutoConfigureMockMvc
@DirtiesContext
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class MemesControllerTest {

    private static final String MEMES_API_URI = "/memes/"; //MemesController.MEMES_API_ENDPOINT + MemesController.MEMES_API;
    //private static final String MEMES_API = MemesController.MEMES_API_ENDPOINT;
    List<MemesEntity> memesList;

    private MockMvc mvc;

    @MockBean
    private MemesService memesService;
   
    @InjectMocks
    private MemesController memesController;

    @BeforeEach
    public void setup() {

        MockitoAnnotations.openMocks(this);

        mvc = MockMvcBuilders.standaloneSetup(memesController).build();
  }

    @Test
    public void incorrectQueryReturnsBadRequestResponse() throws Exception{
     
      Mockito.when(memesService.saveEntity(any(MemesEntity.class)) ).thenReturn("64f337205c0edc56343e3162");

      URI uri = UriComponentsBuilder
        .fromPath(MEMES_API_URI)
        .build().toUri();
        

      assertEquals(MEMES_API_URI, uri.toString());
      System.out.println("MEMES_API_URI: "+MEMES_API_URI);
      System.out.println("uri.toString() : "+uri.toString());
      RequestBuilder requestBuilder = MockMvcRequestBuilders.post(MEMES_API_URI).accept(MediaType.TEXT_HTML_VALUE);
      //.content("64f337205c0edc56343e3162").contentType(MediaType.APPLICATION_JSON);

      MvcResult result = mvc.perform(requestBuilder).andReturn();

      MockHttpServletResponse response = result.getResponse();
      assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

    }

    @Test
    public void correctQueryReturnsOkResponseAndListOfMemes() throws Exception{
      Mockito.when(memesService.getMemesEntities()).thenReturn(loadSampleResponseList()); 

      URI uri = UriComponentsBuilder
      .fromPath(MEMES_API_URI)
      .build().toUri();

      assertEquals(MEMES_API_URI, uri.toString());
      System.out.println("MEMES_API_URI: "+MEMES_API_URI);
      System.out.println("uri.toString() : "+uri.toString());

      RequestBuilder requestBuilder = MockMvcRequestBuilders.get(MEMES_API_URI).accept(MediaType.APPLICATION_JSON);
      MvcResult result = mvc.perform(requestBuilder).andReturn();

      MockHttpServletResponse response = result.getResponse();
      assertEquals(HttpStatus.OK.value(), response.getStatus());

      verify(memesService, times(1)).getMemesEntities();

    }

    private List<MemesEntity> loadSampleResponseList() throws IOException {
        memesList = new ArrayList<MemesEntity>();
        
        MemesEntity memes = new MemesEntity();
        memes.setId("64f337205c0edc56343e3162");
        memes.setName("Ashoka");
        memes.setUrl("https://images.pexels.com/photos/3573382/pexels-photo-3573382.jpeg");
        memes.setCaption("This is memes");
        memesList.add(memes);

        return memesList;
      }  
}
