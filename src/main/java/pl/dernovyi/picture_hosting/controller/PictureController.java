package pl.dernovyi.picture_hosting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.dernovyi.picture_hosting.security.SecurityService;
import pl.dernovyi.picture_hosting.service.StorageService;
import pl.dernovyi.picture_hosting.service.PictureService;
import pl.dernovyi.picture_hosting.service.UserService;

import java.net.URI;
import java.security.Principal;


@Controller
public class PictureController {

    private  StorageService storageService;
    private  UserService userService;
    private  SecurityService securityService;
    private  PictureService pictureService;

    @Autowired
    public PictureController(StorageService storageService, UserService userService, SecurityService securityService, PictureService pictureService) {
        this.storageService = storageService;
        this.userService = userService;
        this.securityService = securityService;
        this.pictureService = pictureService;
    }

    @GetMapping("/user")
    public String user(Principal principal, Model model) {
        if (securityService.isAuthenticated()) {
            model.addAttribute("name", principal.getName());
            model.addAttribute("pictures", pictureService.getPictureByEmail(principal.getName()));
            return "user";
        }
        return "login";
    }
    @RequestMapping(value = "/admin",  method = RequestMethod.GET)
    public String admin(Model model) {
        if (securityService.isAuthenticated()) {
            model.addAttribute("names", userService.getAllUsersName());
            return "admin";

        }
        return "login";
    }

    @RequestMapping(value="admin",  method = RequestMethod.POST)
    public String admin(@RequestParam("image") MultipartFile multipartFile, @RequestParam("name") String name) {
        if (securityService.isAuthenticated()) {
            URI uri = storageService.saveToStorage(multipartFile);
            pictureService.addPictureToUser(uri, name);
            return "redirect:/admin";
        }
        return "redirect:/";
    }
    @GetMapping("/all")
    public  String getAll() {
        return "available to all";
    }
}
