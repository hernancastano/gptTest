package gptTest.Service;

import gptTest.Interfaz.PDFService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PDFServiceImp implements PDFService
{
    @Override
    public List<String> separateParagraphsFromAPdf(String filePath){
        List<String> paragraphs = new ArrayList<>();
        try {
            try(PDDocument document = PDDocument.load(new File(filePath))) {
                PDFTextStripper stripper = new PDFTextStripper();
                String text = stripper.getText(document);
                String[] arrayParagraphs = text.split("(?<=\\.)\\s+");
                for (String paragraph : arrayParagraphs) {
                    paragraphs.add(paragraph.trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return paragraphs;
    }
}
