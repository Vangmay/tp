package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.ParserUtil.APPOINTMENT_ENTITY_STRING;
import static seedu.address.logic.parser.ParserUtil.PERSON_ENTITY_STRING;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Edits the details of an existing entity (person or appointment) in the address book.
 */
public abstract class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the details of an entity identified "
            + "by the index number used in the displayed list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: ENTITY_TYPE (person/appt) INDEX (must be a positive integer) [DATA_FIELDS]...\n"
            + "Example: " + COMMAND_WORD + " " + PERSON_ENTITY_STRING + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com\n"
            + "Example: " + COMMAND_WORD + " " + APPOINTMENT_ENTITY_STRING + " 1 "
            + PREFIX_APPOINTMENT_TYPE + "Health Checkup "
            + PREFIX_MEDICINE + "Panadol";

    protected final Index targetIndex;
    protected final EditEntityDescriptor editEntityDescriptor;

    /**
     * @param targetIndex Index of entity to be edited.
     */
    public EditCommand(Index targetIndex, EditEntityDescriptor editEntityDescriptor) {
        requireNonNull(targetIndex);
        requireNonNull(editEntityDescriptor);
        this.targetIndex = targetIndex;
        this.editEntityDescriptor = editEntityDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<?> lastShownList = getFilteredList(model);

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(getInvalidIndexMessage());
        }

        Object entityToEdit = lastShownList.get(targetIndex.getZeroBased());
        Object editedEntity = createEditedEntity(model, entityToEdit, editEntityDescriptor);

        if (isSameEntity(model, editedEntity, entityToEdit) && hasEntity(model, editedEntity)) {
            throw new CommandException(getDuplicateMessage());
        }

        editEntity(model, editedEntity, entityToEdit);
        return new CommandResult(String.format(getSuccessMessage(), formatEntity(editedEntity)));
    }

    /**
     * Checks if entity already exists in list
     */
    protected abstract boolean hasEntity(Model model, Object entity) throws CommandException;

    /**
     * Checks if entity to edit is the same as edited entity
     */
    protected abstract boolean isSameEntity(Model model, Object editedEntity, Object entityToEdit)
            throws CommandException;

    /**
     * Gets the filtered list of entities in the model.
     */
    protected abstract List<?> getFilteredList(Model model);

    /**
     * Edits Entity
     */
    protected abstract void editEntity(Model model, Object editedEntity, Object entityToEdit) throws CommandException;

    /**
     * Adds the entity to the model.
     */
    protected abstract Object createEditedEntity(Model model, Object entityToEdit,
                                                 EditEntityDescriptor editEntityDescriptor) throws CommandException;

    /**
     * Returns success message to display upon adding entity.
     */
    protected abstract String getSuccessMessage();

    /**
     * Returns duplicate message to display upon adding entity.
     */
    protected abstract String getDuplicateMessage();

    /**
     * Returns the invalid index message when the index is out of bounds.
     */
    protected abstract String getInvalidIndexMessage();

    /**
     * Formats the entity for displaying in the success message.
     */
    protected abstract String formatEntity(Object entity);

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand otherEditCommand)) {
            return false;
        }

        return targetIndex.equals(otherEditCommand.targetIndex)
                && editEntityDescriptor.equals(otherEditCommand.editEntityDescriptor);
    }

    /**
     * Abstract descriptor for editing entities.
     */
    public abstract static class EditEntityDescriptor {
        // This class can have common fields or methods for all entities.

        public EditEntityDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditEntityDescriptor(EditEntityDescriptor toCopy) {
            // Copy logic for common fields, if any, can go here.
        }

        @Override
        public abstract boolean equals(Object other);
    }

}
