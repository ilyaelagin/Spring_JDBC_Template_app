package ru.elagin.springcourse.repository;

import java.util.List;

public interface Repository<K, M, F> {

    List<M> index();

    List<M> index(F filter);

    M show(K id);

    void save(M customer);

    void update(K id, M updatedCustomer);

    void delete(K id);
}
