package be.ipl.tc.servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.ipl.tc.exceptions.ArgumentInvalideException;
import be.ipl.tc.exceptions.VoitureException;
import be.ipl.tc.sessions.SessionManager;
import be.ipl.tc.usecases.GestionVoitures;

/**
 * Servlet implementation class PlacementVoiture
 */
@WebServlet("/placer")
public class PlacementVoiture extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PlacementVoiture() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@EJB GestionVoitures gestionVoituresUCC;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!SessionManager.isNameSet(request.getSession(true))) {
			response.sendRedirect("index.html");
		} else {
			try {
				gestionVoituresUCC.placerVoiture(Integer.parseInt(request.getParameter("idpartie")),Integer.parseInt(request.getParameter("idjoueur")), request.getParameter("voiture"), Integer.parseInt(request.getParameter("l")), Integer.parseInt(request.getParameter("c")), Integer.parseInt(request.getParameter("d")));
			} catch (NumberFormatException e) {
				response.getWriter().write(e.getMessage());  //Message brut reçu en AJAX
			} catch (ArgumentInvalideException e) {
				response.getWriter().write(e.getMessage()); //Message brut reçu en AJAX
			} catch (VoitureException e) {
				response.getWriter().write(e.getMessage()); //Message brut reçu en AJAX
			}
		}
	}

}
