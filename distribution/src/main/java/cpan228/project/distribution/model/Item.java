package cpan228.project.distribution.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    @JoinColumn(name = "distribution_center_id")
    @JsonBackReference
    private DistributionCenter distributionCenter;
}