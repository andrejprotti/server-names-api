package com.ajp.servernamesapi.controller;

import com.ajp.servernamesapi.exception.ResourceNotFoundException;
import com.ajp.servernamesapi.model.Server;
import com.ajp.servernamesapi.repository.ServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/")
public class ServerController {

    @Autowired
    ServerRepository serverRepository;


    @GetMapping("/servers")
    public List<Server> getAllServers() {
        return serverRepository.findAll();
    }

    @PostMapping("/servers")
    public Server createServer(@Valid @RequestBody Server server) {
        return serverRepository.save(server);
    }

    @GetMapping("/servers/{id}")
    public Server getServerById(@PathVariable(value="id") Long serverId){
        return serverRepository.findById(serverId)
                .orElseThrow(() -> new ResourceNotFoundException("Server", "id", serverId));
    }

    @PutMapping("/servers/{id}")
    public Server updateServer(@PathVariable(value = "id") Long serverId,
                           @Valid @RequestBody Server serverDetails) {

        Server server = serverRepository.findById(serverId)
                .orElseThrow(() -> new ResourceNotFoundException("Server", "id", serverId));

        server.setName(serverDetails.getName());

        return serverRepository.save(server);
    }

    @DeleteMapping("/servers/{id}")
    public ResponseEntity<?> deleteServer(@PathVariable(value = "id") Long serverId) {
        Server server = serverRepository.findById(serverId)
                .orElseThrow(() -> new ResourceNotFoundException("Server", "id", serverId));

        serverRepository.delete(server);

        return ResponseEntity.ok().build();
    }

}
