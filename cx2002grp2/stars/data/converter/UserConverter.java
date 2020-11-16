package cx2002grp2.stars.data.converter;

import java.util.Arrays;
import java.util.List;

import cx2002grp2.stars.data.dataitem.User;

/**
 * Concrete implementation for {@link Converter converter} of {@link User User}
 */
public class UserConverter implements Converter<User> {

    /**
     * Size of row of the table storing the user.
     */
    private static final int ROW_SIZE = 5;
    /**
     * Position of field in the row of table.
     */
    private static final int NAME_POS = 0, PASSWD_POS = 2, DOMAIN_POS = 1, EMAIL_POS = 3, PHONE_POS = 4;

    @Override
    public List<String> toStringList(User item) {
        String[] row = new String[ROW_SIZE];

        row[NAME_POS] = item.getUsername();
        row[PASSWD_POS] = item.getHashedPassword();
        row[DOMAIN_POS] = item.getDomain().name();
        row[EMAIL_POS] = item.getEmail();
        row[PHONE_POS] = item.getPhoneNo();

        return Arrays.asList(row);
    }

    @Override
    public User fromStringList(List<String> strings) {
        String username, hashedPassword, email, phoneNo;
        User.Domain domain;

        username = strings.get(NAME_POS);
        hashedPassword = strings.get(PASSWD_POS);
        domain = User.Domain.valueOf(strings.get(DOMAIN_POS));
        email = strings.get(EMAIL_POS);
        phoneNo = strings.get(PHONE_POS);

        return new User(username, hashedPassword, domain, email, phoneNo);
    }

}
