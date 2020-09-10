import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    @Test
    public void testArrayDeque() {
        StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ads1 = new ArrayDequeSolution<>();
        String message = "";

        for (int i = 0; i < 1000; i += 1) {
            double numberBetweenZeroAndOne = StdRandom.uniform();

            if (numberBetweenZeroAndOne < 0.25) {
                Integer expected = ads1.size();
                Integer actual = sad1.size();
                message += "size(): " + actual + "\n";
                assertEquals(message, expected, actual);
                if (actual > 0) {
                    expected = ads1.removeLast();
                    actual = sad1.removeLast();
                    message += "removeLast(): " + actual + "\n";
                    assertEquals(message, expected, actual);
                }
            } else if (numberBetweenZeroAndOne < 0.5){
                Integer expected = ads1.size();
                Integer actual = sad1.size();
                message += "size(): " + actual + "\n";
                assertEquals(message, expected, actual);
                if (actual > 0) {
                    expected = ads1.removeFirst();
                    actual = sad1.removeFirst();
                    message += "removeLast(): " + actual + "\n";
                    assertEquals(message, expected, actual);
                }
            } else if (numberBetweenZeroAndOne < 0.75) {
                sad1.addLast(i);
                ads1.addLast(i);
                message += "addLast(" + i + ")\n";
            }else {
                sad1.addFirst(i);
                ads1.addFirst(i);
                message += "addFirst(" + i + ")\n";
            }
        }


    }
}
