package modelToJson;

import java.util.List;
import java.util.Set;

import org.json.JSONObject;

import model.Organizacion;

public class OrganizacionToJson {
	public static JSONObject parseOrganizacion(List<Organizacion> listaOrganizacion){
		//Objeto Json a devolver
		JSONObject obj = new JSONObject();
		
		//Se recorre la lista de app para convertirla en Json
		for(int i=0; i<listaOrganizacion.size();i++){
			JSONObject objaux = new JSONObject();
			Organizacion organizacion = listaOrganizacion.get(i);
			
			//Se recorren todos los atributos del canal
			objaux.put("Nombre", organizacion.getNombre());
			objaux.put("Pais", organizacion.getPais());
			objaux.put("Region", organizacion.getRegion());
			objaux.put("Direccion", organizacion.getDireccion());
			objaux.put("CodigoPostal", organizacion.getCodigoPostal());
			objaux.put("Ciudad", organizacion.getCiudad());
			objaux.put("Telefono", organizacion.getTelefono());
			objaux.put("Email", organizacion.getEmail());
			
			obj.put(organizacion.getId().toString(), objaux);
		}
				
		return obj;
	}
	
	public static JSONObject parseOrganizacionName(Set<Organizacion> setOrganizaciones){
		//Objeto Json a devolver
		JSONObject obj = new JSONObject();
		
		for(Organizacion organizacion : setOrganizaciones){
			JSONObject objaux = new JSONObject();
			
			objaux.put("Nombre", organizacion.getNombre());
			obj.put(organizacion.getId().toString(), objaux);
		}
		
		return obj;
	}

}
