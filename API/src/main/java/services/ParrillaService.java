package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.json.JSONObject;

import database.Conexion;
import model.Channels;
import model.Organizacion;
import model.Paquetes;
import model.Parrilla;
import modelToJson.ChannelsToJson;
import modelToJson.PaquetesToJson;
import modelToJson.ParrillaToJson;
import spark.Request;
import utilities.CalcsUtil;
import utilities.JsonUtil;

public class ParrillaService {
	
	public Object baja(Conexion conn, Request req) {
		
		JSONObject jsonResult = new JSONObject();
		String pidRequest = (req.queryParams("pid") != null) ? req.queryParams("pid") : "";
		int idparrilla = (req.queryParams("parrillaid") != null) ? Integer.parseInt(req.queryParams("parrillaid")) : -1;
		  
		//Calculo de PID
		String Pid = "";//CalcsUtil.generatePid();
		  
		//Se comprueba que se ha insertado el Id del Stb
		if(idparrilla == -1){
			jsonResult = JsonUtil.cursoAlternativo("INTRODUCE_PARRILLAID");
		}
		//Se comprueba que el PID utilizado para al peticion es correcto
		else if(!pidRequest.equals(Pid)){
			jsonResult = JsonUtil.cursoAlternativo("REQUEST_NOT_ALLOWED");
		}
		//La peticion es correcta, se ejecuta
		else{
			try {
				Session sesion = conn.iniciaOperacion();
				List<Parrilla> listaParrilla = sesion.createQuery("FROM Parrilla WHERE idparrilla = '" + idparrilla + "'").list();
				if(listaParrilla.size() == 0){
					jsonResult = JsonUtil.cursoAlternativo("PARRILLA_NOT_EXIST");
				}else{
					Parrilla parrilla = listaParrilla.get(0);
					
					//Se borran los canales asociados a la parrilla
					Set<Channels> setCanales = parrilla.getChannelses();
					
					//Se recorren todos los canales asociado y se eliminan la parrilla
					for(Channels canal : setCanales){
						canal.getParrillas().remove(parrilla);
					}
					
					//Se recorren todos los paquetes asociados y se eleminan de la parrilla
					parrilla.setPaqueteses(null);
					
					sesion.update(parrilla);
					
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
		JSONObject jsonResult = new JSONObject();
		String pidRequest = (req.queryParams("pid") != null) ? req.queryParams("pid") : "";
		int idparrilla = (req.queryParams("parrillaid") != null) ? Integer.parseInt(req.queryParams("parrillaid")) : -1;
		String channelsesRequest = (req.queryParams("channelses") != null) ? req.queryParams("channelses") : "";
		String paquetesesRequest = (req.queryParams("paqueteses") != null) ? req.queryParams("paqueteses") : "";
		  
		//Calculo de PID
		String Pid = CalcsUtil.generatePid();
		  
		//Se comprueba que se ha insertado el Id del Stb
		if(idparrilla == -1){
			jsonResult = JsonUtil.cursoAlternativo("INTRODUCE_PARRILLAID");
		}
		//Se comprueba que el PID utilizado para al peticion es correcto
		else if(!pidRequest.equals(Pid)){
			jsonResult = JsonUtil.cursoAlternativo("REQUEST_NOT_ALLOWED");
		}
		//La peticion es correcta, se ejecuta
		else{
			try {
				Session sesion = conn.iniciaOperacion();
				List<Parrilla> listaParrilla = sesion.createQuery("FROM Parrilla WHERE idparrilla = '" + idparrilla + "'").list();
				if(listaParrilla.size() == 0){
					jsonResult = JsonUtil.cursoAlternativo("PARRILLA_NOT_EXIST");
				}else{
					Parrilla parrilla = listaParrilla.get(0);
					
					//Actualizar canales de la parrilla
					if(!channelsesRequest.equals("")){
						
						Set<Channels> canalesAnteriores = parrilla.getChannelses();
						
						Iterator<Channels> itr = canalesAnteriores.iterator();
						
						while (itr.hasNext())
						{
							if(!Arrays.asList(canalesAnteriores).contains(parrilla.getIdparrilla().toString())){
								Channels canal = itr.next();
								canal.getParrillas().remove(parrilla);
							}
						}
						
						//Se comprueba si va a pertencer a algun paquete el canal o no
						String[] listaCanalesId = channelsesRequest.split(";");
						
						for(String canalId : listaCanalesId){
							
							List<Channels> listaCanales = sesion.createQuery("FROM Channels WHERE idchannels = '" + canalId + "'").list();
							
							if(listaCanales.size() > 0){
								//Se actualizan los canales asociados a la parrilla
								Set<Parrilla> setCanalesParrilla = listaCanales.get(0).getParrillas();
								
								//Se comprueba si el canal contiene a la parrilla
								if(!setCanalesParrilla.contains(parrilla)){
									setCanalesParrilla.add(parrilla);
								}
							}
						}
					}else{
						Set<Channels> canalesAnteriores = parrilla.getChannelses();
						
						Iterator<Channels> itr = canalesAnteriores.iterator();
						
						while (itr.hasNext())
						{
							Channels canal = itr.next();
							canal.getParrillas().remove(parrilla);
						}
					}
					
					//Actualizar paquetes de la parrilla
					if(!paquetesesRequest.equals("")){
						//Se borran todos los paquetes asociados
						parrilla.setPaqueteses(null);
						
						Set<Paquetes> setPaquetes = new HashSet<Paquetes>();
						
						//Se comprueba si va a pertencer a algun paquete el canal o no
						String[] listaPaqueteId = paquetesesRequest.split(";");
						
						for(String paqueteId : listaPaqueteId){
							
							List<Paquetes> listaPaquete = sesion.createQuery("FROM Paquetes WHERE idpaquete = '" + paqueteId + "'").list();
							
							if(listaPaquete.size() > 0){
								//Se actualizan los paquetes asociados a la parrilla
								setPaquetes.add(listaPaquete.get(0));
							}
						}
						parrilla.setPaqueteses(setPaquetes);
					}else{
						parrilla.setPaqueteses(null);
					}
						
					sesion.update(parrilla);
					
					jsonResult  = JsonUtil.cursoNormal();
				}
				conn.terminaOperacion();
			} catch (Exception e) {
				jsonResult = JsonUtil.cursoAlternativo("ACTION_NOT_ALLOWED");
			}
		 }
		 return jsonResult;
	}
	
	public Object obtener(Conexion conn, Request req) {
		JSONObject jsonResult = new JSONObject();
		String pidRequest = (req.queryParams("pid") != null) ? req.queryParams("pid") : "";
		int idorganizacion = ((req.queryParams("organizacionid") != null) && (!req.queryParams("organizacionid").equals(""))) ? Integer.parseInt(req.queryParams("organizacionid")) : -1;
		  
		//Calculo de PID
		String Pid = CalcsUtil.generatePid();
		  
		//Se comprueba que se ha insertado el Id del Stb
		if(idorganizacion == -1){
			jsonResult = JsonUtil.cursoAlternativo("INTRODUCE_ORGANIZATIONID");
		}
		//Se comprueba que el PID utilizado para al peticion es correcto
		else if(!pidRequest.equals(Pid)){
			jsonResult = JsonUtil.cursoAlternativo("REQUEST_NOT_ALLOWED");
		}
		//La peticion es correcta, se ejecuta
		else{
			try {
				Session sesion = conn.iniciaOperacion();
				List<Organizacion> listaOrganizacion = sesion.createQuery("FROM Organizacion WHERE id = '" + idorganizacion + "'").list();
				if(listaOrganizacion.size() == 0){
					jsonResult = JsonUtil.cursoAlternativo("INTRODUCE_ORGANIZATIONID");
				}else{
					//Se obtiene la parrilla de la organizacion
					Parrilla parrilla = listaOrganizacion.get(0).getParrilla();
					jsonResult = ParrillaToJson.parseParrilla(parrilla);
				}
				conn.terminaOperacion();
			} catch (Exception e) {
				jsonResult = JsonUtil.cursoAlternativo("ACTION_NOT_ALLOWED");
			}
		 }
		 return jsonResult;
	}
}
