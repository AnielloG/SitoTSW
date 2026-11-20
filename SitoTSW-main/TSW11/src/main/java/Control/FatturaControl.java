package Control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Model.Ordine;
import Model.OrdineDAO;
import Model.Prodotto;
import Model.ProductDAO;
import Model.Utente;
import Model.UtenteDAO;

@WebServlet("/fattura")
public class FatturaControl extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(FatturaControl.class.getName());
    private static final long serialVersionUID = 1L;
    private OrdineDAO ordineDAO;
    private UtenteDAO utenteDAO;
    private ProductDAO prodottoDAO;

    public void init() throws ServletException {
        ordineDAO = new OrdineDAO();
        utenteDAO = new UtenteDAO();
        prodottoDAO = new ProductDAO();
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
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}