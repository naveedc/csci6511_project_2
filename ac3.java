import java.util.ArrayList;
import java.util.HashMap;


public class ac3 {
    public static HashMap<String, graphNode> graph;

    public String run(HashMap<String, graphNode> Graph, ArrayList<Integer> colors) {
        graph = Graph;

        ArrayList<graphNode> listOfNodesRemaing = new ArrayList<graphNode>(graph.values());
        ArrayList<graphNode> nodesChecked = new ArrayList<>();

        // adding first node
        graphNode startNode = listOfNodesRemaing.get(0);
        listOfNodesRemaing.remove(0);
        nodesChecked.add(startNode);
        int loops = 0;
        while (listOfNodesRemaing.size() >= 0) {
            loops++;
            // assign a color to current last node in nodes checked
            if (nodesChecked.size() == 0) {
                break;
            }
            graphNode currentNode = nodesChecked.get(nodesChecked.size() - 1);

            // check if all values have been checked all ready
            if (currentNode.colorsChecked.size() == colors.size()) {
                currentNode.reset();
                listOfNodesRemaing.add(nodesChecked.get(nodesChecked.size() - 1));
                nodesChecked.remove(nodesChecked.size() - 1);
            } else {
                int nextColor = colors.get(currentNode.colorsChecked.size());
                currentNode.colorsChecked.add(nextColor);
                currentNode.color = nextColor;
                // currentNode.color =
                if (isColorValid(currentNode.color, currentNode.edges)) {

                    if (0 != listOfNodesRemaing.size()) {
                        graphNode nextnode =  chooseNextNode(nodesChecked, listOfNodesRemaing);
                        nodesChecked.add(nextnode);
                    } else {
                        break;
                    }
                }
            }

        }

        String finalString = "";
        System.out.println(nodesChecked.size());
        System.out.println(loops);
        for (graphNode node : nodesChecked) {
            finalString = finalString + node.nodeNumber + ':' + node.color + '\n';
        }
        return finalString;
    }

    public boolean isColorValid(int color, ArrayList<String> edges) {

        for (int i = 0; i < edges.size(); i++) {
            if (color == graph.get(edges.get(i)).color) {
                return false;
            }
        }
        return true;
    }

    public graphNode chooseNextNode(ArrayList<graphNode> nodesChecked, ArrayList<graphNode> listOfNodesRemaing )  {

        String nextID = null;
        int maxChosen = 0;
        int j = nodesChecked.size() - 1;
        graphNode nextNode;
        while (nextID == null && j >= 0) {

            graphNode currentNode = nodesChecked.get(j);
            for (String edge : currentNode.edges) {

                graphNode node = graph.get(edge);
                int count = 0;
                if (graph.get(edge).color == -1) {
                    for (String edge1 : node.edges) {
                        if (graph.get(edge1).color != -1) {
                            count++;
                        }
                    }
                    if (count > maxChosen) {
                        maxChosen = count;
                        nextID = edge;
                    }
                }
            }
            j--;
        }
        if(nextID == null ){
            nextNode = listOfNodesRemaing.get(0);
            return nextNode;
        } else {
            nextNode = graph.get(nextID);
            int index = listOfNodesRemaing.indexOf(nextNode);
            listOfNodesRemaing.remove(index );
        }

        return nextNode;
    }
}
