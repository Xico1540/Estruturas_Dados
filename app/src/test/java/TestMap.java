import exceptions.InvalidVerticeNumberException;
import implementation_classes.Map;
import org.junit.jupiter.api.*;


import static org.junit.jupiter.api.Assertions.assertThrows;


public class TestMap {

    @Test
    public void directedMap()   {

        Map map1 = new Map();

        //test directedMap with numVertices <10
        Exception exception = assertThrows(InvalidVerticeNumberException.class, () -> map1.directedMap(0, 0.30) );
        Assertions.assertEquals("The number of vertices must be between 10 and 100", exception.getMessage());

        //test directedMap with numVertices >100
         exception = assertThrows(InvalidVerticeNumberException.class, () -> map1.directedMap(101, 0.30) );
        Assertions.assertEquals("The number of vertices must be between 10 and 100", exception.getMessage());

        //test directedMap with density <0.25
         exception = assertThrows(InvalidVerticeNumberException.class, () -> map1.directedMap(10, 0) );
        Assertions.assertEquals("The density must be between 1 and 0.25", exception.getMessage());

        //test directedMap with density >1
        exception = assertThrows(InvalidVerticeNumberException.class, () -> map1.directedMap(10, 2) );
        Assertions.assertEquals("The density must be between 1 and 0.25", exception.getMessage());
    }

    @Test
    public void nonDirectedMap()   {

        Map map1 = new Map();

        //test directedMap with numVertices <10
        Exception exception = assertThrows(InvalidVerticeNumberException.class, () -> map1.nonDirectedMap(0, 0.30) );
        Assertions.assertEquals("The number of vertices must be between 10 and 100", exception.getMessage());

        //test directedMap with numVertices >100
        exception = assertThrows(InvalidVerticeNumberException.class, () -> map1.nonDirectedMap(101, 0.30) );
        Assertions.assertEquals("The number of vertices must be between 10 and 100", exception.getMessage());

        //test directedMap with density <0.25
        exception = assertThrows(InvalidVerticeNumberException.class, () -> map1.nonDirectedMap(10, 0) );
        Assertions.assertEquals("The density must be between 1 and 0.25", exception.getMessage());

        //test directedMap with density >1
        exception = assertThrows(InvalidVerticeNumberException.class, () -> map1.nonDirectedMap(10, 2) );
        Assertions.assertEquals("The density must be between 1 and 0.25", exception.getMessage());
    }

    @Test
    public void Network() throws InvalidVerticeNumberException {
        Map map1 = new Map();
        map1.directedMap(20, 0.30);
        Assertions.assertEquals(20, map1.size());

        //test expandCapacity EXPAND_BY=2, 2*20=40
        map1.addVertex(null);
        Assertions.assertEquals(40, map1.size());

    }


}
