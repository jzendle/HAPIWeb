/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.level3.hiper.hapi.util;

import com.level3.hiper.hapi.velocity.input.ArgBinder;
import com.level3.hiper.hapi.velocity.input.ResolutionArg;
import com.level3.hiper.hapi.velocity.input.StringArg;
import com.level3.hiper.hapi.velocity.input.TimestampArg;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.json.simple.JSONArray;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author zendle.joe
 */
public class VelocityDbTest {

	static Connection connection = null;

	public VelocityDbTest() {
	}

	@BeforeClass
	public static void setUpClass() throws ClassNotFoundException, SQLException {

		String className = "com.vertica.jdbc.Driver";

        String url = "jdbc:vertica://dbavertdalplp61.twtelecom.com:5433/NPMP";
//		String url = "jdbc:vertica://invertidcplp301.twtelecom.com:5433/NPMP";
		String user = "npm_locator";
		String password = "l0cateme";
		connection = JDBC.getConnection(className, url, user, password);

		Properties p = new Properties();
		String path = "./target/test-classes";
		p.setProperty("file.resource.loader.path", path);
		Velocity.init(p);

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
	// @Test
	// public void hello() {}
	@Test
	public void hello3() throws SQLException {
		VelocityContext ct = new VelocityContext();

		TimestampArg min = new TimestampArg("1441218921");
		TimestampArg max = new TimestampArg("1441222521");
		ResolutionArg res = new ResolutionArg("5m");
		ResolutionArg interval = new ResolutionArg("5m");

		ct.put("minimum", min);
		ct.put("maximum", max);
		ct.put("domain", new StringArg("tw telecom - public"));
		ct.put("service", new StringArg("19/HCFS/110374/TWCS"));
		ct.put("resolution", res);
		ct.put("interval", interval);

		ArgBinder toBind = new ArgBinder();
		ct.put("bind", toBind);

		String tmpl = "SELECT /*+label(jz)*/\n"
			+ "	d.stamp    AS stamp,\n"
			+ "	DECODE(\n"
			+ "      (SUM(d.tx) = 0) AND (count(d.tx) = 1), \n"
			+ "      TRUE, \n"
			+ "		0,\n"
			+ "		ROUND(SUM(d.tx), 3)\n"
			+ "   )               AS tx,\n"
			+ "   DECODE(\n"
			+ "   	(SUM(d.rx) = 0) AND (count(d.rx) = 1),\n"
			+ "   	TRUE, \n"
			+ "		0,\n"
			+ "		ROUND(SUM(d.rx), 3)\n"
			+ "   )               AS rx\n"
			+ "FROM\n"
			+ "(\n"
			+ "	(\n"
			+ "	   SELECT\n"
			+ "	      EXTRACT(EPOCH FROM (ts AT TIMEZONE 'UTC'))::INT AS stamp,\n"
			+ "	      0                             AS tx,\n"
			+ "	      0                             AS rx\n"
			+ "	   FROM\n"
			+ "	   (\n"
			+ "	      SELECT TO_TIMESTAMP_TZ( #bind ( $minimum.defaultCurrent('-86400') ) ) AT TIMEZONE 'UTC'  AS tstamp\n"
			+ "	      UNION\n"
			+ "	      SELECT TO_TIMESTAMP_TZ( #bind ( $maximum.defaultCurrent )) AT TIMEZONE 'UTC'       AS tstamp\n"
			+ "	   ) as expected_hours\n"
			+ "	   TIMESERIES ts AS '$interval.asSeconds seconds' OVER (ORDER BY tstamp)\n"
			+ "	) \n"
			+ "	UNION\n"
			+ "	(\n"
			+ "		SELECT\n"
			+ "			d.stamp                 AS stamp,\n"
			+ "			AVG(d.tx)               AS tx,\n"
			+ "			AVG(d.rx)               AS rx\n"
			+ "		FROM\n"
			+ "		(\n"
			+ "			SELECT\n"
			+ "				CEILING(\n"
			+ "					m.stamp/ $interval.asSeconds\n"
			+ "				) * $interval.asSeconds\n"
			+ "	                                 AS stamp,\n"
			+ "				m.bit_tx/n.packet_tx    AS tx,\n"
			+ "				m.bit_rx/n.packet_rx    AS rx\n"
			+ "			FROM\n"
			+ "			(\n"
			+ "				SELECT\n"
			+ "					d.stamp         AS stamp,\n"
			+ "					d.snmp_instance AS snmp_instance,\n"
			+ "					MAX(\n"
			+ "						DECODE(\n"
			+ "							c.metric, \n"
			+ "							'Bits / Second - TX',\n"
			+ "							d.value\n"
			+ "						)\n"
			+ "					)               AS bit_tx,\n"
			+ "					MAX(\n"
			+ "						DECODE(\n"
			+ "							c.metric, \n"
			+ "							'Bits / Second - RX',\n"
			+ "							d.value\n"
			+ "						)\n"
			+ "					)               AS bit_rx\n"
			+ "				FROM\n"
			+ "					npm_dba.circuit_meta            c,\n"
			+ "					npm_dba.snmp_data_$resolution d\n"
			+ "				WHERE\n"
			+ "					c.metric             IN (\n"
			+ "						'Bits / Second - TX',\n"
			+ "						'Bits / Second - RX'\n"
			+ "					)\n"
			+ "				AND\n"
			+ "					c.domain              = lower(  #bind ( $domain.defaultTo( 'tw telecom - public') ) )\n"
			+ "				AND\n"
			+ "					c.circuit_identifier  = upper( #bind ( $service ) )\n"
			+ "				AND\n"
			+ "					c.snmp_object                = d.snmp_object\n"
			+ "				AND\n"
			+ "					c.snmp_instance              = d.snmp_instance\n"
			+ "				AND\n"
			+ "					c.min_stamp                 <= d.stamp\n"
			+ "				AND\n"
			+ "					c.max_stamp                 >= d.stamp\n"
			+ "				AND\n"
			+ "					d.stamp                     >=  #bind ( $minimum.defaultCurrent('-86400'))\n"
			+ "				AND\n"
			+ "					d.stamp                     <= #bind ( $maximum.defaultCurrent )\n"
			+ "				GROUP BY\n"
			+ "					d.snmp_instance, d.stamp\n"
			+ "				ORDER BY\n"
			+ "					d.stamp\n"
			+ "				) m LEFT JOIN\n"
			+ "				(\n"
			+ "					SELECT\n"
			+ "						d.stamp         AS stamp,\n"
			+ "						d.snmp_instance AS snmp_instance,\n"
			+ "						SUM(\n"
			+ "							DECODE(\n"
			+ "								REGEXP_LIKE(c.metric, 'Packets / Second - TX'), \n"
			+ "								TRUE,\n"
			+ "								d.value\n"
			+ "							)\n"
			+ "						)               AS packet_tx,\n"
			+ "						SUM(\n"
			+ "							DECODE(\n"
			+ "								REGEXP_LIKE(c.metric, 'Packets / Second - RX'), \n"
			+ "								TRUE,\n"
			+ "								d.value\n"
			+ "							)\n"
			+ "						)               AS packet_rx\n"
			+ "					FROM\n"
			+ "						npm_dba.circuit_meta            c\n"
			+ "					JOIN\n"
			+ "						npm_dba.snmp_data_$resolution d\n"
			+ "					ON\n"
			+ "					(\n"
			+ "							c.metric             IN (\n"
			+ "								'Unicast Packets / Second - TX',\n"
			+ "								'Unicast Packets / Second - RX',\n"
			+ "								'Multicast Packets / Second - TX',\n"
			+ "								'Multicast Packets / Second - RX',\n"
			+ "								'Broadcast Packets / Second - TX',\n"
			+ "								'Broadcast Packets / Second - RX'\n"
			+ "							)\n"
			+ "						AND\n"
			+ "							c.domain              = lower( #bind ( $domain.defaultTo( 'tw telecom - public') ) )\n"
			+ "						AND\n"
			+ "							c.circuit_identifier  = upper( #bind ( $service ) )\n"
			+ "						AND\n"
			+ "							c.snmp_object                = d.snmp_object\n"
			+ "						AND\n"
			+ "							c.snmp_instance              = d.snmp_instance\n"
			+ "						AND\n"
			+ "							d.stamp                     >= #bind ( $minimum.defaultCurrent('-86400'))\n"
			+ "						AND\n"
			+ "							d.stamp                     <= #bind ( $maximum.defaultCurrent )\n"
			+ "						AND\n"
			+ "							c.min_stamp                 <= d.stamp\n"
			+ "						AND\n"
			+ "							c.max_stamp                 >= d.stamp\n"
			+ "					)\n"
			+ "					GROUP BY\n"
			+ "						d.snmp_instance, d.stamp\n"
			+ "					ORDER BY\n"
			+ "						d.stamp\n"
			+ "				) n ON ( \n"
			+ "						m.snmp_instance = n.snmp_instance\n"
			+ "					AND\n"
			+ "		  				m.stamp         = n.stamp\n"
			+ "				)\n"
			+ "			WHERE\n"
			+ "				n.packet_rx IS NOT NULL\n"
			+ "			AND\n"
			+ "				n.packet_tx IS NOT NULL\n"
			+ "			AND \n"
			+ "				n.packet_rx != 0\n"
			+ "			AND\n"
			+ "				n.packet_tx != 0\n"
			+ "		) d\n"
			+ "		GROUP BY\n"
			+ "			d.stamp\n"
			+ "		ORDER BY\n"
			+ "			d.stamp \n"
			+ "	)\n"
			+ "	ORDER BY\n"
			+ "		stamp\n"
			+ ") d\n"
			+ "WHERE\n"
			+ "	d.stamp                     >= #bind ( $minimum.defaultCurrent('-86400'))\n"
			+ "AND\n"
			+ "	d.stamp                     <= #bind ( $maximum.defaultCurrent )\n"
			+ "GROUP BY \n"
			+ "	d.stamp\n"
			+ "ORDER BY\n"
			+ "	d.stamp\n"
			+ "";
//        String tmpl = "     SELECT TO_TIMESTAMP_TZ( #bind ( $minimum.defaultCurrent ) ) AT TIMEZONE 'UTC'  AS tstamp\n"
//                + "	      UNION\n"
//                + "	      SELECT TO_TIMESTAMP_TZ( #bind ( $maximum.defaultCurrent( '-3456' ) ) ) AT TIMEZONE 'UTC'       AS tstamp";

		StringWriter out = new StringWriter();

		try {
			Velocity.evaluate(ct, out, " LOGGER ", tmpl);
		} catch (Exception ex) {
			Logger.getLogger(VelocityDbTest.class.getName()).log(Level.SEVERE, null, ex);
		}

		System.out.println(out);

		System.out.println("bound variables: " + ct.get("bind"));

		Collection bindVals = toBind.getValues();

        // assertTrue(bindVals.size() == 2);
		PreparedStatement ps = connection.prepareStatement(out.toString());

		ps = JDBC.populate(ps, bindVals);

		try (ResultSet rs = ps.executeQuery()) {
			JSONArray json = ResultSetConverter.convert(rs);
			System.out.println("response: " + json);
		}

	}
	@Test
	public void hello4() throws SQLException {

	}

}
