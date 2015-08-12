package com.vivareal.locations.statistic;

import java.nio.file.Paths;
import java.util.function.Function;
import java.util.function.Predicate;

public class Main {

	public static void main(String args[]) throws Exception {

		Inmuebles inmuebles = new Inmuebles(Paths.get("/Users/marcelofelix/Downloads/data.csv"));

		new Reports()
				.process("Numero de imovies que possuem cep valido", c -> {
					c.add(count("Com cep", Inmueble::hasZipCode)
							.add(count("Mesmo cep da imobiliaria", Inmueble::hasZipCodeEqualCuenta)));
				})
				.process("Número de imoveis com lat/long dentro do Brasil", c -> {
					c.add(count("Com lat/long", Inmueble::hasLatLong)
							.add(count("Dentro do Brasil", Inmueble::isInBrazil))
							.add(count("Invertidos", Inmueble::isLatLngInverted))
							);
				})
				.process("Agrupado por modelo de feed", c -> {
					c.add(count(Inmueble::getTipo));
				})
				.process("Agrupado por software", c -> {
					c.add(count(Inmueble::getDescription));
				})
				.process("Imoveis com endereço", c -> {
					c.add(count("Com endereço", Inmueble::hasAddress)
							.add(count(Inmueble::getDescription)));
					c.add(count("Sem endereço", i -> !i.hasAddress())
							.add(count(Inmueble::getDescription)));
				})
				.run(inmuebles);

	}

	private static Counter count(String name, Predicate<Inmueble> predicate) {
		return new Counter(name, predicate);
	}

	private static Counter count(Function<Inmueble, String> function) {
		return new Counter(function);
	}

}
