import org.junit.Test;
import static org.junit.Assert.*;

public class FlikTest {
    @Test
    public void testIsSameNumber() {
        int a = 128;
        int b = 128;
        int c = 129;
        assertEquals(true, Flik.isSameNumber(a, b));
        assertEquals(false, Flik.isSameNumber(a, c));
    }
}
