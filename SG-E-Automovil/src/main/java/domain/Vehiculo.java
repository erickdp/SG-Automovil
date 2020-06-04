package domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Vehiculo {

    private int idVehiculo;
    private String propietario;
    private String placa;
    private String tipoVehiculo;
    private LocalDateTime fechaEntrada;
    private LocalDateTime fechaSalida;
    private double valorPagado;
    private byte disponible;

    public Vehiculo() {
    }

    public Vehiculo(String propietario, String placa, String tipoVehiculo, LocalDateTime fechaEntrada, LocalDateTime fechaSalida, double valorPagado, byte disponible) {
        this.propietario = propietario;
        this.placa = placa;
        this.tipoVehiculo = tipoVehiculo;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.valorPagado = valorPagado;
        this.disponible = disponible;
    }

    public Vehiculo(String propietario, String placa, String tipoVehiculo, LocalDateTime fechaEntrada, byte disponible) {
        this.propietario = propietario;
        this.placa = placa;
        this.tipoVehiculo = tipoVehiculo;
        this.fechaEntrada = fechaEntrada;
        this.disponible = disponible;
        this.fechaSalida = null;
        this.valorPagado = 0d;
    }

    public Vehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public int getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public LocalDateTime getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(LocalDateTime fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public LocalDateTime getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDateTime fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public double getValorPagado() {
        return valorPagado;
    }

    public void setValorPagado(double valorPagado) {
        this.valorPagado = valorPagado;
    }

    public byte getDisponible() {
        return disponible;
    }

    public void setDisponible(byte disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        String cadena = "Vehiculo{" + "propietario=" + propietario + ", placa=" + placa
                + ", tipoVehiculo=" + tipoVehiculo + ", fechaEntrada=" + fechaEntrada.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + ", disponible=" + disponible + '}';
        if(this.fechaSalida == null) {
            cadena += " valor Pagado = 0 " + " fechaSalida = null"; 
        } else {
            cadena += "valor Pagado = " + valorPagado + " fechaSalida =" + fechaSalida.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        return cadena;
    }

}
