package az.javafx.service;

import az.javafx.model.Credential;
import az.javafx.model.User;

import java.net.CacheRequest;

public interface RegistrationService {
    boolean addUser(User user);

}
