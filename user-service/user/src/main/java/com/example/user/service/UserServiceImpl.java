package com.example.user.service;

import com.example.user.model.User;
import com.example.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {
    private final UserRepository repository;

    public User save(User user) {
        return repository.save(user);
    }

    public List<User> saveAll(List<User> users) {
        return repository.saveAll(users);
    }

    public User findById(String id) {
        return repository.findById(id).orElse(null);
    }

    public List<User> findByPrefix(String prefix) {
        return repository.findByNameStartingWith(prefix);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }

    public void deleteByIds(List<String> ids) {
        ids.forEach(repository::deleteById);
    }
}