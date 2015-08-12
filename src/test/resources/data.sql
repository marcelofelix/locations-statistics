delete from cuenta;
delete from inmueble;
delete from proveedor_feed;

insert into cuenta(id, nombre, estado, codigo_postal)values(1, 'Teste 1', 'ACTIVO', '');


insert into proveedor_feed
	(id, tipo, descripcion, mega_proveedor_feed, estado)
	values
	(1, 'NEW', 'VR', 'XXX', 'X');

insert into inmueble
	(id, ubicacion, estado, direccion, codigo_zip, latitud, longitud, cuenta, proveedor_feed)
	values
	(1,'','ACTIVO', '', '','','',1,1);