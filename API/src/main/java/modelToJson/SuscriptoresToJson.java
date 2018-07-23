package modelToJson;

import java.util.List;

import org.json.JSONObject;

import model.Suscriptores;

public class SuscriptoresToJson {
	public static JSONObject parseSuscriptores(List<Suscriptores> listaSuscriptores){
		//Objeto Json a devolver
		JSONObject obj = new JSONObject();
		
		for(int i=0; i<listaSuscriptores.size();i++){
			
			JSONObject objaux = new JSONObject();
			
			Suscriptores suscriptor = listaSuscriptores.get(i);
			
			//Se recorren todos los atributos del canal
			objaux.put("Activo", suscriptor.getActivo());
			objaux.put("Organizacion", suscriptor.getOrganizacion().getNombre());
			objaux.put("App", suscriptor.getApp().getNombre());
			
			obj.put(suscriptor.getIdsuscriptor().toString(), objaux);
		}
		
		return obj;
	}
}
