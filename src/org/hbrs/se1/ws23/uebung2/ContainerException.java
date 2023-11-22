package org.hbrs.se1.ws23.uebung2;

public class ContainerException extends Exception {
    public ContainerException(Integer id) {
        super("Das Member-Objekt mit der ID " + id + " ist bereits vorhanden!");
    }
    public ContainerException() { super("Die ID darf nicht Null sein"); }
}