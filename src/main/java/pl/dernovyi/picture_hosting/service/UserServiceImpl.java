package pl.dernovyi.picture_hosting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.dernovyi.picture_hosting.model.MyUser;
import pl.dernovyi.picture_hosting.model.Picture;
import pl.dernovyi.picture_hosting.model.Role;
import pl.dernovyi.picture_hosting.model.VerificationToken;
import pl.dernovyi.picture_hosting.reposirory.PictureRepo;
import pl.dernovyi.picture_hosting.reposirory.RoleRepo;
import pl.dernovyi.picture_hosting.reposirory.TokenRepo;
import pl.dernovyi.picture_hosting.reposirory.UserRepo;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Configuration
public class UserServiceImpl implements UserService{

    private final RoleRepo roleRepo;
    private final UserRepo userRepo;
    private final TokenRepo tokenRepo;
    private final MailSenderService mailSenderService;

    @Autowired
    public UserServiceImpl(RoleRepo roleRepo, UserRepo userRepo, TokenRepo tokenRepo, MailSenderService mailSenderService) {
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
        this.tokenRepo = tokenRepo;
        this.mailSenderService = mailSenderService;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    public void save(MyUser user, HttpServletRequest request) {

        if(user.getTransRole().equals("USER")){
            user.setPassword(getPasswordEncoder().encode(user.getPassword()));
            user.addRoles(roleRepo.findByName("ROLE_USER"));
            user.setEnabled(true);
            userRepo.save(user);
        }else {
            user.setPassword(getPasswordEncoder().encode(user.getPassword()));
            user.addRoles(roleRepo.findByName("ROLE_ADMIN"));
            user.setEnabled(false);
            userRepo.save(user);
            mailSender(user, request);
        }
    }

    @Override
    public MyUser findByUsername(String username) {
        return userRepo.findByUsername(username);
    }


    @Override
    public void verifyToken(String token) {
        VerificationToken verificationToken = tokenRepo.findByValue(token);

        LocalDateTime time = LocalDateTime.now();
        LocalDateTime createTime = verificationToken.getDate().plusMinutes(5);
        MyUser myUser = verificationToken.getMyUser();
        if(time.isAfter(createTime)){
            tokenRepo.delete(verificationToken);
        }else{
            myUser.setEnabled(true);
            tokenRepo.delete(verificationToken);
            userRepo.save(myUser);
        }

    }

    @Override
    public List<String> getAllUsersName() {
        List<MyUser> listUsers = userRepo.findByRoles(roleRepo.findByName("ROLE_USER"));
        return  listUsers.stream()
                .map(MyUser::getUsername)
                .collect(Collectors.toList());
    }




    private void mailSender(MyUser user, HttpServletRequest request) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(user, token);
        tokenRepo.save(verificationToken);
        String url = "http://" + request.getServerName() +
                ":" +
                request.getServerPort()+
                request.getContextPath() +
                "/verify-token?token="+ token;
        try {
            mailSenderService.sendMail(user.getUsername(), "VerificationToken", url  , false);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
