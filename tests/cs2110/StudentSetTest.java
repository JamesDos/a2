package cs2110;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;
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
       // int i =0;
        //while (i<20){
           // students.add(s1);
            //i++;
        //}

        assertFalse(students.contains(s1));
        students.add(s1);
        System.out.println(students.size());
        assertEquals(1, students.size());
        assertTrue(students.contains(s1));
        assertFalse(students.contains(s2));

        students.add(s2);
        System.out.println(students.size());
        assertEquals(2, students.size());
        assertTrue(students.contains(s1));
        assertTrue(students.contains(s2));

        System.out.println(students);
        students.remove(s1);
        assertEquals(1, students.size());
        assertFalse(students.contains(s1));
        assertTrue(students.contains(s2));

        System.out.println(students);
        students.remove(s2);
        System.out.println(students);
        assertEquals(0, students.size());
        assertFalse(students.contains(s2));
    }
    @Test
    void additionalRemoveContain(){
        Student student;
        StudentSet students = new StudentSet();
        Student testStudent = new Student(" "," ");
        for(int i = 0; i < 10;i++){
            student = new Student(testString(),testString());
            if (i==3){
                // stores one student object for testing
                testStudent = new Student(student.firstName(), student.lastName());
            }
            students.add(student);
            System.out.println(student.fullName());
        }
        assertEquals(10, students.size());
        System.out.println("Searching for "+testStudent.fullName());
        //System.out.println("Student is " + students.store[3]);
        //System.out.println(students.store[3] == testStudent);
        // TODO figure out why assert fail.
        // Strings are the same but the reference is different (test student is a different object
        // compared to the student you add to "students")
        // Make "store" public if you want to check
        assertTrue(students.contains(testStudent));
    }

    private String testString(){
        int i;
        String characters = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder strBuild = new StringBuilder();
        while (strBuild.length() < 10){
            i = (int)(Math.random()*26);
            strBuild.append(characters.charAt(i));
        }
        String testString = strBuild.toString();
        return testString;
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
