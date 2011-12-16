package be.ipl.tc.servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.ipl.tc.domaine.Partie;
import be.ipl.tc.sessions.SessionManager;
import be.ipl.tc.usecases.GestionParties;

/**
 * Servlet implementation class CreePartie
 */
@WebServlet("/cree.html")
public class CreePartie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */	
    public CreePartie() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

    @EJB private GestionParties gestionPartiesUCC;	
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!SessionManager.isNameSet(request.getSession(true))) {
				response.sendRedirect("index.html");
		} else {
			System.out.println(SessionManager.getNom(request.getSession()));
			System.out.println(request.getParameter("nompartie"));
			gestionPartiesUCC.creerPartie(SessionManager.getNom(request.getSession()), request.getParameter("nompartie"));
		}

		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
