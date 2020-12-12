package pl.dernovyi.picture_hosting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;
import pl.dernovyi.picture_hosting.security.SecurityService;
import pl.dernovyi.picture_hosting.service.UserServiceImpl;
import pl.dernovyi.picture_hosting.service.UserValidator;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class SecurityControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    UserServiceImpl userService;
    @MockBean
    SecurityService securityService;
    @MockBean
    UserValidator validator;

    @MockBean
    BindingResult bindingResult;
    @Test
    void should_registration_get_view_status_200()throws Exception {
        this.mockMvc.perform(get("/registration"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"))
                .andExpect(content().string(containsString("registration")));
    }
    @Test
    void should_registration_get_view_status_3__()throws Exception {
        given(securityService.isAuthenticated()).willReturn(true);
        this.mockMvc.perform(get("/registration"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }


    @Test
    void should_registration_post_view_status_3__()throws Exception {
        this.mockMvc.perform(post("/registration"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/welcome"));
    }

    @Test
    void should_login_get_view_status_3__() throws Exception {
        given(securityService.isAuthenticated()).willReturn(true);
        this.mockMvc.perform(get("/login"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }
    @Test
    void should_login_get_view_status_200() throws Exception {
        this.mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }
    @Test
    void should_verify_get_status_200() throws Exception {
        String token = "123";
        doNothing().when(userService).verifyToken(token);
        mockMvc.perform(get("/verify-token" )
                .param("token", token)
                .content(new ObjectMapper().writeValueAsString(token))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
    @WithMockUser(value = "spring")
    @Test
    void should_welcome_get_status_200() throws Exception{
        mockMvc.perform(get("/welcome"))
                .andExpect(status().isOk());
    }
}
