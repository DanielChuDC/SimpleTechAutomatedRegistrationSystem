package cx2002grp2.stars.util;

import java.util.Comparator;

import cx2002grp2.stars.data.dataitem.Registration;

/**
 * Comparator for registration used to manage wait list.
 * <p>
 * Compare the status of two registration first. If the status are the same,
 * compare the register time stamp.
 * <p>
 * Using this comparator to sort registration will results in the registration
 * to be partitioned according to the registration status. In each partition of
 * registration, the registration will be sorted according to the registration
 * timestamp
 */
public class RegistrationComparator implements Comparator<Registration> {

    @Override
    public int compare(Registration o1, Registration o2) {
        int deltaStatus = o1.getStatus().compareTo(o2.getStatus());

        if (deltaStatus == 0) {
            return o1.getRegisterDateTime().compareTo(o2.getRegisterDateTime());
        } else {
            return deltaStatus;
        }
    }

}