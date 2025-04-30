# Servicio REST de Empleados

 Esta es una aplicación Spring Boot que proporciona una API REST para gestionar datos de empleados. También interactúa con un servicio web SOAP para la persistencia de datos.

 ## Características

 * Proporciona una API REST para crear empleados.
 * Valida los datos de los empleados.
 * Utiliza un servicio web SOAP para almacenar la información de los empleados en una base de datos MySQL.
 * Devuelve los detalles del empleado incluyendo la edad y el tiempo de vinculación.

 ## Primeros Pasos

 ### Requisitos Previos

 * Java 17 o superior
 * Maven o Gradle
 * Docker (opcional, para la contenerización)

 ### Ejecutar la Aplicación

 **1. Sin Docker (Localmente)**

 * **Clonar el repositorio:**

     ```bash
     git clone [https://github.com/SantiagoRojasBuitrago/JavaDeveloperParameta](https://github.com/SantiagoRojasBuitrago/JavaDeveloperParameta)
     cd JavaDeveloperParameta
     ```

 * **Configurar la conexión a la base de datos:**

     * Edita `src/main/resources/application.properties` o `application.yml` para proporcionar la URL correcta de la base de datos, el nombre de usuario y la contraseña para tu instancia local de MySQL.

 * **Compilar y ejecutar la aplicación:**

     * **Maven:**

         ```bash
         mvn spring-boot:run
         ```

     * **Gradle:**

         ```bash
         gradle bootRun
         ```

 * La aplicación se iniciará, normalmente en el puerto 8080.

 **2. Con Docker**

 * **Asegúrate de que Docker y Docker Compose estén instalados.**

 * **Compilar la aplicación (si es necesario):**

     * Maven:

         ```bash
         mvn clean install
         ```

     * Gradle:

         ```bash
         gradle build
         ```

 * **Ejecutar Docker Compose:**

     * Navega al directorio raíz del proyecto (donde se encuentra el archivo `docker-compose.yml`).

     * Puedes editar los datos de la base de datos (nombre, usuario, contraseña) en el archivo `docker-compose.yml` antes de ejecutar este comando.

     * Ejecuta:

         ```bash
         docker-compose up -d
         ```

     * Esto compilará la imagen de Docker (si es necesario), creará los contenedores necesarios (aplicación y MySQL) y los iniciará.

 ## Documentación de la API

 La documentación de la API REST está disponible en:

 * `http://localhost:8080/swagger-ui/index.html`

 o, para versiones anteriores (Spring Boot 2.x con Springfox):

 * `http://localhost:8080/swagger-ui.html`

 ## Uso de la API

 (Proporciona ejemplos de cómo usar los endpoints de la API, si es relevante. Por ejemplo:)

 ### Crear un Empleado

 * **Endpoint:** `GET http://localhost:8080/empleado`
 * **Parámetros:**
    * `nombres` (String, obligatorio): Nombre del empleado.
    * `apellidos` (String, obligatorio): Apellidos del empleado.
    * `tipoDocumento` (String, obligatorio): Tipo de documento del empleado.
    * `numeroDocumento` (String, obligatorio): Número de documento del empleado.
    * `fechaNacimiento` (String, obligatorio): Fecha de nacimiento del empleado (formato: AAAA-MM-DD).
    * `fechaVinculacion` (String, obligatorio): Fecha de vinculación del empleado (formato: AAAA-MM-DD).
    * `cargo` (String, obligatorio): Cargo del empleado.
    * `salario` (Double, obligatorio): Salario del empleado.
 * **Ejemplo:**

    ```
    GET http://localhost:8080/empleado?nombres=Juan&apellidos=Pérez&tipoDocumento=CC&numeroDocumento=1234567891011&fechaNacimiento=1990-13-20&fechaVinculacion=2020-05-10&cargo=Desarrollador%20Prueba&salario=5000000
    ```

 ### Invocar el Servicio SOAP

 * El servicio SOAP para almacenar la información del empleado se invoca mediante un método POST a la siguiente URL:

    `http://localhost:8080/ws/empleados`

 * El cuerpo de la solicitud debe ser un XML SOAP con la siguiente estructura:

    ```xml
    <soapenv:Envelope xmlns:soapenv="[http://schemas.xmlsoap.org/soap/envelope/](http://schemas.xmlsoap.org/soap/envelope/)"
                      xmlns:emp="[http://empleado.parameta.com/](http://empleado.parameta.com/)">
        <soapenv:Header/>
        <soapenv:Body>
            <almacenarEmpleadoRequest xmlns="[http://empleado.parameta.com/](http://empleado.parameta.com/)">
                <empleado>
                    <id>22</id>
                    <nombres>Nombres</nombres>
                    <apellidos>Apellidos</apellidos>
                    <tipoDocumento>CC</tipoDocumento>
                    <numeroDocumento>99999</numeroDocumento>
                    <fechaNacimiento>1990-01-15</fechaNacimiento>
                    <fechaVinculacion>2020-05-10</fechaVinculacion>
                    <cargo>Cargo</cargo>
                    <salario>5000000.0</salario>
                </empleado>
            </almacenarEmpleadoRequest>
        </soapenv:Body>
    </soapenv:Envelope>
    ```

 * La solicitud SOAP debe incluir la cabecera `Content-Type` con el valor `text/xml;charset=UTF-8`.

 * **Ejemplo de cabecera:**

    ```json
    {
      "Content-Type": "text/xml;charset=UTF-8"
    }
    ```

 ## Tecnologías Utilizadas

 * Java
 * Spring Boot
 * Spring WS (para SOAP)
 * MySQL
 * Maven / Gradle (para la construcción)
 * Docker (opcional, para la contenerización)
 * Swagger / Springdoc (para la documentación)

 ## Notas
