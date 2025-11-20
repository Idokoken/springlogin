package ndgroups.springLogin.controller;

import ndgroups.springLogin.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

//    @GetMapping
//    public List<User> getUsers(){
//      return userService.getUsers();
//    }
//    @GetMapping("/user")
//    public String userProfilePage(){
//        return "userProfile";
//    }
//    @GetMapping("/register")
//    public String registerPage(){
//        return "register";
//    }
//    @GetMapping("/login")
//    public String loginPage(){
//        return "login";
//    }


}


//}
