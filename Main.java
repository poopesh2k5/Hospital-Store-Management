import java.util.*;

class Medicine {                                  
    int id;
    String name;
    int quantity;
    double price;

    public Medicine(int id, String name, int quantity, double price) {                     
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
}
public class Main {
    private static ArrayList<Medicine> inventory = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        inventory.add(new Medicine(1, "Paracetamol", 50, 2.50));
        inventory.add(new Medicine(2, "Cetirizine", 30, 12.00));
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("\n=== MEDICAL STORE MANAGEMENT ===");
            System.out.println("1. View Inventory");
            System.out.println("2. Add Stock");
            System.out.println("3. Update Stock/Price");
            System.out.println("4. Delete Medicine");
            System.out.println("5. Process Bill");
            System.out.println("6. Exit");
            System.out.print("Select an option: ");
            
            switch (scanner.next()) {
                case "1": viewInventory(); break;
                case "2": addStock(); break;
                case "3": updateStock(); break;
                case "4": deleteMedicine(); break;
                case "5": processBill(); break;
                case "6": isRunning = false; break;
                default: System.out.println("Invalid option.");
            }
        }
    }
    private static void viewInventory() {
        System.out.println("\nID\tName\t\tStock\tPrice");
        System.out.println("--------------------------------------------");1

        for (Medicine med : inventory) {
            System.out.printf("%d\t%-15s\t%d\tRS %.2f\n", med.id, med.name, med.quantity, med.price);
        }
    }
    private static void addStock() {
        System.out.print("Enter ID: "); 
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Name: "); 
        String name = scanner.nextLine();
        System.out.print("Enter Quantity to Add: "); 
        int qty = scanner.nextInt();
        System.out.print("Enter Price: "); 
        double price = scanner.nextDouble();
        boolean found = false;
        for (Medicine med : inventory) {
            if (med.name.equalsIgnoreCase(name)) {
                med.quantity += qty;
                med.price = price;
                System.out.println("Medicine '" + name + "' found. Stock and price updated.");
                found = true;
                break;
            }
        }
        if (!found) {
            inventory.add(new Medicine(id, name, qty, price));
            System.out.println("New medicine added to inventory.");
        }
    }
    private static void updateStock() {
        System.out.print("Enter ID to update: ");
        int id = scanner.nextInt();
        
        Medicine selectedMed = null;
        for (Medicine med : inventory) {
            if (med.id == id) {
                selectedMed = med;
                break;
            }
        }
        if (selectedMed == null) {
            System.out.println("Medicine not found.");
            return;
        }
        System.out.println("\n1. Replace Quantity and Price");
        System.out.println("2. Add to existing Quantity");
        System.out.println("3. Update Price only");
        System.out.print("Select update mode: ");
        String mode = scanner.next();
        switch (mode) {
            case "1" -> {
                System.out.print("Enter New Quantity: ");
                selectedMed.quantity = scanner.nextInt();
                System.out.print("Enter New Price: ");
                selectedMed.price = scanner.nextDouble();
                System.out.println("Values replaced successfully.");
            }
            case "2" -> {
                System.out.print("Enter quantity to ADD: ");
                int addQty = scanner.nextInt();
                selectedMed.quantity += addQty;
                System.out.println("Quantity added. New total: " + selectedMed.quantity);
            }
            case "3" -> {
                System.out.print("Enter New Price: ");
                selectedMed.price = scanner.nextDouble();
                System.out.println("Price updated successfully.");
            }
            default -> System.out.println("Invalid option.");
        }
    }

    private static void deleteMedicine() {
        System.out.print("Enter ID to delete: ");
        int id = scanner.nextInt();
        boolean removed = inventory.removeIf(med -> med.id == id);
        System.out.println(removed ? "Deleted successfully." : "Medicine not found.");
    }

    private static void processBill() {
        System.out.print("Enter Medicine ID to purchase: ");
        int searchId = scanner.nextInt();
        Medicine selectedMed = null;

        for (Medicine med : inventory) {
            if (med.id == searchId) {
                selectedMed = med;
                break;
            }
        }
        if (selectedMed == null) {
            System.out.println("Error: Medicine ID not found.");
            return;
        }
        System.out.print("Enter quantity needed: ");
        int orderQty = scanner.nextInt();

        if (orderQty > selectedMed.quantity) {
            System.out.println("Purchase Rejected: Insufficient stock.");
        } else {
            selectedMed.quantity -= orderQty;
            System.out.printf("Total Due: RS %.2f\n", orderQty * selectedMed.price);
            System.out.println("Purchase complete.");
        }
    }
}