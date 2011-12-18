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
				
				List<Voiture> voitures  = gestionVoituresUCC.getVoitures(Integer.parseInt(request.getParameter("id")), SessionManager.getNom(request.getSession()));
				String responseString = "";
				
				for (Voiture voiture : voitures) {
			     	if (responseString == "")
			     		responseString += voiture.estCrevée() + ";" + voiture.getLigne()  + ";" + voiture.getColonne()  + ";" + voiture.getNbrPneus() + ";" + voiture.getDirection();
			     	else
			     		responseString += ";" + voiture.estCrevée() + ";" + voiture.getLigne()  + ";" + voiture.getColonne()  + ";" + voiture.getNbrPneus() + ";" + voiture.getDirection();
				}
				
                response.getWriter().write(responseString);
			} else if (request.getParameter("id") != null && request.getParameter("action").equals("getTentative")) {
				List<Partie> lp = gestionPartiesUCC.listerParties();
				boolean isRed = false;

				for (Partie partie : lp) {
					if (partie.getId() == Integer.parseInt(request.getParameter("id"))) {
						if (partie.getJoueurRouge().getNom().equals(SessionManager.getNom(request.getSession()))) 
							isRed = true;
						break;
					}
				}
				
				List<TentativeCrevaison> tcs = gestionTentativesUCC.listerTentatives(Integer.parseInt(request.getParameter("id")));
				String responseString = "";
				int i = 0;
				
				for (TentativeCrevaison tentativeCrevaison : tcs) {
					String classe = "";
					if (isRed) {
						if (i%2 == 0)
							classe = "D";
						else
							classe = "G";
					} else {
						if (i%2 == 0)
						classe = "G";
							else
						classe = "D";
					}
					if (responseString == "")
			     		responseString += classe + ";" + tentativeCrevaison.getColonne() + ";" + tentativeCrevaison.getLigne() + ";" + String.valueOf(tentativeCrevaison.getEtatTentative());
			     	else
			     		responseString += ";" + classe + ";" + tentativeCrevaison.getColonne() + ";" + tentativeCrevaison.getLigne() + ";" + String.valueOf(tentativeCrevaison.getEtatTentative());
					i++;
				}
				   response.getWriter().write(responseString);
			}
		}
	}

}
