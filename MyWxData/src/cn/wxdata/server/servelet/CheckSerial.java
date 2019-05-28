package cn.wxdata.server.servelet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import cn.wxdata.server.dao.SerialsRelateDao;

/**
 * Servlet implementation class CheckSerial
 */
public class CheckSerial extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String msgstr=request.getParameter("msg");
			JSONObject requestJson=new JSONObject(msgstr);
			String cmd=requestJson.get("command").toString();
			if(!cmd.equals("CheckSerial")) {
				JSONObject sJsonObject=new JSONObject();
				sJsonObject.put("status", "1001");
				sJsonObject.put("msg", "请求命令出错");
				response.getWriter().append(sJsonObject.toString());
				return;
			}
			JSONObject msgJson=requestJson.getJSONObject("params");
			String serialstr=msgJson.get("serial").toString();
			int result=SerialsRelateDao.checkSerials(serialstr);
			JSONObject returnJson=new JSONObject();
			if(result==0) {
				returnJson.put("status", "1002");
				returnJson.put("msg", "注册码不正确");
				response.getWriter().append(returnJson.toString());
				
			}else if(result==1) {
				returnJson.put("status", "1003");
				returnJson.put("msg", "注册码已经被激活");
				response.getWriter().append(returnJson.toString());
			}else if(result==2) {
				returnJson.put("status", "1000");
				returnJson.put("msg", "激活码可以使用");
				response.getWriter().append(returnJson.toString());
			}else if(result==-1) {
				returnJson.put("status", "-999");
				returnJson.put("msg", "服务器异常");
				response.getWriter().append(returnJson.toString());
			}
		} catch (Exception e) {
			// TODO: handle exception
			JSONObject requestJson=new JSONObject();
			requestJson.put("status", "-999");
			requestJson.put("msg", "服务器异常");
			response.getWriter().append(requestJson.toString());
		}
		
		
	}

}
