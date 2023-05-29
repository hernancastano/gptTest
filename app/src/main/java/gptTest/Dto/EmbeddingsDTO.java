package gptTest.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmbeddingsDTO implements Serializable {
    @JsonProperty("input")
    private String input;
    @JsonProperty("model")
    private String model;
    @JsonIgnore
    private String search;
    public String getSearch() {
        return search;
    }
    @JsonProperty("search")
    public void setSearch(String search) {
        this.search = search;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
