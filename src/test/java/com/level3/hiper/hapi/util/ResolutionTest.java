/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.level3.hiper.hapi.util;

import com.level3.hiper.hapi.velocity.input.ResolutionArg;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jzendle
 */
public class ResolutionTest {

   public ResolutionTest() {
   }

   @BeforeClass
   public static void setUpClass() {
   }

   @AfterClass
   public static void tearDownClass() {
   }

   @Before
   public void setUp() {
   }

   @After
   public void tearDown() {
   }

   // TODO add test methods here.
   // The methods must be annotated with annotation @Test. For example:
   //
   @Test
   public void hello() {

      ResolutionArg arg = new ResolutionArg("5m");

      assertTrue(arg.validate());
      assertEquals(arg.getAsSeconds(), new Integer(300));

      arg = new ResolutionArg("8h");
      assertTrue(arg.validate());
      assertEquals(arg.getAsSeconds(), new Integer(8 * 3600));

      arg = new ResolutionArg("24h");
      assertTrue(arg.validate());
      assertEquals(arg.getAsSeconds(), new Integer(24 * 3600));

      arg = new ResolutionArg("5d");
      assertFalse(arg.validate());

   }
}
