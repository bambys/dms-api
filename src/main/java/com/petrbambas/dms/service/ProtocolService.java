package com.petrbambas.dms.service;

import com.petrbambas.dms.model.Protocol;
import com.petrbambas.dms.repository.ProtocolRepository;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProtocolService {
    @Autowired
    private ProtocolRepository protocolRepository;
    @Autowired
    private EntityManager entityManager;

    public List<Protocol> findAll() {
        return protocolRepository.findAll();
    }

    public Protocol getProtocolById(Integer id) {
        return protocolRepository.findById(id).get();
    }

    @Transactional
    public Protocol save(Protocol protocol) {
        Protocol protocolUpdated = protocolRepository.save(protocol);
        /*
        refresh is called because of loading set of documents including their attributes to the JSON response properly
        The documents itself can not be changed via saving or updating Protocol.
        The Set of documents - IDs - has to be changed via save or update Protocol.
        So only protocol and protocol_document table should be changed.
        */
        entityManager.unwrap(Session.class).refresh(protocolUpdated);
        return protocolUpdated;
    }

    @Transactional
    public Optional<Protocol> UpdateProtocolAllData(Integer id, Protocol updatedProtocol) {
        Optional<Protocol> savedProtocol = protocolRepository.findById(id);
        if (savedProtocol.isPresent()) {
            Protocol protocol = savedProtocol.get();
            protocol.setName(updatedProtocol.getName());
            protocol.setStatus(updatedProtocol.getStatus());
            protocol.setDocuments(updatedProtocol.getDocuments());
            protocolRepository.save(protocol);
        }
        return savedProtocol;
    }

    @Transactional
    // this method will update status of protocol only
    public Optional<Protocol> UpdateProtocolStatus(Integer id, Protocol updatedProtocol) {
        Optional<Protocol> savedProtocol = protocolRepository.findById(id);
        if (savedProtocol.isPresent()) {
            Protocol Protocol = savedProtocol.get();
            Protocol.setStatus(updatedProtocol.getStatus());
            protocolRepository.save(Protocol);
        }
        return savedProtocol;
    }
}
