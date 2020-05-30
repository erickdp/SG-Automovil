package domain;

import java.time.LocalDateTime;

public class Vehiculo {

    private int idVehiculo;
    private String propietario;
    private String placa;
    private String marca;
    private LocalDateTime fechaEntrada;
    private LocalDateTime fechaSalida;
    private double valorPagado;
    private boolean disponible;

    public Vehiculo() {
    }

    public Vehiculo(int idVehiculo, String propietario, String placa, String marca, LocalDateTime fechaEntrada, LocalDateTime fechaSalida, double valorPagado, boolean disponible) {
        this.idVehiculo = idVehiculo;
        this.propietario = propietario;
        this.placa = placa;
        this.marca = marca;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.valorPagado = valorPagado;
        this.disponible = disponible;
    }

    public Vehiculo(String propietario, String placa, String marca, LocalDateTime fechaEntrada, LocalDateTime fechaSalida, double valorPagado, boolean disponible) {
        this.propietario = propietario;
        this.placa = placa;
        this.marca = marca;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.valorPagado = valorPagado;
        this.disponible = disponible;
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

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
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

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return "Vehiculo{" + "idVehiculo=" + idVehiculo + ", propietario=" + propietario + ", placa=" + placa + ", marca=" + marca + ", fechaEntrada=" + fechaEntrada + ", fechaSalida=" + fechaSalida + ", valorPagado=" + valorPagado + ", disponible=" + disponible + '}';
    }

}
