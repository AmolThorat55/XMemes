package com.crio.starter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import com.crio.starter.App;
import com.crio.starter.model.MemesEntity;
import com.crio.starter.repositoryservices.MemesRepositoryService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@AutoConfigureMockMvc
@DirtiesContext
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@SpringBootTest(classes = App.class)
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class MemesServiceTest {

    List<MemesEntity> memesList;
    
    @MockBean
    private MemesRepositoryService memesRepositoryService;
   
    @InjectMocks
    private MemesServiceImpl memesService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
  }

  @Test
  public void getListOfMemesEntitiesAsResponse() throws IOException{

    Mockito.when(memesRepositoryService.getMemes() ).thenReturn(loadSampleResponseList());
    
    List<MemesEntity> memesList = memesService.getMemesEntities();

    assertEquals(loadSampleResponseList(), memesList);
  }

  @Test
  public void getMemesByIdTest() throws IOException{

    List<MemesEntity> memesList = loadSampleResponseList();

    Mockito.when( memesRepositoryService.getMemesById(anyString())).thenReturn(Optional.of(memesList.get(0) ) );
    
    MemesEntity actual = memesService.getMemesEntityById("64f337205c0edc56343e3162").get();

    assertEquals(memesList.get(0).getId(), actual.getId());

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

