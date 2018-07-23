package services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.json.JSONObject;

import database.Conexion;
import model.Channels;
import model.Epg;
import model.Organizacion;
import model.Paquetes;
import model.Suscriptores;
import modelToJson.ChannelsToJson;
import modelToJson.EpgToJson;
import modelToJson.OrganizacionToJson;
import spark.Request;
import utilities.CalcsUtil;
import utilities.JsonUtil;

public class ChannelService {
	
	//Obtener todos los canales de la empresa.
	public Object getAllChannel(Conexion conn, Request req) {
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
				List<Channels> channels = sesion.createQuery("from Channels").list();
				jsonResult  = ChannelsToJson.parseChannel(channels);
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
		  int logicalChannelNumber = ((req.queryParams("logicalChannelNumber") != null) && (!req.queryParams("logicalChannelNumber").equals(""))) ? Integer.parseInt(req.queryParams("logicalChannelNumber")) : -1;
		  String url = (req.queryParams("url") != null) ? req.queryParams("url") : "";
		  String name = (req.queryParams("name") != null) ? req.queryParams("name") : "";
		  String description = (req.queryParams("description") != null) ? req.queryParams("description") : "";
		  String aspectRatio = (req.queryParams("aspectRatio") != null) ? req.queryParams("aspectRatio") : "";
		  String quality = (req.queryParams("quality") != null) ? req.queryParams("quality") : "";
		  String visibility = (req.queryParams("visibility") != null) ? req.queryParams("visibility") : "";
		  String smallIcon = (req.queryParams("smallIcon") != null) ? req.queryParams("smallIcon") : "";
		  String mediumIcon = (req.queryParams("mediumIcon") != null) ? req.queryParams("mediumIcon") : "";
		  String largeIcon = (req.queryParams("largeIcon") != null) ? req.queryParams("largeIcon") : "";
		  String paqueteses = (req.queryParams("paqueteses") != null) ? req.queryParams("paqueteses") : "";

		  //Calculo de PID
		  String Pid = CalcsUtil.generatePid();
		  
		  if(!pidRequest.equals(Pid)){
			  jsonResult = JsonUtil.cursoAlternativo("REQUEST_NOT_ALLOWED");
		  }
		  //La peticion es correcta, se ejecuta
		  else{
			  try {
				  Session sesion = conn.iniciaOperacion();
				  
				  Channels canal = new Channels(); 
				  	
				  //Se introducen los atributos en caso de se indicados mediante la peticion
				  if(logicalChannelNumber != -1){
					  canal.setLogicalChannelNumber(logicalChannelNumber);
				  }
				  
				  if(!url.equals("")){
					  canal.setUrl(url);
				  }

				  if(!name.equals("")){
					  canal.setName(name);
				  }
				  
				  if(!quality.equals("")){
					  canal.setQuality(quality);
				  }
				  
				  if(!visibility.equals("")){
					  canal.setVisibility(visibility);
				  }

				  if(!description.equals("")){
					  canal.setDescription(description);
				  }
				  
				  if(!aspectRatio.equals("")){
					  canal.setAspectRatio(aspectRatio);
				  }
				  
				  if(!smallIcon.equals("")){
					  canal.setSmallIcon(smallIcon);
				  }
					
				  if(!mediumIcon.equals("")){
					  canal.setMediumIcon(mediumIcon);
				  }
					
				  if(!largeIcon.equals("")){
					  canal.setLargeIcon(largeIcon);
				  }
				  
				  if(!paqueteses.equals("")){
					  //Se comprueba si va a pertencer a algun paquete el canal o no
					  String[] paquetes = paqueteses.split(";");
					  Set setPaquetes = new HashSet();
						
					  //Recorrer los paquetes
					  for(String idPaquete : paquetes){
						  List<Paquetes> listPaquetes = sesion.createQuery("from Paquetes where idpaquete = '" + idPaquete + "'").list();
													
						  //Se verifica que la organizacion existe en el sistema
						  if(listPaquetes.size() > 0){
							  Paquetes paquete = listPaquetes.get(0);
							  //Se añade el canal al paquete
							  paquete.getChannelses().add(canal);
						  }
					  }
				  }
				  
				  sesion.save(canal);
				  
				  jsonResult = JsonUtil.cursoNormal();
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
		  int idchannels = ((req.queryParams("channelid") != null) && (!req.queryParams("channelid").equals(""))) ? Integer.parseInt(req.queryParams("channelid")) : -1;
		  
		  //Calculo de PID
		  String Pid = CalcsUtil.generatePid();
		  
		  //Se comprueba que se ha insertado el Id del canal
		  if(idchannels == -1){
			  jsonResult = JsonUtil.cursoAlternativo("INTRODUCE_CHANNELID");
		  }
		  //Se comprueba que el PID utilizado para al peticion es correcto
		  else if(!pidRequest.equals(Pid)){
			  jsonResult = JsonUtil.cursoAlternativo("REQUEST_NOT_ALLOWED");
		  }
		  //La peticion es correcta, se ejecuta
		  else{
			  try {
				  Session sesion = conn.iniciaOperacion();
				  List<Suscriptores> channels = sesion.createQuery("FROM Channels WHERE idchannels = '" + idchannels + "'").list();
				  if(channels.size() == 0){
					  jsonResult = JsonUtil.cursoAlternativo("CHANNEL_NOT_EXIST");
				  }else{
					  sesion.remove(channels.get(0));
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
		int logicalChannelNumber = ((req.queryParams("logicalChannelNumber") != null) && (!req.queryParams("logicalChannelNumber").equals(""))) ? Integer.parseInt(req.queryParams("logicalChannelNumber")) : -1;
		String url = (req.queryParams("url") != null) ? req.queryParams("url") : "";
		String name = (req.queryParams("name") != null) ? req.queryParams("name") : "";
		String description = (req.queryParams("description") != null) ? req.queryParams("description") : "";
		String aspectRatio = (req.queryParams("aspectRatio") != null) ? req.queryParams("aspectRatio") : "";
		String quality = (req.queryParams("quality") != null) ? req.queryParams("quality") : "";
		String visibility = (req.queryParams("visibility") != null) ? req.queryParams("visibility") : "";
		String smallIcon = (req.queryParams("smallIcon") != null) ? req.queryParams("smallIcon") : "";
		String mediumIcon = (req.queryParams("mediumIcon") != null) ? req.queryParams("mediumIcon") : "";
		String largeIcon = (req.queryParams("largeIcon") != null) ? req.queryParams("largeIcon") : "";
		String paqueteses = (req.queryParams("paqueteses") != null) ? req.queryParams("paqueteses") : "";
		String prueba = req.queryParams("channelid");
		int idchannels = ((req.queryParams("channelid") != null) && (!req.queryParams("channelid").equals(""))) ? Integer.parseInt(req.queryParams("channelid")) : -1;
		    
		//Calculo de PID
		String Pid = CalcsUtil.generatePid();
		  
		//Se comprueba que se ha insertado el Id del Stb
		if(idchannels == -1){
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
				List<Channels> listaChannels = sesion.createQuery("FROM Channels WHERE idchannels = '" + idchannels + "'").list();
				if(listaChannels.size()>0){
					  
					Channels channel = listaChannels.get(0);
					  
					//Se introducen los atributos en caso de se indicados mediante la peticion
					if(logicalChannelNumber != -1){
						channel.setLogicalChannelNumber(logicalChannelNumber);
					}
					  
					if(!url.equals("")){
						channel.setUrl(url);
					}

					if(!name.equals("")){
						channel.setName(name);
					}
					  
					if(!quality.equals("")){
						channel.setQuality(quality);
					}
					  
					if(!visibility.equals("")){
						channel.setVisibility(visibility);
					}

					if(!description.equals("")){
						channel.setDescription(description);
					}
					  
					if(!aspectRatio.equals("")){
						channel.setAspectRatio(aspectRatio);
					}
					  
					if(!smallIcon.equals("")){
						channel.setSmallIcon(smallIcon);
					}
						
					if(!mediumIcon.equals("")){
						channel.setMediumIcon(mediumIcon);
					}
						
					if(!largeIcon.equals("")){
						channel.setLargeIcon(largeIcon);
					}
					  
					if(!paqueteses.equals("")){
						//Se comprueba si va a pertencer a algun paquete el canal o no
						String[] paquetes = paqueteses.split(";");
						Set setPaquetes = new HashSet();
							
						//Recorrer los paquetes
						for(String idPaquete : paquetes){
							List<Paquetes> listPaquetes = sesion.createQuery("from Paquetes where idpaquete = '" + idPaquete + "'").list();
														
							//Se verifica que la organizacion existe en el sistema
							if(listPaquetes.size() > 0){
								Paquetes paquete = listPaquetes.get(0);
								//Se añade el canal al paquete
								paquete.getChannelses().add(channel);
							}
						}
					}
					  
					  
					//Se guarda el suscriptor
					sesion.update(channel);
					  
					jsonResult  = JsonUtil.cursoNormal();
					  
				}else{
					jsonResult = JsonUtil.cursoAlternativo("CHANNEL_NOT_EXIST");
				}	
				conn.terminaOperacion();
			}catch (Exception e) {
				jsonResult = JsonUtil.cursoAlternativo("ACTION_NOT_ALLOWED");
			}
		}
			
		return jsonResult;
	}
	
	public Object obtener(Conexion conn, Request req) {
		JSONObject jsonResult = null;
		String pidRequest = (req.queryParams("pid") != null) ? req.queryParams("pid") : "";
		int channelid = ((req.queryParams("channelid") != null) && (!req.queryParams("channelid").equals(""))) ? Integer.parseInt(req.queryParams("channelid")) : -1;
		
		
		//Calculo de PID
		String Pid = CalcsUtil.generatePid();
		  
		if(!pidRequest.equals(Pid)){
			jsonResult = JsonUtil.cursoAlternativo("REQUEST_NOT_ALLOWED");
		}else if(channelid == -1l){
			jsonResult = JsonUtil.cursoAlternativo("INTRODUCE_CHANNELID");
		}
		//La peticion es correcta, se ejecuta
		else{
			try {
				Session sesion = conn.iniciaOperacion();
				List<Channels> channels = sesion.createQuery("FROM Channels WHERE id = '" + channelid + "'").list();
				  if(!(channels.size() > 0)){
					  jsonResult = JsonUtil.cursoAlternativo("CHANNEL_NOT_EXIST");
				  }else{
					  jsonResult  = ChannelsToJson.parseChannel(channels);
				  }
				conn.terminaOperacion();
			} catch (Exception e) {
				jsonResult = JsonUtil.cursoAlternativo("ACTION_NOT_ALLOWED");
			}
		}
			
		  return jsonResult;
	}
	
	public Object obtenerEpg(Conexion conn, Request req) {
		  
		JSONObject jsonResult = null;
		String pidRequest = (req.queryParams("pid") != null) ? req.queryParams("pid") : "";
		int idchannels = ((req.queryParams("channelid") != null) && (!req.queryParams("channelid").equals(""))) ? Integer.parseInt(req.queryParams("idchannels")) : -1;
		    
		//Calculo de PID
		String Pid = CalcsUtil.generatePid();
		  
		//Se comprueba que se ha insertado el Id del Stb
		if(idchannels == -1){
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
				List<Channels> listaChannels = sesion.createQuery("FROM Channels WHERE idchannels = '" + idchannels + "'").list();
				if(listaChannels.size()>0){
					  
					//Se obtiene la epg del canal
					Set<Epg> setEpg = listaChannels.get(0).getEpgs();
					
					//Se introduce en una lista las epgs del canal
					List<Epg> listaEpg = new ArrayList<Epg>();
					
					for(Epg epg : setEpg){
						listaEpg.add(epg);
					}
					
					jsonResult  = EpgToJson.parseEpg(listaEpg);
					  
				}else{
					jsonResult = JsonUtil.cursoAlternativo("CHANNEL_NOT_EXIST");
				}	
				conn.terminaOperacion();
			}catch (Exception e) {
				jsonResult = JsonUtil.cursoAlternativo("ACTION_NOT_ALLOWED");
			}
		}
			
		return jsonResult;
	}
	
}
