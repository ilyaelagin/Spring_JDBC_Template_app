package ru.elagin.springcourse.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.elagin.springcourse.models.Customer;

import java.util.List;

@Component
public class CustomerRepository {

    private static final String INDEX_SQL = "SELECT id, tabnum, name, surname, email, birth FROM customers ORDER BY id;";
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

        return jdbcTemplate.query(INDEX_SQL, new BeanPropertyRowMapper<>(Customer.class));
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
