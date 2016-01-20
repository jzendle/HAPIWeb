/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.level3.hiper.hapi.velocity.input;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author zendle.joe
 */
public class Util {

  private static Set<String> vals = new HashSet<String>(Arrays.asList("5m", "1h", "8h", "1d"));

  public static String adjustedTimeMillis(String input) {
    Long delta = Long.parseLong(input);
    Long epoch = System.currentTimeMillis() / 1000;
    return new Long(epoch + delta).toString();
  }

  public static void illegalArgumentException(String arg) throws IllegalArgumentException {
    throw new IllegalArgumentException(arg);

  }

  public static String transformDuration(String value) throws IllegalArgumentException {

    return convertDuration(value);

  }

  public static String transformResolution(String value) throws IllegalArgumentException {
    if (!vals.contains(value)) {
      illegalArgumentException("unsupported resolution: " + value);
    }

    return convertDuration(value);
  }

  private static String convertDuration(String value) {
    int idx = value.length() - 1;
    char multiplier = value.charAt(idx);

    Integer numeric = Integer.parseInt(value.substring(0, idx));
    switch (multiplier) {
      case 'm':
        numeric *= 60;
        break;
      case 'h':
        numeric *= 3600;
        break;
      case 'd':
        numeric *= (3600 * 24);
        break;
      default:
        throw new IllegalArgumentException("invalid duration specifier: " + multiplier);
    }
    return numeric.toString();
  }
}
