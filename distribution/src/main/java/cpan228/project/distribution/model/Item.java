package cpan228.project.distribution.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int yearOfCreation;
    private double price;
    private int quantity;

    @Enumerated(EnumType.STRING)
    private Brand brand;

    @ManyToOne
    private DistributionCenter distributionCenter;
}
