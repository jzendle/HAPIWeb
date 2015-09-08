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
public class Util {

  public static String adjustedTimeMillis(String input) {
    Long delta = Long.parseLong(input);
    Long epoch = System.currentTimeMillis() / 1000;
    return new Long(epoch + delta).toString();
  }

  public static void illegalArgumentException(String arg) throws IllegalAccessException {
    throw new IllegalAccessException("missing manadatory argument: " + arg);

  }
}
