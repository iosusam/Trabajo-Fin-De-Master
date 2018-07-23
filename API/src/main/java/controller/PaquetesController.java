package controller;

import static spark.Spark.get;

import database.Conexion;
import services.PaquetesService;

public class PaquetesController {
	public PaquetesController(final PaquetesService paquetesService) {
		Conexion connection = new Conexion();
		get("/paquetes/all", (req, res) -> paquetesService.getAllPaquetes(connection,req));
		get("/paquetes/alta", (req, res) -> paquetesService.alta(connection,req));
		get("/paquetes/baja", (req, res) -> paquetesService.baja(connection,req));
		get("/paquetes/modificar", (req, res) -> paquetesService.modificar(connection,req));
		get("/paquetes/obtener", (req, res) -> paquetesService.obtener(connection,req));
		get("/paquetes/obtenerCanales", (req, res) -> paquetesService.obtenerCanales(connection,req));
	}
}
