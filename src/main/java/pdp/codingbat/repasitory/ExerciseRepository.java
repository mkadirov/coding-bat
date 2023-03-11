package pdp.codingbat.repasitory;

import org.springframework.data.jpa.repository.JpaRepository;
import pdp.codingbat.entity.Exercise;

public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {
}
