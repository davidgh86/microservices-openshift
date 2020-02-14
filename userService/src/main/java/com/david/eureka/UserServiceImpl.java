package com.david.eureka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService{

    @Value("${spring.kafka.topic.userCreated}")
    private String USER_CREATED_TOPIC;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Sender sender;

    @Override
    public User registerUser(User input) {
        if (!isValid(input.getUsername())) {
            throw new IllegalArgumentException("Username must be an email");
        }
        User createdUser = userRepository.save(input);
        sender.send(USER_CREATED_TOPIC, createdUser);
        return createdUser;
    }

    public boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }
}
