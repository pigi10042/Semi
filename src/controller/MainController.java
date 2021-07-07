package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.CalanderDAO;
import dao.MainDAO;
import dao.MemberDAO;
import dao.MyPageDAO;
import dao.ProfileFileDAO;
import dao.inquiredDAO;
import dto.InquireDTO;
import dto.MainDTO;
import dto.MemberDTO;


@WebServlet("*.main")
public class MainController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset= utf-8");
		
		try {
			String requestURI = request.getRequestURI();
			String ctxPath = request.getContextPath();
			String url = requestURI.substring(ctxPath.length());
			MainDAO dao = MainDAO.getInstance();
			MemberDAO memdao = MemberDAO.getInstance();
			System.out.println(url);
			if(url.contentEquals("/writechat.main")) {
				String contents = request.getParameter("writechat");
				String writer = request.getParameter("writer");
				System.out.println(writer);
				
				MemberDTO dto = memdao.getMainInfo(writer);
				String id = dto.getId();
				String kh_class = dto.getKhClass();
				
				int result = dao.writechat(writer, id, contents, kh_class);
				response.sendRedirect(ctxPath+"/main.main");
			} else if(url.contentEquals("/main.main")) {
				
				ProfileFileDAO pfd = ProfileFileDAO.getInstance();
				MyPageDAO mpd = MyPageDAO.getInstance();
				
				String email = (String)request.getSession().getAttribute("login");
				String name = dao.getName(email);
				MemberDTO dto = memdao.getMainInfo(email);
				String khclass = dto.getKhClass();
				String branch = dto.getBranch();
				
				if(pfd.getFile(mpd.getID((String)request.getSession().getAttribute("login"))).getSysName()!=null) {
					request.setAttribute("profile_img",pfd.getFile(mpd.getID((String)request.getSession().getAttribute("login"))));
				} else {
					request.setAttribute("defalut_profile_img","profile.png");
				}
				request.setAttribute("name",name);
	            request.setAttribute("firstlist", dao.likeFacebook(10, 1));
	            request.setAttribute("list", dao.classList(khclass, branch).size());
	            request.getRequestDispatcher("main/main.jsp").forward(request, response);
			}else if(url.contentEquals("/listchat.main")) {
				Gson g = new Gson();
				List<MainDTO> list = dao.likeFacebook(10, Integer.parseInt(request.getParameter("count")));
				String result = g.toJson(list);
				response.getWriter().append(result);
			} else if(url.contentEquals("/calander.main")) {
				CalanderDAO cd = CalanderDAO.getInstance();
				Calendar cal = Calendar.getInstance();
				Calendar caltoGre = new GregorianCalendar();
				int todayDate = cal.get(caltoGre.DATE);
				cal.set(caltoGre.DATE, 1);	
				
				if(request.getParameter("change_month")!=null) {
					cal.set(caltoGre.MONTH,Integer.parseInt(request.getParameter("change_month")));
				}
				List<Integer> day = new ArrayList<Integer>();
				List<Integer> date = new ArrayList<Integer>();
				for(int i=1; i<=cal.getActualMaximum(cal.DATE); i++) {
					cal.set(caltoGre.DATE, i);
					day.add(cal.get(caltoGre.DAY_OF_WEEK));
					date.add(i);
				}
				try {
					System.out.println(cal.get(caltoGre.MONTH)+1);
					System.out.println(cd.getSchedual("E","J",cal.get(caltoGre.MONTH)+1).size());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				request.setAttribute("month",(cal.get(caltoGre.MONTH)+1));
				try {
					request.setAttribute("list", cd.getSchedual("C","J",cal.get(caltoGre.MONTH)+1));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.setAttribute("day", day);
				request.setAttribute("date", date);
				request.setAttribute("today",todayDate);
				request.getRequestDispatcher("calander/calan_der.jsp").forward(request, response);
			}else if(url.contentEquals("/inquired.main")) {
				inquiredDAO iqD = inquiredDAO.getInstance();
				String writer = request.getParameter("writer");
				String major = request.getParameter("major_category");
				String sub = request.getParameter("sub_category");
				String contents = request.getParameter("inquire_contents");
				try {
					int result=iqD.insert(new InquireDTO(writer, major, sub, contents));
					boolean insertResult = result >0 ? true : false;
					System.out.println(String.valueOf(insertResult));
					request.setAttribute("result", insertResult);
					request.getRequestDispatcher("PopResult.jsp").forward(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
