package cx2002grp2.stars.util;

import java.util.Comparator;

import cx2002grp2.stars.data.dataitem.CourseIndex;
import cx2002grp2.stars.data.dataitem.Registration;

public class RegistrationComparator implements Comparator<Registration> {

    @Override
    public int compare(Registration o1, Registration o2) {
        // TODO Auto-generated method stub
        int deltaStatus = o1.getStatus().compareTo(o2.getStatus());

        if (deltaStatus == 0) {
            return o1.getRegisterDateTime().compareTo(o2.getRegisterDateTime());
        }
        else {
            return deltaStatus;
        }
    }

} 