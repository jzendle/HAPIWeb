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
public class ResolutionArg extends BaseArg {

   private static Set<String> vals = new HashSet<String>(Arrays.asList("5m", "1h", "8h", "24h"));
   Integer seconds;

   @Override
   public Boolean validate() {

      if (!vals.contains(value)) {
         return false;
      }

      int idx = value.length() - 1;
      char multiplier = value.charAt(idx);

      int numeric = Integer.parseInt(value.substring(0, idx));
      seconds = numeric * (multiplier == 'h' ? 3600 : 60);
      return true;
   }

   public ResolutionArg(String in) {
      super(in);
   }

   public Integer getAsSeconds() {
      return seconds;
   }

}
