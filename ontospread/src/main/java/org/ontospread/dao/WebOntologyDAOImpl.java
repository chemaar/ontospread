package org.ontospread.dao;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;

import org.apache.log4j.Logger;
import org.ontospread.exceptions.ConceptNotFoundException;
import org.ontospread.model.loader.OntoSpreadModelWrapper;
import org.ontospread.to.ConceptTO;
import org.ontospread.xmlbind.Concept;

public class WebOntologyDAOImpl extends JenaOntologyDAOImpl {

	private static final Logger logger = Logger.getLogger(WebOntologyDAOImpl.class);
	
	private HashSet<String> fetchedUris = new HashSet<String>();
	
	public WebOntologyDAOImpl(OntoSpreadModelWrapper wrapper) {
		super(wrapper);
	}

	@Override
	public Concept getConcept(String conceptUri, String contextUri)
			throws ConceptNotFoundException {
		fetchDataIfNeeded(conceptUri);
		return super.getConcept(conceptUri, contextUri);
	}

	private void fetchDataIfNeeded(String conceptUri) {
		if (!fetchedUris.contains(conceptUri)) {
			logger.info("Fetching data for: " + conceptUri);
			try {
				URL url = new URL(conceptUri);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setInstanceFollowRedirects(true);
				connection.setRequestProperty("Accept", "application/rdf+xml");
				if (connection.getResponseCode() == HttpURLConnection.HTTP_OK &&
						connection.getContentType().contains("application/rdf+xml")) {
					InputStream is = connection.getInputStream();
					getOntModel().read(is,conceptUri);
					is.close();
					fetchedUris.add(conceptUri);
				} else {
					logger.error("Unable to get a representation of the resource: "
							+ connection.getResponseCode()
							+ " => " + connection.getContentType());
					throw new RuntimeException("Unable to get a representation of the resource");
				}
			} catch (Exception e) {
				logger.error("Unable to fetch data for concept " + conceptUri, e);
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public ConceptTO getConceptTO(String conceptUri)
			throws ConceptNotFoundException {
		fetchDataIfNeeded(conceptUri);
		return super.getConceptTO(conceptUri);
	}
	
	
	
}
