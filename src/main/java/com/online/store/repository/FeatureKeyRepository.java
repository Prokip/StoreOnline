package com.online.store.repository;

import com.online.store.entity.Feature;
import com.online.store.entity.FeatureKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeatureKeyRepository extends JpaRepository<FeatureKey, Long> {

    List<FeatureKey> findByFeature(Feature feature);

}
