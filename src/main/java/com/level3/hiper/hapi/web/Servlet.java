/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.level3.hiper.hapi.web;

import com.level3.hiper.hapi.util.JDBC;
import com.level3.hiper.hapi.util.ResultSetConverter;
import com.level3.hiper.hapi.util.SectionFile;
import com.level3.hiper.hapi.velocity.input.ArgBinder;
import com.level3.hiper.hapi.velocity.input.ResolutionArg;
import com.level3.hiper.hapi.velocity.input.StringArg;
import com.level3.hiper.hapi.velocity.input.TimestampArg;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author zendle.joe
 */
@WebServlet(name = "Servlet", urlPatterns = {"/api/*"})
public class Servlet extends HttpServlet {

	@Resource(name = "jdbc/TestDB")
	private DataSource ds;

	private String dir = "/var/tmp";


	/*
	 <resource-ref>
	 <description>DB Connection</description>
	 <res-ref-name>jdbc/TestDB</res-ref-name>
	 <res-type>javax.sql.DataSource</res-type>
	 <res-auth>Container</res-auth>
	 </resource-ref>
	 */
	/**
	 * Processes requests for both HTTP <code>GET</code> and
	 * <code>POST</code> methods.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException, SQLException {
		response.setContentType("application/json;charset=UTF-8");

		Properties p = new Properties();
		String path = "/tmp";
		p.setProperty("file.resource.loader.path", path);
		Velocity.init(p);

		String source = path + request.getRequestURI() + ".conf";
		Logger.getLogger(Servlet.class.getName()).log(Level.INFO, source);

		/*
		 ** mess with args TODO abstract this
		 */
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

		Map map = SectionFile.parse(source);

		String tmpl = StringUtils.join((((List) map.get("read")).toArray()), '\n');

		StringWriter out = new StringWriter();

		try {
			Velocity.evaluate(ct, out, " LOGGER ", tmpl);
		} catch (ParseErrorException | MethodInvocationException | ResourceNotFoundException ex) {
			Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
		}

		System.out.println(out);

		System.out.println("bound variables: " + ct.get("bind"));

		Collection bindVals = toBind.getValues();

      Response rsp = new Response();
		try (Connection conn = ds.getConnection()) {

			try (PreparedStatement ps = conn.prepareStatement(out.toString())) {

				JDBC.populate(ps, bindVals);

				try (ResultSet rs = ps.executeQuery()) {
					List data = ResultSetConverter.convert(rs);
					Map header = rsp.makeHeader();
					Map error = rsp.makeError();

               header.put("error",error);
               header.put("data", data);
               
               // JSONValue.toJSONString() respects ordering provided
               // by linkedhashmap implementations from above
					response.getWriter().write(JSONValue.toJSONString( header ));
				}
			}

		}

	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (Exception ex) {
			Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (Exception ex) {
			Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

}
