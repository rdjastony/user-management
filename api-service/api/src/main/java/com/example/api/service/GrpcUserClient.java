package com.example.api.service;

import com.example.grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import com.example.grpc.UserRequest;
import com.example.grpc.UserResponse;
import com.example.grpc.UserServiceGrpc;


import java.util.List;

@Service
public class GrpcUserClient {

    private UserServiceGrpc.UserServiceBlockingStub stub;

    @PostConstruct
    public void init() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();
        stub = UserServiceGrpc.newBlockingStub(channel);
    }

    public UserResponse addUser(UserRequest request) {
        return stub.addUser(request);
    }

    public BulkUserResponse addUsers(BulkUserRequest request) {
        return stub.addUsers(request);
    }

    public UserResponse getUser(String id) {
        return stub.getUser(UserId.newBuilder().setId(id).build());
    }

    public UserListResponse search(String pattern) {
        return stub.searchUsers(SearchRequest.newBuilder().setPattern(pattern).build());
    }

    public DeleteResponse delete(String id) {
        return stub.deleteUser(UserId.newBuilder().setId(id).build());
    }

    public DeleteResponse bulkDelete(List<String> ids) {
        return stub.bulkDelete(BulkUserIds.newBuilder().addAllIds(ids).build());
    }
}