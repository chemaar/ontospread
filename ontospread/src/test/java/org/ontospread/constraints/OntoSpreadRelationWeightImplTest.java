package org.ontospread.constraints;

import junit.framework.TestCase;

public class OntoSpreadRelationWeightImplTest extends TestCase {

	public void testDefaultValue(){
		OntoSpreadRelationWeight relationWeight = new OntoSpreadRelationWeightImpl();
		assertEquals(OntoSpreadRelationWeight.DEFAULT_VALUE, relationWeight.getWeight(""));
	}
}
