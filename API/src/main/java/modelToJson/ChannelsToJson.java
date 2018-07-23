package modelToJson;

import java.util.List;

import org.json.JSONObject;

import model.Channels;

public class ChannelsToJson {
	public static JSONObject parseChannel(List<Channels> listaCanales){
		//Objeto Json a devolver
		JSONObject obj = new JSONObject();
		
		//Se recorre la lista de canales para convertirla en Json
		for(int i=0; i<listaCanales.size();i++){
			
			JSONObject objaux = new JSONObject();
			
			Channels canal = listaCanales.get(i);
			
			//Se recorren todos los atributos del canal
			objaux.put("Nombre", canal.getName());
			objaux.put("LogicalChannelNumber", canal.getLogicalChannelNumber());
			objaux.put("Url", canal.getUrl());
			objaux.put("Description", canal.getDescription());
			objaux.put("AspectRatio", canal.getAspectRatio());
			objaux.put("Quality", canal.getQuality());
			objaux.put("Visibility", canal.getVisibility());
			objaux.put("SmallIcon", canal.getSmallIcon());
			objaux.put("MediumIcon", canal.getMediumIcon());
			objaux.put("LargeIcon", canal.getLargeIcon());
			
			obj.put(canal.getIdchannels().toString(), objaux);
		}
		return obj;
	}
}

/*
//Tratamos las relaciones
if(!canal.getEpgs().isEmpty()){
	List<Epg> listaEpg = new ArrayList<Epg>();
	for(Epg epg : canal.getEpgs()){
		listaEpg.add(epg);
	}
	//Si contiene alguna app la parsea a Json
	if(listaEpg.size()>0){
		objaux.put("Epg", EpgToJson.parseEpg(listaEpg));
	}
}*/
