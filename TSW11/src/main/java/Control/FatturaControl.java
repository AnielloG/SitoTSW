package Control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Model.Ordine;
import Model.OrdineDAO;
import Model.Prodotto;
import Model.ProductDao;
import Model.Utente;
import Model.UtenteDao;

public class FatturaControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OrdineDAO ordineDAO;
    private UtenteDao utenteDAO;
    private ProductDao prodottoDAO;

    public void init() throws ServletException {
        ordineDAO = new OrdineDAO();
        utenteDAO = new UtenteDao();
        prodottoDAO = new ProductDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action.equals("viewFattura")) {
            String email = request.getParameter("email");
            int numeroOrdine = Integer.parseInt(request.getParameter("numeroOrdine"));
            try {
                Ordine ordine = ordineDAO.doRetrieveByKey(numeroOrdine, email);
                Utente utente = utenteDAO.doRetrieveByEmail(email);
                Collection<Prodotto> listaProdotti = prodottoDAO.doRetrieveByOrdine(numeroOrdine);
                
                System.out.println("Indirizzo: " + ordine.getIndirizzo());
                System.out.println("Data Ordine: " + ordine.getData());
                System.out.println("Numero Ordine: " + ordine.getNumeroOrdine());

                if (ordine != null && utente != null && listaProdotti != null) {
                    HttpSession session = request.getSession();
                    // Salva gli oggetti ordine, utente e listaProdotti nella sessione
                    session.setAttribute("ordine", ordine);
                    session.setAttribute("utente", utente);
                    session.setAttribute("listaProdotti", listaProdotti);
                    // Reindirizza l'utente alla pagina Fattura.jsp
                    response.sendRedirect("Ricevuta.jsp");
                }
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("SONO QUI");
            }
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}