package com.epam.gtc.dao;

import com.epam.gtc.dao.entities.UserEntity;
import com.epam.gtc.dao.util.DBManager;
import com.epam.gtc.exceptions.DAOException;
import com.epam.gtc.exceptions.Messages;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * MySQL User DAO implementation
 *
 * @author Fazliddin Makhsudov
 */
public class MySQLUserDAOImpl implements UserDAO {
    private static final Logger LOG = Logger.getLogger(MySQLUserDAOImpl.class);
    private final Extractor<UserEntity> userExtractor = new UserExtractor();

    /**
     * Adds new user.
     *
     * @param user user to add
     * @return int an entity id
     *
     * @throws DAOException db exception
     */
    @Override
    public int create(final UserEntity user) throws DAOException {
        int cond = -1;
        if (user.getId() != 0 && user.getId() > 0) {
            return 0;
        }
        final String query = "INSERT INTO users (name, surname, email, password, role_id) VALUES (?, ?, ?, ?, ?);";
        DBManager dbm;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            pstmt = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            int k = 1;
            pstmt.setString(k++, user.getName());
            pstmt.setString(k++, user.getSurname());
            pstmt.setString(k++, user.getEmail());
            pstmt.setString(k++, user.getPassword());
            pstmt.setInt(k, user.getRoleId());

            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs != null && rs.next()) {
                cond = rs.getInt(1);
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_INSERT_USER, ex);
            throw new DAOException(Messages.ERR_CANNOT_INSERT_USER, ex);
        } finally {
            DBManager.close(con, pstmt, rs);
        }
        return cond;
    }

    /**
     * Returns a user with the given identifier.
     *
     * @param id User identifier
     * @return User entity object
     *
     * @throws DAOException db exception
     */
    @Override
    public UserEntity read(int id) throws DAOException {
        final String query = "SELECT * FROM users WHERE id = ?;";
        UserEntity user = null;
        DBManager dbm;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user = userExtractor.extract(rs);
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_BY_ID, ex);
            throw new DAOException(Messages.ERR_CANNOT_OBTAIN_USER_BY_ID, ex);
        } finally {
            DBManager.close(con, pstmt, rs);
        }
        return user;
    }

    /**
     * Returns a user with the given identifier.
     *
     * @param email User identifier
     * @return User entity object
     *
     * @throws DAOException db exception
     */
    @Override
    public UserEntity read(String email) throws DAOException {
        final String query = "SELECT * FROM users WHERE email = ?;";
        UserEntity user = null;
        DBManager dbm;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user = userExtractor.extract(rs);
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_BY_ID, ex);
            throw new DAOException(Messages.ERR_CANNOT_OBTAIN_USER_BY_EMAIL, ex);
        } finally {
            DBManager.close(con, pstmt, rs);
        }
        return user;
    }

    /**
     * Updates user.
     *
     * @param user user to update.
     * @throws DAOException db exception
     */
    @Override
    public boolean update(UserEntity user) throws DAOException {
        final String query = "UPDATE users SET name = ?, surname = ?, email = ?, password=?, role_id = ? WHERE id = ?;";
        DBManager dbm;
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            pstmt = con.prepareStatement(query);
            int k = 1;
            pstmt.setString(k++, user.getName());
            pstmt.setString(k++, user.getSurname());
            pstmt.setString(k++, user.getEmail());
            pstmt.setString(k++, user.getPassword());
            pstmt.setInt(k++, user.getRoleId());
            pstmt.setInt(k, user.getId());
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_UPDATE_USER);
            throw new DAOException(Messages.ERR_CANNOT_UPDATE_USER, ex);
        } finally {
            DBManager.close(con, pstmt);
        }
        return true;
    }

    /**
     * Deletes user
     *
     * @param id entity identifyer
     * @return boolean operation status
     *
     * @throws DAOException exception
     */
    @Override
    public boolean delete(int id) throws DAOException {
        if (id <= 0) {
            return false;
        }
        final String query = "DELETE FROM users where id = ?;";
        DBManager dbm;
        Connection con = null;
        PreparedStatement psmt = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            psmt = con.prepareStatement(query);
            psmt.setInt(1, id);
            psmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_DELETE_USER);
            throw new DAOException(Messages.ERR_CANNOT_DELETE_USER, ex);
        } finally {
            DBManager.close(con, psmt);
        }
        return true;
    }

    /**
     * Reads all users
     *
     * @return list of users
     *
     * @throws DAOException exception
     */
    @Override
    public List<UserEntity> readAll() throws DAOException {
        final String query = "SELECT * FROM users;";
        List<UserEntity> userList = new ArrayList<>();
        DBManager dbm;
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                userList.add(userExtractor.extract(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_READ_ALL_USERS, ex);
            throw new DAOException(Messages.ERR_CANNOT_READ_ALL_USERS, ex);
        } finally {
            DBManager.close(con, stmt, rs);
        }
        return userList;
    }

    /**
     * Counts number of users
     *
     * @return number of users
     */
    @Override
    public int countAllUsers() throws DAOException {
        final String query = "select count(*) from users;";
        DBManager dbm;
        Connection con = null;
        Statement smt = null;
        ResultSet rs = null;
        int usersNumber = 0;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            smt = con.createStatement();
            rs = smt.executeQuery(query);
            rs.next();
            usersNumber = rs.getInt(1);
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_COUNT_ALL_USERS);
            throw new DAOException(Messages.ERR_CANNOT_COUNT_ALL_USERS, ex);
        } finally {
            DBManager.close(con, smt, rs);
        }
        return usersNumber;
    }

    /**
     * Reads users from start row till row number
     *
     * @param offset row from which starts reading
     * @param limit  number
     * @return list of UserEntities
     */
    @Override
    public List<UserEntity> readUsers(int offset, int limit) throws DAOException {
        final String query = "SELECT * FROM users LIMIT ?,?;";
        List<UserEntity> userList = new ArrayList<>();
        DBManager dbm;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            psmt = con.prepareStatement(query);
            psmt.setInt(1, offset);
            psmt.setInt(2, limit);
            rs = psmt.executeQuery();
            while (rs.next()) {
                userList.add(userExtractor.extract(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_READ_USERS_WITH_LIMITATION, ex);
            throw new DAOException(Messages.ERR_CANNOT_READ_USERS_WITH_LIMITATION, ex);
        } finally {
            DBManager.close(con, psmt, rs);
        }
        return userList;
    }

    /**
     * Extracts userEntity from Resultset
     */
    private static class UserExtractor extends Extractor<UserEntity> {
        @Override
        public UserEntity extract(ResultSet rs) throws DAOException {
            try {
                return mapper(rs, UserEntity.class);
            } catch (SQLException e) {
                LOG.error(Messages.ERR_CANNOT_EXTRACT_USER, e);
                throw new DAOException(Messages.ERR_CANNOT_EXTRACT_USER, e);
            }
        }
    }
}
