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

import com.sun.jndi.toolkit.ctx.PartialCompositeContext;

import be.ipl.tc.domaine.Partie;
import be.ipl.tc.sessions.SessionManager;
import be.ipl.tc.usecases.GestionParties;

/**
 * Servlet implementation class RejoindrePartie
 */
@WebServlet("/join.html")
public class RejoindrePartie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RejoindrePartie() {
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
			if (request.getParameter("gameid") != null) {
				Partie p = null;
				if (request.getParameter("owner") == null) {
					p = gestionPartiesUCC.rejoindrePartie(Integer.parseInt(request.getParameter("gameid")), SessionManager.getNom(request.getSession()));
				} else {
					List<Partie> lp = gestionPartiesUCC.listerParties();
					for (Partie partie : lp) {
						if (partie.getId() == Integer.parseInt(request.getParameter("gameid"))) {
							p = partie;
							break;
						}
					}
				}
				RequestDispatcher rd = getServletContext().getNamedDispatcher("PrepareGame");
				request.setAttribute("partie", p);
				rd.forward(request, response);
			} else {
				response.sendRedirect("index.html");
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
