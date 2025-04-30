package tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class OrgChartImpl implements OrgChart{

	//Employee is your generic 'E'..
	private List<GenericTreeNode<Employee>> nodes = new ArrayList<>();
	
	
	public void addRoot(Employee e) {
        if (nodes.isEmpty()) {
            nodes.add(new GenericTreeNode<>(e));   
        }
    }
	
	
	public void clear() {
        nodes.clear();
    }


	public void addDirectReport(Employee manager, Employee newPerson) {
        GenericTreeNode<Employee> managerNode = findNode(manager);
        if (managerNode == null) return;          

        GenericTreeNode<Employee> child = new GenericTreeNode<>(newPerson);
        managerNode.addChild(child);
        nodes.add(child);
    }


	public void removeEmployee(Employee firedPerson) {
        GenericTreeNode<Employee> firedNode = findNode(firedPerson);
        if (firedNode == null) return;            

        GenericTreeNode<Employee> boss = findParentOf(firedNode);
        if (boss == null) return;                 

        // for (GenericTreeNode<Employee> kid : firedNode.children) {
        //     boss.addChild(kid);
        // }
        boss.removeChild(firedPerson);             
        nodes.remove(firedNode);
}
    

	public void showOrgChartDepthFirst() {
        System.out.println("Org Chart dfs of the company is:");
        if (!nodes.isEmpty()) {
            printDFS(nodes.get(0));                
        }
    }


	 public void showOrgChartBreadthFirst() {
        System.out.println("Org Chart bfs of the company is:");
        if (nodes.isEmpty()) return;

        Queue<GenericTreeNode<Employee>> q = new LinkedList<>();
        q.add(nodes.get(0));                      

        while (!q.isEmpty()) {
            int levelSize = q.size();             
            System.out.print("  ");                

            for (int i = 0; i < levelSize; i++) {
                GenericTreeNode<Employee> current = q.remove();
                if (i > 0) System.out.print(" - ");
                System.out.print(current.data);

                q.addAll(current.children);        
            }
            System.out.println();                  
        }
    }



	private GenericTreeNode<Employee> findNode(Employee e) {
        for (GenericTreeNode<Employee> n : nodes) {
            if (n.data.equals(e)) return n;
        }
        return null;
    }

	

	private GenericTreeNode<Employee> findParentOf(GenericTreeNode<Employee> child) {
        for (GenericTreeNode<Employee> n : nodes) {
            if (n.children.contains(child)) return n;
        }
        return null;//child root
    }



	private void printDFS(GenericTreeNode<Employee> node) {
        System.out.println("- " + node.data);
        for (GenericTreeNode<Employee> kid : node.children) {
            printDFS(kid);
        }
    }
}
