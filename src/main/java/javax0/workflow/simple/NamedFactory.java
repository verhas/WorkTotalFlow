package javax0.workflow.simple;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Named objects have name. A NamedFactory contains named objects and you can get the object based on the name.
 * @param <R> the name type, usually String.
 * @param <B> the type of the object we store in the map
 */
abstract class NamedFactory<R, B extends Named<R>> {

    private final Map<R, B> map = new HashMap<>();

    /**
     * Should be provided by concrete implementation creating a new B()
     * @return the new instance of B.
     */
    abstract B factory();

    Set<R> keySet() {
        return map.keySet();
    }

    /**
     * Checks if the objevt was already created.
     *
     * @param name the name of the object
     * @return true if the object was already created
     */
    boolean isAlreadyCreated(R name) {
        return map.containsKey(name);
    }

    /**
     * Get the object based on the name. If the object does not exist yet then this method
     * calls the {@link #factory()} to created one.
     * @param name or identifier of the object
     * @return the object
     */
    B get(R name) {
        if (map.containsKey(name)) {
            return map.get(name);
        } else {
            B b = factory();
            b.name = name;
            map.put(name, b);
            return b;
        }
    }

}
