import java.util.HashMap;
import java.util.Map;
import java.util.Random;

class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

class Account {
    private String accountNumber;
    private double balance;
    private User owner;

    public Account(User owner) {
        this.accountNumber = generateAccountNumber();
        this.balance = 0.0;
        this.owner = owner;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public User getOwner() {
        return owner;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            return true;
        } else {
            System.out.println("Insufficient funds");
            return false;
        }
    }

    private String generateAccountNumber() {
        Random rand = new Random();
        return String.format("%09d", rand.nextInt(1_000_000_000));
    }
}

class BankingSystem {
    private Map<String, User> users;
    private Map<String, Account> accounts;

    public BankingSystem() {
        this.users = new HashMap<>();
        this.accounts = new HashMap<>();
    }

    public void registerUser(String username, String password) {
        if (!users.containsKey(username)) {
            User newUser = new User(username, password);
            users.put(username, newUser);
            System.out.println("User registration successful.");
        } else {
            System.out.println("Username already exists. Please choose another username.");
        }
    }

    public User login(String username, String password) {
        if (users.containsKey(username)) {
            User user = users.get(username);
            if (user.getPassword().equals(password)) {
                System.out.println("Login successful.");
                return user;
            }
        }
        System.out.println("Login failed. Invalid credentials.");
        return null;
    }

    public void createAccount(User user) {
        Account newAccount = new Account(user);
        accounts.put(newAccount.getAccountNumber(), newAccount);
        System.out.println("Account created successfully. Account number: " + newAccount.getAccountNumber());
    }

    public void deposit(String accountNumber, double amount) {
        if (accounts.containsKey(accountNumber)) {
            Account account = accounts.get(accountNumber);
            account.deposit(amount);
            System.out.println("Deposit successful. New balance: $" + account.getBalance());
        } else {
            System.out.println("Invalid account number.");
        }
    }

    public void withdraw(String accountNumber, double amount) {
        if (accounts.containsKey(accountNumber)) {
            Account account = accounts.get(accountNumber);
            if (account.withdraw(amount)) {
                System.out.println("Withdrawal successful. New balance: $" + account.getBalance());
            }
        } else {
            System.out.println("Invalid account number.");
        }
    }

    public double getAccountBalance(String accountNumber) {
        if (accounts.containsKey(accountNumber)) {
            return accounts.get(accountNumber).getBalance();
        } else {
            System.out.println("Invalid account number.");
            return -1;
        }
    }

    public static void main(String[] args) {
        BankingSystem bankingSystem = new BankingSystem();

        // Register users
        bankingSystem.registerUser("user1", "password1");
        bankingSystem.registerUser("user2", "password2");

        // Create accounts
        User user1 = bankingSystem.login("user1", "password1");
        bankingSystem.createAccount(user1);

        User user2 = bankingSystem.login("user2", "password2");
        bankingSystem.createAccount(user2);

        // Deposit and withdraw
        bankingSystem.deposit("000000001", 100.0);
        bankingSystem.withdraw("000000002", 50.0);

        // Check account balance
        double balance = bankingSystem.getAccountBalance("000000001");
        System.out.println("Account balance: $" + balance);
    }
}
