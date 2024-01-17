import java.util.ArrayList;
import java.util.Scanner;

public class CryptocurrencyManagement {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        ArrayList<Cryptocurrency> cryptocurrencies = new ArrayList<>();

        int choice;
        do {
            System.out.println("1. Add cryptocurrency");
            System.out.println("2. Remove cryptocurrency");
            System.out.println("3. View cryptocurrencies");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = input.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter the cryptocurrency name: ");
                    String cryptoName = input.next();
                    System.out.print("Enter the cryptocurrency quantity: ");
                    double quantity = input.nextDouble();
                    System.out.print("Enter the cryptocurrency price: ");
                    double price = input.nextDouble();

                    Cryptocurrency newCrypto = new Cryptocurrency(cryptoName, quantity, price);
                    cryptocurrencies.add(newCrypto);

                    System.out.println("Cryptocurrency added successfully");
                    break;

                case 2:
                    System.out.print("Enter the cryptocurrency name: ");
                    String cryptoToRemove = input.next();
                    System.out.print("Enter the quantity to remove: ");
                    double removeQuantity = input.nextDouble();

                    boolean cryptoFound = false;
                    for (Cryptocurrency crypto : cryptocurrencies) {
                        if (crypto.getName().equals(cryptoToRemove)) {
                            cryptoFound = true;
                            crypto.removeQuantity(removeQuantity);
                            System.out.println("Cryptocurrency removed successfully");
                            break;
                        }
                    }
                    if (!cryptoFound) {
                        System.out.println("Cryptocurrency not found");
                    }
                    break;

                case 3:
                    System.out.println("Current cryptocurrencies:");
                    for (Cryptocurrency crypto : cryptocurrencies) {
                        System.out.println(crypto.toString());
                    }
                    break;

                case 4:
                    System.out.println("Exiting the program...");
                    break;

                default:
                    System.out.println("Invalid choice, try again.");
                    break;
            }

        } while (choice != 4);

        input.close();
    }
}

class Cryptocurrency {
    private String name;
    private double quantity;
    private double price;

    public Cryptocurrency(String name, double quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public void removeQuantity(double quantityToRemove) {
        if (quantityToRemove <= quantity) {
            quantity -= quantityToRemove;
        } else {
            System.out.println("Not enough quantity to remove");
        }
    }

    public String toString() {
        return name + ", quantity: " + quantity + ", price: $" + price;
    }
}
