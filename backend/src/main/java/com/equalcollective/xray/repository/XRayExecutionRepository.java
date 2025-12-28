package com.equalcollective.xray.repository;

import com.equalcollective.xray.model.XRayExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface XRayExecutionRepository extends JpaRepository<XRayExecution, String> {

    List<XRayExecution> findAllByOrderByStartTimeDesc();
}
