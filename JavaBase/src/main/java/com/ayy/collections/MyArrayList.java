package com.ayy.collections;

/**
 * @ ClassName MyArrayList
 * @ Description My ArrayList
 * @ Author Zhao JIN
 * @ Date 30/10/2020 13:06
 * @ Version 1.0
 */
public class MyArrayList<E> {
    private Object[] elementData;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    public MyArrayList(){
        elementData = new Object[MyArrayList.DEFAULT_CAPACITY];
    }

    public MyArrayList(int capacity){
        if (capacity<0){
            throw new IllegalArgumentException("Negative Capacity");
        }
        if(capacity==0){
            elementData = new Object[MyArrayList.DEFAULT_CAPACITY];
        }else {
            elementData = new Object[capacity];
        }
    }

    public E get(int index){
        return (E) elementData[index];
    }

    public void set(E element, int index){
        checkRange(index);
        elementData[index] = element;
    }

    public int getSize(){
        return this.size;
    }

    public boolean isEmpty(){
        return this.size==0;
    }

    public void checkRange(int index){
        if (index<0||index>=this.size){
            throw new IllegalArgumentException("Illegal index");
        }
    }

    public void add(E element){
        if(size==elementData.length-1){
            Object[] newArray = new Object[elementData.length+(elementData.length>>1)];
            System.arraycopy(elementData,0,newArray,0,elementData.length);
            elementData = newArray;
        }
        elementData[size++] = element;
    }

    public void remove(int index){
        checkRange(index);
        int numMoved = elementData.length-index-1;
        if(numMoved>0){
            System.arraycopy(elementData,index+1,elementData,index,elementData.length-index-1);
        }
        elementData[--size]=null;
    }

    public void remove(E element){
        for (int i = 0; i < size; i++) {
            if (element.equals(get(i))){
                remove(i);
            }
        }
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i<size ;i++) {
            sb.append(elementData[i]);
            sb.append(",");
        }
        sb.setCharAt(sb.length()-1,']');
        return sb.toString();
    }
}
