/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.level3.hiper.hapi.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

/**
 *
 * @author zendle.joe
 */
public class JDBC {

    public static PreparedStatement populate(PreparedStatement ps, Collection args) throws SQLException {
        int argc = 1;
        for (Object arg : args) {
            ps.setObject(argc++, arg);
        }

        return ps;
    }


    public static Connection getConnection(String className, String url, String user, String password) throws ClassNotFoundException, SQLException {

        Connection connection = null;

        // the mysql driver string
        Class.forName(className);

        // get the mysql database connection
        connection = DriverManager.getConnection(url, user, password);

        return connection;
    }

}
