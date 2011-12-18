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
import be.ipl.tc.usecases.GestionParties;
import be.ipl.tc.usecases.GestionTentativesCrevaison;

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
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("id") != null && request.getParameter("action") == "getEtat") {
			List<Partie> lp = gestionPartiesUCC.listerParties();
			for (Partie partie : lp) {
				if (partie.getId() == Integer.parseInt(request.getParameter("id"))) {
					response.getWriter().write(partie.getEtat().name());
				}
			}
		} else if (request.getParameter("id") != null && request.getParameter("action") == "getTour") {
			List<Partie> lp = gestionPartiesUCC.listerParties();
			for (Partie partie : lp) {
				if (partie.getId() == Integer.parseInt(request.getParameter("id"))) {
					List<TentativeCrevaison> lt = gestionTentativesUCC.listerTentatives(partie.getId());
					if (lt.size()%2 == 0) { //Tour de joueur rouge
						response.getWriter().write(partie.getJoueurRouge().getNom()); //Réponse brut pour AJAX
					} else { //Tour de joueur bleu
						response.getWriter().write(partie.getJoueurBleu().getNom()); //Réponse brut pour AJAX
					}
				}
			}
		}
		
	}

}
