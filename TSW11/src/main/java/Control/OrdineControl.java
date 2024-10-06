package Control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import Model.Ordine;
import Model.OrdineDAO;
import Model.Prodotto;


/**
 * Servlet implementation class OrdineControl
 */
@WebServlet("/OrdineControl")
public class OrdineControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    OrdineDAO ordine = new OrdineDAO();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrdineControl() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");

		if (action != null) {
			
			if (action.equalsIgnoreCase("ViewOrdini")) {
				String email = request.getParameter("email");
				request.setAttribute("ordini",ordine.getOrdini(email));
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Profilo.jsp");
				dispatcher.forward(request, response);
			} 
			else if(action.equalsIgnoreCase("valuta")) {
			    String email = (String) request.getSession().getAttribute("email");
			    int codP = Integer.parseInt(request.getParameter("prodotto"));
			    int val = Integer.parseInt(request.getParameter("stelle"));
			    ordine.ValutaProd(email, codP, val);
			    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/ordine?action=ViewOrdini&email=" + email);
			    dispatcher.forward(request, response);
			}
			else if(action.equalsIgnoreCase("Dettagli")) {
				String email = request.getParameter("email");
		        List<Ordine> ordini = ordine.getOrdini(email); // Recupera gli ordini dal database
		        int numeroOrdine = Integer.parseInt(request.getParameter("NumeroOrdine"));
		        List<Prodotto> prodotti = ordine.getProdotti(numeroOrdine); // Recupera i prodotti associati a un ordine dal database

		        request.setAttribute("ordini", ordini); // Imposta l'attributo "ordini" sulla richiesta
		        request.setAttribute("prodotti", prodotti); // Imposta l'attributo "prodotti" sulla richiesta

		        request.getRequestDispatcher("Profilo.jsp").forward(request, response);
		    }
			else if (action.equalsIgnoreCase("searchByEmail")) {

			    try {
			    	String email = request.getParameter("email");
		            // Esegui la logica per ottenere gli utenti corrispondenti all'email dal tuo database
			        OrdineDAO dao = new OrdineDAO();
			        List<Ordine> ordini = dao.searchByEmail(email);

		            // Converte gli utenti in formato JSON e invia la risposta
		            Gson gson = new Gson();
		            String json = gson.toJson(ordini);
		            response.setContentType("application/json");
		            response.setCharacterEncoding("UTF-8");
		            response.getWriter().write(json);

			    } catch (SQLException e) {
			        e.printStackTrace();
			        request.setAttribute("errore", "Errore del database: " + e.getMessage());
			    }
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
