package cpan228.project.distribution.config;

import cpan228.project.distribution.model.Brand;
import cpan228.project.distribution.model.DistributionCenter;
import cpan228.project.distribution.model.Item;
import cpan228.project.distribution.repository.DistributionCenterRepository;
import cpan228.project.distribution.repository.ItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseLoader {
    @Bean
    CommandLineRunner initDatabase(DistributionCenterRepository centerRepo, ItemRepository itemRepo) {
        return args -> {
            DistributionCenter dc1 = new DistributionCenter();
            dc1.setName("North America Hub");
            dc1.setLatitude(40.7128);
            dc1.setLongitude(-74.0060);
            centerRepo.save(dc1);

            DistributionCenter dc2 = new DistributionCenter();
            dc2.setName("Europe Hub");
            dc2.setLatitude(51.5074);
            dc2.setLongitude(-0.1278);
            centerRepo.save(dc2);

            DistributionCenter dc3 = new DistributionCenter();
            dc3.setName("Asia Hub");
            dc3.setLatitude(35.6762);
            dc3.setLongitude(139.6503);
            centerRepo.save(dc3);

            DistributionCenter dc4 = new DistributionCenter();
            dc4.setName("Middle East Hub");
            dc4.setLatitude(24.7136);
            dc4.setLongitude(46.6753);
            centerRepo.save(dc4);

            addSampleItem(itemRepo, dc1, "Varsity Jacket", 2022, 2500.00, 10, Brand.Gucci);
            addSampleItem(itemRepo, dc1, "Designer Sneakers", 2022, 1800.00, 5, Brand.Balenciaga);
            addSampleItem(itemRepo, dc2, "Sparkling Dress", 2023, 3200.00, 8, Brand.Chanel);
            addSampleItem(itemRepo, dc3, "Luxury Handbag", 2022, 4100.00, 3, Brand.Prada);
            addSampleItem(itemRepo, dc4, "Silk Scarf", 2022, 1500.00, 15, Brand.Versace);
        };
    }

    private void addSampleItem(ItemRepository itemRepo, DistributionCenter center,
                               String name, int year, double price, int quantity, Brand brand) {
        Item item = new Item();
        item.setName(name);
        item.setYearOfCreation(year);
        item.setPrice(price);
        item.setQuantity(quantity);
        item.setBrand(brand);
        item.setDistributionCenter(center);
        itemRepo.save(item);
    }
}
