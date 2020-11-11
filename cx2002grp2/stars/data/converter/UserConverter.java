package cx2002grp2.stars.data.converter;

import java.util.Arrays;
import java.util.List;

import cx2002grp2.stars.data.dataitem.User;

public class UserConverter implements Converter<User> {
    private static final int ROW_SIZE = 5;
    private static final int NAME_POS = 0;
    private static final int PASSWD_POS = 2;
    private static final int DOMAIN_POS = 1;
    private static final int EMAIL_POS = 3;
    private static final int PHONE_POS = 4;

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
