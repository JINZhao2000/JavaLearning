package com.ayy.synchronization.order.management;

/**
 * @ ClassName Manager1
 * @ Description use management method
 * @ Author Zhao JIN
 * @ Date 09/11/2020 16:13
 * @ Version 1.0
 */
public class Manager1 {
    public static void main (String[] args) {
        Container container = new Container();
        Producer producer = new Producer(container);
        Consommer consommer = new Consommer(container);
        producer.start();
        consommer.start();
    }
}

class Producer extends Thread{
    private Container container;

    public Producer (Container container) {
        this.container = container;
    }

    @Override
    public void run () {
        for (int i = 0; i < 10; i++) {
            System.out.println("Creation "+i+"eme element");
            container.push(new Element(i));
        }
    }
}

class Consommer extends Thread{
    private Container container;

    public Consommer (Container container) {
        this.container = container;
    }

    @Override
    public void run () {
        for (int i = 0; i < 10; i++) {
            System.out.println("Utilsation " + container.pop().getId() + "eme Element");
        }
    }
}

class Container {
    Element[] elements = new Element[10];
    int count = 0;
    public synchronized void push(Element e){
        if(count==10){
            try {
                this.wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        elements[count++] = e;
        notifyAll();
    }

    public synchronized Element pop(){
        if(this.count == 0){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Element e = elements[--count];
        elements[count] = null;
        this.notifyAll();
        return e;
    }
}

class Element{
    private int id;

    public Element (int id) {
        this.id = id;
    }

    public int getId () {
        return id;
    }
}
