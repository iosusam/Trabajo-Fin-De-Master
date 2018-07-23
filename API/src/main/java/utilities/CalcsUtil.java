package utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CalcsUtil {
	
	//Genera el PID para la comprobacion de conexion segura entre API y STB y WEB
	public static String generatePid(){
		//Se coge el dia y el mes del sistema
		Date date = new Date();
		
		//La clave privada
		String key = "Md#EtC2";
		String mes = "";
		String dia = "";
		
		//Se coge el mes
		String formato="MM";
		SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
		mes = dateFormat.format(date);
		
		//Se coge el dia
		formato="dd";
		dateFormat = new SimpleDateFormat(formato);
		dia = dateFormat.format(date);
		String cadenaPid = dia + "-" + mes + key;
		
		return org.apache.commons.codec.digest.DigestUtils.md5Hex(cadenaPid); 
	}
}
