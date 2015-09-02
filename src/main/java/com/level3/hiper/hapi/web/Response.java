/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.level3.hiper.hapi.web;

import java.util.Map;
import org.json.simple.JSONObject;

/**
 *
 * @author zendle.joe
 * encapsulate functionality to build hapi specific response
 */
/*

"identifier": "866bc2c3-d77c-4c32-9499-3f3327c3f077",
"host": "inutilidcplp322.twtelecom.com",
"uri": "/hiper/v3/domains/network/services/aggregate/packet-size/time-series?service=19%2FHCFS%2F110374%2FTWCS&minimum=1441218921&maximum=1441222521&resolution=5m&interval=5m",
"version": "HAPI v2.23.0",
"environment": "production",
"owner": "IN Support <insupport@level3.com>",
"error": {},
"data": [],
"columns": 3,
"rows": 12,
"affected": 12,
"cached": true,
"runtime": 0.001263t
*/
public class Response {

	public JSONObject addHeader(JSONObject out, Map headerInfo) {
		return  out;
	}
	
}
