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
        assertEquals(0, cms.auditCredits(0).size());

        c1.enrollStudent(s1);
        StudentSet set1 = cms.auditCredits(0);
        assertEquals(1, set1.size());
        assertTrue(set1.contains(s1));

        c2.enrollStudent(s2);
        StudentSet set2 = cms.auditCredits(3);
        assertEquals(1, set2.size());
        assertTrue(set2.contains(s2));
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

        // Multiple conflicts
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
        assertTrue(cms.checkCreditConsistency());
        c1.enrollStudent(s1);
        c2.enrollStudent(s2);
        // TROUBLESHOOT
        // System.out.println(c1.formatStudents()+ "\t" + c2.formatStudents());
        // System.out.println("s1 credits " + s1.credits() + "\ts2 credits " + s2.credits());
        // System.out.println("c1 credits " + c1.credits() + "\tc2 credits " + c2.credits());
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
