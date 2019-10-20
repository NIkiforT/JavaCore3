import java.util.Arrays;

public class Main {

        // Метод возвращает новый массив.
    static int[] foo (int[] num) {
        boolean isNum = false;

        for (int n: num){
            if(n == 4){
                isNum = true;
            }
        }
        int[] resultArr = new int[0];
        int start = 0;
        if(isNum){
            for(int i = 0; i < num.length; i++){
                if (num[i] == 4){
                    start = i;

                    }
                }
            }else {
            throw new RuntimeException("Нет 4");
        }
        resultArr = Arrays.copyOfRange(num, start + 1, num.length);
        return resultArr;
        }


        // Метод на проверку, состоит ли метод только из 1 и 4.
        static boolean check (int[] num){
        boolean result = true;
        for(int i: num){
            if(i != 4 && i != 1){
                result = false;
            }
        }
        return result;
        }


    public static void main(String[] args) {

        int[] arr = {1, 1, 4, 1, 1, 4, 1, 1};
        int[] result = foo(arr);

        System.out.println(check(arr));

        for(int i = 0; i < result.length; i ++){
            System.out.println(result[i]);
        }

    }
}
