package cpan228.project.distribution.controller;

import cpan228.project.distribution.model.Brand;
import cpan228.project.distribution.model.DistributionCenter;
import cpan228.project.distribution.model.Item;
import cpan228.project.distribution.repository.DistributionCenterRepository;
import cpan228.project.distribution.repository.ItemRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/distribution-centers")
public class DistributionCenterController {
    private final DistributionCenterRepository centerRepository;
    private final ItemRepository itemRepository;

    public DistributionCenterController(DistributionCenterRepository centerRepository,
                                        ItemRepository itemRepository) {
        this.centerRepository = centerRepository;
        this.itemRepository = itemRepository;
    }

    @GetMapping
    public List<DistributionCenter> getAllCenters() {
        return centerRepository.findAll();
    }

    @PostMapping("/{centerId}/items")
    public ResponseEntity<Object> addItem(@PathVariable Long centerId, @RequestBody Item item) {
        return centerRepository.findById(centerId)
                .map(center -> {
                    item.setDistributionCenter(center);
                    itemRepository.save(item);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long itemId) {
        itemRepository.deleteById(itemId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/request-item")
    public ResponseEntity<Object> requestItem(@RequestBody ItemRequest request) {
        return itemRepository.findByNameAndBrand(request.name(), request.brand())
                .stream()
                .filter(item -> item.getQuantity() > 0)
                .findFirst()
                .map(item -> {
                    item.setQuantity(item.getQuantity() - 1);
                    itemRepository.save(item);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    public record ItemRequest(String name, Brand brand) {}
}
