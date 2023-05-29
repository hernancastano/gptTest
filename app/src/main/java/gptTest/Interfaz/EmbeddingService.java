package gptTest.Interfaz;

import gptTest.Dto.EmbeddingdResponseDTO;
import gptTest.Dto.EmbeddingsDTO;
import gptTest.Dto.EmbeddingsWithPdfDTO;

import java.util.List;
import java.util.Map;

public interface EmbeddingService {

    double initService(EmbeddingsDTO embeddings);

    EmbeddingdResponseDTO callGptEmbeddingsApi(String text);

    double[] convertListDoubletoArrayDouble(List<Double> list);

    public double calculateCosineSimilatiry(List<Double> vectorOne, List<Double> vectorTwo);

    Map<String, Double> ordenarMapaPorValor(Map<String, Double> mapa);

    Map<String, Double> initServiceListPdf(EmbeddingsWithPdfDTO embeddingsWithPdf);
}
