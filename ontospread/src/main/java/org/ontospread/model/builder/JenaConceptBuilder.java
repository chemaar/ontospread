package org.ontospread.model.builder;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.ontospread.exceptions.ConceptNotFoundException;
import org.ontospread.exceptions.ConceptUnsupportedOperation;
import org.ontospread.xmlbind.Concept;
import org.ontospread.xmlbind.ConceptDescription;
import org.ontospread.xmlbind.ObjectFactory;
import org.ontospread.xmlbind.Relation;
import org.ontospread.xmlbind.Relations;
import org.ontospread.xmlbind.TypeHierarchy;

import com.hp.hpl.jena.ontology.BooleanClassDescription;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.ontology.Restriction;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.vocabulary.OWL;


public class JenaConceptBuilder {
        
    private static final Logger logger = Logger.getLogger(JenaConceptBuilder.class);

    ObjectFactory factory = new ObjectFactory();
    public JenaConceptBuilder(){        
    }

    public Concept createConceptResource(OntResource ontResource) throws ConceptNotFoundException {   
    	if(ontResource == null){
    		throw new ConceptNotFoundException("Concept can not be built because resource is "+ontResource);
    	}
        logger.debug("Creating concept for resource " +ontResource.getURI());
        Concept concept =ConceptBuilderUtils.createEmptyConcept();
        if (!OntologyHelper.isUserConcept(ontResource)) {
            throw new ConceptUnsupportedOperation(ontResource.getURI()+ " createConcepResource");
        }
    
        ConceptDescription currentConceptDescription = OntologyHelper.createConceptDescription(ontResource);
        concept.setConceptDescription(currentConceptDescription);
        
        if(ontResource.isClass()){            
            OntClass ontClass = ontResource.asClass();
            Relations relations = concept.getRelations();
            logger.debug("Adding relations "+relations);
            logger.debug("Adding superclasses "+ ontClass.listSuperClasses(true));
            relations.getRelations().addAll(getRelations(TypeHierarchy.SUPERCLASS, ontClass.listSuperClasses(true)));
            relations.getRelations().addAll(getRelations(TypeHierarchy.SUBCLASS, ontClass.listSubClasses(true)));
            concept.setRelations(relations);
            fillInstances(ontClass, concept);
            logger.debug("Relations "+relations.getRelations().size());
        }else if(ontResource.isIndividual()){
            Individual individual = ontResource.asIndividual();
            List conceptRelations = concept.getRelations().getRelations();
            HashMap relationsMap = new HashMap();
            
//            conceptRelations.addAll(createRelationsForInstance(
//                    OWL.differentFrom.getURI(), 
//                    OWL.differentFrom.getURI(),
//                    OWL.differentFrom.getLocalName(),
//                    OWL.differentFrom.getLocalName(),
//                    individual.listDifferentFrom(),relationsMap));
            
//            conceptRelations.addAll(createRelationsForInstance(
//                    OWL.sameAs.getURI(), 
//                    OWL.sameAs.getURI(),
//                    OWL.sameAs.getLocalName(),
//                    OWL.sameAs.getLocalName(),
//                    individual.listSameAs(),relationsMap));
//            
//            conceptRelations.addAll(createRelationsForInstance(
//                    RDFS.isDefinedBy.getURI(), 
//                    RDFS.isDefinedBy.getURI(),
//                    RDFS.isDefinedBy.getLocalName(),
//                    RDFS.isDefinedBy.getLocalName(),
//                    individual.listIsDefinedBy(),relationsMap));
            
           	createRelationsFromProperties(individual.listProperties(), relationsMap);
                   
            conceptRelations.addAll(relationsMap.values());
            
            concept.getInstanceof().getConceptDescriptions().addAll(createInstanceOf(individual));                
        }
         
        return concept;
    }
    
    private void createRelationsFromProperties(Iterator iterator, HashMap relationsMap) {
        Relation relation;
        for(;iterator.hasNext();){
        	Statement statement = (Statement) iterator.next();
        	if(statement.getObject().canAs(Individual.class)){
        		if(relationsMap.containsKey(statement.getPredicate().getURI())){
        			relation = (Relation) relationsMap.get(statement.getPredicate().getURI());
        		}else{
        			relation = factory.createRelation();                 
        			relation = createRelationFromInstance(statement.getPredicate().getURI(),
        					TypeHierarchy.SUBCLASS,
        					statement.getPredicate().getLocalName(),
        					statement.getPredicate().getLocalName());
        		}

        		addRelation(relation,
        				OntologyHelper.createConceptDescription((Individual) statement.getObject().as(Individual.class)),relationsMap);
        	}else{
        		logger.debug("Discarding instance concept "+statement.getObject()+" conversion to Individual is not possible because is "+statement.getObject().getClass());
        	}
        }       
        
    }
    
    private void addRelation(Relation relationType, ConceptDescription conceptDescription, HashMap relationsMap){        
        Relation relation;
        if(relationsMap.containsKey(relationType.getUri())){
            relation = (Relation) relationsMap.get(relationType.getUri());
         }else{
             relation = relationType;
             relationsMap.put(relation.getUri(),relation);
         }
         relation.getConceptDescriptions().add(conceptDescription);            
    }
    
    
    private List createRelationsForInstance(String uri, TypeHierarchy hierarchy, String description, String value, Iterator it, HashMap relationsMap){
        List relations = new LinkedList();
        for(;it.hasNext();){   
            Object o = it.next();           
            OntResource withInstance =  (OntResource) o;
            Relation relation;
            if(relationsMap.containsKey(uri)){
                relation = (Relation) relationsMap.get(uri);
             }else{
                 relation = createRelationFromInstance(uri, hierarchy, description, value);
             }            
            addRelation(relation,OntologyHelper.createConceptDescription(withInstance),relationsMap);
        }
        return relations;
    }

    private Relation createRelationFromInstance(String uri, TypeHierarchy hierarchy, String description, String value) {
        Relation relation = factory.createRelation();        
        relation.setDescription(description);            
        relation.setHierarchy(hierarchy);
        relation.setOnproperty(uri);
        relation.setUri(uri);
        relation.setValue(value);
        return relation;        
    }

    private void fillInstances(OntClass currentClass,Concept concept){
        List instances = concept.getInstances().getConceptDescriptions(); 
        for(Iterator i = currentClass.listInstances();i.hasNext();){            
            instances.add(OntologyHelper.createConceptDescription((Resource) i.next()));
        }
    }
   
    private List createInstanceOf(Individual individual){
        List instances = new LinkedList();
        for(Iterator i = individual.listRDFTypes(true);i.hasNext();){            
            instances.add(OntologyHelper.createConceptDescription((Resource) i.next()));
        }
        return instances;
    }
    
    /**
     * Transforms an iterator over the relations in the model into a
     * collection of RelationTypes
     * 
     * @param hierarchy Hierarchy relation
     * @param iterator Relation iterator
     * @return A collection of RelationType
     */
    private Collection getRelations(TypeHierarchy hierarchy, Iterator iterator) {
        logger.debug("Creating relations with " + hierarchy);
        LinkedList<Relation> relations = new LinkedList<Relation>();
        while (iterator.hasNext()) { // concat . map
            OntClass relatedClass = (OntClass) iterator.next();
            logger.debug(relatedClass.getURI());
            if (!isOwlInternalClass(relatedClass)) {
                Relation relation = factory.createRelation();
                relation.setHierarchy(hierarchy);                  
                /* Setting description in superclass and subclass, relations overwrite this value.*/
                relation.setDescription(OntologyHelper.getPublicName(relatedClass));
                collectRelatedClassDescription(relatedClass, relation);
                relation.setUri(relation.getOnproperty());
                relations.add(relation);
                logger.debug("Added relation with hierarchy " + relation.getHierarchy() + " on property "+ relation.getOnproperty());
            } else {
                logger.debug("Discarding related class " + relatedClass.getURI());
            }
        }
        logger.debug("End relations with " + hierarchy);
        return relations;
    }
    
    /**
     * Describe a ontoclass, depending ontoclass type.
     * 
     * @param relatedClass Ontoclass to describe
     * @param relation Relation which is being described (collecting parameter)
     */
    
    private  void collectRelatedClassDescription(OntClass relatedClass, Relation relation) {
        if (relatedClass.isUnionClass()) {
            createBooleanClassDescription(relatedClass.asUnionClass() , relation);
        }
        else if (relatedClass.isIntersectionClass()) {
            createBooleanClassDescription(relatedClass.asIntersectionClass(), relation);            
        }
        else if (relatedClass.isComplementClass()) {
            createBooleanClassDescription(relatedClass.asComplementClass(), relation);
        }
        else if (relatedClass.isRestriction()) {
            createRestrictionClassDescription(relatedClass.asRestriction(), relation);
        }
        else if (!relatedClass.isAnon()) {
            ConceptDescription relatedConceptDescription = OntologyHelper.createConceptDescription(relatedClass); 
            relation.getConceptDescriptions().add(relatedConceptDescription);
        }
    }
    
    /**
     * Describe a restriction ontoclass
     * 
     * @param restriction Ontoclass of this type.
     * @param relation Relation which is being described (collecting parameter)
     */
    
    private void createRestrictionClassDescription(Restriction restriction, Relation relation) {
        createRestrictionClassDescriptionElem(restriction.getOnProperty(), relation);
        if (restriction.isAllValuesFromRestriction()) {
            relation.setValue(OWL.allValuesFrom.getLocalName());
            createRestrictionClassDescriptionElem(restriction.asAllValuesFromRestriction().getAllValuesFrom(), relation);        
        } else if (restriction.isSomeValuesFromRestriction()) {
            relation.setValue(OWL.someValuesFrom.getLocalName());
            createRestrictionClassDescriptionElem(restriction.asSomeValuesFromRestriction().getSomeValuesFrom(), relation);        
        } else if (restriction.isHasValueRestriction()) {
            relation.setValue(OWL.hasValue.getLocalName());
            createRestrictionClassDescriptionElem(restriction.asHasValueRestriction().getHasValue(), relation);        
        }
    }
    
    /**
     * 
     * @param value Value of RDF node in ontoclass.
     * @param relation Relation which is being described (collecting parameter)
     */
    private void createRestrictionClassDescriptionElem(RDFNode value, Relation relation) {
        if (value.canAs(OntClass.class)) {            
            collectRelatedClassDescription((OntClass) value.as(OntClass.class), relation);
        }else if (value instanceof Resource) { 
            Resource resource = (Resource) value;
            setRelationNameAndDescriptionFromResource(relation, resource);
        } else if (value instanceof Literal) {
            logger.debug( ((Literal) value).getLexicalForm() );
        } else {
            logger.debug( value );
        }
    }
    
    /**
     * 
     * @param boolClass Ontoclass of this type.
     * @param relation Relation which is being described (collecting parameter)
     */
    private  void createBooleanClassDescription(BooleanClassDescription boolClass, Relation relation) {
        Iterator i = boolClass.listOperands();
        while (i.hasNext()) {
            collectRelatedClassDescription((OntClass) i.next(), relation);
        }        
    }
    
    /**
     * Sets the name and description of a relation from a resource
     * 
     * @param relation The relation to fill data in
     * @param resource The resource from which data will be extracted
     */
    private static void setRelationNameAndDescriptionFromResource(Relation relation, Resource resource) {        
        if(resource instanceof Individual){            
            ConceptDescription relatedConceptDescription = OntologyHelper.createConceptDescription(resource); 
            relation.getConceptDescriptions().add(relatedConceptDescription);
        }else{                
            relation.setOnproperty(resource.getURI());   
            relation.setDescription(OntologyHelper.getPublicName(resource));
        }    
    }
    
    private static boolean isOwlInternalClass(OntClass ontClass){
        return (!ontClass.isAnon()) && (ontClass.getURI() != null) && 
        			(ontClass.getURI().startsWith(OWL.getURI()));
    }
    
    
}
