package be.ipl.tc.servlets;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.ipl.tc.domaine.Partie;
import be.ipl.tc.domaine.Partie.Etat;
import be.ipl.tc.domaine.TentativeCrevaison;
import be.ipl.tc.domaine.Voiture;
import be.ipl.tc.sessions.SessionManager;
import be.ipl.tc.usecases.GestionParties;
import be.ipl.tc.usecases.GestionTentativesCrevaison;
import be.ipl.tc.usecases.GestionVoitures;
import be.ipl.tc.usecasesimpl.GestionVoituresImpl;

/**
 * Servlet implementation class PingPartie
 */
public class PingPartie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PingPartie() {
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
	@EJB GestionParties gestionPartiesUCC;
	@EJB GestionTentativesCrevaison gestionTentativesUCC;
	@EJB GestionVoitures gestionVoituresUCC;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("action") != null) {
			if (request.getParameter("id") != null && request.getParameter("action").equals("getEtat")) {
				List<Partie> lp = gestionPartiesUCC.listerParties();
				for (Partie partie : lp) {
					if (partie.getId() == Integer.parseInt(request.getParameter("id"))) {
						response.getWriter().write(partie.getEtat().name());
					}
				}
			} else if (request.getParameter("id") != null && request.getParameter("action").equals("getTour")) {
				response.getWriter().write(gestionPartiesUCC.getTour(Integer.parseInt(request.getParameter("id"))).getNom());
			} else if (request.getParameter("id") != null && request.getParameter("action").equals("getVoiture")) {
				

                response.getWriter().write(gestionVoituresUCC.getVoitures(Integer.parseInt(request.getParameter("id")), SessionManager.getNom(request.getSession())));
			}
		}
	}

}
