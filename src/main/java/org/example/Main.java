package org.example;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            runMenu(scanner);
        } finally {
            HibernateUtil.getSessionFactory().close();
        }
    }

    private static void runMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("\n--- To Do List ---");
            System.out.println("1. Add task");
            System.out.println("2. Display tasks");
            System.out.println("3. Remove task");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice;

            try {
                String input = scanner.nextLine();
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            if (choice == 1) {
                System.out.print("Enter task title: ");
                String title = scanner.nextLine();
                addTask(title);
            } else if (choice == 2) {
                displayTasks();
            } else if (choice == 3) {
                System.out.print("Enter task ID to remove: ");
                try {
                    int id = Integer.parseInt(scanner.nextLine());
                    removeTask(id);
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid task ID.");
                }
            } else if (choice == 4) {
                running = false;
            } else {
                System.out.println("Invalid option.");
            }
        }

    }

    public static void addTask(String title) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(new Task(title));
            transaction.commit();
            System.out.println("Task added successfully!");
        } catch (IllegalArgumentException e) {
            rollback(transaction);
            System.out.println(e.getMessage());
        } catch (RuntimeException e) {
            rollback(transaction);
            System.out.println("Unable to add the task. Please check the database connection.");
        }
    }

    public static void displayTasks() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Task> tasks = session.createQuery("from Task order by id", Task.class).getResultList();

            System.out.println("\nCurrent Tasks:");
            if (tasks.isEmpty()) {
                System.out.println("No tasks found.");
            }
            for (Task task : tasks) {
                System.out.println("ID: " + task.getId() + " - " + task.getTitle());
            }
        } catch (RuntimeException e) {
            System.out.println("Unable to load tasks. Please check the database connection.");
        }
    }

    public static void removeTask(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Task task = session.find(Task.class, id);

            if (task != null) {
                session.remove(task);
                System.out.println("Task removed successfully!");
            } else {
                System.out.println("Task not found.");
            }
            transaction.commit();
        } catch (RuntimeException e) {
            rollback(transaction);
            System.out.println("Unable to remove the task. Please check the database connection.");
        }
    }

    private static void rollback(Transaction transaction) {
        if (transaction != null && transaction.isActive()) {
            transaction.rollback();
        }
    }
}
