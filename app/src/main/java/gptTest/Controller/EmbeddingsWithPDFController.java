package gptTest.Controller;

import gptTest.Dto.EmbeddingsWithPdfDTO;
import gptTest.Service.EmbeddingServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
public class EmbeddingsWithPDFController {

    @Autowired
    private EmbeddingServiceImp embeddingService;

    @PostMapping(value = "/embeddings/pdf", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Double>> embiddingsWithPdf(@RequestBody EmbeddingsWithPdfDTO embeddingsWithPdf) {
        return ResponseEntity.ok(embeddingService.initServiceListPdf(embeddingsWithPdf));
    }
}
