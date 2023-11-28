package com.petrbambas.dms.controller;

import com.petrbambas.dms.model.Document;
import com.petrbambas.dms.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @GetMapping("/documents")
    public List<Document> getAllDocuments() {
        return documentService.findAll();
    }

    @PostMapping("/documents")
    public ResponseEntity<Document> addProduct(@RequestBody Document document) {
        document.setId(0);
        documentService.save(document);
        return ResponseEntity.ok(document);
    }

    @PutMapping("/documents/{id}")
    public ResponseEntity<Document> updateDocument(@PathVariable int id, @RequestBody Document document) {
        Optional<Document> updatedDocument = documentService.updateDocument(id, document);
        return updatedDocument.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("documents/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) {
        boolean deleted = documentService.deleteDocument(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
