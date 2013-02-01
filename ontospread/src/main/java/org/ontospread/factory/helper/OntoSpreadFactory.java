package org.ontospread.factory.helper;

import org.ontospread.dao.JenaOntologyDAOImpl;
import org.ontospread.dao.OntologyDAO;
import org.ontospread.model.loader.JenaOWLModelWrapper;
import org.ontospread.model.loader.OntoSpreadModelWrapper;
import org.ontospread.model.resources.FilesResourceLoader;
import org.ontospread.model.resources.ResourceLoader;
import org.ontospread.process.OntoSpreadProcess;
import org.ontospread.process.post.OntoSpreadPostAdjustment;
import org.ontospread.process.post.OntoSpreadPostAdjustmentImpl;
import org.ontospread.process.pre.OntoSpreadPreAdjustment;
import org.ontospread.process.pre.OntoSpreadPreAdjustmentImpl;
import org.ontospread.process.run.OntoSpreadRun;
import org.ontospread.process.run.OntoSpreadRunImpl;
import org.ontospread.restrictions.OntoSpreadCompositeRestriction;
import org.ontospread.restrictions.common.OntoSpreadRestrictionMaxConcepts;
import org.ontospread.restrictions.common.OntoSpreadRestrictionMinConcepts;
import org.ontospread.restrictions.visitor.OntoSpreadBooleanRestrictionVisitor;
import org.ontospread.restrictions.visitor.OntoSpreadRestrictionVisitorAdapter;
import org.ontospread.strategy.OntoSpreadSimpleStrategy;
import org.ontospread.strategy.pair.OntoSpreadStrategyVisitorPair;

public class OntoSpreadFactory {
	private static OntoSpreadFactory factory;
	
	public static OntoSpreadFactory getOntoSpreadFactory(){
		if(factory == null){
			factory = new OntoSpreadFactory();
		}
		return factory;
	}
	public OntoSpreadProcess createDefaultOntoSpreadProcess(){
		return new OntoSpreadProcess(
				createOntologyDAO(),
				createDefaultOntoSpreadPreAdjustment(),
				createDefaultOntoSpreadRun(),
				createDefaultOntoSpreadPostAdjustment());
	}
	public OntoSpreadPreAdjustment createDefaultOntoSpreadPreAdjustment(){
		return new OntoSpreadPreAdjustmentImpl();
	}
	
	
	public OntoSpreadPostAdjustment createDefaultOntoSpreadPostAdjustment(){
		return new OntoSpreadPostAdjustmentImpl();
	}
	
	public OntoSpreadRun createDefaultOntoSpreadRun(){
		return new OntoSpreadRunImpl(createOntologyDAO(),
				createManagerStopStrategy(),
				createManagerSelectConceptStrategy(),
				null,
				null,
				null,
				null);
	}
	


	public static OntoSpreadModelWrapper createOntoModelWrapper(String []filenames){
		ResourceLoader resource = new FilesResourceLoader(filenames);
		JenaOWLModelWrapper model = new JenaOWLModelWrapper(resource);
		return model;
	}
	
	public static OntologyDAO createOntologyDAO() {
		String[] filenames = new String[]{"OntoNodrizaFull-local.owl"};
		OntologyDAO ontoDAO = new JenaOntologyDAOImpl(createOntoModelWrapper(filenames ));
		return ontoDAO;
	}


	public OntoSpreadCompositeRestriction createDefaultStopRestrictions() {
		OntoSpreadCompositeRestriction restrictions = new OntoSpreadCompositeRestriction();
		restrictions.getRestrictions().add(new OntoSpreadRestrictionMinConcepts(2));
		restrictions.getRestrictions().add(new OntoSpreadRestrictionMaxConcepts(10));
		return restrictions;
	}

	public OntoSpreadCompositeRestriction createDefaultSelectConceptRestriction() {
		OntoSpreadCompositeRestriction restrictions = new OntoSpreadCompositeRestriction();		
		return restrictions;
	}


	public OntoSpreadStrategyVisitorPair createManagerStopStrategy(){
		return new OntoSpreadStrategyVisitorPair(
								new OntoSpreadSimpleStrategy(createDefaultStopRestrictions()), 
								new OntoSpreadBooleanRestrictionVisitor());
	}
	public OntoSpreadStrategyVisitorPair createManagerSelectConceptStrategy(){
		return new OntoSpreadStrategyVisitorPair(new OntoSpreadSimpleStrategy(
								createDefaultSelectConceptRestriction()), 
								new OntoSpreadRestrictionVisitorAdapter());
	}

	
}
