package com.online.store.entity;

import com.online.store.entity.enums.Status;
import com.online.store.util.Constant;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.online.store.util.Constant.ORDER;

@Getter
@Setter
@Entity
public class Order extends IdHolder {

    private String numberOrder;

    private String createDate;

    private Integer delivery;

    private Integer totalAmount;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(
            mappedBy = Constant.ORDER,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Card> cards = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public void addCard(Card card) {
        cards.add(card);
        card.setOrder(this);
    }

    public void removeCard(Card card) {
        cards.remove(card);
        card.setOrder(null);
    }

    @Override
    public String toString() {
        return "Order{" +
                "numberOrder='" + numberOrder + '\'' +
                ", createDate='" + createDate + '\'' +
                ", delivery=" + delivery +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(numberOrder, order.numberOrder) &&
                Objects.equals(createDate, order.createDate) &&
                Objects.equals(delivery, order.delivery) &&
                status == order.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOrder, createDate, delivery, status);
    }
}
