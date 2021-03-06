package ru.elagin.springcourse.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.elagin.springcourse.dto.CustomerFilter;
import ru.elagin.springcourse.models.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerRepository {

    private static final String INDEX_SQL = "SELECT id, tabnum, name, surname, email, birth FROM customers";
    private static final String INDEX_ORDER_BY_SQL = "SELECT id, tabnum, name, surname, email, birth FROM customers ORDER BY id";
    private static final String SHOW_SQL = "SELECT id, tabnum, name, surname, email, birth FROM customers WHERE id = ? ;";
    private static final String SAVE_SQL = "INSERT INTO customers(tabnum, name, surname, email, birth) VALUES(?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE customers SET tabnum = ?, name = ?, surname = ?, email = ?, birth = ? WHERE id = ?;";
    private static final String DELETE_SQL = "DELETE FROM customers WHERE id = ?;";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CustomerRepository(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Customer> index() {

        return jdbcTemplate.query(INDEX_ORDER_BY_SQL, new BeanPropertyRowMapper<>(Customer.class));
    }

    public List<Customer> index(CustomerFilter filter) {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        String where;

        if (filter.getTabnum() != null) {
            whereSql.add("tabnum = ?");
            parameters.add(filter.getTabnum());
        }
        if (filter.getName() != null) {
            whereSql.add("name LIKE ?");
            parameters.add("%" + filter.getName() + "%");
        }
        if (filter.getSurname() != null) {
            whereSql.add("surname LIKE ?");
            parameters.add("%" + filter.getSurname() + "%");
        }
        if (filter.getEmail() != null) {
            whereSql.add("email LIKE ?");
            parameters.add("%" + filter.getEmail() + "%");
        }
        if (filter.getBirth() != null) {
            whereSql.add("birth = ?");
            parameters.add(filter.getBirth());
        }

        parameters.add(filter.getLimit());
        parameters.add((filter.getOffset()));

        if (whereSql.isEmpty()) {
            where = " ORDER BY id LIMIT ? OFFSET ?";
        } else {
            where = whereSql.stream().collect(Collectors.joining
                    (" AND ", " WHERE ", " ORDER BY id LIMIT ? OFFSET ?"));
        }

        return jdbcTemplate.query(INDEX_SQL + where, preparedStatement -> {
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i + 1, parameters.get(i));
            }
        }, new BeanPropertyRowMapper<>(Customer.class));
    }

    public Customer show(int id) {

        return jdbcTemplate.query(SHOW_SQL, new BeanPropertyRowMapper<>(Customer.class), id)
                .stream().findAny().orElse(null);
    }

    public void save(Customer customer) {

        jdbcTemplate.update(SAVE_SQL, customer.getTabnum(), customer.getName(), customer.getSurname(),
                customer.getEmail(), customer.getBirth());
    }

    public void update(int id, Customer updatedCustomer) {

        jdbcTemplate.update(UPDATE_SQL, updatedCustomer.getTabnum(), updatedCustomer.getName(),
                updatedCustomer.getSurname(), updatedCustomer.getEmail(), updatedCustomer.getBirth(), id);
    }

    public void delete(int id) {

        jdbcTemplate.update(DELETE_SQL, id);
    }
}
