package data;

import java.sql.*;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;

public class Conexion {

    private static final String SQL_URL = "jdbc:mysql://localhost:3306/control_vehiculos?useSSL=false&useTimezone=false&serverTimeZone=UTC&allowPublicKeyRetrieval=true";
    private static final String SQL_USER = "root";
    private static final String SQL_PASSWORD = "admin";

    private static BasicDataSource bs; //Objeto de pool de conexiones

    private static DataSource getDataSource() {
        if (bs == null) {
            bs = new BasicDataSource();
            bs.setUrl(SQL_URL);
            bs.setUsername(SQL_USER);
            bs.setPassword(SQL_PASSWORD);
            bs.setInitialSize(10);
        }
        return bs;
    }

    public static Connection getConexion() throws SQLException {
        return getDataSource().getConnection();
    }

    public static void close(Connection conn) {
        try {
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public static void close(ResultSet rs) {
        try {
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public static void close(PreparedStatement stmt) {
        try {
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }

}
