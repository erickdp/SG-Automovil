
import data.Conexion;
import domain.Vehiculo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NewMain {

    public static void main(String[] args) {
        usoFecha();
    }

    public static void testConexion() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Vehiculo n = null;
        String fechaEntrada;
        String fechaSalida;
        try {
            conn = Conexion.getConexion();
            stmt = conn.prepareStatement("SELECT * FROM vehiculo WHERE idVehiculo=?");
            stmt.setInt(1, 1);
            rs = stmt.executeQuery();
            while (rs.next()) {
                n = new Vehiculo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), LocalDateTime.parse(rs.getString(5).replace(" ", "T")),
                        LocalDateTime.parse(rs.getString(6).replace(" ", "T")), rs.getDouble(7), rs.getByte(8));
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewMain.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            System.out.println(n);
        }

    }

    public static void usoFecha() {
        DateTimeFormatter formato = DateTimeFormatter.ISO_DATE_TIME;

        LocalDateTime fechaEntrada = LocalDateTime.parse("2020-06-01T21:10:00");
        LocalDateTime fechaSalida = LocalDateTime.now();

        System.out.println("fechaSalida = " + fechaSalida.format(formato));
        System.out.println("fechaEntrada = " + fechaEntrada.format(formato));

        int diasEstacionado = fechaSalida.getDayOfYear() - fechaEntrada.getDayOfYear();
        int minutosEstacionado = fechaSalida.getMinute() - fechaEntrada.getMinute();
        int horasEstacionado = fechaSalida.getHour() - fechaEntrada.getHour();

        if (diasEstacionado != 0) {
            System.out.println("A estado estacionado durante: " + diasEstacionado + " dias");
        } else {
            System.out.println("minutosEstacionado = " + minutosEstacionado);
        }
    }

}
