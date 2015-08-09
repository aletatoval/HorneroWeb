package py.com.hornero.utils.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.web.multipart.MultipartFile;

import py.com.hornero.controller.BaseController;

public class ManejoArchivos extends BaseController{
	public static boolean crearArchivo(MultipartFile filea) {
		String path = "tmp/";
		try {
			// verifica si existe la carpeta, sino la crea
			File directorio = new File(path);
			directorio.mkdir();
			if (filea.getSize() > 0) {
				InputStream inputStream = filea.getInputStream();
				OutputStream outputStream = new FileOutputStream(path
						+ filea.getOriginalFilename());
				int readBytes = 0;
				byte[] buffer = new byte[8192];
				while ((readBytes = inputStream.read(buffer, 0, 8192)) != -1) {
					outputStream.write(buffer, 0, readBytes);
				}
				outputStream.close();
				inputStream.close();
			} else
				return false;
			return true;
		} catch (Exception e) {
			logger.error("Error al crear el archivo", e);
			return false;
		}
	}
}
