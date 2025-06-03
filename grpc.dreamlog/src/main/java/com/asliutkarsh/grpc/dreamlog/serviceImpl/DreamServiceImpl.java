package com.asliutkarsh.grpc.dreamlog.serviceImpl;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import org.springframework.grpc.server.service.GrpcService;
import com.asliutkarsh.grpc.dreamlog.protobuf.Dream;
import com.asliutkarsh.grpc.dreamlog.protobuf.DreamId;
import com.asliutkarsh.grpc.dreamlog.protobuf.DreamList;
import com.asliutkarsh.grpc.dreamlog.protobuf.TagRequest;
import com.asliutkarsh.grpc.dreamlog.protobuf.MoodRequest;
import com.asliutkarsh.grpc.dreamlog.protobuf.DreamServiceGrpc;
import com.google.protobuf.Empty;

@GrpcService
public class DreamServiceImpl extends DreamServiceGrpc.DreamServiceImplBase {

    // In-memory store for dreams
    // In a real application, this would be replaced with a database or persistent storage
    private final Map<String, Dream> dreamStore = new ConcurrentHashMap<>();

    @Override
    public void addDream(Dream request, StreamObserver<DreamId> responseObserver) {
        String id = UUID.randomUUID().toString();
        Dream saved = request.toBuilder().setId(id).build();
        dreamStore.put(id, saved);
        responseObserver.onNext(DreamId.newBuilder().setId(id).build());
        responseObserver.onCompleted();
    }

    @Override
    public void getDream(DreamId request, StreamObserver<Dream> responseObserver){
        Dream dream = dreamStore.get(request.getId());
        if (dream != null) {
        responseObserver.onNext(dream);
        } else {
        responseObserver.onError(Status.NOT_FOUND.withDescription("Dream not found").asRuntimeException());
        }
        responseObserver.onCompleted();
    }

    @Override
    public void listDreams(Empty request, StreamObserver<DreamList> responseObserver) {
        DreamList list = DreamList.newBuilder().addAllDreams(dreamStore.values()).build();
        responseObserver.onNext(list);
        responseObserver.onCompleted();
    }

    @Override
    public void searchByTag(TagRequest request, StreamObserver<DreamList> responseObserver) {
        var filtered = dreamStore.values().stream()
                .filter(d -> d.getTagsList().contains(request.getTag()))
                .toList();
        responseObserver.onNext(DreamList.newBuilder().addAllDreams(filtered).build());
        responseObserver.onCompleted();
    }

    @Override
    public void searchByMood(MoodRequest request, StreamObserver<DreamList> responseObserver) {
        var filtered = dreamStore.values().stream()
                .filter(d -> d.getMood() == request.getMood())
                .toList();
        responseObserver.onNext(DreamList.newBuilder().addAllDreams(filtered).build());
        responseObserver.onCompleted();
    }
}
