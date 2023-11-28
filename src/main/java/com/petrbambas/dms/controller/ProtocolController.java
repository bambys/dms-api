package com.petrbambas.dms.controller;

import com.petrbambas.dms.model.Protocol;
import com.petrbambas.dms.service.ProtocolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProtocolController {
    @Autowired
    private ProtocolService protocolService;

    @GetMapping("/protocols")
    public ResponseEntity<List<Protocol>> getAllProtocols() {
        List<Protocol> protocols = protocolService.findAll();
        if (protocols.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(protocols);
    }

    @PostMapping("/protocols")
    public ResponseEntity<Protocol> addProtocol(@RequestBody Protocol protocol) {
        protocol.setId(0);
        return ResponseEntity.ok(protocolService.save(protocol));
    }

    @PutMapping("/protocols/{id}")
    public ResponseEntity<Protocol> updateProtocol(@PathVariable int id, @RequestBody Protocol protocol) {
        Optional<Protocol> updatedProtocol = protocolService.UpdateProtocolAllData(id, protocol);
        if (updatedProtocol.isPresent()) {
            return (ResponseEntity.ok(updatedProtocol.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/protocols/{id}/status")
    public ResponseEntity<Protocol> updateProtocolStatus(@PathVariable int id, @RequestBody Protocol protocol) {
        Optional<Protocol> updateProtocol = protocolService.UpdateProtocolStatus(id, protocol);
        if (updateProtocol.isPresent()) {
            return (ResponseEntity.ok(updateProtocol.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}