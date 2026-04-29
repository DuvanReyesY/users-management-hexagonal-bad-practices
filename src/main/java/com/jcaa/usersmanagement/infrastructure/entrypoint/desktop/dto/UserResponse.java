package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto;

//se uso record para la clase userResponse lo que la vuelve inmutable y su acceso es por getters automaticos

public record UserResponse(
        String id,
        String name,
        String email,
        String role,
        String status
) {}