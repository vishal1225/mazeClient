package com.systema.maze.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by vssharma on 29/01/2018.
 */
public class MazeGameTest {

    private MazeGame  mazeGame;
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
    }

    @Test
    public void testCreatedMaze() {
        Assert.assertEquals(false, mazeGame.isAt_goal());
        Assert.assertEquals("cf6f9a9ba918de3bc625163bb620d0b5", mazeGame.getGame_id());
        Assert.assertEquals(false, mazeGame.getLinked_neighbors().isEast());
        Assert.assertEquals(false, mazeGame.getLinked_neighbors().isWest());
        Assert.assertEquals(true, mazeGame.getLinked_neighbors().isNorth());
        Assert.assertEquals(true, mazeGame.getLinked_neighbors().isSouth());
        Assert.assertEquals(6, mazeGame.getPosition()[0]);
        Assert.assertEquals(34, mazeGame.getPosition()[1]);
    }

}