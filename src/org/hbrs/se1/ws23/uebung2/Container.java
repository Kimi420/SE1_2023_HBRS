package org.hbrs.se1.ws23.uebung2;
import org.hbrs.se1.ws23.uebung3.persistence.PersistenceException;
import org.hbrs.se1.ws23.uebung3.persistence.PersistenceStrategy;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Container {

    ArrayList<Member> aList;

    private static boolean erstellt = false;
    private PersistenceStrategy<Member> ps;

    public void setPersistenceStrategy(PersistenceStrategy<Member> ps) { this.ps = ps;}

    public Container() throws Exception {
        if(!erstellt) {
            aList = new ArrayList<>();
            erstellt = true;
        } else {
            throw new Exception("Es wurde bereits ein Container erstellt!");
        }
    }
    public boolean idEnthalten(Integer idToFind) {
        for(Member m : aList) {
            if(m.getID().equals(idToFind)) {
                return true;
            }
        }
        return false;
    }



    public Member zugMember(Integer id) {
        for(Member m : aList) {
            if(m.getID() == id) {
                return m;
            }
        }
        return null;
    }
    public void addMember(Member m) throws ContainerException{
      if(m.getID()!=null) {
          if (!idEnthalten(m.getID())) {
              aList.add(m);
          } else throw new ContainerException(m.getID());
      } else throw new ContainerException();
    }

    public String deleteMember(Integer id) {
        if (idEnthalten(id)) {
            for (Member m : aList) {
                if(m.getID() == id) {
                    aList.remove(m);
                    return "Der Member mit der ID " + id + " wurde erfolgreich entfernt.";
                }
            }
        }
        return "Es wurde kein Member mit der ID " + id + " gefunden.";

    }

    public void dump() {
        for(Member m : aList) {
            System.out.println(m.toString());
        }
    }

    public int size() {
        return aList.size();
    }

    public void store() throws PersistenceException {
        try {
            ps.openConnection();
            ps.save(aList);
        } catch (FileNotFoundException e) {
            throw new PersistenceException(PersistenceException.ExceptionType.SavingError,"Fehler beim Speichern des Containers");
        }
    }

    public void load() throws PersistenceException {
        aList = (ArrayList<Member>) ps.load();
        ps.closeConnection();
    }

    public List<Member> getCurrentList() {
        return aList;
    }
}
