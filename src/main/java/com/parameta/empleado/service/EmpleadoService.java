package com.parameta.empleado.service;

import com.parameta.empleado.model.Empleado;
import com.parameta.empleado.repository.EmpleadoRepository;
import com.parameta.empleado.response.EmpleadoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

@Service
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    @Autowired
    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    public EmpleadoResponse procesarEmpleado(Empleado empleado) {
        validarEmpleado(empleado);

        empleadoRepository.save(empleado);
        System.out.println("Service: Almacenando empleado (vía Service): " + empleado.getNombres());

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaVinculacion = null;
        Date fechaNacimiento = null;

        try {
            fechaVinculacion = formato.parse(empleado.getFechaVinculacion());
            fechaNacimiento = formato.parse(empleado.getFechaNacimiento());

        } catch (ParseException e) {
            System.out.println("Error al parsear la fecha: " + e.getMessage());
        }

        Period tiempoVinculacion = calcularTiempoTranscurrido(fechaVinculacion);
        Period edad = calcularEdad(fechaNacimiento);

        EmpleadoResponse response = new EmpleadoResponse();
        response.setEmpleado(empleado);
        response.setTiempoVinculacion(String.format("%d años, %d meses, %d días",
                tiempoVinculacion.getYears(), tiempoVinculacion.getMonths(), tiempoVinculacion.getDays()));
        response.setEdad(String.format("%d años, %d meses, %d días",
                edad.getYears(), edad.getMonths(), edad.getDays()));

        return response;
    }

    private void validarEmpleado(Empleado empleado) {
        if (empleado.getNombres() == null || empleado.getNombres().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (empleado.getApellidos() == null || empleado.getApellidos().isEmpty()) {
            throw new IllegalArgumentException("Los apellidos no pueden estar vacíos.");
        }
        if (empleado.getTipoDocumento() == null || empleado.getTipoDocumento().isEmpty()) {
            throw new IllegalArgumentException("El tipo de documento no puede estar vacío.");
        }
        if (empleado.getNumeroDocumento() == null || empleado.getNumeroDocumento().isEmpty()) {
            throw new IllegalArgumentException("El número de documento no puede estar vacío.");
        }
        if (empleado.getFechaNacimiento() == null) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede estar vacía.");
        }
        if (empleado.getFechaVinculacion() == null) {
            throw new IllegalArgumentException("La fecha de vinculación no puede estar vacía.");
        }
        if (empleado.getCargo() == null || empleado.getCargo().isEmpty()) {
            throw new IllegalArgumentException("El cargo no puede estar vacío.");
        }
        if (empleado.getSalario() == null) {
            throw new IllegalArgumentException("El salario no puede estar vacío.");
        }

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaNacimiento = null;

        try {
            fechaNacimiento = formato.parse(empleado.getFechaNacimiento());
        } catch (ParseException e) {
            System.out.println("Error al parsear la fecha: " + e.getMessage());
        }

        LocalDate fechaNacimientoLocal = fechaNacimiento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate ahora = LocalDate.now();
        if (Period.between(fechaNacimientoLocal, ahora).getYears() < 18) {
            throw new IllegalArgumentException("El empleado debe ser mayor de edad.");
        }
    }

    private Period calcularTiempoTranscurrido(Date fecha) {
        LocalDate fechaLocal = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate ahora = LocalDate.now();
        return Period.between(fechaLocal, ahora);
    }

    private Period calcularEdad(Date fechaNacimiento) {
        LocalDate fechaNacimientoLocal = fechaNacimiento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate ahora = LocalDate.now();
        return Period.between(fechaNacimientoLocal, ahora);
    }
}