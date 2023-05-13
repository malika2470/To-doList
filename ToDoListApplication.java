import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
//java TodoListApplication
class Task {
    private String description;
    private boolean completed;
    private LocalDate createdDate;
    private LocalDate completedDate;

    public Task(String description) {
        this.description = description;
        this.completed = false;
        this.createdDate = LocalDate.now();
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void markAsCompleted() {
        if (!completed) {
            completed = true;
            completedDate = LocalDate.now();
        }
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public LocalDate getCompletedDate() {
        return completedDate;
    }

    public int getDaysToComplete() {
        if (completedDate == null) {
            return -1; // task is not yet completed
        }
        long daysBetween = ChronoUnit.DAYS.between(createdDate, completedDate);
        return (int) daysBetween;
    }
}

public class TodoListApplication {
    private static final List<Task> tasks = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to the Todo List Application!");

        while (true) {
            printMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    addTask();
                    break;
                case 2:
                    viewTasks();
                    break;
                case 3:
                    markTaskAsCompleted();
                    break;
                case 4:
                    removeTask();
                    break;
                case 5:
                    showStatistics();
                    break;
                case 6:
                    System.out.println("Thank you for using the Todo List Application. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private static void printMenu() {
        System.out.println("------------------------------");
        System.out.println("1. Add Task");
        System.out.println("2. View Tasks");
        System.out.println("3. Mark Task as Completed");
        System.out.println("4. Remove Task");
        System.out.println("5. Show Statistics");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void addTask() {
        System.out.print("Enter task description: ");
        String description = scanner.nextLine();

        Task task = new Task(description);
        tasks.add(task);

        System.out.println("Task added successfully.");
    }

    private static void viewTasks() {
        System.out.println("Todo List:");
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                System.out.println((i + 1) + ". " + task.getDescription() + " - " + (task.isCompleted() ? "Completed" : "Not Completed"));
            }
        }
    }
    private static void markTaskAsCompleted() {
        System.out.print("Enter the task number to mark as completed: ");
        int taskNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline character
    
        if (taskNumber < 1 || taskNumber > tasks.size()) {
            System.out.println("Invalid task number. Please try again.");
        } else {
            Task task = tasks.get(taskNumber - 1);
            task.markAsCompleted();
            System.out.println("Task marked as completed.");
        }
    }
    
    private static void removeTask() {
        System.out.print("Enter the task number to remove: ");
        int taskNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline character
    
        if (taskNumber < 1 || taskNumber > tasks.size()) {
            System.out.println("Invalid task number. Please try again.");
        } else {
            tasks.remove(taskNumber - 1);
            System.out.println("Task removed successfully.");
        }
    }
    
    private static void showStatistics() {
        int numCompletedTasks = 0;
        int totalDaysToComplete = 0;
        LocalDate firstCompletionDate = null;
    
        for (Task task : tasks) {
            if (task.isCompleted()) {
                numCompletedTasks++;
    
                int daysToComplete = task.getDaysToComplete();
                if (daysToComplete >= 0) {
                    totalDaysToComplete += daysToComplete;
                }
    
                LocalDate completedDate = task.getCompletedDate();
                if (firstCompletionDate == null || (completedDate != null && completedDate.isBefore(firstCompletionDate))) {
                    firstCompletionDate = completedDate;
                }
            }
        }
    
        System.out.println("Todo List Statistics:");
        System.out.println("Total tasks: " + tasks.size());
        System.out.println("Completed tasks: " + numCompletedTasks);
        System.out.println("Incomplete tasks: " + (tasks.size() - numCompletedTasks));
    
        if (numCompletedTasks > 0) {
            double averageDaysToComplete = (double) totalDaysToComplete / numCompletedTasks;
            System.out.println("Average task completion time: " + averageDaysToComplete + " days");
            System.out.println("First completion date: " + firstCompletionDate);
        }
    }
}     
    
