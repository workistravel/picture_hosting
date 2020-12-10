package pl.dernovyi.picture_hosting.reposirory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dernovyi.picture_hosting.model.MyUser;
import pl.dernovyi.picture_hosting.model.Picture;

import java.util.List;

@Repository
public interface PictureRepo extends JpaRepository<Picture, Long> {
    List<Picture> findAllByUser(MyUser user);
}
