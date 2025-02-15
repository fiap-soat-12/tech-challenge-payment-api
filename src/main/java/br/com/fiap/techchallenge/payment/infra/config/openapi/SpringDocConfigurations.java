package br.com.fiap.techchallenge.payment.infra.config.openapi;

import br.com.fiap.techchallenge.payment.infra.entrypoint.controller.handler.ProblemDTO;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SpringDocConfigurations {

	@Value("${springdoc.api-docs.server.url:}")
	private String apiDocsUrl;

	@Bean
	public OpenAPI customOpenAPI() {
		OpenAPI openAPI = new OpenAPI()
			.info(new Info().title("Tech Challenge FIAP Payment API").version("v1").description("""
					Payment API Rest for Tech Challenge of Master's Degree in Software Architecture \n
					Developed by:\n
					 - Alexandre Miranda - RM357321\n
					 - Diego Ceccon - RM357437\n
					 - Jéssica Rodrigues - RM357218\n
					 - Rodrigo Sartori - RM358002\n
					 - Wilton Souza - RM357991\n
					""").contact(new Contact().name("SOAT 8 Group").email("soat-group@gmail.com")))
			.components(new Components().schemas(this.generateSchemas()));

		if (apiDocsUrl != null && !apiDocsUrl.isEmpty()) {
			openAPI.addServersItem(new Server().url(apiDocsUrl));
		}

		return openAPI;
	}

	@SuppressWarnings("rawtypes")
	private Map<String, Schema> generateSchemas() {
		final Map<String, Schema> schemaMap = new HashMap<>();

		Map<String, Schema> problemSchema = ModelConverters.getInstance().read(ProblemDTO.class);

		Schema errorsValidateDataArraySchema = new ArraySchema()
			.items(new Schema<>().$ref("#/components/schemas/ErrorsValidateData"));

		schemaMap.putAll(problemSchema);
		schemaMap.put("ErrorsValidateDataList", errorsValidateDataArraySchema);

		return schemaMap;
	}

}