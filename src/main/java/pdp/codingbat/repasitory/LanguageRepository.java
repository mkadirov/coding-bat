package pdp.codingbat.repasitory;

import org.springframework.data.jpa.repository.JpaRepository;
import pdp.codingbat.entity.Language;

public interface LanguageRepository extends JpaRepository<Language, Integer> {

    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Integer id);
}
