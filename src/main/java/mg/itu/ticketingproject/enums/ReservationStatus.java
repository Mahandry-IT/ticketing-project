package mg.itu.ticketingproject.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReservationStatus {
    PENDING("PENDING"),
    CONFIRMED("CONFIRMED"),
    CANCELED("CANCELED");

    private final String status;

    public static ReservationStatus fromString(String status) {
        for (ReservationStatus reservationStatus : ReservationStatus.values()) {
            if (reservationStatus.getStatus().equalsIgnoreCase(status)) {
                return reservationStatus;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + status);
    }
}
