package com.asliutkarsh.grpc.client.dto;

public record DreamResponseDto(
    String id,
    String title,
    String description,
    String dreamDate,
    String mood
) {}
