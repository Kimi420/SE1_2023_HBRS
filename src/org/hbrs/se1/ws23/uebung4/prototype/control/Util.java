package org.hbrs.se1.ws23.uebung4.prototype.control;

import org.hbrs.se1.ws23.uebung4.prototype.model.Container;

import java.util.List;
import java.util.stream.Collectors;


public class Util {

    private static List<UserStory> list = Container.getInstance().getCurrentList();


    public static boolean contains(UserStory userStory) {
        int ID = userStory.getId();
        for ( UserStory userStory1 : list) {
            if ( userStory1.getId() == ID ) {
                return true;
            }
        }
        return false;
    }
    public static UserStory UserStoryCheck(int id) {
        for(UserStory u: list) {
            if(u.getId() == id) return u;
        }
        return null;
    }

    public static List<UserStory> UserStoryFilter(String project) {
        List<UserStory> listMitName = list.stream()
                .filter(userstory -> userstory.getProject().equals(project))
                .filter(userstory -> userstory.getRisk() <=5)
                .collect(Collectors.toList());
        return listMitName;
    }


}
