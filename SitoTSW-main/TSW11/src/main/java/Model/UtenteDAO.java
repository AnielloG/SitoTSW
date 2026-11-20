package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UtenteDAO {

    private static final String TABLE_NAME = "utente";

    private static final String INSERT_COMPLETO = "INSERT INTO " + TABLE_NAME
            + " (email, pass, nome, cognome, indirizzo, citta, provincia, cap, tipo_account)"
            + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String INSERT_RISTRETTO = "INSERT INTO " + TABLE_NAME
            + " (email, pass, nome, cognome) VALUES (?, ?, ?, ?)";
    private static final String SELECT_BY_EMAIL = "SELECT * FROM " + TABLE_NAME + " WHERE email = ?";
    private static final String SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
    private static final String SEARCH_BY_EMAIL = "SELECT * FROM " + TABLE_NAME + " WHERE email LIKE ?";
    private static final String UPDATE = "UPDATE " + TABLE_NAME
            + " SET nome = ?, cognome = ?, indirizzo = ?, citta = ?, provincia = ?, cap = ?, pass = ?"
            + " WHERE email = ?";

    public void doSave(Utente u) throws SQLException {
        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_COMPLETO)) {
            ps.setString(1, u.getEmail());
            ps.setString(2, u.getPass());
            ps.setString(3, u.getNome());
            ps.setString(4, u.getCognome());
            ps.setString(5, u.getIndirizzo());
            ps.setString(6, u.getCitta());
            ps.setString(7, u.getProvincia());
            ps.setString(8, u.getCap());
            ps.setInt(9, u.getTipo_account());
            ps.executeUpdate();
        }
    }

    public void doSaveRistretto(Utente u) throws SQLException {
        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_RISTRETTO)) {
            ps.setString(1, u.getEmail());
            ps.setString(2, u.getPass());
            ps.setString(3, u.getNome());
            ps.setString(4, u.getCognome());
            ps.executeUpdate();
        }
    }

    public Utente doRetrieveByEmail(String email) throws SQLException {
        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_BY_EMAIL)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapRow(rs) : null;
            }
        }
    }

    public void doUpdate(Utente utente) throws SQLException {
        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE)) {
            ps.setString(1, utente.getNome());
            ps.setString(2, utente.getCognome());
            ps.setString(3, utente.getIndirizzo());
            ps.setString(4, utente.getCitta());
            ps.setString(5, utente.getProvincia());
            ps.setString(6, utente.getCap());
            ps.setString(7, utente.getPass());
            ps.setString(8, utente.getEmail());
            ps.executeUpdate();
        }
    }

    public List<Utente> doRetrieveAll() throws SQLException {
        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_ALL)) {
            return readList(ps);
        }
    }

    public List<Utente> searchByEmail(String email) throws SQLException {
        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(SEARCH_BY_EMAIL)) {
            ps.setString(1, "%" + email + "%");
            return readList(ps);
        }
    }

    private List<Utente> readList(PreparedStatement ps) throws SQLException {
        List<Utente> utenti = new ArrayList<>();
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                utenti.add(mapRow(rs));
            }
        }
        return utenti;
    }

    private Utente mapRow(ResultSet rs) throws SQLException {
        Utente utente = new Utente();
        utente.setEmail(rs.getString("email"));
        utente.setNome(rs.getString("nome"));
        utente.setCognome(rs.getString("cognome"));
        utente.setIndirizzo(rs.getString("indirizzo"));
        utente.setCitta(rs.getString("citta"));
        utente.setProvincia(rs.getString("provincia"));
        utente.setCap(rs.getString("cap"));
        utente.setPass(rs.getString("pass"));
        utente.setTipo_account(rs.getInt("tipo_account"));
        return utente;
    }
}
