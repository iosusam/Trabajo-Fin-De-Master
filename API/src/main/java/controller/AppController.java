package controller;

import static spark.Spark.get;

import database.Conexion;
import services.AppService;

public class AppController {
	public AppController(final AppService appService) {
		Conexion connection = new Conexion();
		get("/app/all", (req, res) -> appService.getAllApp(connection,req));
		get("/app/alta", (req, res) -> appService.getAllApp(connection,req));
		get("/app/baja", (req, res) -> appService.getAllApp(connection,req));
		get("/app/modificar", (req, res) -> appService.getAllApp(connection,req));
		get("/app/obtener", (req, res) -> appService.getAllApp(connection,req));
	}
}
