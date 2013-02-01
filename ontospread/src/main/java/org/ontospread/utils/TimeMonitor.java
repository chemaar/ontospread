package org.ontospread.utils;

public class TimeMonitor {

    long before = 0;
    private static long NOT_INIT_TIME=-1;
    
    public TimeMonitor() {
        this.before =  NOT_INIT_TIME;
    }
    
    public void start() {
        before = System.currentTimeMillis();
    }
    
    public long stop() {
        if (before ==  NOT_INIT_TIME) {
            return  NOT_INIT_TIME;
        } else {
            return System.currentTimeMillis() - before;
        }
    }
    
}
