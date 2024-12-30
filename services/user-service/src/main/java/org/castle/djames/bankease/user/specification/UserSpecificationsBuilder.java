package org.castle.djames.bankease.user.specification;

import org.castle.djames.bankease.user.entity.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public final class UserSpecificationsBuilder {

    private final List<SpecSearchCriteria> params;

    /**
     * Default constructor that initializes an empty list of search criteria.
     */
    public UserSpecificationsBuilder() {
        params = new ArrayList<>();
    }


    /**
     * Adds a new search criteria to the list of specifications.
     *
     * @param orPredicate Specifies whether to combine this condition with an OR operation.
     * @param key         The key (attribute name) for the search criteria.
     * @param operation   The operation type for the search criteria (e.g., equality, contains, etc.).
     * @param value       The value to be compared with the key's value.
     * @param prefix      An optional prefix used for specifying search patterns.
     * @param suffix      An optional suffix used for specifying search patterns.
     */
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

    /**
     * Builds a {@link Specification} object for the {@link User} entity using the provided search criteria.
     *
     * @return A {@link Specification} instance representing the combined search criteria,
     * or {@code null} if no criteria are specified.
     */
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
