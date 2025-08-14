package mg.itu.ticketingproject.data.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import mg.itu.annotation.FormParametre;

@Data
public class LoginRequest {
    @NotEmpty
    @FormParametre("email")
    private String email;
    @NotEmpty
    @FormParametre("password")
    private String password;
}
