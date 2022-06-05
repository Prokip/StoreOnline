package com.online.store.repository.specification;

import com.online.store.entity.Category;
import com.online.store.util.Constant;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class CategorySpecification implements Specification<Category> {

    public static final String PERCENT = "%";

    private String parentCategory;
    private String name;

    public CategorySpecification(String parentCategory, String name) {
        this.parentCategory = parentCategory;
        this.name = name;
    }

    @Override
    public Predicate toPredicate(Root<Category> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();

        predicateByParamCategory(root, criteriaBuilder, predicates, parentCategory);

        checkParameterName(root, criteriaBuilder, predicates);

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void checkParameterName(Root<Category> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (!isBlank(name)) {
            if (name.contains(PERCENT)) {
                predicates.add(criteriaBuilder.like(root.get(Constant.NAME), name));
            } else {
                predicateByParam(root, criteriaBuilder, predicates, name);
            }
        }
    }

    private void predicateByParam(Root<Category> root, CriteriaBuilder criteriaBuilder,
                                  List<Predicate> predicates, String field) {
        if (!isBlank(field)) {
            predicates.add(criteriaBuilder.equal(root.get(Constant.NAME), field));
        }
    }

    private void predicateByParamCategory(Root<Category> root, CriteriaBuilder criteriaBuilder,
                                          List<Predicate> predicates, String field) {
        if (!isBlank(field)) {
            predicates.add(criteriaBuilder.equal(root.get(Constant.PARENT_CATEGORY).get(Constant.NAME), field));
        }
    }

}
