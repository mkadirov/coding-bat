package pdp.codingbat.payload;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordDto {

    @NotNull(message = "Username can't be empty")
    private String username;
    @NotNull(message = "oldPass can't be empty")
    private String oldPass;
    @NotNull(message = "newPass can't be empty")
    private String newPass;
}
