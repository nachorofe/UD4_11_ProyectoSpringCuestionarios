package org.dam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

/**
 * Clase principal que inicia la aplicación Spring Boot para el sistema de gestión de preguntas.
 * Esta clase configura los componentes esenciales de la aplicación y habilita soporte
 * para integración entre Spring Data y Spring Web.
 */
@SpringBootApplication // Anotación compuesta que incluye:
// - @Configuration: Marca la clase como fuente de definiciones de beans
// - @EnableAutoConfiguration: Habilita la configuración automática de Spring Boot
// - @ComponentScan: Habilita el escaneo de componentes en el paquete actual y subpaquetes
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO) // Habilita soporte para integración entre Spring Data y Spring Web
// VIA_DTO indica que la paginación se serializará mediante DTOs
public class AppPreguntasApplication {

	/**
	 * Método principal que inicia la aplicación Spring Boot.
	 *
	 * @param args Argumentos de línea de comandos (opcionales)
	 */
	public static void main(String[] args) {
		SpringApplication.run(AppPreguntasApplication.class, args);
	}

}
