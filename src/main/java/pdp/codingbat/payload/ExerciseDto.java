package pdp.codingbat.payload;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseDto {

    @NotNull(message = "name can't be empty")
    private String name;

    @NotNull(message = "Text can't be empty")
    private String exerciseText;

    private String codeText;

    @NotNull(message = "Solution can't be empty")
    private String solution;
}
