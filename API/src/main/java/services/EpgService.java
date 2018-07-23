package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.hibernate.Session;
import org.json.JSONObject;

import database.Conexion;
import model.Channels;
import model.Epg;
import modelToJson.EpgToJson;
import spark.Request;
import utilities.JsonUtil;

public class EpgService {
	public Object getAllEpg(Conexion conn, Request req) {
		
		JSONObject jsonResult = null;
		
		String pidRequest = (req.queryParams("pid") != null) ? req.queryParams("pid") : "";
		
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
				List<Epg> epg = sesion.createQuery("from Epg").list();
				jsonResult  = EpgToJson.parseEpg(epg);
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
		int channelId = (req.queryParams("channels") != null) ? Integer.parseInt(req.queryParams("channels")) : -1;
		String nombrePrograma = (req.queryParams("nombrePrograma") != null) ? req.queryParams("nombrePrograma") : "";
		String categoria = (req.queryParams("categoria") != null) ? req.queryParams("categoria") : "";
		int edadMinima = (req.queryParams("edadMinima") != null) ? Integer.parseInt(req.queryParams("edadMinima")) : 0;
		Date fechaInicio = null;
		try {
			fechaInicio = (req.queryParams("fechaInicio") != null) ? new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(req.queryParams("fechaInicio")) : null;
		} catch (ParseException e1) {
			jsonResult = JsonUtil.cursoAlternativo("DATE_INCORRECT_FORMAT");
		}
		
		//Calculo de PID
		String Pid = "";//CalcsUtil.generatePid();
		
		//Se comprueba que se ha insertado el Id del Stb
		if(channelId == -1){
			jsonResult = JsonUtil.cursoAlternativo("INTRODUCE_CHANNELID");
		}
		//Se comprueba que el PID es correcto
		else if(!pidRequest.equals(Pid)){
			jsonResult = JsonUtil.cursoAlternativo("REQUEST_NOT_ALLOWED");
		}
		//La peticion es correcta, se ejecuta
		else{
			try {
				Session sesion = conn.iniciaOperacion(); 
				
				List<Channels> listaCanales = sesion.createQuery("FROM Channels WHERE idchannel = '" + channelId + "'").list();
				if(listaCanales.size() == 0){
					jsonResult = JsonUtil.cursoAlternativo("CHANNEL_NOT_EXIST");
				}else{
					//Se crea la egp del canal
					if(fechaInicio != null){
						Epg epgCanal = new Epg(listaCanales.get(0),nombrePrograma,categoria,edadMinima,fechaInicio);
						sesion.save(epgCanal);
					}else{
						jsonResult = JsonUtil.cursoAlternativo("DATE_INCORRECT_FORMAT");
					}
				}
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
		int epgId = (req.queryParams("epgid") != null) ? Integer.parseInt(req.queryParams("epgid")) : -1;
		
		
		//Calculo de PID
		String Pid = "";//CalcsUtil.generatePid();
		
		//Se comprueba que se ha insertado el Id del Stb
		if(epgId == -1){
			jsonResult = JsonUtil.cursoAlternativo("INTRODUCE_EPGID");
		}
		//Se comprueba que el PID es correcto
		else if(!pidRequest.equals(Pid)){
			jsonResult = JsonUtil.cursoAlternativo("REQUEST_NOT_ALLOWED");
		}
		//La peticion es correcta, se ejecuta
		else{
			try {
				Session sesion = conn.iniciaOperacion(); 
				
				List<Epg> listaEpg = sesion.createQuery("FROM Epg WHERE idepg = '" + epgId + "'").list();
				if(listaEpg.size() == 0){
					jsonResult = JsonUtil.cursoAlternativo("EPG_NOT_EXIST");
				}else{
					//Se crea la egp del canal
					sesion.remove(listaEpg.get(0));
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
		int epgId = (req.queryParams("epgId") != null) ? Integer.parseInt(req.queryParams("epgId")) : -1;
		int channelId = (req.queryParams("channels") != null) ? Integer.parseInt(req.queryParams("channels")) : -1;
		String nombrePrograma = (req.queryParams("nombrePrograma") != null) ? req.queryParams("nombrePrograma") : "";
		String categoria = (req.queryParams("categoria") != null) ? req.queryParams("categoria") : "";
		int edadMinima = (req.queryParams("edadMinima") != null) ? Integer.parseInt(req.queryParams("edadMinima")) : 0;
		Date fechaInicio = null;
		try {
			fechaInicio = (req.queryParams("fechaInicio") != null) ? new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(req.queryParams("fechaInicio")) : null;
		} catch (ParseException e1) {
			jsonResult = JsonUtil.cursoAlternativo("DATE_INCORRECT_FORMAT");
		}
		
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
				
				List<Epg> listaEpg = sesion.createQuery("FROM Epg WHERE idepg = '" + epgId + "'").list();
				if(listaEpg.size() == 0){
					jsonResult = JsonUtil.cursoAlternativo("EPG_NOT_EXIST");
				}else{
					//Se obtiene la epg para actualizarla
					Epg epgCanal = listaEpg.get(0);
					
					//Si ha cambiado el canal
					if(channelId != -1){
						//Se obtiene el canal
						List<Channels> listaCanal = sesion.createQuery("FROM Channels WHERE idchannels = '" + channelId + "'").list();
						if(listaCanal.size() > 0){
							epgCanal.setChannels(listaCanal.get(0));
						}
					}
					//Si ha cambiado el nombre del programa
					if(!nombrePrograma.equals("")){
						epgCanal.setNombrePrograma(nombrePrograma);
					}
					//Si ha cambiado la categoria
					if(!categoria.equals("")){
						epgCanal.setCategoria(categoria);
					}
					//Si ha cambiado la edad minima
					if(edadMinima != -1){
						epgCanal.setEdadMinima(edadMinima);
					}
					
					//Si ha cambiado la fecha de inicio
					if(fechaInicio != null){
						epgCanal.setFechaInicio(fechaInicio);
					}else{
						jsonResult = JsonUtil.cursoAlternativo("DATE_INCORRECT_FORMAT");
					}
					
					sesion.save(epgCanal);
					
					jsonResult = JsonUtil.cursoNormal();
				}
				conn.terminaOperacion();
			} catch (Exception e) {
				jsonResult = JsonUtil.cursoAlternativo("ACTION_NOT_ALLOWED");
			}
		}
		
		return jsonResult;
	}
	
}
