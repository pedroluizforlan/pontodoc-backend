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
    public User create(User user) {
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    /*
     * @TODO Atualizar método de update de usuário. Validar quando o dado for == null.
     */
    @Override
    public User update(Long id, User user) {
        User existingUser = findById(id);

        if (user.getEmail() != existingUser.getEmail()) {
            existingUser.setEmail(user.getEmail());
        }
        if (user.getPassword() != existingUser.getPassword()) {
            existingUser.setPassword(user.getPassword());
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
        user.setDeleatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

}