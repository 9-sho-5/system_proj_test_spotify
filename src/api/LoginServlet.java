package api;

import java.io.Writer;
import java.util.Enumeration;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
/**
 * Spotify Login Servlet
 */
@WebServlet("/api/login")
public class LoginServlet extends HttpServlet implements Filter {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
        
		String auhorizeUri = null;

		Spotify spotify = new Spotify();
		auhorizeUri = spotify.authorize();

		response.setHeader("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE, OPTIONS");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept, X-User-Email, X-Auth-Token, X-HTTP-Method-Override, X-Requested-With, api-token");
		response.setHeader("Access-Control-Allow-Credentials", "true");

		// System.out.println(response.containsHeader("Access-Control-Allow-Origin"));
		// 		doOptions(request, response);
		// response.sendRedirect(auhorizeUri);
		// response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
		// response.setHeader("Location", auhorizeUri);
		// response.setContentType("application/json");
		StringBuilder builder = new StringBuilder();
		builder.append('{');
		builder.append("\"uri\":\"").append(auhorizeUri).append("\"");
		builder.append('}');
		String json = builder.toString();
		System.out.println(json);

		Writer writer = response.getWriter();
		writer.append(json);
		writer.flush();
		
    }

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		
	}
}
