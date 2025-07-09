package com.example.collections;

import java.util.Objects;

public class MyHashMap<K, V> {
    private static final int INITIAL_CAPACITY = 16;
    private Node<K, V>[] buckets;
    private int size = 0;

    public MyHashMap() {
        buckets = new Node[INITIAL_CAPACITY];
    }

    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public void put(K key, V value) {
        if (size >= buckets.length) {
            resize(); 
        }

        int index = getIndex(key);
        Node<K, V> current = buckets[index];

        if (current == null) {
            buckets[index] = new Node<>(key, value);
            size++;
        } else {
            while (true) {
                if (Objects.equals(current.key, key)) {
                    current.value = value;
                    return;
                }
                if (current.next == null) {
                    break;
                }
                current = current.next;
            }
            current.next = new Node<>(key, value);
            size++;
        }
    }

    private void resize() {
        Node<K, V>[] oldBuckets = buckets;
        buckets = new Node[oldBuckets.length * 2];
        size = 0;

        for (Node<K, V> node : oldBuckets) {
            while (node != null) {
                put(node.key, node.value);
                node = node.next;
            }
        }
    }

    public V get(K key) {
        int index = getIndex(key);
        Node<K, V> current = buckets[index];

        while (current != null) {
            if (Objects.equals(current.key, key)) {
                return current.value;
            }
            current = current.next;
        }

        return null;
    }

    public boolean remove(K key) {
        int index = getIndex(key);
        Node<K, V> current = buckets[index];
        Node<K, V> previous = null;

        while (current != null) {
            if (Objects.equals(current.key, key)) {
                if (previous == null) {
                    buckets[index] = current.next;
                } else {
                    previous.next = current.next;
                }
                size--;
                return true;
            }
            previous = current;
            current = current.next;
        }
        return false;
    }

    public void clear() {
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = null;
        }
        size = 0;
    }

    public int size() {
        return size;
    }

    private int getIndex(K key) {
        return Math.abs(key.hashCode() % buckets.length);
    }

    
    public void print() {
        for (int i = 0; i < buckets.length; i++) {
            Node<K, V> current = buckets[i];
            while (current != null) {
                System.out.println(current.key + "=" + current.value);
                current = current.next;
            }
        }
    }
}
