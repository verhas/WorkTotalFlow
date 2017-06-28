package javax0.workflow.simple.builder;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

abstract class BldMap<R,B extends Named<R>> {

    private Map<R,B> map = new HashMap<>();

    abstract B factory();

    Set<R> keySet(){
        return map.keySet();
    }

    boolean containsKey(R name){
        return map.containsKey(name);
    }

    B get(R name){
        if( map.containsKey(name)){
            return map.get(name);
        }else{
            B b = factory();
            b.name = name;
            map.put(name,b);
            return b;
        }
    }

}
