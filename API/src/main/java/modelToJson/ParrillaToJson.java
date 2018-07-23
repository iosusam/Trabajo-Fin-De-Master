package modelToJson;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import model.Channels;
import model.Paquetes;
import model.Parrilla;

public class ParrillaToJson {
	
	public static JSONObject parseParrilla(Parrilla parrilla){
		JSONObject jsonResult = new JSONObject();
		JSONObject jsonResultAux = new JSONObject();
		
		//Se guarda en una lista los canales para despuesta pasarlos a Json
		List<Channels> listaCanales = new ArrayList<Channels>();
		for(Channels canal : parrilla.getChannelses()){
			listaCanales.add(canal);
		}
		
		//Introducir canales
		jsonResultAux.put("Channels", ChannelsToJson.parseChannel(listaCanales));
		
		//Se guarda en una lista los paquetes para despuesta pasarlos a Json
		List<Paquetes> listaPaquetes = new ArrayList<Paquetes>();
		for(Paquetes paquete : parrilla.getPaqueteses()){
			listaPaquetes.add(paquete);
		}
		
		jsonResultAux.put("Paquetes", PaquetesToJson.parsePaquetes(listaPaquetes));
		
		jsonResult.put(parrilla.getIdparrilla().toString(), jsonResultAux);
		
		return jsonResult;
	}
	
	
}
