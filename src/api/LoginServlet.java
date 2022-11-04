package api;

import java.io.Writer;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Spotify Login Servlet
 */
@WebServlet("/api/login")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
		String auhorizeUri = null;

		Spotify spotify = new Spotify();
		auhorizeUri = spotify.authorize();

		// CROS設定
		// response.setHeader("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE, OPTIONS");
		// response.setHeader("Access-Control-Allow-Origin", "*");
		// response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept, X-User-Email, X-Auth-Token, X-HTTP-Method-Override, X-Requested-With, api-token");
		// response.setHeader("Access-Control-Allow-Credentials", "true");
		// response.sendRedirect(auhorizeUri);

		StringBuilder builder = new StringBuilder();
		builder.append('{');
		builder.append("\"uri\":\"").append(auhorizeUri).append("\"");
		builder.append('}');
		String json = builder.toString();

		Writer writer = response.getWriter();
		writer.append(json);
		writer.flush();
		
	}
}
