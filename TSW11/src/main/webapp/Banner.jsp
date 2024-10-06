<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Banner</title>
<style>
	.banner {
	background-color: #fefaf6;
	position: relative;
	height: 110px;
	width: 100%;
	display: flex; /* Added */
	align-items: center; /* Added to vertically center */
	justify-content: space-between; /* Space between logo and icons */
    padding: 0 10px; /* Optional: Adjust for side padding */
}

#image {
	margin-left: 10px; /* Adjust as needed */
	z-index: 1;
	width: 125px;
	height: auto;
}

.dx {
    display: flex;
    align-items: center; /* Center icons vertically with the logo */
    /*gap: 15px;  Space between icons */
    margin-right: 20px;
}

.dx img {
    width: 30px;
    height: 30px;
    margin-left: 10px;
    margin-right: 10px;
}

.cerca {
    display: none;
    
}

#searchInput{
border: 2px solid black;
border-radius: 5px;
}
</style>
</head>
<body>
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
<div class="cerca" style="display: none;">
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
</body>
</html>