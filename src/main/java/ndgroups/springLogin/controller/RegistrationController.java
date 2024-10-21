package ndgroups.springLogin.controller;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import ndgroups.springLogin.events.RegistrationCompleteEvent;
import ndgroups.springLogin.model.RegistrationRequest;
import ndgroups.springLogin.model.User;
import ndgroups.springLogin.registration.token.VerificationToken;
import ndgroups.springLogin.repository.VerificationTokenRepository;
import ndgroups.springLogin.services.UserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;
    private final ApplicationEventPublisher publisher;
    private final VerificationTokenRepository verificationTokenRepository;

    @PostMapping
    public String registerUser(@RequestBody RegistrationRequest registrationRequest,
                               final HttpServletRequest request){
      User user =  userService.registerUser(registrationRequest);
      //publish an event to the user
        publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));
        return "please confirm your email to complete your registration";
    }

    public String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    @GetMapping("/verifyEmail")
    public String verifyEmail(@RequestParam("token") String token){
        VerificationToken theToken  = verificationTokenRepository.findByToken(token);
        if(theToken.getUser().isEnabled()){
            return "This account has already been verified, Please login.";
        }
        String verificationResult = userService.validateToken(token);
        if(verificationResult.equalsIgnoreCase("valid")){
            return "Email verified successfully. Now you can login into your account";
        }
        return "invalid verification token";
    }
}
