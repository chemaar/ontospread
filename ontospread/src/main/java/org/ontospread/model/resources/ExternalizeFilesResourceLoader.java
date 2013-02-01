package org.ontospread.model.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.apache.log4j.Logger;



public class ExternalizeFilesResourceLoader extends FilesResourceLoader{
    
    protected static Logger logger = Logger.getLogger(ExternalizeFilesResourceLoader.class);
    public ExternalizeFilesResourceLoader(String[] filenames) {
        super(filenames);
    }
    public ExternalizeFilesResourceLoader(List <String>filenames) {
        super(filenames.toArray(new String[filenames.size()]));
    }
    protected InputStream openInputStream(String filename) throws FileNotFoundException {
        InputStream in;
        try {
			File file = new File(filename);
            logger.info("Opening file " + file.getAbsolutePath());
            in = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            logger.error("Resource file not found: " + filename);
            throw e;
        }        
        if (in == null) {
            logger.error("Resource file not found: " + filename);
            throw new FileNotFoundException(filename);
        }
        return in;
    }
    
    
}
