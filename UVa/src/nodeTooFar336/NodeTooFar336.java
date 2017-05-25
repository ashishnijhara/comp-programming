package nodeTooFar336;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class NodeTooFar336 {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		int caseNo = 0;
		if (!scanner.hasNextLine()) {
			scanner.close();
			return;
		}

		int edges = scanner.nextInt();
		if (edges == 0) {
			scanner.close();
			return;
		}

		Graph<Integer> graph = new Graph<>();

		for (int i = 0; i < edges; i++) {
			int head = scanner.nextInt();
			int tail = scanner.nextInt();
			graph.addEdge(head, tail);
			graph.addEdge(tail, head);
		}

		// assert (graph.length() == nc) : "\nThe graph vertices should be same
		// as the input vertices";

		int initialVertex = scanner.nextInt();
		int ttl = scanner.nextInt();

		while (initialVertex != 0 && ttl != 0) {
			int notReachable = graph.notReachableVertices(initialVertex, ttl);
			System.out.println("Case " + ++caseNo + ": " + notReachable + " nodes not reachable from node "
					+ initialVertex + " with TTL = " + ttl + ".");
			initialVertex = scanner.nextInt();
			ttl = scanner.nextInt();
		}
		if (initialVertex == 0 && ttl == 0) {
			scanner.close();
			return;
		}

	}

	static class Graph<T> {
		private Map<T, Set<Edge<T>>> graphDS;

		public Graph() {
			graphDS = new HashMap<>();
		}

		public int length() {
			return graphDS.size();
		}

		public int notReachableVertices(T initialVertex, int ttl) {
			Set<T> visitedVertices = new HashSet<>();
			reachableVertices(initialVertex, ttl, visitedVertices);
			return length() - visitedVertices.size();
		}

		public Set<T> getNeighours(T from) {
			Set<Edge<T>> edges = graphDS.get(from);

			Set<T> neighbouringVertices = new HashSet<>();
			for (Edge<T> edge : edges) {
				neighbouringVertices.add(edge.getToVertex());
			}
			return neighbouringVertices;
		}

		private void reachableVertices(T initialVertex, int ttl, Set<T> visitedVertices) {
			while (ttl > 0) {
				--ttl;
				visitedVertices.add(initialVertex);
				Set<T> neighours = getNeighours(initialVertex);
				for (T vertex : neighours) {
					visitedVertices.add(vertex);
					reachableVertices(vertex, ttl, visitedVertices);
				}
			}
		}

		public void addEdge(T from, T to) {
			addVertex(from);
			addVertex(to);
			Edge<T> v1v2 = new Edge<>(from, to);
			Set<Edge<T>> edgesForV1 = graphDS.get(from);
			edgesForV1.add(v1v2);
		}

		public void addVertex(T vertex) {
			if (graphDS.containsKey(vertex))
				return;
			Set<Edge<T>> edges = new HashSet<>();
			graphDS.put(vertex, edges);
		}

	}

	static class Edge<T> {
		private T from;
		private T to;

		public Edge(T vertexOne, T vertexTwo) {
			this.from = vertexOne;
			this.to = vertexTwo;
		}

		public T getFromVertex() {
			return from;
		}

		public T getToVertex() {
			return to;
		}

		@Override
		public String toString() {
			return "Edge [from=" + from + ", to=" + to + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((from == null) ? 0 : from.hashCode());
			result = prime * result + ((to == null) ? 0 : to.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Edge<T> other = (Edge<T>) obj;
			if (from == null) {
				if (other.from != null)
					return false;
			} else if (!from.equals(other.from))
				return false;
			if (to == null) {
				if (other.to != null)
					return false;
			} else if (!to.equals(other.to))
				return false;
			return true;
		}
	}
}
