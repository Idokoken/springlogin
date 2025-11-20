package ndgroups.springLogin.services;

import lombok.RequiredArgsConstructor;
import ndgroups.springLogin.config.UserAlreadyExistException;
import ndgroups.springLogin.model.RegistrationRequest;
import ndgroups.springLogin.model.User;
import ndgroups.springLogin.registration.token.VerificationToken;
import ndgroups.springLogin.repository.UserRepository;
import ndgroups.springLogin.repository.VerificationTokenRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository tokenRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User registerUser(RegistrationRequest request) {
        Optional<User> user = userRepository.findByEmail(request.email());
        if(user.isPresent()){
            throw new UserAlreadyExistException(
                    "user with email address " + request.email() + " already exist");
        }
        var newUser =  new User();
        newUser.setFirstName(request.firstName());
        newUser.setLastName(request.lastName());
        newUser.setEmail(request.email());
        newUser.setPassword(passwordEncoder.encode(request.password()));
        newUser.setRole(request.role());
        return userRepository.save(newUser);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveUserVerificationToken(User user, String token) {
        var verificationToken = new VerificationToken(token, user);
        tokenRepository.save(verificationToken);
    }
    @Override
    public String validateToken(String theToken) {
        VerificationToken token  = tokenRepository.findByToken(theToken);
        if(token  == null) {
            return "Invalid verification token";
        }
        User user = token.getUser();
        Calendar calendar = Calendar.getInstance();
        if((token.getTokenExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
            tokenRepository.delete(token);
            return "token already expired";
        }
        user.setEnabled(true);
        userRepository.save(user);
        return "valid";
    }


}
