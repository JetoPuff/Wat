package com.thisBetterwork;

import java.util.ArrayList;

public class Sort {

    public boolean isSorted(ArrayList<String> data){
        boolean sortedCheck = true;
        for (int i = 0; i < data.size() -1 ; i++) {
            if (data.get(i).compareTo(data.get(i + 1)) >= 0){
                sortedCheck = false;
                break;
            }
        }
        return sortedCheck;
    }

    //Recursive Quick Sort algorithm with the pivot being the last item in the ArrayList
    public void quickSort(ArrayList<String> data, int begin, int end) {
        if (begin < end) {

            int partitionIndex = partition(data, begin, end);

            quickSort(data, begin, partitionIndex-1);
            quickSort(data, partitionIndex+1, end);
        }
    }
    private int partition(ArrayList<String> data, int begin, int end) {
        String pivot = data.get(end);
        int i = (begin-1);

        for (int j = begin; j < end; j++) {
            if (data.get(j).compareTo(pivot) <= 0) {
                i++;
                String swapTemp = data.get(i);
                data.set(i, data.get(j));
                data.set(j, swapTemp);
            }
        }

        String swapTemp = data.get(i + 1);
        data.set(i + 1, data.get(end));
        data.set(end, swapTemp);

        return i+1;
    }
}

