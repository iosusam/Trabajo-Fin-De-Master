package controller;

import static spark.Spark.get;

import database.Conexion;
import services.SuscriptorService;

public class SuscriptorsController {
	public SuscriptorsController(final SuscriptorService suscriptorService) {
		Conexion connection = new Conexion();
		get("/suscriptors/all", (req, res) -> suscriptorService.getAllSuscriptors(connection,req));
		get("/suscriptors/alta", (req, res) -> suscriptorService.alta(connection,req));
		get("/suscriptors/baja", (req, res) -> suscriptorService.baja(connection,req));
		get("/suscriptors/modificar", (req, res) -> suscriptorService.modificar(connection,req));
		get("/suscriptors/obtener", (req, res) -> suscriptorService.obtener(connection,req));
		get("/suscriptors/obtenerOrganization", (req, res) -> suscriptorService.getSuscriptorsOrganization(connection,req));
		get("/suscriptors/active", (req, res) -> suscriptorService.active(connection,req));
		
	}
}