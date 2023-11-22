package org.hbrs.se1.ws23.uebung2.test;

import org.hbrs.se1.ws23.uebung2.ConcreteMember;
import org.hbrs.se1.ws23.uebung2.Container;
import org.hbrs.se1.ws23.uebung2.ContainerException;
import org.hbrs.se1.ws23.uebung2.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ContainerTest {
    ConcreteMember m1;
    ConcreteMember m2;
    ConcreteMember m3;
    ConcreteMember m4;
    ConcreteMember m5;
    Container c ;
    @BeforeEach
    void setUp() throws Exception {
        m1 = new ConcreteMember(17);
        m2 = new ConcreteMember(12);
        m3 = new ConcreteMember(14);
        m4 = new ConcreteMember(17);
        m5 = new ConcreteMember(null);
        c = new Container();
    }

    @Test
    void testContainer() throws ContainerException {  // 1. Test
        c.addMember(m1);
        assertNotNull(c);
    }
    @Test
    void addMemberTest() throws ContainerException { // 2. Test
        c.addMember(m1);
        assertEquals(1, c.size());
    }
    @Test
    void getIDTest() {      // 3. Test
        assertEquals(17, m1.getID());
    }

    @Test
    void deleteMemberTest() throws ContainerException {       // 4. Test
        c.addMember(m1);
        c.deleteMember(m1.getID());
        assertEquals(0, c.size());
    }

    @Test
    void addNullMemberTest() throws ContainerException {        // 5. Test
        assertThrows(ContainerException.class,() -> c.addMember(m5));
    }

    @Test
    void delNotExistingMember() throws ContainerException {     // 6. Test
        c.addMember(m1);
        c.addMember(m2);
        c.addMember(m3);
        c.deleteMember(0);
        assertEquals(3, c.size());
    }

    @Test
    void sizeTest() throws ContainerException {     // 7. Test
        c.addMember(m1);
        c.addMember(m3);
        assertEquals(2,c.size());
    }

    @Test
    void searchIDTest() throws ContainerException {     // 8. Test
        c.addMember(m1);
        c.addMember(m2);
        c.addMember(m3);
        assertTrue(c.idEnthalten(17));
    }


    @Test
    void sizeTest2() throws ContainerException {        // 9. Test
        assertEquals(0, c.size());
    }

   /*  @Test
    void dumpTest() {       // 10. Test, Funktioniert nur, wenn dump einen String Rückgabewert hätte
                            // Daher weiß ich nicht, wie man die Methode anders testen soll.
        assertEquals("Member (ID = 17)\nMember (ID = 12)", c.dump());
    }
*/


}
