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
    void testConstructorObserver() {
        //testing constructor
        Course cs2110 = new Course("cs2110", 3, "prof", "phillips", 10, 30, 75);
        //testing observers
        assertEquals("cs2110", cs2110.title());
        assertEquals(3, cs2110.credits());
        assertEquals("phillips", cs2110.location());
    }

    @Test
    void testInstructor() {
        //Basic test case
        Course cs2110 = new Course("cs2110", 3, "prof", "phillips", 10, 30, 75);
        assertEquals("Professor prof", cs2110.instructor());
        // Professor name contains spaces
        cs2110 = new Course("cs2110", 3, " ", "phillips", 10, 30, 75);
        assertEquals("Professor  ", cs2110.instructor());
    }

    @Test
    void formatStartTime() {
        //Basic test case AM; Double-Digit Hours
        Course cs2110 = new Course("cs2110", 3, "prof", "phillips", 11, 35, 75);
        assertEquals("11:35 AM", cs2110.formatStartTime());
        //Basic test case PM; Single-Digit Hours
        cs2110 = new Course("cs2110", 3, "prof", "phillips", 20, 35, 75);
        assertEquals("8:35 PM", cs2110.formatStartTime());
        //Two zeros in minutes
        cs2110 = new Course("cs2110", 3, "prof", "phillips", 20, 0, 75);
        assertEquals("8:00 PM", cs2110.formatStartTime());
        //One zero in minutes (right zero)
        cs2110 = new Course("cs2110", 3, "prof", "phillips", 20, 10, 75);
        assertEquals("8:10 PM", cs2110.formatStartTime());
        //One zero in minutes (left zero)
        cs2110 = new Course("cs2110", 3, "prof", "phillips", 20, 9, 75);
        assertEquals("8:09 PM", cs2110.formatStartTime());
        //Zero in Hours
        cs2110 = new Course("cs2110", 3, "prof", "phillips", 10, 9, 75);
        assertEquals("10:09 AM", cs2110.formatStartTime());
        //Single Digit Hours AM
        cs2110 = new Course("cs2110", 3, "prof", "phillips", 1, 0, 75);
        assertEquals("1:00 AM", cs2110.formatStartTime());
        //12:00 AM
        cs2110 = new Course("cs2110", 3, "prof", "phillips", 0, 0, 75);
        assertEquals("12:00 AM", cs2110.formatStartTime());
        //12:00 PM
        cs2110 = new Course("cs2110", 3, "prof", "phillips", 12, 0, 75);
        assertEquals("12:00 PM", cs2110.formatStartTime());
    }

    @Test
    void testOverlaps() {
        //No Overlap (difference in times > 0)
        Course cs2110 = new Course("cs2110", 3, "prof", "phillips", 9, 35, 60);
        Course cs1110 = new Course("cs1110", 3, "prof", "phillips", 11, 35, 60);
        assertFalse(cs2110.overlaps(cs1110));
        assertFalse(cs1110.overlaps(cs2110));
        //No Overlap (difference in times = 0)
        cs2110 = new Course("cs2110", 3, "prof", "phillips", 9, 35, 60);
        cs1110 = new Course("cs1110", 3, "prof", "phillips", 10, 35, 60);
        assertFalse(cs2110.overlaps(cs1110));
        assertFalse(cs1110.overlaps(cs2110));
        //1 minute overlap
        cs2110 = new Course("cs2110", 3, "prof", "phillips", 9, 35, 61);
        cs1110 = new Course("cs1110", 3, "prof", "phillips", 10, 35, 60);
        assertTrue(cs2110.overlaps(cs1110));
        assertTrue(cs1110.overlaps(cs2110));
        //Overlap > 1 minute
        cs2110 = new Course("cs2110", 3, "prof", "phillips", 9, 35, 80);
        cs1110 = new Course("cs1110", 3, "prof", "phillips", 10, 35, 60);
        assertTrue(cs2110.overlaps(cs1110));
        assertTrue(cs1110.overlaps(cs2110));
        //Overlap across AM-PM classes -> AM class overlaps with PM class
        cs2110 = new Course("cs2110", 3, "prof", "phillips", 11, 59, 2);
        cs1110 = new Course("cs1110", 3, "prof", "phillips", 12, 0, 60);
        assertTrue(cs2110.overlaps(cs1110));
        assertTrue(cs1110.overlaps(cs2110));
        //Classes with 0 minutes duration
        cs2110 = new Course("cs2110", 3, "prof", "phillips", 11, 59, 0);
        cs1110 = new Course("cs1110", 3, "prof", "phillips", 12, 0, 0);
        assertFalse(cs2110.overlaps(cs1110));
        assertFalse(cs1110.overlaps(cs2110));
    }

    @Test
    void testHasEnrollDropStudent() {
        Course cs2110 = new Course("cs2110", 3, "prof", "phillips", 9, 35, 60);
        Student s1 = new Student("f1", "l1");
        Student s2 = new Student("f2", "l2");
        //Multiple students enrolled in one course
        // Student is not enrolled (studentSet has no students)
        assertFalse(cs2110.hasStudent(s1));
        assertFalse(cs2110.hasStudent(s2));
        assertEquals(0, s1.credits());
        assertEquals(0, s2.credits());
        cs2110.enrollStudent(s1);
        // Student is enrolled
        assertTrue(cs2110.hasStudent(s1));
        assertEquals(3, s1.credits());
        // Student is not enrolled (studentSet has students)
        assertFalse(cs2110.hasStudent(s2));
        assertEquals(0, s2.credits());
        cs2110.enrollStudent(s2);
        // Enrolling accumulates in studentSet
        assertTrue(cs2110.hasStudent(s1));
        assertTrue(cs2110.hasStudent(s2));
        assertEquals(3, s1.credits());
        assertEquals(3, s2.credits());
        //Dropping One Student
        cs2110.dropStudent(s1);
        assertFalse(cs2110.hasStudent(s1));
        assertTrue(cs2110.hasStudent(s2));
        assertEquals(0, s1.credits());
        assertEquals(3, s2.credits());
        //Dropping accumulates in studentSet
        cs2110.dropStudent(s2);
        assertFalse(cs2110.hasStudent(s1));
        assertFalse(cs2110.hasStudent(s2));
        assertEquals(0, s1.credits());
        assertEquals(0, s2.credits());

        // Student is enrolled in multiple courses
        // These tests are to check the class invariant in Student.java:
        // nCredits = sum of credits of all Courses they're enrolled in
        Course cs1110 = new Course("cs1110", 3, "prof", "phillips", 9, 35, 60);
        cs2110 = new Course("cs2110", 4, "prof", "phillips", 9, 35, 60);
        Course cs3110 = new Course("cs3110", 5, "prof", "phillips", 9, 35, 60);
        assertFalse(cs1110.hasStudent(s1));
        assertFalse(cs2110.hasStudent(s1));
        assertFalse(cs3110.hasStudent(s1));
        // Enroll in 1 course
        cs1110.enrollStudent(s1);
        assertTrue(cs1110.hasStudent(s1));
        assertEquals(3, s1.credits());
        // Enroll in 2 courses (student's credits should accumulate)
        cs2110.enrollStudent(s1);
        assertTrue(cs2110.hasStudent(s1));
        assertEquals(7, s1.credits());
        // Enroll in 3 courses
        cs3110.enrollStudent(s1);
        assertTrue(cs3110.hasStudent(s1));
        assertEquals(12, s1.credits());
        // Drop 1 course
        cs3110.dropStudent(s1);
        assertTrue(cs1110.hasStudent(s1));
        assertTrue(cs2110.hasStudent(s1));
        assertFalse(cs3110.hasStudent(s1));
        assertEquals(7, s1.credits());
        // Drop 2 courses
        cs2110.dropStudent(s1);
        assertTrue(cs1110.hasStudent(s1));
        assertFalse(cs2110.hasStudent(s1));
        assertFalse(cs3110.hasStudent(s1));
        assertEquals(3, s1.credits());
        // Drop 3 courses
        cs1110.dropStudent(s1);
        assertFalse(cs1110.hasStudent(s1));
        assertFalse(cs2110.hasStudent(s1));
        assertFalse(cs3110.hasStudent(s1));
        assertEquals(0, s1.credits());
    }
}
