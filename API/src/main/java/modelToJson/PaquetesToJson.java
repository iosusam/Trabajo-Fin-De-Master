package modelToJson;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import model.Channels;
import model.Paquetes;

public class PaquetesToJson {
	public static JSONObject parsePaquetes(List<Paquetes> listPaquetes){
		//Objeto Json a devolver
		JSONObject obj = new JSONObject();
		
		for(int i=0; i<listPaquetes.size();i++){
			
			JSONObject objaux = new JSONObject();
			
			Paquetes paquete = listPaquetes.get(i);
			
			//Se recorren todos los atributos del canal
			objaux.put("Nombre", paquete.getNombre());
			objaux.put("Descripcion", paquete.getDescripcion());
			
			//Se guarda en una lista los canales para despuesta pasarlos a Json
			List<Channels> listaCanales = new ArrayList<Channels>();
			//Recorrer canales del paquete
			for(Channels canal : paquete.getChannelses()){
				listaCanales.add(canal);
			}
			objaux.put("Channels", ChannelsToJson.parseChannel(listaCanales));
			
			obj.put(paquete.getIdpaquete().toString(), objaux);
		}
		
		return obj;
	}
}
