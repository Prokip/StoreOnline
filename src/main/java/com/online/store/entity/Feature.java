package com.online.store.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.online.store.util.Constant.FEATURE;

@Getter
@Setter
@Entity
public class Feature extends IdHolder {

    private String name;

    @OneToMany(
            mappedBy = FEATURE,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    private List<FeatureKey> featureKeyList = new ArrayList<>();


    public void addFeatureKey(FeatureKey featureKey) {
        this.getFeatureKeyList().add(featureKey);
        featureKey.setFeature(this);
    }

    public void removeFeatureKey(FeatureKey featureKey) {
        this.getFeatureKeyList().remove(featureKey);
        featureKey.setFeature(null);
    }

    @Override
    public String toString() {
        return "Feature{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feature feature = (Feature) o;
        return Objects.equals(name, feature.name) &&
                Objects.equals(featureKeyList, feature.featureKeyList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, featureKeyList);
    }
}
