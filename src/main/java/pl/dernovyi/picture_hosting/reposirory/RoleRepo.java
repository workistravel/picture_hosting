package pl.dernovyi.picture_hosting.reposirory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dernovyi.picture_hosting.model.Role;
@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByName(String nameRole);
}
