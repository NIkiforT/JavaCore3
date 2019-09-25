package com.company.Lesson1;

import java.util.ArrayList;

public class TestClass {
    public static void main(String[] args) {
        ArrayNum<Integer>  arr1= new ArrayNum<>(1,2,3,4,5,6,7,8,9,0);
        arr1.svapNum(1,6);
        arr1.arrList();


        // Задание 3
        Orange or1 = new Orange();
        Orange or2 = new Orange();
        Orange or3 = new Orange();
        Orange or4 = new Orange();


        Apple ap1 = new Apple();
        Apple ap2 = new Apple();
        Apple ap3 = new Apple();
        Apple ap4 = new Apple();


        Box<Apple> arr2 = new Box<>(ap1);
        arr2.addFruit(ap3);
        System.out.println(arr2.getWeight());

        Box<Apple> arr4 = new Box<>(ap2);
        arr4.shift(arr2);//перекладываем фрукты
        System.out.println(arr2.getWeight());
        System.out.println(arr4.getWeight());

        Box<Orange> arr3 = new Box<>(or1, or2);
        arr3.addFruit(ap1); //проверка на добавление другого фркта
        System.out.println(arr3.getWeight());
        System.out.println( arr3.compare(arr2)); // сравнение коробок


    }

}
