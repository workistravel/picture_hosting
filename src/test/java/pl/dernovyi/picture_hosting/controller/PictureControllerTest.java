package pl.dernovyi.picture_hosting.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
class PictureControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    public PictureController pictureController;


    @Test
    public void test() throws Exception {

    }
}
