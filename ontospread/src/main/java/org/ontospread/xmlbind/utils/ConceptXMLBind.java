package org.ontospread.xmlbind.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.ontospread.exceptions.DocumentBuilderException;
import org.ontospread.exceptions.OntoSpreadModelException;
import org.ontospread.utils.DocumentBuilderHelper;
import org.ontospread.xmlbind.Concept;
import org.w3c.dom.Document;
import org.w3c.dom.Node;


public class ConceptXMLBind {
	
	private static Logger logger = Logger.getLogger(ConceptXMLBind.class);
	
	public static final String PACKAGE_NAME = Concept.class.getPackage().getName();
	
	private Unmarshaller unmarshaller;
	private Marshaller marshaller;
	
	private ConceptXMLBind() {
		try {
			JAXBContext jc = JAXBContext.newInstance(PACKAGE_NAME);
			this.unmarshaller = jc.createUnmarshaller();
			this.marshaller = jc.createMarshaller();
			//            unmarshaller.setEventHandler(eventHandler);
			//            marshaller.setEventHandler(eventHandler);
		} catch(JAXBException e) {
			//
		}
	}

	public Concept restoreConcept(Node node) {
		try {
			return (Concept) unmarshaller.unmarshal(node);
		} catch (JAXBException je) {
			throw new OntoSpreadModelException(je, "ConceptXmlBind: Unmarshalling concept.");
		}
	}
	
	
	public Document serializeConcept(Concept concept) {
		logger.debug("Serializing concept XML");
		try {
			Document doc = DocumentBuilderHelper.getEmptyDocument();
			marshaller.marshal(concept, doc);
			return doc;
		} catch (JAXBException je) {
			throw new OntoSpreadModelException(je, "ConceptXmlBind: Marshalling concept.");
		} catch (DocumentBuilderException e) {
			throw new OntoSpreadModelException(e, "ConceptXmlBind: Building document for marshall concept.");
		}        
	}

	/**
	 * Singleton field
	 */
	private static ConceptXMLBind instance;
	
	/**
	 * Singleton method
	 * 
	 * @return The singleton instance of this class 
	 * @throws XmlBindException
	 */
	public static ConceptXMLBind getInstance() {
		if (instance == null) {
			instance = new ConceptXMLBind();
		}
		return instance;
	}	
}
