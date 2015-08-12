CREATE TABLE cuenta(
	id INTEGER PRIMARY KEY,
	nombre VARCHAR(100),
	estado CHAR(50),
	codigo_postal CHAR(10),
);

CREATE TABLE inmueble(
	id INTEGER PRIMARY KEY,
	ubicacion VARCHAR(150),
	estado CHAR(50),
	direccion VARCHAR(150),
	codigo_zip CHAR(10),
	latitud CHAR(20),
	longitud CHAR(20),
	cuenta INTEGER,
	proveedor_feed INTEGER
);

CREATE TABLE proveedor_feed(
	id INTEGER PRIMARY KEY,
	tipo CHAR(20),
	descripcion VARCHAR(100),
	mega_proveedor_feed CHAR(30),
	estado CHAR(50)
);