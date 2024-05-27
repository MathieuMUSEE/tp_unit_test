package com.example.tp_unit_test.service;

import com.example.tp_unit_test.exception.DataClownException;
import com.example.tp_unit_test.model.User;
import com.example.tp_unit_test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    public UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    public User saveUser(User user) {
        if (!user.getEmail().contains("@")) {
            throw new DataClownException("Wrong email format");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new DataClownException("Email already exists");
        }
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user){
        if(userRepository.findById(id).isPresent()){
            user.setId(id);
            return userRepository.save(user);
        }else{
            throw new DataClownException("User not found");
        }

    }

    public void deleteUserById(Long id){
        if(userRepository.findById(id).isPresent()){
            userRepository.deleteById(id);
        }else{
            throw new DataClownException("User not found");
        }

    }


}
