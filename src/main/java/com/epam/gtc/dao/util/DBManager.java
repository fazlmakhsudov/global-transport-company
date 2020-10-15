package com.epam.gtc.dao.util;

import com.epam.gtc.exceptions.Messages;
import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;

/**
 * Database Manager,
 * introduces connection pool service
 *
 * @author Fazliddin Makhsudov
 */
public final class DBManager {
    private static final Logger LOG = Logger.getLogger(DBManager.class);
    private static DBManager instance;
    private DataSource ds;

    /**
     * Get instance.
     *
     * @return DBManagerMysql
     */
    public static synchronized DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    private DBManager() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            ds = (DataSource) envContext.lookup("jdbc/gtc");
            LOG.trace("Data source ==> " + ds);
        } catch (NamingException ex) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
        }
    }

    /**
     * Returns a DB connection from the Pool Connections. *
     *
     * @return DB connection.
     */
    public Connection getConnection() {
        Connection con = null;
        try {
            con = ds.getConnection();
        } catch (SQLException ex) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
        }
        return con;
    }

    /**
     * Closes resources.
     *
     * @param con  connection
     * @param stmt statement
     * @param rs   result set
     */
    public static void close(final Connection con, final Statement stmt, final ResultSet rs) {
        close(rs);
        close(stmt);
        close(con);
    }

    /**
     * Close resource
     *
     * @param con  connection
     * @param psmt Prepared statement
     */
    public static void close(final Connection con, final PreparedStatement psmt) {
        close(psmt);
        close(con);
    }

    /**
     * Closes a connection.
     *
     * @param con Connection to be closed.
     */
    public static void close(final Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                LOG.error(Messages.ERR_CANNOT_CLOSE_CONNECTION, ex);
            }
        }
    }

    /**
     * Rollbacks a connection.
     *
     * @param con Connection to be rollbacked.
     */
    public static void rollback(final Connection con) {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                LOG.error(Messages.CANNOT_ROLLBACK_TRANSACTION, ex);
            }
        }
    }

    private static void close(final Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                LOG.error(Messages.ERR_CANNOT_CLOSE_STATEMENT, ex);
            }
        }
    }


    private static void close(final PreparedStatement psmt) {
        if (psmt != null) {
            try {
                psmt.close();
            } catch (SQLException ex) {
                LOG.error(Messages.ERR_CANNOT_CLOSE_PREPARED_STATEMENT, ex);
            }
        }
    }

    private static void close(final ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                LOG.error(Messages.ERR_CANNOT_CLOSE_RESULTSET, ex);
            }
        }
    }
}