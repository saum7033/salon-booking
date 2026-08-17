package com.zosh.user.service.service;

import com.zosh.user.service.exception.UserException;
import com.zosh.user.service.model.User;

import java.util.List;

public interface UserService {

    User createUser(User user);
    User getUserById(Long id) throws UserException;
    List<User> getAllUsers();
    void deleteUser(Long id) throws Exception;
    User updateUser(Long id, User user) throws UserException;

}
