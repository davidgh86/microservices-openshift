package com.david.eureka;

public interface UserService {
    User registerUser(User input);
    Iterable<User> findAll();
}