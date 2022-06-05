package com.online.store.service;

import com.online.store.dto.request.FeatureRequest;
import com.online.store.dto.response.FeatureResponse;
import com.online.store.entity.Feature;
import com.online.store.entity.FeatureKey;

public interface FeatureService {

    FeatureResponse createFeature(FeatureRequest featureRequest);

    FeatureResponse modifyFeature(Long id, FeatureRequest featureRequest);

    void deleteFeature(Long id);

    FeatureResponse getFeatureById(Long id);

    Feature getFeatureByIdFromDB(Long id);

    FeatureResponse createFeatureKey(Long id, FeatureRequest featureRequest);

    FeatureResponse modifyFeatureKey(Long id, FeatureRequest featureRequest, Long characteristicId);

    void deleteFeatureKey(Long id);

    FeatureResponse getFeatureKeyById(Long id);

    FeatureKey getFeatureKeyByIdFromDB(Long id);
}
