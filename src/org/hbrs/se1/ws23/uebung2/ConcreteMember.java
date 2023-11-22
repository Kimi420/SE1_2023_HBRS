package org.hbrs.se1.ws23.uebung2;
import java.io.Serializable;

public class ConcreteMember implements Member, Serializable {

    Integer id;
    public ConcreteMember(Integer id) {
        this.id = id;
    }


    public String toString() {
        return "Member (ID = " + this.getID() + ")";
    }

    @Override
    public Integer getID() {
        return this.id;
    }
}
