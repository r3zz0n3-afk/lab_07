package it.unibo.nestedenum;

import java.util.Comparator;


/**
 * Implementation of {@link MonthSorter}.
 */

public final class MonthSorterNested implements MonthSorter {
    private enum Month {
        JANUARY(31),FEBRUARY(28),MARCH(31),APRIL(30),MAY(31),JUNE(30),JULY(31),AUGUST(31),
        SEPTEMBER(30),OCTOBER(31),NOVEMBER(30),DECEMBER(31);

        private final int numDay; 
        private Month(int numDay)
        {
            this.numDay = numDay;
        }

       static private boolean isTheMonth(Month m, String s){
            return (m.name().toLowerCase().equals(s.toLowerCase()) || m.name().toLowerCase().startsWith(s.toLowerCase()));
        }
       static public Month fromString(String s)
        {
            for(Month m : Month.values()){
                if(s.length() > 1 && isTheMonth(m, s)){
                    return m;
                }
            }
            throw new IllegalArgumentException();

        }
        public int getDays(){
            return numDay;
        }
    }
    @Override
    public Comparator<String> sortByDays() {
        return new Comparator<String>() {
            @Override
            public int compare(String o1, String o2){
                return Integer.compare(Month.fromString(o1).getDays(), Month.fromString(o2).getDays());
            }
        };
    }

    @Override
    public Comparator<String> sortByOrder() {
        return new Comparator<String>() {
            @Override
            public int compare(String o1, String o2){
                return Integer.compare(Month.fromString(o1).ordinal(), Month.fromString(o2).ordinal());
            }
        };
    }
}
