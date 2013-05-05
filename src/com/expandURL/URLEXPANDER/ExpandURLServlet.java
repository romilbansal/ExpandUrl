package com.expandURL.URLExpander;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.http.*;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

@SuppressWarnings("serial")
public class ExpandURLServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		String shortURL="";
		String callback="";
		URL url=null;
		boolean flag = true;
		try{
			shortURL=req.getParameter("url");
			url=new URL(shortURL);
			callback=req.getParameter("callback");
			if(callback==null||callback.equals("null"))
				flag=false;
			//the first attempt (of two attempts) to expand the URL making request to site
			//	
			try{
				URLConnection conn=url.openConnection();
				conn.getHeaderFields();
				JSONObject json=new JSONObject();
				json.put("end_url", conn.getURL().toString().replaceAll("/", "\\/"));
				json.put("start_url", url.toString().replaceAll("/", "\\/"));
				json.put("status", "OK");
				if(conn.getURL().toString().trim().equals(url.toString().trim()))
					json.put("redirects", "0");
				else
					json.put("redirects", "1");
				if(flag)
					resp.getWriter().println(callback+"("+json.toString()+")");
				else
					resp.getWriter().println(json.toString());
			}
			catch (Exception e) {

				// The first attempts fails//
				//The second attempt (of two attempts) to expand the URL
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
					if(flag)
						resp.getWriter().println(callback+"("+json1.toString()+")");
					else
						resp.getWriter().println(json1.toString());
				}

				//Failed to expand the page
				//return default
				catch (Exception ef) {
					try {
						JSONObject json2=new JSONObject();
						json2.put("start_url", url.toString().replaceAll("/", "\\/"));
						json2.put("end_url", url.toString().replaceAll("/", "\\/"));
						json2.put("redirects", "0");
						json2.put("status", "Invalid");
						if(flag)
							resp.getWriter().println(callback+"("+json2.toString()+")");
						else
							resp.getWriter().println(json2.toString());
					} catch (JSONException e1) {

					}
				}
			}
		}
		//Problem with code
		// return default
		catch (Exception e) {
			JSONObject json2=new JSONObject();
			try {
				if(shortURL==null)
					shortURL="";
				json2.put("start_url", shortURL.toString().replaceAll("/", "\\/"));
				json2.put("end_url", shortURL.toString().replaceAll("/", "\\/"));
				json2.put("redirects", "0");
				json2.put("status", "Invalid");
				if(flag)
					resp.getWriter().println(callback+"("+json2.toString()+")");
				else
					resp.getWriter().println(json2.toString());
			} catch (JSONException e1) {

			}
		}
		//System.out.println(json.toString());
		//resp.getWriter().println("{ end_url:"+conn.getURL().toString().replaceAll("\\", "")+"}");
	}
}
