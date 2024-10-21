package ndgroups.springLogin.repository;

import ndgroups.springLogin.registration.token.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    public VerificationToken findByToken(String token);
}
