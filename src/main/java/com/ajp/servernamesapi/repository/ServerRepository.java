package com.ajp.servernamesapi.repository;

import com.ajp.servernamesapi.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerRepository extends JpaRepository<Server, Long> {
}
