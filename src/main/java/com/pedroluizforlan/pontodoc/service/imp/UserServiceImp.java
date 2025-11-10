package com.pedroluizforlan.pontodoc.service.imp;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pedroluizforlan.pontodoc.model.User;
import com.pedroluizforlan.pontodoc.repository.UserRepository;
import com.pedroluizforlan.pontodoc.service.UserService;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    public UserServiceImp(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        var users = userRepository.findAll();
        return users;
    }

    @Override
    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    public User create(User entity) {
        entity.setCreatedAt(LocalDateTime.now());
        return userRepository.save(entity);
    }

    @Override
    public User update(Long id, User entity) {
        User existingUser = findById(id);

        if (entity.getEmail() != null) {
            existingUser.setEmail(entity.getEmail());
        }
        if (entity.getPassword() != null) {
            existingUser.setPassword(entity.getPassword());
        }
        if (entity.getUseType() != null) {
            existingUser.setUseType(entity.getUseType());
        }
        if (entity.isVerifiedEmail()) {
            existingUser.setVerifiedEmail(entity.isVerifiedEmail());
        }

        existingUser.setUploadedAt(LocalDateTime.now());
        return userRepository.save(existingUser);
    }

    @Override
    public User delete(Long id) {
        User user = findById(id);
        user.setDeleatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

}