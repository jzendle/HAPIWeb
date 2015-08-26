/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.level3.hiper.hapi.velocity.input;

/**
 *
 * @author zendle.joe
 */
public class BaseArg {

    protected String value;
    
    public BaseArg(String input) {
        value=input;
    }
    @Override
    public String toString() {
        return value;
    }
}
