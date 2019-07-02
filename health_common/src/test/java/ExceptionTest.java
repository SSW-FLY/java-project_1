import org.junit.Test;

public class ExceptionTest {

    @Test
    public void test01(){
        try {
            int i = 1/0;
        } catch (Exception e) {
            //e.printStackTrace();
        }
        System.out.println("ok");
    }
}
