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
public class TimestampArg extends BaseArg {

    Long longValue;
    Boolean valid = false;

    private void parse(String input) {

        if (input == null) {
            return;
        }

        try {
            longValue = Long.parseLong(input);
            value = input;
            valid = true;
        } catch (Exception e) {
            String msg = e.getMessage();
            msg += " unable to parse timestamp " + input;
            throw new IllegalArgumentException(msg);
        }
    }

    public TimestampArg(String input) {
        super(input);
        parse(input);
    }

    @Override
    public String getValue() {
        return longValue.toString();
    }

    // so $class.defaultToCurrent(blah) works
    public String defaultCurrent(String input) {
        if (longValue != null)
            return longValue.toString();
            
        Long delta = Long.parseLong(input);
        Long epoch = System.currentTimeMillis()/1000;

        return new Long(epoch + delta).toString();
    }

    // name so $class.defaultToCurrent works
    public String getDefaultCurrent() {
        if (longValue != null)
            return longValue.toString();
            
        Long epoch = System.currentTimeMillis()/1000;

        return epoch.toString();
    }

    public Boolean isValid() {
        return valid;
    }

}
