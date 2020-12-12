package pl.dernovyi.picture_hosting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.dernovyi.picture_hosting.model.MyUser;
import pl.dernovyi.picture_hosting.security.SecurityService;
import pl.dernovyi.picture_hosting.service.UserService;
import pl.dernovyi.picture_hosting.service.UserValidator;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SecurityController {
    private final UserService userService;
    private final SecurityService securityService;
    private final UserValidator userValidator;

    @Autowired
    public SecurityController(UserService userService, SecurityService securityService, UserValidator userValidator) {
        this.userService = userService;
        this.securityService = securityService;
        this.userValidator = userValidator;
    }

    @RequestMapping(value = {"/", "/welcome"},  method = RequestMethod.GET)
    public String welcome() {
        return "welcome";
    }

    @RequestMapping(value = "/registration",  method = RequestMethod.GET)
    public String registration(Model model) {
        if (securityService.isAuthenticated()) {
            return "redirect:/";
        }
        model.addAttribute("userForm", new MyUser());
        return "registration";
    }

    @RequestMapping(value = "/registration",  method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") MyUser userForm, BindingResult bindingResult, HttpServletRequest request) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm, request);
        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());
        return "redirect:/welcome";
    }

    @RequestMapping(value = "/login",  method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {

        if (securityService.isAuthenticated()) {
            return "redirect:/";
        }
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }
    @RequestMapping(value = "/verify-token",  method = RequestMethod.GET)
    public String verify(@RequestParam String token){
        userService.verifyToken(token);
        return "login";
    }
}
