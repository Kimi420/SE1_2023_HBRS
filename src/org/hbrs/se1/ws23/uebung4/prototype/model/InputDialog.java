package org.hbrs.se1.ws23.uebung4.prototype.model;

import org.hbrs.se1.ws23.uebung4.prototype.control.UserStory;
import org.hbrs.se1.ws23.uebung4.prototype.control.Util;
import org.hbrs.se1.ws23.uebung4.prototype.model.exception.ContainerException;
import org.hbrs.se1.ws23.uebung4.prototype.view.UserStoryView;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
public class InputDialog {

    final static String LOCATION = "allStories.ser";
    private List<UserStory> UserStoryListe = Container.getInstance().getCurrentList();

    public void startEingabe() throws ContainerException, Exception {
        String strInput = null;
        System.out.println("UserStory-Tool V1.0 by Julius P. (dedicated to all my friends)");
        // Initialisierung des Eingabe-View
        // ToDo: Funktionsweise des Scanners erklären (F3)
        // Zu aller erst wird ein Scanner erstellt. Dieser liest den Input über das Terminal ein.
        // Durch scanner.nextLine() wird die Zeile eingelesen und in einen String gepackt.
        // Danach wird der String gesplittet/geteilt. Das erste Wort (was normalerweise ein Command ist. Der Rest danach soll erstmal egal sein)
        // ist dann der Command. Je nach Command wird was anderes ausgeführt (siehe if-Bedingungen)
        Scanner scanner = new Scanner( System.in );

        while ( true ) {
            // Ausgabe eines Texts zur Begruessung

            System.out.print("> ");

            strInput = scanner.nextLine();

            // Extrahiert ein Array aus der Eingabe
            String[] strings = strInput.split(" ");

            // 	Falls 'help' eingegeben wurde, werden alle Befehle ausgedruckt
            if (strings[0].equals("help")) {
                System.out.println("Folgende Befehle stehen zur Verfuegung: help, dump, dump projekt *projektname*, enter, exit, store, load, search *projektname*, ");
                continue;
            }
            // Auswahl der bisher implementierten Befehle:
            if (strings[0].equals("dump") && strings.length == 1) {
                if (UserStoryListe.size() == 0) {
                    System.out.println("Die UserStory-Liste ist leer!");
                }
                startAusgabe();
                continue;
            }
            // Auswahl der bisher implementierten Befehle:
            if (strings[0].equals("enter")) {
                System.out.println("Bitte geben sie die gewünschte UserStory-ID ein:");
                int id = scanner.nextInt();
                while (Util.UserStoryCheck(id) != null) {
                    System.out.println("Die von dir übergebene ID ist bereits enthalten. Bitte wähle eine neue ID");
                    id = scanner.nextInt();
                    scanner.nextLine();
                }
                UserStory us = new UserStory();
                us.setId(id);
                Container.getInstance().addUserStory(us);
                System.out.println("Wollen sie weitere Informationen speichern? (Y/n)");
                if (scanner.next().equals("Y")) {
                    System.out.println("Wenn sie Informationen nicht angeben wollen/können, so geben sie entweder ein - (bei Zeichenketten) oder eine 0 (bei Zahlen) an.");
                    System.out.println("Geben sie einen Titel für die neue UserStory ein");
                    us.setTitel(scanner.next());
                    System.out.println("Geben sie einen Mehrwert für die neue UserStory ein");
                    us.setMehrwert(scanner.nextInt());
                    System.out.println("Geben sie eine Strafe für die neue UserStory ein. Sollte es noch keine Strafe gegeben haben, so empfiehlt sich die Eingabe 0");
                    us.setStrafe(scanner.nextInt());
                    System.out.println("Geben sie einen Aufwand für die neue UserStory ein");
                    us.setAufwand(scanner.nextInt());
                    System.out.println("Geben sie ein Risiko für die neue UserStory ein. Besteht kein Risiko,so empfiehlt sich die Eingabe 0");
                    us.setRisk(scanner.nextInt());
                    System.out.println("Geben sie die Priorität für die neue UserStory ein");
                    us.setPrio(scanner.nextDouble());
                    scanner.nextLine();
                    System.out.println("UserStory erfolgreich hinzugefügt!");
                }
                System.out.println("Soll der User einem Projekt hinzugefügt werden? (y/n)");
                if (scanner.next().equals("y")) {
                    System.out.println("Geben Sie den Projektnamen ein:");
                    us.setProject(scanner.next());
                }
                scanner.nextLine();
                continue;
            }
            if (  strings[0].equals("store")  ) {
                try{
                    Container.getInstance().store();
                } catch(ContainerException e)  {
                    throw new ContainerException("Fehler beim speichern");
                }
                continue;
            }
            if( strings[0].equals("load") ) {
                Container.getInstance().load();
                continue;
            }
            if( strings[0].equals("search") ) {
                String proj = strings[1];
                if(Util.UserStoryFilter(proj).size()>0) {
                    System.out.println("Es wurden " + Util.UserStoryFilter(proj).size() + " UserStories in deinem Projekt gefunden. Für genauere Angaben zu den einzelnen UserStories benutze dump projektname");
                    continue;
                } else {
                    System.out.println("Es wurde kein UserStory mit diesem Projektnamen gefunden");
                    continue;
                }
            }
            if( strings[0].equals("dump") && strings[1].equals("projekt") ) {
                String proj = strings[2];
                if(Util.UserStoryFilter(proj)!=null) {
                    System.out.println("Es gibt folgende UserStories im Projekt " + proj);
                    List<UserStory> dumpList = Util.UserStoryFilter(proj);
                    for (UserStory u : dumpList) {
                        System.out.println("UserStory Nr." + u.getId() + ",Titel: „" + u.getTitel() + "“, Projekt " + u.getProject());
                    }
                } else {
                    System.out.println("Es wurden keine UserStories mit diesem Projekt gefunden.");

                }
                continue;
            }
            if( strings[0].equals("exit") ) {
                break;
            }
            System.out.println("Command nicht gefunden. Der Command help kann dir helfen :)");
        }
    }

    /**
     * Diese Methode realisiert die Ausgabe.
     */
    public void startAusgabe() {

        Collections.sort(UserStoryListe, ((o1,o2) -> (int) (o1.getPrio() - o2.getPrio())));
        // Klassische Ausgabe ueber eine For-Each-Schleife
        for (UserStory story : UserStoryListe) {
            System.out.println("UserStory Nr." + story.getId() + ", Titel:„" + story.getTitel() + "“, Mehrwert: " +
            story.getMehrwert() + ", Strafe(n): " + story.getStrafe() + ", Aufwand: " + story.getAufwand() +
            ", Risiko: " + story.getRisk() + ", Priotität: " + story.getPrio() + ", Projekt: " + story.getProject());
        }

    }

}
