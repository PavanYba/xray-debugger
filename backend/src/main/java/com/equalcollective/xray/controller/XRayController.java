package com.equalcollective.xray.controller;

import com.equalcollective.xray.model.XRayExecution;
import com.equalcollective.xray.repository.XRayExecutionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/executions")
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class XRayController {

    private final XRayExecutionRepository executionRepository;

    public XRayController(XRayExecutionRepository executionRepository) {
        this.executionRepository = executionRepository;
    }

    /**
     * Get all executions
     * GET /api/executions
     */
    @GetMapping
    public ResponseEntity<List<XRayExecution>> getAllExecutions() {
        List<XRayExecution> executions = executionRepository.findAllByOrderByStartTimeDesc();
        log.info("Retrieved {} executions", executions.size());
        return ResponseEntity.ok(executions);
    }

    @GetMapping("/{executionId}")
    public ResponseEntity<XRayExecution> getExecution(@PathVariable String executionId) {
        return executionRepository.findById(executionId)
                .map(execution -> {
                    log.info("Retrieved execution: {} with {} steps",
                            executionId, execution.getSteps().size());
                    return ResponseEntity.ok(execution);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{executionId}")
    public ResponseEntity<Void> deleteExecution(@PathVariable String executionId) {
        if (executionRepository.existsById(executionId)) {
            executionRepository.deleteById(executionId);
            log.info("Deleted execution: {}", executionId);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllExecutions() {
        long count = executionRepository.count();
        executionRepository.deleteAll();
        log.info("Deleted all {} executions", count);
        return ResponseEntity.ok().build();
    }
}
