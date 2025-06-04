package com.asliutkarsh.grpc.stream.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.grpc.server.service.GrpcService;

import com.asliutkarsh.grpc.stream.proto.fitness.*;

import io.grpc.stub.StreamObserver;

@GrpcService
public class FitnessTrackerService extends FitnessTrackerServiceGrpc.FitnessTrackerServiceImplBase {

    @Override
    public StreamObserver<FitnessData> streamFitnessData(StreamObserver<FitnessSummary> responseObserver) {
        List<FitnessData> logs = new ArrayList<>();
        AtomicInteger count = new AtomicInteger(0);
        AtomicInteger totalSteps = new AtomicInteger(0);
        AtomicLong totalCalories = new AtomicLong(0);
        AtomicInteger totalHeartRate = new AtomicInteger(0);
        final String[] userIdHolder = new String[1]; // To hold userId from stream

        StreamObserver<FitnessData> response = new StreamObserver<FitnessData>() {
            @Override
            public void onNext(FitnessData value) {
                System.out.println("Received fitness data: " + value);
                if (userIdHolder[0] == null) {
                    userIdHolder[0] = value.getUserId();
                }
                count.incrementAndGet();
                totalSteps.addAndGet(value.getSteps());
                totalCalories.addAndGet((long) (value.getCaloriesBurned() * 1000));
                totalHeartRate.addAndGet(value.getHeartRate());
                logs.add(value);
            }

            @Override
            public void onError(Throwable t) {
                // Handle error
                responseObserver.onError(t);
            }

            @Override
            public void onCompleted() {
                // Create a summary of the received fitness data
                int avgHeartRate = count.get() > 0 ? totalHeartRate.get() / count.get() : 0;
                double totalCaloriesDouble = totalCalories.get() / 1000.0;
                FitnessSummary summary = FitnessSummary.newBuilder()
                        .setUserId(userIdHolder[0] == null ? "" : userIdHolder[0])
                        .setTotalSteps(totalSteps.get())
                        .setTotalCalories(totalCaloriesDouble)
                        .setAverageHeartRate(avgHeartRate)
                        .build();

                responseObserver.onNext(summary);
                System.out.println("Sending summary: " + summary);
                responseObserver.onCompleted();
            }
        };

        return response;
    }

}
