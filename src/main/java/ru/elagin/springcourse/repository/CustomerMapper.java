package ru.elagin.springcourse.repository;

import org.springframework.jdbc.core.RowMapper;
import ru.elagin.springcourse.models.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;

/*
Класс не используется, т.к. названия полей и колонок в таблице совпадают.
 */
public class CustomerMapper implements RowMapper<Customer> {
    @Override
    public Customer mapRow(ResultSet resultSet, int i) throws SQLException {
        Customer customer = new Customer();

        customer.setId(resultSet.getInt("id"));
        customer.setTabnum(resultSet.getInt("tabnum"));
        customer.setName(resultSet.getString("name"));
        customer.setSurname(resultSet.getString("surname"));
        customer.setEmail(resultSet.getString("email"));
        customer.setBirth(resultSet.getDate("birth").toLocalDate());

        return customer;
    }
}
