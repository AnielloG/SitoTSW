<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,Control.*,Model.*"%>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="javax.servlet.http.HttpServletResponse" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
Collection<?> products = (Collection<?>) request.getAttribute("products");
%>
<!DOCTYPE html>
<html lang="it">
<head>
	<title>Homepage</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<style>
body {
	margin: 0;
	overflow-x: hidden;
}
/* Stile per il contenitore dello slideshow */
.slideshow-container {
	width: 100%;
	position: relative;
	margin: auto;
}

/* Nasconde tutte le immagini inizialmente */
.mySlides {
	display: none;
}

/* Stile per i pulsanti del cambio immagine */
.prev, .next {
	cursor: pointer;
	position: absolute;
	top: 50%;
	width: auto;
	margin-top: -22px;
	padding: 16px;
	color: white;
	font-weight: bold;
	font-size: 18px;
	transition: 0.6s ease;
	border-radius: 0 3px 3px 0;
}

/* Stile per il pulsante prev */
.prev {
	left: 0;
	border-radius: 3px 0 0 3px;
	text-decoration:none;
}

/* Stile per il pulsante next */
.next {
	right: 0;
	border-radius: 3px 0 0 3px;
	text-decoration:none;
}

/* Stile per il punto attivo */
.dot {
	cursor: pointer;
	height: 15px;
	width: 15px;
	margin: 0 2px;
	background-color: #bbb;
	border-radius: 50%;
	display: inline-block;
	transition: background-color 0.6s ease;
}

/* Stile per il punto attivo */
.active, .dot:hover {
	background-color: #717171;
}

/*PER LE COSE SOTTO*/
/* Contenitore principale del carosello */
.container1 {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 20vw; /* Puoi aumentare l'altezza se vuoi più spazio verticale */
    width: 100%;
    flex-wrap: wrap; /* Aggiunto per permettere l'avvolgimento delle categorie */
    padding-top: 50px; /* Aggiungi padding per abbassare il contenitore */
    padding-bottom: 50px; /* Aggiungi padding per aggiungere spazio in basso */
    gap: 20px; /* Aggiungi gap per distanziare le categorie orizzontalmente */
}

/* Immagini all'interno del carosello */
.container1 img {
    margin: 20px 30px; /* Aggiungi margini per distanziare verticalmente */
    width: 18vw;
    height: auto;
}

/* Responsività */
@media only screen and (max-width: 768px) {
  .container1 {
    flex-wrap: wrap;
    height: auto; /* Permetti al contenitore di adattarsi al contenuto */
    padding-top: 30px; /* Riduci il padding per dispositivi più piccoli */
    padding-bottom: 30px;
    gap: 10px; /* Riduci il gap per dispositivi più piccoli */
  }

  .container1 img {
    width: 40vw;
    margin: 10px;
  }

  body {
    overflow-x: hidden;
  }
}

/* Stile per le immagini nel carosello */
.carousel-inner img {
    display: none; /* Nascondi tutte le immagini di default */
     /* Assicurati che le immagini abbiano una larghezza del 100% per riempire il contenitore */
}

.carousel-inner img.active {
    display: block; /* Mostra solo l'immagine attiva */
    border-radius: 15px;
}


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
    position: absolute;
    top: 100%; /* Posiziona sotto l'icona di ricerca */
    right: 0;
    background-color: white;
    padding: 10px;
    border: 1px solid #ddd;
    z-index: 1000;
    
}

#searchInput{
border: 2px solid black;
border-radius: 5px;
}
 .cerca {
    position: relative;
    display: none; /* Assicura che il contenitore si adatti al contenuto */
}

#suggestions {
    position: absolute;
    top: 100%; /* Posiziona i suggerimenti subito sotto la barra di ricerca */
    left: 0;
    width: 100%;
    background-color: white;
    border: 1px solid #ddd;
    box-shadow: 0 2px 4px rgba(0,0,0,0.1);
    z-index: 1000;
    max-height: 200px;
    overflow-y: auto;
}

#suggestions div {
    padding: 10px;
    cursor: pointer;
}

#suggestions div:hover {
    background-color: #f1f1f1;
}

</style>
</head>
<body>
	 <div class="banner"> 
        <a href="Home.jsp"><img src="assets/images/nuovologo.png" id="image" alt="#"></a>
        <div class="dx">
            <% if (session.getAttribute("email") == null) { %>
                <a href="#0" id="cercap"><img src="assets/images/cerca.png" alt="#"></a>
                <div class="cerca">
                    <form action="ProductControl" method="GET" id="searchForm" onsubmit="submitSearch(event)">
                <input type="hidden" name="action" value="search">
                <input type="text" name="nome" id="searchInput" placeholder="Cerca prodotto">
                <button type="submit">Cerca</button>
                    </form>
                    <div id="suggestions"></div>
                </div>
                <a href="Accedi.jsp"><img src="assets/images/utente.png" alt="#"></a>
                <a href="product?action=viewC"><img src="assets/images/cart.png" alt="#"></a>
            <% } else { %>
                <a href="#0" id="cercap"><img src="assets/images/cerca.png" alt="#"></a>
                <div class="cerca">
                    <form action="ProductControl" method="GET" id="searchForm" onsubmit="submitSearch(event)">
                <input type="hidden" name="action" value="search">
                <input type="text" name="nome" id="searchInput" placeholder="Cerca prodotto">
                <button type="submit">Cerca</button>
                    </form>
                    <div id="suggestions"></div>
                </div>
                <a href="ordine?action=ViewOrdini&email=<%=session.getAttribute("email") %>"><img src="assets/images/utente.png" alt="#"></a>
                <a href="registration?action=logout"><img src="assets/images/logout.png" alt="#"></a>
                <a href="product?action=viewC"><img src="assets/images/cart.png" alt="#"></a>
            <% } %>
        </div>
    </div>

	  <br>
	<!-- Contenitore dello slideshow -->
	<div class="slideshow-container">

		<!-- Prima immagine -->
		<div class="mySlides">
			<img src="assets/images/slideshow1.png" style="width:100%" alt="#">
		</div>
		<!-- Seconda immagine -->
		<div class="mySlides">
			<img src="assets/images/slideshow2.jpg" style="width:100%" alt="#">
		</div>
		<!-- terza immagine -->
		<div class="mySlides">
			<img src="assets/images/slideshow3.png" style="width:100%" alt="#">
		</div>

		<!-- Pulsante per la visualizzazione dell'immagine precedente -->
		<a class="prev" onclick="plusSlides(-1)">&#10094;</a>

		<!-- Pulsante per la visualizzazione dell'immagine successiva -->
		<a class="next" onclick="plusSlides(1)">&#10095;</a>

	</div>

	<br><br><br>

	<!-- Punti per il cambio immagine -->
	<div style="text-align:center">
		<span class="dot" onclick="currentSlide(1)"></span>
		<span class="dot" onclick="currentSlide(2)"></span>
		<span class="dot" onclick="currentSlide(3)"></span>
	</div>

	<!-- Script per lo slideshow automatico -->
	<script>
	// Inizializzazione dello slideshow
	var slideIndex = 1;
	showSlides(slideIndex);

	// Definizione della funzione per il cambio immagine
	function plusSlides(n) {
		showSlides(slideIndex += n);
	}

	// Definizione della funzione per il cambio immagine corrente
	function currentSlide(n) {
		showSlides(slideIndex = n);
	}

	// Definizione della funzione per il cambio immagine automatico ogni 2 secondi
	function autoSlides() {
		showSlides(slideIndex += 1);
	}

	// Definizione della funzione per la visualizzazione delle slide
	function showSlides(n) {
		var i;
		var slides = document.getElementsByClassName("mySlides");
		var dots = document.getElementsByClassName("dot");
		if (n > slides.length) {slideIndex = 1}
		if (n < 1) {slideIndex = slides.length}
		for (i = 0; i < slides.length; i++) {
			slides[i].style.display = "none";
		}
		for (i = 0; i < dots.length; i++) {
			dots[i].className = dots[i].className.replace(" active", "");
		}
		slides[slideIndex-1].style.display = "block";
		dots[slideIndex-1].className += " active";
	}

	// Avvio del cambio immagine automatico ogni 3 secondi
	setInterval(autoSlides, 3000);
</script>
<script type="text/javascript">
document.addEventListener("DOMContentLoaded", function () {
    // Array per tracciare la slide corrente di ciascun carosello
    let currentSlideIndexes = [0, 0, 0, 0];
    const carousels = document.querySelectorAll('.carousel');

    function showSlide(carouselIndex) {
        const carousel = carousels[carouselIndex];
        const slides = carousel.querySelectorAll('.carousel-inner img');
        slides.forEach((slide, index) => {
            slide.classList.remove('active'); // Nascondi tutte le immagini
            if (index === currentSlideIndexes[carouselIndex]) {
                slide.classList.add('active'); // Mostra solo l'immagine corrente
            }
        });
    }

    // Avvia ciascun carosello mostrando la prima slide
    carousels.forEach((carousel, index) => {
        showSlide(index);
    });

    // Carosello automatico per ciascun carosello
    setInterval(() => {
        carousels.forEach((carousel, index) => {
            currentSlideIndexes[index]++;
            if (currentSlideIndexes[index] >= carousel.querySelectorAll('.carousel-inner img').length) {
                currentSlideIndexes[index] = 0;
            }
            showSlide(index);
        });
    }, 4000); // Intervallo di 4 secondi
});
</script>
<script type="text/javascript">
//funzione suggerimenti con AJAX
$(document).ready(function() {
    var searchInput = $("#searchInput");
    var suggestionsDiv = $("#suggestions");
    var cercaSection = $(".cerca");
    var cercaLink = $("#cercap");

    cercaLink.on("click", function(event) {
        event.preventDefault();
        cercaSection.toggle();
        if (cercaSection.is(":visible")) {
            searchInput.focus();
        }
    });
    
    searchInput.on("input", function() {
        var query = $(this).val();
        if (query.length > 2) {
            $.ajax({
                url: "ProductControl",
                method: "GET",
                data: {
                    action: "suggest",
                    query: query
                },
                success: function(response) {
                    suggestionsDiv.empty();
                    if (response && response.length > 0) {
                        response.forEach(function(suggestion) {
                            suggestionsDiv.append('<div>' + suggestion + '</div>');
                        });
                        suggestionsDiv.show();
                    } else {
                        suggestionsDiv.hide();
                    }
                },
                error: function(xhr, status, error) {
                    console.error("Errore AJAX:", status, error);
                    suggestionsDiv.hide();
                }
            });
        } else {
            suggestionsDiv.hide();
        }
    });

        suggestionsDiv.on("click", "div", function() {
            searchInput.val($(this).text());
            suggestionsDiv.hide();
            $("#searchForm").submit();
        });

        $(document).on("click", function(event) {
            if (!$(event.target).closest(".cerca").length) {
                suggestionsDiv.hide();
            }
        });

        $("#cercap").on("click", function(event) {
            event.preventDefault();
            cercaSection.toggle();
        });
    });

function submitSearch(event) {
    event.preventDefault(); // Previeni il comportamento predefinito del link

    var searchInput = document.getElementById("searchInput");
    var nome = searchInput.value.trim();

    if (nome !== "") {
        var url = "product?action=search&nome=" + encodeURIComponent(nome);
        window.location.href = url;
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

	<div class="container1">
    <!-- Carosello per la categoria 'DONNA' -->
    <div class="carousel">
        <a href="product?action=dettaglio&sesso=F">
            <div class="carousel-inner">
                <img src="assets/images/DONNA1.png" alt="Donna Immagine 1" class="active">
                <img src="assets/images/DONNA2.png" alt="Donna Immagine 2">
                <img src="assets/images/DONNA3.png" alt="Donna Immagine 3">
            </div>
        </a>
    </div>

    <!-- Carosello per la categoria 'UOMO' -->
    <div class="carousel">
        <a href="product?action=dettaglio&sesso=M">
            <div class="carousel-inner">
                <img src="assets/images/UOMO3.png" alt="Uomo Immagine 1" class="active">
                <img src="assets/images/UOMO2.png" alt="Uomo Immagine 2">
                <img src="assets/images/UOMO1.png" alt="Uomo Immagine 3">
            </div>
        </a>
    </div>

    <!-- Carosello per la categoria 'ACCESSORI' -->
    <div class="carousel">
        <a href="product?action=dettaglio&categoria=accessori&sesso=M">
            <div class="carousel-inner">
                <img src="assets/images/ACCESSORI1.png" alt="Acc Immagine 1" class="active">
                <img src="assets/images/ACCESSORI.png" alt="Acc Immagine 2">
                <img src="assets/images/ACCESSORI2.png" alt="Acc Immagine 3">
            </div>
        </a>
    </div>

    <!-- Carosello per la categoria 'ALL' -->
    <div class="carousel">
        <a href="product?action=all">
            <div class="carousel-inner">
                <img src="assets/images/ALL1.png" alt="All Immagine 1" class="active">
                <img src="assets/images/ALL2.png" alt="All Immagine 2">
                <img src="assets/images/ALL.png" alt="All Immagine 3">
            </div>
        </a>
    </div>
</div>
	

<br><br><br>
<jsp:include page="footer.jsp"/>

</body>
</html>
