package cpan228.assignment01.brands.config;

import cpan228.assignment01.brands.model.*;
import cpan228.assignment01.brands.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DatabaseLoader {

    @Bean
    CommandLineRunner initDatabase(ItemRepository itemRepo, UserRepository userRepo,
                                   RoleRepository roleRepo, PasswordEncoder encoder) {
        return _ -> {
            // Create roles
            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            roleRepo.save(adminRole);

            Role employeeRole = new Role();
            employeeRole.setName("EMPLOYEE");
            roleRepo.save(employeeRole);

            Role userRole = new Role();
            userRole.setName("USER");
            roleRepo.save(userRole);

            // Create admin user for testing
            User admin = new User();
            admin.setUsername("admin1");
            admin.setPassword(encoder.encode("adminpass"));
            admin.setEnabled(true);
            admin.getRoles().add(adminRole);
            userRepo.save(admin);

            // Create employee user for testing
            User employee = new User();
            employee.setUsername("employee1");
            employee.setPassword(encoder.encode("employeepass"));
            employee.setEnabled(true);
            employee.getRoles().add(employeeRole);
            userRepo.save(employee);

            // Create regular user for testing
            User user = new User();
            user.setUsername("user1");
            user.setPassword(encoder.encode("userpass"));
            user.setEnabled(true);
            user.getRoles().add(userRole);
            userRepo.save(user);

            // Add items
            itemRepo.save(new Item(null, "Varsity Jacket", 2022, 2500.00, Brand.Gucci));
            itemRepo.save(new Item(null, "Designer Sneakers", 2022, 1800.00, Brand.Balenciaga));
            itemRepo.save(new Item(null, "Sparkling Dress", 2023, 3200.00, Brand.Chanel));
            itemRepo.save(new Item(null, "Luxury Handbag", 2022, 4100.00, Brand.Prada));
            itemRepo.save(new Item(null, "Silk Scarf", 2022, 1500.00, Brand.Versace));
            itemRepo.save(new Item(null, "Tinted Sunglasses", 2024, 2200.00, Brand.Gucci));
            itemRepo.save(new Item(null, "Fancy Heels", 2022, 2800.00, Brand.Balenciaga));
            itemRepo.save(new Item(null, "Evening Gown", 2022, 5200.00, Brand.Chanel));
            itemRepo.save(new Item(null, "Blue Blazer", 2023, 3400.00, Brand.Prada));
            itemRepo.save(new Item(null, "Embroidered Hoodie", 2024, 2000.00, Brand.Versace));
        };
    }
}
