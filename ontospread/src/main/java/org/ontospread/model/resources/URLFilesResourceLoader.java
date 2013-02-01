package org.ontospread.model.resources;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.apache.log4j.Logger;

public class URLFilesResourceLoader extends FilesResourceLoader {

    protected static Logger logger = Logger.getLogger(URLFilesResourceLoader.class);
    public URLFilesResourceLoader(String[] filenames) {
        super(filenames);
    }
    public URLFilesResourceLoader(List <String>filenames) {
        super(filenames.toArray(new String[filenames.size()]));
    }
    protected InputStream openInputStream(String filename) throws FileNotFoundException {
        InputStream in = null;
        try {
        	URL url = new URL(filename);
        	in = url.openConnection().getInputStream();			
            logger.info("Opening file " + filename);            
        } catch (FileNotFoundException e) {
            logger.error("Resource file not found: " + filename);
            throw e;
        } catch (IOException e) {
        	 logger.error("Resource file can not be readed: " + filename);
             throw new FileNotFoundException("Resource file can not be readed: " + filename);
		}        
        if (in == null) {
            logger.error("Resource file not found: " + filename);
            throw new FileNotFoundException(filename);
        }
        return in;
    }

}
