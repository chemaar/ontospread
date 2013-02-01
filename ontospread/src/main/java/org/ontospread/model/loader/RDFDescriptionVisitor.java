package org.ontospread.model.loader;

import java.util.LinkedList;
import java.util.List;

import org.ontospread.to.ConceptTO;

import com.hp.hpl.jena.rdf.model.AnonId;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.RDFVisitor;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;


public class RDFDescriptionVisitor implements RDFVisitor {

    
    private static final String DUMMY_VALUE = "FIXME";
    
    private ConceptTO conceptTO;    
    private List<String> altLabels = new LinkedList<String>();

    public RDFDescriptionVisitor(){
        previousConfig();
    }

    public void previousConfig() {
        this.conceptTO = new ConceptTO();
        this.conceptTO.setDescription("");       
        this.altLabels = new LinkedList<String>();
    }
    
    
    public ConceptTO getConceptTO() {
        return conceptTO;
    }
    

    public List<String> getAltLabels() {
		return altLabels;
	}

	public void setAltLabels(List<String> altLabels) {
		this.altLabels = altLabels;
	}


	public Object visitBlank(Resource r, AnonId id) {
        for(StmtIterator iterator = r.listProperties(); iterator.hasNext();){
            Statement stmt = iterator.nextStatement();
            stmt.getObject().visitWith(this);
        }        
        return null;
     }

     public Object visitURI(Resource r, String uri) {
        for(StmtIterator iterator = r.listProperties(); iterator.hasNext();){
            Statement stmt = iterator.nextStatement();
            this.applyStatement(stmt,this.conceptTO);
//            Resource  subject   = stmt.getSubject();    
//            Property  predicate = stmt.getPredicate(); 
//            RDFNode   object    = stmt.getObject();     
//            subject.visitWith(this);
//            predicate.visitWith(this);
//            object.visitWith(this);
        }
        return null;  
     }

     public void applyStatement (Statement stmt, ConceptTO conceptTO){
         Resource  subject   = stmt.getSubject(); 
         Property  predicate = stmt.getPredicate();
         RDFNode   object    = stmt.getObject();
     }
     
     private String getStringValue(RDFNode object){
         return (object != null && object instanceof Literal?((Literal)object).getString():(object==null?DUMMY_VALUE:object.toString()));
     }
     
     public Object visitLiteral(Literal literal) {
        //System.out.println("Literal: "+l+" Lang: "+l.getLanguage()+" Value: "+l.getString());
        return null; 
     }

    

     private static void print(Statement statement) {
//         System.out.println("\tS: "+statement.getSubject().getURI()+
//                 " P: " +statement.getPredicate().getURI()+
//                 " O:"+statement.getObject()+"  "
//                 +statement.getObject().getClass());
       }
}
