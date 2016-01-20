/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.level3.hiper.hapi.util;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ResultSetConverter {

   public List<Map<String,Object>> convert(ResultSet rs)
           throws SQLException {
      List array = new ArrayList();
      ResultSetMetaData rsmd = rs.getMetaData();

      while (rs.next()) {
         int numColumns = rsmd.getColumnCount();
         // keep entries in same order as from db with linked struct
         Map<String,Object> columns = new LinkedHashMap();

         for (int i = 1; i < numColumns + 1; i++) {
            String column_name = rsmd.getColumnName(i);

            if (rsmd.getColumnType(i) == java.sql.Types.ARRAY) {
               columns.put(column_name, rs.getArray(column_name));
            } else if (rsmd.getColumnType(i) == java.sql.Types.BIGINT) {
               columns.put(column_name, rs.getInt(column_name));
            } else if (rsmd.getColumnType(i) == java.sql.Types.BOOLEAN) {
               columns.put(column_name, rs.getBoolean(column_name));
            } else if (rsmd.getColumnType(i) == java.sql.Types.BLOB) {
               columns.put(column_name, rs.getBlob(column_name));
            } else if (rsmd.getColumnType(i) == java.sql.Types.DOUBLE) {
               columns.put(column_name, rs.getDouble(column_name));
            } else if (rsmd.getColumnType(i) == java.sql.Types.FLOAT) {
               columns.put(column_name, rs.getFloat(column_name));
            } else if (rsmd.getColumnType(i) == java.sql.Types.INTEGER) {
               columns.put(column_name, rs.getInt(column_name));
            } else if (rsmd.getColumnType(i) == java.sql.Types.NVARCHAR) {
               columns.put(column_name, rs.getNString(column_name));
            } else if (rsmd.getColumnType(i) == java.sql.Types.VARCHAR) {
               columns.put(column_name, rs.getString(column_name));
            } else if (rsmd.getColumnType(i) == java.sql.Types.TINYINT) {
               columns.put(column_name, rs.getInt(column_name));
            } else if (rsmd.getColumnType(i) == java.sql.Types.SMALLINT) {
               columns.put(column_name, rs.getInt(column_name));
            } else if (rsmd.getColumnType(i) == java.sql.Types.DATE) {
               columns.put(column_name, rs.getDate(column_name));
            } else if (rsmd.getColumnType(i) == java.sql.Types.TIMESTAMP) {
               columns.put(column_name, rs.getTimestamp(column_name));
            } else {
               columns.put(column_name, rs.getObject(column_name));
            }
         }

         array.add(columns);
      }

      return array;
   }
}
