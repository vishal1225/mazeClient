package com.systema.maze.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;


@JsonIgnoreProperties(ignoreUnknown = true)
public class MazeGame {
    private static final long serialVersionUID = 1L;

    private String game_id;

    private final Integer n = 100;

    private int currentX;

    private int currentY;

    public CurrentStatus getLinked_neighbors() {
        return linked_neighbors;
    }

    public void setLinked_neighbors(CurrentStatus linked_neighbors) {
        this.linked_neighbors = linked_neighbors;
    }

    private CurrentStatus linked_neighbors;
    public int[] getPosition() {
        return position;
    }

    public void setPosition(int[] position) {
        this.position = position;
        this.currentX = position[0];
        this.currentY = position[1];
    }

    private int[] position;
    private List<Vertex> traversedVertices = new LinkedList<>();
    private List<Vertex> unTraversedVertices = new LinkedList<>();
    private Graph graph = null;

    public boolean isAt_goal() {
        return at_goal;
    }

    public void setAt_goal(boolean at_goal) {
        this.at_goal = at_goal;
    }

    private boolean at_goal;

    public int getCurrentX() {
        return currentX;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }

    public void setGame_id(String game_id) {
        this.game_id = game_id;
    }

    public Integer getN() {

        return n;
    }

    public MazeGame() {
        init();
        generateMaze(getCurrentX(),getCurrentY());
    }

    private void generateMaze(Integer x, Integer y) {
        Random random = new Random();
        Vertex vertex1 = null;
        Vertex vertex2 = null;
        int r = 0;
        while (!traversedVertices.get(Integer.parseInt(String.valueOf(x).concat(String.valueOf(y+1)))).isVisited() ||
            !traversedVertices.get(Integer.parseInt(String.valueOf(x+1).concat(String.valueOf(y)))).isVisited() ||
            !traversedVertices.get(Integer.parseInt(String.valueOf(x).concat(String.valueOf(Math.abs(y-1))))).isVisited()||
            !traversedVertices.get(Integer.parseInt(String.valueOf(Math.abs(x-1)).concat(String.valueOf(y)))).isVisited()) {

            while (true) {
                vertex1 = graph.getVertex(String.valueOf(r));
                int nextRandom = random.nextInt(4);
                vertex2 = graph.getVertex(String.valueOf(nextRandom));
                vertex1.setVisited(false);
                vertex2.setVisited(false);
                graph.addVertex(vertex1, true);
                graph.addVertex(vertex2, true);
                graph.addEdge(vertex1, vertex2);
                traversedVertices.remove(r);
                traversedVertices.remove(nextRandom);
                unTraversedVertices.add(r,vertex1);
                unTraversedVertices.add(nextRandom,vertex2);
            }
        }
    }


    private void init() {
        graph = new Graph();
        for (int x = 0; x <= n; x++) {
            for (int y = 0 ; y <=n ; y++) {
                String label = String.valueOf(x)+String.valueOf(y);
                Vertex vertex = new Vertex(label, true, x, y);
                traversedVertices.add(x+y,vertex);
                graph.addVertex(vertex, true);
            }
        }
    }

//    public static void main(String[] args) {
//
//        MazeGame mazeGame = new MazeGame(100);
//        System.out.println(mazeGame.toString());
//    }


    public Graph getGraph() {
        if (this.graph != null) {
            return this.graph;
        } else {
            this.init();
            return this.getGraph();
        }

    }

    public String getGame_id() {
        return game_id;
    }


    // create by build json plugin

}
