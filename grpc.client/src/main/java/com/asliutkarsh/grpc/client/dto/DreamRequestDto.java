package com.asliutkarsh.grpc.client.dto;

public record DreamRequestDto(
    String title,
    String description,
    String dreamDate,
    String mood
) {}
