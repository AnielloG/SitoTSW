package Control;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;

import Model.Cart;
import Model.Prodotto;
import Model.ProductDao;


/**
 * Servlet implementation class ProductControl
 */
@WebServlet("/ProductControl")
@MultipartConfig  // Necessario per la gestione dell'upload di file
public class ProductControl extends HttpServlet {
	private static final long serialVersionUID = 1L;	
	
	ProductDao	Model = new ProductDao();
	
	public ProductControl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	    Cart cart = (Cart) request.getSession().getAttribute("cart");
	    if (cart == null) {
	        cart = new Cart();
	        request.getSession().setAttribute("cart", cart);
	    }

	    String action = request.getParameter("action");

	    try {
	        if (action != null) {
	            String id;  // Dichiarazione all'inizio per evitare duplicazione
	            RequestDispatcher dispatcher;

	            switch (action.toLowerCase()) {
	                case "addc":
	                    int productId = Integer.parseInt(request.getParameter("id"));  // Cambia 'id' in 'productId'
	                    cart.addProduct(Model.doRetrieveByKey(productId));
	                    request.setAttribute("cart", cart);
	                    
	                    String referer = request.getHeader("Referer");
	                    response.sendRedirect(referer);
	                    break;

	                case "svuotac":
	                    cart.deleteAllProduct();
	                    request.setAttribute("cart", cart);
	                    dispatcher = getServletContext().getRequestDispatcher("/carrello.jsp");
	                    dispatcher.forward(request, response);
	                    break;

	                case "deletec":
	                    productId = Integer.parseInt(request.getParameter("id"));  // Riutilizza 'productId'
	                    cart.deleteProduct(Model.doRetrieveByKey(productId));
	                    request.setAttribute("cart", cart);
	                    dispatcher = getServletContext().getRequestDispatcher("/carrello.jsp");
	                    dispatcher.forward(request, response);
	                    break;

	                case "read":
	                    productId = Integer.parseInt(request.getParameter("id"));  // Riutilizza 'productId'
	                    request.removeAttribute("product");
	                    request.setAttribute("product", Model.doRetrieveByKey(productId));
	                    request.setAttribute("media", Model.media(productId));
	                    dispatcher = getServletContext().getRequestDispatcher("/ProductDetails.jsp");
	                    dispatcher.forward(request, response);
	                    break;

	                case "delete":
	                    productId = Integer.valueOf(request.getParameter("id"));  // Riutilizza 'productId'
	                    Model.doDelete(productId);
	                    dispatcher = getServletContext().getRequestDispatcher("/Amministratore.jsp");
	                    dispatcher.forward(request, response);
	                    break;

	                case "suggest":
	                    String query = request.getParameter("query");
	                    try {
	                        List<String> suggestions = Model.getSuggestions(query);
	                        response.setContentType("application/json");
	                        response.setCharacterEncoding("UTF-8");
	                        String json = new Gson().toJson(suggestions);
	                        response.getWriter().write(json);
	                    } catch (SQLException e) {
	                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	                        response.getWriter().write("Errore durante il recupero dei suggerimenti");
	                        e.printStackTrace();
	                    }
	                    return; // Aggiungi questo per evitare ulteriori elaborazioni

	                case "search":
	                	HttpSession session = request.getSession(false); // Verifica la sessione attuale
	                    if (session != null) {
	                        System.out.println("Utente loggato, sessione attiva: " + session.getAttribute("user"));
	                    } else {
	                        System.out.println("Nessuna sessione attiva");
	                    }
	                    System.out.println("Search action received. Query: " + request.getParameter("nome"));
	                    String nome = request.getParameter("nome");
	                    try {
	                        Collection<Prodotto> searchResults = Model.searchProducts(nome);
	                        request.setAttribute("products", searchResults);
	                        RequestDispatcher dispatcher1 = getServletContext().getRequestDispatcher("/ProductView.jsp");
	                        dispatcher1.forward(request, response);
	                    } catch (SQLException e) {
	                        System.out.println("Error:" + e.getMessage());
	                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore durante la ricerca dei prodotti");
	                    }
	                    return;

	                case "updateq":
	                    productId = Integer.valueOf(request.getParameter("id"));  // Riutilizza 'productId'
	                    Model.doupdateq(productId);
	                    dispatcher = getServletContext().getRequestDispatcher("/Amministratore.jsp");
	                    dispatcher.forward(request, response);
	                    break;

	                case "change":
	                    int idfoto = Integer.parseInt(request.getParameter("id"));
	                    int idprod = Integer.parseInt(request.getParameter("productid"));
	                    request.setAttribute("product", Model.Cambiafoto(idfoto, idprod));
	                    dispatcher = getServletContext().getRequestDispatcher("/ProductDetails.jsp");
	                    dispatcher.forward(request, response);
	                    break;

	                case "dettaglio":
	                    String sesso = request.getParameter("sesso");
	                    String categoria = request.getParameter("categoria");
	                    try {
	                        request.removeAttribute("products");
	                        if (sesso != null && categoria != null) {
	                            request.setAttribute("products", Model.doRetrieveBySessoAndCategoria(sesso, categoria));
	                        } else if (sesso != null) {
	                            request.setAttribute("products", Model.doRetrieveBySesso(sesso));
	                        } else if (categoria != null) {
	                            request.setAttribute("products", Model.doRetrieveByCategoria(categoria));
	                        }
	                    } catch (SQLException e) {
	                        System.out.println("Error:" + e.getMessage());
	                    }

	                    dispatcher = getServletContext().getRequestDispatcher("/ProductView.jsp");
	                    dispatcher.forward(request, response);
	                    break;

	                case "viewc":
	                    request.setAttribute("cart", cart);
	                    dispatcher = getServletContext().getRequestDispatcher("/carrello.jsp");
	                    dispatcher.forward(request, response);
	                    break;

	                case "edit":
	                    id = request.getParameter("id");
	                    String prezzo = request.getParameter("prezzo");
	                    Model.cambiaprezzo(id, prezzo);
	                    dispatcher = getServletContext().getRequestDispatcher("/Amministratore.jsp");
	                    dispatcher.forward(request, response);
	                    break;

	                case "insert":
	                    String descrizione = request.getParameter("descrizione");
	                    double prezzoValue = Double.parseDouble(request.getParameter("prezzo"));
	                    int quantita = Integer.parseInt(request.getParameter("quantita"));
	                    sesso = request.getParameter("sesso");
	                    nome = request.getParameter("nome");
	                    InputStream inputStream = request.getPart("foto").getInputStream();
	                    categoria = request.getParameter("categoria");
	                    byte[] bytes = null;
	                    try {
	                        bytes = IOUtils.toByteArray(inputStream);
	                    } catch (IOException e) {
	                        e.printStackTrace();
	                    } finally {
	                        if (inputStream != null) {
	                            inputStream.close();
	                        }
	                    }

	                    Prodotto prodotto = new Prodotto();
	                    prodotto.setDescrizione(descrizione);
	                    prodotto.setPrezzo(prezzoValue);
	                    prodotto.setQuantita(quantita);
	                    prodotto.setImg(bytes);
	                    prodotto.setSesso(sesso);
	                    prodotto.setNome(nome);
	                    prodotto.setCategoria(categoria);

	                    try {
	                        Model.doSave(prodotto);
	                        response.sendRedirect(request.getContextPath() + "/Amministratore.jsp");
	                    } catch (SQLException e) {
	                        e.printStackTrace();
	                        request.setAttribute("errore", "Errore del database: " + e.getMessage());
	                        dispatcher = getServletContext().getRequestDispatcher("/Errore.jsp");
	                        dispatcher.forward(request, response);
	                    }
	                    break;

	                case "all":
	                    try {
	                        request.removeAttribute("products");
	                        request.setAttribute("products", Model.doRetrieveAll());
	                    } catch (SQLException e) {
	                        System.out.println("Error:" + e.getMessage());
	                    }
	                    dispatcher = getServletContext().getRequestDispatcher("/ProductView.jsp");
	                    dispatcher.forward(request, response);
	                    break;

	                default:
	                    System.out.println("Azione non riconosciuta: " + action);
	                    break;
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("Error:" + e.getMessage());
	        e.printStackTrace();
	    }

	    request.getSession().setAttribute("cart", cart);
	    request.setAttribute("cart", cart);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    String action = request.getParameter("action");

	    if (action != null && action.equals("insert")) {
	        try {
	            // Raccogli i dati inviati dal form
	            String nome = request.getParameter("nome");
	            String descrizione = request.getParameter("descrizione");
	            double prezzo = Double.parseDouble(request.getParameter("prezzo"));
	            int quantita = Integer.parseInt(request.getParameter("quantita"));
	            String sesso = request.getParameter("sesso");
	            String categoria = request.getParameter("categoria");  // Raccogli la categoria
	            double iva = 22.0;  // Imposta IVA predefinita al 22%

	            // Gestisci il caricamento dell'immagine
	            Part fotoPart = request.getPart("foto");
	            byte[] fotoBytes = null;

	            if (fotoPart != null && fotoPart.getSize() > 0) {
	                // Usa try-with-resources per chiudere automaticamente l'InputStream
	                try (InputStream inputStream = fotoPart.getInputStream()) {
	                    fotoBytes = inputStream.readAllBytes();
	                }
	            }

	            // Crea un nuovo oggetto Prodotto
	            Prodotto prodotto = new Prodotto();
	            prodotto.setNome(nome);
	            prodotto.setDescrizione(descrizione);
	            prodotto.setPrezzo(prezzo);  // Salva il prezzo senza IVA
	            prodotto.setQuantita(quantita);
	            prodotto.setSesso(sesso);
	            prodotto.setCategoria(categoria);  // Imposta la categoria
	            prodotto.setIva(iva);  // Imposta l'IVA
	            prodotto.setImg(fotoBytes);

	            // Salva il prodotto nel database usando il DAO
	            ProductDao productDao = new ProductDao();
	            productDao.doSave(prodotto);

	            // Rispondi al client con un messaggio di successo
	            response.setStatus(HttpServletResponse.SC_OK);
	            response.getWriter().write("Prodotto salvato con successo");

	        } catch (SQLException e) {
	            e.printStackTrace();
	            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	            response.getWriter().write("Errore durante il salvataggio del prodotto");
	        } catch (NumberFormatException e) {
	            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	            response.getWriter().write("Errore: formato dei dati non valido");
	        }
	    } else {
	        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	        response.getWriter().write("Azione non valida");
	    }
	}

}
