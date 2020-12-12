package pl.dernovyi.picture_hosting.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.context.junit4.SpringRunner;
import pl.dernovyi.picture_hosting.model.MyUser;
import pl.dernovyi.picture_hosting.model.Role;
import pl.dernovyi.picture_hosting.model.VerificationToken;
import pl.dernovyi.picture_hosting.reposirory.TokenRepo;
import pl.dernovyi.picture_hosting.reposirory.UserRepo;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTest {
    @MockBean
    UserRepo userRepo;
    @MockBean
    UserServiceImpl userService;
    @MockBean
    TokenRepo tokenRepo;

    @Test
    public void should_find_user_by_username(){
        MyUser user =  getTestUser();
        when(userService.findByUsername(Mockito.any(String.class))).thenReturn(user);
        MyUser byUsername = userService.findByUsername("123");
        Assert.assertEquals(byUsername, user);

    }
    @Test
    public void should_verify_token(){
        VerificationToken token = getToken();
        when(tokenRepo.findByValue(Mockito.any(String.class))).thenReturn(token);
        VerificationToken getToken = tokenRepo.findByValue("get");
        Assert.assertEquals(getToken, token);
        doNothing().when(userService).verifyToken("get");

    }

    @Test
    public void should_get_all_users_names(){
        List<MyUser> list = new ArrayList<>();
        list.add(getTestUser());
        list.add(getTestUser());
        List<String> listNames = new ArrayList<>();
        listNames.add("name_first");
        listNames.add("name_second");
        when(userRepo.findByRoles(Mockito.any(Role.class))).thenReturn(list);
        when(userService.getAllUsersName()).thenReturn(listNames);

        List<String> listStr = userService.getAllUsersName();
        Assert.assertEquals(listStr.size(), list.size());
    }
    MyUser getTestUser(){
        MyUser user = new MyUser();
        user.setId(1L);
        user.setPassword("123");
        user.setUsername("test@test.com");
        user.setEnabled(true);
        return user;
    }
    VerificationToken getToken(){
        VerificationToken token = new VerificationToken();
        token.setId(1L);
        token.setValue("123");
        return token;
    }

}
