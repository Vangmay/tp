package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentDescriptor;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonDescriptor;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX = "The appointment index is invalid";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String formatPerson(Person person) {
        return formatPerson(person.getPersonDescriptor());
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String formatPerson(PersonDescriptor person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Email: ")
                .append(person.getEmail())
                .append("; Address: ")
                .append(person.getAddress())
                .append("; Tags: ");
        person.getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code appointment} for display to the user.
     */
    public static String formatAppointment(Appointment appointment) {
        return formatAppointment(appointment.getAppointmentDescriptor());
    }

    /**
     * Formats the {@code appointment} for display to the user.
     */
    public static String formatAppointment(AppointmentDescriptor appointment) {
        final StringBuilder builder = new StringBuilder();
        builder.append(appointment.getAppointmentType())
                .append("; Id: ")
                .append(appointment.getPersonId())
                .append("; Date and Time")
                .append(appointment.getAppointmentDateTime())
                .append("; Sickness: ")
                .append(appointment.getSickness())
                .append("; Medicine: ")
                .append(appointment.getMedicine());
        return builder.toString();
    }
}
