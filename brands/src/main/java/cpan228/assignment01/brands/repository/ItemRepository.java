package cpan228.assignment01.brands.repository;

import cpan228.assignment01.brands.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import cpan228.assignment01.brands.model.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    // custom query to return items for only one brand and the year (2022)
    /* use Page and pageable for pagination - if the list of items is more than 10,
     it starts to sort into multiple pages for a more user-friendly site/app */
    Page<Item> findByBrandAndYearOfCreation(Brand brand, int year, Pageable pageable);
}
