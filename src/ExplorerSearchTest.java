import static org.junit.Assert.*;
import org.junit.Test;

public class ExplorerSearchTest {
    @Test
    public void testReachableArea_someUnreachable() {
        int[][] island = {
            {1,1,1,3,1,1},
            {3,2,3,1,3,1},
            {1,1,1,1,3,3},
            {3,1,2,1,0,1},
            {1,1,1,2,1,1},
        };
        int actual = ExplorerSearch.reachableArea(island);
        assertEquals(14, actual);
    }

    // Add more tests here!
    // Come up with varied cases
    @Test
    public void testExplorerLocation_reachableTest() {
        int[][] island = {
            {1,1,1,3,1,1},
            {3,2,3,1,3,1},
            {1,1,1,1,3,3},
            {3,1,2,1,0,1},
            {1,1,1,2,1,1},
        };
        int[] expected = {3, 4};
        assertArrayEquals(expected, ExplorerSearch.explorerLocation(island));
    }

    @Test
    public void testExplorerLocation_centerOfGrid() {
        int[][] island = {
            {1,1,1,3,1,1,1},
            {3,2,3,1,3,1,1},
            {1,1,1,0,3,3,1},
            {3,1,2,1,1,1,1},
            {1,1,1,2,1,1,1},
        };
        int[] expected = {2, 3};
        assertArrayEquals(expected, ExplorerSearch.explorerLocation(island));
    }

    @Test
    public void testExplorerLocation_noExplorerError() {
        int[][] island = {
            {1,1,1,3,1,1,1},
            {3,2,3,1,3,1,1},
            {1,1,1,1,3,3,1},
            {3,1,2,1,1,1,1},
            {1,1,1,2,1,1,1},
        };
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ExplorerSearch.explorerLocation(island);
        });
        assertEquals("No explorer present", exception.getMessage());
    }

    @Test
    public void testExplorerLocation_rightCorner() {
        int[][] island = {
            {1,1,1,3,1,1,1},
            {3,2,3,1,3,1,1},
            {1,1,1,1,3,3,1},
            {3,1,2,1,1,1,1},
            {1,1,1,2,1,1,0},
        };
        int[] expected = {4, 6};
        assertArrayEquals(expected, ExplorerSearch.explorerLocation(island));
    }

}
