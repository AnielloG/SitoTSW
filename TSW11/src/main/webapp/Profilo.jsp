<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,Control.*,Model.*"%>
<%
    List<Ordine> ordini = (List <Ordine>) request.getAttribute("ordini");
    List<Prodotto> prodotti =(List <Prodotto>) request.getAttribute("prodotti");

    // Recupera il tipo di account dalla sessione
    Integer tipoAccount = (Integer) session.getAttribute("tipo_account");
%>
<!DOCTYPE html>
<html lang="it">
<head>
<title>Profilo</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<style>

<style>
.star {
  color: black;
  cursor: pointer;
}

.checked {
  color: orange;
}

*, *::before, *::after {
  box-sizing: border-box;
}

body {
   font-family: sans-serif;
   font-size: 1em;
   color: #333;
   overflow-x: hidden;
}

h1 {
  font-size: 1.4em;
}

em {
   font-style: normal;
}

a {
   text-decoration: none;
   color: inherit;
} 

/* Layout */
.s-layout {
   display: flex;
   width: 100%;
   min-height: 100vh;
}

   .s-layout__content {
   display: flex;
   
   flex: 1;
   margin-left: 0;
   margin-top: 30px;
   position: relative;
   flex-wrap: wrap; /* Aggiungi questa riga */
}

/* Sidebar */
.s-sidebar__trigger {
   z-index: 2;
   position: fixed;
   top: 0;
   left: 0;
   width: 100%;
   height: 4em;
   }

.s-sidebar__trigger > i {
   display: inline-block;
   margin: 1.5em 0 0 1.5em;
   color: #f07ab0;
}

.s-sidebar__nav {
   position: relative;
   top: 0px; /* Aggiungi una margine superiore per allineare la sidebar sotto la navbar */
   left: 0;
   overflow: hidden;
   transition: all .3s ease-in;
   width: 15em;
   height: calc(100% - 245px); /* Calcola l'altezza della sidebar in base alla navbar */
   background: #102C57;
   color: rgba(255, 255, 255, 0.7);
   z-index: 0;
}

.s-sidebar__nav:hover,
.s-sidebar__nav:focus,
.s-sidebar__trigger:focus + .s-sidebar__nav,
.s-sidebar__trigger:hover + .s-sidebar__nav {
   left: 0;
}

.s-sidebar__nav ul {
   position: absolute;
   top: 4em;
   left: 0;
   margin: 0;
   padding: 0;
   width: 15em;
}

.s-sidebar__nav ul li {
   width: 100%;
}

.s-sidebar__nav-link {
   position: relative;
   display: inline-block;
   width: 100%;
   height: 4em;
}

.s-sidebar__nav-link em {
   position: absolute;
   top: 50%;
   left: 4em;
   transform: translateY(-50%);
}

.s-sidebar__nav-link:hover {
   background: #102C57;
}

.s-sidebar__nav-link > i {
   position: absolute;
   top: 0;
   left: 0;
   display: inline-block;
   width: 4em;
   height: 4em;
}

.s-sidebar__nav-link > i::before {
   position: absolute;
   top: 50%;
   left: 50%;
   transform: translate(-50%, -50%);
}

@media (min-width: 42em) {
  .s-sidebar__nav {
    width: 4em;
  }

  .s-sidebar__nav:hover,
  .s-sidebar__nav:focus,
  .s-sidebar__trigger:focus + .s-sidebar__nav,
  .s-sidebar__trigger:hover + .s-sidebar__nav {
    width: 15em;
  }
}

@media (min-width: 68em) {
  .s-sidebar__trigger {
    display: none;
  }

  .s-sidebar__nav {
    width: 15em;
    height: 57em;
  }

  .s-sidebar__nav ul {
    top: 1.3em;
  }
}

@media (max-width: 42em) {
  .s-sidebar__nav {
    width: 0;
  }

  .s-sidebar__nav:hover,
  .s-sidebar__nav:focus,
  .s-sidebar__trigger:focus + .s-sidebar__nav,
  .s-sidebar__trigger:hover + .s-sidebar__nav {
    width: 0;
  }
}

@media (max-width: 68em) {
  .s-sidebar__trigger {
    display: block;
  }

  .s-sidebar__nav {
    width: 0;
  }

  .s-sidebar__nav ul {
    top: 0;
  }
}

@media (max-width: 768px) {
  .profile-form {
    width: 100%;
    margin: 10px;
  }

  @media (min-width: 68em) {
    .s-sidebar__nav {
      width: 15em;
    }
  }
}

.order-table {
  width: 100%;
  border-collapse: collapse;
  font-size: 14px;
  margin-top: 10px;
}

.order-table th,
.order-table td {
  padding: 10px;
  text-align: left;
}

.order-table th {
  background-color: #f5f5f5;
  font-weight: bold;
}

.order-table tr:nth-child(even) {
  background-color: #f9f9f9;
}

.order-table tr:hover {
  background-color: #f2f2f2;
}

.order-table a {
  text-decoration: none;
  color: #333;
}

.order-table th:not(:last-child),
.order-table td:not(:last-child) {
  border-right: 1px solid #ddd;
}

.profile-form {
    display: block;
    position: absolute;
    top: 10px;
    left: 20px;
    width: 300px;
    background-color: #f5f5f5;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.profile-form input[type="text"],
.profile-form input[type="password"] {
    margin-bottom: 10px;
}

.profile-form input[type="submit"] {
    margin-top: 10px;
}


.order-form{
	display:none;
	    position: absolute;
    top: 10px;
    left: 20px;
  background-color: #f5f5f5;
  border-radius: 5px;
  padding: 10px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
   overflow: auto;
  max-height:500px; /* Altezza massima desiderata */
  
}

.order-table {
  width: 100%;
  border-collapse: collapse;
  background-color: #fff;
  font-size: 14px;
 
}

.order-table th,
.order-table td {
  padding: 10px;
  text-align: left;
}

.order-table th {
  background-color: #f9f9f9;
  font-weight: bold;
}

.order-table tr:nth-child(even) {
  background-color: #f2f2f2;
}

.order-table tr:hover {
  background-color: #e5e5e5;
}

.order-table a {
  text-decoration: none;
  color: #333;
}

.order-table th:not(:last-child),
.order-table td:not(:last-child) {
  border-right: 1px solid #ddd;
}

	.banner {
	background-color: rgba(235, 235, 240, 0.66);
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
	position: absolute;
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

  .hidden {
    display: none;
  }

.centrato-tabella {
  margin-left: auto;
  margin-right: auto;
}

</style>
</head>

<body>
<jsp:include page="Banner.jsp"/>
<div class="s-layout">
<!-- Sidebar -->
<div class="s-layout__sidebar">
  <a class="s-sidebar__trigger" href="#0">
     <i class="fa fa-bars"></i>
  </a>

  <nav class="s-sidebar__nav">
     <ul>
        <li>
           <a class="s-sidebar__nav-link" href="#0" id="areaUtenteLink">
              <i class="fa fa-home"></i><em>I miei dati</em>
           </a>
        </li>
        <li>
           <a class="s-sidebar__nav-link" href="#0" id="mieiOrdiniLink">
             <i class="fa fa-user"></i><em>I miei ordini</em>
           </a>
        </li>
        <% if (tipoAccount != null && tipoAccount == 1) { %>
        <li>
           <a class="s-sidebar__nav-link" href="Amministratore.jsp">
              <i class="fa fa-shield"></i><em>Area Admin</em>
           </a>
        </li>
        <% } %>
     </ul>
  </nav>
</div>

<!-- Content -->
<main class="s-layout__content">
  
  <div class="profile-form">
   <h1>Profilo utente</h1>
    <form action="registration?action=modifica" method="post">
      <label>Nome:</label>
      <input type="text" name="nome" value="<%=session.getAttribute("nome") %>"><br>
    <label>Cognome:</label>
    <input type="text" name="cognome" value="<%= session.getAttribute("cognome") %>"><br>
    
    <label>Indirizzo:</label>
    <input type="text" name="indirizzo" value="<%=session.getAttribute("indirizzo") %>"><br>
    
    <label>Città:</label>
    <input type="text" name="citta" value="<%=session.getAttribute("citta") %>"><br>
    
    <label>Provincia:</label>
    <input type="text" name="provincia" value="<%=session.getAttribute("provincia") %>" maxlength="2" ><br>
    
    <label>CAP:</label>
    <input type="text" name="cap" value="<%=session.getAttribute("cap") %>" maxlength="5" ><br>
    
    <label>Email:</label>
    <input type="text" name="email" value="<%=session.getAttribute("email") %>"><br>
    
    <label>Password:</label>
    <input type="password" name="pass" value="<%=session.getAttribute("pass") %>"><br>
      
      <input type="submit" value="Salva modifiche">
    </form>
  </div>
  <br>
  <div class="order-form">
  <h2>I tuoi ordini</h2>
<table class="order-table">
  <tr>
    <th>Numero ordine</th>
    <th>Data</th>
    <th>Totale</th>
    <th>Stato</th>
    <th>Dettagli</th>
    <th>Ricevuta</th>
  </tr>
  
  <% 
  if (ordini != null && !ordini.isEmpty()) {
    for (Ordine ordine : ordini) { %>
      <tr>
        <td><%= ordine.getNumeroOrdine() %></td>
        <td><%= ordine.getData() %></td>
        <td><%= ordine.getTotale() %>€</td>
        <td><%= ordine.getStato() %></td>
        <td><a href="#" class="mostra-dettagli" data-numero-ordine="<%= ordine.getNumeroOrdine() %>">Mostra</a></td>
        <td><a href="fattura?action=viewFattura&numeroOrdine=<%= ordine.getNumeroOrdine() %>&email=<%=session.getAttribute("email") %>">Visualizza</a></td>
      </tr>
      <tr class="dettagli-ordine hidden" id="dettagli-<%= ordine.getNumeroOrdine() %>">
        <td colspan="6">
		 <table class="centrato-tabella">
		  <tr>
		    <th>ID</th>
		    <th>Nome</th>
		    <th>Foto</th>
		    <th>Recensisci</th>
		  </tr>
		  <% 
		  // Esegui la query per ottenere i dettagli dei prodotti associati all'ordine
		  OrdineDAO ordineDAO = new OrdineDAO();
		  List<Prodotto> dettagliProdotti = ordineDAO.getProdotti(ordine.getNumeroOrdine());
		  
		  // Itera sui prodotti e mostra i dettagli
		  for (Prodotto prodotto : dettagliProdotti) { 
			  
		  %>
			<tr>
  <td><%= prodotto.getID() %></td>
  <td><%= prodotto.getNome() %></td>
  <td>
    <% 
      byte[] image = prodotto.getImg();
      String base64Image = "";
      
      if (image != null && image.length > 0) {
          base64Image = Base64.getEncoder().encodeToString(image);
      } else {
          base64Image = ""; // Imposta un'immagine vuota o di default
          out.println("Nessuna Immagine Disponibile");
      }
    %>
    <% if (!base64Image.isEmpty()) { %>
      <img src="data:image/jpeg;base64, <%= base64Image %>" width="40" height="40" alt="#">
    <% } %>
  </td>
  <td> 
    <form action="ordine" method="GET">
      <input type="hidden" name="action" value="valuta">
      <input type="hidden" name="prodotto" value="<%= prodotto.getID() %>">
      <input type="hidden" name="stelle" id="stelle-<%= prodotto.getID() %>" value="">
      
      <div class="stars" id="stars-<%= prodotto.getID() %>">
        <span class="star" onclick="rate(1, this, <%= prodotto.getID() %>)">&#9733;</span>
        <span class="star" onclick="rate(2, this, <%= prodotto.getID() %>)">&#9733;</span>
        <span class="star" onclick="rate(3, this, <%= prodotto.getID() %>)">&#9733;</span>
        <span class="star" onclick="rate(4, this, <%= prodotto.getID() %>)">&#9733;</span>
        <span class="star" onclick="rate(5, this, <%= prodotto.getID() %>)">&#9733;</span>
      </div>
      
      <input type="submit" value="Invia recensione">
    </form>		 
  </td>
</tr>
			
		  <% } %>
		</table>
        </td>
      </tr>
    <% }
  }
  %>
</table>
        
</div>

</main>
</div>
<jsp:include page="footer.jsp"/>
<script>
  var areaUtenteLink = document.getElementById("areaUtenteLink");
  var mieiOrdiniLink = document.getElementById("mieiOrdiniLink");

  areaUtenteLink.addEventListener("click", function(event) {
    event.preventDefault();
    var ordiniSection = document.querySelector(".order-form");
    ordiniSection.style.display = "none";
    var profiloUtenteSection = document.querySelector(".profile-form");
    profiloUtenteSection.style.display = "block";
  });

  mieiOrdiniLink.addEventListener("click", function(event) {
    event.preventDefault();
    var profiloUtenteSection = document.querySelector(".profile-form");
    profiloUtenteSection.style.display = "none";
    var ordiniSection = document.querySelector(".order-form");
    ordiniSection.style.display = "block";
  });
</script>

<script>
  $(document).ready(function() {
    // Funzione per mostrare/nascondere i dettagli dell'ordine
    $('.mostra-dettagli').click(function() {
      var numeroOrdine = $(this).data('numero-ordine');
      $('#dettagli-' + numeroOrdine).toggleClass('hidden');
    });
  });
</script>

<script>
function rate(stars, element, productId) {
	  var starsSelector = $(element).closest('.stars');
	  $(starsSelector).find('.star').removeClass('checked');

	  for (var i = 1; i <= stars; i++) {
	    $(starsSelector).find('.star:nth-child(' + i + ')').addClass('checked');
	  }

	  $('#stelle-' + productId).val(stars); // Imposta il valore del campo di input "stelle" con il numero di stelle selezionate
	}

</script>
<script>
  $(document).ready(function() {
    $('.star-rating').click(function() {
      var stars = $(this).index() + 1; // Ottieni l'indice della stella cliccata
      $('#stelle').val(stars); // Imposta il valore del campo di input "stelle"

      // Rimuovi la classe "checked" da tutte le stelle
      $('.star-rating').removeClass('checked');

      // Aggiungi la classe "checked" alle stelle selezionate
      $(this).addClass('checked');
      $(this).prevAll('.star-rating').addClass('checked');
    });
  });
</script>

</body>
</html>
