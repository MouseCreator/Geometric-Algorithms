package mouse.project.saver;

import mouse.project.state.ConstUtils;
import mouse.project.ui.components.graph.Edge;
import mouse.project.ui.components.graph.Node;
import mouse.project.ui.components.graph.UIGraph;
import mouse.project.utils.math.Position;

import java.util.*;

public class GraphSaver {

    public static final String NODE = "node";
    public static final String EDGE = "edge";

    public String toSaveString(UIGraph graph) {
        StringBuilder builder = new StringBuilder();
        List<Node> nodes = graph.getNodes();
        builder.append("/*Nodes*/\n");
        nodes.forEach(n -> {
            Optional<String> s = saveNode(n);
            s.ifPresent(builder::append);
            builder.append("\n");
        });
        List<Edge> edges = graph.getEdges();
        builder.append("/*Edges*/\n");
        edges.forEach(e -> {
            Optional<String> s = saveEdge(e);
            s.ifPresent(builder::append);
            builder.append("\n");
        });
        return builder.toString();
    }

    private record Tokens(List<String> values) {
            private Tokens(List<String> values) {
                this.values = new ArrayList<>(values);
            }

            public String next() {
                if (isEmpty()) {
                    throw new LoadingException("Cannot get next token.");
                }
                return values.remove(0);
            }

            public Integer nextInteger() {
                String next = next();
                try {
                    return Integer.parseInt(next);
                } catch (Exception e) {
                    throw new LoadingException("Cannot parse to integer " + next);
                }
            }

            public void expect(String pattern) {
                String[] strs = pattern.split(" ");
                if (values.size() < strs.length) {
                    throw new LoadingException("Cannot expect pattern " + pattern);
                }
                int i = 0;
                for (String s : strs) {
                    String key = values.get(i++);
                    switch (s.toUpperCase()) {
                        case "S":
                            continue;
                        case "X":
                            isPosition(key, "x");
                        case "Y":
                            isPosition(key, "y");
                    }
                }
            }

            private void isPosition(String s, String cord) {
                boolean matches = s.matches("^-?\\d+$");
                if (!matches) {
                    throw new LoadingException("Expected integer, found: " + s);
                }
                int i = Integer.parseInt(s);
                if (i < 0) {
                    throw new LoadingException("Cannot accept negative coordinate: " + i);
                }
                if (cord.equals("x") && i > ConstUtils.WORLD_WIDTH) {
                    throw new LoadingException("X out of bounds: " + i + " > " + ConstUtils.WORLD_WIDTH);
                } else if (cord.equals("y") && i > ConstUtils.WORLD_HEIGHT) {
                    throw new LoadingException("Y out of bounds: " + i + " > " + ConstUtils.WORLD_HEIGHT);
                }
            }

            public boolean isEmpty() {
                return values.isEmpty();
            }

            public boolean hasValues() {
                return !values.isEmpty();
            }
        }
    public void fromSaveString(UIGraph graph, String input) {
        graph.removeAll();
        Map<String, Node> nodes = new HashMap<>();
        List<Edge> edges = new ArrayList<>();

        Tokens tokenStream = tokenize(input);

        while (tokenStream.hasValues()) {
            String next = tokenStream.next();
            switch (next) {
                case NODE -> {
                    tokenStream.expect("S X Y");
                    String key = tokenStream.next();
                    int x = tokenStream.nextInteger();
                    int y = tokenStream.nextInteger();
                    nodes.put(key, new Node(key, Position.of(x,y), false));
                }
                case EDGE -> {
                    tokenStream.expect("S S");
                    Node node1 = getNode(nodes, tokenStream.next());
                    Node node2 = getNode(nodes, tokenStream.next());
                    edges.add(new Edge(node1, node2, false));
                }
                default -> throw new LoadingException("Unexpected token: " + next);
            }
        }
        for (Node node : nodes.values()) {
            graph.addNamedNode(node);
        }
        for (Edge edge : edges) {
            graph.addEdge(edge);
        }
    }

    private static Node getNode(Map<String, Node> nodes, String key1) {
        Node node1 = nodes.get(key1);
        if (node1 == null) {
            throw new LoadingException("Unexpected node key: " + key1);
        }
        return node1;
    }

    private Optional<String> saveNode(Node node) {
        if (node.isExtra()) {
            return Optional.empty();
        }
        String id = node.getId();
        Position position = node.getPosition();
        String result = String.format("%s %s %d %d", NODE, id, position.x(), position.y());
        return Optional.of(result);
    }

    private Optional<String> saveEdge(Edge edge) {
        if (edge.isExtra()) {
            return Optional.empty();
        }
        String id1 = edge.node1().getId();
        String id2 = edge.node2().getId();
        String result = String.format("%s %s %s", EDGE, id1, id2);
        return Optional.of(result);
    }

    private Tokens tokenize(String input) {
        String[] strings = removeWhitespaces(input);
        List<String> list = Arrays.asList(strings);
        List<String> noComments = removeComments(list);
        return new Tokens(noComments);
    }

    private List<String> removeComments(List<String> list) {
        List<String> result = new ArrayList<>();
        boolean commentStart = false;
        for (String s : list) {
            if (s.contains("/*")) {
                commentStart = true;
            }
            if (!commentStart) {
                result.add(s);
            }
            if (s.contains("*/")) {
                commentStart = false;
            }
        }
        return result;
    }

    private static String[] removeWhitespaces(String input) {
        String normalizedSpaces = input.replaceAll("\\s", " ");
        String singleSpaced = normalizedSpaces.replaceAll(" +", " ");
        return singleSpaced.split(" ");
    }

}
