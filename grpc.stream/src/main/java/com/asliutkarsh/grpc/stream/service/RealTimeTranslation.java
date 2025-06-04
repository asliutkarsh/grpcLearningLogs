package com.asliutkarsh.grpc.stream.service;

import java.time.Instant;

import org.springframework.grpc.server.service.GrpcService;

import com.asliutkarsh.grpc.stream.proto.translation.RealTimeTranslationServiceGrpc.RealTimeTranslationServiceImplBase;
import com.asliutkarsh.grpc.stream.proto.translation.TranslationRequest;
import com.asliutkarsh.grpc.stream.proto.translation.TranslationResponse;

import io.grpc.stub.StreamObserver;

@GrpcService
public class RealTimeTranslation extends RealTimeTranslationServiceImplBase {

    @Override
    public StreamObserver<TranslationRequest> translate(StreamObserver<TranslationResponse> responseObserver) {
        return new StreamObserver<>() {

            @Override
            public void onNext(TranslationRequest request) {
                String translatedText = mockTranslate(request.getText(), request.getSourceLang(), request.getTargetLang());

                TranslationResponse response = TranslationResponse.newBuilder()
                        .setTranslatedText(translatedText)
                        .setTargetLang(request.getTargetLang())
                        .setTimestamp(Instant.now().toEpochMilli())
                        .build();

                responseObserver.onNext(response);
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Error in translation stream: " + t.getMessage());
                responseObserver.onCompleted();
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }

            // Dummy translation logic
            private String mockTranslate(String text, String sourceLang, String targetLang) {
                return "[" + targetLang.toUpperCase() + "] " + text; // Just tag the text
            }
        };
    }
}
