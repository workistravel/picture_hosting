package pl.dernovyi.picture_hosting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dernovyi.picture_hosting.model.MyUser;
import pl.dernovyi.picture_hosting.model.Picture;
import pl.dernovyi.picture_hosting.reposirory.PictureRepo;
import pl.dernovyi.picture_hosting.reposirory.UserRepo;

import java.net.URI;
import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {

    private final UserRepo userRepo;
    private final PictureRepo pictureRepo;

    @Autowired
    public PictureServiceImpl(UserRepo userRepo, PictureRepo pictureRepo) {
        this.userRepo = userRepo;
        this.pictureRepo = pictureRepo;
    }

    @Override
    public void addPictureToUser(URI uri, String email) {
        MyUser user = userRepo.findByUsername(email);
        Picture picture = new Picture();
        picture.setUrl(uri.toString());
        picture.setUser(user);
        pictureRepo.save(picture);
    }

    public List<Picture> getPictureByEmail(String email){
        List<Picture> allPicture = pictureRepo.findAllByUser(userRepo.findByUsername(email));
        return  allPicture;
    }
}
