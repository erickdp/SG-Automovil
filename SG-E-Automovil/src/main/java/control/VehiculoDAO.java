package control;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import data.CRUD;
import data.Conexion;
import domain.Vehiculo;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VehiculoDAO implements CRUD<Vehiculo> {

    private static final String SQL_CREATE = "INSERT INTO vehiculo (propietario, placa, marca, "
            + "fechaEntrada, fechaSalida, valorPagado, disponible) VALUES(?,?,?,?,?,?,?)";
    private static final String SQL_READ = "SELECT idVehiculo, propietario, placa, marca, fechaEntrada, "
            + "fechaSalida, valorPagado, disponible FROM vehiculo";
    private static final String SQL_UPDATE = "UPDATE FROM vehiculo propietario=?, placa=?, marca=?, fechaEntrada=?,"
            + " fechaSalida=?, valorPagado=?, disponible=? WHERE idVehiculo=?";
    private static final String SQL_DELETE = "DELETE FROM cliente WHERE idVehiculo=?";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM vehiculo WHERE placa=? and disponoble=?";

    @Override
    public int create(Vehiculo miObjeto) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int row = 0;
        try {
            conn = Conexion.getConexion();
            stmt = conn.prepareStatement(SQL_CREATE);
            stmt.setString(1, miObjeto.getPropietario());
            stmt.setString(2, miObjeto.getPlaca());
            stmt.setString(3, miObjeto.getMarca());
            stmt.setString(4, miObjeto.getFechaEntrada());
            stmt.setString(5, miObjeto.getFechaSalida());
            stmt.setDouble(6, miObjeto.getValorPagado());
            stmt.setBoolean(7, miObjeto.isDisponible());
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
    public List<Vehiculo> read() {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        List<Vehiculo> vehiculos = null;
        try {
            vehiculos = new ArrayList<>();
            conn = Conexion.getConexion();
            stmt = conn.prepareStatement(SQL_READ);
            rs = stmt.executeQuery();
            while (rs.next()) {
                vehiculos.add(new Vehiculo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getDouble(7), rs.getBoolean(8)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(conn);
            Conexion.close(rs);
            Conexion.close(stmt);
        }
        return vehiculos;
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
            stmt.setString(3, miObjeto.getMarca());
            stmt.setString(4, miObjeto.getFechaEntrada());
            stmt.setString(5, miObjeto.getFechaSalida());
            stmt.setDouble(6, miObjeto.getValorPagado());
            stmt.setBoolean(7, miObjeto.isDisponible());
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
            stmt.setInt(1, miObjeto.getIdVehiculo());
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
    public Vehiculo readObjetById(Vehiculo miObjeto) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Vehiculo vehiculo = null;
        try {
            conn = Conexion.getConexion();
            stmt = conn.prepareStatement(SQL_SELECT_BY_ID);
            rs = stmt.executeQuery();
            rs.first();
            vehiculo = new Vehiculo
        } catch (SQLException ex) {
            
        }
        
        
        
        
        
        
        return null;
    }
    
    public double pagoEstacionamiento() {
        
    }

    private void generarTicket(Vehiculo vehiculo) {
        Document documento = new Document();
        String ruta = System.getProperty("user.home");
        try {
            PdfWriter.getInstance(documento, new FileOutputStream(ruta + "/Desktop/Ticket_Ingreso.pdf"));
            documento.open();

            PdfPTable tabla = new PdfPTable(3);
            tabla.addCell("Placa");
            tabla.addCell("Propietario");
            tabla.addCell("Hora Entrada");
            tabla.addCell(vehiculo.getPlaca());
            tabla.addCell(vehiculo.getPropietario());
            tabla.addCell(vehiculo.getFechaEntrada());
            documento.close();

            System.out.println("TICKET Generado");
        } catch (FileNotFoundException | DocumentException ex) {
            Logger.getLogger(VehiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if ((new File(ruta + "/Desktop/Ticket_Ingreso.pdf").exists())) {
                    Process p;
                    p = Runtime.getRuntime().exec("rund1132 url.d11.FileProtocolHandler " + ruta + "/Desktop/Ticket_Ingreso.pdf");
                    p.waitFor();
                } else {
                    System.out.println("File is not exist");
                }
                System.out.println("Done");
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(VehiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}