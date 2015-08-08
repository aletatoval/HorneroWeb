package py.com.mensajeria.utils.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;

import py.com.mensajeria.model.entity.EntidadBase;
import py.com.mensajeria.utils.json.JSONObject;

public class Utils {
	public static String getURL(HttpServletRequest req) {

		String scheme = req.getScheme(); // http
		String serverName = req.getServerName(); // hostname.com
		int serverPort = req.getServerPort(); // 80
		String contextPath = req.getContextPath(); // /mywebapp
		// String servletPath = req.getServletPath(); // /servlet/MyServlet
		// String pathInfo = req.getPathInfo(); // /a/b;c=123
		// String queryString = req.getQueryString(); // d=789

		// Reconstruct original requesting URL
		StringBuffer url = new StringBuffer();
		url.append(scheme).append("://").append(serverName);

		if ((serverPort != 80) && (serverPort != 443)) {
			url.append(":").append(serverPort);
		}

		url.append(contextPath);
		//
		// url.append(servletPath);
		//
		// if (pathInfo != null) {
		// url.append(pathInfo);
		// }
		// if (queryString != null) {
		// url.append("?").append(queryString);
		// }
		return url.toString();
	}

	public static EntidadBase mapearAtributosBase(JSONObject objetoJson,
			EntidadBase entidad, Long idUsuarioModificacion) {
		try {
			if (objetoJson.has("id")) {
				entidad.setId(Long.parseLong(objetoJson.getString("id")));
			}
			if (objetoJson.has("fechaCreacion")) {
				entidad.setFechaCreacion(Timestamp.valueOf(objetoJson
						.getString("fechaCreacion")));
			}
			if (objetoJson.has("fechaEliminacion")) {
				entidad.setFechaEliminacion(Timestamp.valueOf(objetoJson
						.getString("fechaEliminacion")));
			}
			if (objetoJson.has("idUsuarioCreacion")) {
				entidad.setIdUsuarioCreacion(Long.parseLong(objetoJson
						.getString("idUsuarioCreacion")));
			}
			if (objetoJson.has("idUsuarioEliminacion")) {
				entidad.setIdUsuarioEliminacion(Long.parseLong(objetoJson
						.getString("idUsuarioEliminacion")));
			}

			if (objetoJson.has("origenModificacion")) {
				entidad.setOrigenModificacion(objetoJson
						.getString("origenModificacion"));
			}
			if (objetoJson.has("activo")) {
				entidad.setActivo(objetoJson.getString("activo"));
			}

			entidad.setFechaModificacion(new Timestamp(System
					.currentTimeMillis()));
			entidad.setIdUsuarioModificacion(idUsuarioModificacion);

		} catch (Exception ex) {

		}

		return entidad;
	}

	/**
	 * Método para generar la clave de manera aleatoria
	 * 
	 * @param u
	 * @param longitud
	 * @return retorna un String con la nueva clave
	 */
	public static String generarClaveAleatoria(Long idEmpresa) {

		String cadenaAleatoria = "";
		String chards = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789$#*";
		
		String cadenaNueva= getCadenaAlfanumAleatoria(Long.toString(idEmpresa), chards);

		long milis = new java.util.GregorianCalendar().getTimeInMillis();
		Random r = new Random(milis);

		for (int i = 0; i < 10; i++) {
			char c = cadenaNueva.charAt(r.nextInt(cadenaNueva.length()));
			cadenaAleatoria = cadenaAleatoria + c;
		}
		
		return cadenaAleatoria;
	}

	public static String getCadenaAlfanumAleatoria(String id, String texto) {

		String secretKey = id; // llave para encriptar datos
		String base64EncryptedString = "";

		try {

			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
			byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

			SecretKey key = new SecretKeySpec(keyBytes, "DESede");
			Cipher cipher = Cipher.getInstance("DESede");
			cipher.init(Cipher.ENCRYPT_MODE, key);

			byte[] plainTextBytes = texto.getBytes("utf-8");
			byte[] buf = cipher.doFinal(plainTextBytes);
			byte[] base64Bytes = Base64.encodeBase64(buf);
			base64EncryptedString = new String(base64Bytes);

		} catch (Exception ex) {
			System.out.print(ex);
		}
		return base64EncryptedString;

	}

}
