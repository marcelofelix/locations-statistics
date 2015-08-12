package com.vivareal.locations.statistic;

import static org.apache.commons.lang3.math.NumberUtils.isNumber;

public class Inmueble {

	private String id;
	private String ubicacion;
	private String tipo;
	private String estado;
	private String direccion;
	private String codigo_zip;
	private String latitud;
	private String longitud;
	private String cuenta;
	private String codigo_postal;
	private String description;
	private String mega;
	private String feedEstado;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMega() {
		return mega;
	}

	public void setMega(String mega) {
		this.mega = mega;
	}

	public String getFeedEstado() {
		return feedEstado;
	}

	public void setFeedEstado(String feedEstado) {
		this.feedEstado = feedEstado;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCodigo_zip() {
		return codigo_zip;
	}

	public void setCodigo_zip(String codigo_zip) {
		this.codigo_zip = codigo_zip;
	}

	public String getLatitud() {
		return latitud;
	}

	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}

	public String getLongitud() {
		return longitud;
	}

	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}

	public boolean hasLatLong() {
		return latitud != null && longitud != null;
	}

	public boolean hasValidLatLong() {
		if (hasLatLong()) {
			return isNumber(latitud) && isNumber(longitud);
		}
		return false;
	}

	public Double getLat() {
		return Double.valueOf(latitud);
	}

	public Double getLng() {
		return Double.valueOf(longitud);
	}

	public boolean isActive() {
		return "ACTIVO".equals(estado);
	}

	public boolean hasZipCode() {
		return codigo_zip != null;
	}

	public String getCuenta() {
		return cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	public String getCodigo_postal() {
		return codigo_postal;
	}

	public void setCodigo_postal(String codigo_postal) {
		this.codigo_postal = codigo_postal;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean hasValidZipCode() {
		return hasZipCode() && codigo_zip.replaceAll("\\D", "").length() == 8;
	}

	public boolean isInBrazil() {
		return hasValidLatLong() && inBrazil(getLat(), getLng());
	}

	public boolean isLatLngInverted() {
		return hasValidLatLong() && inBrazil(getLng(), getLat());
	}

	public boolean hasAddress() {
		return direccion != null;
	}

	private boolean inBrazil(Double lat, Double lng) {
		Double startLat = -34.397845;
		Double endLat = 6.206090;
		Double startLong = -75.058594;
		Double endLong = -33.750000;
		return lat > startLat && lat < endLat && lng > startLong && lng < endLong;
	}

	public boolean isTipo(String value) {
		return value.equals(tipo);
	}

	public boolean hasZipCodeEqualCuenta() {
		if (codigo_postal != null && hasValidZipCode()) {
			return codigo_postal.replaceAll("\\D", "").equals(codigo_zip.replaceAll("\\D", ""));
		}
		return false;
	}
}
