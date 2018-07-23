package services;

import java.util.List;

import org.hibernate.Session;
import org.json.JSONObject;

import database.Conexion;
import model.Organizacion;
import model.Parrilla;
import modelToJson.OrganizacionToJson;
import spark.Request;
import utilities.CalcsUtil;
import utilities.JsonUtil;

public class OrganizationService {
	
	//Obtener todos los clientes de la empresa.
	public JSONObject getAllOrganizations(Conexion conn, Request req) { 

		JSONObject jsonResult = null;
		
		String pidRequest = (req.queryParams("pid") != null) ? req.queryParams("pid") : "";
		
		//Calculo de PID
		String Pid = CalcsUtil.generatePid();
		
		//Se comprueba que el PID es correcto
		if(!pidRequest.equals(Pid)){
			jsonResult = JsonUtil.cursoAlternativo("REQUEST_NOT_ALLOWED");
		}
		//La peticion es correcta, se ejecuta
		else{
			try {
				Session sesion = conn.iniciaOperacion(); 
				List<Organizacion> organizaciones = sesion.createQuery("from Organizacion").list();
				jsonResult  = OrganizacionToJson.parseOrganizacion(organizaciones);
				conn.terminaOperacion();
			} catch (Exception e) {
				jsonResult = JsonUtil.cursoAlternativo("ACTION_NOT_ALLOWED");
			}
		}
		
		return jsonResult;
	}

	public Object alta(Conexion conn, Request req) {
		JSONObject jsonResult = null;
		String pidRequest = (req.queryParams("pid") != null) ? req.queryParams("pid") : "";
		String nombre = (req.queryParams("nombre") != null) ? req.queryParams("nombre") : "";
		String pais = (req.queryParams("pais") != null) ? req.queryParams("pais") : "";
		String region = (req.queryParams("region") != null) ? req.queryParams("region") : "";
		String direccion = (req.queryParams("direccion") != null) ? req.queryParams("direccion") : "";
		int codigopostal = ((req.queryParams("codigopostal") != null) && (!req.queryParams("codigopostal").equals(""))) ? Integer.parseInt(req.queryParams("codigopostal")) : 0;
		String ciudad = (req.queryParams("ciudad") != null) ? req.queryParams("ciudad") : "";
		String telefono = (req.queryParams("telefono") != null) ? req.queryParams("telefono") : "";
		String email = (req.queryParams("email") != null) ? req.queryParams("email") : "";
		  
		//Calculo de PID
		String Pid = CalcsUtil.generatePid();
		
		//Se comprueba que el PID es correcto
		if(!pidRequest.equals(Pid)){
			jsonResult = JsonUtil.cursoAlternativo("REQUEST_NOT_ALLOWED");
		}
		//La peticion es correcta, se ejecuta
		else{
			try {
				Session sesion = conn.iniciaOperacion();
				
				//Se crea una parrilla nueva y vacia para la organizacion
				Parrilla parri = new Parrilla();
				sesion.save(parri);
				//Se crea el nuevo suscriptor
				Organizacion nuevo_suscriptor = new Organizacion(parri,nombre,pais,region,direccion,codigopostal,ciudad,telefono,email);
				//Se guarda el suscriptor
				sesion.save(nuevo_suscriptor);
				jsonResult  = JsonUtil.cursoNormal();
				conn.terminaOperacion();
			} catch (Exception e) {
				jsonResult = JsonUtil.cursoAlternativo("ACTION_NOT_ALLOWED");
			}
		}
			
		return jsonResult;
	}

	public Object baja(Conexion conn, Request req) {
		JSONObject jsonResult = null;
		String pidRequest = (req.queryParams("pid") != null) ? req.queryParams("pid") : "";
		int organizationId = ((req.queryParams("organizationid") != null) && (!req.queryParams("organizationid").equals(""))) ? Integer.parseInt(req.queryParams("organizationid")) : -1;
		
		
		//Calculo de PID
		String Pid = CalcsUtil.generatePid();
		  
		if(!pidRequest.equals(Pid)){
			jsonResult = JsonUtil.cursoAlternativo("REQUEST_NOT_ALLOWED");
		}else if(organizationId == -1l){
			jsonResult = JsonUtil.cursoAlternativo("INTRODUCE_ORGANIZATION");
		}
		//La peticion es correcta, se ejecuta
		else{
			try {
				Session sesion = conn.iniciaOperacion();
				List<Organizacion> organizacion = sesion.createQuery("FROM Organizacion WHERE id = '" + organizationId + "'").list();
				  if(!(organizacion.size()>0)){
					  jsonResult = JsonUtil.cursoAlternativo("ORGANIZATION_NOT_EXIST");
				  }else{
					  sesion.delete(organizacion.get(0));
					  jsonResult  = JsonUtil.cursoNormal();
				  }
				conn.terminaOperacion();
			} catch (Exception e) {
				jsonResult = JsonUtil.cursoAlternativo("ACTION_NOT_ALLOWED");
			}
		}
			
		  return jsonResult;
	}
	
	public Object modificar(Conexion conn, Request req) {
		JSONObject jsonResult = null;
		
		String pidRequest = (req.queryParams("pid") != null) ? req.queryParams("pid") : "";
		int organizationId = ((req.queryParams("organizationid") != null) && (!req.queryParams("organizationid").equals(""))) ? Integer.parseInt(req.queryParams("organizationid")) : -1;
		String nombre = (req.queryParams("nombre") != null) ? req.queryParams("nombre") : "";
		String pais = (req.queryParams("pais") != null) ? req.queryParams("pais") : "";
		String region = (req.queryParams("region") != null) ? req.queryParams("region") : "";
		String direccion = (req.queryParams("direccion") != null) ? req.queryParams("direccion") : "";
		int codigopostal = ((req.queryParams("codigopostal") != null) && (!req.queryParams("codigopostal").equals(""))) ? Integer.parseInt(req.queryParams("codigopostal")) : 0;
		String ciudad = (req.queryParams("ciudad") != null) ? req.queryParams("ciudad") : "";
		String telefono = (req.queryParams("telefono") != null) ? req.queryParams("telefono") : "";
		String email = (req.queryParams("email") != null) ? req.queryParams("email") : "";
		
		//Calculo de PID
		String Pid = CalcsUtil.generatePid();
		
		//Se comprueba que el PID es correcto
		if(!pidRequest.equals(Pid)){
			jsonResult = JsonUtil.cursoAlternativo("REQUEST_NOT_ALLOWED");
		}
		//La peticion es correcta, se ejecuta
		else{
			try {
				Session sesion = conn.iniciaOperacion(); 
				List<Organizacion> organizaciones = sesion.createQuery("from Organizacion where id = '" + organizationId + "'").list();
				if(organizaciones.size() == 0){
					jsonResult = JsonUtil.cursoAlternativo("ORGANIZATION_NOT_EXIST");
				}else{
					Organizacion organizacion = organizaciones.get(0);
					
					//Se actualizan los atributos de la organizacion
					
					//Si ha cambiado el usuario
					if(!nombre.equals("")){
						organizacion.setNombre(nombre);
					}
					//Si ha cambiado la contraseña
					if(!pais.equals("")){
						organizacion.setPais(pais);
					}
					//Si ha cambiado el nombre
					if(!region.equals("")){
						organizacion.setRegion(region);
					}
					
					//Si ha cambiado el usuario
					if(!direccion.equals("")){
						organizacion.setDireccion(direccion);
					}
					//Si ha cambiado la contraseña
					if(codigopostal != -1){
						organizacion.setCodigoPostal(codigopostal);
					}
					//Si ha cambiado el nombre
					if(!ciudad.equals("")){
						organizacion.setCiudad(ciudad);
					}
					//Si ha cambiado el nombre
					if(!telefono.equals("")){
						organizacion.setTelefono(telefono);
					}
					//Si ha cambiado el nombre
					if(!email.equals("")){
						organizacion.setEmail(email);
					}
					
					sesion.save(organizacion);
					jsonResult = JsonUtil.cursoNormal();
				}
				
				conn.terminaOperacion();
			} catch (Exception e) {
				jsonResult = JsonUtil.cursoAlternativo("ACTION_NOT_ALLOWED");
			}
		}
		return jsonResult;
	}

	public Object obtener(Conexion conn, Request req) {
		JSONObject jsonResult = null;
		String pidRequest = (req.queryParams("pid") != null) ? req.queryParams("pid") : "";
		int organizationId = ((req.queryParams("organizationid") != null) && (!req.queryParams("organizationid").equals(""))) ? Integer.parseInt(req.queryParams("organizationid")) : -1;
		
		
		//Calculo de PID
		String Pid = CalcsUtil.generatePid();
		  
		if(!pidRequest.equals(Pid)){
			jsonResult = JsonUtil.cursoAlternativo("REQUEST_NOT_ALLOWED");
		}else if(organizationId == -1l){
			jsonResult = JsonUtil.cursoAlternativo("INTRODUCE_ORGANIZATION");
		}
		//La peticion es correcta, se ejecuta
		else{
			try {
				Session sesion = conn.iniciaOperacion();
				List<Organizacion> organizacion = sesion.createQuery("FROM Organizacion WHERE id = '" + organizationId + "'").list();
				  if(!(organizacion.size() > 0)){
					  jsonResult = JsonUtil.cursoAlternativo("ORGANIZATION_NOT_EXIST");
				  }else{
					  jsonResult  = OrganizacionToJson.parseOrganizacion(organizacion);
				  }
				conn.terminaOperacion();
			} catch (Exception e) {
				jsonResult = JsonUtil.cursoAlternativo("ACTION_NOT_ALLOWED");
			}
		}
			
		  return jsonResult;
	}
	
}
