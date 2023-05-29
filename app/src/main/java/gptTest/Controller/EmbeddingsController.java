package gptTest.Controller;

import gptTest.Dto.EmbeddingsDTO;
import gptTest.Dto.ResponseDTO;
import gptTest.Interfaz.EmbeddingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EmbeddingsController {
    @Autowired
    EmbeddingService embeddingService;

    @PostMapping(value = "/embeddings", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO generateEmbeddings(@RequestBody EmbeddingsDTO embeddings) {
      return new ResponseDTO(200, "Proceso exitoso", embeddingService.initService(embeddings));
    }
}
