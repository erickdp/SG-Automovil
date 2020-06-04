
import data.Conexion;
import domain.Vehiculo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JRadioButton;

public class NewMain {

    public static void main(String[] args) {
//        testConexion(new Vehiculo("Mauto", "AS-34", "Auto", LocalDateTime.now(), (byte) 1));
//        testConexion();
//        testSeleccion();
//        VehiculoDAO a = new VehiculoDAO();
        testSeleccion();
    }

    public static void elementos(Enumeration a) {
        while (a.hasMoreElements()) {
            JRadioButton nextElement = (JRadioButton) a.nextElement();
            if (nextElement.isSelected()) {
                System.out.println("Seleccionado " + nextElement.getText());
            } else {
                System.out.println(nextElement.getText());
            }
        }
    }

    public static void testSeleccion() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Vehiculo n = null;
        String fechaEntrada;
        String fechaSalida;
        Set<Vehiculo> vehiculos = new HashSet<>();
        try {
            conn = Conexion.getConexion();
//            stmt = conn.prepareStatement("SELECT * FROM vehiculo WHERE fechaEntrada LIKE '%%' AND propietario LIKE '%Erick%'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt = conn.prepareStatement("SELECT * FROM vehiculo");
            rs = stmt.executeQuery();
            while (rs.next()) {
                fechaSalida = rs.getString(6);
                if (fechaSalida == null) {
                    n = new Vehiculo(rs.getString(2), rs.getString(3),
                            rs.getString(4), LocalDateTime.parse(rs.getString(5).replace(" ", "T")), rs.getByte(8));
                } else {
                    n = new Vehiculo(rs.getString(2), rs.getString(3),
                            rs.getString(4), LocalDateTime.parse(rs.getString(5).replace(" ", "T")),
                            LocalDateTime.parse(fechaSalida.replace(" ", "T")), rs.getDouble(7), rs.getByte(8));
                }
                vehiculos.add(n);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewMain.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Conexion.close(conn);
            Conexion.close(stmt);
            Conexion.close(rs);
            for (Vehiculo vehiculo : vehiculos) {
                System.out.println(vehiculo);
            }
        }
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
            stmt = conn.prepareStatement("SELECT * FROM vehiculo WHERE placa='AS-34' AND disponible=0", ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery();
            rs.absolute(1);

            n = new Vehiculo(rs.getString(2), rs.getString(3), rs.getString(4), LocalDateTime.parse(rs.getString(5).replace(" ", "T")),
                    rs.getByte(8));

//            stmt = conn.prepareStatement("UPDATE vehiculo SET fechaSalida='" + LocalDateTime.now() + "', valorPagado='2.3', disponible=1 WHERE placa='AS-34' AND disponible=0");
//            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            System.out.println(n);
        }

    }

    public static void testConexion(Vehiculo vehiculo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        String SQL_CREATE = "INSERT INTO vehiculo (propietario, placa, tipoVehiculo, fechaEntrada, disponible) VALUES(?,?,?,?,?)";
        try {
            conn = Conexion.getConexion();
            stmt = conn.prepareStatement(SQL_CREATE);
            stmt.setString(1, vehiculo.getPropietario());
            stmt.setString(2, vehiculo.getPlaca());
            stmt.setString(3, vehiculo.getTipoVehiculo());
            stmt.setString(4, vehiculo.getFechaEntrada().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            stmt.setByte(5, vehiculo.getDisponible());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(conn);
            Conexion.close(stmt);
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
