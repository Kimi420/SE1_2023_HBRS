package org.hbrs.se1.ws23.uebung4.prototype.model;

import org.hbrs.se1.ws23.uebung3.persistence.PersistenceException;
import org.hbrs.se1.ws23.uebung3.persistence.PersistenceStrategy;
import org.hbrs.se1.ws23.uebung4.prototype.control.UserStory;

import java.io.*;
import java.util.List;

public class PSStream implements PersistenceStrategy {

    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private List<UserStory> con;
    @Override
    public void openConnection() throws PersistenceException, FileNotFoundException {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(InputDialog.LOCATION);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            FileInputStream fileInputStream = new FileInputStream(InputDialog.LOCATION);
            objectInputStream = new ObjectInputStream(fileInputStream);
        } catch (IOException e) {
            throw new PersistenceException(PersistenceException.ExceptionType.ConnectionNotAvailable, "Fehler beim Öffnen der Verbindung");
        }
    }

    @Override
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
    public List<UserStory> load() throws PersistenceException {
        try {
            // Some Coding hints ;-)
            List<UserStory> newCon;
            // Reading and extracting the list (try .. catch ommitted here)
            try {
                Object obj = objectInputStream.readObject();
                if (obj instanceof List<?>) {
                    newCon = (List) obj;
                    return newCon;
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

    @Override
    public void save(List member) throws PersistenceException {
        if(objectOutputStream==null) {
            throw new PersistenceException(PersistenceException.ExceptionType.ConnectionNotAvailable,"Es ist noch keine Verbindung aufgebaut");
        }
        try {
            objectOutputStream.writeObject(member);
        } catch (IOException e) {
            throw new PersistenceException(PersistenceException.ExceptionType.WritingError, "Fehler beim Schreiben in den Stream");
        }
    }
}
