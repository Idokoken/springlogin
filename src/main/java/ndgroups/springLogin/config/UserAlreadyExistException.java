package ndgroups.springLogin.config;

public class UserAlreadyExistException extends RuntimeException{

    public UserAlreadyExistException(String message){
        super(message);
    }
}
