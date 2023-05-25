package gptTest.Dto;

public class Embeddings {
    private String input;
    private String model;

    private String search;

    public String getInput() {
        return input;
    }

    public String getModel() {
        return model;
    }

    public  String getSearch() {
        return search;
    }

    public String getToStringWithInput() {
        return "{\n" +
                "    \"input\": \""+ this.getInput() +"\",\n" +
                "    \"model\": \"" + this.getModel() + "\"\n" +
                "}";
    }

    public String getToStringWithSearch() {
        return "{\n" +
                "    \"input\": \""+ this.getSearch() +"\",\n" +
                "    \"model\": \"" + this.getModel() + "\"\n" +
                "}";
    }
}
