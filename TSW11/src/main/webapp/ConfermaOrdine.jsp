<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
	Cart cart = (Cart) request.getAttribute("cart");
%>
<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,Model.*"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Conferma</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #F6F5F7;
            text-align:center;
            color: #FEFAF6
        }

        h2 {
            color: #FEFAF6;
        }

		#conf {
		            display: flex;
		            align-items: center;
					text-align: center;
					justify-content: center;
		            width: 30vw;
					min-width: 400px;
		            margin: 0 auto;
		            padding: 20px;
		            background-color: #102c57;
		            border: 1px solid #ddd;
		            border-radius: 5px;
					font-family: Arial, Sans-Serif;
		        }
		
		#tab{
			width: 400px;
		}
		#tab th, td {
		    padding: 5px;
			vertical-align: top;
			
		  }
		  
		  #tab td:first-child {
		    text-align: left;
		   
			
		  }
		  
		  #tab td:last-child {
		    text-align: right;
		  }

		table.cart-table {
		    width: 100%;
		    text-align:center;
		    vertical-align: middle;
		    border-color: #f6f5f7; 
		    border-collapse: collapse;
		    margin-top: 20px;
		    transition: transform 0.3s ease-in-out;
		}
		table.cart-table:hover {
		    transform: scale(1.05);
		}
		
		#table1 td{ 
		align-items: center; 
		justify-content: center; 
		vertical-align:middle;
		}
		
		input[type="submit"]:hover {
		  background-color: #102C57;
		  border: 1px solid #f6f5f7;
		  border-radius: 14px;
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
	       #image{
	       width: 10vw;
	       height: 10vw;
	       min-width:150px;
	       min-height: 150px;
	       }
    </style>
</head>

<body>
<a href="Home.jsp"><img src="assets/images/nuovologo.png" id="image" alt="#"></a>
<div id="conf">
	<form action="acquista" method="post">
		<input type="hidden" name="action" value="completa">
		<input type="hidden" name="email" value="<%= session.getAttribute("email") %>">

		<h2>Conferma ordine</h2>
		<table id="tab">
		
		<tr>
		<input type="hidden" name="Tipocons" value="<%= request.getAttribute("TipoC") %>">
		<td align="left">Tipo di consegna</td>
		<td><%= request.getAttribute("TipoC") %></td>
		<% String tipoc = (String) request.getAttribute("TipoC");
		            if (tipoc.equals("Consegna a casa")) { %>
		</tr>
		<tr>
		<td align="left">Indirizzo</td>
		<td><input type="hidden" name="indirizzo" value="<%= session.getAttribute("indirizzo") %>">
   					<%= session.getAttribute("indirizzo") %>
  					<input type="hidden" name="citta" value="<%= session.getAttribute("citta") %>">
                    <%= session.getAttribute("citta") %>
                    <input type="hidden" name="provincia" value="<%= session.getAttribute("provincia") %>">
                    <%= session.getAttribute("provincia") %>
                    <input type="hidden" name="cap" value="<%= session.getAttribute("cap") %>">
                    <%= session.getAttribute("cap") %></td>
                    </tr>
    <%}else if(tipoc.equals("Altro indirizzo")){ %>
    <tr>
    <td align="left">Indirizzo</td>
    				<td><input type="hidden" name="indirizzo" value="<%= request.getAttribute("indirizzo") %>">
   				    <%= request.getAttribute("indirizzo") %>
   				    <input type="hidden" name="citta" value="<%= request.getAttribute("citta") %>">
                    <%= request.getAttribute("citta") %>
                    <input type="hidden" name="provincia" value="<%= request.getAttribute("provincia") %>">
                    <%= request.getAttribute("provincia") %>
                    <input type="hidden" name="cap" value="<%= request.getAttribute("cap") %>">
                    <%= request.getAttribute("cap") %>    </td>	
    <% }
    %></tr>
		<tr>
		<td></td>
		<td></td>
		</tr>
		<tr>
		<td></td>
		<td></td>
		</tr>
		<tr>
		<td></td>
		<td></td>
		</tr>
		<tr>
		 <input type="hidden" name="Pagamento" value="<%= request.getAttribute("Pagamento") %>">
		<td align="left">Pagamento</td>
		<td><%= request.getAttribute("Pagamento") %>
		<% String pagamento = (String) request.getAttribute("Pagamento");
		            if (pagamento.equals("Carta di credito")) { %> </td>
		</tr>
		<tr>
		 <input type="hidden" name="NumeroC" value="<%= request.getAttribute("numeroCarta") %>">
		<td align="left">Numero Carta</td>
		<td><%= request.getAttribute("numeroCarta") %></td>
		</tr>
		<tr>
		<input type="hidden" name="TitolareC" value="<%= request.getAttribute("card-holder") %>">
		<td align="left">Titolare Carta</td>
		<td><%= request.getAttribute("card-holder") %></td>
		</tr>
		<tr>
		 <input type="hidden" name="ScadenzaC" value="<%= request.getAttribute("scadenzaCarta") %>">
		<td align="left">Scadenza Carta</td>
		<td><%= request.getAttribute("scadenzaCarta") %></td>
		</tr>
		<tr>
		 <input type="hidden" name="cvv" value="<%= request.getAttribute("cvv") %>">
		<td align="left">CVV</td>
		<td><%= request.getAttribute("cvv") %></td>
		</tr>
		<% } %>
		</table>
		<% if (cart != null) { %>
			<table class="cart-table" border="1" id="table1">
				<% for (Prodotto p : cart.getProducts()) {
					byte[] imageB = p.getImg();
					String base64img = "";
					if (imageB != null) {
						base64img = Base64.getEncoder().encodeToString(imageB);
					}
				%>
					<tr data-product-id="<%= p.getID() %>">
						<td><img src="data:image/jpg;base64, <%= base64img %>" width="100" height="100" alt="#"></td>
						<td><%= p.getNome() %></td>
						<td><span id="totalPrice_<%= p.getID() %>"><%= p.getPrezzo() %>€</span></td>
					</tr>
				<% } %>
			</table>
		<% } %>
						<input type="submit" value="Acquista Ora">
			</form>
</div>

<script>
function handleAcquisto(event) {
    event.preventDefault(); // Blocca l'invio del form per gestirlo con JavaScript

    const form = document.getElementById('acquista-form');

    // Effettua una richiesta AJAX per inviare il form senza ricaricare la pagina
    const xhr = new XMLHttpRequest();
    xhr.open("POST", form.action, true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            // Controlla la risposta dal server
            const response = xhr.responseText;
            if (response.includes("successo")) {
                alert("Acquisto andato a buon fine!");
            } else {
                alert("Acquisto non andato a buon fine!");
            }
        }
    };

    // Ottieni i dati del form da inviare
    const formData = new FormData(form);
    xhr.send(new URLSearchParams(formData).toString());
    
    return false; // Blocca il normale invio del form
}
</script>

<br><br><br><br>
<br><br><br><br>
<jsp:include page="footer.jsp"/>
</body>
</html>
