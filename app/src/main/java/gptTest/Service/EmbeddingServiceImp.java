package gptTest.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import gptTest.Dto.EmbeddingdResponseDTO;
import gptTest.Dto.EmbeddingsDTO;
import gptTest.Dto.EmbeddingsWithPdfDTO;
import gptTest.Interfaz.EmbeddingService;
import gptTest.Interfaz.PDFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@Service
public class EmbeddingServiceImp implements EmbeddingService {

    @Value("${chatgpt.api-key}")
    private String apiKey;
    @Autowired
    private PDFService pdfService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public double initService(EmbeddingsDTO embeddings) {
            String getToStringWithInput = this.jsonConvert(embeddings);
            EmbeddingdResponseDTO imputEmbeddingdResponse = this.callGptEmbeddingsApi(getToStringWithInput);
            embeddings.setInput(embeddings.getSearch());
            String getToStringWithSearch = this.jsonConvert(embeddings);
            EmbeddingdResponseDTO searchEmbeddingdResponse = this.callGptEmbeddingsApi(getToStringWithSearch);
            assert searchEmbeddingdResponse != null;
            List<Double> searchConvertListDoubletoArrayDouble = searchEmbeddingdResponse.getData().get(0).getEmbedding();
            assert imputEmbeddingdResponse != null;
            List<Double> imputConvertListDoubletoArrayDouble = imputEmbeddingdResponse.getData().get(0).getEmbedding();

        return this.calculateCosineSimilatiry(searchConvertListDoubletoArrayDouble, imputConvertListDoubletoArrayDouble);
    }

    @Override
    public Map<String, Double> initServiceListPdf(EmbeddingsWithPdfDTO embeddingsWithPdf) {
        Map<String, Double> response = new HashMap<>();
        List<String> separateParagraphsFromAPdf = pdfService.separateParagraphsFromAPdf(embeddingsWithPdf.getPdfPath());
        embeddingsWithPdf.setInput(embeddingsWithPdf.getSearch() );
        String getToStringWithInput = this.jsonConvert(embeddingsWithPdf);
        EmbeddingdResponseDTO searchEmbeddingdResponse = this.callGptEmbeddingsApi(getToStringWithInput);

        List<Double> searchConvertListDoubletoArrayDouble = searchEmbeddingdResponse.getData().get(0).getEmbedding();
        for (String input : separateParagraphsFromAPdf) {
            embeddingsWithPdf.setInput(input);
            getToStringWithInput = this.jsonConvert(embeddingsWithPdf);
            EmbeddingdResponseDTO inputEmbeddingdResponse = this.callGptEmbeddingsApi(getToStringWithInput);
            List<Double> imputConvertListDoubletoArrayDouble = inputEmbeddingdResponse.getData().get(0).getEmbedding();
            double responseCalculateCosineSimilatiry = this.calculateCosineSimilatiry(searchConvertListDoubletoArrayDouble, imputConvertListDoubletoArrayDouble);
            response.put(input, responseCalculateCosineSimilatiry);
        }
        return this.ordenarMapaPorValor(response);
    }

    @Override
    public EmbeddingdResponseDTO callGptEmbeddingsApi(String text) {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/embeddings"))
                .header("Content-Type", "application/json")
                .header("Authorization", apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(text))
                .build();
        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            String json = response.body();
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, EmbeddingdResponseDTO.class);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public double[] convertListDoubletoArrayDouble(List<Double> list) {
        return list.stream().mapToDouble(Double::doubleValue).toArray();
    }

    @Override
    public double calculateCosineSimilatiry(List<Double> vectorOne, List<Double> vectorTwo) {
        // Verifica si los vectores tienen la misma longitud
        if (vectorOne.size() != vectorTwo.size()) {
            throw new IllegalArgumentException("Los vectores deben tener la misma longitud");
        }
        // Calcula el producto punto de los dos vectores
        double dotProduct = 0.0;
        for (int i = 0; i < vectorOne.size(); i++) {
            dotProduct += vectorOne.get(i) * vectorTwo.get(i);
        }
        // Calcula la norma de cada vector
        double norm1 = calculateNorm(vectorOne);
        double norm2 = calculateNorm(vectorTwo);
        // Calcula la similitud del coseno
        return dotProduct / (norm1 * norm2);
    }

    private double calculateNorm(List<Double> vector) {
        double sum = 0.0;
        for (double value : vector) {
            sum += Math.pow(value, 2);
        }
        return Math.sqrt(sum);
    }

    @Override
    public Map<String, Double> ordenarMapaPorValor(Map<String, Double> mapa) {
        List<Map.Entry<String, Double>> lista = new ArrayList<>(mapa.entrySet());
        Collections.sort(lista, new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return Double.compare(o2.getValue(), o1.getValue());
            }
        });
        Map<String, Double> mapaOrdenado = new LinkedHashMap<>();
        for (Map.Entry<String, Double> entry : lista) {
            mapaOrdenado.put(entry.getKey(), entry.getValue());
        }
        return mapaOrdenado;
    }

    public String jsonConvert(Object object) {
        String json = "";
        try {
            json = objectMapper.writeValueAsString(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

}