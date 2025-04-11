package com.example.user.grpc;

import com.example.grpc.*;
import com.example.user.model.User;

import com.example.user.service.UserServiceImpl;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;
import java.util.stream.Collectors;

@GrpcService
@RequiredArgsConstructor
public class UserServiceGrpcImpl extends UserServiceGrpc.UserServiceImplBase {

    private final UserServiceImpl service;

    private UserResponse mapToResponse(User user) {
        return UserResponse.newBuilder()
                .setId(user.getId())
                .setName(user.getName())
                .setEmail(user.getEmail())
                .setPhone(user.getPhone())
                .build();
    }

    @Override
    public void addUser(UserRequest request, StreamObserver<UserResponse> responseObserver) {
        User user = User.builder()
                .id(request.getId())
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .build();
        User saved = service.save(user);
        responseObserver.onNext(mapToResponse(saved));
        responseObserver.onCompleted();
    }

    @Override
    public void addUsers(BulkUserRequest request, StreamObserver<BulkUserResponse> responseObserver) {
        List<User> users = request.getUsersList().stream()
                .map(u -> User.builder()
                        .id(u.getId())
                        .name(u.getName())
                        .email(u.getEmail())
                        .phone(u.getPhone())
                        .build())
                .collect(Collectors.toList());

        List<User> saved = service.saveAll(users);



        BulkUserResponse response = BulkUserResponse.newBuilder()
                .addAllUsers(saved.stream().map(this::mapToResponse).collect(Collectors.toList()))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

    @Override
    public void getUser(UserId request, StreamObserver<UserResponse> responseObserver) {
        User user = service.findById(request.getId());
        if (user != null) {
            responseObserver.onNext(mapToResponse(user));
        }
        responseObserver.onCompleted();
    }

    @Override
    public void searchUsers(SearchRequest request, StreamObserver<UserListResponse> responseObserver) {
        List<User> matched = service.findByPrefix(request.getPattern());
        UserListResponse response = UserListResponse.newBuilder()
                .addAllUsers(matched.stream().map(this::mapToResponse).collect(Collectors.toList()))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteUser(UserId request, StreamObserver<DeleteResponse> responseObserver) {
        service.deleteById(request.getId());
        responseObserver.onNext(DeleteResponse.newBuilder().setStatus("Deleted").build());
        responseObserver.onCompleted();
    }

    @Override
    public void bulkDelete(BulkUserIds request, StreamObserver<DeleteResponse> responseObserver) {
        service.deleteByIds(request.getIdsList());
        responseObserver.onNext(DeleteResponse.newBuilder().setStatus("Bulk Deleted").build());
        responseObserver.onCompleted();
    }


}