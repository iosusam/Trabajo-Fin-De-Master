package utilities;

import java.sql.ResultSet;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;

import com.google.gson.Gson;

import spark.ResponseTransformer;

public class JsonUtil {

	public static String toJson(Object object) {
	    return new Gson().toJson(object);
	}
	
	public static ResponseTransformer json() {
		return JsonUtil::toJson;
	}
	/*
	//Convertir los resultados de la BBDD a JSON
	public static JSONObject convertResultSetIntoJSON(List lista) throws Exception {
		//Objeto Json a devolver
		JSONObject obj = new JSONObject();
		for(int i=0; i<lista.size();i++ ){
			
		}
		while (resultSet.next()) {
            int total_rows = resultSet.getMetaData().getColumnCount();
            for (int i = 0; i < total_rows; i++) {
                String columnName = resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
                Object columnValue = resultSet.getObject(i + 1);
                // if value in DB is null, then we set it to default value
                if (columnValue == null){
                    columnValue = "null";
                }
                /*
                Next if block is a hack. In case when in db we have values like price and price1 there's a bug in jdbc - 
                both this names are getting stored as price in ResulSet. Therefore when we store second column value,
                we overwrite original value of price. To avoid that, i simply add 1 to be consistent with DB.
                 
                if (obj.has(columnName)){
                    columnName += "1";
                }
                obj.put(columnName, columnValue);
            }
        }
        return obj;
    }*/
	
	public static JSONObject cursoNormal(){
		JSONObject obj = new JSONObject();
		obj.put("status", "ok");
		obj.put("message", "OK");
		return obj;
	}
	
	public static JSONObject cursoNormal(String message){
		JSONObject obj = new JSONObject();
		obj.put("status", "ok");
		obj.put("message", message);
		return obj;
	}
	
	public static JSONObject cursoAlternativo(String message){
		JSONObject obj = new JSONObject();
		obj.put("status", "ko");
		obj.put("message", message);
		return obj;
	}
	
	public static String hashMd5(String password){
		return DigestUtils.md5Hex(password).toUpperCase();
	}
}