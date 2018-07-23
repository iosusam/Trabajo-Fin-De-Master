package services;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.json.JSONObject;

import database.Conexion;
import model.Channels;
import model.Organizacion;
import model.Usuarios;
import modelToJson.ChannelsToJson;
import modelToJson.UsuariosToJson;
import spark.Request;
import utilities.CalcsUtil;
import utilities.JsonUtil;

public class UserService {
	JsonUtil jsonConverter = new JsonUtil();
	
	// returns a list of all users
	public JSONObject getAllUsers(Conexion conn, Request req) { 
		JSONObject jsonResult = null;
		
		String pidRequest = (req.queryParams("pid") != null) ? req.queryParams("pid") : "";
		
		//Calculo de PID
		String Pid = CalcsUtil.generatePid();
		
		//Se comprueba que el PID es correcto
		if((pidRequest == null) || !pidRequest.equals(Pid)){
			jsonResult = JsonUtil.cursoAlternativo("REQUEST_NOT_ALLOWED");
		}
		//La peticion es correcta, se ejecuta
		else{
			try {
				Session sesion = conn.iniciaOperacion(); 
				
				List<Usuarios> usuarios = sesion.createQuery("from Usuarios").list();
				jsonResult  = UsuariosToJson.parseUsuarios(usuarios);
				
				conn.terminaOperacion();
				
			} catch (Exception e) {
				jsonResult = JsonUtil.cursoAlternativo("ACTION_NOT_ALLOWED");
			}
		}
		return jsonResult;
	}
	  
	public JSONObject login(Conexion conn, Request req) { 
		JSONObject jsonResult = null;
		
		String pidRequest = (req.queryParams("pid") != null) ? req.queryParams("pid") : "";
		String usuarioRequest = (req.queryParams("usuario") != null) ? req.queryParams("usuario") : "";
		String passwordRequest = (req.queryParams("pass") != null) ? req.queryParams("pass") : "";
		
		
		//Calculo de PID
		String Pid = CalcsUtil.generatePid();
		
		//Se comprueba que el PID es correcto
		if((pidRequest == null) || !pidRequest.equals(Pid)){
			jsonResult = JsonUtil.cursoAlternativo("REQUEST_NOT_ALLOWED");
		}
		//La peticion es correcta, se ejecuta
		else{
			try {
				Session sesion = conn.iniciaOperacion(); 
 				Query userQuery = sesion.createQuery("from Usuarios where email = '" + usuarioRequest + "' AND password = '" + passwordRequest + "'");
				
				//Si hay algun usuario en el sistema
				if(userQuery.list().size() > 0){
					Usuarios usuario = (Usuarios)userQuery.getSingleResult();
					if(usuario.getActivo() == 1){
						
						//Se actualiza la fecha de ultimo ingreso en la web
						Date date = new Date();
						usuario.setFechaUltimoAcceso(date);
						sesion.save(usuario);
						
						jsonResult = JsonUtil.cursoNormal();
					}else{
						jsonResult = JsonUtil.cursoAlternativo("USER_NOT_ACTIVE");
					}
				//No se ha encontrado a ese usuario con esa contraseña
				}else{
					jsonResult = JsonUtil.cursoAlternativo("PASSWORD_USER_NOT_CORRECT");
				}
				conn.terminaOperacion();
			} catch (Exception e) {
				jsonResult = JsonUtil.cursoAlternativo("ACTION_NOT_ALLOWED");
			}
		}
		
		return jsonResult;
	  }
	
	public JSONObject alta(Conexion conn, Request req) { 
		JSONObject jsonResult = null;
		
		String pidRequest = (req.queryParams("pid") != null) ? req.queryParams("pid") : "";
		String usuarioRequest = (req.queryParams("usuario") != null) ? req.queryParams("usuario") : "";
		String passwordRequest = (req.queryParams("pass") != null) ? req.queryParams("pass") : "";
		String nombreRequest = (req.queryParams("nombre") != null) ? req.queryParams("nombre") : "";
		byte activoRequest = ((req.queryParams("activo") != null) && (!req.queryParams("activo").equals(""))) ? (byte)Integer.parseInt(req.queryParams("activo")) : 0;
		int rolRequest = ((req.queryParams("rol") != null) && (!req.queryParams("rol").equals(""))) ? Integer.parseInt(req.queryParams("rol")) : -1;
		String organizationRequest = (req.queryParams("organizations") != null) ? req.queryParams("organizations") : "";
		
		
		//Calculo de PID
		String Pid = CalcsUtil.generatePid();
		
		//Se comprueba que el PID es correcto
		if(!pidRequest.equals(Pid)){
			jsonResult = JsonUtil.cursoAlternativo("REQUEST_NOT_ALLOWED");
		}
		//La peticion es correcta, se ejecuta
		else if(usuarioRequest.equals("") || passwordRequest.equals("") ){
			jsonResult = JsonUtil.cursoAlternativo("INFORMATION_INCORRECT");
		}else{
			try {
				Session sesion = conn.iniciaOperacion(); 
				
				//Se comprueba que el usuario no exista en el sistema
				List<Usuarios> listUsuarios = sesion.createQuery("from Usuarios where email = '" + usuarioRequest + "'").list();
				
				if(listUsuarios.size() == 0){
					//Fecha actual
					Date date = new Date();
					
					//Se consulta a que clientes va asociados el usuario nuevo
					if(organizationRequest.equals("")){
						//Se crea un usuario, sin asociar a una organizacion
						Usuarios usuario = new Usuarios(nombreRequest,usuarioRequest,passwordRequest,date,date,activoRequest,rolRequest);
						sesion.save(usuario);
					}else{
						//Se consulta a que organizaciones se va a asignar al usuario
						String[] organizaciones = organizationRequest.split(";");
						Set setOrganizations = new HashSet();
						
						//Se crea un usuario
						Usuarios usuario = new Usuarios(nombreRequest,usuarioRequest,passwordRequest,date,date,activoRequest,rolRequest);
						
						//Recorrer las organizaciones
						for(String idorganizacion : organizaciones){
							
							List<Organizacion> listOrganizacion = sesion.createQuery("from Organizacion where ID = '" + idorganizacion + "'").list();
														
							//Se verifica que la organizacion existe en el sistema
							if(listOrganizacion.size() > 0){
								Organizacion oragani = listOrganizacion.get(0);
								//Se añade el usuario a la organizacion
								oragani.getUsuarioses().add(usuario);
								sesion.save(usuario);
							}
						}
					}
					jsonResult = JsonUtil.cursoNormal();
					
				}else{
					jsonResult = JsonUtil.cursoAlternativo("USER_ALREDY_EXIST");
				}
				
				conn.terminaOperacion();
				
			} catch (Exception e) {
				jsonResult = JsonUtil.cursoAlternativo("ACTION_NOT_ALLOWED");
			}
		}
		return jsonResult;
	  }
	
	public JSONObject baja(Conexion conn, Request req) { 
		JSONObject jsonResult = null;
		
		String pidRequest = (req.queryParams("pid") != null) ? req.queryParams("pid") : "";
		String usuarioRequest = (req.queryParams("usuario") != null) ? req.queryParams("usuario") : "";
		
		//Calculo de PID
		String Pid = CalcsUtil.generatePid();
		
		//Se comprueba que el PID es correcto
		if((pidRequest == null) || !pidRequest.equals(Pid)){
			jsonResult = JsonUtil.cursoAlternativo("REQUEST_NOT_ALLOWED");
		}else{
			try {
				Session sesion = conn.iniciaOperacion(); 
				
				Query userQuery = sesion.createQuery("from Usuarios where email = '" + usuarioRequest + "'");
				
				//Si hay algun usuario en el sistema
				if(userQuery.list().size() > 0){
					Usuarios usuario = (Usuarios)userQuery.getSingleResult();
					sesion.remove(usuario);
					jsonResult = JsonUtil.cursoNormal();
				}else{
					jsonResult = JsonUtil.cursoAlternativo("USER_NOT_EXIST");
				}
				
				conn.terminaOperacion();
			} catch (Exception e) {
				jsonResult = JsonUtil.cursoAlternativo("ACTION_NOT_ALLOWED");
			}
		}
		return jsonResult;
	}
	
	public JSONObject modificar(Conexion conn, Request req) { 
		JSONObject jsonResult = null;
		
		String pidRequest = (req.queryParams("pid") != null) ? req.queryParams("pid") : "";
		String usuarioRequest = (req.queryParams("usuario") != null) ? req.queryParams("usuario") : "";
		String passwordRequest = (req.queryParams("pass") != null) ? req.queryParams("pass") : "";
		String nombreRequest = (req.queryParams("nombre") != null) ? req.queryParams("nombre") : "";
		byte activoRequest = (req.queryParams("activo") != null) ? (byte)Integer.parseInt(req.queryParams("activo")) : 0;
		int rolRequest = (req.queryParams("rol") != null) ? Integer.parseInt(req.queryParams("rol")) : 2;
		String organizationRequest = (req.queryParams("organizations") != null) ? req.queryParams("organizations") : "";
		
		
		
		//Calculo de PID
		String Pid = CalcsUtil.generatePid();
		
		//Se comprueba que el PID es correcto
		if(!pidRequest.equals(Pid)){
			jsonResult = JsonUtil.cursoAlternativo("REQUEST_NOT_ALLOWED");
		}else{
			try {
				Session sesion = conn.iniciaOperacion(); 
				
				//Se comprueba que el usuario no exista en el sistema
				List<Usuarios> listUsuarios = sesion.createQuery("from Usuarios where email = '" + usuarioRequest + "'").list();
				
				if(listUsuarios.size() > 0){
					
					Usuarios usuario = listUsuarios.get(0);
					
					//Si ha cambiado los clientes asociados
					if(!organizationRequest.equals("")){
						//Se consulta a que organizaciones se va a asignar al usuario
						String[] organizaciones = organizationRequest.split(";");
						
						//Usuario que pertenecen al cliente
						Set<Organizacion> clientesAnteriores = usuario.getOrganizacions();
						
						Iterator<Organizacion> itr = clientesAnteriores.iterator();
						
						while (itr.hasNext())
						{
							Organizacion cliente =  itr.next();
							
							//Si el usuario ya no pertenece al cliente, se borra
							if(!Arrays.asList(organizaciones).contains(cliente.getId().toString())){
								Set<Usuarios> usuariosCliente = cliente.getUsuarioses();
								usuariosCliente.remove(usuario);
							}      
						}
						
						//Si introducen el usuario en los clientes
						for(String idorganizacion : organizaciones){
							List<Organizacion> listOrganizacion = sesion.createQuery("from Organizacion where ID = '" + idorganizacion + "'").list();
							if(listOrganizacion.size() > 0){
								if(!clientesAnteriores.contains(listOrganizacion.get(0))){
									Set<Usuarios> usuariosCliente = listOrganizacion.get(0).getUsuarioses();
									usuariosCliente.add(usuario);
								}
							}    
						}
						
					}else{
						//Se recorren los clientes a los que esta asociado el usuario y se borran
						Set<Organizacion> clientesAnteriores = usuario.getOrganizacions();
						
						Iterator<Organizacion> itr = clientesAnteriores.iterator();
						
						while (itr.hasNext())
						{
							//Obtenemos el cliente
							Organizacion cliente =  itr.next();
							
							//Se borra el usuario del cliente
							Set<Usuarios> usuariosCliente = cliente.getUsuarioses();
							usuariosCliente.remove(usuario);
						}
					}
					
					//Si ha cambiado el usuario
					if(!usuarioRequest.equals("")){
						usuario.setEmail(usuarioRequest);
					}
					//Si ha cambiado la contraseña
					if(!passwordRequest.equals("")){
						usuario.setPassword(passwordRequest);
					}
					//Si ha cambiado el nombre
					if(!nombreRequest.equals("")){
						usuario.setNombre(nombreRequest);
					}
					
					
					
					usuario.setActivo(activoRequest);
					usuario.setRol(rolRequest);
					
					sesion.update(usuario);
					
					jsonResult = JsonUtil.cursoNormal();
					
				}else{
					jsonResult = JsonUtil.cursoAlternativo("USER_NOT_EXIST");
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
		String usuarioid = ((req.queryParams("usuario") != null)) ? req.queryParams("usuario") : "";
		
		
		//Calculo de PID
		String Pid = CalcsUtil.generatePid();
		  
		if(!pidRequest.equals(Pid)){
			jsonResult = JsonUtil.cursoAlternativo("REQUEST_NOT_ALLOWED");
		}else if(usuarioid.equals("")){
			jsonResult = JsonUtil.cursoAlternativo("INTRODUCE_USERID");
		}
		//La peticion es correcta, se ejecuta
		else{
			try {
				Session sesion = conn.iniciaOperacion();
				List<Usuarios> usuario = sesion.createQuery("FROM Usuarios WHERE email = '" + usuarioid + "'").list();
				  if(!(usuario.size() > 0)){
					  jsonResult = JsonUtil.cursoAlternativo("USERL_NOT_EXIST");
				  }else{
					  jsonResult  = UsuariosToJson.parseUsuarios(usuario);
				  }
				conn.terminaOperacion();
			} catch (Exception e) {
				jsonResult = JsonUtil.cursoAlternativo("ACTION_NOT_ALLOWED");
			}
		}
			
		  return jsonResult;
	}
}
