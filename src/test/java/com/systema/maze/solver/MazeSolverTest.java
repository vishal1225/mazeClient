package com.systema.maze.solver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.systema.maze.domain.MazeGame;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by vssharma on 29/01/2018.
 */
public class MazeSolverTest {
    private MazeGame mazeGame = null;
    private MazeSolver mazeSolver = null;
    @Before
    public void setUp() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String createMazeString = "{\n" +
                "  \"at_goal\": false, \n" +
                "  \"game_id\": \"cf6f9a9ba918de3bc625163bb620d0b5\", \n" +
                "  \"linked_neighbors\": {\n" +
                "    \"east\": false, \n" +
                "    \"north\": true, \n" +
                "    \"south\": true, \n" +
                "    \"west\": false\n" +
                "  }, \n" +
                "  \"position\": [6,34]\n" +
                "}\n";
        mazeGame = objectMapper.readValue(createMazeString, MazeGame.class);
        mazeSolver =  new MazeSolver(mazeGame);
    }


    @Test
    public void solve() throws Exception {
        String label = String.valueOf(mazeGame.getCurrentX()).concat(String.valueOf(mazeGame.getCurrentY()));
        mazeSolver.solve(mazeGame.getGraph().getVertex(label));
        Assert.assertEquals(true, mazeGame.isAt_goal());
    }

    @Test
    public void getPath() throws Exception {

    }

}