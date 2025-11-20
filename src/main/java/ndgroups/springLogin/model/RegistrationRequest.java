package ndgroups.springLogin.model;

public record RegistrationRequest(String firstName, String lastName, String email,
                                  String password, String role) {
}
