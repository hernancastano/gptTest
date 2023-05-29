package gptTest.Interfaz;

import java.io.IOException;
import java.util.List;

public interface PDFService {
     List<String> separateParagraphsFromAPdf(String filePath);
}
