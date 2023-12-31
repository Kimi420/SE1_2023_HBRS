package org.hbrs.se1.ws23.uebung3.persistence;

import org.hbrs.se1.ws23.uebung2.Member;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenceStrategyStream<E> implements PersistenceStrategy<Member> {

    // URL of file, in which the objects are stored
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private String location = "objects.ser";

    // Backdoor method used only for testing purposes, if the location should be changed in a Unit-Test
    // Example: Location is a directory (Streams do not like directories, so try this out ;-)!
    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    /**
     * Method for opening the connection to a stream (here: Input- and Output-Stream)
     * In case of having problems while opening the streams, leave the code in methods load
     * and save.
     */
    public void openConnection() throws PersistenceException {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(location);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            FileInputStream fileInputStream = new FileInputStream(location);
            objectInputStream = new ObjectInputStream(fileInputStream);
        } catch (IOException e) {
            throw new PersistenceException(PersistenceException.ExceptionType.ConnectionNotAvailable, "Fehler beim Öffnen der Verbindung");
        }

    }


    @Override
    /**
     * Method for closing the connections to a stream
     */
    public void closeConnection() throws PersistenceException {
        try {
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }

            if (objectInputStream != null) {
                objectInputStream.close();
            }
        } catch (IOException e) {
            throw new PersistenceException(PersistenceException.ExceptionType.CantCloseConnection, "Fehler beim Schliessen der Verbindung");
        }
    }

    @Override
    /**
     * Method for saving a list of Member-objects to a disk (HDD)
     */
    public void save(List<Member> member) throws PersistenceException  {
        if(objectOutputStream==null) {
            throw new PersistenceException(PersistenceException.ExceptionType.ConnectionNotAvailable,"Es ist noch keine Verbindung aufgebaut");
        }

        try {
            objectOutputStream.writeObject(member);
        } catch (IOException e) {
            throw new PersistenceException(PersistenceException.ExceptionType.WritingError, "Fehler beim Schreiben in den Stream");
        }
    }

    @Override
    /**
     * Method for loading a list of Member-objects from a disk (HDD)
     * Some coding examples come for free :-)
     * Take also a look at the import statements above ;-!
     */
    public List<Member> load() throws PersistenceException  {
        try {
            // Some Coding hints ;-)
            List<Member> newListe = new ArrayList<>();
            // Reading and extracting the list (try .. catch ommitted here)
            try {
                Object obj = objectInputStream.readObject();
                if (obj instanceof List<?>) {
                    newListe = (List) obj;
                    return newListe;
                }
                } catch (ClassNotFoundException e) {
                throw new PersistenceException(PersistenceException.ExceptionType.ImplementationNotAvailable,"Fehler beim Lesen");
            }

            // and finally close the streams (guess where this could be...?)
            closeConnection();
        } catch (IOException e) {
                throw new PersistenceException(PersistenceException.ExceptionType.ImplementationNotAvailable, "Fehler beim Laden des Streams");
            }
        return null;
    }
}
