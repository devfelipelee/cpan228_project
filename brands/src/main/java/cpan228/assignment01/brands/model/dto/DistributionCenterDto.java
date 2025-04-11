package cpan228.assignment01.brands.model.dto;

import lombok.Data;

@Data
public class DistributionCenterDto {
    private Long id;
    private String name;
    private double latitude;
    private double longitude;
}
