package modelToJson;

import java.util.List;

import org.json.JSONObject;

import model.Epg;


public class EpgToJson {
	public static JSONObject parseEpg(List<Epg> listaEpg){
		//Objeto Json a devolver
		JSONObject obj = new JSONObject();
		
		//Se recorre la lista de app para convertirla en Json
		for(int i=0; i<listaEpg.size();i++){
			
			JSONObject objaux = new JSONObject();
			
			Epg epg = listaEpg.get(i);
			
			//Se recorren todos los atributos del canal
			objaux.put("Categoria", epg.getCategoria());
			/*objaux.put("Inicio", epg.getInicio());
			objaux.put("Final", epg.getFinal_());*/
			objaux.put("EdadMinima", epg.getEdadMinima());
			
			obj.put(epg.getNombrePrograma(), objaux);
		}
		
		return obj;
	}
}
