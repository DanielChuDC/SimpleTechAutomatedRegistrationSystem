package cx2002grp2.stars.util;

import java.util.Comparator;

import cx2002grp2.stars.dataitem.Registration;

/**
 * Comparator for registration used to manage wait list.
 * <p>
 * Compare the status of two registration first. If the status are the same,
 * compare the register time stamp. If time stamp are the same, compare the
 * username of the student of the registration.
 * <p>
 * Using this comparator to sort registration will results in the registration
 * to be partitioned according to the registration status. In each partition of
 * registration, the registration will be sorted according to the registration
 * timestamp
 */
public class RegistrationComparator implements Comparator<Registration> {

    @Override
    public int compare(Registration o1, Registration o2) {
        int result = o1.getStatus().compareTo(o2.getStatus());

        if (result != 0) {
            return result;
        }

        result = o1.getRegisterDateTime().compareTo(o2.getRegisterDateTime());
        if (result != 0) {
            return result;
        }

        if (o1.getStudent() == o2.getStudent()) { return 0; }
        if (o1.getStudent() == null) { return -1; }
        if (o2.getStudent() == null) { return 1; }
        return o1.getStudent().getUsername().compareTo(o2.getStudent().getUsername());
    }

}