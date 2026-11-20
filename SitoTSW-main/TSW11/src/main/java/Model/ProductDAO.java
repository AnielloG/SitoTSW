package Model;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductDAO {

    private static final Logger LOGGER = Logger.getLogger(ProductDAO.class.getName());
    private static final String TABLE_NAME = "product";

    private static final String INSERT = "INSERT INTO " + TABLE_NAME
            + " (descrizione, prezzo, quantita, foto, sesso, nome, categoria, iva)"
            + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
    private static final String SELECT_BY_ID = SELECT_ALL + " WHERE id = ?";
    private static final String SELECT_BY_SESSO = SELECT_ALL + " WHERE sesso = ? OR sesso = 'U'";
    private static final String SELECT_BY_CATEGORIA = SELECT_ALL + " WHERE categoria = ?";
    private static final String SELECT_BY_SESSO_CATEGORIA = SELECT_ALL + " WHERE sesso = ? AND categoria = ?";
    private static final String SELECT_BY_NOME = SELECT_ALL + " WHERE nome LIKE ?";
    private static final String SELECT_BY_ORDINE = "SELECT product.* FROM " + TABLE_NAME
            + " JOIN composizione ON product.id = composizione.codP WHERE composizione.numeroO = ?";
    private static final String SELECT_SUGGESTIONS = "SELECT DISTINCT nome FROM " + TABLE_NAME
            + " WHERE nome LIKE ? LIMIT 5";
    private static final String UPDATE_QUANTITA = "UPDATE " + TABLE_NAME + " SET quantita = ? WHERE id = ?";
    private static final String UPDATE_PREZZO = "UPDATE " + TABLE_NAME + " SET prezzo = ? WHERE id = ?";
    private static final String SELECT_IMMAGINE = "SELECT img FROM immagini WHERE cod = ?";
    private static final String SELECT_IMMAGINI = "SELECT cod, img FROM immagini WHERE codprodotto = ?";
    private static final String SELECT_MEDIA = "SELECT ROUND(AVG(valutazione), 2) AS media_valutazione "
            + "FROM recensioni WHERE codp = ?";

    // ---------------------------------------------------------------- scrittura

    public void doSave(Prodotto product) throws SQLException {
        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT)) {
            ps.setString(1, product.getDescrizione());
            ps.setDouble(2, product.getPrezzo());
            ps.setInt(3, product.getQuantita());

            byte[] img = product.getImg();
            if (img != null && img.length > 0) {
                InputStream inputStream = new ByteArrayInputStream(img);
                ps.setBinaryStream(4, inputStream, img.length);
            } else {
                ps.setNull(4, Types.BLOB);
            }

            ps.setString(5, product.getSesso());
            ps.setString(6, product.getNome());
            ps.setString(7, product.getCategoria());
            ps.setDouble(8, product.getIva());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Errore durante l'inserimento del prodotto", e);
        }
    }

    /** Imposta la quantita' a 1 (prodotto disponibile). */
    public boolean doDelete(int code) throws SQLException {
        return updateQuantita(code, 1);
    }

    /** Imposta la quantita' a 0 (prodotto non piu' disponibile). */
    public boolean doupdateq(int code) throws SQLException {
        return updateQuantita(code, 0);
    }

    public void cambiaprezzo(String id, String prezzo) {
        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_PREZZO)) {
            ps.setDouble(1, Double.parseDouble(prezzo.replace("€", "").trim()));
            ps.setString(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore nell'aggiornamento del prezzo del prodotto " + id, e);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Formato del prezzo non valido: " + prezzo, e);
        }
    }

    private boolean updateQuantita(int code, int quantita) throws SQLException {
        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_QUANTITA)) {
            ps.setInt(1, quantita);
            ps.setInt(2, code);
            return ps.executeUpdate() != 0;
        }
    }

    // ----------------------------------------------------------------- lettura

    /** Prodotto con tutte le sue immagini; se non esiste restituisce un Prodotto vuoto. */
    public Prodotto doRetrieveByKey(int code) throws SQLException {
        Prodotto bean = new Prodotto();
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_ID)) {
                ps.setInt(1, code);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        bean = mapRow(rs);
                    }
                }
            }
            bean.setAllimg(loadImmagini(connection, code));
        }
        if (bean.getImg() == null) {
            bean.setImg(new byte[0]);
        }
        return bean;
    }

    /**
     * Prodotto {@code codp} che mostra come immagine principale quella con
     * codice {@code codef} (usato dalla galleria in ProductDetails.jsp).
     */
    public Prodotto Cambiafoto(int codef, int codp) throws SQLException {
        Prodotto bean = new Prodotto();
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_ID)) {
                ps.setInt(1, codp);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        bean = mapRow(rs);
                    }
                }
            }
            try (PreparedStatement ps = connection.prepareStatement(SELECT_IMMAGINE)) {
                ps.setInt(1, codef);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        bean.setImg(rs.getBytes("img"));
                    }
                }
            }
            bean.setAllimg(loadImmagini(connection, codp));
        }
        return bean;
    }

    public Collection<Prodotto> doRetrieveAll() throws SQLException {
        return query(SELECT_ALL);
    }

    public Collection<Prodotto> doRetrieveBySesso(String sesso) throws SQLException {
        return query(SELECT_BY_SESSO, sesso);
    }

    public Collection<Prodotto> doRetrieveByCategoria(String categoria) throws SQLException {
        return query(SELECT_BY_CATEGORIA, categoria);
    }

    public Collection<Prodotto> doRetrieveBySessoAndCategoria(String sesso, String categoria) throws SQLException {
        return query(SELECT_BY_SESSO_CATEGORIA, sesso, categoria);
    }

    public Collection<Prodotto> doRetrieveByOrdine(int numeroOrdine) throws SQLException {
        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_BY_ORDINE)) {
            ps.setInt(1, numeroOrdine);
            return readList(ps);
        }
    }

    public Collection<Prodotto> searchProducts(String nome) throws SQLException {
        return query(SELECT_BY_NOME, "%" + nome + "%");
    }

    public List<String> getSuggestions(String query) throws SQLException {
        List<String> suggestions = new ArrayList<>();
        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_SUGGESTIONS)) {
            ps.setString(1, "%" + query + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    suggestions.add(rs.getString("nome"));
                }
            }
        }
        return suggestions;
    }

    /** Media delle valutazioni del prodotto (0 se non ce ne sono). */
    public float media(int code) throws SQLException {
        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_MEDIA)) {
            ps.setInt(1, code);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getFloat("media_valutazione") : 0f;
            }
        }
    }

    // ----------------------------------------------------------------- helper

    /** Esegue una SELECT con parametri String e mappa tutte le righe in Prodotto. */
    private Collection<Prodotto> query(String sql, String... params) throws SQLException {
        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                ps.setString(i + 1, params[i]);
            }
            return readList(ps);
        }
    }

    private Collection<Prodotto> readList(PreparedStatement ps) throws SQLException {
        Collection<Prodotto> products = new LinkedList<>();
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                products.add(mapRow(rs));
            }
        }
        return products;
    }

    private ArrayList<Immagine> loadImmagini(Connection connection, int productId) throws SQLException {
        ArrayList<Immagine> immagini = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_IMMAGINI)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Immagine immagine = new Immagine();
                    immagine.setId(rs.getInt("cod"));
                    immagine.setImg(rs.getBytes("img"));
                    immagini.add(immagine);
                }
            }
        }
        return immagini;
    }

    /** Conversione unica riga -> Prodotto, usata da tutte le query. */
    private Prodotto mapRow(ResultSet rs) throws SQLException {
        Prodotto bean = new Prodotto();
        bean.setID(rs.getInt("id"));
        bean.setNome(rs.getString("nome"));
        bean.setDescrizione(rs.getString("descrizione"));
        bean.setPrezzo(rs.getDouble("prezzo"));
        bean.setQuantita(rs.getInt("quantita"));
        bean.setSesso(rs.getString("sesso"));
        bean.setCategoria(rs.getString("categoria"));
        bean.setIva(rs.getDouble("iva"));

        byte[] foto = rs.getBytes("foto");
        bean.setImg(foto != null ? foto : new byte[0]);
        return bean;
    }
}
