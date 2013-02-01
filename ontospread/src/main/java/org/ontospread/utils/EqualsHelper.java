package org.ontospread.utils;

import java.util.Date;

public abstract class EqualsHelper {

    public static boolean optionalEquals(Object o1, Object o2) {
        return (o1 == null && o2 == null) || (o1 != null && o2 != null && o1.equals(o2));
    }
    public static boolean equalsDates(Date d1,Date d2){
       return (d1 == null && d2== null ) || (d1 !=null && d2 != null && d1.compareTo(d2)==0);
    }
}
