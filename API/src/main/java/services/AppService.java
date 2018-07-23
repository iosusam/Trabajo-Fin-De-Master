package services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.json.JSONObject;

import database.Conexion;
import model.App;
import model.Organizacion;
import model.Suscriptores;
import modelToJson.AppToJson;
import spark.Request;
import utilities.CalcsUtil;
import utilities.JsonUtil;

public class AppService {
	public Object getAllApp(Conexion conn, Request req) {
		
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
				
				List<App> app = sesion.createQuery("from App").list();
				jsonResult  = AppToJson.parseApp(app);
				
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
		int idappRequest = (req.queryParams("appid") != null) ? Integer.parseInt(req.queryParams("appid")) : -1;
		String nombreRequest = (req.queryParams("nombre") != null) ? req.queryParams("nombre") : "";
		String versionRequest = (req.queryParams("version") != null) ? req.queryParams("version") : "";
		String ubicacionServerRequest = (req.queryParams("ubicacionServer") != null) ? req.queryParams("ubicacionServer") : "";
		String organizationRequest = (req.queryParams("organizacionid") != null) ? req.queryParams("organizacionid") : "";
		String suscriptoresRequest = (req.queryParams("suscriptores") != null) ? req.queryParams("suscriptores") : "";
		
		//Calculo de PID
		String Pid = "";//CalcsUtil.generatePid();
		
		//Se comprueba que el PID es correcto
		if((pidRequest == null) || !pidRequest.equals(Pid)){
			jsonResult = JsonUtil.cursoAlternativo("REQUEST_NOT_ALLOWED");
		}
		//La peticion es correcta, se ejecuta
		else{
			try {
				Session sesion = conn.iniciaOperacion(); 
				
				
				List<App> listaApp = sesion.createQuery("from App where '" + idappRequest + "'").list();
				if(listaApp.size() > 0){
					jsonResult = JsonUtil.cursoAlternativo("APP_ALREDY_EXIST");
				}else{
					//Se consulta a que suscriptores se va a asignar al usuario
					String[] suscriptores = suscriptoresRequest.split(";");
					Set setSuscriptores = new HashSet();
					
					//Recorrer los suscriptores
					for(String idSuscriptor : suscriptores){
						List<Suscriptores> listOrganizacion = sesion.createQuery("from Suscriptores where ID = '" + idSuscriptor + "'").list();
						
						//Se verifica que la organizacion existe en el sistema
						if(listOrganizacion.size() > 0){
							setSuscriptores.add(listOrganizacion.get(0));
						}
					}
					
					//Se consulta a que organizaciones se va a asignar al usuario
					String[] organizaciones = organizationRequest.split(";");
					Set setOrganizations = new HashSet();
					
					//Recorrer las organizaciones
					for(String idorganizacion : organizaciones){
						List<Organizacion> listOrganizacion = sesion.createQuery("from Organizacion where ID = '" + idorganizacion + "'").list();
						
						//Se verifica que la organizacion existe en el sistema
						if(listOrganizacion.size() > 0){
							setOrganizations.add(listOrganizacion.get(0));
						}
					}
					
					//Se crea una app
					App app = new App(nombreRequest,versionRequest,ubicacionServerRequest,setOrganizations,setSuscriptores);
					sesion.save(app);
				}
				
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
		int idappRequest = (req.queryParams("appid") != null) ? Integer.parseInt(req.queryParams("appid")) : -1;
				
		//Calculo de PID
		String Pid = "";//CalcsUtil.generatePid();
		
		//Se comprueba que el PID es correcto
		if(pidRequest.equals("")){
			jsonResult = JsonUtil.cursoAlternativo("REQUEST_NOT_ALLOWED");
		}
		else if(idappRequest == -1){
			jsonResult = JsonUtil.cursoAlternativo("INTRODUCE_APPID");
		}
		//La peticion es correcta, se ejecuta
		else{
			try {
				Session sesion = conn.iniciaOperacion(); 
				List<App> app = sesion.createQuery("from App where '" + idappRequest + "'").list();
				if(app.size() == 0){
					jsonResult = JsonUtil.cursoAlternativo("APP_NOT_EXIST");
				}else{
					sesion.remove(app.get(0));
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
		int idappRequest = (req.queryParams("appid") != null) ? Integer.parseInt(req.queryParams("appid")) : -1;
		String nombreRequest = (req.queryParams("nombre") != null) ? req.queryParams("nombre") : "";
		String versionRequest = (req.queryParams("version") != null) ? req.queryParams("version") : "";
		String ubicacionServerRequest = (req.queryParams("ubicacionServer") != null) ? req.queryParams("ubicacionServer") : "";
		String organizationRequest = (req.queryParams("organizacionid") != null) ? req.queryParams("organizacionid") : "";
		String suscriptoresRequest = (req.queryParams("suscriptores") != null) ? req.queryParams("suscriptores") : "";
		
		
		//Calculo de PID
		String Pid = "";//CalcsUtil.generatePid();
		
		//Se comprueba que el PID es correcto
		if(!pidRequest.equals(Pid)){
			jsonResult = JsonUtil.cursoAlternativo("REQUEST_NOT_ALLOWED");
		}
		//La peticion es correcta, se ejecuta
		else{
			try {
				Session sesion = conn.iniciaOperacion(); 
				List<App> listaApp = sesion.createQuery("from App where idapp '" + idappRequest + "'").list();
				
				if(listaApp.size() > 0){
					//Se obtiene la aplicacion
					App app = listaApp.get(0);
					
					//Se actualiza el nombre de la app
					if(!nombreRequest.equals("")){
						
						app.setNombre(nombreRequest);
					}
					//se actualiza la version de la app
					if(!versionRequest.equals("")){
						
						app.setVersionApp(versionRequest);
					}
					//Se actualiza la ubicacion
					if(!ubicacionServerRequest.equals("")){
						app.setUbicacionServer(ubicacionServerRequest);
					}
					
					if(!organizationRequest.equals("")){
						//Se consulta a que organizaciones se va a asignar al usuario
						String[] organizaciones = organizationRequest.split(";");
						Set setOrganizations = new HashSet();
						
						//Recorrer las organizaciones
						for(String idorganizacion : organizaciones){
							List<Organizacion> listOrganizacion = sesion.createQuery("from Organizacion where ID = '" + idorganizacion + "'").list();
							
							//Se verifica que la organizacion existe en el sistema
							if(listOrganizacion.size() > 0){
								setOrganizations.add(listOrganizacion.get(0));
							}
						}
						
						app.setOrganizacions(setOrganizations);
					}
					
					if(!suscriptoresRequest.equals("")){
						//Se consulta a que suscriptores se va a asignar al usuario
						String[] suscriptores = suscriptoresRequest.split(";");
						Set setSuscriptores = new HashSet();
						
						//Recorrer los suscriptores
						for(String idSuscriptor : suscriptores){
							List<Suscriptores> listOrganizacion = sesion.createQuery("from Suscriptores where ID = '" + idSuscriptor + "'").list();
							
							//Se verifica que la organizacion existe en el sistema
							if(listOrganizacion.size() > 0){
								setSuscriptores.add(listOrganizacion.get(0));
							}
						}
						
						app.setSuscriptoreses(setSuscriptores);
					}
					
					//Se guarda el suscriptor
					sesion.update(app);
					 
					jsonResult  = JsonUtil.cursoNormal();

				}else{
					jsonResult = JsonUtil.cursoAlternativo("APP_NOT_EXIST");
				}
				
				conn.terminaOperacion();
			} catch (Exception e) {
				jsonResult = JsonUtil.cursoAlternativo("ACTION_NOT_ALLOWED");
			}
		}
		
		return jsonResult;
	}

	public Object getAppClient(Conexion conn, Request req) {
		
		JSONObject jsonResult = null;
		
		String pidRequest = (req.queryParams("pid") != null) ? req.queryParams("pid") : "";
		int organizacionId = (req.queryParams("organizacionid") != null) ? Integer.parseInt(req.queryParams("organizacionid")) : -1;
				
		//Calculo de PID
		String Pid = "";//CalcsUtil.generatePid();
		
		//Se comprueba que el PID es correcto
		if((pidRequest == null) || !pidRequest.equals(Pid)){
			jsonResult = JsonUtil.cursoAlternativo("REQUEST_NOT_ALLOWED");
		}
		//La peticion es correcta, se ejecuta
		else{
			try {
				Session sesion = conn.iniciaOperacion(); 
				List<Organizacion> organizaciones = sesion.createQuery("from Organizacion where id = '" + organizacionId + "'").list();
				
				if(organizaciones.size() > 0){
					Organizacion organizacion = organizaciones.get(0);
					
					//Lista de suscriptores
					List<App> listaApp = new ArrayList<App>();
					for(App app : organizacion.getApps()){
						listaApp.add(app);
					}
					
					jsonResult = AppToJson.parseApp(listaApp);
				}else{
					jsonResult = JsonUtil.cursoAlternativo("ORGANIZATION_NOT_EXIST");
				}
				conn.terminaOperacion();
			} catch (Exception e) {
				jsonResult = JsonUtil.cursoAlternativo("ACTION_NOT_ALLOWED");
			}
		}
		
		return jsonResult;
	}
}
