package com.lazyloading.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * Util methods to create and manages files
 * @author Erika
 *
 */
public class FileUtil {
	
	/**
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static List<BigDecimal> readFile(MultipartFile file) throws IOException{
		List<BigDecimal> data = new ArrayList<>();
		if (!file.isEmpty()) {

			InputStream inputStream = new ByteArrayInputStream(file.getBytes());
			 
		    try (BufferedReader reader = new BufferedReader(new InputStreamReader
		      (inputStream, Charset.forName(StandardCharsets.UTF_8.name())))) {
		        String value;
		        while ((value = reader.readLine()) != null) {
		            data.add(new BigDecimal(value));
		        }
		    }
	    	
	        inputStream.close();
		}
        return data;
	}
	
    /**
     * Save the content in a file with name <b>name</b> in a <b>uploadFolder</b> route
     * @param content content file
     * @param uploadFolder route to save the file
     * @param name File name
     * @throws IOException
     */
    public static void saveDataToFile(String content, String uploadFolder, String name) throws IOException {
            byte[] bytes = content.getBytes();
            
            Path path = Paths.get(uploadFolder);
            
            if (Files.notExists(path)){
            	if(new File(uploadFolder).mkdir()){
		            path = Paths.get(uploadFolder + name);
		            Files.write(path, bytes);
            	}
            }else{
            	path = Paths.get(uploadFolder + name);
	            Files.write(path, bytes);
            }
    }	
    
    /**
     * Return the byte array of a file
     * @param fileRoute Full file route with extension
     * @return file byte[] 
     * @throws IOException
     */
    public static byte[] getFileBytes(String fileRoute) throws IOException{
    	File file = new File(fileRoute);
		
	    return FileCopyUtils.copyToByteArray(file);
    }
	
}
