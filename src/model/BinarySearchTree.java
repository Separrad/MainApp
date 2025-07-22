/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.List;

public class BinarySearchTree {
    private Node root;
    private DestinationComparator comparator;

    public BinarySearchTree(DestinationComparator comparator) {
        this.comparator = comparator;
    }

    private class Node {
        Destination data;
        Node left, right;

        public Node(Destination data) {
            this.data = data;
            left = right = null;
        }
    }

    public void insert(Destination data) {
        root = insertRec(root, data);
    }

    private Node insertRec(Node root, Destination data) {
        if (root == null) {
            return new Node(data);
        }

        if (comparator.compare(data, root.data) < 0) {
            root.left = insertRec(root.left, data);
        } else if (comparator.compare(data, root.data) > 0) {
            root.right = insertRec(root.right, data);
        }

        return root;
    }

    public List<Destination> inOrderTraversal() {
        List<Destination> result = new ArrayList<>();
        inOrderRec(root, result);
        return result;
    }

    private void inOrderRec(Node root, List<Destination> result) {
        if (root != null) {
            inOrderRec(root.left, result);
            result.add(root.data);
            inOrderRec(root.right, result);
        }
    }

    public Destination search(String destinationName) {
        return searchRec(root, destinationName);
    }

    private Destination searchRec(Node root, String destinationName) {
        if (root == null) {
            return null;
        }

        int cmp = destinationName.compareToIgnoreCase(root.data.getName());
        if (cmp == 0) {
            return root.data;
        } else if (cmp < 0) {
            return searchRec(root.left, destinationName);
        } else {
            return searchRec(root.right, destinationName);
        }
    }

    public boolean contains(Destination destination) {
        return containsRec(root, destination);
    }

    private boolean containsRec(Node root, Destination destination) {
        if (root == null) {
            return false;
        }

        int cmp = comparator.compare(destination, root.data);
        if (cmp == 0) {
            return true;
        } else if (cmp < 0) {
            return containsRec(root.left, destination);
        } else {
            return containsRec(root.right, destination);
        }
    }

    public void clear() {
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }
}