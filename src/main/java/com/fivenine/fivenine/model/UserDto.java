package com.fivenine.fivenine.model;

import java.time.LocalDateTime;

public class UserDto {
    public record Create(String name, String email) {}
    public record Update(String name, String email) {}
    public record View(Long id, String name, String email, LocalDateTime createdAt) {}
}

