package org.ontospread.process;

import java.util.LinkedList;
import java.util.List;

import org.ontospread.constraints.OntoSpreadRelationWeight;
import org.ontospread.constraints.OntoSpreadRelationWeightRDFImpl;
import org.ontospread.dao.DAOUtils;
import org.ontospread.dao.OntologyDAO;
import org.ontospread.process.post.OntoSpreadPostAdjustment;
import org.ontospread.process.post.OntoSpreadPostAdjustmentImpl;
import org.ontospread.process.pre.OntoSpreadPreAdjustment;
import org.ontospread.process.pre.OntoSpreadPreAdjustmentConfig;
import org.ontospread.process.pre.OntoSpreadPreAdjustmentConfigImpl;
import org.ontospread.process.pre.OntoSpreadPreAdjustmentImpl;
import org.ontospread.process.run.OntoSpreadDegradationFunction;
import org.ontospread.process.run.OntoSpreadDegradationFunctionImpl;
import org.ontospread.process.run.OntoSpreadDegradationFunctionIterationsImpl;
import org.ontospread.process.run.OntoSpreadRun;
import org.ontospread.process.run.OntoSpreadRunImpl;
import org.ontospread.restrictions.OntoSpreadCompositeRestriction;
import org.ontospread.restrictions.common.OntoSpreadRestrictionMaxConcepts;
import org.ontospread.restrictions.common.OntoSpreadRestrictionMinActivationValue;
import org.ontospread.restrictions.common.OntoSpreadRestrictionMinConcepts;
import org.ontospread.restrictions.visitor.OntoSpreadBooleanRestrictionVisitor;
import org.ontospread.strategy.OntoSpreadSelectConceptStrategy;
import org.ontospread.strategy.OntoSpreadSimpleStrategy;
import org.ontospread.strategy.OntoSpreadStrategy;
import org.ontospread.strategy.pair.OntoSpreadStrategyVisitorPair;
import org.ontospread.to.ScoredConceptTO;

public class OntoSpreadTestUtils {

	public static OntoSpreadStrategyVisitorPair createSelectStrategy(){
		//Restrictions: Strategy Select
		OntoSpreadCompositeRestriction restrictionsSelect = new OntoSpreadCompositeRestriction();
		restrictionsSelect.getRestrictions().add(new OntoSpreadRestrictionMinActivationValue(10.0));
		OntoSpreadStrategy selectStrategy = new OntoSpreadSelectConceptStrategy(restrictionsSelect);		
		return new OntoSpreadStrategyVisitorPair(selectStrategy,new OntoSpreadBooleanRestrictionVisitor());		
	}

	public static OntoSpreadStrategyVisitorPair createStopStrategy(int min, int max, double minActivation){
		OntoSpreadCompositeRestriction restrictions = new OntoSpreadCompositeRestriction();
		restrictions.getRestrictions().add(new OntoSpreadRestrictionMinConcepts(min));
		restrictions.getRestrictions().add(new OntoSpreadRestrictionMaxConcepts(max));	
		restrictions.getRestrictions().add(new OntoSpreadRestrictionMinActivationValue(minActivation));
		OntoSpreadStrategy stopStrategy = new OntoSpreadSimpleStrategy(restrictions);		
		return new OntoSpreadStrategyVisitorPair(stopStrategy,new OntoSpreadBooleanRestrictionVisitor());		
	}

	public static ScoredConceptTO[] createScoredConcepts(String [] uris, double initialScore){
		List <ScoredConceptTO> scoredConcepts = new LinkedList<ScoredConceptTO>();
		for(int i = 0; i<uris.length;i++){
			scoredConcepts.add(new ScoredConceptTO(uris[i],initialScore));
		}
		return scoredConcepts.toArray(new ScoredConceptTO[uris.length]);
	}

	public static OntoSpreadPreAdjustment createDefaultPreAdjustment(){
		OntoSpreadPreAdjustment pre = new OntoSpreadPreAdjustmentImpl();
		OntoSpreadPreAdjustmentConfig ontoSpreadPreConfig = new OntoSpreadPreAdjustmentConfigImpl(10.0);
		pre.setOntoPreAdjustmentConfig(ontoSpreadPreConfig );
		return pre;
	}

	public static OntoSpreadRun createDefaultRun(int min, int max,  double minActivation) {	
		return new OntoSpreadRunImpl(
			DAOUtils.createOntologyDAO(), 
			createStopStrategy(min,max, minActivation), 
			createSelectStrategy(),
			createRelationWeight());
		
	}
	
	public static OntoSpreadRun createDefaultRunWithDegradation(int min, int max,  double minActivation, 
			OntoSpreadDegradationFunction degradationFunction) {	
		return new OntoSpreadRunImpl(
			DAOUtils.createOntologyDAO(), 
			createStopStrategy(min,max, minActivation), 
			createSelectStrategy(),
			createRelationWeight(),
			degradationFunction);
		
	}

	private static OntoSpreadRelationWeight createRelationWeight() {		
		return new OntoSpreadRelationWeightRDFImpl(DAOUtils.createModelWrapper());
	}

	private static OntoSpreadRelationWeight createRelationWeightGalen() {		
		return new OntoSpreadRelationWeightRDFImpl(DAOUtils.createGalenModelWrapper());
	}
	
	public static OntoSpreadPostAdjustment createDefaultPost() {
		return new OntoSpreadPostAdjustmentImpl(Boolean.TRUE);
	}

	public static OntoSpreadProcess createDefaultOntoSpreadProcess(int min, int max,  double minActivation){
		return new OntoSpreadProcess(
				DAOUtils.createOntologyDAO(),
				createDefaultPreAdjustment(), 
				createDefaultRun(min, max, minActivation), 
				createDefaultPost()); 
	}

	public static OntoSpreadDegradationFunction createDegradationFunction() {		
			return new OntoSpreadDegradationFunctionImpl(); //h1
		//return new OntoSpreadDegradationFunctionIterationsImpl();
//		}else if(this.functionType.equals(FunctionType.H_2)){
//			this.function =  new OntoSpreadDegradationFunctionIterationsImpl();
//		}
	}

	public static OntoSpreadProcess createDefaultGalenOntoSpreadProcess(int min, int max,  double minActivation, OntologyDAO dao, 
			OntoSpreadDegradationFunction degradationFunction){
		return new OntoSpreadProcess(
				dao,
				createDefaultPreAdjustment(), 
				createDefaultRunWithDegradation(min, max, minActivation,degradationFunction), 
				createDefaultPost()); 
	}
}
