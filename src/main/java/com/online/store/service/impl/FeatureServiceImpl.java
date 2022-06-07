package com.online.store.service.impl;

import com.online.store.dto.request.FeatureRequest;
import com.online.store.dto.response.FeatureResponse;
import com.online.store.entity.Feature;
import com.online.store.entity.FeatureKey;
import com.online.store.repository.FeatureKeyRepository;
import com.online.store.repository.FeatureRepository;
import com.online.store.service.FeatureService;
import com.online.store.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.online.store.exception.NotFoundException.notFoundException;

@Service
public class FeatureServiceImpl implements FeatureService {

    @Autowired
    private FeatureRepository featureRepository;
    @Autowired
    private FeatureKeyRepository featureKeyRepository;


    @Override
    public FeatureResponse createFeature(FeatureRequest featureRequest) {
        Feature feature = new Feature();
        feature.setName(featureRequest.getName());
        featureRepository.save(feature);
        return getFeatureResponse(feature);
    }

    @Override
    public FeatureResponse modifyFeature(Long id, FeatureRequest featureRequest) {
        Feature feature = getFeatureByIdFromDB(id);
        feature.setName(featureRequest.getName());
        featureRepository.save(feature);
        return getFeatureResponse(feature);
    }

    @Transactional
    @Override
    public void deleteFeature(Long id) {
        Feature feature = getFeatureByIdFromDB(id);
        List<FeatureKey> featureKeys = featureKeyRepository.findByFeature(feature);
        featureKeys.forEach(featureKey -> featureKey.setFeature(null));
        featureRepository.delete(feature);
    }

    @Override
    public Feature getFeatureByIdFromDB(Long id) {
        return featureRepository.findById(id).orElseThrow(() -> notFoundException(Constant.FEATURE + id));
    }

    @Override
    public FeatureResponse getFeatureById(Long id) {
        Feature feature = getFeatureByIdFromDB(id);
        return getFeatureResponse(feature);
    }

    @Override
    public FeatureResponse createFeatureKey(Long id, FeatureRequest featureRequest) {
        Feature feature = getFeatureByIdFromDB(id);
        FeatureKey featureKey = new FeatureKey();
        featureKey.setName(featureRequest.getName());
        feature.addFeatureKey(featureKey);
        featureKeyRepository.save(featureKey);
        return getFeatureKeyResponse(featureKey);
    }

    @Override
    public FeatureResponse modifyFeatureKey(Long characteristicId, FeatureRequest featureRequest, Long id) {
        getFeatureByIdFromDB(id);
        FeatureKey featureKey = getFeatureKeyByIdFromDB(characteristicId);
        featureKey.setName(featureRequest.getName());
        featureKeyRepository.save(featureKey);
        return getFeatureKeyResponse(featureKey);
    }

    @Override
    public void deleteFeatureKey(Long id) {
        FeatureKey featureKey = getFeatureKeyByIdFromDB(id);
        featureKey.getFeature().removeFeatureKey(featureKey);
        featureKeyRepository.delete(featureKey);
    }

    @Override
    public FeatureResponse getFeatureKeyById(Long id) {
        FeatureKey featureKey = getFeatureKeyByIdFromDB(id);
        return getFeatureKeyResponse(featureKey);
    }

    @Override
    public FeatureKey getFeatureKeyByIdFromDB(Long id) {
        return featureKeyRepository
                .findById(id)
                .orElseThrow(() -> notFoundException(Constant.FEATURE_KEY + id));
    }

    private FeatureResponse getFeatureResponse(Feature feature) {
        FeatureResponse featureResponse = new FeatureResponse();
        featureResponse.setName(feature.getName());
        featureResponse.setId(feature.getId());
        return featureResponse;
    }

    private FeatureResponse getFeatureKeyResponse(FeatureKey featureKey) {
        FeatureResponse featureResponse = new FeatureResponse();
        featureResponse.setName(featureKey.getName());
        featureResponse.setId(featureKey.getId());
        return featureResponse;
    }

}
