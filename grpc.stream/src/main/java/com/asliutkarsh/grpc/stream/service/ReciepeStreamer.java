package com.asliutkarsh.grpc.stream.service;

import java.util.List;
import java.util.Map;

import org.springframework.grpc.server.service.GrpcService;

import com.asliutkarsh.grpc.stream.proto.recipe.RecipeRequest;
import com.asliutkarsh.grpc.stream.proto.recipe.RecipeStep;
import com.asliutkarsh.grpc.stream.proto.recipe.RecipeStreamerServiceGrpc.RecipeStreamerServiceImplBase;

import io.grpc.stub.StreamObserver;

@GrpcService
public class ReciepeStreamer extends RecipeStreamerServiceImplBase {

     private static final Map<String, List<RecipeStep>> recipes = Map.of(
        "pasta", List.of(
            RecipeStep.newBuilder().setStepNumber(1).setInstruction("Boil water").setDurationInSeconds(300).build(),
            RecipeStep.newBuilder().setStepNumber(2).setInstruction("Add pasta").setDurationInSeconds(600).build(),
            RecipeStep.newBuilder().setStepNumber(3).setInstruction("Drain and serve").setDurationInSeconds(120).build()
        ),
        "omelette", List.of(
            RecipeStep.newBuilder().setStepNumber(1).setInstruction("Crack eggs into bowl").setDurationInSeconds(60).build(),
            RecipeStep.newBuilder().setStepNumber(2).setInstruction("Whisk and season").setDurationInSeconds(90).build(),
            RecipeStep.newBuilder().setStepNumber(3).setInstruction("Cook in pan").setDurationInSeconds(180).build()
        )
    );

    @Override
    public void getRecipeSteps(RecipeRequest request, StreamObserver<RecipeStep> responseObserver) {
        String recipeName = request.getRecipeName().toLowerCase();
        List<RecipeStep> steps = recipes.getOrDefault(recipeName, List.of(
            RecipeStep.newBuilder().setStepNumber(1).setInstruction("Recipe not found").setDurationInSeconds(0).build()
        ));

        for (RecipeStep step : steps) {
            responseObserver.onNext(step);
            try {
                Thread.sleep(step.getDurationInSeconds() * 10L); // Simulate step delay (scaled down)
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        responseObserver.onCompleted();

    }
    
}
