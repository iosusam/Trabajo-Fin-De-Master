package controller;

import static spark.Spark.get;

import database.Conexion;
import services.OrganizationService;

public class OrganizationController {
	public OrganizationController(final OrganizationService organizationService) {
		Conexion connection = new Conexion();
		get("/organization/all", (req, res) -> organizationService.getAllOrganizations(connection,req));
		get("/organization/alta", (req, res) -> organizationService.alta(connection,req));
		get("/organization/baja", (req, res) -> organizationService.baja(connection,req));
		get("/organization/modificar", (req, res) -> organizationService.modificar(connection,req));
		get("/organization/obtener", (req, res) -> organizationService.obtener(connection,req));
		
	}
}
