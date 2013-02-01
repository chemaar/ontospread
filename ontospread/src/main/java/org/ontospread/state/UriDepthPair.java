package org.ontospread.state;

import java.io.Serializable;

public class UriDepthPair implements Serializable{
    String uri;
    int depth;

    public UriDepthPair(String uri, int depth) {
        super();
        this.uri = uri;
        this.depth = depth;
    }
    public int getDepth() {
        return depth;
    }
    public void setDepth(int prof) {
        this.depth = prof;
    }
    public String getUri() {
        return uri;
    }
    public void setUri(String uri) {
        this.uri = uri;
    }
    public String toString(){
    	return "UriDepthPair ("+uri+", "+depth+" )";
    }
	public boolean equals(Object obj) {
		return (obj != null && obj instanceof UriDepthPair && ((UriDepthPair)obj).getUri().equals(this.uri));
	}

	public int hashCode() {
		return (this.uri == null)?super.hashCode():this.uri.hashCode();
	}
}
