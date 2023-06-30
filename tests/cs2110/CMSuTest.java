package cs2110;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CMSuTest {

    // TODO 23: These are test cases to cover `CMSu`'s extra functionality that you implement.
    // Ensure that each implemented method is run against these tests and work.

    @Test
    void testAuditCredits() {
        CMSu cms = new CMSu();
        Student s1 = new Student("F1", "L1");
        Student s2 = new Student("F2", "L2");
        Course c1 = new Course("OOP", 3, "Gries", "STL185", 10, 10, 50);
        Course c2 = new Course("FP", 4, "Clarkson", "Uris G01", 13, 30, 50);
        cms.addCourse(c1);
        cms.addCourse(c2);
        cms.addStudent(s1);
        cms.addStudent(s2);

        // No students should be above credit limit of 0 (no students enrolled in any courses)
        assertEquals(0, cms.auditCredits(0).size());

        // s1 over credit limit
        c1.enrollStudent(s1);
        StudentSet set1 = cms.auditCredits(0);
        assertEquals(1, set1.size());
        assertTrue(set1.contains(s1));

        // s2 over credit limit, s1 not over credit limit
        c2.enrollStudent(s2);
        StudentSet set2 = cms.auditCredits(3);
        assertEquals(1, set2.size());
        assertTrue(set2.contains(s2));

        // s2 on credit limit
        StudentSet set3 = cms.auditCredits(4);
        assertEquals(0, set3.size());
        assertFalse(set3.contains(s2));
        assertFalse(set3.contains(s1));

        // Additional Test Cases
        // reset values
        c1.dropStudent(s1);
        c2.dropStudent(s2);
        Course c3 = new Course("Python", 4, "White", "Bailey", 10, 15, 50);
        Course c4 = new Course("c4", 3, "Gries", "STL185", 11, 0, 60);

        // student is enrolled in multiple courses (on credit limit)
        c1.enrollStudent(s1);
        c2.enrollStudent(s1);
        c3.enrollStudent(s1);
        c4.enrollStudent(s1);
        StudentSet set4 = cms.auditCredits(14);
        assertEquals(0, set4.size());
        assertFalse(set4.contains(s1));

        // student is enrolled in multiple courses (under credits)
        StudentSet set5 = cms.auditCredits(15);
        assertEquals(0, set5.size());
        assertFalse(set5.contains(s1));

        // student is enrolled in multiple courses (over credits)
        StudentSet set6 = cms.auditCredits(13);
        assertEquals(1, set6.size());
        assertTrue(set6.contains(s1));

        // multiple students over credit limit
        c1.enrollStudent(s2);
        c2.enrollStudent(s2);
        c3.enrollStudent(s2);
        c4.enrollStudent(s2);
        StudentSet set7 = cms.auditCredits(13);
        assertEquals(2, set7.size());
        assertTrue(set7.contains(s2));

        // Dropping students under credit limit
        c4.dropStudent(s1);
        c4.dropStudent(s2);
        StudentSet set8 = cms.auditCredits(13);
        assertEquals(0, set8.size());
        assertFalse(set8.contains(s1));
        assertFalse(set8.contains(s2));
    }

    @Test
    void testHasConflict() {
        CMSu cms = new CMSu();
        Student s1 = new Student("F", "L");
        Course c1 = new Course("OOP", 3, "Gries", "STL185", 10, 10, 50);
        Course c2 = new Course("CS 2800", 3, "Silva", "Gates G01", 1, 30, 50);
        Course c3 = new Course("Python", 4, "White", "Bailey", 10, 15, 50);

        cms.addCourse(c1);
        cms.addCourse(c2);
        cms.addCourse(c3);
        cms.addStudent(s1);

        // No conflicts when not enrolled in any classes
        assertFalse(cms.hasConflict(s1));

        // No conflicts when only enrolled in one class
        c1.enrollStudent(s1);
        assertFalse(cms.hasConflict(s1));

        // No conflict when enrolled in two non-overlapping classes
        c2.enrollStudent(s1);
        assertFalse(cms.hasConflict(s1));

        // Conflict (and course IDs are not adjacent)
        c3.enrollStudent(s1);
        assertTrue(cms.hasConflict(s1));

        // No more conflict
        c3.dropStudent(s1);
        assertFalse(cms.hasConflict(s1));

        // Additional Test Cases
        // Reset from last test case:
        c2.dropStudent(s1);
        c1.dropStudent(s1);

        Course c4 = new Course("c4", 3, "Gries", "STL185", 11, 0, 60);
        Course c5 = new Course("c5", 3, "Silva", "Gates G01", 12, 0, 60);
        Course c6 = new Course("c6", 4, "White", "Bailey", 9, 0, 360);
        cms.addCourse(c4);
        cms.addCourse(c5);
        cms.addCourse(c6);

        // Non Overlapping Classes (0-minute difference)
        // Tests courses added to CMSu for which s1 is not enrolled in

        c1.enrollStudent(s1);
        c4.enrollStudent(s1);
        c5.enrollStudent(s1);
        assertFalse(cms.hasConflict(s1));

        // Conflict across multiple courses (one course conflicts with all others)
        c6.enrollStudent(s1);
        assertTrue(cms.hasConflict(s1));

        // Multiple conflicts (multiple courses conflict with multiple others)
        c2.enrollStudent(s1);
        c3.enrollStudent(s1);
        assertTrue(cms.hasConflict(s1));

        // Still has conflict
        c6.dropStudent(s1);
        c2.dropStudent(s1);
        assertTrue(cms.hasConflict(s1));

        //No more conflict
        c3.dropStudent(s1);
        assertFalse(cms.hasConflict(s1));

    }

    @Test
    void testCheckCreditConsistency() {
        CMSu cms = new CMSu();
        Student s1 = new Student("F1", "L1");
        Student s2 = new Student("F2", "L2");
        Course c1 = new Course("OOP", 3, "Gries", "STL185", 10, 10, 50);
        Course c2 = new Course("FP", 4, "Clarkson", "Uris G01", 13, 30, 50);
        cms.addCourse(c1);
        cms.addCourse(c2);
        cms.addStudent(s1);
        cms.addStudent(s2);

        // Students not enrolled in any courses
        assertTrue(cms.checkCreditConsistency());
        c1.enrollStudent(s1);
        c2.enrollStudent(s2);
        // TROUBLESHOOT
        // System.out.println(c1.formatStudents()+ "\t" + c2.formatStudents());
        // System.out.println("s1 credits " + s1.credits() + "\ts2 credits " + s2.credits());
        // System.out.println("c1 credits " + c1.credits() + "\tc2 credits " + c2.credits());
        assertTrue(cms.checkCreditConsistency());

        // Tests whether credits are consistent when students course credits accumulate
        // and when multiple students enrolled in the same course
        c2.enrollStudent(s1);
        c1.enrollStudent(s2);
        assertTrue(cms.checkCreditConsistency());

        // Tests whether credits are consistent when students drop courses
        c1.dropStudent(s1);
        c2.dropStudent(s2);
        assertTrue(cms.checkCreditConsistency());

        // The following operations violate an invariant that CMSu is supposed to maintain, but this
        // is not undefined behavior because `checkCreditConsistency()` relaxes its precondition and
        // defines its result in such circumstances.
        s1.adjustCredits(1);
        assertFalse(cms.checkCreditConsistency());
        s1.adjustCredits(-1);
        assertTrue(cms.checkCreditConsistency());
        s2.adjustCredits(-1);
        assertFalse(cms.checkCreditConsistency());
        s2.adjustCredits(1);
        assertTrue(cms.checkCreditConsistency());
    }

}
