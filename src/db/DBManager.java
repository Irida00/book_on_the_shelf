package db;

import model.Book;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private static final String DB_URL = "jdbc:sqlite:books.db";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL);
            System.out.println("Connecting to books.db!");
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
        return conn;
    }

    public static void createTables() {
        String createBooks = """
                CREATE TABLE IF NOT EXISTS books (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                title TEXT NOT NULL,
                author TEXT NOT NULL
                );
                """;

        String createUsers = """
                CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT NOT NULL
                );
                """;

        String createUserBooks = """
                CREATE TABLE IF NOT EXISTS user_books (
                user_id INTEGER NOT NULL,
                book_id INTEGER NOT NULL,
                status TEXT NOT NULL,
                FOREIGN KEY (user_id) REFERENCES users(id),
                FOREIGN KEY (book_id) REFERENCES books(id)
                );
                """;

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(createBooks);
            stmt.execute(createUsers);
            stmt.execute(createUserBooks);
            System.out.println("All tables created or already exists!");
        } catch (SQLException e) {
            System.out.println("Table creation failed: " + e.getMessage());
        }
    }

    public static List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT id, title, author FROM books";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("List of all books: ");
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");

                Book book = new Book(id, title, author);
                books.add(book);
            }
        } catch (SQLException e) {
            System.out.println("Read failed: " + e.getMessage());
        }

        return books;
    }

    public static void insertBook(Book book) {
        String sql = "INSERT INTO books (title, author) VALUES (?,?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());

            pstmt.executeUpdate();
            System.out.println(book.getTitle() + " inserted into 'books'.");
        } catch (SQLException e) {
            System.out.println("Insert failed: " + e.getMessage());
        }
    }

    public static Book getBookById(int id) {
        String sql = "SELECT * FROM books WHERE id =?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String title = rs.getString("title");
                String author = rs.getString("author");
                return new Book(id, title, author);
            } else {
                System.out.println("No book was found with that ID");
            }
        } catch (SQLException e) {
            System.out.println("Error getting book: " + e.getMessage());
        }

        return null;
    }

    public static void updateBook(Book book) {
        String sql = """
                UPDATE books
                SET title = ?, author = ?
                WHERE id = ?;
                """;

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setInt(3, book.getId());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Book is updated!");
            } else {
                System.out.println("No book found with that ID: " + book.getId());
            }
        } catch (SQLException e) {
            System.out.println("Update failed" + e.getMessage());
        }
    }

    public static void deleteBook(int id) {
        String sql = "DELETE FROM books WHERE id = ?";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Book is deleted!");
            } else {
                System.out.println("No book found with that ID: " + id);
            }
        } catch (SQLException e) {
            System.out.println("Book ID not found");
        }
    }

    public static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, username FROM users";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                User user = new User(id, username);
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Failed to get all users" + e.getMessage());
        }
        return users;
    }

    public static void insertUser(User user) {
        String sql = "INSERT INTO users (username) VALUES (?)";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.executeUpdate();
            System.out.println(user.getUsername() + " user created!");
        } catch (SQLException e) {
            System.out.println("Failed to create user" + e.getMessage());
        }
    }

    public static User getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String username = rs.getString("username");
                return new User(id, username);
            } else {
                System.out.println("No user found with that ID!");
            }
        } catch (SQLException e) {
            System.out.println("Error getting the user: " + e.getMessage());
        }
        return null;
    }

    public static void updateUser(User user) {
        String sql = """
                UPDATE users
                SET username = ?
                WHERE id = ?;
                """;

        String oldUsername = user.getUsername();

        try (Connection conn = connect(); PreparedStatement pstmt =conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setInt(2, user.getId());

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("User: " + oldUsername + " is updated to: " + user.getUsername());
            } else {
                System.out.println("User with this id: " + user.getId() + " does not exist!");
            }
        } catch (SQLException e) {
            System.out.println("Failed to update: " + e.getMessage());
        }
    }

    public static void deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Book is deleted");
            } else {
                System.out.println("No user found with that ID: " + id);
            }
        } catch (SQLException e) {
            System.out.println("Failed to delete: " + e.getMessage());
        }
    }

    public static void insertUserBook(int user_id, int book_id, String status) {
        // Check if book and user exists before printing
        Book book = getBookById(book_id);
        User user = getUserById(user_id);

        if (book == null || user == null) {
            System.out.println("Error: User or Book not found.");
            return;
        }

        String sql = "INSERT INTO user_books (user_id, book_id, status) VALUES (?, ?, ?)";

        try (Connection conn = connect(); PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setInt(1, user_id);
            psmt.setInt(2, book_id);
            psmt.setString(3, status);

            psmt.executeUpdate();



            System.out.println("Successfully added book " + getBookById(book_id).getTitle()
                                + " in " + getUserById(user_id).getUsername() + " 's "
                                + status + " list!");
        } catch (SQLException e) {
            System.out.println("Failed to assign book: " + e.getMessage());
        }
    }

    public static List<Book> getBooksByUser(int user_id) {
        List<Book> books = new ArrayList<>();
        String sql = """
                SELECT books.id, books.title, books.author, user_books.status
                FROM books
                JOIN user_books ON books.id = user_books.book_id
                WHERE user_books.user_id = ?
                """;

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, user_id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String status = rs.getString("status");

                Book book = new Book(id, title, author);
                books.add(book);
                //System.out.println(title + " by " + author + " - Status: " + status);
            }
        }catch (SQLException e) {
                System.out.println("No books found for user ID: " + e.getMessage());
        }

        return books;
    }

    public static void updateUserBookStatus(int user_id, int book_id, String newStatus) {
        // Fetching the current status
        String oldStatus = "Unknowm";

        String current_sql = "SELECT status FROM user_books WHERE user_id = ? AND book_id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(current_sql)) {
            pstmt.setInt(1, user_id);
            pstmt.setInt(2, book_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                oldStatus = rs.getString("status");
            }
            System.out.println("Successfully fetched oldstatus: " + oldStatus );
        } catch (SQLException e) {
            System.out.println("Error fetching oldStatus: " + e.getMessage());
        }

        // Update the status
        String sql = "UPDATE user_books SET status = ? WHERE user_id = ? AND book_id = ?";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newStatus);
            pstmt.setInt(2, user_id);
            pstmt.setInt(3, book_id);
            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Status changed from '" + oldStatus + "' to '" + newStatus);
            } else {
                System.out.println("USer or book with ID don't exist");
            }
        } catch (SQLException e) {
            System.out.println("Error updating the status: " + e.getMessage());
        }
    }
}
