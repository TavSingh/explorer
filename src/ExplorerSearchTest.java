import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    // Explorer location
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

    // Possible moves
    @Test
    public void testPossibleMoves_openSpace() {
        int[][] island = {
            {1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1},
            {1,1,1,0,1,1,1},
            {1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1},
        };
        int[] location = {2,3};
        List<int[]> moves = ExplorerSearch.possibleMoves(island, location);
        Set<String> moveSet = toSet(moves);
        // for (String move : moveSet) {
        //     System.out.println(move);
        // }

        assertEquals(4, moves.size());
        assertTrue(moveSet.contains("1,3")); // up
        assertTrue(moveSet.contains("2,2")); // left
        assertTrue(moveSet.contains("2,4")); // right
        assertTrue(moveSet.contains("3,3")); // down
    }

    private Set<String> toSet(List<int[]> list) {
        Set<String> set = new HashSet<>();
        for (int[] arr : list) {
            set.add(arr[0] + "," + arr[1]);
        }
        return set;
    }
}
