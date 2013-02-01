package org.ontospread.to;

import java.io.InputStream;
import java.io.Serializable;

import org.ontospread.pk.KnowledgeSourcePK;

public class KnowledgeResourceTO implements Serializable {

    private KnowledgeSourcePK knowledgeSourcePk;
    private InputStream knowledgeSourceData;
    public KnowledgeResourceTO() {
        super();
    }
   public KnowledgeResourceTO(InputStream data, KnowledgeSourcePK pk) {
        super();
        knowledgeSourceData = data;
        knowledgeSourcePk = pk;
    }
    public InputStream getKnowledgeSourceData() {
        return knowledgeSourceData;
    }
    public void setKnowledgeSourceData(InputStream knowledgeSourceData) {
        this.knowledgeSourceData = knowledgeSourceData;
    }
    public KnowledgeSourcePK getKnowledgeSourcePk() {
        return knowledgeSourcePk;
    }
    public void setKnowledgeSourcePk(KnowledgeSourcePK knowledgeSourcePk) {
        this.knowledgeSourcePk = knowledgeSourcePk;
    }
    public String toString() {     
        return this.getClass().getSimpleName()+"("+knowledgeSourcePk.toString()+", "+knowledgeSourceData.toString()+")";
    }
    
    
    

    
}
