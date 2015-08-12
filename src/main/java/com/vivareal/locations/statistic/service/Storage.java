package com.vivareal.locations.statistic.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Storage {

	@Value("${data.extracted.dir}")
	private String dir;

	@PostConstruct
	public void init() throws Exception {
		Path path = Paths.get(dir);
		if (!Files.exists(path)) {
			Files.createDirectories(path);
		}
	}

	public Path getPath(String file) {
		return Paths.get(dir, file);
	}

	public boolean exists(String file) {
		return Files.exists(Paths.get(dir, file));
	}

	public File getFile(String file) {
		return Paths.get(dir, file).toFile();
	}

	public void copy(File file, String name) throws Exception {

		Path origin = Paths.get(file.getAbsolutePath());
		Path destiny = Paths.get(dir, name);
		Files.deleteIfExists(destiny);
		Files.copy(origin, destiny);

	}
}
