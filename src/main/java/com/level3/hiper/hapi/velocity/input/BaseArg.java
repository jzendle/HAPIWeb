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

  protected String value="";

  public BaseArg(String input) {
    value = input;
  }

	public BaseArg() {
	}

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return value;
  }

  public Boolean validate() {
    return true;
  }
}
