package mg.itu.ticketingproject.util;

import java.math.BigDecimal;
import java.util.List;

public class ClassUtil {
    public static List<BigDecimal> toBigDecimalList(List<Double> doubleList) {
        if (doubleList == null || doubleList.isEmpty()) {
            return List.of();
        }
        return doubleList.stream()
                .map(BigDecimal::valueOf)
                .toList();
    }
}
