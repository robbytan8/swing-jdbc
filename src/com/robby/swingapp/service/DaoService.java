package com.robby.swingapp.service;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Robby Tan
 */
public interface DaoService<T> {
    List<T> fetchAll() throws SQLException, ClassNotFoundException;

    int addData(T t) throws SQLException, ClassNotFoundException;

    T fetch(Object id) throws SQLException, ClassNotFoundException;
}
