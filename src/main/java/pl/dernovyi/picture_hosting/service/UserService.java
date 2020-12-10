package pl.dernovyi.picture_hosting.service;

import pl.dernovyi.picture_hosting.model.MyUser;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

public interface UserService {
    void save(MyUser user, HttpServletRequest http);

    MyUser findByUsername(String username);

    void verifyToken(String token);
    List<String> getAllUsersName();


}
