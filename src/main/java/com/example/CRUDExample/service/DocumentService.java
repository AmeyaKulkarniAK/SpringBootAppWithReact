package com.example.CRUDExample.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.CRUDExample.model.Document;
import com.example.CRUDExample.repo.DocumentRepository;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public void saveDocument(Document document) {
        documentRepository.save(document);
    }

    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    public Document getDocumentById(Long id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid document ID: " + id));
    }

    public void deleteDocument(Long id) {
        documentRepository.deleteById(id);
    }
}
