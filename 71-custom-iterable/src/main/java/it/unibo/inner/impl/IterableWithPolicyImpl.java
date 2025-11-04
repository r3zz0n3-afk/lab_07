package it.unibo.inner.impl;
import java.util.NoSuchElementException;
import it.unibo.inner.api.IterableWithPolicy;
import it.unibo.inner.api.Predicate;

public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T> {
    //mettere a posto pk usa un array non una arraylist
    private T[] elements;
    private Predicate<T> filter;
    
    public IterableWithPolicyImpl(final T[] elements, final Predicate<T> predicate) {
        this.elements = elements;
       this.setIterationPolicy(predicate);
    }
    public IterableWithPolicyImpl(final T[] elements) {
        this(elements, new Predicate<T>() {
            @Override
            public boolean test(T elem) {
                return true; 
            }
        });
    }
    
    public void setIterationPolicy(final Predicate<T> filter) {
        this.filter = filter;
    }
    public java.util.Iterator<T> iterator(){
        return new InnerIterator();
    }
    private class InnerIterator implements java.util.Iterator<T> {
        private int currentIndex = 0;

        public boolean hasNext() {
            while(currentIndex < elements.length){
                if(filter.test(elements[currentIndex])){
                    return true;
                }
                currentIndex++;
            }
            return false;
        }

        public T next()  {
            if(!hasNext()) {
                throw new NoSuchElementException();  
            }
            return elements[currentIndex++];  
        }
    }
    /*  private ArrayList<T> elements;
    private Predicate<T> filter;
    public IterableWithPolicyImpl(ArrayList<T> elements, Predicate<T> filter) {
        this.elements = elements;
        this.setIterationPolicy(filter);
    }
    public IterableWithPolicyImpl(ArrayList<T> elements) {
        this(elements, new Predicate<T>() {
            @Override
            public boolean test(T elem) {
                return true; 
            }
        });
    }
    
    public void setIterationPolicy(final Predicate<T> filter) {
        this.filter = filter;
    }

    public java.util.Iterator<T> iterator(){
        return new InnerIterator();
    }

    private class InnerIterator implements java.util.Iterator<T> {
        private int currentIndex = 0;

        public boolean hasNext() {
            int nElem = 0;
            while(currentIndex < elements.size()){
                if(filter.test(elements.get(currentIndex))){
                    
                    return true;
                }
                currentIndex++;
            }
            //throw a exception if not found another element
            return false;
        }

        public T next()  {
            return hasNext() ? elements.get(currentIndex) : new NoSuchElementException();  
        }
    }*/


}
