package algorithms;

import entitys.Position;
import exceptions.ElementNotFoundException;
import exceptions.EmptyCollectionException;
import implementation_classes.Map;

import java.util.Iterator;

public class ShortestPathBFS implements Algorithm {
    @Override
    public int nextMove(Map map, Position current, Position target) throws EmptyCollectionException, ElementNotFoundException {

        Iterator<Position> iterator = map.iteratorShortestPathBFS( current, target);
        current = iterator.next();
        if (!iterator.hasNext()) {
            return map.getIndex(current);
        }
        Position nextPosition = iterator.next();
        return map.getIndex(nextPosition);

    }
}
