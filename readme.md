# gRPC Learning Log

Lets use Java, Maven, and Spring Boot. The goal is to understand the fundamentals of Protocol Buffers (`.proto` files), gRPC service definitions, code generation, and service implementation in a microservice-style architecture.

---

## 📘 What I'm Learning

### Core Concepts of gRPC
- What gRPC is and how it works over HTTP/2.
- Understanding client-server communication with **rpc** methods.
- gRPC message contracts using **Protocol Buffers (proto3)**.

### Working with .proto Files
- Syntax and structure of `.proto` files.
- Defining:
  - Messages (e.g., `Dream`, `DreamId`)
  - Enums (e.g., `Mood`)
  - Services with rpc methods
- Marking fields as **required (optional/mandatory)** by API design.

### Generating Java Code from Protobuf
- Using `protobuf-maven-plugin` and `protoc` to generate Java classes.
- Integration of generated code with Spring Boot.
- Managing `google.protobuf.Empty` and importing standard proto files.

### Service Implementation
- Implementing `@GrpcService` interfaces.
- Handling input/output via generated proto classes.
- Structuring basic in-memory logic before adding databases.

---

## Current Project – `DreamLog`

A basic service to:
- Add a dream
- Get a dream by ID
- List all dreams

This is a toy example to **simulate real microservice communication patterns** and build confidence with protobuf-based API design.

---

## Challenges Faced

- Debugging missing `google.protobuf.Empty` dependency
- Understanding `java_package`, `java_outer_classname` options
- Making sure `protoc` generates the correct structure inside `target/`
- Handling VS Code not resolving generated classes

## VS code fixes

 To resolve issues with VS Code not recognizing generated classes, I added the following configuration to my `settings.json` file:

 ```json
 {
    "java.project.sourcePaths": [
        "src/main/java",
        "target/generated-sources/protobuf/java",
        "target/generated-sources/protobuf/grpc-java"
    ]
}
```

---

## Achievements

- Successfully compiled `.proto` file with `import "google/protobuf/empty.proto"`
- Java code generated using Maven
- gRPC service implemented and running with Spring Boot
- First successful gRPC request-response cycle using in-memory storage

---
---

## 🧠 Summary

This project helped solidify my understanding of **API-first design using gRPC and protobufs**. It lays the foundation for more complex, scalable microservices using **type-safe**, fast, and contract-driven APIs.
