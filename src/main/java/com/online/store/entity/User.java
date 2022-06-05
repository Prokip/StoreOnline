package com.online.store.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.online.store.util.Constant;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

import static com.online.store.util.Constant.*;

@Getter
@Setter
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = Constant.EMAIL)
})
public class User extends IdHolder {

    @JsonIgnore
    private LocalDateTime createDate;

    @JsonIgnore
    private String password;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String street;
    private String city;
    private String countryCode;

    private Integer discount;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = USER_ROLES,
            joinColumns = @JoinColumn(name = USER_ID),
            inverseJoinColumns = @JoinColumn(name = ROLES_ID))
    private Set<Roles> roles = new HashSet<>();

    @ManyToMany(cascade = {
            CascadeType.PERSIST
    })
    @JoinTable(name = USERS_FAVOURITE_PRODUCTS,
            joinColumns = {@JoinColumn(name = USER_ID)},
            inverseJoinColumns = {@JoinColumn(name = PRODUCT_ID)})
    private Set<Product> favourites = new HashSet<>();

    @OneToMany(
            mappedBy = USER,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Order> orderList = new ArrayList<>();


    public void removeRoles(Roles oldRole) {
        roles.remove(oldRole);
        oldRole.getUsers().remove(this);
    }

    public void addOrder(Order order) {
        orderList.add(order);
        order.setUser(this);
    }

    public void removeOrder(Order order) {
        orderList.remove(order);
        order.setUser(null);
    }

    public void addFavouriteProduct(Product product) {
        favourites.add(product);
        product.getUsers().add(this);
    }

    public void removeFavouriteProduct(Product product) {
        favourites.remove(product);
        product.getUsers().remove(this);
    }


    @Override
    public String toString() {
        return "user: {" +
                "firstName: " + firstName + ',' + ' ' +
                "lastName: " + lastName + ',' + ' ' +
                "email: " + email + ',' + ' ' +
                "phone: " + phone + ',' + ' ' +
                "street: " + street + ',' + ' ' +
                "city: " + city + ',' + ' ' +
                "countryCode: " + countryCode + ',' + ' ' +
                "discount: " + discount + ',' + ' ' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(createDate, user.createDate) &&
                Objects.equals(password, user.password) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(email, user.email) &&
                Objects.equals(phone, user.phone) &&
                Objects.equals(street, user.street) &&
                Objects.equals(city, user.city) &&
                Objects.equals(countryCode, user.countryCode) &&
                Objects.equals(discount, user.discount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createDate, password, firstName, lastName,
                email, phone, street, city, countryCode, discount);
    }
}
