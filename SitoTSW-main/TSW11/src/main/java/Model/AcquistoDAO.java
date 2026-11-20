package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AcquistoDAO {

    private static final String INSERT_ORDINE = "INSERT INTO ordine "
            + "(dataOrdine, totale, stato, email, indirizzo, citta, provincia, cap) "
            + "VALUES (?, ?, 'In Preparazione', ?, ?, ?, ?, ?)";
    private static final String INSERT_COMPOSIZIONE = "INSERT INTO composizione (IVA, totale, codP, numeroO) "
            + "VALUES (?, ?, ?, ?)";
    private static final String UPDATE_QUANTITA = "UPDATE product SET quantita = 1 WHERE id = ?";
    private static final String INSERT_PAGAMENTO = "INSERT INTO pagamento "
            + "(tipo, titolare, numero_carta, scadenza, CVV, n_Ordine) VALUES (?, ?, ?, ?, ?, ?)";

    /**
     * Registra l'ordine, la sua composizione e il pagamento in un'unica
     * transazione: o vengono scritti tutti i dati o nessuno.
     *
     * @throws SQLException se un qualsiasi inserimento fallisce (viene fatto il rollback)
     */
    public void inserimentoaq(String provincia, String indirizzo, String cap, String citta, Cart cart,
            String email, String pagamento, String numeroCarta, String titolareCarta,
            String scadenzaCarta, String cvv) throws SQLException {

        double totale = 0;
        for (Prodotto p : cart.getProducts()) {
            totale += p.getPrezzo();
        }

        try (Connection connection = Database.getConnection()) {
            connection.setAutoCommit(false);
            try {
                int numeroOrdine = inserisciOrdine(connection, totale, email, indirizzo, citta, provincia, cap);
                inserisciComposizione(connection, cart, numeroOrdine);
                inserisciPagamento(connection, numeroOrdine, pagamento, titolareCarta, numeroCarta,
                        scadenzaCarta, cvv);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        }
    }

    private int inserisciOrdine(Connection connection, double totale, String email, String indirizzo,
            String citta, String provincia, String cap) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(INSERT_ORDINE, Statement.RETURN_GENERATED_KEYS)) {
            ps.setDate(1, new java.sql.Date(System.currentTimeMillis()));
            ps.setDouble(2, totale);
            ps.setString(3, email);
            ps.setString(4, indirizzo);
            ps.setString(5, citta);
            ps.setString(6, provincia);
            ps.setString(7, cap);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
                throw new SQLException("Numero ordine non generato");
            }
        }
    }

    private void inserisciComposizione(Connection connection, Cart cart, int numeroOrdine) throws SQLException {
        try (PreparedStatement composizione = connection.prepareStatement(INSERT_COMPOSIZIONE);
             PreparedStatement quantita = connection.prepareStatement(UPDATE_QUANTITA)) {
            for (Prodotto p : cart.getProducts()) {
                composizione.setDouble(1, p.getIva());
                composizione.setDouble(2, p.getPrezzo());
                composizione.setInt(3, p.getID());
                composizione.setInt(4, numeroOrdine);
                composizione.executeUpdate();

                quantita.setInt(1, p.getID());
                quantita.executeUpdate();
            }
        }
    }

    private void inserisciPagamento(Connection connection, int numeroOrdine, String tipo, String titolare,
            String numeroCarta, String scadenza, String cvv) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(INSERT_PAGAMENTO)) {
            ps.setString(1, tipo);
            ps.setString(2, titolare);
            ps.setString(3, numeroCarta);
            ps.setString(4, scadenza);
            ps.setString(5, cvv);
            ps.setInt(6, numeroOrdine);
            ps.executeUpdate();
        }
    }
}
