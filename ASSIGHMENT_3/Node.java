package ASSIGHMENT_3;
import java.util.List;
import java.util.ArrayList;
public class Node {
    
    private String value;
    private List<Node> children;

    public Node(String value, List<Node> children){
        this.value=value;
        if(children!=null)
            this.children= new ArrayList<>();
        this.children=children;
    }

    public String getValue(){
        return value;
    }

    public List<Node> getChildren(){
        return children;
    }

    public void addChild(Node child){
        children.add(child);
    }
    
}
