package com.company.Lesson1;

import java.util.ArrayList;

public class Box<T extends Fruit> {
    private T[] arr1;
    ArrayList<T> array = new ArrayList<>();

    public Box(T...arr){
        this.arr1 = arr;
        for (T i : arr) {
            this.array.add(i);
        }
    }

    //получаем массу коробки
    public float getWeight() {
        int numberFruit = this.array.size();
        float massBox = 0;
        if(array.size() != 0) {
            if (array.get(0) instanceof Orange) {
                massBox = numberFruit * Orange.mass;
            } else if (array.get(0) instanceof Apple) {
                massBox = numberFruit * Apple.mass;
            }
        } else {
            System.out.println("Коробка пуста");
        }
        return massBox;

    }
    //добавляем фрукт
    public void addFruit(Fruit fruit){
        if (array.get(0).getClass().getName() == fruit.getClass().getName()) {
            array.add((T) fruit);
        }else {
            System.out.println("Эта коробка с другими фруктами");
        }
    }

    //метод сравнения коробок
    public boolean compare(Box name){
        return name.getWeight() == this.getWeight();
    }

    public void shift (Box name) {
        if (this.array.get(0).getClass().getName() == name.array.get(0).getClass().getName()) {
            for (int i = 0; i < this.array.size(); i++) {
                name.array.add(this.array.get(i));
                this.array.remove(i);
            }
        } else {
            System.out.println("В этих коробках разные фрукты");
        }
    }
}
