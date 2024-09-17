package implementation_classes;

import implementation_classes.Lists.UnorderedListADT;

public class ArrayUnorderedList<T> extends ArrayList<T> implements UnorderedListADT<T> {
    public ArrayUnorderedList() {
        super();
    }


    @Override
    public void addToFront(T element) {
        if (size() == list.length) {
            expandCapacity();
        }

        for(int j = rear; j > 0; j--) {
            list[j] = list[j - 1];
        }

        list[0] = element;
        rear ++;
        modCount++;
    }

    @Override
    public void addToRear(T element) {
        if (size() == list.length) {
            expandCapacity();
        }

        list[rear++] = element;
        modCount++;
    }

    @Override
    public void addAfter(T element, T target) {
        if(size() == list.length) {
            expandCapacity();
        }


        int i = 0;
        while(list[i] != target)
            i++;
        for(int j = rear; j > i; j--)
            list[j] = list[j - 1];
        list[i] = element;
        rear++;
        modCount++;
    }
}
