package gptTest.Dto;
import java.util.List;

public class EmbeddingdResponseDTO {
    private String object;
    private List<EmbeddingDataDTO> data;
    private String model;
    private UsageDTO usage;

    public String getObject() {
        return object;
    }

    public List<EmbeddingDataDTO> getData() {
        return data;
    }

    public String getModel() {
        return model;
    }

    public UsageDTO getUsage() {
        return usage;
    }
}
