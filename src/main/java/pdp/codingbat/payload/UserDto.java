package pdp.codingbat.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserDto {
    @NotNull(message = "username can't be empty")
    private String username;
    @NotNull(message = "password can't be empty")
    private String password;
}
