package Model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Punto unico di accesso al DataSource "jdbc/sito" configurato in context.xml.
 * Tutti i DAO ottengono le connessioni da qui, cosi' la lookup JNDI non e'
 * ripetuta in ogni classe.
 */
public final class Database {

    private static final Logger LOGGER = Logger.getLogger(Database.class.getName());
    private static final String JNDI_NAME = "jdbc/sito";

    private static DataSource dataSource;

    static {
        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            dataSource = (DataSource) envCtx.lookup(JNDI_NAME);
        } catch (NamingException e) {
            LOGGER.log(Level.SEVERE, "Impossibile ottenere il DataSource " + JNDI_NAME, e);
        }
    }

    private Database() {
    }

    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            throw new SQLException("DataSource " + JNDI_NAME + " non disponibile");
        }
        return dataSource.getConnection();
    }
}
