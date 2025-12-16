package com.robby.swingapp.service;

import com.robby.swingapp.entity.Department;
import com.robby.swingapp.entity.Student;
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
public final class StudentDao implements DaoService<Student> {
    @Override
    public List<Student> fetchAll() throws SQLException, ClassNotFoundException {
        List<Student> students = new ArrayList<>();
        String query = "SELECT s.id, s.name, address, male, department_id, d.name AS dep_name FROM student s JOIN department d ON d.id = s.department_id";
        try (Connection connection = MySQLUtil.createConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Department department = new Department.Builder()
                                .id(resultSet.getString("department_id"))
                                .name(resultSet.getString("dep_name"))
                                .build();
                        Student student = new Student.Builder()
                                .id(resultSet.getString("id"))
                                .name(resultSet.getString("name"))
                                .address(resultSet.getString("address"))
                                .male(resultSet.getBoolean("male"))
                                .department(department)
                                .build();
                        students.add(student);
                    }
                }
            }
        }
        return students;
    }

    @Override
    public int addData(Student student) throws SQLException, ClassNotFoundException {
        int result = 0;
        String query = "INSERT INTO student(id, name, address, male, department_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = MySQLUtil.createConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, student.getId());
                statement.setString(2, student.getName());
                statement.setString(3, student.getAddress());
                statement.setBoolean(4, student.isMale());
                statement.setString(5, student.getDepartment().getId());
                if (statement.executeUpdate() != 0) {
                    connection.commit();
                    result = 1;
                } else {
                    connection.rollback();
                }
            }
        }
        return result;
    }

    @Override
    public Student fetch(Object id) throws SQLException, ClassNotFoundException {
        Student student = null;
        String query = "SELECT * FROM student WHERE id = ? LIMIT 1";
        try (Connection connection = MySQLUtil.createConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, id.toString());
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        Department department = new Department.Builder()
                                .id(resultSet.getString("department_id")).build();
                        student = new Student.Builder()
                                .id(resultSet.getString("id"))
                                .name(resultSet.getString("name"))
                                .address(resultSet.getString("address"))
                                .male(resultSet.getBoolean("male"))
                                .department(department).build();
                    }
                }
            }
        }
        return student;
    }
}
