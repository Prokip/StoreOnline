package com.online.store.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;

import static com.online.store.util.Constant.PRODUCT_ID;

@Getter
@Setter
@Entity
public class Card extends IdHolder {

    private Integer quantity;

    private Integer total;
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = PRODUCT_ID)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;


    @Override
    public String toString() {
        return "Card{" +
                "quantity=" + quantity +
                ", total=" + total +
                ", isActive=" + isActive +
                ", product=" + product +
                ", order=" + order +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(quantity, card.quantity) &&
                Objects.equals(total, card.total) &&
                Objects.equals(isActive, card.isActive) &&
                Objects.equals(product, card.product) &&
                Objects.equals(order, card.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity, total, isActive, product, order);
    }
}
