package com.equalcollective.xray.model;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "xray_executions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class XRayExecution {

    @Id
    @Column(name = "execution_id", nullable = false, unique = true)
    private String executionId;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "status", length = 500)
    @Builder.Default
    private String status = "IN_PROGRESS";

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "context", columnDefinition = "json")
    private JsonNode context;

    @OneToMany(
        mappedBy = "execution",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.EAGER
    )
    @OrderBy("timestamp ASC")
    @Builder.Default
    private List<XRayStep> steps = new ArrayList<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (startTime == null) {
            startTime = LocalDateTime.now();
        }
    }

    public void addStep(XRayStep step) {
        steps.add(step);
        step.setExecution(this);
    }

    public void complete() {
        this.endTime = LocalDateTime.now();
        this.status = "COMPLETED";
    }

    public void fail(String reason) {
        this.endTime = LocalDateTime.now();
        this.status = "FAILED: " + reason;
    }

    public long getDurationMs() {
        if (endTime == null || startTime == null) {
            return 0;
        }
        return java.time.Duration.between(startTime, endTime).toMillis();
    }
}
