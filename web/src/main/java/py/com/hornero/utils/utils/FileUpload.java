/**
 * 
 */
package py.com.hornero.utils.utils;

import org.springframework.web.multipart.MultipartFile;

public class FileUpload {

	MultipartFile file;

	/**
	 * @return el file de FileUpload
	 */
	public MultipartFile getFile() {
		return file;
	}

	/**
	 * @param file
	 *            el file de FileUpload a setear
	 */
	public void setFile(MultipartFile file) {
		this.file = file;
	}

}
