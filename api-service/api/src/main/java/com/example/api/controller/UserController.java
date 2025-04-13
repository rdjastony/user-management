package com.example.api.controller;

import com.example.api.dto.UserDTO;
import com.example.api.service.GrpcUserClient;
import com.example.grpc.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.grpc.UserServiceGrpc;
import com.example.grpc.UserRequest;
import com.example.grpc.UserResponse;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final GrpcUserClient grpcClient;

    @PostMapping
    public Object addUsers(@RequestBody List<UserDTO> requests) {
        try {
            if (requests.size() == 1) {
                UserRequest grpcReq = UserRequest.newBuilder()
                        .setId(requests.get(0).getId())
                        .setName(requests.get(0).getName())
                        .setEmail(requests.get(0).getEmail())
                        .setPhone(requests.get(0).getPhone())
                        .build();
                UserResponse response = grpcClient.addUser(grpcReq);
                return new UserDTO(response.getId(), response.getName(), response.getEmail(), response.getPhone());
            } else {
                List<UserRequest> grpcRequests = requests.stream().map(dto ->
                        UserRequest.newBuilder()
                                .setId(dto.getId())
                                .setName(dto.getName())
                                .setEmail(dto.getEmail())
                                .setPhone(dto.getPhone())
                                .build()
                ).toList();

                BulkUserResponse response = grpcClient.addUsers(
                        BulkUserRequest.newBuilder().addAllUsers(grpcRequests).build()
                );

                return response.getUsersList().stream().map(user ->
                        new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getPhone())
                ).toList();
            }
        } catch (Exception e) {
            e.printStackTrace(); // 👈 Just for now, to debug
            return Map.of("error", "Internal error: " + e.getMessage());
        }
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }


    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable String id) {
        UserResponse proto = grpcClient.getUser(id);
        return new UserDTO(proto.getId(), proto.getName(), proto.getEmail(), proto.getPhone());
    }


    @GetMapping("/search")
    public List<UserDTO> search(@RequestParam String pattern) {
        UserListResponse protoList = grpcClient.search(pattern);
        return protoList.getUsersList().stream()
                .map(proto -> new UserDTO(proto.getId(), proto.getName(), proto.getEmail(), proto.getPhone()))
                .toList();
    }


    @DeleteMapping("/{id}")
    public Map<String, String> deleteUser(@PathVariable String id) {
        DeleteResponse response = grpcClient.delete(id);
        return Map.of("status", response.getStatus());
    }


    @DeleteMapping("/bulk")
    public Map<String, String> bulkDelete(@RequestBody List<String> ids) {
        DeleteResponse response = grpcClient.bulkDelete(ids);
        return Map.of("status", response.getStatus());
    }

}


