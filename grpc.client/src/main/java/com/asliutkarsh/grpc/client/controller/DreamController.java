package com.asliutkarsh.grpc.client.controller;

import com.asliutkarsh.grpc.client.dto.DreamRequestDto;
import com.asliutkarsh.grpc.client.dto.DreamResponseDto;
import com.asliutkarsh.grpc.dreamlog.protobuf.*;

import net.devh.boot.grpc.client.inject.GrpcClient;

import java.util.List;
import java.util.stream.Collectors;
import com.google.protobuf.Empty;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dreams")
public class DreamController {

    @GrpcClient("dreamlog")
    private DreamServiceGrpc.DreamServiceBlockingStub dreamStub;

    @PostMapping
    public String addDream(@RequestBody DreamRequestDto dto) {
        Dream dream = Dream.newBuilder()
                .setTitle(dto.title())
                .setDescription(dto.description())
                .setDreamDate(dto.dreamDate())
                .setMood(Mood.valueOf(dto.mood()))
                .build();

        DreamId response = dreamStub.addDream(dream);
        return response.getId();
    }


    @GetMapping
    public List<DreamResponseDto> getAllDreams() {
        Empty request = Empty.newBuilder().build();
        DreamList response = dreamStub.listDreams(request);

        return response.getDreamsList().stream()
                .map(dream -> new DreamResponseDto(
                        dream.getId(),
                        dream.getTitle(),
                        dream.getDescription(),
                        dream.getDreamDate(),
                        dream.getMood().name()
                ))
                .collect(Collectors.toList());
    }

}