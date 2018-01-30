package com.systema.maze.domain;

public class Edge implements Comparable<Edge> {

    private Vertex source, destination;
    private int weight;
    public Edge(Vertex source, Vertex destination){
        this(source, destination, 1);
    }
    public Edge(Vertex source, Vertex destination, int weight){
        this.source = (source.getLabel().compareTo(destination.getLabel()) <= 0) ? source : destination;
        this.destination = (this.source == source) ? destination : source;
        this.weight = weight;
    }

    public Vertex getNeighbor(Vertex current){
        if(!(current.equals(source) || current.equals(destination))){
            return null;
        }

        return (current.equals(source)) ? destination : source;
    }

    public Vertex getSource(){
        return this.source;
    }
    public Vertex getDestination(){
        return this.destination;
    }
    public int getWeight(){
        return this.weight;
    }
    public void setWeight(int weight){
        this.weight = weight;
    }
    public int compareTo(Edge other){
        return this.weight - other.weight;
    }

    public String toString(){
        return "({" + source + ", " + destination + "}, " + weight + ")";
    }

    public int hashCode(){
        return (source.getLabel() + destination.getLabel()).hashCode();
    }
    public boolean equals(Object other){
        if(!(other instanceof Edge)){
            return false;
        }

        Edge e = (Edge)other;

        return e.source.equals(this.source) && e.destination.equals(this.destination);
    }


}
