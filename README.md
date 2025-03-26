# Book on the Shelf

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
- **Java 21** ☕
- **SQLite** (Embedded Database)
- **Maven** (Build & Dependency Management)
- **IntelliJ IDEA** (IDE)

## 🔧 Installation

1. **Clone the repository:**
   - git clone https://github.com/Irida00/book_on_the_shelf
   - cd book_on_the_shelf/book-on-the-shelf

2. **Ensure you have Maven installed:**
   mvn -version

3. **SQLite JDBC Driver** (Already included in pom.xml, no need to install manually)   
   
4. **Run the program:**
   - mvn clean install
   - mvn exec:java
   

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

