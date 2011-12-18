package be.ipl.tc.sessions;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionManager {
	
	public static boolean isNameSet(HttpSession session) {
		
		if (session.getAttribute("nom") != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public static String getNom(HttpSession session) {
		return (String)session.getAttribute("nom");
	}
	
	public static void setNom(HttpSession session, String nom) {
		session.setAttribute("nom", nom);
	}
	
	public static void checkSession(HttpServletRequest request, HttpServletResponse reponse, ServletContext context)
    throws ServletException, IOException {
        String url = request.getServletPath();
        HttpSession sess = request.getSession(true);
        if (SessionManager.isNameSet(sess)) {
        	RequestDispatcher rd  = context.getRequestDispatcher("/parties.html");
        	rd.forward(request, reponse);
        } else {
            if (url.equals("")) {
                url = "/index.html";
            }
            url = url.substring(1);
             
            RequestDispatcher rd = context.getNamedDispatcher(url);
            if (rd == null) {
                url = "index.html";
                rd = context.getNamedDispatcher(url);
            }
            rd.forward(request, reponse);
        }

	}
}
