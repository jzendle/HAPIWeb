/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.level3.hiper.hapi.util;

import com.level3.hiper.hapi.velocity.input.ResolutionArg;
import com.level3.hiper.hapi.velocity.input.StringArg;
import com.level3.hiper.hapi.velocity.input.TimestampArg;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author zendle.joe
 */
public class VelocityTest {

    Connection conn;

    public class Bind {

        List<String> vals = new ArrayList();

        public void addValue(String val) {
            System.out.println("adding " + val);
            vals.add(val);
        }

        @Override
        public String toString() {
            return vals.toString();
        }

    }

    public class VTest {

        public String getName() {
            return "fred";
        }

        public String getMessage() {
            return "Hello";
        }

        public String getMessage(String who) {
            return "Hello " + who;
        }

        public List getNames() {
            return Arrays.asList("one", "two", "three");
        }

        public void setName(String t) {
        }

        public void setMessage(String who) {
        }

        @Override
        public String toString() {
            return "VTest.toString()";
        }
    }

    public VelocityTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        try {
            Properties p = new Properties();
            URL location = VelocityTest.class.getProtectionDomain().getCodeSource().getLocation();
            System.out.println(location.getFile());

            String path = "./target/test-classes";
            p.setProperty("file.resource.loader.path", path);
            Velocity.init(p);

            ClassLoader cl = ClassLoader.getSystemClassLoader();

            URL[] urls = ((URLClassLoader) cl).getURLs();

            for (URL url : urls) {
                System.out.println(url.getFile());
            }
        } catch (Exception ex) {
            Logger.getLogger(VelocityTest.class.getName()).log(Level.SEVERE, null, ex);
        }
//        try {
//            // The newInstance() call is a work around for some
//            // broken Java implementations
//
//            Class.forName("com.mysql.jdbc.Driver").newInstance();
//            conn = DriverManager.getConnection("jdbc:mysql://localhost/test?user=root");
//
//        } catch (Exception ex) {
//            // handle the error
//        }

    }

    @After
    public void tearDown() {
    }

    // @Test
    public void hello() {
        VelocityContext ct = new VelocityContext();

	    Object put = ct.put("test", new VTest());
	    Object put1 = ct.put("bind", new Bind());

        String tmpl = "select * from test.test where name = #bind ( $test.getMessage('Test') )  and name != #bind ( $test.value )";

        StringWriter out = new StringWriter();

        try {
            Velocity.evaluate(ct, out, "JZ was here", tmpl);
        } catch (ParseErrorException | MethodInvocationException | ResourceNotFoundException ex) {
            Logger.getLogger(VelocityTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println(out);

        System.out.println("bind: " + ct.get("bind"));

    }

    @Test
    public void hello3() {
        VelocityContext ct = new VelocityContext();
        
        TimestampArg min = new TimestampArg("1440433779");
        TimestampArg max = new TimestampArg(null);
        ResolutionArg res = new ResolutionArg("5m");

        ct.put("minimum", min);
        ct.put("maximum", max);
        ct.put("domain", new StringArg("fred"));
        ct.put("resolution", res);
        ct.put("bind", new Bind());
//        String tmpl = "select #replace ( $resolution '5m' 'd.true' 'd.false' ) from test.test where domain = lower( $domain.defaultTo('tw telecom - public')) ts > #bind ( $minimum.defaultCurrent ) and ts < #bind ( $maximum.defaultCurrent( '-3456' ) )";
        String tmpl = "select '#replace ( $resolution '5m' 'd.true' 'd.false' )' " + 
                "from test.test where domain = lower( #default ( $domain 'tw telecom - public' )) and " + 
                "ts > #bind ( $minimum.defaultCurrent ) and ts < #bind ( $maximum.defaultCurrent( '-3456' ) )";
//        String tmpl = "select * from test.test where ts > #bind ( $minimum ) and ts < #bind ( #default ( $minimum $minimum ) )";
//        String tmpl = "select #default ( $minimum $minimum  )";

        StringWriter out = new StringWriter();

        try {
            Velocity.evaluate(ct, out, " LOGGER ", tmpl);
        } catch (Exception ex) {
            Logger.getLogger(VelocityTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println(out);

        System.out.println("bind: " + ct.get("bind"));

    }

    //@Test
    public void hello2() throws Exception {

        VelocityContext ct = new VelocityContext();

        ct.put("test", new VTest());
        ct.put("bind", new Bind());

        StringWriter out = new StringWriter();

        Template t = Velocity.getTemplate("test.vm");

        t.merge(ct, out);
        System.out.println(out);

        System.out.println("bind: " + ct.get("bind"));

    }

}
