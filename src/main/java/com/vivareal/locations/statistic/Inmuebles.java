package com.vivareal.locations.statistic;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

public class Inmuebles {

	private static final String ID = "id";
	private static final String UBICACION = "ubicacion";
	private static final String ESTADO = "estado";
	private static final String DIRECCION = "direccion";
	private static final String CODIGO_ZIP = "codigo_zip";
	private static final String LATITUD = "latitud";
	private static final String LONGITUD = "longitud";
	private static final String CUENTA = "cuenta";
	private static final String CODIGO_POSTAL = "codigo_postal";
	private static final String TIPO = "tipo";
	private static final String DESCRIPTION = "descripcion";
	private static final String MEGA = "mega";
	private static final String FEED_ESTADO = "feed_estado";

	private File file;
	private LineIterator iterator;
	private Map<String, Integer> header = new HashMap<>();

	public Inmuebles(Path path) throws Exception {
		file = path.toFile();
		start();
	}

	public boolean hasNext() {
		return iterator.hasNext();
	}

	public Inmueble next() {
		Inmueble inmueble = new Inmueble();
		String line = iterator.next();
		// System.out.println(line);
		String[] split = line.split(",", -1);
		inmueble.setCodigo_zip(getValue(split, CODIGO_ZIP));
		inmueble.setDireccion(getValue(split, DIRECCION));
		inmueble.setEstado(getValue(split, ESTADO));
		inmueble.setId(getValue(split, ID));
		inmueble.setLatitud(getValue(split, LATITUD));
		inmueble.setLongitud(getValue(split, LONGITUD));
		inmueble.setTipo(getValue(split, TIPO));
		inmueble.setUbicacion(getValue(split, UBICACION));
		inmueble.setCodigo_postal(getValue(split, CODIGO_POSTAL));
		inmueble.setCuenta(getValue(split, CUENTA));
		inmueble.setDescription(getValue(split, DESCRIPTION));
		inmueble.setMega(getValue(split, MEGA));
		inmueble.setFeedEstado(getValue(split, FEED_ESTADO));
		return inmueble;
	}

	private String getValue(String[] split, String name) {
		Integer index = header.get(name.trim().toUpperCase());
		if (index >= split.length) {
			return null;
		}
		String value = split[index];
		if (value == null || value.trim().isEmpty()) {
			return null;
		}
		return value.trim();
	}

	public void start() {
		try {
			iterator = FileUtils.lineIterator(file);
			header.clear();
			if (iterator.hasNext()) {
				String line = iterator.next();
				// System.out.println(line);
				String[] split = line.split(",");
				for (int i = 0; i < split.length; i++) {
					header.put(split[i].trim().toUpperCase(), i);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
