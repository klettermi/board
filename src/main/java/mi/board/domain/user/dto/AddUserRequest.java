package mi.board.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddUserRequest {
    @NotBlank(message = "Username cannot be empty")
    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "Username must be 4-10 characters long and contain only lowercase letters and digits")
    private String username;

    @NotBlank(message = "Password cannot be empty")
    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$", message = "Password must be 8-15 characters long and contain only letters and digits")
    private String password;
}
