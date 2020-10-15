package com.epam.gtc.dao;

import com.epam.gtc.dao.entities.CityEntity;
import com.epam.gtc.exceptions.DAOException;
import com.epam.gtc.exceptions.Messages;
import com.epam.gtc.utils.DBManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLCityDAOImpl implements CityDAO {
    private static final Logger LOG = Logger.getLogger(MySQLCityDAOImpl.class);
    private final Extractor<CityEntity> cityExtractor = new CityExtractor();

    /**
     * Adds new city.
     *
     * @param city city to add
     * @return int entity id
     *
     * @throws DAOException db exception
     */
    @Override
    public int create(final CityEntity city) throws DAOException {
        final String query = "INSERT INTO cities (name) VALUES(?);";
        int cond = -1;
        if (city.getId() != 0 && city.getId() > 0) {
            return 0;
        }
        DBManager dbm;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            pstmt = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            int k = 1;
            pstmt.setString(k, city.getName());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs != null && rs.next()) {
                cond = rs.getInt(1);
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_INSERT_CITY, ex);
            throw new DAOException(Messages.ERR_CANNOT_INSERT_CITY, ex);
        } finally {
            DBManager.close(con, pstmt, rs);
        }
        return cond;
    }

    /**
     * Returns a city with the given identifier.
     *
     * @param id city identifier
     * @return city object
     *
     * @throws DAOException db exception
     */
    @Override
    public CityEntity read(int id) throws DAOException {
        final String query = "SELECT * FROM cities WHERE id=?;";
        CityEntity city = null;
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
                city = cityExtractor.extract(rs);
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_CITY_BY_ID, ex);
            throw new DAOException(Messages.ERR_CANNOT_OBTAIN_CITY_BY_ID, ex);
        } finally {
            DBManager.close(con, pstmt, rs);
        }
        return city;
    }

    /**
     * Returns a city with the given identifier.
     *
     * @param name city identifier
     * @return city object
     *
     * @throws DAOException db exception
     */
    @Override
    public CityEntity read(String name) throws DAOException {
        final String query = "SELECT * FROM cities WHERE name=?;";
        CityEntity city = null;
        DBManager dbm;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                city = cityExtractor.extract(rs);
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_CITY_BY_NAME, ex);
            throw new DAOException(Messages.ERR_CANNOT_OBTAIN_CITY_BY_NAME, ex);
        } finally {
            DBManager.close(con, pstmt, rs);
        }
        return city;
    }

    /**
     * Updates city.
     *
     * @param city city to update.
     * @throws DAOException db exception
     */
    @Override
    public boolean update(CityEntity city) throws DAOException {
        final String query = "UPDATE cities SET name = ? WHERE id = ?;";
        DBManager dbm;
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            pstmt = con.prepareStatement(query);
            int k = 1;
            pstmt.setString(k++, city.getName());
            pstmt.setInt(k, city.getId());
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_UPDATE_CITY);
            throw new DAOException(Messages.ERR_CANNOT_UPDATE_CITY, ex);
        } finally {
            DBManager.close(con, pstmt);
        }
        return true;
    }

    /**
     * Deletes city
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
        final String query = "DELETE FROM cities where id=?;";
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
            LOG.error(Messages.ERR_CANNOT_DELETE_CITY);
            throw new DAOException(Messages.ERR_CANNOT_DELETE_CITY, ex);
        } finally {
            DBManager.close(con, psmt);
        }
        return true;
    }

    /**
     * Reads all cities
     *
     * @return list of cities
     *
     * @throws DAOException exception
     */
    @Override
    public List<CityEntity> readAll() throws DAOException {
        final String query = "SELECT * FROM cities;";
        List<CityEntity> cityList = new ArrayList<>();
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
                cityList.add(cityExtractor.extract(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_READ_ALL_CITIES, ex);
            throw new DAOException(Messages.ERR_CANNOT_READ_ALL_CITIES, ex);
        } finally {
            DBManager.close(con, stmt, rs);
        }
        return cityList;
    }

    /**
     * Counts number of cities
     *
     * @return number of cities
     */
    @Override
    public int countAllCities() {
        final String query = "select count(*) from cities;";
        DBManager dbm;
        Connection con = null;
        Statement smt = null;
        ResultSet rs = null;
        int citiesNumber = 0;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            smt = con.createStatement();
            rs = smt.executeQuery(query);
            rs.next();
            citiesNumber = rs.getInt(1);
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_READ_ALL_CITIES);
        } finally {
            DBManager.close(con, smt, rs);
        }
        return citiesNumber;
    }

    /**
     * Reads cities from start row till row number
     *
     * @param offset row from which starts reading
     * @param limit  number
     * @return
     */
    @Override
    public List<CityEntity> readCities(int offset, int limit) throws DAOException {
        final String query = "SELECT * FROM cities LIMIT ?,?;";
        List<CityEntity> cityList = new ArrayList<>();
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
                cityList.add(cityExtractor.extract(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_READ_CITIES_WITH_LIMITATION, ex);
            throw new DAOException(Messages.ERR_CANNOT_READ_CITIES_WITH_LIMITATION, ex);
        } finally {
            DBManager.close(con, psmt, rs);
        }
        return cityList;
    }

    /**
     * Extracts CityEntity from Resultset
     */
    private static class CityExtractor extends Extractor<CityEntity> {
        @Override
        public CityEntity extract(ResultSet rs) throws DAOException {
            try {
                return mapper(rs, CityEntity.class);
            } catch (SQLException e) {
                LOG.error(Messages.ERR_CANNOT_EXTRACT_CITY, e);
                throw new DAOException(Messages.ERR_CANNOT_EXTRACT_CITY, e);
            }
        }
    }
}
