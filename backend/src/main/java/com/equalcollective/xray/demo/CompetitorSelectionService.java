package com.equalcollective.xray.demo;

import com.equalcollective.xray.service.XRayTracer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class CompetitorSelectionService {

    private final XRayTracer xrayTracer;

    public CompetitorSelectionService(XRayTracer xrayTracer) {
        this.xrayTracer = xrayTracer;
    }

    public String runCompetitorSelection() {
        MockData.Product referenceProduct = MockData.getReferenceProduct();

        Map<String, Object> context = new HashMap<>();
        context.put("referenceProduct", referenceProduct);
        context.put("pipeline", "competitor_selection");
        
        String executionId = xrayTracer.startExecution(context);
        log.info("Starting competitor selection for product: {}", referenceProduct.getTitle());

        try {
            // STEP 1: Generate Keywords (Mock LLM)
            List<String> keywords = generateKeywords(executionId, referenceProduct);
            
            // STEP 2: Search Candidates (Mock API)
            List<MockData.Product> candidates = searchCandidates(executionId, keywords);

            // STEP 3: Apply Filters & Select
            MockData.Product selectedCompetitor = applyFiltersAndSelect(executionId, candidates, referenceProduct);
            
            xrayTracer.endExecution(executionId);
            log.info("Competitor selection completed. Selected: {}", selectedCompetitor.getTitle());
            
        } catch (Exception e) {
            log.error("Competitor selection failed", e);
            xrayTracer.failExecution(executionId, e.getMessage());
        }
        
        return executionId;
    }

    private List<String> generateKeywords(String executionId, MockData.Product product) {
        // Mock LLM keyword generation - in reality would call GPT-4/Claude
        List<String> keywords = extractKeywords(product);

        xrayTracer.recordStep(executionId, XRayTracer.StepRecord.builder()
                .stepName("keyword_generation")
                .input(Map.of(
                        "product_title", product.getTitle(),
                        "category", product.getCategory()
                ))
                .output(Map.of(
                        "keywords", keywords,
                        "model", "gpt-4-mock"
                ))
                .reasoning("Extracted key product attributes: material (stainless steel), " +
                          "capacity (32oz), feature (insulated)")
                .build());
        
        return keywords;
    }

    /**
     * STEP 2: Search for Candidate Products (Mock API)
     */
    private List<MockData.Product> searchCandidates(String executionId, List<String> keywords) {
        List<MockData.Product> allCandidates = MockData.getCandidateProducts();
        
        xrayTracer.recordStep(executionId, XRayTracer.StepRecord.builder()
                .stepName("candidate_search")
                .input(Map.of(
                        "keyword", keywords.get(0),
                        "limit", 50
                ))
                .output(Map.of(
                        "total_results", 2847,
                        "candidates_fetched", allCandidates.size(),
                        "candidates", allCandidates
                ))
                .reasoning(String.format("Fetched top %d results by relevance; 2847 total matches found",
                          allCandidates.size()))
                .build());
        
        return allCandidates;
    }

    /**
     * STEP 3: Apply Filters and Select Best Match
     */
    private MockData.Product applyFiltersAndSelect(
            String executionId,
            List<MockData.Product> candidates,
            MockData.Product referenceProduct) {

        double minPrice = referenceProduct.getPrice() * 0.5;
        double maxPrice = referenceProduct.getPrice() * 2.0;
        double minRating = 3.8;
        int minReviews = 100;

        List<CandidateEvaluation> evaluations = new ArrayList<>();
        List<MockData.Product> qualifiedProducts = new ArrayList<>();
        
        for (MockData.Product candidate : candidates) {
            CandidateEvaluation eval = evaluateCandidate(
                    candidate, minPrice, maxPrice, minRating, minReviews);
            evaluations.add(eval);
            
            if (eval.isQualified()) {
                qualifiedProducts.add(candidate);
            }
        }
        
        MockData.Product selected = selectBestMatch(qualifiedProducts);
        
        xrayTracer.recordStep(executionId, XRayTracer.StepRecord.builder()
                .stepName("apply_filters")
                .input(Map.of(
                        "candidates_count", candidates.size(),
                        "reference_product", referenceProduct
                ))
                .output(Map.of(
                        "total_evaluated", candidates.size(),
                        "passed", qualifiedProducts.size(),
                        "failed", candidates.size() - qualifiedProducts.size(),
                        "selected_competitor", selected
                ))
                .reasoning(String.format(
                        "Applied price ($%.2f-$%.2f), rating (%.1f+), and review count (%d+) filters. " +
                        "Narrowed candidates from %d to %d. Selected '%s' (highest review count: %d, rating: %.1f★)",
                        minPrice, maxPrice, minRating, minReviews,
                        candidates.size(), qualifiedProducts.size(),
                        selected.getTitle(), selected.getReviews(), selected.getRating()
                ))
                .metadata(Map.of(
                        "filters_applied", Map.of(
                                "price_range", Map.of(
                                        "min", minPrice,
                                        "max", maxPrice,
                                        "rule", "0.5x - 2x of reference price"
                                ),
                                "min_rating", Map.of(
                                        "value", minRating,
                                        "rule", "Must be at least 3.8 stars"
                                ),
                                "min_reviews", Map.of(
                                        "value", minReviews,
                                        "rule", "Must have at least 100 reviews"
                                )
                        ),
                        "evaluations", evaluations // Full detail for every candidate
                ))
                .build());
        
        return selected;
    }

    private CandidateEvaluation evaluateCandidate(
            MockData.Product candidate,
            double minPrice,
            double maxPrice,
            double minRating,
            int minReviews) {
        
        Map<String, FilterResult> filterResults = new HashMap<>();
        
        boolean passesPrice = candidate.getPrice() >= minPrice && candidate.getPrice() <= maxPrice;
        filterResults.put("price_range", FilterResult.builder()
                .passed(passesPrice)
                .detail(passesPrice ?
                        String.format("$%.2f is within $%.2f-$%.2f", candidate.getPrice(), minPrice, maxPrice) :
                        String.format("$%.2f is %s $%.2f %s",
                                candidate.getPrice(),
                                candidate.getPrice() < minPrice ? "below minimum" : "above maximum",
                                candidate.getPrice() < minPrice ? minPrice : maxPrice,
                                candidate.getPrice() < minPrice ? "" : "maximum"
                        ))
                .build());
        
        boolean passesRating = candidate.getRating() >= minRating;
        filterResults.put("min_rating", FilterResult.builder()
                .passed(passesRating)
                .detail(passesRating ?
                        String.format("%.1f >= %.1f", candidate.getRating(), minRating) :
                        String.format("%.1f < %.1f threshold", candidate.getRating(), minRating))
                .build());
        
        boolean passesReviews = candidate.getReviews() >= minReviews;
        filterResults.put("min_reviews", FilterResult.builder()
                .passed(passesReviews)
                .detail(passesReviews ?
                        String.format("%d >= %d", candidate.getReviews(), minReviews) :
                        String.format("%d < %d minimum", candidate.getReviews(), minReviews))
                .build());
        
        boolean qualified = passesPrice && passesRating && passesReviews;
        
        return CandidateEvaluation.builder()
                .asin(candidate.getAsin())
                .title(candidate.getTitle())
                .metrics(Map.of(
                        "price", candidate.getPrice(),
                        "rating", candidate.getRating(),
                        "reviews", candidate.getReviews()
                ))
                .filterResults(filterResults)
                .qualified(qualified)
                .build();
    }

    /**
     * Select the best match from qualified products
     * Ranking criteria: review count (primary), rating (secondary)
     */
    private MockData.Product selectBestMatch(List<MockData.Product> qualified) {
        return qualified.stream()
                .max(Comparator
                        .comparingInt(MockData.Product::getReviews)
                        .thenComparingDouble(MockData.Product::getRating))
                .orElseThrow(() -> new RuntimeException("No qualified products found"));
    }

    /**
     * Extract keywords from product (mock LLM logic)
     * In production, this would call GPT-4/Claude API
     */
    private List<String> extractKeywords(MockData.Product product) {
        List<String> keywords = new ArrayList<>();
        
        String title = product.getTitle().toLowerCase();
        
        keywords.add("stainless steel water bottle insulated");
        
        if (title.contains("32oz") || title.contains("30oz")) {
            keywords.add("vacuum insulated bottle 32oz");
        } else {
            keywords.add("insulated water bottle");
        }
        
        return keywords;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CandidateEvaluation {
        private String asin;
        private String title;
        private Map<String, Object> metrics;
        private Map<String, FilterResult> filterResults;
        private boolean qualified;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FilterResult {
        private boolean passed;
        private String detail;
    }
}
