package mouse.project.algorithm.impl.sweep;

import mouse.project.algorithm.impl.trapezoid.*;

import java.util.*;

public class SweepLineScannerImpl implements SweepLineScanner {
    @Override
    public EdgesSet scanAndCreate(VerticesSet vertices, VertexEdgeMap map) {
        Collection<Vertex> allVertices = vertices.getAllSortedByY();

        List<LeftRightPair> pairs = processVertices(map, allVertices);

        List<Edge> edges = topologicalSort(pairs);
        EdgesSetImpl edgesSet = new EdgesSetImpl();
        edgesSet.addAll(edges);
        return edgesSet;
    }

    private List<LeftRightPair> processVertices(VertexEdgeMap map,
                                 Collection<Vertex> allVertices) {
        Status status = new Status();
        List<LeftRightPair> pairs = new ArrayList<>();
        for (Vertex v : allVertices) {
            int y = v.position().y();
            List<Edge> leftToRight = map.getLeftToRight(v);
            List<Edge> addedEdges = new ArrayList<>();
            List<Edge> tempList = new ArrayList<>();
            for (Edge e : leftToRight) {
                if (status.remove(e)) {
                    continue;
                }
                tempList.add(e);
            }
            Edge left = status.findLeft(v);
            if (left != null) {
                addedEdges.add(left);
            }
            Edge right = status.findRight(v);
            addedEdges.addAll(tempList);
            status.addAll(tempList, y);
            if (right != null) {
                addedEdges.add(right);
            }
            mapToPairs(pairs, addedEdges);
        }
        return pairs;
    }
    private List<Edge> topologicalSort(List<LeftRightPair> pairs) {
        Map<Edge, List<Edge>> graph = buildGraph(pairs);
        Map<Edge, Boolean> visited = new HashMap<>();
        List<Edge> sortedEdges = new ArrayList<>();

        for (Edge edge : graph.keySet()) {
            if (!visited.containsKey(edge)) {
                dfs(edge, graph, visited, sortedEdges);
            }
        }

        Collections.reverse(sortedEdges);
        return sortedEdges;
    }

    private void dfs(Edge edge, Map<Edge, List<Edge>> graph, Map<Edge, Boolean> visited, List<Edge> edges) {
        visited.put(edge, true);

        List<Edge> neighbors = graph.getOrDefault(edge, new ArrayList<>());
        for (Edge neighbor : neighbors) {
            if (!visited.containsKey(neighbor)) {
                dfs(neighbor, graph, visited, edges);
            }
        }

        edges.add(edge);
    }

    private Map<Edge, List<Edge>> buildGraph(List<LeftRightPair> pairs) {
        Map<Edge, List<Edge>> graph = new HashMap<>();

        for (LeftRightPair pair : pairs) {
            Edge left = pair.left();
            Edge right = pair.right();

            graph.putIfAbsent(left, new ArrayList<>());
            graph.putIfAbsent(right, new ArrayList<>());
            graph.get(left).add(right);
        }

        return graph;
    }

    private void mapToPairs(List<LeftRightPair> pairs, List<Edge> addedEdges) {
        Edge prev = null;
        for (Edge e : addedEdges) {
            if (prev == null) {
                prev = e;
                continue;
            }
            pairs.add(LeftRightPair.of(prev,e));
            prev = e;
        }
    }

    private record LeftRightPair(Edge left, Edge right) {
        public static LeftRightPair of(Edge left, Edge right) {
            return new LeftRightPair(left, right);
        }
    }


    private static class Status {
        private final List<Edge> statusList;

        public Status() {
            statusList = new ArrayList<>();
        }

        public Edge findLeft(Vertex v) {
            Edge edge = new EdgeImpl(v, v);
            int indexOfTarget = findIndexOf(edge, v.position().y());
            if (indexOfTarget == 0) {
                return null;
            }
            return statusList.get(indexOfTarget-1);
        }
        public Edge findRight(Vertex v) {
            Edge edge = new EdgeImpl(v, v);
            int indexOfTarget = findIndexOf(edge, v.position().y());
            if (indexOfTarget == statusList.size()) {
                return null;
            }
            return statusList.get(indexOfTarget);
        }

        public boolean remove(Edge edge) {
            return statusList.remove(edge);
        }

        private int findIndexOf(Edge node, int y) {
            int index = findIndexGeneral(node, y);
            if (index < 0) {
                index = -(index + 1);
            }
            return index;
        }

        private int findIndexGeneral(Edge input, int y) {
            return Collections.binarySearch(statusList, input, Comparator.comparingInt(e -> EdgeHelper.getX(e, y)));
        }


        public void addAll(List<Edge> tempList, int y) {
            if (tempList.isEmpty()) {
                return;
            }
            Edge edge = tempList.get(0);
            int index = findIndexOf(edge, y);
            statusList.addAll(index, tempList);
        }
    }

}
