import java.time.LocalDate;
import java.util.*;

public class MenuDrivenApp {
    private static ArrayList<Customer> customers = new ArrayList<>();
    private static HashMap<Integer, Product> products = new HashMap<>();
    private static HashSet<Product> uniqueProducts = new HashSet<>();
    private static TreeSet<Product> sortedProducts;
    private static TreeSet<Customer> sortedCustomers;
    private static ArrayList<Order> orders = new ArrayList<>();

    public static void main(String[] args) {
        // Comparator for sorting products by price
        Comparator<Product> productPriceComparator = Comparator.comparingDouble(Product::getPrice);
        sortedProducts = new TreeSet<>(productPriceComparator);

        // Comparator for sorting customers by loyalty points
        Comparator<Customer> customerPointsComparator = Comparator.comparingInt(Customer::getLoyaltyPoints).reversed();
        sortedCustomers = new TreeSet<>(customerPointsComparator);

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nMenu:");
            System.out.println("1. Add Customer");
            System.out.println("2. Add Product");
            System.out.println("3. Place Order");
            System.out.println("4. Display Sorted Products (by Price)");
            System.out.println("5. Display Sorted Customers (by Loyalty Points)");
            System.out.println("6. Display Orders");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addCustomer(scanner);
                    break;
                case 2:
                    addProduct(scanner);
                    break;
                case 3:
                    placeOrder(scanner);
                    break;
                case 4:
                    displaySortedProducts();
                    break;
                case 5:
                    displaySortedCustomers();
                    break;
                case 6:
                    displayOrders();
                    break;
                case 7:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private static void addCustomer(Scanner scanner) {
        System.out.print("Enter Customer ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter Customer Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Loyalty Points: ");
        int points = scanner.nextInt();

        Customer customer = new Customer(id, name, points);
        customers.add(customer);
        sortedCustomers.add(customer);

        System.out.println("Customer added successfully.");
    }

    private static void addProduct(Scanner scanner) {
        System.out.print("Enter Product ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter Product Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Product Price: ");
        double price = scanner.nextDouble();

        Product product = new Product(id, name, price);
        products.put(id, product);
        uniqueProducts.add(product);
        sortedProducts.add(product);

        System.out.println("Product added successfully.");
    }

    private static void placeOrder(Scanner scanner) {
        System.out.print("Enter Customer ID: ");
        int customerId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        Customer customer = customers.stream()
                .filter(c -> c.getCustomerId() == customerId)
                .findFirst()
                .orElse(null);

        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        System.out.print("Enter Product ID: ");
        int productId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Product product = products.get(productId);

        if (product == null) {
            System.out.println("Product not found.");
            return;
        }

        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();

        Order order = new Order(customer, product, quantity, LocalDate.now());
        orders.add(order);

        System.out.println("Order placed successfully.");
    }

    private static void displaySortedProducts() {
        System.out.println("\nSorted Products (by Price):");
        if (sortedProducts.isEmpty()) {
            System.out.println("No products available.");
        } else {
            sortedProducts.forEach(System.out::println);
        }
    }

    private static void displaySortedCustomers() {
        System.out.println("\nSorted Customers (by Loyalty Points):");
        if (sortedCustomers.isEmpty()) {
            System.out.println("No customers available.");
        } else {
            sortedCustomers.forEach(System.out::println);
        }
    }

    private static void displayOrders() {
        System.out.println("\nOrders:");
        if (orders.isEmpty()) {
            System.out.println("No orders available.");
        } else {
            orders.forEach(System.out::println);
        }
    }
}

class Customer {
    private int customerId;
    private String name;
    private int loyaltyPoints;

    public Customer(int customerId, String name, int loyaltyPoints) {
        this.customerId = customerId;
        this.name = name;
        this.loyaltyPoints = loyaltyPoints;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void updateName(String newName) {
        this.name = newName;
    }

    public void updateLoyaltyPoints(int points) {
        this.loyaltyPoints += points;
    }

    @Override
    public String toString() {
        return "Customer ID: " + customerId + ", Name: " + name + ", Loyalty Points: " + loyaltyPoints;
    }
}

class Product {
    private int productId;
    private String name;
    private double price;

    public Product(int productId, String name, double price) {
        this.productId = productId;
        this.name = name;
        this.price = price;
    }

    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void updatePrice(double newPrice) {
        this.price = newPrice;
    }

    @Override
    public String toString() {
        return "Product ID: " + productId + ", Name: " + name + ", Price: " + price;
    }
}

class Order {
    private Customer customer;
    private Product product;
    private int quantity;
    private LocalDate orderDate;

    public Order(Customer customer, Product product, int quantity, LocalDate orderDate) {
        this.customer = customer;
        this.product = product;
        this.quantity = quantity;
        this.orderDate = orderDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    @Override
    public String toString() {
        return "Order [Customer=" + customer.getName() + ", Product=" + product.getName() + ", Quantity=" + quantity + ", Order Date=" + orderDate + "]";
    }
}

