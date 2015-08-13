package com.vivareal.locations.statistic.service;

import static java.nio.file.Paths.get;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.vivareal.locations.statistic.exception.FileNotFount;
import com.vivareal.locations.statistic.model.Report;

@Component
public class Storage {

	private static final Logger log = LoggerFactory.getLogger(Storage.class);

	@Value("${data.extracted.dir}")
	private String dir;

	@Value("${locations.bucket}")
	private String bucketName;

	private AmazonS3Client client = new AmazonS3Client();

	@PostConstruct
	public void init() throws Exception {
		Path path = get(dir);
		if (!Files.exists(path)) {
			Files.createDirectories(path);
		}
	}

	@Async
	public void upload(Report report) {
		try {
			PutObjectRequest request = new PutObjectRequest(bucketName, report.getFileName(), get(dir, report.getFileName()).toFile());
			client.putObject(request);
		} catch (Exception e) {
			log.error("Não foi possível salvar arquivo no s3", e);
		}
	}

	public Path create(Report report) {
		return Paths.get(dir, report.getFileName());
	}

	public Path getPath(String file) {
		try {
			Path path = get(dir, file);
			if (!Files.exists(path)) {
				GetObjectRequest request = new GetObjectRequest(bucketName, file);
				S3Object s3file = client.getObject(request);
				Files.copy(s3file.getObjectContent(), path);
			}
			return path;
		} catch (Exception e) {
			throw new FileNotFount();
		}
	}

	public void delete(Report report) {
		try {
			Path path = Paths.get(dir, report.getFileName());
			Files.deleteIfExists(path);
		} catch (IOException e) {
			log.error("Não foi possível remover o arquivo", e);
		}
	}

	public void copy(File file, String name) throws Exception {

		Path origin = Paths.get(file.getAbsolutePath());
		Path destiny = Paths.get(dir, name);
		Files.deleteIfExists(destiny);
		Files.copy(origin, destiny);

	}
}
