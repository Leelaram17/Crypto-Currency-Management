import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class CryptocurrencyManagementGUI {
    private static JComboBox<String> cryptoNameComboBox;
    private static JLabel currentPriceLabel;

    // Set the initial current price to $50.00
    private static double currentPrice = 50.00;

    private static ArrayList<Cryptocurrency> cryptocurrencies = new ArrayList<>();

    public static void main(String[] args) {
        cryptocurrencies.add(new Cryptocurrency("Bitcoin", 0, 0.00));
        cryptocurrencies.add(new Cryptocurrency("Ethereum", 0, 0.00));
        cryptocurrencies.add(new Cryptocurrency("Litecoin", 0, 0.00));
        cryptocurrencies.add(new Cryptocurrency("Ripple", 0, 0.00));
        cryptocurrencies.add(new Cryptocurrency("Cardano", 0, 0.00));
        cryptocurrencies.add(new Cryptocurrency("Polkadot", 0, 0.00));
        cryptocurrencies.add(new Cryptocurrency("Chainlink", 0, 0.00));
        cryptocurrencies.add(new Cryptocurrency("Stellar", 0, 0.00));

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Cryptocurrency Management");
            frame.setSize(400, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel panel = new JPanel();
            frame.add(panel);
            placeComponents(panel);

            frame.setVisible(true);
        });

        // Start a timer to update the price label every minute
        Timer timer = new Timer(60000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCurrentPrice();
            }
        });
        timer.start();
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel label = new JLabel("Cryptocurrency Management System");
        label.setBounds(50, 20, 300, 25);
        panel.add(label);

        JLabel cryptoNameLabel = new JLabel("Cryptocurrency Name:");
        cryptoNameLabel.setBounds(50, 60, 150, 25);
        panel.add(cryptoNameLabel);

        String[] cryptoNames = {"Bitcoin", "Ethereum", "Litecoin", "Ripple", "Cardano", "Polkadot", "Chainlink", "Stellar"};
        cryptoNameComboBox = new JComboBox<>(cryptoNames);
        cryptoNameComboBox.setBounds(200, 60, 150, 25);
        panel.add(cryptoNameComboBox);

        currentPriceLabel = new JLabel("Current Price: $" + String.format("%.2f", currentPrice));
        currentPriceLabel.setBounds(50, 90, 300, 25);
        panel.add(currentPriceLabel);

        JButton buyButton = new JButton("Buy Cryptocurrency");
        buyButton.setBounds(50, 120, 150, 25);
        panel.add(buyButton);

        JButton sellButton = new JButton("Sell Cryptocurrency");
        sellButton.setBounds(50, 160, 200, 25);
        panel.add(sellButton);

        JButton viewButton = new JButton("View Cryptocurrencies");
        viewButton.setBounds(50, 200, 200, 25);
        panel.add(viewButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(50, 240, 200, 25);
        panel.add(exitButton);

        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cryptoName = (String) cryptoNameComboBox.getSelectedItem();

                // Check for invalid input when parsing double values
                try {
                    double buyAmount = Double.parseDouble(JOptionPane.showInputDialog("Enter the amount to buy:"));

                    boolean cryptoFound = false;
                    for (Cryptocurrency crypto : cryptocurrencies) {
                        if (crypto.getName().equalsIgnoreCase(cryptoName.trim())) {
                            cryptoFound = true;
                            crypto.buyQuantity(buyAmount, currentPrice);
                            JOptionPane.showMessageDialog(null, "Cryptocurrency bought successfully");
                            break;
                        }
                    }
                    if (!cryptoFound) {
                        JOptionPane.showMessageDialog(null, "Cryptocurrency not found");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid amount entered");
                }
            }
        });

        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cryptoToSell = (String) cryptoNameComboBox.getSelectedItem();
                double sellAmount = Double.parseDouble(JOptionPane.showInputDialog("Enter the amount to sell:"));

                boolean cryptoFound = false;
                for (Cryptocurrency crypto : cryptocurrencies) {
                    if (crypto.getName().equals(cryptoToSell)) {
                        cryptoFound = true;
                        crypto.sellAmount(sellAmount, currentPrice);
                        JOptionPane.showMessageDialog(null, "Cryptocurrency sold successfully");
                        break;
                    }
                }
                if (!cryptoFound) {
                    JOptionPane.showMessageDialog(null, "Cryptocurrency not found");
                }
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder output = new StringBuilder("Current cryptocurrencies:\n");
                for (Cryptocurrency crypto : cryptocurrencies) {
                    output.append(crypto.toString()).append("\n");
                }
                JOptionPane.showMessageDialog(null, output.toString());
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private static void updateCurrentPrice() {
        // Simulate price fluctuation by adding a random value between -5 and 5
        Random random = new Random();
        double priceChange = random.nextDouble() * 10 - 5;

        // Update the current price label accordingly
        currentPrice += priceChange;

        // Ensure the price remains positive
        if (currentPrice < 0) {
            currentPrice = 0;
        }

        currentPriceLabel.setText("Current Price: $" + String.format("%.2f", currentPrice));
    }
}

class Cryptocurrency {
    private String cryptoName;
    private double quantity;
    private double price;

    public Cryptocurrency(String cryptoName, double quantity, double price) {
        this.cryptoName = cryptoName;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return cryptoName;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public void buyQuantity(double amount, double currentPrice) {
        if (amount > 0) {
            double quantityToBuy = amount / currentPrice;
            quantity += quantityToBuy;
            // Update the individual cryptocurrency price
            price = currentPrice;
            JOptionPane.showMessageDialog(null, "Cryptocurrency bought successfully");
        } else {
            JOptionPane.showMessageDialog(null, "Invalid amount to buy");
        }
    }

    public void sellAmount(double amount, double currentPrice) {
        if (amount <= quantity * currentPrice && amount > 0) {
            double quantityToSell = amount / currentPrice;
            quantity -= quantityToSell;
            // Update the individual cryptocurrency price
            price = currentPrice;
        } else {
            JOptionPane.showMessageDialog(null, "Invalid amount to sell");
        }
    }

    public double getTotalValue() {
        return quantity * price;
    }

    public String toString() {
        return cryptoName + ", quantity: " + String.format("%.4f", quantity) + ", total value: $" + String.format("%.2f", getTotalValue());
    }
}

