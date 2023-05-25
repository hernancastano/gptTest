package gptTest.Controller;

import gptTest.Dto.EmbeddingdResponse;
import gptTest.Dto.EmbeddingsWithPdf;
import gptTest.Service.EmbeddingService;
import gptTest.Service.PDFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class EmbeddingsWithPDFController {
    @Autowired
    private PDFService pdfService;

    @Autowired
    private EmbeddingService embeddingService;

    @PostMapping(value = "/embeddings/pdf", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Double>> embiddingsWithPdf(@RequestBody EmbeddingsWithPdf embeddingsWithPdf) throws IOException {
        Map<String, Double> response = new HashMap<>();
        List<String> separateParagraphsFromAPdf = pdfService.separateParagraphsFromAPdf(embeddingsWithPdf.getPdfPath());
        EmbeddingdResponse searchEmbeddingdResponse = embeddingService.callGptEmbeddingsApi(embeddingsWithPdf.getToStringWithInput(embeddingsWithPdf.getSearch()));
        double[] searchConvertListDoubletoArrayDouble = embeddingService.convertListDoubletoArrayDouble(searchEmbeddingdResponse.getData().get(0).getEmbedding());
        for (String input: separateParagraphsFromAPdf) {
            EmbeddingdResponse inputEmbeddingdResponse = embeddingService.callGptEmbeddingsApi(embeddingsWithPdf.getToStringWithInput(input));
            double[] imputConvertListDoubletoArrayDouble = embeddingService.convertListDoubletoArrayDouble(inputEmbeddingdResponse.getData().get(0).getEmbedding());
            double responseCalculateCosineSimilatiry = embeddingService.calculateCosineSimilatiry(searchConvertListDoubletoArrayDouble, imputConvertListDoubletoArrayDouble);
            response.put(input, responseCalculateCosineSimilatiry);
        }
        Map<String, Double> mapaOrdenado = embeddingService.ordenarMapaPorValor(response);
        return ResponseEntity.ok(mapaOrdenado);
    }
}
