import java.util.ArrayList;
import java.util.Scanner;

class Book {
    private String title;
    private String author;
    private boolean isAvailable;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.isAvailable = true;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void checkOut() {
        if (isAvailable) {
            isAvailable = false;
            System.out.println("You have checked out: " + title);
        } else {
            System.out.println("Sorry, this book is already checked out.");
        }
    }

    public void returnBook() {
        isAvailable = true;
        System.out.println("You have returned: " + title);
    }

    public void displayBook() {
        System.out.println("Title: " + title + ", Author: " + author + ", Available: " + (isAvailable ? "Yes" : "No"));
    }
}

class User {
    private String username;
    private ArrayList<Book> borrowedBooks;

    public User(String username) {
        this.username = username;
        borrowedBooks = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void borrowBook(Book book) {
        if (book.isAvailable()) {
            borrowedBooks.add(book);
            book.checkOut();
        } else {
            System.out.println("Sorry, the book is currently unavailable.");
        }
    }

    public void returnBook(Book book) {
        if (borrowedBooks.contains(book)) {
            borrowedBooks.remove(book);
            book.returnBook();
        } else {
            System.out.println("You haven't borrowed this book.");
        }
    }
}

class Library {
    private ArrayList<Book> books;
    private ArrayList<User> users;

    public Library() {
        books = new ArrayList<>();
        users = new ArrayList<>();
    }

    public void addBook(String title, String author) {
        books.add(new Book(title, author));
        System.out.println("Book added: " + title);
    }

    public void displayBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available in the library.");
        } else {
            System.out.println("\nAvailable Books:");
            for (Book book : books) {
                book.displayBook();
            }
        }
    }

    public Book searchBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }

    public void addUser(String username) {
        users.add(new User(username));
        System.out.println("User added: " + username);
    }

    public User getUser(String username) {
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }
}

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();
        int choice;
        User currentUser = null;

        while (true) {
            System.out.println("\nLibrary Management System");
            if (currentUser != null) {
                System.out.println("Logged in as: " + currentUser.getUsername());
            }
            System.out.println("1. Add Book");
            System.out.println("2. Display Books");
            System.out.println("3. Search Book");
            System.out.println("4. Borrow Book");
            System.out.println("5. Return Book");
            System.out.println("6. Register User");
            System.out.println("7. Login");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();  // consume newline character

            switch (choice) {
                case 1:
                    if (currentUser == null) {
                        System.out.println("You must log in to add books.");
                        break;
                    }
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter book author: ");
                    String author = scanner.nextLine();
                    library.addBook(title, author);
                    break;

                case 2:
                    library.displayBooks();
                    break;

                case 3:
                    System.out.print("Enter book title to search: ");
                    String searchTitle = scanner.nextLine();
                    Book book = library.searchBook(searchTitle);
                    if (book != null) {
                        book.displayBook();
                    } else {
                        System.out.println("Book not found.");
                    }
                    break;

                case 4:
                    if (currentUser == null) {
                        System.out.println("You must log in to borrow books.");
                        break;
                    }
                    System.out.print("Enter book title to borrow: ");
                    String borrowTitle = scanner.nextLine();
                    Book borrowBook = library.searchBook(borrowTitle);
                    if (borrowBook != null) {
                        currentUser.borrowBook(borrowBook);
                    } else {
                        System.out.println("Book not found.");
                    }
                    break;

                case 5:
                    if (currentUser == null) {
                        System.out.println("You must log in to return books.");
                        break;
                    }
                    System.out.print("Enter book title to return: ");
                    String returnTitle = scanner.nextLine();
                    Book returnBook = library.searchBook(returnTitle);
                    if (returnBook != null) {
                        currentUser.returnBook(returnBook);
                    } else {
                        System.out.println("Book not found.");
                    }
                    break;

                case 6:
                    System.out.print("Enter username to register: ");
                    String username = scanner.nextLine();
                    library.addUser(username);
                    break;

                case 7:
                    System.out.print("Enter username to log in: ");
                    String loginUsername = scanner.nextLine();
                    currentUser = library.getUser(loginUsername);
                    if (currentUser != null) {
                        System.out.println("Logged in as: " + currentUser.getUsername());
                    } else {
                        System.out.println("User not found.");
                    }
                    break;

                case 8:
                    System.out.println("Exiting system...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
