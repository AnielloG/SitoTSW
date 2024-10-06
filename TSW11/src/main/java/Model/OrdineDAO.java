package Model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class OrdineDAO {
	private static DataSource ds;
    private List<Ordine> ordini;
    private static final String TABLE_NAME = "ordine";
    static {
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");

			ds = (DataSource) envCtx.lookup("jdbc/sito");

		} catch (NamingException e) {
			System.out.println("Error:" + e.getMessage());
		}
	}
    
    public OrdineDAO() {
        ordini = new ArrayList<>();
    }
    
    public void aggiungiOrdine(Ordine ordine) {
        ordini.add(ordine);
    }
    
    public List<Ordine> getOrdini(String email) {
    	Connection connection = null;
	    PreparedStatement preparedStatement = null;
    	List<Ordine> ordini = new ArrayList<>();
    	String selectSQL = "SELECT * FROM ordine WHERE email = ?";
    	
    	try {
	        connection = ds.getConnection();
	        preparedStatement = connection.prepareStatement(selectSQL);
	        preparedStatement.setString(1, email);

	        ResultSet rs = preparedStatement.executeQuery();

	        while (rs.next()) {
                int numeroOrdine = rs.getInt("numeroOrdine");
                Date data = rs.getDate("dataOrdine");
                double totale = rs.getDouble("totale");
                String stato = rs.getString("stato");
                String indirizzo = rs.getString("indirizzo");
                String cap = rs.getString("cap");
                String provincia = rs.getString("provincia");
                String citta = rs.getString("citta");
                

                Ordine ordine = new Ordine();
                ordine.setCap(cap);
                ordine.setCitta(citta);
                ordine.setData(data);
                ordine.setIndirizzo(indirizzo);
                ordine.setProvincia(provincia);
                ordine.setTotale(totale);
                ordine.setNumeroOrdine(numeroOrdine);
                ordine.setStato(stato);
                ordini.add(ordine);
	        }

	    } catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
	        try {
	            if (preparedStatement != null)
	                preparedStatement.close();
	        } catch (SQLException e) {
				
				e.printStackTrace();
			} finally {
	            if (connection != null)
					try {
						connection.close();
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
	        }
	    }

	    return ordini;

    }
    

    
    public Ordine getOrdineByNumero(int numeroOrdine) {
        for (Ordine ordine : ordini) {
            if (ordine.getNumeroOrdine() == numeroOrdine) {
                return ordine;
            }
        }
        return null;
    }
    
    public void aggiornaStatoOrdine(int numeroOrdine, String nuovoStato) {
        Ordine ordine = getOrdineByNumero(numeroOrdine);
        if (ordine != null) {
            ordine.setStato(nuovoStato);
        }
    }

	public List<Prodotto> getProdotti(int idO) {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
    	List<Prodotto> prodotti = new ArrayList<Prodotto>();
    	String selectSQL = "SELECT product.id, product.nome, product.foto FROM product, composizione" 
    		    + " WHERE composizione.codP = product.id"
    		    + " AND composizione.numeroO = ?";

    	
    	try {
	        connection = ds.getConnection();
	        preparedStatement = connection.prepareStatement(selectSQL);
	        preparedStatement.setInt(1, idO);

	        ResultSet rs = preparedStatement.executeQuery();

	        while (rs.next()) {
	        	int id = rs.getInt("product.id");
	        	String nome = rs.getString("product.nome");
	        	byte[] foto = rs.getBytes("product.foto");
	        	Prodotto bean = new Prodotto();
	        	bean.setID(id);
	        	bean.setNome(nome);
	        	bean.setImg(foto);
                prodotti.add(bean);
	        }

	    } catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
	        try {
	            if (preparedStatement != null)
	                preparedStatement.close();
	        } catch (SQLException e) {
				
				e.printStackTrace();
			} finally {
	            if (connection != null)
					try {
						connection.close();
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
	        }
	    }

	    return prodotti;
	}
    
	public List<Ordine> getAllOrdini() throws SQLException {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    List<Ordine> ordini = new ArrayList<>();
	    String selectSQL = "SELECT * FROM " + TABLE_NAME;

	    try {
	        connection = ds.getConnection();
	        preparedStatement = connection.prepareStatement(selectSQL);

	        ResultSet rs = preparedStatement.executeQuery();

	        while (rs.next()) {
	            int numeroOrdine = rs.getInt("numeroOrdine");
	            Date data = rs.getDate("dataOrdine");
	            double totale = rs.getDouble("totale");
	            String stato = rs.getString("stato");
	            String indirizzo = rs.getString("indirizzo");
	            String cap = rs.getString("cap");
	            String provincia = rs.getString("provincia");
	            String citta = rs.getString("citta");
	            String email = rs.getString("email");

	            Ordine ordine = new Ordine();
	            ordine.setCap(cap);
	            ordine.setCitta(citta);
	            ordine.setData(data);
	            ordine.setIndirizzo(indirizzo);
	            ordine.setProvincia(provincia);
	            ordine.setTotale(totale);
	            ordine.setNumeroOrdine(numeroOrdine);
	            ordine.setStato(stato);
	            ordine.setEmail(email); 
	            ordini.add(ordine);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
            if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
        }
	    return ordini;
	}

	public List<Ordine> searchByEmail(String email) throws SQLException {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;

	    List<Ordine> ordini = new ArrayList<>();
	    String selectSQL = "SELECT * FROM ordine WHERE email LIKE ?";

	    try {
	        connection = ds.getConnection();
	        preparedStatement = connection.prepareStatement(selectSQL);
	        
	        preparedStatement.setString(1, "%" + email + "%");

	        ResultSet rs = preparedStatement.executeQuery();

	        while (rs.next()) {
	            Ordine ordine = new Ordine();
	            ordine.setNumeroOrdine(rs.getInt("numeroOrdine"));
	            ordine.setData(rs.getDate("dataOrdine"));
	            ordine.setTotale(rs.getDouble("totale"));
	            ordine.setStato(rs.getString("stato"));
	            ordine.setEmail(rs.getString("email"));
	            ordine.setIndirizzo(rs.getString("indirizzo"));
	            ordine.setCitta(rs.getString("citta"));
	            ordine.setProvincia(rs.getString("provincia"));
	            ordine.setCap(rs.getString("cap"));

	            ordini.add(ordine);
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

	    return ordini;
	}

	public void ValutaProd(String email, int codP, int val) {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    String sql1 ="INSERT INTO recensioni (valutazione, email, codp)"+
		"VALUES (?,?,?)";
	    try {
	        connection = ds.getConnection();
	        preparedStatement = connection.prepareStatement(sql1);
	        preparedStatement.setInt(1, val);
	        preparedStatement.setString(2,email);
	        preparedStatement.setInt(3,codP);
	        preparedStatement.executeUpdate();
		
	}catch (SQLException e) {
		
		e.printStackTrace();
	} finally {
        try {
            if (preparedStatement != null)
                preparedStatement.close();
        } catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
            if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
        }
    }

}
	
	public synchronized Ordine doRetrieveByKey(int numeroOrdine, String key2) throws SQLException {
		Connection con = null;
		PreparedStatement statement = null;
		Ordine ordine = new Ordine();
		
		String query = "SELECT * FROM " + OrdineDAO.TABLE_NAME + " WHERE numeroOrdine = ? AND email = ?";
		
		try {
			con = DriverManagerConnectionPool.getConnection();
			statement = con.prepareStatement(query);
			statement.setInt(1, numeroOrdine);
			statement.setString(2, key2);
			
			
			ResultSet result = statement.executeQuery();
			
			while(result.next()) {
				ordine.setNumeroOrdine(result.getInt("numeroOrdine"));
				ordine.setEmail(result.getString("email"));
				ordine.setStato(result.getString("stato"));
				ordine.setTotale(result.getDouble("totale"));
				ordine.setData(result.getDate("dataOrdine"));
				ordine.setCitta(result.getString("citta"));
				ordine.setCap(result.getString("cap"));
				ordine.setIndirizzo(result.getString("indirizzo"));
				ordine.setProvincia(result.getString("provincia"));
			}
			System.out.println("Ordine indirizzo Recuperato: " + ordine.getIndirizzo());
			System.out.println("Ordine numero Recuperato: " + ordine.getNumeroOrdine());

		} finally {
			try {
				if(statement != null) {
					statement.close();
				}
			} finally {
				DriverManagerConnectionPool.releaseConnection(con);
			}
		}

		return ordine;
	}
}
