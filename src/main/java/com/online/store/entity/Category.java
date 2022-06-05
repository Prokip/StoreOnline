package com.online.store.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.online.store.util.Constant;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.online.store.util.Constant.CATEGORY;
import static com.online.store.util.Constant.PARENT_CATEGORY;

@Getter
@Setter
@Entity
public class Category extends IdHolder {

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category parentCategory;

    @OneToMany(
            mappedBy = CATEGORY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    private List<Product> productList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(
            mappedBy = PARENT_CATEGORY,
            cascade = CascadeType.MERGE
    )
    private List<Category> childCategory = new ArrayList<>();


    public void addProduct(Product product) {
        this.getProductList().add(product);
        product.setCategory(this);
    }

    public void removeProduct(Product product) {
        this.getProductList().remove(product);
        product.setCategory(null);
    }


    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", parentCategory=" + parentCategory +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(name, category.name) &&
                Objects.equals(parentCategory, category.parentCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, parentCategory);
    }

}
