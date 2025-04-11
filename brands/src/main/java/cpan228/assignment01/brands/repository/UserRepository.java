package cpan228.assignment01.brands.repository;

import cpan228.assignment01.brands.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
