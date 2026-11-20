package Control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import Model.Ordine;
import Model.OrdineDAO;
import Model.Utente;
import Model.UtenteDAO;

/**

Servlet implementation class RegistrationControl
*/
@WebServlet("/registration")
public class RegistrationControl extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(RegistrationControl.class.getName());
private static final long serialVersionUID = 1L;
UtenteDAO utenteDAO = new UtenteDAO();
/**

@see HttpServlet#HttpServlet()
*/
public RegistrationControl() {
super();
}
/**

@see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
*/
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String action = request.getParameter("action");
    
    if (action != null && action.equalsIgnoreCase("logout")) {
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect("Home.jsp");
    }
}
/**

@see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
*/
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String action = request.getParameter("action");
	try {
		
	if (action != null) {
		
		if (action.equalsIgnoreCase("insert")) {
			String email = request.getParameter("email");
			String pass = request.getParameter("pass");
			String nome = request.getParameter("nome");
			String cognome = request.getParameter("cognome");
			String citta = request.getParameter("citta");
			String indirizzo = request.getParameter("indirizzo");
			String provincia = request.getParameter("provincia");
			String cap = request.getParameter("cap");
			
			Utente bean = new Utente();
			bean.setEmail(email);
			bean.setPass(pass);
			bean.setCap(cap);
			bean.setCitta(citta);
			bean.setCognome(cognome);
			bean.setNome(nome);
			bean.setIndirizzo(indirizzo);
			bean.setProvincia(provincia);
			utenteDAO.doSave(bean);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Home.jsp");
			dispatcher.forward(request, response);
		}
		if (action.equalsIgnoreCase("insertbreve")) {
			String email = request.getParameter("email");
			String pass = request.getParameter("pass");
			String nome = request.getParameter("nome");
			String cognome = request.getParameter("cognome");
			
			Utente bean = new Utente();
			bean.setEmail(email);
			bean.setPass(pass);
			bean.setCognome(cognome);
			bean.setNome(nome);
			HttpSession session = request.getSession();
            session.setAttribute("email", bean.getEmail());
            session.setAttribute("cognome", bean.getCognome());
            session.setAttribute("nome", bean.getNome());
            session.setAttribute("password", bean.getPass());
		
			utenteDAO.doSaveRistretto(bean);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Home.jsp");
			dispatcher.forward(request, response);
		}
		if (action.equalsIgnoreCase("login")){
            String email = request.getParameter("username");
            String pwd = request.getParameter("pass");
            
            try {
                UtenteDAO dao = new UtenteDAO();
                Utente utente = dao.doRetrieveByEmail(email);
                
                if (utente != null && utente.getPass().equals(pwd)) {
                    HttpSession session = request.getSession();
                    session.setAttribute("email", utente.getEmail());
                    session.setAttribute("citta", utente.getCitta());
                    session.setAttribute("cognome", utente.getCognome());
                    session.setAttribute("pass", utente.getPass());
                    session.setAttribute("indirizzo", utente.getIndirizzo());
                    session.setAttribute("cap", utente.getCap());
                    session.setAttribute("provincia", utente.getProvincia());
                    session.setAttribute("nome", utente.getNome()); 
                    session.setAttribute("tipo_account", utente.getTipo_account());
                    if(utente.getTipo_account()==0) {
                    response.sendRedirect(request.getContextPath() + "/Home.jsp");
                    } 
                    else if (utente.getTipo_account() == 1) {
                        response.sendRedirect(request.getContextPath() + "/Home.jsp");
                    }
                    
            } else {
                	request.setAttribute("errore", "Email o password non validi");
                    request.getRequestDispatcher("/Accedi.jsp").forward(request, response);
                    
                }
                }catch (SQLException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
                request.setAttribute("errore", "Errore del database: " + e.getMessage());
                request.getRequestDispatcher("/Accedi.jsp").forward(request, response);
            }
        }
		if (action.equalsIgnoreCase("searchByEmail")) {

		    try {
		    	String email = request.getParameter("email");
	            // Esegui la logica per ottenere gli utenti corrispondenti all'email dal tuo database
		        UtenteDAO dao = new UtenteDAO();
		        List<Utente> utenti = dao.searchByEmail(email);

	            // Converte gli utenti in formato JSON e invia la risposta
	            Gson gson = new Gson();
	            String json = gson.toJson(utenti);
	            response.setContentType("application/json");
	            response.setCharacterEncoding("UTF-8");
	            response.getWriter().write(json);

		    } catch (SQLException e) {
		        LOGGER.log(Level.SEVERE, e.getMessage(), e);
		        request.setAttribute("errore", "Errore del database: " + e.getMessage());
		    }
		}
        if (action != null && action.equalsIgnoreCase("modifica")) {
            String email = request.getParameter("email");
            String nome = request.getParameter("nome");
            String cognome = request.getParameter("cognome");
            String indirizzo = request.getParameter("indirizzo");
            String citta = request.getParameter("citta");
            String provincia = request.getParameter("provincia");
            String cap = request.getParameter("cap");
            String pass = request.getParameter("pass");
            
            Utente utente = new Utente();
            utente.setEmail(email);
            utente.setNome(nome);
            utente.setCognome(cognome);
            utente.setIndirizzo(indirizzo);
            utente.setCitta(citta);
            utente.setProvincia(provincia);
            utente.setCap(cap);
            utente.setPass(pass);
            
            OrdineDAO ordine = new OrdineDAO();
            UtenteDAO utenteDao = new UtenteDAO();
            utenteDao.doUpdate(utente);
            ArrayList<Ordine> ordini= (ArrayList<Ordine>) ordine.searchByEmail(email);
            HttpSession session = request.getSession();
            session.setAttribute("nome", nome);
            session.setAttribute("cognome", cognome);
            session.setAttribute("indirizzo", indirizzo);
            session.setAttribute("citta", citta);
            session.setAttribute("provincia", provincia);
            session.setAttribute("cap", cap);
            request.setAttribute("ordini", ordini);
            
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Profilo.jsp");
            dispatcher.forward(request, response);
        }
	}}
	catch (SQLException e) {
	LOGGER.log(Level.SEVERE, e.getMessage(), e);
	request.setAttribute("errore2", "Mail gia usata" );
	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Accedi.jsp");
	dispatcher.forward(request, response);
}
	
}}
