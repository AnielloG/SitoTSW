package Control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.AcquistoDao;
import Model.Cart;


/**
 * Servlet implementation class AcquistoControl
 */
@WebServlet("/AcquistoControl")
public class AcquistoControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	AcquistoDao	model = new AcquistoDao();
    public AcquistoControl() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    Cart cart = (Cart)request.getSession().getAttribute("cart");
	    String action = request.getParameter("action");

	    if (action != null) {
	        
	        if (action.equalsIgnoreCase("visualizza")) {
	            if(cart.getProducts().size() == 0) {
	                request.setAttribute("errore", "Il carrello è vuoto. Si prega di inserire almeno un prodotto prima di procedere all'acquisto.\n");
	                RequestDispatcher dispatcher = request.getRequestDispatcher("/product?action=viewC");
	                dispatcher.forward(request, response);
	            }
	            
	            if(request.getSession().getAttribute("email") != null) {
	                String pagamento = request.getParameter("Pagamento");
	                String numeroCarta = request.getParameter("card-number");
	                String titolareCarta = request.getParameter("card-holder");
	                String scadenzaCarta = request.getParameter("expiration-date");
	                String cvv = request.getParameter("cvv");
	                String TipoC = request.getParameter("delivery-option");
	                String indirizzo = request.getParameter("address");
	                String citta = request.getParameter("citta");
	                String cap = request.getParameter("cap");
	                String provincia = request.getParameter("provincia");

	                // Set attributi da passare a ConfermaOrdine.jsp
	                request.setAttribute("provincia", provincia);
	                request.setAttribute("citta", citta);
	                request.setAttribute("cap", cap);
	                request.setAttribute("indirizzo", indirizzo);
	                request.setAttribute("TipoC", TipoC);
	                request.setAttribute("cart", cart);
	                request.setAttribute("Pagamento", pagamento);
	                request.setAttribute("numeroCarta", numeroCarta);
	                request.setAttribute("card-holder", titolareCarta);
	                request.setAttribute("scadenzaCarta", scadenzaCarta);
	                request.setAttribute("cvv", cvv);
	                
	                RequestDispatcher dispatcher = request.getRequestDispatcher("/ConfermaOrdine.jsp");
	                dispatcher.forward(request, response);
	            } else {
	                request.setAttribute("errore3", "Per completare la procedura devi accedere");
	                request.getRequestDispatcher("/Accedi.jsp").forward(request, response);
	            }
	        }

	        if (action.equalsIgnoreCase("completa")) {
	            String pagamento = request.getParameter("Pagamento");
	            String numeroCarta = request.getParameter("NumeroC");
	            String titolareCarta = request.getParameter("TitolareC");
	            String scadenzaCarta = request.getParameter("ScadenzaC");
	            String cvv = request.getParameter("cvv");
	            String TipoC = request.getParameter("Tipocons");
	            String indirizzo = request.getParameter("indirizzo");
	            String citta = request.getParameter("citta");
	            String cap = request.getParameter("cap");
	            String provincia = request.getParameter("provincia");
	            String email = request.getParameter("email");

	            // Inserimento dell'ordine nel database (chiamata al modello)
	            try {
	                model.inserimentoaq(provincia, indirizzo, cap, citta, cart, email, pagamento, numeroCarta, titolareCarta, scadenzaCarta, cvv);
	                
	                // Controllo se il carrello è stato svuotato correttamente come validazione
	                cart.deleteAllProduct();
	                if (cart.getProducts().size() == 0) {
	                    request.setAttribute("successMessage", "Acquisto andato a buon fine.");
	                } else {
	                    request.setAttribute("errorMessage", "Acquisto non andato a buon fine.");
	                }

	                request.setAttribute("cart", cart);
	            } catch (Exception e) {
	                // Gestione di eventuali errori che potrebbero accadere durante l'inserimento
	                request.setAttribute("errorMessage", "Errore durante l'elaborazione dell'acquisto: " + e.getMessage());
	            }

	            // Reindirizza l'utente alla Home con il messaggio appropriato
	            RequestDispatcher dispatcher = request.getRequestDispatcher("/Home.jsp");
	            dispatcher.forward(request, response);
	        }
	    }
	}

}
