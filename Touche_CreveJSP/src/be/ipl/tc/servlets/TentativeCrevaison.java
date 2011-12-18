package be.ipl.tc.servlets;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.ipl.tc.domaine.Partie;
import be.ipl.tc.sessions.SessionManager;
import be.ipl.tc.usecases.GestionParties;
import be.ipl.tc.usecases.GestionTentativesCrevaison;

/**
 * Servlet implementation class TentativeCrevaison
 */
@WebServlet("/gameaction.html")
public class TentativeCrevaison extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TentativeCrevaison() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!SessionManager.isNameSet(request.getSession(true))) {
			response.sendRedirect("index.html");
		} else {
		
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@EJB GestionTentativesCrevaison gestionTentativesUCC;
	@EJB GestionParties gestionPartiesUCC;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!SessionManager.isNameSet(request.getSession(true))) {
			response.sendRedirect("index.html");
		} else {
			Partie p = null;
			int idJoueur = 0;
			List<Partie> lp = gestionPartiesUCC.listerParties();
			for (Partie partie : lp) {
				if (partie.getId() == Integer.parseInt(request.getParameter("idpartie"))) {
					p = partie;
					if(p.getJoueurRouge().getNom().equals(SessionManager.getNom(request.getSession())))
						idJoueur = p.getJoueurRouge().getIdJoueur();
					else 
						idJoueur = p.getJoueurBleu().getIdJoueur();
					break;
				}
			}
			
			try {
				be.ipl.tc.domaine.TentativeCrevaison tc = gestionTentativesUCC.tenterCrevaison(p.getId(), idJoueur, Integer.parseInt(request.getParameter("l")), Integer.parseInt(request.getParameter("c")));
				response.getWriter().write(String.valueOf(tc.getEtatTentative()));
			} catch (Throwable t){
				response.getWriter().write(t.getMessage());
			}
			
		}
	}

}
