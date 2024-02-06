import java.util.LinkedList;
/**
 * The following class works as a cache modifier. Allowing the removing adding and checking of a cache
 * @author brycekratzer
 * @see Test.java
 */
public class Cache<T> {
    private LinkedList<T> cacheStorage;
    private int cacheSize;

    /**
     * creates a cache with a capacity
     * @param capacity the size of cache storage
     */
    public Cache(int capacity){
        cacheSize = capacity;
        cacheStorage = new LinkedList<T>();
    }

    /**
     * Checks if object is in cache, if it is then it is removed
     * @param object what is being looked for in cache
     * @return true if object is in cache and removed , false if not
     */
    public boolean getObject(T object){
        return cacheStorage.remove(object);
    }

    /**
     * adds object to top of cache. checks if the list is full before adding
     * @param object the object being added
     */
    public void addObject(T object) {
        if(cacheSize == getSize()) {
            removeObject();
            cacheStorage.addFirst(object);
        } else {
            cacheStorage.addFirst(object);
        }
    }

    /**
     * removes last object in cache, used when cache is full and needs to create
     * space
     */
    public void removeObject() {
        cacheStorage.removeLast();
    }

    /**
     * clears cache
     */
    public void clearCache() {
        cacheStorage.clear();
    }

    /**
     * gets the size of cache
     * @return the size of cache
     */
    public int getSize() {
        return cacheStorage.size();
    }
}