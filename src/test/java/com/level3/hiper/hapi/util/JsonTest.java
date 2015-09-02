package com.level3.hiper.hapi.util;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author zendle.joe
 */
public class JsonTest {

	public JsonTest() {
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
	@Test
	public void hello() {
		JSONArray map = new JSONArray();
		JSONObject obj = new JSONObject();
		obj.put("name", "foo");
		obj.put("nickname", null);
		//obj.put("nickname", 1);
		obj.put("num", 100);
		obj.put("balance", 1000.21);
		obj.put("is_vip", true);
		for (int i = 0; i < 10; i++) {
			map.add(i, obj);
		}
		JSONObject obj2 = new JSONObject();
		obj2.put("name", "foo");
		obj2.put("nickname", null);
		//o2bj.put("nickname", 1);
		obj2.put("num", 100);
		obj2.put("balance", 1000.21);
		obj2.put("is_vip", true);
		obj2.put("results", map);
		System.out.print(obj2);

	}
}
