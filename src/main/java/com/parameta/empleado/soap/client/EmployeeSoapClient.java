package com.parameta.empleado.soap.client;

import com.parameta.empleado.soap.types.AlmacenarEmpleadoRequest;
import com.parameta.empleado.soap.types.AlmacenarEmpleadoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

@Service
public class EmployeeSoapClient {

    private WebServiceTemplate webServiceTemplate;

    @Autowired
    public EmployeeSoapClient(Jaxb2Marshaller marshaller) {
        webServiceTemplate = new WebServiceTemplate(marshaller);
    }

    public AlmacenarEmpleadoResponse almacenarEmpleado(AlmacenarEmpleadoRequest request) {
        String url = "http://localhost:8080/ws/empleado";
        return (AlmacenarEmpleadoResponse) webServiceTemplate.marshalSendAndReceive(url, request);
    }
}