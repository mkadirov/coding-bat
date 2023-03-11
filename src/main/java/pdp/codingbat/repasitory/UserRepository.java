package pdp.codingbat.repasitory;

import org.springframework.data.jpa.repository.JpaRepository;
import pdp.codingbat.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByUserName(String username);
    boolean existsByUserNameAndIdNot(String username, Integer id);
}
