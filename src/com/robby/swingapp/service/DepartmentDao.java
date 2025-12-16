package com.robby.swingapp.service;



import com.robby.swingapp.entity.Department;
import com.robby.swingapp.util.MySQLUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Robby Tan
 */
public final class DepartmentDao implements DaoService<Department> {
    @Override
    public List<Department> fetchAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT id, name FROM department";
        List<Department> departments = new ArrayList<>();
        try (Connection connection = MySQLUtil.createConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Department department = new Department.Builder()
                                .id(resultSet.getString("id"))
                                .name(resultSet.getString("name"))
                                .build();
                        departments.add(department);
                    }
                }
            }
        }
        return departments;
    }

    @Override
    public int addData(Department department) throws SQLException, ClassNotFoundException {
        return 0;
    }

    @Override
    public Department fetch(Object id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
