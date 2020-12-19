package com.ayy.collections;

/**
 * @ ClassName MyHashMap
 * @ Description My HashMap
 * @ Author Zhao JIN
 * @ Date 30/10/2020 13:08
 * @ Version 1.0
 */
public class MyHashMap<K, V> {
    private class Entry<K, V> {
        private int hash;
        private K key;
        private V value;
        private Entry next;

    }

    private Entry<K,V>[] table;
    private int size;

    public MyHashMap () {
        table = new Entry[16];
    }

    public void put (K k ,V v) {
        Entry newEntry = new Entry();
        newEntry.hash = MyHashMap.myHashCode(k.hashCode(), table.length);
        newEntry.key = k;
        newEntry.value = v;
        newEntry.next = null;

        Entry temp = table[newEntry.hash];
        boolean changed = false;
        if (temp == null) {
            table[newEntry.hash] = newEntry;
            size++;
        } else {
            while (temp.next != null) {
                if (temp.key == newEntry.key) {
                    temp.value = newEntry.value;
                    changed = true;
                    break;
                }
                temp = temp.next;
            }
            if (!changed) {
                if (temp.key == newEntry.key) {
                    temp.value = newEntry.value;
                } else {
                    temp.next = newEntry;
                    size++;
                }
            }
        }

    }

    public V get (K key) {
        int hash = MyHashMap.myHashCode(key.hashCode(), table.length);
        V value = null;
        if (table[hash] != null) {
            Entry temp = table[hash];
            while (temp != null) {
                if (temp.key.equals(key)) {
                    value = (V)temp.value;
                    break;
                }
                temp = temp.next;
            }
        }
        return value;
    }

    private static int myHashCode (int v, int length) {
        return v & (length - 1);
    }

    @Override
    public String toString () {
        StringBuilder sb = new StringBuilder("[\n");
        for (int i = 0; i < table.length; i++) {
            sb.append("hash " + i + ": ");
            Entry temp = table[i];
            while (temp != null) {
                sb.append(temp.key + "::" + temp.value + ", ");
                temp = temp.next;
            }
            sb.append("\n");
        }
        sb.append("]");
        return sb.toString();
    }
}
