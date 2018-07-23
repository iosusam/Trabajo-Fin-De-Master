package controller;

import static spark.Spark.get;

import database.Conexion;
import services.EpgService;

public class EpgController {
	public EpgController(final EpgService epgService) {
		Conexion connection = new Conexion();
		get("/epg/all", (req, res) -> epgService.getAllEpg(connection,req));
		get("/epg/alta", (req, res) -> epgService.getAllEpg(connection,req));
		get("/epg/baja", (req, res) -> epgService.getAllEpg(connection,req));
		get("/epg/modificar", (req, res) -> epgService.getAllEpg(connection,req));
		get("/epg/obtener", (req, res) -> epgService.getAllEpg(connection,req));
	}
}
