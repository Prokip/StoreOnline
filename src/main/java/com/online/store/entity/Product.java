package com.online.store.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

import static com.online.store.util.Constant.*;

@Getter
@Setter
@Entity
public class Product extends IdHolder {

    private LocalDateTime createDate;
    private String name;
    private String codeUnit;
    private Integer maxPrice;
    private Integer price;
    private String description;
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = PRODUCTS_WITH_FEATURE,
            joinColumns = {@JoinColumn(name = PRODUCT_ID)},
            inverseJoinColumns = {@JoinColumn(name = FEATURE_KEY_ID)})
    private Set<FeatureKey> featureKeys = new HashSet<>();

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = PRODUCTS_WITH_FILES,
            joinColumns = {@JoinColumn(name = PRODUCT_ID)},
            inverseJoinColumns = {@JoinColumn(name = FILE_ID)})

    private Set<ProductFile> productFiles = new HashSet<>();

    @OneToMany(
            mappedBy = PRODUCT,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Card> cards = new ArrayList<>();

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = PRODUCTS_WITH_IMAGES,
            joinColumns = {@JoinColumn(name = PRODUCT_ID)},
            inverseJoinColumns = {@JoinColumn(name = IMAGE_ID)})

    private Set<ProductImage> productImages = new HashSet<>();

    @ManyToMany(mappedBy = FAVOURITES)
    private Set<User> users = new HashSet<>();


    public void addFeatureKey(FeatureKey featureKey) {
        this.getFeatureKeys().add(featureKey);
        featureKey.getProducts().add(this);
    }

    public void removeFeatureKey(FeatureKey featureKey) {
        this.getFeatureKeys().remove(featureKey);
        featureKey.getProducts().remove(this);
    }

    public void addFile(ProductFile productFile) {
        this.getProductFiles().add(productFile);
        productFile.getProducts().add(this);
    }

    public void removeFile(ProductFile productFile) {
        this.getProductFiles().remove(productFile);
        productFile.getProducts().remove(this);
    }

    public void addCard(Card card) {
        this.getCards().add(card);
        card.setProduct(this);
    }

    public void removeCard(Card card) {
        this.getCards().remove(card);
        card.setProduct(null);
    }

    public void addImage(ProductImage productImage) {
        this.getProductImages().add(productImage);
        productImage.getProducts().add(this);
    }

    public void removeImage(ProductImage productImage) {
        this.getProductImages().remove(productImage);
        productImage.getProducts().remove(this);
    }


    @Override
    public String toString() {
        return "Product{" +
                "createDate=" + createDate +
                ", name='" + name + '\'' +
                ", codeUnit='" + codeUnit + '\'' +
                ", maxPrice=" + maxPrice +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", isActive=" + isActive +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(createDate, product.createDate) &&
                Objects.equals(name, product.name) &&
                Objects.equals(codeUnit, product.codeUnit) &&
                Objects.equals(maxPrice, product.maxPrice) &&
                Objects.equals(price, product.price) &&
                Objects.equals(description, product.description) &&
                Objects.equals(isActive, product.isActive) &&
                Objects.equals(category, product.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createDate, name, codeUnit, maxPrice, price, description, isActive, category);
    }
}
