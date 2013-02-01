package org.ontospread.process;


import junit.framework.TestCase;

public class OntoSpreadProcessTest extends TestCase {

	public void testOntoSpreadProcess(){		
		OntoSpreadProcess ontoSpreadProcess = OntoSpreadTestUtils.
		createDefaultOntoSpreadProcess(1, 1, 10.0);		
		assertNotNull(ontoSpreadProcess);
	}
}
