package gptTest.Service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PDFService
{
    public List<String> separateParagraphsFromAPdf(String filePath) throws IOException {
        List<String> paragraphs = new ArrayList<>();
        try(PDDocument document = PDDocument.load(new File(filePath))) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            String[] arrayParagraphs = text.split("(?<=\\.)\\s+");
            for (String paragraph : arrayParagraphs) {
                paragraphs.add(paragraph.trim());
            }
        }
        return paragraphs;
    }
}
