package pl.dernovyi.picture_hosting.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.dernovyi.picture_hosting.model.MyUser;
import pl.dernovyi.picture_hosting.model.Role;
import pl.dernovyi.picture_hosting.reposirory.RoleRepo;
import pl.dernovyi.picture_hosting.reposirory.UserRepo;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.*;
@SpringBootTest
@AutoConfigureMockMvc
class UserServiceImplTest {


    @Spy
    @InjectMocks
    UserServiceImpl userService;
    @MockBean
    UserRepo userRepo;
    @MockBean
    RoleRepo roleRepo;
    @MockBean
    HttpServletRequest http;
    @MockBean
    MailSenderService mail;
    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    void should_save_new_admin() {
        MyUser user = getTestUser();
        user.setTransRole("ADMIN");
        Mockito.when(passwordEncoder.encode(user.getPassword())).thenReturn("321");
        Mockito.when(roleRepo.findByName("ROLE_ADMIN")).thenReturn(Mockito.any(Role.class));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.addRoles(roleRepo.findByName("ROLE_ADMIN"));
        doNothing().when(userService).save(Mockito.any(MyUser.class), Mockito.any(HttpServletRequest.class));
        userService.save(user ,http);

        Mockito.verify(userService,  Mockito.after(1000).times(1)).save(user, http);
    }
    @Test
    void should_save_new_user() {
        MyUser user = getTestUser();
        user.setTransRole("USER");
        Mockito.when(passwordEncoder.encode(user.getPassword())).thenReturn("321");
        Mockito.when(roleRepo.findByName("ROLE_USER")).thenReturn(Mockito.any(Role.class));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.addRoles(roleRepo.findByName("ROLE_USER"));
        doNothing().when(userService).save(Mockito.any(MyUser.class), Mockito.any(HttpServletRequest.class));
        userService.save(user ,http);

        Mockito.verify(userService,  Mockito.after(1000).times(1)).save(user, http);
    }

    MyUser getTestUser(){
        MyUser user = new MyUser();
        user.setId(1L);
        user.setPassword("123");
        user.setUsername("test@test.com");
        user.setEnabled(true);
        return user;
    }
}
