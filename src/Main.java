import db.DBManager;
import model.Book;
import model.User;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DBManager.createTables();

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("""
                    
                    1. Create a new user
                    2. Add a new book
                    3. Assign a book to a user
                    4. View all books of a user
                    5. Update book status
                    6. View all users
                    7. View all books
                    8. Exit
                    """);
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1": //Create a new user
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    DBManager.insertUser(new User(username));
                    break;

                case "2": //Add a new book
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter book author: ");
                    String author = scanner.nextLine();
                    DBManager.insertBook(new Book(title, author));
                    break;

                case "3": //Assign a book to a user
                    System.out.print("Enter user ID: ");
                    int userId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter book ID: ");
                    int bookId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter book status (reading, wishlist, finished): ");
                    String status = scanner.nextLine();
                    DBManager.insertUserBook(userId, bookId, status);
                    break;

                case "4": //View all books of a user
                    System.out.print("Enter user ID: ");
                    int viewUserId = Integer.parseInt(scanner.nextLine());
                    List<Book> books = DBManager.getBooksByUser(viewUserId);
                    if (books.isEmpty()) {
                        System.out.println("No books found! You should start reading!");
                    } else {
                        for (Book b : books) {
                            System.out.println(b.getTitle() + " by " + b.getAuthor());
                        }
                    }
                    break;

                case "5": //Update book status
                    System.out.print("Enter user ID: ");
                    int updateUserId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter book ID: ");
                    int updateBookId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter new status (reading, wishlist, finished): ");
                    String newStatus = scanner.nextLine();
                    DBManager.updateUserBookStatus(updateUserId, updateBookId, newStatus);
                    break;

                case "6": //View all users
                    List<User> users = DBManager.getAllUsers();
                    if (users.isEmpty()) {
                        System.out.println("No users found");
                    } else {
                        System.out.print("All users:");
                        for (User u : users) {
                            System.out.println("ID: " + u.getId() + " |Username: " + u.getUsername());
                        }
                    }
                    break;

                case "7": //View all books
                    List<Book> allBooks = DBManager.getAllBooks();
                    if (allBooks.isEmpty()) {
                        System.out.println("No books found");
                    } else {
                        System.out.print("All books:");
                        for (Book b : allBooks) {
                            System.out.println("ID: " + b.getId() + " |Title: " + b.getTitle() + " |Author: " + b.getAuthor());
                        }
                    }
                    break;

                case "8":
                    System.out.println("Exiting program! Continue reading!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice! Try again!");
            }
        }
    }
}


