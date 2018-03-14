package com.cafe24.guestbook.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cafe24.guestbook.dao.GuestBookDao;
import com.cafe24.gusetbook.vo.GuestBookVo;
import com.cafe24.mvc.util.WebUtil;

/**
 * Servlet implementation class GuestBookServlet
 */
@WebServlet("/gs")
public class GuestBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String actionName = request.getParameter("a");
		
		System.out.println(actionName);
		
		if ("deleteform".equals(actionName)) {
			WebUtil.forward(request, response, "/WEB-INF/views/deleteform.jsp");

		} else if ("delete".equals(actionName)) {
			request.setCharacterEncoding("UTF-8");

			String no = request.getParameter("no");
			String password = request.getParameter("password");

			GuestBookVo vo = new GuestBookVo();
			vo.setNo(Long.parseLong(no));
			vo.setPassword(password);

			GuestBookDao dao = new GuestBookDao();
			dao.delete(vo);

			WebUtil.redirect(request, response, "/guestbook2/gs");
		}
		else if ("add".equals(actionName)) {
			request.setCharacterEncoding("UTF-8");

			String name = request.getParameter("name");
			String password = request.getParameter("pass");
			String content = request.getParameter("content");

			GuestBookVo vo = new GuestBookVo();
			vo.setName(name);
			vo.setPassword(password);
			vo.setContent(content);

			GuestBookDao dao = new GuestBookDao();
			dao.insert(vo);

			WebUtil.redirect(request, response, "/guestbook2/gs");
		} else {
			GuestBookDao dao = new GuestBookDao();
			List<GuestBookVo> list = dao.getList();

			request.setAttribute("list", list);

			// 포워딩(forwarding)
			WebUtil.forward(request, response, "/WEB-INF/views/index.jsp");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
