package seedu.address.logic.commands;

import java.util.List;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.employee.Employee;
import seedu.address.model.task.Task;

/**
 * Assigns a task to an employee.
 */
public class AssignTaskCommand extends Command {
    public static final String COMMAND_WORD = "assigntask";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Assigns a task object identified by TASK_ID to employee identified by EMPLOYEE_ID.\n"
            + "Parameters: taskID, employeeID (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 2";

    public static final String MESSAGE_ASSIGN_TASK_SUCCESS = "Assign task success";

    private final int taskID;
    private final int employeeID;

    /**
     * Creates an AssignTaskCommand to assign a task to an employee.
     *
     * @param taskID The ID of the task to be assigned.
     * @param employeeID The ID of the employee to whom the task is assigned.
     */
    public AssignTaskCommand(int taskID, int employeeID) {
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
     * Executes the AssignTaskCommand to assign a task to an employee.
     *
     * @param model The model in which the command should be executed.
     * @return The result of the command execution.
     * @throws CommandException If there is an error executing the command.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.updateFilteredTaskList(Model.PREDICATE_SHOW_ALL_TASKS);
        model.updateFilteredEmployeeList(Model.PREDICATE_SHOW_ALL_EMPLOYEES);
        Task assignTask = findTask(model);
        Employee assignEmployee = findEmployee(model);
        if (assignTask != null && assignEmployee != null) {
            Employee updatedEmployee = assignEmployee.assignTask(assignTask);
            model.setEmployee(assignEmployee, updatedEmployee);
            Task updatedTask = assignTask.assignEmployee(assignEmployee);
            model.setTask(assignTask, updatedTask);
        }
        return new CommandResult(MESSAGE_ASSIGN_TASK_SUCCESS);
    }
}
