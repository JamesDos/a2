package cs2110;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class StudentSetTest {
    @Test
    void testConstructorAndSize() {
        // Constructor should yield an empty set
        StudentSet students = new StudentSet();
        assertEquals(0, students.size());
    }

    // TODO 8: Run this test suite and confirm that it fails.  Complete TODOs 9-10, then run it
    // again and confirm that it now passes.  As you proceed with TODOs 11-13, ensure that the
    // test case here for the method being implemented works. This TODO is complete when
    // all the test cases covering all of `StudentSet`'s public methods pass without any errors.
    // Be sure handle the case that triggers a resize, given your chosen initial capacity.

    @Test
    void testAddRemoveContains() {
        StudentSet students = new StudentSet();

        Student s1 = new Student("f1", "l1");
        Student s2 = new Student("f2", "l2");

        assertFalse(students.contains(s1));
        students.add(s1);
        assertEquals(1, students.size());
        assertTrue(students.contains(s1));
        assertFalse(students.contains(s2));

        students.add(s2);
        assertEquals(2, students.size());
        assertTrue(students.contains(s1));
        assertTrue(students.contains(s2));

        students.remove(s1);
        assertEquals(1, students.size());
        assertFalse(students.contains(s1));
        assertTrue(students.contains(s2));

        students.remove(s2);
        assertEquals(0, students.size());
        assertFalse(students.contains(s2));
    }

    @Test
    void testResize() {
        StudentSet students = new StudentSet();
        int nAdds = 100;
        Student[] addedStudents = new Student[nAdds];
        for (int i = 0; i < nAdds; ++i) {
            addedStudents[i] = new Student("f" + i, "l" + i);
            students.add(addedStudents[i]);
            assertEquals(i + 1, students.size());
        }

        for (int i = 0; i < addedStudents.length; ++i) {
            assertTrue(students.contains(addedStudents[i]));
        }
    }

}
