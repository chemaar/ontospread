package org.ontospread.pk;


public class KnowledgeSourcePK {

   private String id;
   

   public KnowledgeSourcePK(String id) {
        super();
        this.id = id;
    }

   public KnowledgeSourcePK() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String toString(){
        return this.getId();
    }
    
}
