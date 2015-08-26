/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.level3.hiper.hapi.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
    
    
    Connection getConnection(String className, String url, String user, String password) {
        
        return null;
        
    }

}
