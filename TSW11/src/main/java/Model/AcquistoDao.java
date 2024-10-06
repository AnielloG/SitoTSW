package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class AcquistoDao {
	
		private static DataSource ds;
	    static {
			try {
				Context initCtx = new InitialContext();
				Context envCtx = (Context) initCtx.lookup("java:comp/env");

				ds = (DataSource) envCtx.lookup("jdbc/sito");

			} catch (NamingException e) {
				System.out.println("Error:" + e.getMessage());
			}
		}
	    public AcquistoDao() {}
	    public void inserimentoaq(String provincia, String indirizzo, String cap, String citta, Cart cart, String email,
                String pagamento, String numeroCarta, String titolareCarta, String scadenzaCarta, String cvv) {
Date dataCorrente = new Date();
java.sql.Date dataOrdine = new java.sql.Date(dataCorrente.getTime());
double prezzotot = 0;
for (Prodotto p : cart.getProducts()) {
prezzotot += p.getPrezzo();
}

Connection connection = null;

String sql1 = "INSERT INTO ordine (dataOrdine, totale, stato, email, indirizzo, citta, provincia, cap) VALUES (?, ?, 'In Preparazione',?, ?, ?, ?, ?)";
String sql2 = "SELECT MAX(numeroOrdine) AS numeroOrdineMax FROM ordine";
String sql3 = "INSERT INTO composizione(IVA, totale, codP, numeroO) VALUES(?, ?, ?, ?)";
String deleteSQL = "UPDATE product SET quantita = 1 WHERE id = ?";
String sql4 = "INSERT INTO pagamento (tipo, titolare, numero_carta, scadenza, CVV, n_Ordine) VALUES(?,?,?,?,?,?)";

try {
connection = ds.getConnection();

// Primo PreparedStatement (sql1)
try (PreparedStatement preparedStatement1 = connection.prepareStatement(sql1)) {
  preparedStatement1.setDate(1, dataOrdine);
  preparedStatement1.setDouble(2, prezzotot);
  preparedStatement1.setString(3, email);
  preparedStatement1.setString(4, indirizzo);
  preparedStatement1.setString(5, citta);
  preparedStatement1.setString(6, provincia);
  preparedStatement1.setString(7, cap);
  preparedStatement1.executeUpdate();
}

// Secondo PreparedStatement (sql2) e ResultSet
int numeroOrdineMax = -1;
try (PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
   ResultSet resultSet = preparedStatement2.executeQuery()) {
  if (resultSet.next()) {
      numeroOrdineMax = resultSet.getInt("numeroOrdineMax");
  }
}

// Terzo PreparedStatement (sql3)
for (Prodotto p : cart.getProducts()) {
  try (PreparedStatement preparedStatement3 = connection.prepareStatement(sql3)) {
      preparedStatement3.setDouble(1, p.getIva());
      preparedStatement3.setDouble(2, p.getPrezzo());
      preparedStatement3.setInt(3, p.getID());
      preparedStatement3.setInt(4, numeroOrdineMax);
      preparedStatement3.executeUpdate();
  }
}

// Quarto PreparedStatement (deleteSQL)
for (Prodotto p : cart.getProducts()) {
  try (PreparedStatement preparedStatement4 = connection.prepareStatement(deleteSQL)) {
      preparedStatement4.setInt(1, p.getID());
      preparedStatement4.executeUpdate();
  }
}

// Quinto PreparedStatement (sql4)
try (PreparedStatement preparedStatement5 = connection.prepareStatement(sql4)) {
  preparedStatement5.setString(1, pagamento);
  preparedStatement5.setString(2, titolareCarta);
  preparedStatement5.setString(3, numeroCarta);
  preparedStatement5.setString(4, scadenzaCarta);
  preparedStatement5.setString(5, cvv);
  preparedStatement5.setInt(6, numeroOrdineMax);
  preparedStatement5.executeUpdate();
}

} catch (SQLException e) {
e.printStackTrace();
} finally {
if (connection != null) {
  try {
      connection.close();
  } catch (SQLException e) {
      e.printStackTrace();
  }
}
}
}

	}
