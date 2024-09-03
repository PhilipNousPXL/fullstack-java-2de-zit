package be.pxl.services;

import be.pxl.services.domain.Category;
import be.pxl.services.domain.Product;
import be.pxl.services.domain.SustainabilityCriteria;
import be.pxl.services.domain.SustainabilityType;
import be.pxl.services.repository.ProductRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.List;
import java.util.Random;

//@Component
public class DatabaseInitializer implements ApplicationRunner {

    private final ProductRepository userRepository;
    Random random = new Random();
    String longDescription = """
            Lorem ipsum dolor sit amet, consectetur adipiscing elit,\s
            sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
             Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris\s
             nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in
              reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.
               Excepteur sint occaecat cupidatat non proident, sunt in culpa qui\s
               officia deserunt mollit anim id est laborum.
            """;

    String shortDescription = """
            Lorem ipsum odor amet, consectetuer adipiscing elit. Aliquet vitae
             sapien donec per tincidunt odio diam efficitur. Lobortis etiam\s
             elementum taciti fames accumsan ligula. Velit et erat etiam\s
             lobortis odio et imperdiet ullamcorper montes.
            """;

    public DatabaseInitializer(ProductRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.count() > 0) {
            return;
        }

        for (Product product : products) {
            int flag = random.nextInt(0, 2);
            if (flag == 1) {
                product.setDescription(longDescription);
            } else {
                product.setDescription(shortDescription);
            }
        }

        userRepository.saveAllAndFlush(products);
    }

    List<Product> products = List.of(
            Product.builder()
                    .name("Laptop")
                    .category(Category.ELECTRONICS)
                    .price(599.99f)
                    .labels(List.of("High Performance", "Portable", "Lightweight"))
                    .sustainabilityCriteria(List.of(
                            new SustainabilityCriteria(SustainabilityType.LOW_CARBON_EMISSION, 4),
                            new SustainabilityCriteria(SustainabilityType.REPAIRABLE, 3),
                            new SustainabilityCriteria(SustainabilityType.RECYCLED, 2)))
                    .build(),

            Product.builder()
                    .name("Organic Apple")
                    .category(Category.FOOD)
                    .price(0.99f)
                    .labels(List.of("Fresh", "Organic", "Healthy"))
                    .sustainabilityCriteria(List.of(
                            new SustainabilityCriteria(SustainabilityType.FAIR_TRADE, 5),
                            new SustainabilityCriteria(SustainabilityType.LOW_CARBON_EMISSION, 4)))
                    .build(),

            Product.builder()
                    .name("Stuffed Bear")
                    .category(Category.TOYS)
                    .price(14.99f)
                    .labels(List.of("Soft", "Cuddly", "Safe for Kids"))
                    .sustainabilityCriteria(List.of(
                            new SustainabilityCriteria(SustainabilityType.RECYCLED, 3),
                            new SustainabilityCriteria(SustainabilityType.SUSTAINABLE_MATERIALS, 4)))
                    .build(),

            Product.builder()
                    .name("Designer Handbag")
                    .category(Category.ACCESSORIES)
                    .price(199.99f)
                    .labels(List.of("Luxury", "Elegant", "Fashionable"))
                    .sustainabilityCriteria(List.of(
                            new SustainabilityCriteria(SustainabilityType.FAIR_TRADE, 4),
                            new SustainabilityCriteria(SustainabilityType.SUSTAINABLE_MATERIALS, 5)))
                    .build(),

            Product.builder()
                    .name("Jeans")
                    .category(Category.CLOTHES)
                    .price(49.99f)
                    .labels(List.of("Durable", "Comfortable", "Classic"))
                    .sustainabilityCriteria(List.of(
                            new SustainabilityCriteria(SustainabilityType.REPAIRABLE, 4),
                            new SustainabilityCriteria(SustainabilityType.RECYCLED, 3)))
                    .build(),

            Product.builder()
                    .name("Smart Watch")
                    .category(Category.ELECTRONICS)
                    .price(149.99f)
                    .labels(List.of("High Tech", "Wearable", "Fitness Tracker"))
                    .sustainabilityCriteria(List.of(
                            new SustainabilityCriteria(SustainabilityType.LOW_CARBON_EMISSION, 3),
                            new SustainabilityCriteria(SustainabilityType.RECYCLED, 4)))
                    .build(),

            Product.builder()
                    .name("Eco-friendly Water Bottle")
                    .category(Category.DRINKS)
                    .price(15.99f)
                    .labels(List.of("Reusable", "BPA Free", "Durable"))
                    .sustainabilityCriteria(List.of(
                            new SustainabilityCriteria(SustainabilityType.RECYCLED, 5),
                            new SustainabilityCriteria(SustainabilityType.SUSTAINABLE_MATERIALS, 5)))
                    .build(),

            Product.builder()
                    .name("Bluetooth Headphones")
                    .category(Category.ELECTRONICS)
                    .price(89.99f)
                    .labels(List.of("Wireless", "Noise Cancelling", "Comfortable"))
                    .sustainabilityCriteria(List.of(
                            new SustainabilityCriteria(SustainabilityType.REPAIRABLE, 2),
                            new SustainabilityCriteria(SustainabilityType.RECYCLED, 3)))
                    .build(),

            Product.builder()
                    .name("Wooden Toy Blocks")
                    .category(Category.TOYS)
                    .price(24.99f)
                    .labels(List.of("Safe", "Educational", "Durable"))
                    .sustainabilityCriteria(List.of(
                            new SustainabilityCriteria(SustainabilityType.SUSTAINABLE_MATERIALS, 4),
                            new SustainabilityCriteria(SustainabilityType.FAIR_TRADE, 3)))
                    .build(),

            Product.builder()
                    .name("Vegan Protein Bar")
                    .category(Category.FOOD)
                    .price(2.99f)
                    .labels(List.of("High Protein", "Gluten Free", "Healthy"))
                    .sustainabilityCriteria(List.of(
                            new SustainabilityCriteria(SustainabilityType.LOW_CARBON_EMISSION, 4),
                            new SustainabilityCriteria(SustainabilityType.FAIR_TRADE, 5)))
                    .build(),

            Product.builder()
                    .name("Leather Wallet")
                    .category(Category.ACCESSORIES)
                    .price(29.99f)
                    .labels(List.of("Durable", "Stylish", "Compact"))
                    .sustainabilityCriteria(List.of(
                            new SustainabilityCriteria(SustainabilityType.REPAIRABLE, 4),
                            new SustainabilityCriteria(SustainabilityType.SUSTAINABLE_MATERIALS, 3)))
                    .build(),

            Product.builder()
                    .name("Running Shoes")
                    .category(Category.CLOTHES)
                    .price(69.99f)
                    .labels(List.of("Comfortable", "Breathable", "Lightweight"))
                    .sustainabilityCriteria(List.of(
                            new SustainabilityCriteria(SustainabilityType.REPAIRABLE, 2),
                            new SustainabilityCriteria(SustainabilityType.LOW_CARBON_EMISSION, 4)))
                    .build(),

            Product.builder()
                    .name("Smartphone Case")
                    .category(Category.ACCESSORIES)
                    .price(12.99f)
                    .labels(List.of("Shockproof", "Stylish", "Slim"))
                    .sustainabilityCriteria(List.of(
                            new SustainabilityCriteria(SustainabilityType.RECYCLED, 4),
                            new SustainabilityCriteria(SustainabilityType.SUSTAINABLE_MATERIALS, 3)))
                    .build(),

            Product.builder()
                    .name("Organic Coffee")
                    .category(Category.DRINKS)
                    .price(8.99f)
                    .labels(List.of("Fair Trade", "Rich Flavor", "Ethically Sourced"))
                    .sustainabilityCriteria(List.of(
                            new SustainabilityCriteria(SustainabilityType.FAIR_TRADE, 5),
                            new SustainabilityCriteria(SustainabilityType.LOW_CARBON_EMISSION, 4)))
                    .build(),

            Product.builder()
                    .name("Yoga Mat")
                    .category(Category.ACCESSORIES)
                    .price(25.99f)
                    .labels(List.of("Non-slip", "Cushioned", "Eco-friendly"))
                    .sustainabilityCriteria(List.of(
                            new SustainabilityCriteria(SustainabilityType.SUSTAINABLE_MATERIALS, 5),
                            new SustainabilityCriteria(SustainabilityType.RECYCLED, 4)))
                    .build(),

            Product.builder()
                    .name("Electric Kettle")
                    .category(Category.ELECTRONICS)
                    .price(39.99f)
                    .labels(List.of("Fast Boil", "Energy Efficient", "Compact"))
                    .sustainabilityCriteria(List.of(
                            new SustainabilityCriteria(SustainabilityType.LOW_CARBON_EMISSION, 3),
                            new SustainabilityCriteria(SustainabilityType.REPAIRABLE, 4)))
                    .build(),

            Product.builder()
                    .name("Organic Tea")
                    .category(Category.DRINKS)
                    .price(5.99f)
                    .labels(List.of("Calming", "Organic", "Aromatic"))
                    .sustainabilityCriteria(List.of(
                            new SustainabilityCriteria(SustainabilityType.FAIR_TRADE, 5),
                            new SustainabilityCriteria(SustainabilityType.SUSTAINABLE_MATERIALS, 4)))
                    .build(),

            Product.builder()
                    .name("Winter Jacket")
                    .category(Category.CLOTHES)
                    .price(89.99f)
                    .labels(List.of("Warm", "Waterproof", "Stylish"))
                    .sustainabilityCriteria(List.of(
                            new SustainabilityCriteria(SustainabilityType.REPAIRABLE, 4),
                            new SustainabilityCriteria(SustainabilityType.RECYCLED, 3),
                            new SustainabilityCriteria(SustainabilityType.LOW_CARBON_EMISSION, 3)))
                    .build(),

            Product.builder()
                    .name("Children's Book")
                    .category(Category.TOYS)
                    .price(9.99f)
                    .labels(List.of("Educational", "Illustrated", "Interactive"))
                    .sustainabilityCriteria(List.of(
                            new SustainabilityCriteria(SustainabilityType.RECYCLED, 4),
                            new SustainabilityCriteria(SustainabilityType.FAIR_TRADE, 3)))
                    .build(),

            Product.builder()
                    .name("Reusable Straw Set")
                    .category(Category.ACCESSORIES)
                    .price(7.99f)
                    .labels(List.of("Eco-friendly", "Portable", "Durable"))
                    .sustainabilityCriteria(List.of(
                            new SustainabilityCriteria(SustainabilityType.SUSTAINABLE_MATERIALS, 5),
                            new SustainabilityCriteria(SustainabilityType.RECYCLED, 5)))
                    .build()
    );


}
