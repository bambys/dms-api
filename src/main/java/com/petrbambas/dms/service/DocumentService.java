package com.petrbambas.dms.service;

import com.petrbambas.dms.model.Document;
import com.petrbambas.dms.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {
    @Autowired
    private final DocumentRepository documentRepository;
    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }
    public List<Document> findAll() {
        return documentRepository.findAll();
    }

    @Transactional
    public void save(Document document) {
        documentRepository.save(document);
    }

    @Transactional
    public Optional<Document> updateDocument(Integer id, Document updatedDoc) {
        Optional<Document> savedDoc = documentRepository.findById(id);

        if (savedDoc.isPresent()) {
            Document document = savedDoc.get();
            document.setName(updatedDoc.getName());
            document.setType(updatedDoc.getType());
            documentRepository.save(document);
        }
        return savedDoc;
    }

    @Transactional
    public boolean deleteDocument(Integer id) {
        if (documentRepository.existsById(id)) {
            documentRepository.deleteById(id);
            return true;
        }
        return false;
    }
}