package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class UtenteDao {
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


	private static final String TABLE_NAME = "utente";
			
	public synchronized void doSave(Utente u) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO " + UtenteDao.TABLE_NAME
				+ " (email,pass,nome,cognome,indirizzo,citta,provincia,cap, tipo_account) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, u.getEmail());
			preparedStatement.setString(2, u.getPass());
			preparedStatement.setString(3, u.getNome());
			preparedStatement.setString(4, u.getCognome());
			preparedStatement.setString(5, u.getIndirizzo());
			preparedStatement.setString(6, u.getCitta());
			preparedStatement.setString(7, u.getProvincia());
			preparedStatement.setString(8, u.getCap());
			preparedStatement.setInt(9, u.getTipo_account());

			preparedStatement.executeUpdate();

			//connection.commit();
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
	}
	
	public synchronized void doSaveRistretto(Utente u) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO " + UtenteDao.TABLE_NAME
				+ " (email,pass,nome,cognome) VALUES (?, ?, ?, ?)";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, u.getEmail());
			preparedStatement.setString(2, u.getPass());
			preparedStatement.setString(3, u.getNome());
			preparedStatement.setString(4, u.getCognome());	
			preparedStatement.executeUpdate();

			//connection.commit();
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
	}
	
	public Utente doRetrieveByEmail(String email) throws SQLException {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;

	    Utente utente = null;
	    String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE email = ?";

	    try {
	        connection = ds.getConnection();
	        preparedStatement = connection.prepareStatement(selectSQL);
	        preparedStatement.setString(1, email);

	        ResultSet rs = preparedStatement.executeQuery();

	        if (rs.next()) {
	            utente = new Utente();
	            utente.setEmail(rs.getString("email"));
	            utente.setNome(rs.getString("nome"));
	            utente.setCognome(rs.getString("cognome"));
	            utente.setIndirizzo(rs.getString("indirizzo"));
	            utente.setCitta(rs.getString("citta"));
	            utente.setProvincia(rs.getString("provincia"));
	            utente.setCap(rs.getString("cap"));
	            utente.setPass(rs.getString("pass"));
	            utente.setTipo_account(rs.getInt("tipo_account"));
	        }

	    } finally {
	        try {
	            if (preparedStatement != null)
	                preparedStatement.close();
	        } finally {
	            if (connection != null)
	                connection.close();
	        }
	    }

	    return utente;
	}
	
	public void doUpdate(Utente utente) throws SQLException {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;

	    String query = "UPDATE utente SET nome=?, cognome=?, indirizzo=?, citta=?, provincia=?, cap=?, pass=? WHERE email=?";
	    
	    try {
	    	connection = ds.getConnection();
	        
	        preparedStatement = connection.prepareStatement(query);

	        preparedStatement.setString(1, utente.getNome());
	        preparedStatement.setString(2, utente.getCognome());
	        preparedStatement.setString(3, utente.getIndirizzo());
	        preparedStatement.setString(4, utente.getCitta());
	        preparedStatement.setString(5, utente.getProvincia());
	        preparedStatement.setString(6, utente.getCap());
	        preparedStatement.setString(7, utente.getPass());
	        preparedStatement.setString(8, utente.getEmail());

	        preparedStatement.executeUpdate();
	    } finally {
	        if (preparedStatement != null) {
	            preparedStatement.close();
	        }
	        if (connection != null) {
	            connection.close();
	        }
	    }
	}
	
	public List<Utente> doRetrieveAll() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        List<Utente> utenti = new ArrayList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME;

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Utente utente = new Utente();
                utente.setEmail(rs.getString("email"));
                utente.setNome(rs.getString("nome"));
                utente.setCognome(rs.getString("cognome"));
                utente.setIndirizzo(rs.getString("indirizzo"));
                utente.setCitta(rs.getString("citta"));
                utente.setProvincia(rs.getString("provincia"));
                utente.setCap(rs.getString("cap"));
                utente.setPass(rs.getString("pass"));

                utenti.add(utente);
            }

        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } finally {
                if (connection != null)
                    connection.close();
            }
        }

        return utenti;
    }
	
	public List<Utente> searchByEmail(String email) throws SQLException {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;

	    List<Utente> utenti = new ArrayList<>();
	    String selectSQL = "SELECT * FROM utente WHERE email LIKE ?";

	    try {
	        connection = ds.getConnection();
	        preparedStatement = connection.prepareStatement(selectSQL);
	        
	        preparedStatement.setString(1, "%" + email + "%");

	        ResultSet rs = preparedStatement.executeQuery();

	        while (rs.next()) {
	            Utente utente = new Utente();
	            utente.setEmail(rs.getString("email"));
	            utente.setNome(rs.getString("nome"));
	            utente.setCognome(rs.getString("cognome"));
	            utente.setIndirizzo(rs.getString("indirizzo"));
	            utente.setCitta(rs.getString("citta"));
	            utente.setProvincia(rs.getString("provincia"));
	            utente.setCap(rs.getString("cap"));
	            utente.setPass(rs.getString("pass"));

	            utenti.add(utente);
	        }

	    } finally {
	        try {
	            if (preparedStatement != null)
	                preparedStatement.close();
	        } finally {
	            if (connection != null)
	                connection.close();
	        }
	    }

	    return utenti;
	}


}