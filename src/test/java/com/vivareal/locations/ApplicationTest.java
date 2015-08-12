package com.vivareal.locations;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
public class ApplicationTest extends Application {

	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.HSQL)
				.addScripts("classpath:/database.sql")
				.build();
	}

	@Bean
	@Qualifier("sqlInmuebles")
	public String sqlInmuebles() {
		return "select i.id, i.ubicacion, i.estado, i.direccion, i.codigo_zip, i.latitud, i.longitud, c.nombre as cuenta, "
				+ "       c.codigo_postal, pf.tipo, pf.descripcion, pf.mega_proveedor_feed as mega, pf.estado as feed_estado "
				+ "  from inmueble i "
				+ "  join cuenta c on c.id = i.cuenta "
				+ "  join proveedor_feed pf on pf.id = i.proveedor_feed "
				+ "where i.estado = 'ACTIVO'"
				+ "  and i.cuenta in (:ids)";
	}

	@Bean
	@Qualifier("sqlCuentas")
	public String sqlCuentas() {
		return "select id from cuenta where estado = 'ACTIVO'";
	}
}
