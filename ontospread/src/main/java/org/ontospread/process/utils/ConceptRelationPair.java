package org.ontospread.process.utils;

import org.ontospread.xmlbind.TypeHierarchy;

public class ConceptRelationPair {
    private TypeHierarchy hierarchy;
    private String conceptUri;
	private String onproperty;

    public ConceptRelationPair(TypeHierarchy hierarchy, String conceptUri) {
        super();
        this.hierarchy = hierarchy;
        this.conceptUri = conceptUri;
    }

    public ConceptRelationPair(TypeHierarchy hierarchy, String objectConceptUri, String onproperty) {
        super();
        this.hierarchy = hierarchy;
        this.conceptUri = objectConceptUri;
        this.onproperty = onproperty;
	}

	public String getConceptUri() {
        return conceptUri;
    }

    public void setConceptUri(String conceptUri) {
        this.conceptUri = conceptUri;
    }

    
    public TypeHierarchy getHierarchy() {
		return hierarchy;
	}

	public void setHierarchy(TypeHierarchy hierarchy) {
		this.hierarchy = hierarchy;
	}

	public String toString(){
        return "ConceptRelationPair ("+this.hierarchy+", "+this.conceptUri+" , "+this.getOnproperty()+")";
    }


	public String getOnproperty() {
		return onproperty;
	}


	public void setOnproperty(String onproperty) {
		this.onproperty = onproperty;
	}
    
}
