package gptTest.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import gptTest.Dto.EmbeddingdResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@Service
public class EmbeddingService {
    public EmbeddingdResponse callGptEmbeddingsApi(String text) {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/embeddings"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer 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")
                .POST(HttpRequest.BodyPublishers.ofString(text))
                .build();
        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            String json = response.body();
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, EmbeddingdResponse.class);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public double[] convertListDoubletoArrayDouble(List<Double> list) {
        return list.stream().mapToDouble(Double::doubleValue).toArray();
    }

    public double calculateCosineSimilatiry(double[] vectorOne, double[] vectorTwo){
        // Verifica si los vectores tienen la misma longitud
        if (vectorOne.length != vectorTwo.length) {
            throw new IllegalArgumentException("Los vectores deben tener la misma longitud");
        }
        // Calcula el producto punto de los dos vectores
        double dotProduct = 0.0;
        for (int i = 0; i < vectorOne.length; i++) {
            dotProduct += vectorOne[i] * vectorTwo[i];
        }
        // Calcula la norma de cada vector
        double norm1 = calculateNorm(vectorOne);
        double norm2 = calculateNorm(vectorTwo);
        // Calcula la similitud del coseno
        return dotProduct / (norm1 * norm2);
    }

    private double calculateNorm(double[] vector) {
        double sum = 0.0;
        for (double value : vector) {
            sum += Math.pow(value, 2);
        }
        return Math.sqrt(sum);
    }

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
}
