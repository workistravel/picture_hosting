package pl.dernovyi.picture_hosting.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.dernovyi.picture_hosting.model.MyUser;
import pl.dernovyi.picture_hosting.model.Picture;
import pl.dernovyi.picture_hosting.reposirory.PictureRepo;
import pl.dernovyi.picture_hosting.reposirory.UserRepo;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@SpringBootTest
class PictureServiceImplTest {

    @Mock
    PictureService pictureService;
    @Mock
    PictureRepo pictureRepo;
    @Mock
    UserRepo userRepo;


    @Test
    void should_add_picture_to_user() {
        MyUser user = getTestUser();
        when(userRepo.findByUsername(Mockito.any(String.class))).thenReturn(user);
        Picture picture = getTestPicture();
        picture.setUser(user);
        when(pictureRepo.save(picture)).thenReturn(picture);
        Picture pic = pictureRepo.save(picture);
        Assert.assertEquals(pic, picture);
        Mockito.verify(pictureRepo, times(1)).save(picture);
    }

    @Test
    void should_get_picture_by_email() {
        MyUser user = getTestUser();
        Picture picture = getTestPicture();
        List<Picture> list = new ArrayList<>();
        list.add(picture);
        when(userRepo.findByUsername(Mockito.any(String.class))).thenReturn(user);
        when(pictureRepo.findAllByUser(user)).thenReturn(list);
        MyUser byUsername = userRepo.findByUsername("123");
        List<Picture> allByUser = pictureRepo.findAllByUser(byUsername);
        Assert.assertEquals(allByUser, list );
    }

    MyUser getTestUser(){
        MyUser user = new MyUser();
        user.setId(1L);
        user.setPassword("123");
        user.setUsername("test@test.com");
        user.setEnabled(true);
        return user;
    }
    Picture getTestPicture(){
        Picture picture = new Picture();
        picture.setUrl("http://picture");
        return picture;
    }
}
