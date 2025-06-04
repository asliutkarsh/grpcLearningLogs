# Streaming with Grpc

This repository contains a simple example of how to implement streaming with gRPC in Java Spring Boot. The example demonstrates both server-side and client-side streaming.

## Server-Side Streaming

Refer to `src\main\java\com\asliutkarsh\grpc\stream\service\ReciepeStreamer.java` for the server-side streaming implementation. The server streams a list of recipes to the client.

## Client-Side Streaming

Refer to `src\main\java\com\asliutkarsh\grpc\stream\service\FitnessTrackerService.java` for the client-side streaming implementation. The client sends a stream of fitness data to the server. and the server responds with a summary of the data received.

## Bi-Directional Streaming

Refer to `src\main\java\com\asliutkarsh\grpc\stream\service\RealTimeTranslation.java` for the bi-directional streaming implementation. This service allows both the client and server to send and receive messages in real-time.
