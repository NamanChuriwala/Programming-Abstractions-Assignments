import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class BinaryTree<T> implements Iterable<T>{

    public T data;
    private BinaryTree<T> left;
    private BinaryTree<T> right;

    public BinaryTree(){
        this.data = null;
    }

    public BinaryTree(T value){
        this.data = value;
    }

    public void addLeftChild(BinaryTree<T> tree){
        this.left = tree;
    }

    public void addRightChild(BinaryTree<T> tree){
        this.right = tree;
    }

    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator<T>(this);
    }

    public class BinaryTreeIterator<E> implements Iterator<E>{

        Queue<BinaryTree<T>> q = new LinkedList<>();

        public BinaryTreeIterator(BinaryTree<T> tree){
            q.add(tree);
        }

        @Override
        public boolean hasNext() {
            if(q.size()>0){
                return true;
            }
            return false;
        }

        @Override
        public E next() {
            BinaryTree<T> element = q.remove();
            if(element.left!=null) {
                if (element.left != null) {
                    q.add(element.left);
                }
            }
            if(element.right!=null) {
                if (element.right != null) {
                    q.add(element.right);
                }
            }
            return (E) element.data;
        }
    }

    public static void main(String[] args) {
    }
}
