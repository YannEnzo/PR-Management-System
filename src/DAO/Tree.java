/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

/**
 *
 * @author yanne
 */
import java.util.Collection;

  public interface Tree<E> extends Collection<E> { 
  /** Return true if the element is in the tree */
  public boolean search(E e);
 
  /** Insert element e into the binary tree
  * Return true if the element is inserted successfully */
  public boolean insert(E e);
  public boolean delete(E e);
/** Get the number of elements in the tree */
 public int getSize(); 

 /** Inorder traversal from the root*/
 public default void inorder() { 
 }

 /** Postorder traversal from the root */
 public default void postorder() {
 }
 public default void preorder() {
 }

 @Override /** Return true if the tree is empty */
 public default boolean isEmpty() { 
 return size() == 0;
 }

 @Override
 public default boolean contains(Object e) {
 return search((E)e);
 }

 @Override
 public default boolean add(E e) {
 return insert(e);
 }

 @Override
 public default boolean remove(Object e) {
 return delete((E)e);
 }

 @Override
 public default int size() {
 return getSize();
 }
@Override
public default boolean containsAll(Collection<?> c) {
    // Left as an exercise
    return false;
}

@Override
public default boolean addAll(Collection<? extends E> c) {
    // Left as an exercise
    return false;
}

@Override
public default boolean removeAll(Collection<?> c) {
    // Left as an exercise
    return false;
}

@Override
public default boolean retainAll(Collection<?> c) {
    // Left as an exercise
    return false;
}

@Override
public default Object[] toArray() {
    // Left as an exercise
    return null;
}
@Override
public default <T> T[] toArray(T[] array) {
    // Left as an exercise
    return null;
}

  }
