package com.klob.Bomberklob.resources;

public class PathFindingNode {

	
	/**
	 * Manhattan heuristic
	 */
	public int F;
	/**
	 * Addition of heuristics and cost of travel
	 */
	public int G;
	/**
	 * Cost of travel from the starting square
	 */
	public int H;
	/**
	 * Father node
	 */
	public Point father;

	/**
	 * Creates a node within the parameters entered
	 * @param f Manhattan heuristic
	 * @param g Addition of heuristics and cost of travel
	 * @param h Cost of travel from the starting square
	 * @param father Father node
	 */
	public PathFindingNode(int f, int g, int h, Point father) {
		super();
		F = f;
		G = g;
		H = h;
		this.father = father;
	}
}
