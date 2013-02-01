

import org.ontospread.player.SpreadPlayer;
import org.ontospread.player.SpreadSimplePlayer;
import org.ontospread.process.OntoSpreadTestUtils;
import org.ontospread.state.OntoSpreadState;
import org.ontospread.to.SpreadedConceptTO;

public class SimplePlayerMain {

	public static void main(String []args) throws Exception{
		String []conceptUris = new String[]{
				"http://websemantica.fundacionctic.org/ontologias/bopa/empleado.owl#Vacaciones",
				 "http://websemantica.fundacionctic.org/ontologias/bopa/ensamble.owl#EmpleadoDeFincas"};
		OntoSpreadState ontoSpreadState = new OntoSpreadState();
		ontoSpreadState.setInitialConcepts(OntoSpreadTestUtils.createScoredConcepts(conceptUris,1.0));
		SpreadPlayer player = new SpreadSimplePlayer(OntoSpreadTestUtils.createDefaultOntoSpreadProcess(3, 5, 0.3),
				ontoSpreadState);		
		while(player.hasNext()){
			ontoSpreadState = player.next();			
		}
		
		SpreadedConceptTO spreadedConcepts[] = ontoSpreadState.getFinalSpreadedConcepts();
		for (int i = 0; i < spreadedConcepts.length; i++) {
			System.out.println(spreadedConcepts[i]);
		}
		
	}
}
