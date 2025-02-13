import java.util.*;

class InvalidValueException extends Exception {
    public InvalidValueException(String message) {
        super(message);
    }
}
abstract class Account {
    double amount;
    abstract double calculateInterest() throws InvalidValueException;
}
class SBAccount extends Account {
    String accountType; 
    SBAccount(double amount, String accountType) {
        this.amount = amount;
        this.accountType = accountType;
    }
    double calculateInterest() throws InvalidValueException {
        if (amount < 0) {
            throw new InvalidValueException("Invalid amount. Please enter a positive value.");
        }
        if (accountType.equalsIgnoreCase("Normal")) {
            return amount * 0.04;
        } else if (accountType.equalsIgnoreCase("NRI")) {
            return amount * 0.06;
        } else {
            throw new InvalidValueException("Invalid account type. Please enter 'Normal' or 'NRI'.");
        }
    }
}

class FDAccount extends Account {
    int days;    int age;

    FDAccount(double amount, int days, int age) {
        this.amount = amount;
        this.days = days;
        this.age = age;
    }

    double calculateInterest() throws InvalidValueException {
        if (amount < 0 || days < 0 || age < 0) {
            throw new InvalidValueException("Invalid input. Please enter positive values.");
        }
        double rate = 0;
        if (amount < 10000000) { // Below 1 crore
            if (age > 60) { // Senior Citizen
                if (days >= 7 && days <= 14) rate = 5.00;
                else if (days >= 15 && days <= 29) rate = 5.25;
                else if (days >= 30 && days <= 45) rate = 6.00;
                else if (days >= 46 && days <= 60) rate = 7.50;
                else if (days >= 61 && days <= 184) rate = 8.00;
                else if (days >= 185 && days <= 366) rate = 8.50;
            } else { // General
                if (days >= 7 && days <= 14) rate = 4.50;
                else if (days >= 15 && days <= 29) rate = 4.75;
                else if (days >= 30 && days <= 45) rate = 5.50;
                else if (days >= 46 && days <= 60) rate = 7.00;
                else if (days >= 61 && days <= 184) rate = 7.50;
                else if (days >= 185 && days <= 366) rate = 8.00;
            }
        } else { // Above 1 crore
            if (days >= 7 && days <= 14) rate = 6.50;
            else if (days >= 15 && days <= 29) rate = 6.75;
            else if (days >= 30 && days <= 45) rate = 6.75;
            else if (days >= 46 && days <= 60) rate = 8.00;
            else if (days >= 61 && days <= 184) rate = 8.50;
            else if (days >= 185 && days <= 366) rate = 10.00;
        }
        if (rate == 0) {
            throw new InvalidValueException("Invalid number of days. Please enter correct values.");
        }
        return amount * (rate / 100);
    }
}

class RDAccount extends Account {
    int months;
    double monthlyAmount;
    RDAccount(double monthlyAmount, int months, int age) {
        this.monthlyAmount = monthlyAmount;
        this.months = months;
        this.amount = monthlyAmount * months; // Total amount
    }

    double calculateInterest() throws InvalidValueException {
        if (monthlyAmount < 0 || months < 0) {
            throw new InvalidValueException("Invalid input. Please enter positive values.");
        }
        double rate = 0;
        if (months == 6) rate = 7.50;
        else if (months == 9) rate = 7.75;
        else if (months == 12) rate = 8.00;
        else if (months == 15) rate = 8.25;
        else if (months == 18) rate = 8.50;
        else if (months == 21) rate = 8.75;
        if (rate == 0) {
            throw new InvalidValueException("Invalid number of months. Please enter correct values.");
        }
        return amount * (rate / 100);
    }
}

public class Main {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("1. Interest Calculator - SB\n2. Interest Calculator - FD\n3. Interest Calculator - RD\n4. Exit");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Enter the Average amount in your account: ");
                    double amount = sc.nextDouble();
                    System.out.println("Enter the account type (Normal/NRI): ");
                    String accountType = sc.next();
                    try {
                        SBAccount sb = new SBAccount(amount, accountType);
                        System.out.println("Interest gained: Rs. " + sb.calculateInterest());
                    } catch (InvalidValueException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("Enter the FD amount: ");
                    amount = sc.nextDouble();
                    System.out.println("Enter the number of days: ");
                    int days = sc.nextInt();
                    System.out.println("Enter your age: ");
                    int age = sc.nextInt();
                    try {
                        FDAccount fd = new FDAccount(amount, days, age);
                        System.out.println("Interest gained: Rs. " + fd.calculateInterest());
                    } catch (InvalidValueException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("Enter the monthly RD amount: ");
                    double monthlyAmount = sc.nextDouble();
                    System.out.println("Enter the number of months: ");
                    int months = sc.nextInt();
                    System.out.println("Enter your age: ");
                    age = sc.nextInt();
                    try {
                        RDAccount rd = new RDAccount(monthlyAmount, months, age);
                        System.out.println("Interest gained: Rs. " + rd.calculateInterest());
                    } catch (InvalidValueException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 4:
                    System.out.println("Exiting...");
                    sc.close();
                    System.out.println("22BCS14491");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
