package util;

import db.DBManager;
import model.Book;
import model.User;

import java.util.List;
import java.util.Scanner;

public class ConsoleHelper {
    private static final Scanner scanner = new Scanner(System.in);

    public static int readValidInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number!");
            }
        }
    }

    public static boolean hasBooks() {
        return !DBManager.getAllBooks().isEmpty();
    }

    public static boolean hasUsers() {
        return !DBManager.getAllUsers().isEmpty();
    }

    public static boolean hasUser(int user_id) {
        User user = DBManager.getUserById(user_id);
        return user != null;
    }

    public static boolean hasBook(int book_id) {
        Book book = DBManager.getBookById(book_id);
        return book != null;
    }

    public static void displayAllBooks() {
        List<Book> allBooks = DBManager.getAllBooks();
        if (allBooks.isEmpty()) {
            System.out.println("No books found");
        } else {
            System.out.println("All books in the library: ");
            for (Book b : allBooks) {
                System.out.println("ID: " + b.getId() + " |Title: " + b.getTitle() + " |Author: " + b.getAuthor());
            }
        }
    }

    public static void displayAllUsers() {
        List<User> allUsers =DBManager.getAllUsers();
        if (allUsers.isEmpty()) {
            System.out.println("No users found");
        } else {
            System.out.println("All users in the system: ");
            for (User u : allUsers) {
                System.out.println("ID: " + u.getId() + " Username: " + u.getUsername());
            }
        }
    }
}
