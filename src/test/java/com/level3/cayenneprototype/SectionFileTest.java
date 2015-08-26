/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.level3.cayenneprototype;

import com.level3.hiper.hapi.util.SectionFile;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;
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
public class SectionFileTest {

    public SectionFileTest() {
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
        Map map = null;
        try {
            URL location = SectionFileTest.class.getProtectionDomain().getCodeSource().getLocation();
            System.out.println("current dir: " + location.getFile());
            
            File tt = new File("time-series.conf");
            System.out.println("dddd: " + tt.getAbsolutePath());
            map = SectionFile.parse("./target/test-classes/time-series.conf");
        } catch (IOException ex) {
            Logger.getLogger(SectionFileTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println(StringUtils.join((((List) map.get("read")).toArray()), '\n'));
    }
}
