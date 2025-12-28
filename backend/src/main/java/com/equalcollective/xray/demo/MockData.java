package com.equalcollective.xray.demo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Mock data for competitor product selection demo
 * Simulates Amazon product data
 */
public class MockData {

    /**
     * Reference product - the seller's product we're finding competitors for
     */
    public static Product getReferenceProduct() {
        return Product.builder()
                .asin("B0XYZ123")
                .title("ProBrand Stainless Steel Water Bottle 32oz Insulated")
                .category("Sports & Outdoors > Water Bottles")
                .price(29.99)
                .rating(4.2)
                .reviews(1247)
                .build();
    }

    /**
     * Generate 50 mock candidate products
     * Mix of good matches, poor matches, and accessories
     */
    public static List<Product> getCandidateProducts() {
        List<Product> products = new ArrayList<>();

        // Good competitors (high review count, good ratings, appropriate price)
        products.add(Product.builder()
                .asin("B0COMP01")
                .title("HydroFlask 32oz Wide Mouth Stainless Steel Water Bottle")
                .category("Sports & Outdoors > Water Bottles")
                .price(44.99)
                .rating(4.5)
                .reviews(8932)
                .build());

        products.add(Product.builder()
                .asin("B0COMP02")
                .title("Yeti Rambler 26oz Vacuum Insulated Stainless Steel Bottle")
                .category("Sports & Outdoors > Water Bottles")
                .price(34.99)
                .rating(4.4)
                .reviews(5621)
                .build());

        products.add(Product.builder()
                .asin("B0COMP07")
                .title("Stanley Adventure Quencher 30oz Insulated Tumbler")
                .category("Sports & Outdoors > Water Bottles")
                .price(35.00)
                .rating(4.3)
                .reviews(4102)
                .build());

        products.add(Product.builder()
                .asin("B0COMP08")
                .title("Contigo AUTOSEAL Stainless Steel Travel Mug 24oz")
                .category("Sports & Outdoors > Water Bottles")
                .price(28.99)
                .rating(4.4)
                .reviews(3854)
                .build());

        products.add(Product.builder()
                .asin("B0COMP09")
                .title("Simple Modern Summit Water Bottle 32oz Vacuum Insulated")
                .category("Sports & Outdoors > Water Bottles")
                .price(26.99)
                .rating(4.5)
                .reviews(3201)
                .build());

        products.add(Product.builder()
                .asin("B0COMP10")
                .title("Thermos Stainless King 40oz Beverage Bottle")
                .category("Sports & Outdoors > Water Bottles")
                .price(32.99)
                .rating(4.3)
                .reviews(2847)
                .build());

        products.add(Product.builder()
                .asin("B0COMP11")
                .title("CamelBak Chute Mag 32oz BPA Free Water Bottle")
                .category("Sports & Outdoors > Water Bottles")
                .price(18.99)
                .rating(4.2)
                .reviews(2156)
                .build());

        products.add(Product.builder()
                .asin("B0COMP12")
                .title("Nalgene Tritan Wide Mouth BPA-Free Water Bottle 32oz")
                .category("Sports & Outdoors > Water Bottles")
                .price(15.99)
                .rating(4.6)
                .reviews(1892)
                .build());

        // Marginal products (pass some filters, fail others)
        products.add(Product.builder()
                .asin("B0COMP13")
                .title("Iron Flask Sports Water Bottle 40oz")
                .category("Sports & Outdoors > Water Bottles")
                .price(29.99)
                .rating(3.7) // FAILS rating threshold
                .reviews(1543)
                .build());

        products.add(Product.builder()
                .asin("B0COMP14")
                .title("MIRA Stainless Steel Vacuum Insulated Water Bottle 32oz")
                .category("Sports & Outdoors > Water Bottles")
                .price(24.99)
                .rating(4.4)
                .reviews(87) // FAILS review count
                .build());

        // Poor matches (fail multiple filters)
        products.add(Product.builder()
                .asin("B0COMP03")
                .title("Generic Plastic Water Bottle 24oz")
                .category("Sports & Outdoors > Water Bottles")
                .price(8.99) // FAILS price (too low)
                .rating(3.2) // FAILS rating
                .reviews(45) // FAILS review count
                .build());

        products.add(Product.builder()
                .asin("B0COMP15")
                .title("Budget Water Bottle 20oz BPA Free")
                .category("Sports & Outdoors > Water Bottles")
                .price(7.49) // FAILS price
                .rating(3.5) // FAILS rating
                .reviews(234)
                .build());

        // Accessories and false positives (should be filtered out)
        products.add(Product.builder()
                .asin("B0COMP04")
                .title("Water Bottle Cleaning Brush Set with Sponge")
                .category("Sports & Outdoors > Cleaning Supplies")
                .price(12.99) // FAILS price
                .rating(4.6)
                .reviews(3421)
                .build());

        products.add(Product.builder()
                .asin("B0COMP05")
                .title("Replacement Lid for HydroFlask Wide Mouth Bottles")
                .category("Sports & Outdoors > Replacement Parts")
                .price(9.99) // FAILS price
                .rating(4.3)
                .reviews(892)
                .build());

        products.add(Product.builder()
                .asin("B0COMP06")
                .title("Insulated Water Bottle Carrier Bag with Shoulder Strap")
                .category("Sports & Outdoors > Bags & Cases")
                .price(14.99) // Might pass price, but not a competitor
                .rating(4.2)
                .reviews(567)
                .build());

        products.add(Product.builder()
                .asin("B0COMP16")
                .title("Silicone Sleeve for 32oz Water Bottles - Protection Cover")
                .category("Sports & Outdoors > Accessories")
                .price(11.99) // FAILS price
                .rating(4.1)
                .reviews(423)
                .build());

        // Premium products (fail price filter - too expensive)
        products.add(Product.builder()
                .asin("B0COMP17")
                .title("Premium Titanium Water Bottle 32oz Ultra-Light")
                .category("Sports & Outdoors > Water Bottles")
                .price(89.00) // FAILS price (too high)
                .rating(4.8)
                .reviews(234)
                .build());

        products.add(Product.builder()
                .asin("B0COMP18")
                .title("Luxury Stainless Steel Bottle with Smart Temperature Display")
                .category("Sports & Outdoors > Smart Water Bottles")
                .price(79.99) // FAILS price
                .rating(4.3)
                .reviews(156)
                .build());

        // Additional competitive products
        products.add(Product.builder()
                .asin("B0COMP19")
                .title("Owala FreeSip Insulated Stainless Steel Water Bottle 32oz")
                .category("Sports & Outdoors > Water Bottles")
                .price(32.99)
                .rating(4.6)
                .reviews(1678)
                .build());

        products.add(Product.builder()
                .asin("B0COMP20")
                .title("Takeya Actives Insulated Stainless Steel Bottle 32oz")
                .category("Sports & Outdoors > Water Bottles")
                .price(24.99)
                .rating(4.5)
                .reviews(1432)
                .build());

        // Fill remaining slots with varied products
        for (int i = 21; i <= 50; i++) {
            boolean isCompetitor = i % 3 != 0; // ~67% are actual bottles
            boolean isGoodMatch = i % 4 == 0;  // ~25% are good matches
            
            products.add(Product.builder()
                    .asin("B0COMP" + String.format("%02d", i))
                    .title(generateProductTitle(i, isCompetitor))
                    .category(isCompetitor ? "Sports & Outdoors > Water Bottles" : "Sports & Outdoors > Accessories")
                    .price(isGoodMatch ? 25.0 + (i % 20) : (i % 2 == 0 ? 9.99 : 65.99))
                    .rating(isGoodMatch ? 4.0 + (i % 10) * 0.05 : 3.0 + (i % 8) * 0.1)
                    .reviews(isGoodMatch ? 500 + (i * 50) : 20 + (i * 10))
                    .build());
        }

        return products;
    }

    private static String generateProductTitle(int index, boolean isCompetitor) {
        if (isCompetitor) {
            String[] brands = {"TechBottle", "AquaFlow", "HydroMax", "SteelPro", "CoolFlow"};
            String[] features = {"Insulated", "Vacuum Sealed", "Double Wall", "Leak-Proof", "Wide Mouth"};
            String brand = brands[index % brands.length];
            String feature = features[index % features.length];
            int size = 20 + (index % 3) * 8; // 20, 28, or 36 oz
            return brand + " " + feature + " Stainless Steel Water Bottle " + size + "oz";
        } else {
            String[] accessories = {"Bottle Brush", "Carrying Strap", "Cleaning Tablets", "Ice Cube Tray"};
            return accessories[index % accessories.length] + " for Water Bottles";
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Product {
        private String asin;
        private String title;
        private String category;
        private double price;
        private double rating;
        private int reviews;
    }
}
