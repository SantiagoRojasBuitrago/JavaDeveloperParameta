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
import java.text.SimpleDateFormat;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


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
    @Operation(summary = "Crea un nuevo empleado", description = "Este método recibe los datos de un empleado, los valida, y los guarda en la base de datos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleado creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error en la solicitud: datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<String> crearEmpleado(
            @Parameter(description = "Nombre del empleado", required = true) @RequestParam(value = "nombres") String nombres,
            @Parameter(description = "Apellidos del empleado", required = true) @RequestParam(value = "apellidos") String apellidos,
            @Parameter(description = "Tipo de documento del empleado", required = true) @RequestParam(value = "tipoDocumento") String tipoDocumento,
            @Parameter(description = "Número de documento del empleado", required = true) @RequestParam(value = "numeroDocumento") String numeroDocumento,
            @Parameter(description = "Fecha de nacimiento del empleado (yyyy-MM-dd)", required = true) @RequestParam(value = "fechaNacimiento") String fechaNacimientoStr,
            @Parameter(description = "Fecha de vinculación del empleado (yyyy-MM-dd)", required = true) @RequestParam(value = "fechaVinculacion") String fechaVinculacionStr,
            @Parameter(description = "Cargo del empleado", required = true) @RequestParam(value = "cargo") String cargo,
            @Parameter(description = "Salario del empleado", required = true) @RequestParam(value = "salario") Double salario) {


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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e);
        }
    }
}