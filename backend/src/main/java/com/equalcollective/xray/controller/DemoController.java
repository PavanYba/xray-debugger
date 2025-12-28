package com.equalcollective.xray.controller;

import com.equalcollective.xray.demo.CompetitorSelectionService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/demo")
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class DemoController {

    private final CompetitorSelectionService competitorSelectionService;

    public DemoController(CompetitorSelectionService competitorSelectionService) {
        this.competitorSelectionService = competitorSelectionService;
    }

    /**
     * Run the competitor selection demo
     * POST /api/demo/run-competitor-selection
     * 
     * @return Execution ID to view results
     */
    @PostMapping("/run-competitor-selection")
    public ResponseEntity<DemoResponse> runCompetitorSelection() {
        log.info("Running competitor selection demo");
        
        try {
            String executionId = competitorSelectionService.runCompetitorSelection();
            
            return ResponseEntity.ok(new DemoResponse(
                    executionId,
                    "Competitor selection completed successfully",
                    true
            ));
            
        } catch (Exception e) {
            log.error("Demo execution failed", e);
            return ResponseEntity.internalServerError()
                    .body(new DemoResponse(
                            null,
                            "Demo failed: " + e.getMessage(),
                            false
                    ));
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DemoResponse {
        private String executionId;
        private String message;
        private boolean success;
    }
}
