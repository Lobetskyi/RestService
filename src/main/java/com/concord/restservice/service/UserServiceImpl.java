package com.concord.restservice.service;

import com.concord.restservice.model.SearchParameters;
import com.concord.restservice.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Override
    public User findUser(SearchParameters parameters) {
        if (parameters != null && parameters.getId() == 1) {
            User user = new User();
            user.setName("Test Testov");
            return user;
        }
        return null;
    }
}
