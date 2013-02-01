package org.ontospread.demo;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.ontospread.exceptions.ConceptNotFoundException;
import org.ontospread.player.SpreadPlayer;
import org.ontospread.player.SpreadSimplePlayer;
import org.ontospread.process.OntoSpreadTestUtils;
import org.ontospread.state.OntoSpreadState;
import org.ontospread.utils.ToStringHelper;

public class Runner {

	static Logger logger = Logger.getLogger(Runner.class);
	public static void main(String []args) throws ConceptNotFoundException{		
		PropertyConfigurator.configure("/home/chema/pfc_develop/ontospread/trunk/apps/ontospread/src/main/resources/log4j.properties");
		logger.debug("Init ontospread");
		String []conceptUris = new String[]{
		"http://websemantica.fundacionctic.org/ontologias/bopa/piscina.owl#Piscina"};
		OntoSpreadState ontoSpreadState = new OntoSpreadState();
		ontoSpreadState.setInitialConcepts(OntoSpreadTestUtils.createScoredConcepts(conceptUris,1.0));
		SpreadPlayer player = new SpreadSimplePlayer(
				OntoSpreadTestUtils.createDefaultOntoSpreadProcess(3, 3, 1.0),ontoSpreadState);
		while(player.hasNext()){
			ontoSpreadState = player.next();
		}
		System.out.println(ToStringHelper.arrayToString(
				ontoSpreadState.getFinalSpreadedConcepts(),"\n"));
	}
	

}
