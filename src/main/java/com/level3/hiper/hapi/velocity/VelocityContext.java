/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.level3.hiper.hapi.velocity;

import com.level3.hiper.hapi.velocity.input.ArgBinder;
import java.util.Collection;

/**
 *
 * @author zendle.joe
 */
public class VelocityContext extends org.apache.velocity.VelocityContext {

  ArgBinder binder = new ArgBinder();

  public VelocityContext() {
    super();
    this.put("__bind__", binder); // "__bind__" is referenced in the #bind velocity macro defined in the 'system library' 
    this.put("__util__", com.level3.hiper.hapi.velocity.input.Util.class);
  }

  public Collection<String> getBoundValues() {
    return binder.getValues();
  }

}
