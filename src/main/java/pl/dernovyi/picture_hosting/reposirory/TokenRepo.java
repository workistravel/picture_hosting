package pl.dernovyi.picture_hosting.reposirory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dernovyi.picture_hosting.model.VerificationToken;
@Repository
public interface TokenRepo extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByValue(String value);
}
