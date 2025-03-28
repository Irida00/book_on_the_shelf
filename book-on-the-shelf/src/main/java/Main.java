import db.DBManager;
import model.Book;
import model.User;
import util.ConsoleHelper;
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

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": //Create a new user
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine().trim();
                    DBManager.insertUser(new User(username));
                    break;

                case "2": //Add a new book
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine().trim();
                    System.out.print("Enter book author: ");
                    String author = scanner.nextLine().trim();
                    DBManager.insertBook(new Book(title, author));
                    break;

                case "3": //Assign a book to a user
                    if (!ConsoleHelper.hasUsers()) {
                        System.out.println("No users found. Please add a user first.");
                        break;
                    }
                    if (!ConsoleHelper.hasBooks()) {
                        System.out.println("No books available in the system. Please add a book first.");
                        break;
                    }

                    ConsoleHelper.displayAllUsers();
                    ConsoleHelper.displayAllBooks();

                    int userId = ConsoleHelper.readValidInt("Enter user ID: ");

                    if(!ConsoleHelper.hasUser(userId)) {
                        System.out.println("No user found with ID " + userId);
                        break;
                    }

                    int bookId = ConsoleHelper.readValidInt("Enter book ID: ");

                    if(!ConsoleHelper.hasBook(bookId)) {
                        System.out.println("No book found with ID " + bookId);
                        break;
                    }

                    System.out.print("Enter book status (reading, wishlist, finished): ");
                    String status = scanner.nextLine().trim();
                    DBManager.insertUserBook(userId, bookId, status);
                    break;

                case "4": //View all books of a user
                    if (!ConsoleHelper.hasUsers()) {
                        System.out.println("No users found. Please add a user first.");
                        break;
                    }

                    ConsoleHelper.displayAllUsers();

                    int viewUserId = ConsoleHelper.readValidInt("Enter user ID: ");

                    if(!ConsoleHelper.hasUser(viewUserId)) {
                        System.out.println("No user found with ID " + viewUserId);
                        break;
                    }

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
                    if (!ConsoleHelper.hasUsers()) {
                        System.out.println("No users found. Please add a user first.");
                        break;
                    }
                    if (!ConsoleHelper.hasBooks()) {
                        System.out.println("No books available in the system. Please add a book first.");
                        break;
                    }

                    ConsoleHelper.displayAllUsers();
                    ConsoleHelper.displayAllBooks();

                    int updateUserId = ConsoleHelper.readValidInt("Enter user ID: ");

                    if(!ConsoleHelper.hasUser(updateUserId)) {
                        System.out.println("No user found with ID " + updateUserId);
                        break;
                    }

                    int updateBookId = ConsoleHelper.readValidInt("Enter book ID: ");

                    if(!ConsoleHelper.hasBook(updateBookId)) {
                        System.out.println("No book found with ID " + updateBookId);
                        break;
                    }

                    System.out.print("Enter new status (reading, wishlist, finished): ");
                    String newStatus = scanner.nextLine().trim();
                    DBManager.updateUserBookStatus(updateUserId, updateBookId, newStatus);
                    break;

                case "6": //View all users
                    ConsoleHelper.displayAllUsers();
                    break;

                case "7": //View all books
                    ConsoleHelper.displayAllBooks();
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


