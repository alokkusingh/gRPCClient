package com.alok.grpc;

import com.alok.grpc.stub.AbcDataServiceGrpc;
import com.alok.grpc.stub.AbcDataServiceOuterClass;
import com.alok.grpc.stub.AbcDataServiceOuterClass.AbcDataRequest;
import com.alok.grpc.stub.AbcDataServiceOuterClass.AbcDataResponse;
import com.alok.grpc.stub.UserGrpc;
import com.alok.grpc.stub.UserOuterClass.LoginRequest;
import com.alok.grpc.stub.UserOuterClass.APIResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.time.YearMonth;
import java.util.Iterator;
import java.util.UUID;

public class GrpcClient {

    public static void main(String[] args) {

        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 9090).usePlaintext().build();

        login(channel);

        getAbcData(channel);
    }

    public static void login(ManagedChannel channel) {
        LoginRequest loginRequest = LoginRequest.newBuilder()
                .setUsername("Alok")
                .setPassword("Alok")
                .build();

        UserGrpc.UserBlockingStub stub = UserGrpc.newBlockingStub(channel);
        APIResponse loginResponse = stub.login(loginRequest);
        System.out.println(loginResponse);
    }

    public static void getAbcData(ManagedChannel channel) {

        AbcDataRequest request = AbcDataRequest.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setMonth(YearMonth.now().toString())
                .build();


        AbcDataServiceGrpc.AbcDataServiceBlockingStub blockingStub = AbcDataServiceGrpc.newBlockingStub(channel);
        Iterator<AbcDataResponse> abcDataItr;
        try {
            System.out.println("REQUEST::getAbcData - " + request);
            abcDataItr = blockingStub.getAbcData(request);
            for (int i = 1; abcDataItr.hasNext(); i++) {
                AbcDataResponse abcData = abcDataItr.next();
                System.out.println("RESPONSE::getAbcData - #" + i + ": " +  abcData);
            }
        } catch (StatusRuntimeException e) {
            System.out.println("RPC failed: " +  e.getStatus());
        }
    }
}
