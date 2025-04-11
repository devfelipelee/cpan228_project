package cpan228.assignment01.brands.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import cpan228.assignment01.brands.model.Brand;
import cpan228.assignment01.brands.model.dto.ItemRequest;
import cpan228.assignment01.brands.repository.ItemRepository;
import cpan228.assignment01.brands.service.DistributionCenterService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final ItemRepository itemRepository;
    private final DistributionCenterService distributionCenterService;

    public AdminController(ItemRepository itemRepository,
                           DistributionCenterService distributionCenterService) {
        this.itemRepository = itemRepository;
        this.distributionCenterService = distributionCenterService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String adminPanel(Model model) {
        model.addAttribute("items", itemRepository.findAll());
        model.addAttribute("distributionCenters",
                distributionCenterService.getAllDistributionCenters());
        model.addAttribute("itemRequest", new ItemRequest());
        model.addAttribute("brands", Brand.values());
        return "admin";
    }

    @PostMapping("/delete")
    public String deleteItem(@RequestParam("id") Long id) {
        itemRepository.deleteById(id);
        return "redirect:/admin";
    }

    @PostMapping("/request-item")
    public String requestItem(@ModelAttribute ItemRequest request,
                              RedirectAttributes redirectAttributes) {
        boolean success = distributionCenterService.requestItemFromDistributionCenter(request);
        redirectAttributes.addFlashAttribute(success ? "message" : "error",
                success ? "Item requested successfully" : "Failed to request item");
        return "redirect:/admin";
    }
}
