package com.pedroluizforlan.pontodoc.service.imp;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pedroluizforlan.pontodoc.model.User;
import com.pedroluizforlan.pontodoc.repository.UserRepository;
import com.pedroluizforlan.pontodoc.service.UserService;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    public User update(Long id, User user) {
        User existingUser = findById(id);

        if (user.getEmail() != null && !user.getEmail().equals(existingUser.getEmail())) {
            existingUser.setEmail(user.getEmail());
        }
        if (user.getPassword() != null && !user.getPassword().equals(existingUser.getPassword())) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        if (user.getUseType() != existingUser.getUseType()) {
            existingUser.setUseType(user.getUseType());
        }
        if (user.isVerifiedEmail()) {
            existingUser.setVerifiedEmail(user.isVerifiedEmail());
        }

        existingUser.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(existingUser);
    }

    @Override
    public User delete(Long id) {
        User user = findById(id);
        user.setDeletedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

}