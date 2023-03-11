package pdp.codingbat.repasitory;

import org.springframework.data.jpa.repository.JpaRepository;
import pdp.codingbat.entity.Part;

public interface PartRepository extends JpaRepository<Part, Integer> {
}
