package be.ipl.tc.servlets;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.ipl.tc.domaine.Partie;
import be.ipl.tc.sessions.SessionManager;
import be.ipl.tc.usecases.GestionParties;

/**
 * Servlet implementation class AfficherPartie
 */
@WebServlet("/game.html")
public class AfficherPartie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AfficherPartie() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@EJB private GestionParties gestionPartiesUCC;	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!SessionManager.isNameSet(request.getSession(true))) {
			response.sendRedirect("index.html");
		} else {
			if (request.getParameter("gameid") != null) {
				List<Partie> lp = gestionPartiesUCC.listerParties();
				for (Partie partie : lp) {
					if (partie.getId() == Integer.parseInt(request.getParameter("gameid"))) {
						RequestDispatcher rd = getServletContext().getNamedDispatcher("Game");
						request.setAttribute("partie", partie);
						rd.forward(request, response);
						return;
					}
				}
			} else {
				response.sendRedirect("index.html");
			}
		}
	}

}
