/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.level3.hapi.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

/**
 *
 * @author zendle.joe
 */

/*

:domain     - The network domain name.
              Default: 'tw telecom - public'.

:device     - The host name of the device to obtain data about.

:minimum    - The minimum time stamp to include data for.
              Default: current epoch time - #secs in a day.
:maximum    - The maximum time stamp to include data for.
              Default: current epoch time.

:resolution - The time resolution to use as a data source.
              Valid values are: 5m, 1h, 8h, and 1d.

:interval   - The data points interval in multiples of
              mins(m)/hrs(h)/days(d).
              e.g.; 5m, 15m, 1h, 4h, 1d etc.

:unit       - The unit of round-trip jitter.
              Valid values are: ms(millisecs), us(microsecs).
              Default: ms
*/

public class StatementBuilder {
    
    
    Object columnFactory(String name, String value) {
        return new Object();
    }
    
    public Statement buildPreparedStatement(PreparedStatement ps, String sql, Map<String,Object> params) throws SQLException {
        for ( String key : params.keySet() ) {
            Object obj = params.get(key);
            ps.setObject(1,obj);
                
        }
        
        return ps;
    }
    
}
