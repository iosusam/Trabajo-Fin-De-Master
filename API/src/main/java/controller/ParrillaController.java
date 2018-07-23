package controller;

import static spark.Spark.get;

import database.Conexion;
import services.ParrillaService;

public class ParrillaController {
	public ParrillaController(final ParrillaService parrillaService) {
		Conexion connection = new Conexion();
		get("/parrilla/baja", (req, res) -> parrillaService.baja(connection,req));
		get("/parrilla/modificar", (req, res) -> parrillaService.modificar(connection,req));
		get("/parrilla/obtener", (req, res) -> parrillaService.obtener(connection,req));
	}
}
