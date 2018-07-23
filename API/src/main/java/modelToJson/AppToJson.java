package modelToJson;

import java.util.List;

import org.json.JSONObject;

import model.App;

public class AppToJson {
	public static JSONObject parseApp(List<App> listaApp){
		//Objeto Json a devolver
		JSONObject obj = new JSONObject();
		
		//Se recorre la lista de app para convertirla en Json
		for(int i=0; i<listaApp.size();i++){
			JSONObject objaux = new JSONObject();
			App app = listaApp.get(i);
			
			//Se recorren todos los atributos del canal
			objaux.put("Nombre", app.getNombre());
			objaux.put("Version", app.getVersionApp());
			objaux.put("UbicacionServer", app.getUbicacionServer());
			
			//Se introduce en el objeto final
			obj.put(app.getIdapp().toString(), objaux);
			
		}
		return obj;
	}
}
