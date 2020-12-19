package com.ayy.collections;

/**
 * @ ClassName MyLinkedList
 * @ Description MyLinkedList
 * @ Author Zhao JIN
 * @ Date 30/10/2020 13:12
 * @ Version 1.0
 */
public class MyLinkedList<E> {
    private Node first;
    private Node last;

    private int size;

    public E get(int index)throws IllegalArgumentException{
        rangeCheck(index);
        return this.getNode(index)!=null?(E)this.getNode(index).element:null;
    }

    private void rangeCheck(int index)throws IllegalArgumentException{
        if(index<0||index>size){
            throw new IllegalArgumentException("index invalide");
        }
    }

    private void rangeCheck2(int index)throws IllegalArgumentException{
        if(index<0||index>=size){
            throw new IllegalArgumentException("index invalide");
        }
    }

    private Node getNode (int index) throws IllegalArgumentException{
        Node temp;
        rangeCheck(index);
        if(index==size){
            return null;
        }
        if(index<(size>>1)){
            temp = first;
            for (int i = 0; i < index; i++) {
                temp=temp.next;
            }
        }else{
            temp=last;
            for (int i = size-1;i>index; i--) {
                temp=temp.previous;
            }
        }
        return temp;
    }

    public void add(E element){
        Node node = new Node(element);
        if (first == null) {
            node.previous = null;
            node.next = null;
            first = node;
            last = node;
        }else{
            node.previous = last;
            node.next = null;
            last.next = node;
            last = node;
        }
        size++;
    }

    public void add(int index, E element){
        rangeCheck(index);
        Node nNode = new Node(element);
        Node temp = getNode(index);
        if(temp==null){
            add(element);
        }else{
            if(temp.next==null&&temp.previous!=null){
                add(element);
            }else if(temp.previous==null){
                nNode.previous=null;
                nNode.next = temp;
                temp.previous = nNode;
                first = nNode;
            }else{
                temp.previous.next = nNode;
                nNode.previous = temp.previous;
                nNode.next = temp;
                temp.previous = nNode;
            }
        }

        size++;
    }

    public void remove(int index){
        rangeCheck2(index);
        Node temp = getNode(index);
        if (temp != null) {
            Node up = temp.previous;
            Node down = temp.next;
            if (up != null) {
                up.next = down;
            }
            if (down != null) {
                down.previous = up;
            }
            if (index == 0) {
                first = down;
            }
            if (index == size - 1) {
                last = up;
            }
            size--;
        }
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node temp = first;
        while (temp != null) {
            sb.append(temp.element+",");
            temp=temp.next;
        }
        sb.setCharAt(sb.length()-1,']');
        return sb.toString();
    }

    private class Node<E>{
        Node previous;
        Node next;
        Object element;

	public Node (Node previous, Node next, Object element) {
            this.previous = previous;
            this.next = next;
            this.element = element;
        }

	public Node (Object element) {
            this.element = element;
        }
    }
}
