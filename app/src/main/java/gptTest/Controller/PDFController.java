package gptTest.Controller;

import gptTest.Interfaz.PDFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class PDFController {

    @Autowired
    private PDFService pdfService;

    @PostMapping("/pdf/separate-paragraph")
    public ResponseEntity<List<String>> separateParagraphsFromAPdf(@RequestBody String filePath) {
          return ResponseEntity.ok(pdfService.separateParagraphsFromAPdf(filePath));
    }
}
