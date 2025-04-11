package cpan228.project.distribution.repository;

import cpan228.project.distribution.model.Brand;
import cpan228.project.distribution.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByNameAndBrand(String name, Brand brand);
}
