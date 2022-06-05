package com.online.store.repository.specification;

import com.online.store.entity.Product;
import com.online.store.util.Constant;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class ProductSpecification implements Specification<Product> {

    public static final String PERCENT = "%";

    private Integer price;
    private String category;
    private String feature;
    private String name;
    private String parentCategory;


    public ProductSpecification(Integer price, String category, String feature,
                                String name, String parentCategory) {
        this.price = price;
        this.category = category;
        this.feature = feature;
        this.name = name;
        this.parentCategory = parentCategory;
    }

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();

        predicateByParamCategory(root, criteriaBuilder, predicates, category);

        predicateByParamCharacteristicValue(root, criteriaBuilder, predicates, feature);

        predicateByParamChildCategory(root, criteriaBuilder, predicates, parentCategory);

        checkParameterName(root, criteriaBuilder, predicates);

        predicateByPrice(root, criteriaBuilder, predicates);

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void checkParameterName(Root<Product> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (!isBlank(name)) {
            if (name.contains(PERCENT)) {
                predicates.add(criteriaBuilder.like(root.get(Constant.NAME), name));
            } else {
                predicateByParam(root, criteriaBuilder, predicates, name);
            }
        }
    }

    private void predicateByParam(Root<Product> root, CriteriaBuilder criteriaBuilder,
                                  List<Predicate> predicates, String field) {
        if (!isBlank(field)) {
            predicates.add(criteriaBuilder.equal(root.get(Constant.NAME), field));
        }
    }

    private void predicateByParamCategory(Root<Product> root, CriteriaBuilder criteriaBuilder,
                                          List<Predicate> predicates, String field) {
        if (!isBlank(field)) {
            predicates.add(criteriaBuilder.equal(root.get(Constant.CATEGORY).get(Constant.NAME), field));
        }
    }

    private void predicateByParamCharacteristicValue(Root<Product> root, CriteriaBuilder criteriaBuilder,
                                                      List<Predicate> predicates, String field) {
        if (!isBlank(field)) {
            predicates.add(criteriaBuilder.equal(root.join(Constant.FEATURE_KEYS).get(Constant.NAME), field));
        }
    }

    private void predicateByParamChildCategory(Root<Product> root, CriteriaBuilder criteriaBuilder,
                                                     List<Predicate> predicates, String field) {
        if (!isBlank(field)) {
            predicates.add(criteriaBuilder
                    .equal(root.get(Constant.CATEGORY).get(Constant.PARENT_CATEGORY).get(Constant.NAME), field));
        }
    }

    private void predicateByPrice(Root<Product> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (price != null) {
            predicates.add(criteriaBuilder.equal(root.get(Constant.PRICE), price));
        }
    }

}
