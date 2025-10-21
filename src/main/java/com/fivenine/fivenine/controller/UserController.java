package com.fivenine.fivenine.controller;

import com.fivenine.fivenine.model.UserDto;
import com.fivenine.fivenine.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/users") // итоговый путь: /api/v1/users (из твоего context-path)
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public java.util.List<UserDto.View> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public UserDto.View get(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<UserDto.View> create(@RequestBody UserDto.Create req) {
        UserDto.View created = service.create(req);
        return ResponseEntity.created(URI.create("/api/v1/users/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    public UserDto.View update(@PathVariable Long id, @RequestBody UserDto.Update req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

