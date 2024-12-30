package org.castle.djames.bankease.user.specification;

import lombok.Data;

@Data
public class SpecSearchCriteria {

    private String key;
    private SearchOperation operation;
    private Object value;
    private boolean orPredicate;

    public SpecSearchCriteria(final String orPredicate, final String key, final SearchOperation operation, final Object value) {
        super();
        this.orPredicate = orPredicate != null && orPredicate.equals(SearchOperation.OR_PREDICATE_FLAG);
        this.key = key;
        this.operation = operation;
        this.value = value;
    }
}
