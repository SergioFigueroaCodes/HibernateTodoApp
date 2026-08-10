package org.example;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n--- To Do List ---");
            System.out.println("1. Add task");
            System.out.println("2. Display tasks");
            System.out.println("3. Remove task");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.print("Enter task title: ");
                String title = scanner.nextLine();
                addTask(title);
            } else if (choice == 2) {
                displayTasks();
            } else if (choice == 3) {
                System.out.print("Enter task ID to remove: ");
                int id = scanner.nextInt();
                scanner.nextLine();
                removeTask(id);
            } else if (choice == 4) {
                running = false;
            } else {
                System.out.println("Invalid option.");
            }
        }

        scanner.close();
        HibernateUtil.getSessionFactory().close();
    }

    public static void addTask(String title) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Task task = new Task(title);
        session.persist(task);

        transaction.commit();
        session.close();

        System.out.println("Task added successfully!");
    }

    public static void displayTasks() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<Task> tasks = session.createQuery("from Task", Task.class).getResultList();

        System.out.println("\nCurrent Tasks:");
        for (Task task : tasks) {
            System.out.println("ID: " + task.getId() + " - " + task.getTitle());
        }

        session.close();
    }

    public static void removeTask(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Task task = session.find(Task.class, id);

        if (task != null) {
            session.remove(task);
            System.out.println("Task removed successfully!");
        } else {
            System.out.println("Task not found.");
        }

        transaction.commit();
        session.close();
    }
}