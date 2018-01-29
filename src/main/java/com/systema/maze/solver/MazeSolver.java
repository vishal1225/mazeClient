package com.systema.maze.solver;

import com.systema.maze.domain.Edge;
import com.systema.maze.domain.Graph;
import com.systema.maze.domain.MazeGame;
import com.systema.maze.domain.Vertex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MazeSolver {
    private final List<Vertex> nodes;
    private final List<Edge> edges;

    public MazeGame getMazeGame() {
        return mazeGame;
    }

    private final MazeGame mazeGame;
    private Set<Vertex> settledNodes;
    private Set<Vertex> unSettledNodes;
    private Map<Vertex, Vertex> predecessors;
    private Map<Vertex, Integer> distance;

    public MazeSolver(MazeGame mazeGame) {
        this.mazeGame = mazeGame;
        this.nodes = new ArrayList<Vertex>(mazeGame.getGraph().getVertexes());
        this.edges = new ArrayList<Edge>(mazeGame.getGraph().getEdges());
    }

    public void solve(Vertex source) {
        settledNodes = new HashSet<Vertex>();
        unSettledNodes = new HashSet<Vertex>();
        distance = new HashMap<Vertex, Integer>();
        predecessors = new HashMap<Vertex, Vertex>();
        distance.put(source, 0);
        unSettledNodes.add(source);
        while (unSettledNodes.size() > 0) {
            Vertex node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }
        mazeGame.setAt_goal(true);
    }

    private void findMinimalDistances(Vertex node) {
        List<Vertex> adjacentNodes = getNeighbors(node);
        for (Vertex target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node)
                + getDistance(node, target)) {
                distance.put(target, getShortestDistance(node)
                    + getDistance(node, target));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }
    }

    private int getDistance(Vertex node, Vertex target) {
        int weight = 0;
        for (Edge edge : edges) {
            if (edge.getSource().equals(node)
                && edge.getDestination().equals(target)) {
                weight = edge.getWeight();
            }
        }
        return weight;
    }

    private int getShortestDistance(Vertex destination) {
        Integer d = distance.get(destination);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }
    }

    private List<Vertex> getNeighbors(Vertex node) {
        List<Vertex> neighbors = new ArrayList<Vertex>();
        for (Edge edge : edges) {
            if (edge.getSource().equals(node)
                && !isSettled(edge.getDestination())) {
                neighbors.add(edge.getDestination());
            }
        }
        return neighbors;    }

    private Vertex getMinimum(Set<Vertex> unSettledNodes) {
        Vertex minimum = null;
        for (Vertex vertex : unSettledNodes) {
            if (minimum == null) {
                minimum = vertex;
            } else {
                if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                    minimum = vertex;
                }
            }
        }
        return minimum;    }

    private boolean isSettled(Vertex vertex) {
        return settledNodes.contains(vertex);
    }
//    public LinkedList<Vertex> getPath(Vertex target) {
//        LinkedList<Vertex> path = new LinkedList<Vertex>();
//        Vertex step = target;
//        if (predecessors.get(step) == null) {
//            return null;
//        }
//        path.add(step);
//        while (predecessors.get(step) != null) {
//            step = predecessors.get(step);
//            path.add(step);
//        }
//        Collections.reverse(path);
//        return path;
//    }


}
