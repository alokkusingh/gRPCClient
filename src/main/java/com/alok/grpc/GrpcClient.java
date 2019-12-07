package com.alok.grpc;

import com.alok.grpc.stub.UserGrpc;
import com.alok.grpc.stub.UserOuterClass;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GrpcClient {

    public static void main(String[] args) {

        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 9090).usePlaintext().build();

        UserGrpc.UserBlockingStub stub = UserGrpc.newBlockingStub(channel);

        UserOuterClass.LoginRequest loginRequest = UserOuterClass.LoginRequest.newBuilder()
                .setUsername("Alok")
                .setPassword("Alok")
                .build();

        UserOuterClass.APIResponse loginResponse = stub.login(loginRequest);
        System.out.println(loginResponse);
    }
}
