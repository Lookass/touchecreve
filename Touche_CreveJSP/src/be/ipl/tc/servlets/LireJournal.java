package be.ipl.tc.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import be.ipl.tc.domaine.TentativeCrevaison;

import be.ipl.tc.domaine.Joueur;
import be.ipl.tc.domaine.Partie;
import be.ipl.tc.sessions.SessionManager;
import be.ipl.tc.usecases.GestionParties;
import be.ipl.tc.usecases.GestionTentativesCrevaison;

/**
 * Servlet implementation class LireJournal
 */
@WebServlet("/journal.html")
public class LireJournal extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LireJournal() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    @EJB GestionTentativesCrevaison gestionTC;
    @EJB private GestionParties gestionPartiesUCC;	
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!SessionManager.isNameSet(request.getSession(true))) {
			response.sendRedirect("index.html");
		} else {
			/* Mock up object 
			List<TentativeCrevaison> mvt  = new ArrayList<TentativeCrevaison>();
			TentativeCrevaison tc = new TentativeCrevaison(1,1);
			tc.setEtatTentative(0);
			mvt.add(tc);
			tc = new TentativeCrevaison(1,5);
			tc.setEtatTentative(2);
			mvt.add(tc);
			tc = new TentativeCrevaison(4,8);
			tc.setEtatTentative(1);
			mvt.add(tc);
			tc = new TentativeCrevaison(0,9);
			tc.setEtatTentative(1);
			mvt.add(tc);
			tc = new TentativeCrevaison(7,8);
			tc.setEtatTentative(0);
			mvt.add(tc);
			tc = new TentativeCrevaison(4,5);
			tc.setEtatTentative(0);
			mvt.add(tc);
			Partie x = new Partie(new Joueur("Yassan"), "Partie de Yassan");
			x.ajouterJoueurBleu(new Joueur("Un Inconnu"));
			*/
			
			List<Partie> partieTerminee = gestionPartiesUCC.listerPartiesTerminees();
			Partie partie = null;
			for (Partie pt : partieTerminee) {
				if (pt.getId() == Integer.parseInt(request.getParameter("idpartie"))) {
					partie = pt;
					break;
				}
			}
			List<TentativeCrevaison> mvt  = gestionTC.listerTentatives(partie.getId());
			
			request.setAttribute("partie", partie);
			request.setAttribute("mouvements", mvt);
			
			RequestDispatcher rd = getServletContext().getNamedDispatcher("Journal");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
