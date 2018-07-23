package modelToJson;

import java.util.List;

import org.json.JSONObject;

import model.Infochannels;

public class InfoChannelsToJson {
	public static JSONObject parseInfoChannels(List<Infochannels> listaInfoChannels){
		//Objeto Json a devolver
		JSONObject obj = new JSONObject();
		
		//Se recorre la lista de app para convertirla en Json
		for(int i=0; i<listaInfoChannels.size();i++){
			
			JSONObject objaux = new JSONObject();
			
			Infochannels infoChannel = listaInfoChannels.get(i);
			
			//Se recorren todos los atributos del canal
			objaux.put("Premium", infoChannel.getPremium());
			
			obj.put(infoChannel.getId().toString(), objaux);
		}
				
		return obj;
	}
}
