package com.equalcollective.xray.service;

import com.equalcollective.xray.model.XRayExecution;
import com.equalcollective.xray.model.XRayStep;
import com.equalcollective.xray.repository.XRayExecutionRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
public class XRayTracer {

    private final XRayExecutionRepository executionRepository;
    private final ObjectMapper objectMapper;

    public XRayTracer(XRayExecutionRepository executionRepository,
                      ObjectMapper objectMapper) {
        this.executionRepository = executionRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public String startExecution(Object context) {
        String executionId = "exec_" + UUID.randomUUID().toString().substring(0, 8);

        JsonNode contextJson = objectMapper.valueToTree(context);

        XRayExecution execution = XRayExecution.builder()
                .executionId(executionId)
                .startTime(LocalDateTime.now())
                .status("IN_PROGRESS")
                .context(contextJson)
                .build();

        executionRepository.save(execution);
        log.info("Started execution: {}", executionId);

        return executionId;
    }

    @Transactional
    public void recordStep(String executionId, StepRecord stepRecord) {
        XRayExecution execution = executionRepository.findById(executionId)
                .orElseThrow(() -> new IllegalArgumentException("Execution not found: " + executionId));

        String stepId = "step_" + UUID.randomUUID().toString().substring(0, 8);

        XRayStep step = XRayStep.builder()
                .stepId(stepId)
                .stepName(stepRecord.getStepName())
                .timestamp(LocalDateTime.now())
                .input(objectMapper.valueToTree(stepRecord.getInput()))
                .output(objectMapper.valueToTree(stepRecord.getOutput()))
                .reasoning(stepRecord.getReasoning())
                .metadata(stepRecord.getMetadata() != null ?
                         objectMapper.valueToTree(stepRecord.getMetadata()) : null)
                .execution(execution)
                .build();

        execution.addStep(step);
        executionRepository.save(execution);

        log.debug("Recorded step '{}' for execution {}", stepRecord.getStepName(), executionId);
    }

    @Transactional
    public void endExecution(String executionId) {
        XRayExecution execution = executionRepository.findById(executionId)
                .orElseThrow(() -> new IllegalArgumentException("Execution not found: " + executionId));

        execution.complete();
        executionRepository.save(execution);

        log.info("Completed execution: {} (duration: {}ms)",
                 executionId, execution.getDurationMs());
    }

    @Transactional
    public void failExecution(String executionId, String reason) {
        XRayExecution execution = executionRepository.findById(executionId)
                .orElseThrow(() -> new IllegalArgumentException("Execution not found: " + executionId));

        execution.fail(reason);
        executionRepository.save(execution);

        log.error("Failed execution: {} - Reason: {}", executionId, reason);
    }

    @lombok.Data
    @lombok.Builder
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    public static class StepRecord {
        private String stepName;
        private Object input;
        private Object output;
        private String reasoning;
        private Object metadata;
    }
}
