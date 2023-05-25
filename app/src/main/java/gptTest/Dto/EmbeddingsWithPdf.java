package gptTest.Dto;

public class EmbeddingsWithPdf {
    private String pdfPath;
    private String model;
    private String search;

    public String getPdfPath() {
        return pdfPath;
    }
    public String getModel() {
        return model;
    }
    public  String getSearch() {
        return search;
    }
    public String getToStringWithInput(String input) {
        return "{\n" +
                "    \"input\": \""+ input.replace("\n", "") +"\",\n" +
                "    \"model\": \"" + this.getModel() + "\"\n" +
                "}";
    }
}
