package com.expandURL.URLEXPANDER;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class ExpandURLServlet
 */
@WebServlet("/ExpandURLServlet")
public class ExpandURLServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExpandURLServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.setContentType("text/plain");
		String ur="";
		URL url=null;
		try{
			ur=req.getParameter("url");
			url=new URL(ur);
		try{
			URLConnection conn=url.openConnection();
			conn.getHeaderFields();
			//System.out.println(conn.getURL().toString().replaceAll("/", "\\\\/"));
			JSONObject json=new JSONObject();
			json.put("end_url", conn.getURL().toString().replaceAll("/", "\\/"));
			json.put("start_url", url.toString().replaceAll("/", "\\/"));
			json.put("status", "OK");
			if(conn.getURL().toString().trim().equals(url.toString().trim()))
				json.put("redirects", "0");
			else
				json.put("redirects", "1");
			resp.getWriter().println(json.toString());
		}
		catch (Exception e) {

			// TODO: handle exception
			try{
				URLConnection conn=url.openConnection();
				conn.getHeaderFields();
				//System.out.println(conn.getURL().toString().replaceAll("/", "\\\\/"));
				JSONObject json1=new JSONObject();
				json1.put("end_url", conn.getURL().toString().replaceAll("/", "\\/"));
				json1.put("start_url", url.toString().replaceAll("/", "\\/"));
				json1.put("status", "OK");
				if(conn.getURL().toString().trim().equals(url.toString().trim()))
					json1.put("redirects", "0");
				else
					json1.put("redirects", "1");
				//System.out.println("iuouo");
				resp.getWriter().println(json1.toString());
			}
			catch (Exception ef) {
				try {
					JSONObject json2=new JSONObject();
					json2.put("start_url", url.toString().replaceAll("/", "\\/"));
					json2.put("end_url", url.toString().replaceAll("/", "\\/"));
					json2.put("redirects", "0");
					json2.put("status", "Invalid");
					resp.getWriter().println(json2.toString());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				// TODO: handle exception

			}
		}
		}
		catch (Exception e) {
			JSONObject json2=new JSONObject();
			try {
				if(ur==null)
					ur="";
				json2.put("start_url", ur.toString().replaceAll("/", "\\/"));
				json2.put("end_url", ur.toString().replaceAll("/", "\\/"));
				json2.put("redirects", "0");
				json2.put("status", "Invalid");
				resp.getWriter().println(json2.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// TODO: handle exception
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
