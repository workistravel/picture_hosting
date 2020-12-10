package pl.dernovyi.picture_hosting.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.dernovyi.picture_hosting.controller.PictureController;
import pl.dernovyi.picture_hosting.reposirory.RoleRepo;
import pl.dernovyi.picture_hosting.reposirory.TokenRepo;
import pl.dernovyi.picture_hosting.reposirory.UserRepo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
@WebMvcTest(UserDetailsServiceImpl.class)
class UserServiceImplTest {

    @MockBean
    private  RoleRepo roleRepo;
    @MockBean
    private  UserRepo userRepo;
    @MockBean
    private  TokenRepo tokenRepo;
    @MockBean
    private  MailSenderService mailSenderService;


    @Test
    void getPasswordEncoder() {

    }

    @Test
    void save() {
    }

    @Test
    void findByUsername() {
    }

    @Test
    void verifyToken() {
    }

    @Test
    void getAllUsersName() {
    }
}
