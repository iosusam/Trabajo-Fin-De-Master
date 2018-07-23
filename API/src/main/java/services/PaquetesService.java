package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.json.JSONObject;

import database.Conexion;
import model.Channels;
import model.Paquetes;
import modelToJson.ChannelsToJson;
import modelToJson.PaquetesToJson;
import spark.Request;
import utilities.CalcsUtil;
import utilities.JsonUtil;

public class PaquetesService {
	public Object getAllPaquetes(Conexion conn, Request req) {
		
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
				List<Paquetes> paquetes = sesion.createQuery("from Paquetes").list();
				jsonResult  = PaquetesToJson.parsePaquetes(paquetes);
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
		String descripcion = (req.queryParams("descripcion") != null) ? req.queryParams("descripcion") : "";
		String channelses = (req.queryParams("channelses") != null) ? req.queryParams("channelses") : "";
		String parrillas = (req.queryParams("parrillas") != null) ? req.queryParams("parrillas") : "";
		
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
				Paquetes paquete = new Paquetes(nombre,descripcion);
				
				//Se introducen los canales a los que pertenece el paquete
				if(!channelses.equals("")){
					//Se comprueba si va a pertencer a algun paquete el canal o no
					String[] listaChannelsId = channelses.split(";");
					
					for(String channelId : listaChannelsId){
						List<Channels> listaCanales = sesion.createQuery("FROM Channels WHERE idchannels = '" + channelId + "'").list();
						
						if(listaCanales.size() > 0){
							paquete.getChannelses().add(listaCanales.get(0));
						}
					}
				}
				
				//Se introducen los canales a los que pertenece el paquete
				if(!parrillas.equals("")){
					//Se comprueba si va a pertencer a algun paquete el canal o no
					String[] listaParrillasId = parrillas.split(";");
					
					for(String paqueteId : listaParrillasId){
						List<Channels> listaPaquete = sesion.createQuery("FROM Channels WHERE idchannels = '" + paqueteId + "'").list();
						
						if(listaPaquete.size() > 0){
							listaPaquete.get(0).getPaqueteses().add(paquete);
						}
					}
				}
				
				sesion.save(paquete);
				
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
		int idPaquete = ((req.queryParams("paqueteid") != null) && (!req.queryParams("paqueteid").equals(""))) ? Integer.parseInt(req.queryParams("paqueteid")) : -1;
		
		
		//Calculo de PID
		String Pid = CalcsUtil.generatePid();
		
		//Se comprueba que el PID es correcto
		if(!pidRequest.equals(Pid)){
			jsonResult = JsonUtil.cursoAlternativo("REQUEST_NOT_ALLOWED");
		}
		//Se comprueba que se ha insertado el Id del Stb
		else if(idPaquete == -1){
			jsonResult = JsonUtil.cursoAlternativo("INTRODUCE_PAQUETEID");
		}
		//La peticion es correcta, se ejecuta
		else{
			try {
				Session sesion = conn.iniciaOperacion();
				List<Paquetes> paquetes = sesion.createQuery("FROM Paquetes WHERE idpaquete = '" + idPaquete + "'").list();
				if(paquetes.size() == 0){
					jsonResult = JsonUtil.cursoAlternativo("PAQUETE_NOT_EXIST");
				}else{
					sesion.remove(paquetes.get(0));
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
		String nombre = (req.queryParams("nombre") != null) ? req.queryParams("nombre") : "";
		String descripcion = (req.queryParams("descripcion") != null) ? req.queryParams("descripcion") : "";
		String channelses = (req.queryParams("channelses") != null) ? req.queryParams("channelses") : "";
		String parrillas = (req.queryParams("parrillas") != null) ? req.queryParams("parrillas") : "";
		int idPaquete = ((req.queryParams("paqueteid") != null) && (!req.queryParams("paqueteid").equals(""))) ? Integer.parseInt(req.queryParams("paqueteid")) : -1;
		
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
				List<Paquetes> paquetes = sesion.createQuery("FROM Paquetes WHERE idpaquete = '" + idPaquete + "'").list();
				
				if(paquetes.size()>0){
					
					 Paquetes paquete = paquetes.get(0);
					 
					 //Se actualiza el nombre del paquete
					 if(!nombre.equals("")){
						 paquete.setNombre(nombre);
					 }
					 //Se actualiza la descripcion del paquete
					 if(!descripcion.equals("")){
						 paquete.setDescripcion(descripcion);
					 }
					 
					//Se introducen los canales a los que pertenece el paquete
					if(!channelses.equals("")){
						//Se comprueba si va a pertencer a algun paquete el canal o no
						String[] listaChannelsId = channelses.split(";");
						
						//Canales que pertenecen al paquete
						Set<Channels> canalesAnteriores = paquete.getChannelses();
						
						Iterator<Channels> itr = canalesAnteriores.iterator();
						
						while (itr.hasNext())
						{
							Channels canal =  itr.next();
							if(!Arrays.asList(listaChannelsId).contains(canal.getIdchannels().toString())){
								List<Channels> listaCanales = sesion.createQuery("FROM Channels WHERE idchannels = '" + canal.getIdchannels() + "'").list();
								
								if(listaCanales.size() > 0){
									itr.remove();
								}
							}      
						}
						
						for(String channelId : listaChannelsId){
							//Se comprueba si estaba ya asigando el canal al paquete
							List<Channels> listaCanales = sesion.createQuery("FROM Channels WHERE idchannels = '" + channelId + "'").list();
							
							if(listaCanales.size() > 0){
								paquete.getChannelses().add(listaCanales.get(0));
							}
						}
						paquete.setChannelses(canalesAnteriores);
					}else{
						paquete.setChannelses(null);
					}
					
					//Se introducen los canales a los que pertenece el paquete
					if(!parrillas.equals("")){
						//Se comprueba si va a pertencer a algun paquete el canal o no
						String[] listaParrillasId = parrillas.split(";");
						
						for(String paqueteId : listaParrillasId){
							List<Channels> listaPaquete = sesion.createQuery("FROM Channels WHERE idchannels = '" + paqueteId + "'").list();
							
							if(listaPaquete.size() > 0){
								listaPaquete.get(0).getPaqueteses().add(paquete);
							}
						}
					}
					
					jsonResult  = JsonUtil.cursoNormal();
					
					sesion.save(paquete);
					
				}else{
					jsonResult = JsonUtil.cursoAlternativo("PAQUETE_NOT_EXIST");
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
		int paqueteid = ((req.queryParams("paqueteid") != null) && (!req.queryParams("paqueteid").equals(""))) ? Integer.parseInt(req.queryParams("paqueteid")) : -1;
		
		
		//Calculo de PID
		String Pid = CalcsUtil.generatePid();
		  
		if(!pidRequest.equals(Pid)){
			jsonResult = JsonUtil.cursoAlternativo("REQUEST_NOT_ALLOWED");
		}else if(paqueteid == -1){
			jsonResult = JsonUtil.cursoAlternativo("INTRODUCE_CHANNELID");
		}
		//La peticion es correcta, se ejecuta
		else{
			try {
				Session sesion = conn.iniciaOperacion();
				List<Paquetes> paquetes = sesion.createQuery("FROM Paquetes WHERE idpaquete = '" + paqueteid + "'").list();
				  if(!(paquetes.size() > 0)){
					  jsonResult = JsonUtil.cursoAlternativo("PAQUETE_NOT_EXIST");
				  }else{
					  jsonResult  = PaquetesToJson.parsePaquetes(paquetes);
				  }
				conn.terminaOperacion();
			} catch (Exception e) {
				jsonResult = JsonUtil.cursoAlternativo("ACTION_NOT_ALLOWED");
			}
		}
			
		  return jsonResult;
	}
	
	public Object obtenerCanales(Conexion conn, Request req) {
		  
		JSONObject jsonResult = null;
		String pidRequest = (req.queryParams("pid") != null) ? req.queryParams("pid") : "";
		int paqueteid = ((req.queryParams("paqueteid") != null) && (!req.queryParams("paqueteid").equals(""))) ? Integer.parseInt(req.queryParams("paqueteid")) : -1;
		    
		//Calculo de PID
		String Pid = CalcsUtil.generatePid();
		  
		//Se comprueba que se ha insertado el Id del Stb
		if(paqueteid == -1){
			jsonResult = JsonUtil.cursoAlternativo("INTRODUCE_CHANNELID");
		}
		//Se comprueba que se ha insertado el Id del Stb
		else if(!pidRequest.equals(Pid)){
			jsonResult = JsonUtil.cursoAlternativo("REQUEST_NOT_ALLOWED");
		}
		//La peticion es correcta, se ejecuta
		else{
			try {
				Session sesion = conn.iniciaOperacion();
				List<Paquetes> listaPaquetes = sesion.createQuery("FROM Paquetes WHERE idpaquete = '" + paqueteid + "'").list();
				if(listaPaquetes.size()>0){
					  
					//Se obtiene la epg del canal
					Set<Channels> setChannels = listaPaquetes.get(0).getChannelses();
					
					//Se introduce en una lista las epgs del canal
					List<Channels> listaChannels = new ArrayList<Channels>();
					
					for(Channels channel : setChannels){
						listaChannels.add(channel);
					}
					
					jsonResult  = ChannelsToJson.parseChannel(listaChannels);
					  
				}else{
					jsonResult = JsonUtil.cursoAlternativo("PAQUETE_NOT_EXIST");
				}	
				conn.terminaOperacion();
			}catch (Exception e) {
				jsonResult = JsonUtil.cursoAlternativo("ACTION_NOT_ALLOWED");
			}
		}
			
		return jsonResult;
	}
}
