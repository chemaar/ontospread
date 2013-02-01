package org.ontospread.xmlbind.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.ontospread.exceptions.DocumentBuilderException;
import org.ontospread.exceptions.OntoSpreadModelException;
import org.ontospread.utils.DocumentBuilderHelper;
import org.ontospread.xmlbind.OntoSpreadXMLState;
import org.w3c.dom.Document;
import org.w3c.dom.Node;


public class OntoSpreadXMLStateXMLBind {
	
	private static Logger logger = Logger.getLogger(OntoSpreadXMLStateXMLBind.class);
	
	public static final String PACKAGE_NAME = OntoSpreadXMLState.class.getPackage().getName();
	
	private Unmarshaller unmarshaller;
	private Marshaller marshaller;
	
	private OntoSpreadXMLStateXMLBind() {
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

	public OntoSpreadXMLState restoreOntoSpreadXMLState(Node node) {
		logger.debug("Restore OntoSpreadXMLState XML");
		try {
			return (OntoSpreadXMLState) unmarshaller.unmarshal(node);
		} catch (JAXBException je) {
			throw new OntoSpreadModelException(je, "OntoSpreadXMLStateXmlBind: Unmarshalling OntoSpreadXMLState.");
		}
	}
	
	
	public Document serializeOntoSpreadXMLState(OntoSpreadXMLState OntoSpreadXMLState) {
		logger.debug("Serializing OntoSpreadXMLState XML");
		try {
			Document doc = DocumentBuilderHelper.getEmptyDocument();
			marshaller.marshal(OntoSpreadXMLState, doc);
			return doc;
		} catch (JAXBException je) {
			throw new OntoSpreadModelException(je, "OntoSpreadXMLState XmlBind: Marshalling OntoSpreadXMLState .");
		} catch (DocumentBuilderException e) {
			throw new OntoSpreadModelException(e, "OntoSpreadXMLState XmlBind: Building document for marshall OntoSpreadXMLState .");
		}        
	}

	/**
	 * Singleton field
	 */
	private static OntoSpreadXMLStateXMLBind instance;
	
	/**
	 * Singleton method
	 * 
	 * @return The singleton instance of this class 
	 * @throws XmlBindException
	 */
	public static OntoSpreadXMLStateXMLBind getInstance() {
		if (instance == null) {
			instance = new OntoSpreadXMLStateXMLBind();
		}
		return instance;
	}	
}
