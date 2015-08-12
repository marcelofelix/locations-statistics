package com.vivareal.locations;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.vivareal.locations.statistic.repository.ReportRepository;
import com.vivareal.locations.statistic.service.DataExtractorFactory;

@SpringBootApplication
@Configuration
@EnableMongoRepositories(basePackageClasses = ReportRepository.class)
@EnableScheduling
public class Application extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		new SpringApplicationBuilder()
				.sources(Application.class)
				.profiles("dev")
				.run(args);
	}

	@Bean
	public ReportsFactory reports() {
		return new ReportsFactory();
	}

	@Bean
	public DataExtractorFactory dataExtractorFactory() {
		return new DataExtractorFactory();
	}

	@Bean
	@Qualifier("sqlInmuebles")
	public String sqlInmuebles() {
		return "select i.id, i.ubicacion, i.estado, i.direccion, i.codigo_zip, i.latitud, i.longitud, c.nombre as cuenta, "
				+ "       c.codigo_postal, pf.tipo, pf.descripcion, pf.mega_proveedor_feed as mega, pf.estado as feed_estado "
				+ "  from only inmueble i "
				+ "  join only cuenta c on c.id = i.cuenta "
				+ "  join proveedor_feed pf on pf.id = i.proveedor_feed "
				+ "where i.estado = 'ACTIVO'"
				+ "  and i.cuenta in (:ids)";
	}

	@Bean
	@Qualifier("sqlCuentas")
	public String sqlCuentas() {
		return "select id from only cuenta where estado = 'ACTIVO'";
	}

}
