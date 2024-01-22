import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Contact implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String phoneNumber;
    private String emailAddress;

    public Contact(String name, String phoneNumber, String emailAddress) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Phone: " + phoneNumber + ", Email: " + emailAddress;
    }
}

public class ContactManager {
    private static Map<String, Contact> contacts = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadContactsFromFile(); // Load contacts from file on program start

        int choice;

        do {
            System.out.println("\n1. Add a new contact");
            System.out.println("2. View contact list");
            System.out.println("3. Edit a contact");
            System.out.println("4. Delete a contact");
            System.out.println("5. Save contacts to file");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    addContact();
                    break;
                case 2:
                    viewContactList();
                    break;
                case 3:
                    editContact();
                    break;
                case 4:
                    deleteContact();
                    break;
                case 5:
                    saveContactsToFile();
                    break;
                case 6:
                    System.out.println("Exiting the program.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 6);
    }

    private static void loadContactsFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("contacts.ser"))) {
            Object obj = ois.readObject();
            if (obj instanceof HashMap<?, ?>) {
                contacts = (HashMap<String, Contact>) obj;
            } else {
                System.out.println("Error: Unexpected data format in contacts file.");
            }
        } catch (FileNotFoundException e) {
            System.out.println("No previous contacts found. Starting with an empty contact list.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void saveContactsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("contacts.ser"))) {
            oos.writeObject(contacts);
            System.out.println("Contacts saved to file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addContact() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        if (contacts.containsKey(name)) {
            System.out.println("Contact with that name already exists. Please use a different name.");
            return;
        }

        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter email address: ");
        String emailAddress = scanner.nextLine();

        Contact newContact = new Contact(name, phoneNumber, emailAddress);
        contacts.put(name, newContact);

        System.out.println("Contact added successfully!");
    }

    private static void viewContactList() {
        if (contacts.isEmpty()) {
            System.out.println("Contact list is empty.");
        } else {
            System.out.println("Contact List:");
            for (Contact contact : contacts.values()) {
                System.out.println(contact);
            }
        }
    }

    private static void editContact() {
        System.out.print("Enter the name of the contact to edit: ");
        String name = scanner.nextLine();

        if (contacts.containsKey(name)) {
            System.out.print("Enter new phone number: ");
            String newPhoneNumber = scanner.nextLine();
            System.out.print("Enter new email address: ");
            String newEmailAddress = scanner.nextLine();

            Contact editedContact = contacts.get(name);
            editedContact.setPhoneNumber(newPhoneNumber);
            editedContact.setEmailAddress(newEmailAddress);

            System.out.println("Contact edited successfully!");
        } else {
            System.out.println("Contact not found.");
        }
    }

    private static void deleteContact() {
        System.out.print("Enter the name of the contact to delete: ");
        String name = scanner.nextLine();

        if (contacts.containsKey(name)) {
            contacts.remove(name);
            System.out.println("Contact deleted successfully!");
        } else {
            System.out.println("Contact not found.");
        }
    }
}
