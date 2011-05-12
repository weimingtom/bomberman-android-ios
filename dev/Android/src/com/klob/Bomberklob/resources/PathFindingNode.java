package com.klob.Bomberklob.resources;

public class PathFindingNode {

	public int F, G , H;
	public Point father;
	
	public PathFindingNode(int f, int g, int h, Point father) {
		super();
		F = f;
		G = g;
		H = h;
		this.father = father;
	}
}
