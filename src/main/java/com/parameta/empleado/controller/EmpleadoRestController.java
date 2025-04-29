package com.parameta.empleado.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parameta.empleado.model.Empleado;
import com.parameta.empleado.response.EmpleadoResponse;
import com.parameta.empleado.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class EmpleadoRestController {

    private final EmpleadoService empleadoService;
    private final ObjectMapper objectMapper;

    @Autowired
    public EmpleadoRestController(EmpleadoService empleadoService, ObjectMapper objectMapper) {
        this.empleadoService = empleadoService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/empleado")
    public ResponseEntity<String> crearEmpleado(
            @RequestParam(value = "nombres") String nombres,
            @RequestParam(value = "apellidos") String apellidos,
            @RequestParam(value = "tipoDocumento") String tipoDocumento,
            @RequestParam(value = "numeroDocumento") String numeroDocumento,
            @RequestParam(value = "fechaNacimiento") String fechaNacimientoStr,
            @RequestParam(value = "fechaVinculacion") String fechaVinculacionStr,
            @RequestParam(value = "cargo") String cargo,
            @RequestParam(value = "salario") Double salario) {

        try {
            Empleado empleado = new Empleado();
            empleado.setNombres(nombres);
            empleado.setApellidos(apellidos);
            empleado.setTipoDocumento(tipoDocumento);
            empleado.setNumeroDocumento(numeroDocumento);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            empleado.setFechaNacimiento(fechaNacimientoStr);
            empleado.setFechaVinculacion(fechaVinculacionStr);


            empleado.setCargo(cargo);
            empleado.setSalario(salario);

            EmpleadoResponse response = empleadoService.procesarEmpleado(empleado);
            String jsonResponse = objectMapper.writeValueAsString(response);
            return ResponseEntity.ok(jsonResponse);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor.");
        }
    }
}