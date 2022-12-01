package api;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/logout")
public class LogoutServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        Cookie cookieArray[] = request.getCookies();

        if (cookieArray != null){
            for (int i = 0 ; i < cookieArray.length ; i++){
                if (cookieArray[i].getName().equals("loginStatus")){
                    if(cookieArray[i].getValue().equals("loggedIn")){
                        Cookie cookie = new Cookie("loginStatus", "notLoggedIn");
                        response.addCookie(cookie);
                    }
                }
            }
        }
        
        request.setCharacterEncoding("UTF-8");
		response.sendRedirect("index.html");
		
	}
}
