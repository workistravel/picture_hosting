package pl.dernovyi.picture_hosting.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.dernovyi.picture_hosting.security.SecurityService;
import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class PictureControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    SecurityService securityService;


    @WithMockUser(value = "spring")
    @Test
    void should_user_get_view_status_200() throws Exception{
        given(securityService.isAuthenticated()).willReturn(true);
        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(view().name("user"));

    }

    @Test
    void should_user_get_view_status_301_unauthorized() throws Exception{
        mockMvc.perform(get("/user"))
                .andExpect(status().isUnauthorized());

    }

    @WithMockUser(value = "spring" , authorities = "ROLE_ADMIN" )
    @Test
    public void should_admin_get_view_status_200() throws Exception {
        given(securityService.isAuthenticated()).willReturn(true);
        this.mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin"));;
    }
    @WithMockUser(value = "spring")
    @Test
    public void should_admin_get_view_status_403() throws Exception {
        given(securityService.isAuthenticated()).willReturn(true);
        this.mockMvc.perform(get("/admin"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(value = "spring")
    @Test
    public void should_admin_post_registration_fell() throws Exception {
        this.mockMvc.perform(post("/registration"))
                .andExpect(status().isForbidden());
    }
}
