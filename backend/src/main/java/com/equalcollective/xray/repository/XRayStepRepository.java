package com.equalcollective.xray.repository;

import com.equalcollective.xray.model.XRayStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface XRayStepRepository extends JpaRepository<XRayStep, String> {
}
