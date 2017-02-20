package com.lazyloading.web.resource;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lazyloading.domain.LazyLoading;
import com.lazyloading.service.LazyLoadingDataService;
import com.lazyloading.util.FileUtil;
import com.lazyloading.util.HttpUtil;

/**
 * This API provides methods to execute the LazyLoading Maximization Process and
 * to access to LazyLoading data
 * @author Erika
 *
 */
@RestController
@RequestMapping("/lazyloading/api")
public class LazyLoadingResource {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LazyLoadingResource.class);
	
	@Autowired
	public LazyLoadingDataService lazyLoadingDataService;

	private static String UPLOADING_FOLDER = "src/main/resources/downloads/";
	
	
	/**
	 * Reads the submitted file and process it with the Lazy Loading process, and 
	 * writes the result in a server file
	 * @param id user identification
	 * @param file file input
	 * @return Last LazyLoading added 
	 * @throws IOException 
	 * @throws URISyntaxException 
	 */
	@PostMapping(value = "/uploadFile", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<LazyLoading> uploadFile(@RequestParam("id") String id, 
			@RequestParam("fileToUpload") MultipartFile file, HttpServletRequest request) throws IOException{
		
		if (file.isEmpty()){
			return new ResponseEntity<LazyLoading>(HttpStatus.BAD_REQUEST);
		}
		if (id == null || id.isEmpty()){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		LazyLoading lazyFile = null;
		try {
			List<BigDecimal> lazyData = FileUtil.readFile(file);
			
			lazyFile = lazyLoadingDataService.maximumLazyLoadingProcess(lazyData, id, 
					HttpUtil.getCurrentUrl(request), UPLOADING_FOLDER);

            return new ResponseEntity<LazyLoading>(lazyFile, HttpStatus.CREATED);
		} catch (IOException e) {
			LOGGER.error("", e);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (URISyntaxException e) {
			LOGGER.error("", e);
			return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
		}
	}
	
	/**
	 * Given the file name, returns the file 
	 * @param fileName
	 * @return
	 * @throws IOException if the file is not found
	 */
	@GetMapping("/files/{fileName}")
	public HttpEntity<byte[]> getFile(@PathVariable("fileName") String fileName) throws IOException {
	    byte[] document = FileUtil.getFileBytes(UPLOADING_FOLDER + fileName + ".txt");
	    HttpHeaders header = new HttpHeaders();
	    header.setContentType(new MediaType("application", "text"));
	    header.set("Content-Disposition", "inline; filename=" + fileName + ".txt");
	    header.setContentLength(document.length);
	    return new HttpEntity<byte[]>(document, header);
	}
	
	/**
	 * Returns all the LazyLoading test cases
	 * @return
	 */
	@GetMapping(value = "/allCases", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<LazyLoading> getAllCases(){
		return lazyLoadingDataService.getLazyLoadingList();
	}
	
}
