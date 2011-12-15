package be.ipl.tc.servlets;
 
import java.io.IOException;
 
import javax.jms.Session;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.ipl.tc.sessions.SessionManager;

public class Controleur extends HttpServlet implements Servlet {

    private static final long serialVersionUID = 1L;
 
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse reponse)
            throws ServletException, IOException {
    	SessionManager.checkSession(request, reponse, getServletContext());
    }
}