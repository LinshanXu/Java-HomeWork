package com.huilv.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huilv.service.MoneyService;

public class ServletHuiLv extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		double money = Double.parseDouble(req.getParameter("money"));
		String currency = req.getParameter("currency");
		System.out.println("***money="+money+" currency="+currency);
		
		//调取service
		MoneyService ms = new MoneyService();
		String value=ms.change(money,currency);
		String rtnValue="0";
		String msg="失败";
		if(value!=null){
			rtnValue="1";
			msg="成功,可换"+value;
		}
		resp.setContentType("text/xml;charset=utf-8");
		resp.setHeader("Cache-Control", "no-cache");
		
		StringBuffer sb = new StringBuffer();
		sb.append("{flg:"+rtnValue+",msg:'"+msg+"'}");
		PrintWriter out = resp.getWriter();
		out.print(sb.toString());
		out.flush();
		out.close();
	}

}
