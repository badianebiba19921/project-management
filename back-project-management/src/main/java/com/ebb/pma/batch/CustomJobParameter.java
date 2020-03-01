package com.ebb.pma.batch;

import java.util.UUID;

import org.springframework.batch.core.JobParameter;
import org.springframework.web.multipart.MultipartFile;

public class CustomJobParameter extends JobParameter {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MultipartFile multipartFile;
    
    public CustomJobParameter(MultipartFile file){
        super(UUID.randomUUID().toString());//This is to avoid duplicate JobInstance error
        this.multipartFile = file;
    }
    
    public MultipartFile getValue(){
    	
        return multipartFile;
    }
}
