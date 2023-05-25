package gptTest.Dto;
import java.util.List;

public class EmbeddingdResponse {
    private String object;
    private List<EmbeddingData> data;
    private String model;
    private Usage usage;

    public String getObject() {
        return object;
    }

    public List<EmbeddingData> getData() {
        return data;
    }

    public String getModel() {
        return model;
    }

    public Usage getUsage() {
        return usage;
    }
}
