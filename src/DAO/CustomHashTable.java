/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.util.HashMap;
import DAO.RBTree;  // Import your RBTree class
import DAO.RBTree.RBTreeNode;

public class CustomHashTable<K extends Comparable<K>, V> {
    public HashMap<K, RBTree<MyEntry<K, V>>> table;

    public CustomHashTable() {
        table = new HashMap<>();
    }

    public void put(K key, V value) {
        RBTree<MyEntry<K, V>> tree = table.computeIfAbsent(hash(key), k -> new RBTree<>());
        tree.insert(new MyEntry<>(key, value));
    }

    public V get(K key) {
        RBTree<MyEntry<K, V>> tree = table.get(hash(key));
        if (tree != null) {
            MyEntry<K, V> entry = new MyEntry<>(key, null); // Create a dummy entry for search
            RBTreeNode<MyEntry<K, V>> node = tree.find(entry);
            return (node != null) ? node.element.value : null;
        }
        return null;
    }

    // Define the MyEntry class
    public static class MyEntry<K extends Comparable<K>, V> implements Comparable<MyEntry<K, V>> {
        K key;
        V value;

        public MyEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
        public V getValue() {
        return value;
    }

        @Override
        public int compareTo(MyEntry<K, V> o) {
            return this.key.compareTo(o.key);
        }
    }
public void forEachValue(java.util.function.Consumer<V> action) {
    for (RBTree<MyEntry<K, V>> tree : table.values()) {
        for (MyEntry<K, V> entry : tree) { // Iterating over values
            action.accept(entry.value);
        }
    }
}
public void clear() {
    for (RBTree<MyEntry<K, V>> tree : table.values()) {
        tree.clear(); // Clear the RBTree
    }
    table.clear(); // Clear the HashMap
}
    private K hash(K key) {
        // Implement your hash function if needed, or just return the key
        return key;
    }
}

