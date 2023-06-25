package com.example.CRUDExample.controller;

import com.example.CRUDExample.model.Document;
import com.example.CRUDExample.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @GetMapping("/uploadForm")
    public String showUploadForm(Model model) {
        model.addAttribute("document", new Document());
        return "uploadForm";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "redirect:/?error=No file selected";
        }

        try {
            Document document = new Document();
            document.setName(file.getOriginalFilename());
            document.setFileType(file.getContentType());
            document.setFileSize(file.getSize());
            document.setFileContent(file.getBytes());
            documentService.saveDocument(document);
            return "File uploaded successfully";
        } catch (Exception e) {
            return "redirect:/?error=Failed to upload file";
        }
    }

    @GetMapping("/documents")
    public List<Document> showDocuments(Model model) {
        List<Document> documents = documentService.getAllDocuments();
        model.addAttribute("documents", documents);
        return documents;
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadDocument(@PathVariable("id") Long id) {
        Document document = documentService.getDocumentById(id);
        byte[] fileContent = document.getFileContent();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename(document.getName()).build());

        return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
    }

    @GetMapping("/delete/{id}")
    public String deleteDocument(@PathVariable("id") Long id) {
        documentService.deleteDocument(id);
        return "file deleted successfully";
    }
}
