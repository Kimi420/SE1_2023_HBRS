package org.hbrs.se1.ws23.uebung2.test;

import org.hbrs.se1.ws23.uebung2.ConcreteMember;
import org.hbrs.se1.ws23.uebung2.Container;
import org.hbrs.se1.ws23.uebung2.Member;
import org.hbrs.se1.ws23.uebung2.MemberView;
import org.hbrs.se1.ws23.uebung3.persistence.PersistenceException;
import org.hbrs.se1.ws23.uebung3.persistence.PersistenceStrategyMongoDB;
import org.hbrs.se1.ws23.uebung3.persistence.PersistenceStrategyStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.management.MemoryManagerMXBean;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PersistenceTest {

    Container c;
    Member m1;
    Member m2;
    Member m3;
    Member m4;

    @BeforeEach
    void setUp() throws Exception {
        c = new Container();
        m1 = new ConcreteMember(14);
        m2 = new ConcreteMember(11);
        m3 = new ConcreteMember(0);
        m4 = new ConcreteMember(110);
        c.addMember(m1);
        c.addMember(m2);
        c.addMember(m3);
        c.addMember(m4);
    }

    @Test
    void NullStrategyTest() {
        c.setPersistenceStrategy(null);
        assertThrows(NullPointerException.class, () -> c.store());
    }

    @Test
    void MongoDBTest() {
        PersistenceStrategyMongoDB ps = new PersistenceStrategyMongoDB();
        c.setPersistenceStrategy(ps);
        assertThrows(UnsupportedOperationException.class, () -> c.store());
    }

    @Test
    void locationTest() {
        PersistenceStrategyStream ps = new PersistenceStrategyStream();
        ps.setLocation("C:\\Users\\Downloads\\");
        assertThrows(PersistenceException.class, () -> ps.openConnection());
    }

    @Test
    void memberLoadTest() throws PersistenceException {
        PersistenceStrategyStream ps = new PersistenceStrategyStream();
        c.setPersistenceStrategy(ps);
        ps.setLocation("C:\\Users\\kimiw\\Downloads\\members.tmp");
        List<Member> list = c.getCurrentList();
        c.store();
        c.deleteMember(11);
        assertFalse(c.idEnthalten(11));
        c.load();
        assertTrue(c.idEnthalten(11));
        assertTrue(c.idEnthalten(14));
        assertTrue(c.idEnthalten(0));
        assertTrue(c.idEnthalten(110));
    }
}
