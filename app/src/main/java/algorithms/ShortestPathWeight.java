package algorithms;

import entitys.Position;
import exceptions.ElementNotFoundException;
import exceptions.EmptyCollectionException;
import implementation_classes.Map;

import java.util.Iterator;

public class ShortestPathWeight implements Algorithm {
    @Override
    public int nextMove(Map map, Position current, Position target) throws EmptyCollectionException, ElementNotFoundException {

        Iterator<Position> iterator =map.iteratorShortestPathWeight(current,target);

        current = iterator.next();
        if (!iterator.hasNext()) {

            return map.getIndex(current);
        }
        Position nextPosition =  iterator.next();
        return map.getIndex(nextPosition);
    }

}
