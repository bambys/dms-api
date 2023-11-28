package com.petrbambas.dms.repository;

import com.petrbambas.dms.model.Protocol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProtocolRepository extends JpaRepository<Protocol, Integer> {
}