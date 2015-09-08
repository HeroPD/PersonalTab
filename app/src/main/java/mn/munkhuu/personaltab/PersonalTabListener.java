package mn.munkhuu.personaltab;

/**
 * Created by Showtime on 9/8/15.
 */
public interface PersonalTabListener {

    void onTabSelected(PersonalTab tab);

    void onTabReselected(PersonalTab tab);

    void onTabUnselected(PersonalTab tab);

}
