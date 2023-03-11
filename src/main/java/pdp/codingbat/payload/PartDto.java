package pdp.codingbat.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartDto {

    @NotNull(message = "name can't be empty")
    private String name;

    @NotNull(message = "List can't be empty")
    private List<Integer> exerciseIdList;
}
