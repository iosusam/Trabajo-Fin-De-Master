package controller;

import static spark.Spark.get;

import database.Conexion;
import services.UserService;

public class UserController {
	public UserController(final UserService userService) {
		Conexion connection = new Conexion();
	    get("/users/all", (req, res) -> userService.getAllUsers(connection,req));
	    get("/users/login", (req, res) -> userService.login(connection,req));
	    get("/users/alta", (req, res) -> userService.alta(connection,req));
	    get("/users/baja", (req, res) -> userService.baja(connection,req));
	    get("/users/modificar", (req, res) -> userService.modificar(connection,req));  
	    get("/users/obtener", (req, res) -> userService.obtener(connection,req));   
	}
}
