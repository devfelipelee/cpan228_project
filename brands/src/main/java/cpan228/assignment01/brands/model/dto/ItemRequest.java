package cpan228.assignment01.brands.model.dto;

import cpan228.assignment01.brands.model.Brand;
import lombok.Data;

@Data
public class ItemRequest {
    private String name;
    private Brand brand;
}
