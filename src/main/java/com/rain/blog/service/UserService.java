package com.rain.blog.service;

import com.rain.blog.classes.User;

public interface UserService {

    User checkuser(String username, String password);
}
