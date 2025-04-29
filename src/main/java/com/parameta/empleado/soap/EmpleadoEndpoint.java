package com.parameta.empleado.soap;

import com.parameta.empleado.model.Empleado;
import com.parameta.empleado.repository.EmpleadoRepository;
import com.parameta.empleado.soap.types.AlmacenarEmpleadoRequest;
import com.parameta.empleado.soap.types.AlmacenarEmpleadoResponse;
import com.parameta.empleado.soap.types.EmpleadoType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@Component
public class EmpleadoEndpoint {

    private static final String NAMESPACE_URI = "http://empleado.parameta.com/";

    private final EmpleadoRepository empleadoRepository;

    @Autowired
    public EmpleadoEndpoint(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "almacenarEmpleadoRequest")
    @ResponsePayload
    public AlmacenarEmpleadoResponse almacenarEmpleado(@RequestPayload AlmacenarEmpleadoRequest request) {
        EmpleadoType empleadoType = request.getEmpleado(); // Obtén EmpleadoType

        if (empleadoType != null) {
            System.out.println("Nombre del empleado (desde EmpleadoType): " + empleadoType.getNombres());
            System.out.println("ID del empleado (desde EmpleadoType): " + empleadoType.getId());

            Empleado empleado = new Empleado();
            empleado.setId(empleadoType.getId());
            empleado.setNombres(empleadoType.getNombres());
            empleado.setApellidos(empleadoType.getApellidos());
            empleado.setTipoDocumento(empleadoType.getTipoDocumento());
            empleado.setNumeroDocumento(empleadoType.getNumeroDocumento());
            empleado.setFechaNacimiento(empleadoType.getFechaNacimiento());
            empleado.setFechaVinculacion(empleadoType.getFechaVinculacion());
            empleado.setCargo(empleadoType.getCargo());
            empleado.setSalario(empleadoType.getSalario());

            empleadoRepository.save(empleado);

            AlmacenarEmpleadoResponse response = new AlmacenarEmpleadoResponse();
            response.setResultado("Empleado almacenado exitosamente (vía Spring WS)");
            return response;
        } else {
            System.out.println("El objeto empleado dentro del request es null.");
            AlmacenarEmpleadoResponse response = new AlmacenarEmpleadoResponse();
            response.setResultado("Error al recibir los datos del empleado.");
            return response;
        }
    }
}