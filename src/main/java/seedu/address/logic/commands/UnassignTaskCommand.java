package seedu.address.logic.commands;

import java.util.List;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.employee.Employee;
import seedu.address.model.task.Task;

/**
 * Unassigns a task from an employee.
 */
public class UnassignTaskCommand extends Command {
    public static final String COMMAND_WORD = "unassigntask";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unassigns a task object identified by TASK_ID from employee identified by EMPLOYEE_ID.\n"
            + "Parameters: taskID, employeeID (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 2";

    public static final String MESSAGE_UNASSIGN_TASK_SUCCESS = "Unassign task success";

    private final int taskID;
    private final int employeeID;

    /**
     * Creates an UnassignTaskCommand to unassign the specified task from the specified employee.
     *
     * @param taskID     ID of the task to unassign
     * @param employeeID ID of the employee from whom the task is to be unassigned
     */
    public UnassignTaskCommand(int taskID, int employeeID) {
        this.taskID = taskID;
        this.employeeID = employeeID;
    }
    /**
     * Method to find if an employee exists based on employee ID.
     *
     * @param model the current model
     * @return the employee if found
     * @throws CommandException if employee does not exist
     */
    public Employee findEmployee(Model model) throws CommandException {
        List<Employee> employeeList = model.getFilteredEmployeeList();
        for (Employee e : employeeList) {
            if (e.getEmployeeId() == employeeID) {
                return e;
            }
        }
        throw new CommandException(Messages.MESSAGE_INVALID_EMPLOYEEID);
    }
    /**
     * Method to find if an employee exists based on task ID.
     *
     * @param model the current model
     * @return the task if found
     * @throws CommandException if task does not exist
     */
    public Task findTask(Model model) throws CommandException {
        List<Task> taskList = model.getFilteredTaskList();
        for (Task t : taskList) {
            if (t.getTaskId() == taskID) {
                return t;
            }
        }
        throw new CommandException(Messages.MESSAGE_INVALID_TASKID);
    }
    /**
     * Executes the command to unassign the task from the employee.
     *
     * @param model the current model
     * @return the command result indicating the success of the operation
     * @throws CommandException if the command cannot be executed
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.updateFilteredTaskList(Model.PREDICATE_SHOW_ALL_TASKS);
        model.updateFilteredEmployeeList(Model.PREDICATE_SHOW_ALL_EMPLOYEES);
        Task assignTask = findTask(model);
        Employee assignEmployee = findEmployee(model);
        if (assignTask != null && assignEmployee != null) {
            Employee updatedEmployee = assignEmployee.removeTask(assignTask.getTaskId());
            model.setEmployee(assignEmployee, updatedEmployee);
            Task updatedTask = assignTask.removeEmployee(assignEmployee.getEmployeeId());
            model.setTask(assignTask, updatedTask);
        }
        return new CommandResult(MESSAGE_UNASSIGN_TASK_SUCCESS);
    }
}
