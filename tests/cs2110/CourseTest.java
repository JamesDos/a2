package cs2110;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CourseTest {

    // TODO 14: Write a test case that covers `Course`'s constructor and the observers that have
    // already been implemented in the release code.  Confirm that the case initially fails, then
    // complete TODO 15 and return here to confirm that your case now passes.
    // As you proceed with TODOs 16-22, start by adding a test case here for the method being
    // implemented, then repeat the above (confirm that it fails, implement the method, confirm that
    // it passes).  This TODO is complete when there are test cases covering all of `Course`'s
    // public methods.
    // Be sure to verify any effects on objects of other classes as well.

    @Test
    void testConstructorObserver(){
        //testing constructor
        Course cs2110 = new Course("cs2110", 3, "prof", "phillips", 10, 30, 75);
        //testing observers
        assertEquals("cs2110", cs2110.title());
        assertEquals(3, cs2110.credits());
        assertEquals("phillips", cs2110.location());
    }



}
