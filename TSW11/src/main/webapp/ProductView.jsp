<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Base64" %>
<%@ page import="Model.Prodotto" %>
<%@ page import="Model.Cart" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Iterator" %>

<%
    Collection<?> products = (Collection<?>) request.getAttribute("products");
    if (products == null) {
        response.sendRedirect("./product");
        return;
    }

    Cart cart = (Cart) session.getAttribute("cart");
%>

<!DOCTYPE html>
<html lang="it">

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <title>3G Vintage</title>
<style>
			body {
			  font-family: Arial, sans-serif;
			  background-color: #F5F5F7;
			  margin: 0;
			  }
			  
			  h1 {
				  text-align: center;
				  font-size: 36px;
				  color: #1a202c;
				}
				
		.banner {
			background-color: #fefaf6;
			position: relative;
			height: 90px;
			width: 100%;
		}
		
		#image {
			position: absolute;
			top: -18px;
			left: 10px;
			z-index: 1;
			width: 125px;
			height: auto;
		}
		
		.dx {
		    display: flex;
		    justify-content: center; /* Centra orizzontalmente gli elementi */
		    align-items: center;
			position: absolute;
			width: auto;
			top: 20px;
			right: 5px;
			z-index: 1;
		 
		}
		
		.dx img {
			width: 40px;
			height: 40px;
			margin-left: 15px;
			margin-right: 15px;
		}
		
		.cerca {
		    display: none;
		}
		
		#searchInput{
		border: 2px solid black;
		border-radius: 5px;
		}
		/* Stile per la visualizzazione dei prodotti */

	  .product.hover {
	    transform: translateY(-5px);
	    box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2);
	  }
		
		.product h3 {
		  margin-top: 10px;
		  margin-bottom: 5px;
		  font-size: 24px;
		  font-weight: bold;
		  color: #000;
		  text-decoration: none;
		}
		
		.product button {
		  background-color: #ffa500;
		  color: #fff;
		  padding: 10px 20px;
		  border: none;
		  border-radius: 4px;
		  cursor: pointer;
		  font-size: 16px;
		  transition: all 0.3s ease-in-out;
		}
		
		.product button:hover {
		  background-color: #ff8c00;
		}
		
		.product p {
			margin-bottom: 10px;
			font-size: 18px;
		  	color: #666;
		  	text-decoration: none;
		}
		
		.product a {
			margin-right: 10px;
			text-decoration: none;
		}
		
		.product-container {
		  display: flex;
		  flex-direction: column; 
		  align-items: center; 
		  margin: 20px;
		}
		
		.product-row {
		  display: flex;
		  justify-content: center;
		  margin-bottom: 20px;
		  width: 100%;
		  flex-wrap: wrap;
		}
		
		.product {
		 display: flex;
    	 flex-direction: column;
    	 align-items: center;
    	 justify-content: space-between;
    	 height: 400px; /* Altezza fissa per uniformità */
    	 width: 250px;
    	 padding: 10px;
    	 margin: 10px;
    	 background-color: #F5F5F7;
    	 border: 1px solid #ccc;
    	 border-radius: 10px;
    	 box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
		}

		.product img {
		  display: flex;
		  margin: auto;
		  width: 100%;
		  height: 200px;
		  object-fit: contain;
		  margin-bottom: 10px;
		  border-radius: 15px;
		}
		
			
		nav {
		  text-align: center;
		  background-color: #102c57;
		  display: flex;
  		justify-content: center;
		}
		
		nav a {
		  display: inline-block;
		  color: #f2f2f2;
		  text-align: center;
		  padding: 14px 16px;
		  font-size: 17px;
		  text-decoration: none;
		  margin: 0 10px;
		}
		
		nav a:hover {
		  background-color: rgba(235, 235, 240, 0.66);
		  color: #1d1d1f;
		}
		
		input[type="submit"] {
		  background-color: #333;
		  color: #fff;
		  border: none;
		  margin: 27px;
		  padding: 10px 20px;
		  cursor: pointer;
		  transition: all 0.3s ease-in-out;
		}
		
		input[type="submit"]:hover {
		  background-color: #102c57;
		  border-radius: 14px;
		}
		
		.dropdown {
		  float: left;
		  overflow: hidden;
		}
		
		.dropdown .dropbtn {
		  font-size: 16px;
		  border: none;
		  outline: none;
		  color: #FEFAF6;
		  padding: 14px 16px;
		  background-color: inherit;
		  font-family: inherit;
		  margin: 0;
		}
		
		nav a:hover,
		.dropdown:hover .dropbtn {
		  background-color: #DAC0A3;
		}
		
		.dropdown-content {
		  display: none;
		  position: absolute;
		  background-color: #f9f9f9;
		  min-width: 160px;
		  box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
		  z-index: 1;
		}
		
		.dropdown-content a {
		  float: none;
		  color: black;
		  padding: 12px 16px;
		  text-decoration: none;
		  display: block;
		  text-align: left;
		}
		
		.dropdown-content a:hover {
		  background-color: #ddd;
		}
		
		.dropdown:hover .dropdown-content {
		  display: block;
		}
		
		/* Tabella dei dettagli del prodotto */
		table {
		  width: 100%;
		  margin-top: 20px;
		  border-collapse: collapse;
		}
		
		table th,
		table td {
		  padding: 8px;
		  border: 1px solid #ccc;
		}
		
		table th {
		  background-color: #f2f2f2;
		  text-align: left;
		}
		
		h2 {
		  text-align: center;
		  margin:0;
		  padding:0;
		}
		a{
			margin-left: 10px;
		}
		
		a:link {
		  color: #1d1d1f;
		  font-weight: bold;
		}
		
		
		a:visited {
		  color: #1d1d1f;
		  font-weight: bold;
		}
		
		
		a:hover {
		  color: #e7e7e7;
		  font-weight: bold;
		}
		
		a:active {
		  color: #1d1d1f;
		  font-weight: bold;
		}
		.product-list{
			justify-content: center;
			align-items: center;
		}
                .det{
		width: 100%;
    	height: 250px; /* Altezza fissa per la sezione immagine e titolo */
    	display: flex;
    	flex-direction: column;
    	align-items: center;
    	justify-content: center;
    	margin-top: 30px;
		}
</style>
</head>
<body>
<div id="content">
	<div class="banner"> 
	<a href="Home.jsp"><img src="assets/images/nuovologo.png" id="image" alt="#"></a>
	<div class="dx">
<% if (session.getAttribute("email") == null) { %>
        <a href="#0" id="cercap"><img src="assets/images/cerca.png" alt="#"></a>
<div class="cerca" style="display: none;">
    <form onsubmit="submitSearch(event)">
        <input type="text" name="nome" id="searchInput" placeholder="Cerca prodotto">
        <button type="submit">Cerca</button>
    </form>
</div>
        <a href="Accedi.jsp"><img src="assets/images/utente.png" alt="#"></a>
        <a href="product?action=viewC"><img src="assets/images/cart.png" alt="#"></a>
    <% } else { %>
        <a href="#0" id="cercap"><img src="assets/images/cerca.png" alt="#"></a>
<div class="cerca">
    <form onsubmit="submitSearch(event)">
        <input type="text" name="nome" id="searchInput" placeholder="Cerca prodotto">
        <button type="submit">Cerca</button>
    </form>
</div>
        <a href="ordine?action=ViewOrdini&email=<%=session.getAttribute("email") %>"><img src="assets/images/utente.png" alt="#"></a>
        <a href="registration?action=logout"><img src="assets/images/logout.png" alt="#"></a>
        <a href="product?action=viewC"><img src="assets/images/cart.png" alt="#"></a>
    <% } %>
	</div>
	 
	</div>
<nav>
  <div class="dropdown">
    <a href="product?action=dettaglio&sesso=M" class="dropbtn">Uomo</a>
    <div class="dropdown-content">
      <a href="product?action=dettaglio&categoria=giacche&sesso=M">Giacche</a>
      <a href="product?action=dettaglio&categoria=felpe&sesso=M">Felpe</a>
      <a href="product?action=dettaglio&categoria=maglie&sesso=M">Maglie</a>
      <a href="product?action=dettaglio&categoria=pantaloni&sesso=M">Pantaloni</a>
    </div>
  </div>
  <div class="dropdown">
    <a href="product?action=dettaglio&sesso=F" class="dropbtn">Donna</a>
    <div class="dropdown-content">
      <a href="product?action=dettaglio&categoria=giacche&sesso=F">Giacche</a>
      <a href="product?action=dettaglio&categoria=felpe&sesso=F">Felpe</a>
      <a href="product?action=dettaglio&categoria=maglie&sesso=F">Maglie</a>
      <a href="product?action=dettaglio&categoria=pantaloni&sesso=F">Pantaloni</a>
    </div>
  </div>
  <div class="dropdown">
    <a href="product?action=dettaglio&categoria=accessori&sesso=M" class="dropbtn">Accessori</a>
    <div class="dropdown-content">
      <a href="product?action=dettaglio&categoria=accessori&sesso=M">Cappelli</a>
    </div>
  </div>
  <a href="product?action=all" style="color: #FEFAF6;">All</a>
</nav>

  <br><br>
  <h2>Prodotti</h2>
<div>
    <div class="product-container">
        <div class="product-list">
            <% if (products != null && products.size() != 0) {
                Iterator<?> it = products.iterator();
                int count = 0;
                while (it.hasNext()) {
                    if (count % 4 == 0) { %>
                        <div class="product-row">
                    <% }
                    Prodotto bean = (Prodotto) it.next();
                    byte[] imageB = bean.getImg();
                    
                    // Aggiungi un controllo null per l'immagine
                    String base64img = "";
                    if (imageB != null) {
                        base64img = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(imageB);
                    } else {
                        // Usa un'immagine di placeholder se imageB è null
                        base64img = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mP8z8BQDwAFhQL9GVkmjAAAAABJRU5ErkJggg==";
                    }
                %>
                <div class="product">
                    <div class="det">
                        <a href="product?action=read&id=<%=bean.getID()%>">
                            <img src="<%=base64img%>" width="300" height="300" alt="Immagine non disponibile">
                            <p align="center"><%=bean.getNome()%></p>
                        </a>
                    </div>
                    <p align="center"><%=bean.getPrezzo()%>€</p>
                    <% if (bean.getQuantita() == 1) { %>
                        <br>
                        <p style="text-align: center;">Non disponibile</p>
                    <% } else if (cart != null && !cart.presente(bean.getID())) { %>
                        <a href="product?action=addC&id=<%=bean.getID()%>" id="carrello">
                            <input type="submit" value="Aggiungi al carrello">
                        </a>
                    <% } else { %>
                        <br>
                        <p style="text-align: center;">Prodotto già nel carrello</p>
                    <% } %>
                </div>
<% count++;
if (count % 4 == 0) { %>
    </div>
<% }
}
if (count % 4 != 0) { %>
    </div>
<% }
} else { %>
    <p>Non ci sono prodotti disponibili</p>
<% } %>
    </div>
  </div>
  <script>
  function submitSearch(event) {
	    event.preventDefault(); // Previene il comportamento predefinito del form

	    var searchInput = document.getElementById("searchInput");
	    var nome = searchInput.value.trim();

	    if (nome !== "") {
	        // Esegue la richiesta AJAX per la ricerca del prodotto
	        $.ajax({
	            url: "product",
	            type: "GET",
	            data: {
	                action: "search",
	                nome: nome
	            },
	            dataType: "html",
	            success: function(response) {
	                // Aggiorna il contenuto della pagina con i risultati della ricerca
	                $("#content").html(response);
	            },
	            error: function(xhr, status, error) {
	                // Gestione degli errori
	                console.error(error);
	            }
	        });
	    }
	}

  var cercaLink = document.getElementById("cercap");
	var cercaSection = document.querySelector(".cerca");
		 
			cercaLink.addEventListener("click", function(event) {
			event.preventDefault();
		if (cercaSection.style.display === "flex") {
			cercaSection.style.display = "none"; // Se la barra di ricerca è già visibile, nascondila
		} else {
			cercaSection.style.display = "flex"; // Altrimenti, mostra la barra di ricerca
		}
		});
  </script>
  
  <script>
  $(document).ready(function() {
    $(".product").hover(
      function() {
        $(this).addClass("hover");
      },
      function() {
        $(this).removeClass("hover");
      }
    );
  });
</script>
  
  </div>
  
  <br><br><br>
<jsp:include page="footer.jsp"/>
  
</body>
</html>