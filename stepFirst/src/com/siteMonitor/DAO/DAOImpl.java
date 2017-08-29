package com.siteMonitor.DAO;

import com.siteMonitor.Exceptions.FilterCreatingException;
import com.siteMonitor.Model.Ad;
import com.siteMonitor.Model.Filter;
import org.sqlite.SQLiteConfig;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by user on 09.05.2017.
 */
public class DAOImpl implements DAO {

    private String dbPath;

    public DAOImpl(String dbPath) {
        try {
            Class.forName("org.sqlite.JDBC");
            this.dbPath = dbPath;
        } catch (ClassNotFoundException ex) {
            System.out.print("JDBC CLASS NOT FOUND!!!! \n" + ex.getMessage());
        }
    }

    private Connection getConnection() throws SQLException {
        try {
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            return DriverManager.getConnection("jdbc:sqlite:" + dbPath, config.toProperties());
        } catch (SQLException ex) {
            System.out.println("GETTING CONNECTION ERROR");
            throw new SQLException("Driver can't get connection");
        }

    }

    @Override
    public Filter createFilter(String name, String link) throws FilterCreatingException {
        Connection connection;
        Statement statement;

        try {
            connection = getConnection();
            statement = connection.createStatement();

            String query = String.format("INSERT INTO filters VALUES (null, '%s', '%s');", name, link);
            statement.execute(query);

            query = "SELECT last_insert_rowId() FROM filters";
            ResultSet resultSet = statement.executeQuery(query);
            Filter result = new Filter(resultSet.getInt(1), name, link, new ArrayList<Ad>());

            resultSet.close();
            statement.close();
            connection.close();
            return result;
        } catch (SQLException ex) {
            throw new FilterCreatingException(ex.getMessage());
        }
    }

    @Override
    public boolean saveFilter(Filter filter) {
        Connection connection;
        Statement statement;

        try {
            connection = getConnection();
            statement = connection.createStatement();

            String query = String.format("UPDATE filters SET name = '%s', link = '%s' WHERE id = %d);", filter.getName(), filter.getLink(), filter.getId());
            statement.execute(query);

            statement.close();
            connection.close();

            if (filter.getAds().size() > 0)
                saveAdsFromFilter(filter);

            return true;
        } catch (SQLException ex) {
            System.out.println("create filter ERROR!!! \n" + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean saveAdsFromFilter(Filter filter) {
        Connection connection;
        Statement statement;

        try {
            connection = getConnection();
            statement = connection.createStatement();

            String query = "INSERT INTO ads VALUES ";
            for (Ad ad : filter.getAds()) {
                String values = String.format("\n(%d, '%s', %d, '%s', '%s', '%s', '%b', %d ),",
                        ad.getSiteId(), ad.getName(), ad.getPrice(), ad.getLink(), ad.getDescription(), ad.getDate(), ad.isViewed(), filter.getId());
                query += values;
            }
            query = query.substring(0, query.lastIndexOf(','));
            query += ';';
            //System.out.println(query);

            statement.execute(query);
            statement.close();
            connection.close();

            return true;
        } catch (SQLException ex) {
            System.out.println("add Ads ERROR!!! \n" + ex.getMessage());
            return false;
        }

    }

    @Override
    public ArrayList<Filter> getAllFilters() {
        Connection connection;
        Statement statement;

        try {
            connection = getConnection();
            statement = connection.createStatement();

            String query = "SELECT * FROM filters";
            ResultSet resultSet = statement.executeQuery(query);
            ArrayList<Filter> resutList = new ArrayList<>();

            while (resultSet.next()) {
                Filter f = new Filter(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("link"), getAdsByFilter(resultSet.getInt("id")));
                resutList.add(f);
            }

            resultSet.close();
            statement.close();
            connection.close();

            return resutList;
        } catch (SQLException ex) {
            System.out.println("getting filters list ERROR!!! \n" + ex.getMessage());
            return null;
        }
    }

    @Override
    public ArrayList<Ad> getAdsByFilter(int filterId) {
        Connection connection;
        Statement statement;

        try {
            connection = getConnection();
            statement = connection.createStatement();

            String query = "SELECT * FROM ads WHERE filter = " + filterId;
            ResultSet resultSet = statement.executeQuery(query);
            ArrayList<Ad> resutList = new ArrayList<>();

            while (resultSet.next()) {
                resutList.add(new Ad(resultSet.getInt("siteId"),
                        resultSet.getString("name"),
                        resultSet.getInt("price"),
                        resultSet.getString("link"),
                        resultSet.getString("description"),
                        resultSet.getString("date"),
                        resultSet.getBoolean("isViewed")));
            }

            resultSet.close();
            statement.close();
            connection.close();

            return resutList;
        } catch (SQLException ex) {
            System.out.println("getting filters list ERROR!!! \n" + ex.getMessage());
            return null;
        }
    }

    @Override
    public boolean setAdAsViewed(Ad ad, int filterId) {
        Connection connection;
        Statement statement;

        try {
            connection = getConnection();
            statement = connection.createStatement();

            String query = "UPDATE ads SET isViewed = 'true' WHERE siteId = " + ad.getSiteId() + " AND filter = " + filterId + ";";
            statement.execute(query);

            statement.close();
            connection.close();

            return true;
        } catch (SQLException ex) {
            System.out.println("setting not viewed ERROR!!! \n" + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean setAdsAsViewed(ArrayList<Ad> ads, int filterId) {
        Connection connection;
        Statement statement;

        try {
            if (!ads.isEmpty()) {
                connection = getConnection();
                statement = connection.createStatement();

                String query = "UPDATE ads SET isViewed = 'true' WHERE siteId IN (";
                for (Ad ad : ads) {
                    query += ad.getSiteId() + ",";
                }
                query = query.substring(0, query.lastIndexOf(','));
                query += ")" + " AND filter = " + filterId + ";";
                System.out.println(query);


                statement.execute(query);

                statement.close();
                connection.close();
            }
            return true;
        } catch (SQLException ex) {
            System.out.println("setting viewed ERROR!!! \n" + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean setAdAsNotViewed(Ad ad, int filterId) {
        Connection connection;
        Statement statement;

        try {
            connection = getConnection();
            statement = connection.createStatement();

            String query = "UPDATE ads SET isViewed = 'false' WHERE siteId = " + ad.getSiteId()+ " AND filter = " + filterId + ";";;
            statement.execute(query);

            statement.close();
            connection.close();

            return true;
        } catch (SQLException ex) {
            System.out.println("setting not viewed ERROR!!! \n" + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean setAdsAsNotViewed(ArrayList<Ad> ads, int filterId) {
        Connection connection;
        Statement statement;

        try {
            connection = getConnection();
            statement = connection.createStatement();

            String query ="UPDATE ads SET isViewed = 'false' WHERE siteId IN (";

            for (Ad ad : ads) {
                query += ad.getSiteId() + ",";
            }
            query = query.substring(0, query.lastIndexOf(','));
            query += ")" + " AND filter = " + filterId + ";";

            statement.execute(query);

            statement.close();
            connection.close();

            return true;
        } catch (SQLException ex) {
            System.out.println("setting not viewed ERROR!!! \n" + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean setAdsAsViewedByFilter(Filter filter) {
        Connection connection;
        Statement statement;

        try {
            connection = getConnection();
            statement = connection.createStatement();

            String query ="UPDATE ads SET isViewed = 'true' WHERE filter = " + filter.getId() + ";";

            statement.execute(query);

            statement.close();
            connection.close();

            return true;
        } catch (SQLException ex) {
            System.out.println("setting not viewed ERROR!!! \n" + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean setAdsAsNotViewedByFilter(Filter filter) {
        Connection connection;
        Statement statement;

        try {
            connection = getConnection();
            statement = connection.createStatement();

            String query ="UPDATE ads SET isViewed = 'false' WHERE filter = " + filter.getId() + ";";

            statement.execute(query);

            statement.close();
            connection.close();

            return true;
        } catch (SQLException ex) {
            System.out.println("setting not viewed ERROR!!! \n" + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteFilter(Filter filter) {
        Connection connection;
        Statement statement;

        try {
            connection = getConnection();
            statement = connection.createStatement();

            String query = "DELETE FROM filters WHERE id = " + filter.getId() + ";";
            System.out.println(query);
            statement.execute(query);
            statement.close();
            connection.close();
            return true;
        } catch (SQLException ex) {
            System.out.println("delete filter ERROR!!! \n" + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteAdById(Ad ad) {
        Connection connection;
        Statement statement;

        try {
            connection = getConnection();
            statement = connection.createStatement();

            String query = "DELETE FROM ads WHERE siteId = " + ad.getSiteId() + ";";
            System.out.println(query);
            statement.execute(query);
            statement.close();
            connection.close();
            return true;
        } catch (SQLException ex) {
            System.out.println("delete filter ERROR!!! \n" + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteAds(ArrayList<Ad> ads) {
        Connection connection;
        Statement statement;

        try {
            if (!ads.isEmpty()) {
                connection = getConnection();
                statement = connection.createStatement();

                String query = "DELETE FROM ads WHERE siteId IN (";
                for (Ad ad : ads) {
                    query += ad.getSiteId() + ", ";
                }
                query = query.substring(0, query.lastIndexOf(", "));
                query += ");";
                //System.out.println(query);
                statement.execute(query);

                statement.close();
                connection.close();
            }
            return true;
        } catch (SQLException ex) {
            System.out.println("delete filter ERROR!!! \n" + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteAdsByFilter(Filter filter) {
        Connection connection;
        Statement statement;

        try {
            if (!filter.getAds().isEmpty()) {
                connection = getConnection();
                statement = connection.createStatement();

                String query = "DELETE FROM ads WHERE filter = " + filter.getId() + ";";

                System.out.println(query);
                statement.execute(query);

                statement.close();
                connection.close();
            }
            return true;
        } catch (SQLException ex) {
            System.out.println("delete filter ERROR!!! \n" + ex.getMessage());
            return false;
        }
    }
}
