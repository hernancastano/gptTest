package gptTest.Controller;

import gptTest.Dto.EmbeddingdResponse;
import gptTest.Dto.Embeddings;
import gptTest.Service.EmbeddingService;
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
    public String generateEmbeddings(@RequestBody Embeddings embeddings) {
        EmbeddingdResponse imputEmbeddingdResponse = embeddingService.callGptEmbeddingsApi(embeddings.getToStringWithInput());
        EmbeddingdResponse searchEmbeddingdResponse = embeddingService.callGptEmbeddingsApi(embeddings.getToStringWithSearch());
        assert searchEmbeddingdResponse != null;
        double[] searchConvertListDoubletoArrayDouble = embeddingService.convertListDoubletoArrayDouble(searchEmbeddingdResponse.getData().get(0).getEmbedding());
        assert imputEmbeddingdResponse != null;
        double[] imputConvertListDoubletoArrayDouble = embeddingService.convertListDoubletoArrayDouble(imputEmbeddingdResponse.getData().get(0).getEmbedding());
        double responseCalculateCosineSimilatiry = embeddingService.calculateCosineSimilatiry(searchConvertListDoubletoArrayDouble, imputConvertListDoubletoArrayDouble);
        return String.valueOf(responseCalculateCosineSimilatiry);
    }
}
