package services;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.json.JSONObject;

import database.Conexion;
import model.App;
import model.Channels;
import model.Organizacion;
import model.Suscriptores;
import modelToJson.ChannelsToJson;
import modelToJson.SuscriptoresToJson;
import spark.Request;
import utilities.CalcsUtil;
import utilities.JsonUtil;

public class SuscriptorService {
		
	// returns a list of all users
	  public JSONObject getAllSuscriptors(Conexion conn, Request req) { 
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
					List<Suscriptores> suscriptores = sesion.createQuery("from Suscriptores").list();
					jsonResult  = SuscriptoresToJson.parseSuscriptores(suscriptores);
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
		  String deviceId = (req.queryParams("deviceid") != null) ? req.queryParams("deviceid") : "";
		  int organizacionId = ((req.queryParams("organizacionid") != null) && (!req.queryParams("organizacionid").equals(""))) ? Integer.parseInt(req.queryParams("organizacionid")) : -1;
		  byte activo = ((req.queryParams("activo") != null) && (!req.queryParams("activo").equals(""))) ? (byte)Integer.parseInt(req.queryParams("activo")) : 0;
		  int appId = ((req.queryParams("appid") != null) && (!req.queryParams("appid").equals(""))) ? Integer.parseInt(req.queryParams("appid")) : -1;
		  
		  //Calculo de PID
		  String Pid = CalcsUtil.generatePid();
		  
		  //Se comprueba que se ha insertado el Id del Stb
		  if(deviceId.equals("")){
			  jsonResult = JsonUtil.cursoAlternativo("INTRODUCE_DEVICEID");
		  }
		  //Se comprueba que el PID utilizado para al peticion es correcto
		  else if(!pidRequest.equals(Pid)){
			  jsonResult = JsonUtil.cursoAlternativo("REQUEST_NOT_ALLOWED");
		  }
		  //La peticion es correcta, se ejecuta
		  else{
			  try {
				  Session sesion = conn.iniciaOperacion();
				  List<Suscriptores> suscriptores = sesion.createQuery("FROM Suscriptores WHERE idsuscriptor = '" + deviceId + "'").list();
				  if(suscriptores.size()>0){
					  jsonResult = JsonUtil.cursoAlternativo("STB_ALREDY_EXIST");
				  }else{
					  //Se obtiene la organizacion
					  List<Organizacion> listaOrganizacion = sesion.createQuery("FROM Organizacion WHERE id = " + organizacionId).list();
					  //Se comprueba que existe la organizacion donde se va a registrar el STB
					  if(listaOrganizacion.size() > 0){
						  //Se obtiene la app
						  List<App> listaApp = sesion.createQuery("FROM App WHERE idapp = " + appId).list();
						  if(listaApp.size() > 0){
							  //Se crea el nuevo suscriptor
							  Suscriptores nuevo_suscriptor = new Suscriptores(deviceId,listaApp.get(0),listaOrganizacion.get(0),activo);
							  /*listaApp.get(0).getSuscriptoreses().add(nuevo_suscriptor);
							  listaOrganizacion.get(0).getSuscriptoreses().add(nuevo_suscriptor);*/
							  //Se guarda el suscriptor
							  sesion.save(nuevo_suscriptor);
							  jsonResult  = JsonUtil.cursoNormal();
						  }else{
							//La organizacion no existe
							  jsonResult = JsonUtil.cursoAlternativo("APP_NOT_EXIST");
						  }
					  }else{
						  //La organizacion no existe
						  jsonResult = JsonUtil.cursoAlternativo("ORGANIZATION_NOT_EXIST");
					  }
				  }	
				  conn.terminaOperacion();
			  } catch (Exception e) {
				  jsonResult = JsonUtil.cursoAlternativo("ACTION_NOT_ALLOWED");
			  }
		  }
			
		  return jsonResult;
	  }

	  public Object baja(Conexion conn, Request req){
		  JSONObject jsonResult = null;
		  String pidRequest = (req.queryParams("pid") != null) ? req.queryParams("pid") : "";
		  String deviceId = (req.queryParams("deviceid") != null) ? req.queryParams("deviceid") : "";
		  
		  //Calculo de PID
		  String Pid = CalcsUtil.generatePid();
		  
		  //Se comprueba que se ha insertado el Id del Stb
		  if(deviceId.equals("")){
			  jsonResult = JsonUtil.cursoAlternativo("INTRODUCE_DEVICEID");
		  }
		  //Se comprueba que el PID utilizado para al peticion es correcto
		  else if(pidRequest.equals(Pid)){
			  jsonResult = JsonUtil.cursoAlternativo("REQUEST_NOT_ALLOWED");
		  }
		  //La peticion es correcta, se ejecuta
		  else{
			  try {
				  Session sesion = conn.iniciaOperacion();
				  List<Suscriptores> suscriptores = sesion.createQuery("FROM Suscriptores WHERE idsuscriptor = '" + deviceId + "'").list();
				  if(suscriptores.size() == 0){
					  jsonResult = JsonUtil.cursoAlternativo("STB_NOT_EXIST");
				  }else{
					  sesion.remove(suscriptores.get(0));
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
		  String deviceId = (req.queryParams("deviceid") != null) ? req.queryParams("deviceid") : "";
		  int organizacionId = ((req.queryParams("organizacionid") != null) && (!req.queryParams("organizacionid").equals(""))) ? Integer.parseInt(req.queryParams("organizacionid")) : -1;
		  byte activo = ((req.queryParams("activo") != null) && (!req.queryParams("activo").equals(""))) ? (byte)Integer.parseInt(req.queryParams("activo")) : 0;
		  int appId = ((req.queryParams("appid") != null) && (!req.queryParams("appid").equals(""))) ? Integer.parseInt(req.queryParams("appid")) : -1;
		  
		  //Calculo de PID
		  String Pid = CalcsUtil.generatePid();
		  
		  //Se comprueba que se ha insertado el Id del Stb
		  if(!pidRequest.equals(Pid)){
			  jsonResult = JsonUtil.cursoAlternativo("REQUEST_NOT_ALLOWED");
		  }
		  //La peticion es correcta, se ejecuta
		  else{
			  try {
				  Session sesion = conn.iniciaOperacion();
				  List<Suscriptores> suscriptores = sesion.createQuery("FROM Suscriptores WHERE idsuscriptor = '" + deviceId + "'").list();
				  if(suscriptores.size()>0){
					  
					  Suscriptores suscriptor = suscriptores.get(0);
										  
					  //Se obtiene la organizacion
					  List<Organizacion> organizacion = sesion.createQuery("FROM Organizacion WHERE id = " + organizacionId).list();
					  
					  //Se comprueba que existe la organizacion donde se va a registrar el STB
					  if(organizacion.size() > 0){
						  //Se actualiza la organizacion a la que pertenece
						  suscriptor.setOrganizacion(organizacion.get(0));
					  }
					  
					  //Se obtiene la aplicacion
					  List<App> listapp = sesion.createQuery("FROM App WHERE id = " + appId).list();
					  //Se comprueba que existe la organizacion donde se va a registrar el STB
					  if(listapp.size() > 0){
						  //Se actualiza la aplicacion a la que pertenece
						  suscriptor.setApp(listapp.get(0));
					  }
					  
					  //Se actualiza el estado del dispositivo
					  suscriptor.setActivo(activo);
					  
					  //Se guarda el suscriptor
					  sesion.update(suscriptor);
					  
					  jsonResult  = JsonUtil.cursoNormal();
					  
				  }else{
					  jsonResult = JsonUtil.cursoAlternativo("STB_NOT_EXIST");
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

			String deviceId = (req.queryParams("deviceid") != null) ? req.queryParams("deviceid") : "";
			
			//Calculo de PID
			String Pid = CalcsUtil.generatePid();
			  
			if(!pidRequest.equals(Pid)){
				jsonResult = JsonUtil.cursoAlternativo("REQUEST_NOT_ALLOWED");
			}else if(deviceId.equals("")){
				jsonResult = JsonUtil.cursoAlternativo("INTRODUCE_DEVICEID");
			}
			//La peticion es correcta, se ejecuta
			else{
				try {
					Session sesion = conn.iniciaOperacion();
					List<Suscriptores> suscriptores = sesion.createQuery("FROM Suscriptores WHERE idsuscriptor = '" + deviceId + "'").list();
					  if(!(suscriptores.size() > 0)){
						  jsonResult = JsonUtil.cursoAlternativo("DEVICE_NOT_EXIST");
					  }else{
						  jsonResult  = SuscriptoresToJson.parseSuscriptores(suscriptores);
					  }
					conn.terminaOperacion();
				} catch (Exception e) {
					jsonResult = JsonUtil.cursoAlternativo("ACTION_NOT_ALLOWED");
				}
			}
				
			  return jsonResult;
		}
	  
	  public JSONObject getSuscriptorsOrganization(Conexion conn, Request req) { 
		  JSONObject jsonResult = null;
			
		  String pidRequest = (req.queryParams("pid") != null) ? req.queryParams("pid") : "";
		  int organizacionId = ((req.queryParams("organizacionid") != null) && (!req.queryParams("organizacionid").equals(""))) ? Integer.parseInt(req.queryParams("organizacionid")) : -1;
			
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
				  List<Organizacion> organizaciones = sesion.createQuery("from Organizacion where id = '" + organizacionId + "'").list();
					
				  if(organizaciones.size() > 0){
					  Organizacion organizacion = organizaciones.get(0);
						
					  //Lista de suscriptores
					  List<Suscriptores> listaSuscriptores = new ArrayList<Suscriptores>();
					  for(Suscriptores suscriptor : organizacion.getSuscriptoreses()){
						  listaSuscriptores.add(suscriptor);
					  }
						
					  jsonResult = SuscriptoresToJson.parseSuscriptores(listaSuscriptores);
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
	  
	  public Object active(Conexion conn, Request req){
		  JSONObject jsonResult = null;
		  String pidRequest = (req.queryParams("pid") != null) ? req.queryParams("pid") : "";
		  String deviceId = (req.queryParams("deviceid") != null) ? req.queryParams("deviceid") : "";
		  
		  //Calculo de PID
		  String Pid = CalcsUtil.generatePid();
		  
		  //Se comprueba que se ha insertado el Id del Stb
		  if(deviceId.equals("")){
			  jsonResult = JsonUtil.cursoAlternativo("INTRODUCE_DEVICEID");
		  }
		  //Se comprueba que el PID utilizado para al peticion es correcto
		  else if((pidRequest == null) || !pidRequest.equals(Pid)){
			  jsonResult = JsonUtil.cursoAlternativo("REQUEST_NOT_ALLOWED");
		  }
		  //La peticion es correcta, se ejecuta
		  else{
			  try {
				  Session sesion = conn.iniciaOperacion();
				  List<Suscriptores> suscriptores = sesion.createQuery("FROM Suscriptores WHERE idsuscriptor = '" + deviceId + "'").list();
				  if(!(suscriptores.size()>0)){
					  jsonResult = JsonUtil.cursoAlternativo("STB_NOT_EXIST");
				  }else{
					  if((suscriptores.get(0)).getActivo() == 0){
						  jsonResult = JsonUtil.cursoAlternativo("STB_NOT_ACTIVE");
					  }else{
						  jsonResult = JsonUtil.cursoNormal("STB_ACTIVE");
					  }
				  }
				  conn.terminaOperacion();
			  } catch (Exception e) {
				  jsonResult = JsonUtil.cursoAlternativo("ACTION_NOT_ALLOWED");
				  }
		 }
		 return jsonResult;
	 }
}
