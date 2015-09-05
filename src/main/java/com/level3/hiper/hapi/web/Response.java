/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.level3.hiper.hapi.web;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zendle.joe encapsulate functionality to build hapi specific response
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

   private String getOwner() {
      return "IN Support <insupport@level3.com>";
   }

   private String getVersion() {
      return "HAPI v2.23.0";
   }

   private String getIdentifier() {
      return UUID.randomUUID().toString();
   }

   private String getHostname () {
      String host = "localhost";
      try {
         host = InetAddress.getLocalHost().getCanonicalHostName();
      } catch (UnknownHostException ex) {
         Logger.getLogger(Response.class.getName()).log(Level.SEVERE, null, ex);
      }
      return host;
   }

   public Map<String,String> makeHeader() {
      Map <String,String> map = new LinkedHashMap();
      map.put("identifier", getIdentifier());
      map.put("hostname", getHostname());
      map.put("version", getVersion());
      map.put("owner", getOwner());
      return map;
   }
   public Map<String,String> makeError() {
      Map <String,String> map = new LinkedHashMap();
      map.put("code", "success");
      return map;
   }


}
