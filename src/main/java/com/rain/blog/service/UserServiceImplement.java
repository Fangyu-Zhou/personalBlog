package com.rain.blog.service;

import com.rain.blog.classes.User;
import com.rain.blog.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service  /*必须标明是service模块*/
public class UserServiceImplement implements UserService {

    @Autowired /*需要注入*/
    private UserRepository userRepository;


    @Override
    public User checkuser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, password);
        return user;

    }
}
