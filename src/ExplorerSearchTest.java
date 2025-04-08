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

    @Test
    public void testReachableArea_noneUnreachable() {
        int[][] island = {
            {2,2,3,3,3,3},
            {3,2,3,1,3,2},
            {1,1,1,1,3,3},
            {3,1,2,2,0,3},
            {1,1,1,2,3,2},
        };
        int actual = ExplorerSearch.reachableArea(island);
        assertEquals(1, actual);
    }

    @Test
    public void testReachableArea_oneSpaceToTravel() {
        int[][] island = {
            {2,2,3,3,3,3},
            {3,2,3,1,3,2},
            {1,1,1,1,3,3},
            {3,1,2,2,0,1},
            {1,1,1,2,3,2},
        };
        int actual = ExplorerSearch.reachableArea(island);
        assertEquals(2, actual);
    }

    @Test
    public void testReachableArea_wholeArea() {
        int[][] island = {
            {1,1,1,1,1,1},
            {1,1,1,1,1,1},
            {1,1,1,1,1,1},
            {1,1,1,1,0,1},
            {1,1,1,1,1,1},
        };
        int actual = ExplorerSearch.reachableArea(island);
        assertEquals(30, actual);
    }

    @Test
    public void testReachableArea_halfArea() {
        int[][] island = {
            {2,3,2,2,3,3},
            {3,2,3,3,2,2},
            {2,2,2,1,1,1},
            {1,1,1,1,0,1},
            {1,1,1,1,1,1},
        };
        int actual = ExplorerSearch.reachableArea(island);
        assertEquals(15, actual);
    }

    @Test
    public void testReachableArea_noExplorer() {
        int[][] island = {
            {2,3,2,2,3,3},
            {3,2,3,3,2,2},
            {2,2,2,1,1,1},
            {1,1,1,1,1,1},
            {1,1,1,1,1,1},
        };
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ExplorerSearch.reachableArea(island);
        });
        assertEquals("No explorer present", exception.getMessage());
    }

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

    @Test
    public void testPossibleMoves_allDirectionsBlockedByWalls() {
        int[][] island = {
            {2, 2, 2},
            {2, 0, 2},
            {2, 2, 2}
        };
        int[] location = {1, 1};
        List<int[]> moves = ExplorerSearch.possibleMoves(island, location);
        assertTrue(moves.isEmpty());
    }

    @Test
    public void testPossibleMoves_partialEdge() {
        int[][] island = {
            {0, 1, 1}
        };

        int[] location = {0,0};
        List<int[]> moves = ExplorerSearch.possibleMoves(island, location);
        Set<String> moveSet = toSet(moves);

        assertEquals(1, moves.size());
        assertTrue(moveSet.contains("0,1")); // only right
    }

    @Test
    public void testPossibleMoves_oneOpen_twoWalls_oneEdge() {
        // Salamander at (1, 1)
        // Up is W, down is wall, left is edge (0), right is open
        int[][] island = {
            {2, 2, 3},
            {1, 0, 1},
            {3, 3, 2}
        };
        int[] location = {1, 1};
        List<int[]> moves = ExplorerSearch.possibleMoves(island, location);
        Set<String> moveSet = toSet(moves);

        assertEquals(2, moves.size());
        assertTrue(moveSet.contains("1,2"));
        assertTrue(moveSet.contains("1,0"));
    }

    @Test
    public void testPossibleMoves_blockedAboveByWall() {
        int[][] island = {
            {1, 3, 1},
            {1, 0, 1},
            {1, 1, 1}
        };
        int[] location = {1, 1};
        List<int[]> moves = ExplorerSearch.possibleMoves(island, location);
        Set<String> moveSet = toSet(moves);

        assertFalse(moveSet.contains("0,1")); // up blocked
        assertTrue(moveSet.contains("2,1"));  // down open
        assertTrue(moveSet.contains("1,0"));  // left open
        assertTrue(moveSet.contains("1,2"));  // right open
    }

    @Test
    public void testPossibleMoves_blockedBelowByWall() {
        int[][] island = {
            {1, 1, 1},
            {1, 0, 1},
            {1, 2, 1}
        };
        int[] location = {1, 1};
        Set<String> moveSet = toSet(ExplorerSearch.possibleMoves(island, location));

        assertTrue(moveSet.contains("0,1"));  // up open
        assertFalse(moveSet.contains("2,1")); // down blocked
        assertTrue(moveSet.contains("1,0"));  // left open
        assertTrue(moveSet.contains("1,2"));  // right open
    }

    @Test
    public void testPossibleMoves_blockedLeftByWall() {
        int[][] island = {
            {1, 1, 1},
            {2, 0, 1},
            {1, 1, 1}
        };
        int[] location = {1, 1};
        Set<String> moveSet = toSet(ExplorerSearch.possibleMoves(island, location));

        assertTrue(moveSet.contains("0,1"));  // up open
        assertTrue(moveSet.contains("2,1"));  // down open
        assertFalse(moveSet.contains("1,0")); // left blocked
        assertTrue(moveSet.contains("1,2"));  // right open
    }

    @Test
    public void testPossibleMoves_blockedRightByWall() {
        int[][] island = {
            {1, 1, 1},
            {1, 0, 2},
            {1, 1, 1}
        };
        int[] location = {1, 1};
        Set<String> moveSet = toSet(ExplorerSearch.possibleMoves(island, location));

        assertTrue(moveSet.contains("0,1"));  // up open
        assertTrue(moveSet.contains("2,1"));  // down open
        assertTrue(moveSet.contains("1,0"));  // left open
        assertFalse(moveSet.contains("1,2")); // right blocked
    }

    @Test
    public void testPossibleMoves_topLeftCornerWithOneOpen() {
        int[][] island = {
            {0, 2},
            {2, 1}
        };
        int[] location = {0, 0};
        List<int[]> moves = ExplorerSearch.possibleMoves(island, location);

        assertTrue(moves.isEmpty()); // surrounded by walls/edges
    }

    @Test
    public void testPossibleMoves_rightEdgeWithOpenLeft() {
        int[][] island = {
            {1, 1, 0}
        };
        int[] location = {0, 2};
        List<int[]> moves = ExplorerSearch.possibleMoves(island, location);
        Set<String> moveSet = toSet(moves);

        assertEquals(1, moves.size());
        assertTrue(moveSet.contains("0,1"));
    }

    private Set<String> toSet(List<int[]> list) {
        Set<String> set = new HashSet<>();
        for (int[] arr : list) {
            set.add(arr[0] + "," + arr[1]);
        }
        return set;
    }
}
