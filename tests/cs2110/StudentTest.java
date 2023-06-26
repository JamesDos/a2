package cs2110;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class StudentTest {
    @Test
    void testConstructorAndObservers() {
        // Typical case
        {
            Student s = new Student("first", "last");
            assertEquals("first", s.firstName());
            assertEquals("last", s.lastName());
            assertEquals(0, s.credits());
        }

        // Short names
        {
            Student s = new Student("f", "l");
            assertEquals("f", s.firstName());
            assertEquals("l", s.lastName());
        }
        // Spaces as first and last names
        {
            Student s = new Student(" ", " ");
            assertEquals(" ", s.firstName());
            assertEquals(" ", s.lastName());
        }

    }

    @Test
    void testFullName() {
        // TODO 6: Add a test case for the `fullName()` method.
        // Typical Case
        {
            Student s = new Student("James", "Tu");
            assertEquals("James Tu", s.fullName());
        }
        // Short Names
        {
            Student s = new Student("J", "T");
            assertEquals("J T", s.fullName());
        }
        // First and last Names are empty spaces
        {
            Student s = new Student(" ", "  ");
            assertEquals("    ", s.fullName());
        }
        // Non lettered names
        {
            Student s = new Student("%", "$");
            assertEquals("% $", s.fullName());
        }
        //String with extra line
        {
            Student s = new Student("%", "\n$");
            assertEquals("% \n$", s.fullName());
        }
        //fail("TODO");
    }

    @Test
    void testAdjustCredits() {
        Student s = new Student("first", "last");
        s.adjustCredits(3);
        assertEquals(3, s.credits());

        // A second adjustment should be cumulative
        s.adjustCredits(4);
        assertEquals(7, s.credits());

        // Negative adjustments
        s.adjustCredits(-3);
        assertEquals(4, s.credits());

        // Back to zero
        s.adjustCredits(-4);
        assertEquals(0, s.credits());
    }
}
