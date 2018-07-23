package modelToJson;

import java.util.List;

import org.json.JSONObject;

import model.Usuarios;

public class UsuariosToJson {
	public static JSONObject parseUsuarios(List<Usuarios> listaUsuarios){
		//Objeto Json a devolver
		JSONObject obj = new JSONObject();
		
		for(int i=0; i<listaUsuarios.size();i++){
			
			JSONObject objaux = new JSONObject();
			
			Usuarios usuario = listaUsuarios.get(i);
			
			//Se recorren todos los atributos del canal
			objaux.put("Usuario", usuario.getNombre());
			objaux.put("Pass", usuario.getPassword());
			objaux.put("Email", usuario.getEmail());
			objaux.put("FechaCreacion", usuario.getFechaCreacion());
			objaux.put("FechaUltimoAcceso", usuario.getFechaUltimoAcceso());
			objaux.put("Activo", usuario.getActivo());
			objaux.put("Rol", usuario.getRol());
			objaux.put("Organizaciones", OrganizacionToJson.parseOrganizacionName(usuario.getOrganizacions()));
			
			obj.put(usuario.getIdUsuario().toString(), objaux);
		}
		
		return obj;
	}
}
