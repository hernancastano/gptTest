package gptTest.Dto;

import java.util.List;

public class EmbeddingDataDTO {
    private String object;
    private int index;
    private List<Double> embedding;

    public String getObject() {
        return object;
    }

    public int getIndex() {
        return index;
    }

    public List<Double> getEmbedding() {
        return embedding;
    }
}
