package org.ontospread.utils;

import junit.framework.TestCase;

public class TimeMonitorTest extends TestCase {

	public void testInit(){
		TimeMonitor timer = new TimeMonitor();
		assertEquals(-1,timer.stop());
	}
	public void testTime(){
		TimeMonitor timer = new TimeMonitor();
		timer.start();
		long stop = timer.stop() ;
		assertFalse(-1==stop);
	}
}
