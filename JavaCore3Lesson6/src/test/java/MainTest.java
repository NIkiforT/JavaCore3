import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainTest {

    int[] arrIn ={1, 2, 3, 5};
    int[] arrOut = {5};

    int[] arrIn2 ={4, 2, 3, 5};
    int[] arrOut2 = {5};


    int[] arrIn3 ={4, 2, 3, 1, 5};
    int[] arrOut3 = {2,3,1,5};

    int[] arrIn4 ={1, 4, 4, 1, 1};
    boolean isTrue = true;


    @Test
    public void foo() {
        Assert.assertArrayEquals(arrOut3,  Main.foo(arrIn3));
        //Assert.assertArrayEquals(arrOut, Main.foo(arrIn));
        //Assert.assertArrayEquals(arrOut2, Main.foo(arrIn2));

    }


    @Test
    public void check() {
        Assert.assertEquals(isTrue, Main.check(arrIn4));
    }
}