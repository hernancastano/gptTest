package gptTest.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmbeddingsWithPdfDTO extends EmbeddingsDTO implements Serializable {
    private String pdfPath;

    public String getPdfPath() {
        return pdfPath;
    }
    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }
}
