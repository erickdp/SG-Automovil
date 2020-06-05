package util;

import domain.Vehiculo;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Enumeration;
import java.util.Set;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.table.DefaultTableModel;

public class Utileria {

    public final static byte getDisponibilidad(ButtonGroup grupo) {
        Enumeration grupoDisponible = grupo.getElements();
        byte disponible = 0;
        while (grupoDisponible.hasMoreElements()) {
            JRadioButton nextElement = (JRadioButton) grupoDisponible.nextElement();
            if (nextElement.isSelected()) {
                if (nextElement.getText().equalsIgnoreCase("DISPONIBLE")) {
                    disponible = 1;
                    break;
                }
            }
        }
        return disponible;
    }

    public final static String getTipoVehiculo(ButtonGroup grupo) {
        Enumeration grupoDisponible = grupo.getElements();
        while (grupoDisponible.hasMoreElements()) {
            JRadioButton nextElement = (JRadioButton) grupoDisponible.nextElement();
            if (nextElement.isSelected()) {
                if (nextElement.getText().equalsIgnoreCase("AUTO")) {
                    return "Auto";
                }
            }
        }
        return "Motocicleta";
    }

    public final static String getTipoVehiculo(JCheckBox[] grupo) {
        for (JCheckBox cb : grupo) {
            if (cb.isSelected()) {
                return cb.getText();
            }
        }
        return "";
    }

    public static final String getFechaEntrada(Date fecha) {
        DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        return formato.format(fecha);
    }

    public static final DefaultTableModel cargarTabla(Set<Vehiculo> vehiculos) {
        String salida;
        double pago;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Object[] columna = {"Propietario", "Placa", "Tipo", "Fecha Ingreso", "Fecha Retiro", "Valor Pago", "Disponible"};
        DefaultTableModel modelo = new DefaultTableModel(columna, 0);
        for (Vehiculo v : vehiculos) {
            String propietario = v.getPropietario();
            String placa = v.getPlaca();
            String tipoVehiculo = v.getTipoVehiculo();
            String fechaIngreso = v.getFechaEntrada().format(formato);
            LocalDateTime fechaSalida = v.getFechaSalida();
            if (fechaSalida == null) {
                salida = "N/A";
                pago = 0f;
            } else {
                salida = fechaSalida.format(formato);
                pago = v.getValorPagado();
            }
            String disponible = v.getDisponible() == 1 ? "Si" : "No";
            Object[] fila = {propietario, placa, tipoVehiculo, fechaIngreso, salida, pago, disponible};
            modelo.addRow(fila);
        }
        return modelo;
    }

}
