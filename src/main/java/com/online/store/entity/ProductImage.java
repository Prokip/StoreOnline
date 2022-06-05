package com.online.store.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static com.online.store.util.Constant.PRODUCT_IMAGES;

@Getter
@Setter
@Entity
public class ProductImage extends IdHolder {

    private String path;

    @ManyToMany(mappedBy = PRODUCT_IMAGES)
    private Set<Product> products = new HashSet<>();


    @Override
    public String toString() {
        return "ProductImage{" +
                "path='" + path + '\'' +
                ", products=" + products +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductImage that = (ProductImage) o;
        return Objects.equals(path, that.path) &&
                Objects.equals(products, that.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, products);
    }

}




