//Yifan Wan
//10/9/2023
public class LinkedList {
    public static void main(String[] args) {
        LinkedList myLL = new LinkedList();
        myLL.add(3);
        myLL.add(0);
        myLL.add(-2);
        myLL.print();
        myLL.reverseList();
        myLL.print();

        LinkedList stringLL = new LinkedList();
        stringLL.add("hello");
        stringLL.add("world");
        stringLL.print();
        stringLL.reverseList();
        stringLL.print();
    }

    private Link head;
    private Link tail;
    private int length;

    LinkedList() {
        head = tail = new Link();
        length = 0;
    }

    public void reverseList() {
        if (head.next == null) {
            // Empty list or only one element, nothing to reverse
            return;
        }

        Link prev = null;
        Link current = head.next;
        Link next;

        while (current != null) {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }

        // Set the new head to the last element (prev)
        head.next = prev;
    }

    public void clear() {
        head.setNext(null);
        tail = head;
        length = 0;
    }

    public int size() {
        return length;
    }

    public void add(Object elem) {
        tail.setNext(new Link(elem, null));
        tail = tail.next();
        length++;
    }

    public void add(int index, Object elem) {
        assert (index >= 0 && index <= length) : "Index not in list";
        Link tmp = head;
        for (int i = 0; i < index; i++) {
            tmp = tmp.next;
        }
        tmp.next = new Link(elem, tmp.next);
        length++;
    }

    public void remove(int index) {
        assert (index >= 0 && index < length) : "Index not in list";
        Link tmp = head;
        for (int i = 0; i < index; i++) {
            tmp = tmp.next;
        }
        tmp.next = tmp.next.next;
        length--;
    }

    public void remove(Object elem) {
        Link tmp = head;
        while (tmp.next != null && !tmp.next.element.equals(elem)) {
            tmp = tmp.next;
        }
        if (tmp.next != null) {
            tmp.next = tmp.next.next;
            length--;
        }
    }

    public Object get(int index) {
        assert (index >= 0 && index < length) : "Index not in list";
        Link tmp = head.next;
        for (int i = 0; i < index; i++) {
            tmp = tmp.next;
        }
        return tmp.element;
    }

    public void print() {
        Link curLink = head;
        while (curLink.next != null) {
            curLink = curLink.next;
            System.out.print(curLink.element + " ");
        }
        System.out.print("\n");
    }

    private class Link {
        private Object element;
        private Link next;

        Link(Object elem, Link nextelem) {
            element = elem;
            next = nextelem;
        }

        Link(Object elem) {
            element = elem;
        }

        Link() {
        }

        Link next() {
            return next;
        }

        Object element() {
            return element;
        }

        void setNext(Link nextelem) {
            next = nextelem;
        }

        void setElement(Object elem) {
            element = elem;
        }
    }
}