package cx2002grp2.stars.util;

import java.util.Comparator;
import java.util.Objects;

public class Pair<T1, T2> {
    private T1 val1;
    private T2 val2;

    private int hashCodeBuf;

    public Pair(T1 val1, T2 val2) {
        this.val1 = val1;
        this.val2 = val2;

        hashCodeBuf = Objects.hash(val1, val2);
    }

    public T1 val1() {
        return this.val1;
    }

    public T2 val2() {
        return this.val2;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (o == this) {
            return true;
        }

        if (!(o instanceof Pair<?, ?>)) {
            return false;
        }

        Pair<?, ?> pair = (Pair<?, ?>) o;

        return Objects.equals(val1, pair.val1) && Objects.equals(val2, pair.val2);
    }

    @Override
    public int hashCode() {
        return hashCodeBuf;
    }
}
