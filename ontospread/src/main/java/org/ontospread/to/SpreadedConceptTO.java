package org.ontospread.to;

import java.io.Serializable;

import org.ontospread.utils.ToStringHelper;

public class SpreadedConceptTO implements Serializable{

    private ConceptTO concept;
    private PathTO[] spreadPath;

    private double score;

    public SpreadedConceptTO() {
        /* default constructor, required to be a bean */
    }
    
    public SpreadedConceptTO(ConceptTO concept, PathTO[] spreadPath, double score) {
        this.concept = concept;
        this.spreadPath = spreadPath;
        this.score = score;
    }

    public double getScore() {
        return score;
    }

    public ConceptTO getConcept() {
        return concept;
    }
    
    public void setConcept(ConceptTO concept) {
        this.concept = concept;
    }
    
    public void setScore(double score) {
        this.score = score;
    }
    
    public PathTO[] getSpreadPath() {
        return spreadPath;
    }
    
    public void setSpreadPath(PathTO[] spreadPath) {
        this.spreadPath = spreadPath;
    }
    
    public String toString() {
        return this.getClass().getSimpleName()+"(" + getConcept() + ", " +
            getScore() + ", "+getSpreadPath().length+" ={" + ToStringHelper.arrayToString(getSpreadPath()) + "})";
    }
    
}
