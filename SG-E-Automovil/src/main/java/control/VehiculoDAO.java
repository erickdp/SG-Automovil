package control;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import data.*;
import domain.Vehiculo;
import java.io.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import util.Utileria;

public class VehiculoDAO implements CRUD<Vehiculo> {

    private static final String SQL_CREATE = "INSERT INTO vehiculo (propietario, placa, tipoVehiculo, "
            + "fechaEntrada, disponible) VALUES(?,?,?,?,?)";
    private static final String SQL_READ = "SELECT * FROM vehiculo WHERE fechaEntrada";
    private static final String SQL_UPDATE = "UPDATE FROM vehiculo propietario=?, placa=?, marca=?, fechaEntrada=?,"
            + " fechaSalida=?, valorPagado=?, disponible=? WHERE idVehiculo=?";
    private static final String SQL_DELETE = "DELETE FROM cliente WHERE placa=? AND disponible=1";
    private static final String SQL_SELECT_BY_ID = "SELECT fechaEntrada, tipo FROM vehiculo WHERE placa=? AND disponible=?";

    @Override
    public int create(Vehiculo miObjeto) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int row = 0;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try {
            conn = Conexion.getConexion();
            stmt = conn.prepareStatement(SQL_CREATE);
            stmt.setString(1, miObjeto.getPropietario());
            stmt.setString(2, miObjeto.getPlaca());
            stmt.setString(3, miObjeto.getTipoVehiculo());
            stmt.setString(4, miObjeto.getFechaEntrada().format(formato));
            stmt.setByte(5, miObjeto.getDisponible());
            row = stmt.executeUpdate();
            generarTicket(miObjeto);
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(conn);
            Conexion.close(stmt);
        }
        return row;
    }

    @Override
    public int update(Vehiculo miObjeto) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int row = 0;
        try {
            conn = Conexion.getConexion();
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, miObjeto.getPropietario());
            stmt.setString(2, miObjeto.getPlaca());
            stmt.setString(3, miObjeto.getTipoVehiculo());
//            stmt.setString(4, miObjeto.getFechaEntrada());
//            stmt.setString(5, miObjeto.getFechaSalida());
            stmt.setDouble(6, miObjeto.getValorPagado());
            stmt.setByte(7, miObjeto.getDisponible());
            stmt.setInt(8, miObjeto.getIdVehiculo());
            row = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(conn);
            Conexion.close(stmt);
        }
        return row;
    }

    @Override
    public int delete(Vehiculo miObjeto) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int row = 0;
        try {
            conn = Conexion.getConexion();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setString(1, miObjeto.getPlaca());
            row = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(conn);
            Conexion.close(stmt);
        }
        return row;
    }

    public Vehiculo readObjetById(Vehiculo miObjeto) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Vehiculo vehiculo = null;
        try {
            conn = Conexion.getConexion();
            stmt = conn.prepareStatement(SQL_SELECT_BY_ID);
            rs = stmt.executeQuery();
            stmt.setString(1, miObjeto.getPlaca());
            stmt.setBoolean(2, true);
            rs.first();
            String horaEntrada = rs.getString(1);

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            Date date = cal.getTime();
            String fechaActual = dateFormat.format(date);
            try {
                Date fechaEntrada = dateFormat.parse(horaEntrada);

            } catch (ParseException ex) {
                Logger.getLogger(VehiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {

        }
        return null;
    }

    public float pagoEstacionamiento(String placa) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        float valorPagar = 0f;
        LocalDateTime fechaSalida = LocalDateTime.now();
        try {
            conn = Conexion.getConexion();
            stmt = conn.prepareStatement("SELECT fechaEntrada, tipoVehiculo FROM vehiculo WHERE placa='" + placa + "' AND disponible=1",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery();
            rs.absolute(1);

            LocalDateTime fechaEntrada = LocalDateTime.parse(rs.getString(1).replace(" ", "T"));

            String tipo = rs.getString(2);

            int horasParqueo = fechaSalida.getHour() - fechaEntrada.getHour();

            if (horasParqueo != 0) {
                System.out.println("Hora de parqueo" + horasParqueo);
                valorPagar += horasParqueo;
            }

            valorPagar += (fechaSalida.getMinute() - fechaEntrada.getMinute()) / 60;

            if (tipo.equals("Auto")) {
                valorPagar += 0.5f;
            } else {
                valorPagar += 0.25f;
            }

            //Para los valores bit que son boolean no se usa '0' o '1' solo se lo pone directamente disponible=1
            stmt.executeUpdate("UPDATE vehiculo SET fechaSalida='" + fechaSalida + "', disponible=0, valorPagado='" + valorPagar + "' "
                    + "WHERE placa='" + placa + "' AND disponible=1");
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(conn);
            Conexion.close(stmt);
            Conexion.close(rs);
        }
        return valorPagar;
    }

    private void generarTicket(Vehiculo vehiculo) {
        Document documento = new Document();
        String ruta = System.getProperty("user.home");
        try {
            PdfWriter.getInstance(documento, new FileOutputStream(ruta + "/Desktop/Ticket_Ingreso.pdf"));
            documento.open();

            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

            PdfPTable tabla = new PdfPTable(3);
            tabla.addCell("Placa");
            tabla.addCell("Propietario");
            tabla.addCell("Hora Entrada");
            tabla.addCell(vehiculo.getPlaca());
            tabla.addCell(vehiculo.getPropietario());
            tabla.addCell(vehiculo.getFechaEntrada().format(formato));
            documento.add(tabla);

            System.out.println("TICKET Generado");
        } catch (FileNotFoundException | DocumentException ex) {
            Logger.getLogger(VehiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            documento.close();
            try {
                if ((new File(ruta + "/Desktop/Ticket_Ingreso.pdf").exists())) {
                    Process p;
                    p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + ruta + "/Desktop/Ticket_Ingreso.pdf");
                    p.waitFor(); //son ele minuscula en rund y en dll no son dos 11
                } else {
                    System.out.println("File is not exist");
                }
                System.out.println("Done");
            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace(System.out);
            }
        }
    }

    @Override
    public Set<Vehiculo> read(ButtonGroup grupoRD, JCheckBox[] grupoCB, String placa, String propietario, Date fechaEntrada) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Set<Vehiculo> vehiculos = null;
        String SQL_REQUEST = "SELECT propietario, placa, tipoVehiculo, fechaEntrada, fechaSalida, valorPagado, disponible FROM vehiculo WHERE ";

        byte disponible = Utileria.getDisponibilidad(grupoRD);
        String tipoVehiculo = Utileria.getTipoVehiculo(grupoCB);
        String fechaEntradaISO = Utileria.getFechaEntrada(fechaEntrada);

        switch (disponible) {
            case 0:
                SQL_REQUEST += "(CAST(fechaEntrada AS DATE)='" + fechaEntradaISO + "' "
                        + "AND disponible=" + disponible + ") "
                        + "AND (placa LIKE '%" + placa + "%' AND propietario LIKE '%" + propietario + "%' AND tipoVehiculo LIKE '%" + tipoVehiculo + "%')";
                try {
                    conn = Conexion.getConexion();
                    stmt = conn.prepareStatement(SQL_REQUEST);
                    rs = stmt.executeQuery();
                    vehiculos = new HashSet<>();
                    while (rs.next()) {
                        vehiculos.add(new Vehiculo(rs.getString(2), rs.getString(3), rs.getString(4),
                                LocalDateTime.parse(rs.getString(5).replace(" ", "T")),
                                LocalDateTime.parse(rs.getString(6).replace(" ", "T")), rs.getDouble(7), rs.getByte(8)));
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace(System.out);
                } finally {
                    Conexion.close(conn);
                    Conexion.close(rs);
                    Conexion.close(stmt);
                }
                break;

            case 1:
                SQL_REQUEST += "(CAST(fechaEntrada AS DATE)='" + fechaEntradaISO + "' "
                        + "AND disponible=" + disponible + ") "
                        + "AND (placa LIKE '%" + placa + "%' AND propietario LIKE '%" + propietario + "%' AND tipoVehiculo LIKE '%" + tipoVehiculo + "%')";
                try {
                    conn = Conexion.getConexion();
                    stmt = conn.prepareStatement(SQL_REQUEST);
                    rs = stmt.executeQuery();
                    vehiculos = new HashSet<>();
                    while (rs.next()) {
                        vehiculos.add(new Vehiculo(rs.getString(2), rs.getString(3), rs.getString(4),
                                LocalDateTime.parse(rs.getString(5).replace(" ", "T")), rs.getByte(8)));
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace(System.out);
                } finally {
                    Conexion.close(conn);
                    Conexion.close(rs);
                    Conexion.close(stmt);
                }
                break;
                
            default:
                System.out.println("No entro a la disponibilidad");
                break;
        }
        
        return vehiculos;
    }
}
