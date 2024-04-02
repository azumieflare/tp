package seedu.address.model.employee;

/**
 * Represents an Employee's Id.
 */
public class EmployeeId {
    public final int employeeId;

    /**
     * Constructs a {@code EmployeeId}.
     *
     * @param id A valid employeeId.
     */
    public EmployeeId(int id) {
        employeeId = id;
    }

    /**
     * To access the employeeId
     *
     * @return The employeeId
     */
    public int getId() {
        return employeeId;
    }

    @Override
    public String toString() {
        return "" + employeeId;
    }
}
