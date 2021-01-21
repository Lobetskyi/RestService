package com.concord.restservice;

import com.concord.restservice.encryptor.Aes256Encryptor;
import com.concord.restservice.model.SearchParameters;
import com.concord.restservice.model.User;
import com.concord.restservice.service.UserService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {

    private static final Logger LOG = Logger.getLogger(SearchController.class);

    private UserService userService;

    @Autowired
    public SearchController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/findUser", consumes = "application/json", produces = "application/json")
    public User findUser(@RequestBody SearchParameters parameters) {
        LOG.info("[SearchController::findUser] - Request: " + parameters);
        User user = userService.findUser(parameters);
        LOG.info("[SearchController::findUser] - Response: " + user);
        encryptAndDecryptData(parameters, user);
        return user;
    }

    private void encryptAndDecryptData(SearchParameters parameters, User user) {
        String encryptedRequest = Aes256Encryptor.encrypt(parameters.toString());
        LOG.info("[SearchController::encryptAndDecryptData] Encrypted request: " + encryptedRequest
                     + " ; Decrypted request: " + Aes256Encryptor.decrypt(encryptedRequest));

        String encryptedResponse = Aes256Encryptor.encrypt(user.toString());
        LOG.info("[SearchController::encryptAndDecryptData] Encrypted request: " + encryptedResponse
                     + " ; Decrypted request: " + Aes256Encryptor.decrypt(encryptedResponse));

    }
}
