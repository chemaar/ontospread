package org.ontospread.to;

import java.io.Serializable;


public class ScoredConceptTO implements Serializable {

    private String conceptUri;
    private double score;
    
    public ScoredConceptTO() {
        // empty constructor
    }
    
    public ScoredConceptTO(String conceptUri, double score) {
        this.conceptUri = conceptUri;
        this.score = score;
    }
    
    /**
     * @return Returns the conceptUri.
     */
    public String getConceptUri() {
        return conceptUri;
    }
    /**
     * @param conceptUri The conceptUri to set.
     */
    public void setConceptUri(String conceptUri) {
        this.conceptUri = conceptUri;
    }
    /**
     * @return Returns the score.
     */
    public double getScore() {
        return score;
    }
    /**
     * @param score The score to set.
     */
    public void setScore(double score) {
        this.score = score;
    }
    
    public String toString(){
    	return this.getClass().getSimpleName()+"("+getConceptUri()+", "+getScore()+")";
    }
    
}
