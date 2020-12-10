package pl.dernovyi.picture_hosting.reposirory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.dernovyi.picture_hosting.model.MyUser;
import pl.dernovyi.picture_hosting.model.Role;

import java.util.Collection;
import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<MyUser, Long> {
   MyUser findByUsername(String username);
   List<MyUser> findAll();

   List<MyUser> findByRoles(Role role);
}
