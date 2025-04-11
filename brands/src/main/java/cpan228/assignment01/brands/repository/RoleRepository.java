package cpan228.assignment01.brands.repository;

import cpan228.assignment01.brands.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
