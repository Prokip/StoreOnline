package com.online.store.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static com.online.store.util.Constant.FEATURE_ID;
import static com.online.store.util.Constant.FEATURE_KEYS;

@Getter
@Setter
@Entity
public class FeatureKey extends IdHolder {

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = FEATURE_ID)
    private Feature feature;

    @ManyToMany(mappedBy = FEATURE_KEYS)
    private Set<Product> products = new HashSet<>();


    @Override
    public String toString() {
        return "FeatureKey{" +
                "name='" + name + '\'' +
                ", feature=" + feature +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeatureKey that = (FeatureKey) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(feature, that.feature);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, feature);
    }
}
