package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrdineDAO {

    private static final Logger LOGGER = Logger.getLogger(OrdineDAO.class.getName());
    private static final String TABLE_NAME = "ordine";

    private static final String SELECT_BY_EMAIL = "SELECT * FROM " + TABLE_NAME + " WHERE email = ?";
    private static final String SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
    private static final String SEARCH_BY_EMAIL = "SELECT * FROM " + TABLE_NAME + " WHERE email LIKE ?";
    private static final String SELECT_BY_KEY = "SELECT * FROM " + TABLE_NAME
            + " WHERE numeroOrdine = ? AND email = ?";
    private static final String SELECT_PRODOTTI = "SELECT product.id, product.nome, product.foto "
            + "FROM product, composizione "
            + "WHERE composizione.codP = product.id AND composizione.numeroO = ?";
    private static final String INSERT_RECENSIONE = "INSERT INTO recensioni (valutazione, email, codp) "
            + "VALUES (?, ?, ?)";

    /** Ordini di un utente. In caso di errore restituisce una lista vuota. */
    public List<Ordine> getOrdini(String email) {
        List<Ordine> ordini = new ArrayList<>();
        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_BY_EMAIL)) {
            ps.setString(1, email);
            ordini = readList(ps);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore nel recupero degli ordini di " + email, e);
        }
        return ordini;
    }

    /** Tutti gli ordini (usato dalla pagina amministratore). */
    public List<Ordine> getAllOrdini() {
        List<Ordine> ordini = new ArrayList<>();
        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_ALL)) {
            ordini = readList(ps);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore nel recupero di tutti gli ordini", e);
        }
        return ordini;
    }

    public List<Ordine> searchByEmail(String email) throws SQLException {
        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(SEARCH_BY_EMAIL)) {
            ps.setString(1, "%" + email + "%");
            return readList(ps);
        }
    }

    /** Ordine identificato da numero ed email; se non esiste restituisce un Ordine vuoto. */
    public Ordine doRetrieveByKey(int numeroOrdine, String email) throws SQLException {
        Ordine ordine = new Ordine();
        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_BY_KEY)) {
            ps.setInt(1, numeroOrdine);
            ps.setString(2, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ordine = mapRow(rs);
                }
            }
        }
        return ordine;
    }

    /** Prodotti (id, nome, foto) di un ordine. In caso di errore restituisce una lista vuota. */
    public List<Prodotto> getProdotti(int numeroOrdine) {
        List<Prodotto> prodotti = new ArrayList<>();
        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_PRODOTTI)) {
            ps.setInt(1, numeroOrdine);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Prodotto prodotto = new Prodotto();
                    prodotto.setID(rs.getInt("id"));
                    prodotto.setNome(rs.getString("nome"));
                    prodotto.setImg(rs.getBytes("foto"));
                    prodotti.add(prodotto);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore nel recupero dei prodotti dell'ordine " + numeroOrdine, e);
        }
        return prodotti;
    }

    public void ValutaProd(String email, int codP, int val) {
        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_RECENSIONE)) {
            ps.setInt(1, val);
            ps.setString(2, email);
            ps.setInt(3, codP);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore nell'inserimento della valutazione", e);
        }
    }

    private List<Ordine> readList(PreparedStatement ps) throws SQLException {
        List<Ordine> ordini = new ArrayList<>();
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ordini.add(mapRow(rs));
            }
        }
        return ordini;
    }

    private Ordine mapRow(ResultSet rs) throws SQLException {
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
        return ordine;
    }
}
