package org.castle.djames.bankease.user.specification;

import org.castle.djames.bankease.user.entity.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public final class UserSpecificationsBuilder {

    private final List<SpecSearchCriteria> params;

    public UserSpecificationsBuilder() {
        params = new ArrayList<>();
    }

    public void with(final String orPredicate, final String key, final String operation, final Object value, final String prefix, final String suffix) {
        SearchOperation op = SearchOperation.getSimpleOperation(operation.charAt(0));
        if (SearchOperation.EQUALITY == op) { // the operation may be complex operation
            final boolean startWithAsterisk = prefix != null && prefix.contains(SearchOperation.ZERO_OR_MORE_REGEX);
            final boolean endWithAsterisk = suffix != null && suffix.contains(SearchOperation.ZERO_OR_MORE_REGEX);

            if (startWithAsterisk && endWithAsterisk) {
                op = SearchOperation.CONTAINS;
            } else if (startWithAsterisk) {
                op = SearchOperation.ENDS_WITH;
            } else if (endWithAsterisk) {
                op = SearchOperation.STARTS_WITH;
            }
        }
        params.add(new SpecSearchCriteria(orPredicate, key, op, value));
    }

    public Specification<User> build() {
        if (params.isEmpty())
            return null;

        Specification<User> result = new UserSpecification(params.getFirst());

        for (int i = 1; i < params.size(); i++) {
            result = params.get(i).isOrPredicate()
                    ? Specification.where(result).or(new UserSpecification(params.get(i)))
                    : Specification.where(result).and(new UserSpecification(params.get(i)));
        }

        return result;
    }

}
