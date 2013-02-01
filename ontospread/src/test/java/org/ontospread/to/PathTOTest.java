package org.ontospread.to;

import junit.framework.TestCase;

public class PathTOTest extends TestCase {

	public void testPathTO(){
		PathTO pathTO = new PathTO("uri",new String[5]);
		assertEquals(5, pathTO.getRelationsUri().length
				);
	}
}
