package com.company.Lesson1;

import java.util.ArrayList;

public class ArrayNum<T> {
    private T[] arr;

    public ArrayNum(T...arr){
        this.arr = arr;
    }
    //Метод свапа двух чисел, аргументы - индексы чисел.
    public void svapNum (int i, int j){
        Object obj = arr[i];
        arr[i] = arr[j];
        arr[j] = (T) obj;
        for(int k = 0; k < arr.length; k++){
            System.out.print(arr[k] + " "); // выводим массив.
        }
        System.out.println();
    }

    //преобразование в ArrayList
    public void arrList() {
        ArrayList<T> arrayList = new ArrayList<>();
        for (T i : arr) {
            arrayList.add(i);
        }
        for (T j : arrayList) {
            System.out.print(j + " "); //печатаем содержимое ArrayList
        }
        System.out.println();
        System.out.println(arrayList.size()); //размер
    }

}
