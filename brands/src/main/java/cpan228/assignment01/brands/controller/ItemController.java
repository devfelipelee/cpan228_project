package cpan228.assignment01.brands.controller;

import cpan228.assignment01.brands.model.Brand;
import cpan228.assignment01.brands.model.Item;
import cpan228.assignment01.brands.model.User;
import cpan228.assignment01.brands.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ItemController {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping("/items")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public String showItems(Model model) {
        model.addAttribute("newItem", new Item());
        model.addAttribute("brands", Brand.values());
        return "items";
    }

    @PostMapping("/items")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public String addItem(@ModelAttribute("newItem") Item item, RedirectAttributes redirectAttributes) {
        List<String> errors = validateItem(item);

        if (!errors.isEmpty()) {
            redirectAttributes.addFlashAttribute("errors", errors);
            return "redirect:/items";
        }

        item.setId(null);
        itemRepository.save(item);
        redirectAttributes.addFlashAttribute("message", "Exciting news! Your item has been added to the stock!");
        return "redirect:/items-list";
    }

    @GetMapping("/items-list")
    public String listItems(@RequestParam(value = "brand", required = false) String brandName,
                            @RequestParam(value = "page", defaultValue = "0") int page,
                            @RequestParam(value = "size", defaultValue = "10") int size,
                            @RequestParam(value = "sort", defaultValue = "name,asc") String sort,
                            @AuthenticationPrincipal User user,
                            Model model) {

        // Convert brandName to Brand enum
        Brand brand = null;
        if (brandName != null && !brandName.isEmpty()) {
            try {
                brand = Brand.valueOf(brandName);
            } catch (IllegalArgumentException e) {
                model.addAttribute("message", "Invalid brand selection.");
            }
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Item> itemsPage = (brand != null)
                ? itemRepository.findByBrandAndYearOfCreation(brand, 2022, pageable)
                : itemRepository.findAll(pageable);

        if (user != null) {
            boolean isAdmin = user.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
            boolean isEmployee = user.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_EMPLOYEE"));
            model.addAttribute("isAdmin", isAdmin);
            model.addAttribute("isEmployee", isEmployee);
            model.addAttribute("username", user.getUsername()); // Add username
        }

        model.addAttribute("itemsPage", itemsPage);
        model.addAttribute("brands", Brand.values());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", itemsPage.getTotalPages());
        model.addAttribute("pageSize", size);
        model.addAttribute("sort", sort);
        model.addAttribute("paramBrand", brandName);

        return "items-list";
    }

    private List<String> validateItem(Item item) {
        List<String> errors = new ArrayList<>();
        if (item.getName() == null || item.getName().trim().isEmpty()) {
            errors.add("Give it a name, will ya?");
        }
        if (item.getYearOfCreation() <= 2021) {
            errors.add("Nothing older than 2022 please.");
        }
        if (item.getPrice() <= 1000) {
            errors.add("This gotta be more expensive than $1000!");
        }
        if (item.getBrand() == null) {
            errors.add("Please select a brand. I can't guess!");
        }
        return errors;
    }
}
