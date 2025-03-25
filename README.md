# Book on the Slelf

Book on the Shelf is a simple Java-based console application for managing personal book collections.
Users can add books, track reading status, and view their library.

##  Features
- Add and manage users 
- Store books with authors 
- Track reading status (wishlist, reading, finished) 
- View books by user 
- Update book status 
- Simple command-line interface

## Tech Stack
- **Java 17** ☕
- **SQLite** (Embedded Database) 
- **JUnit 5** (Unit Testing) 
- **IntelliJ IDEA** (IDE)

## 🔧 Installation

1. **Clone the repository:**
   git clone https://github.com/yourusername/book-on-the-shelf.git
   cd book-on-the-shelf

2. **Ensure you have Java 17 installed:**
   java -version
   
4. **Run the program:**
   - javac Main.java
   - java Main
   

### **Database Structure**
Show the **tables** and relationships.  
```md
##  Database Structure
The app uses **SQLite** with three tables:

### `books`
| id | title        | author |
|----|-------------|--------|
| 1  | The Hobbit  | Tolkien |
| 2  | 1984        | Orwell |

### `users`
| id | username |
|----|----------|
| 1  | Alice    |
| 2  | Bob      |

### `user_books`
| user_id | book_id | status  |
|---------|---------|---------|
| 1       | 1       | reading |
| 2       | 2       | wishlist |

## How to Use
Run the program and follow the menu prompts:

1 Create a new user
2 Add a new book
3 Assign a book to a user
4 View books by user
5 Update book status
6 View all users
7 View all books
8 Exit

