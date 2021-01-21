package com.concord.restservice.service;

import com.concord.restservice.model.SearchParameters;
import com.concord.restservice.model.User;

public interface UserService {

    User findUser(SearchParameters parameters);

}
