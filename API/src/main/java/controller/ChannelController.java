package controller;

import static spark.Spark.get;

import database.Conexion;
import services.ChannelService;

public class ChannelController {
	public ChannelController(final ChannelService channelService) {
		Conexion connection = new Conexion();
		get("/channel/all", (req, res) -> channelService.getAllChannel(connection,req));
		get("/channel/alta", (req, res) -> channelService.alta(connection,req));
		get("/channel/baja", (req, res) -> channelService.baja(connection,req));
		get("/channel/modificar", (req, res) -> channelService.modificar(connection,req));
		get("/channel/obtener", (req, res) -> channelService.obtener(connection,req));
		get("/channel/obtenerepg", (req, res) -> channelService.obtenerEpg(connection,req));
	}
}
