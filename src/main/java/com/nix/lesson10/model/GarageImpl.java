package com.nix.lesson10.model;

import com.nix.lesson10.model.vehicle.Vehicle;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

public class GarageImpl<T extends Vehicle> implements Garage<T>{
    private static final Random RANDOM = new Random();

    private static class Node<T extends Vehicle> {
        Node<T> next;
        Node<T> prev;
        T value;
        Integer id;
        LocalDateTime time;

        protected Node(T value, Integer id, LocalDateTime time) {
            this.value = value;
            this.id = id;
            this.time = time;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private Integer size = 0;

    @Override
    public T addFirst(T veh, Integer id) {
        Node<T> headNew = new Node<>(veh, id, LocalDateTime.now()
                .minusDays(RANDOM.nextInt(10))
                .minusHours(RANDOM.nextInt(12)));
        headNew.prev = null;
        if (head == null) {
            head = headNew;
            tail = headNew;
        } else {
            headNew.next = head;
            head.prev = headNew;
            head = headNew;
        }
        size++;
        return headNew.value;
    }

    @Override
    public T addLast(T veh, Integer id) {
        Node<T> tailNew = new Node<>(veh, id, LocalDateTime.now().minusHours(RANDOM.nextInt(5)));
        tailNew.next = null;
        if (tail == null) {
            tail = tailNew;
            head = tailNew;
        } else {
            tail.next = tailNew;
            tailNew.prev = tail;
            tail = tailNew;
        }
        size++;
        return tailNew.value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Optional<? extends Vehicle> autoByRestyle(Integer id) {
        Node<T> temp = head;
        while (true) {
            if (temp.id == id) {
                return Optional.of(temp.value);
            }
            if (temp == tail) {
                break;
            }
            temp = temp.next;
        }

        return Optional.empty();
    }

    @Override
    public Optional<? extends Vehicle> deleteAutoByRestyle(Integer id) {
        Node<T> temp = head;
        IteratorImpl iter = iter();
        while (true) {
            if (temp.id == id) {
                iter.setCurrent(temp);
                iter.remove();
                return Optional.of(temp.value);
            }
            if (temp == tail) {
                break;
            }
            temp = temp.next;
        }
        return Optional.empty();
    }

    @Override
    public Optional<? extends Vehicle> replaceAutoByRestyle(Integer id, T veh) {
        Node<T> temp = head;
        while (true) {
            if (temp.id == id) {
                temp.value = veh;
                return Optional.of(temp.value);
            }
            if (temp == tail) {
                break;
            }
            temp = temp.next;
        }
        return Optional.empty();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        Node<T> start = head;
        if (start == null || start.value == null) {
            return "";
        }
        while (start != tail) {
            result.append(start.value.toString()).append(" Restyle № ").append(start.id).append(", Date: ").append(start.time);
            result.append('\n');
            start = start.next;
        }
        result.append(start.value.toString()).append(" Restyle № ").append(start.id).append(", Date: ").append(start.time);
        return result.toString();
    }

    @Override
    public T getFirstRestyle() {
        IteratorImpl iter = iter();
        Node<T> node = iter.current;
        while (iter.hasNext()) {
            iter.next();
            if (node.time.isAfter(iter.current.time)) {
                node = iter.current;
            }
        }
        return node.value;
    }

    @Override
    public T getLastRestyle() {
        IteratorImpl iter = iter();
        Node<T> node = iter.current;
        while (iter.hasNext()) {
            iter.next();
            if (node.time.isBefore(iter.current.time)) {
                node = iter.current;
            }
        }
        return node.value;
    }

    @Override
    public IteratorImpl iter() {
        return new IteratorImpl();
    }

    private class IteratorImpl implements Iterator<Vehicle> {
        Node<T> current = head;

        @Override
        public boolean hasNext() {
            return current.next != null;
        }

        @Override
        public Vehicle next() {
            if (current.value == null) {
                throw new NoSuchElementException();
            }
            Vehicle toReturn = current.value;
            if (hasNext()) {
                current = current.next;
            }
            return toReturn;
        }

        @Override
        public void remove() {
            if (current == tail && current != head) {
                tail = current.prev;
                current.prev.next = null;
                current = null;
                size--;
            } else if (current.prev != null) {
                current.prev.next = current.next;
                current.next.prev = current.prev;
                current = current.next;
                size--;
            } else {
                current = null;
                size--;
            }
        }

        public void setCurrent(Node<T> node) {
            current = node;
        }
    }

}
