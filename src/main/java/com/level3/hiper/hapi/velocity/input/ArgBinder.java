/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.level3.hiper.hapi.velocity.input;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zendle.joe
 */
public class ArgBinder {

    List<String> vals = new ArrayList();

    public List<String> getVals() {
        return vals;
    }

    public void addValue(String val) {
        vals.add(val);
    }

    @Override
    public String toString() {
        return vals.toString();
    }

}
