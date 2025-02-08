import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ContactManager {
    private final JFrame window;
    private final JPanel mainPanel;
    private final CardLayout cardLayout;
    private DefaultListModel<String> listModel;
    private JList<String> contactList;
    private JTextField nameField, phoneField, emailField;
    private JLabel detailsLabel;
    private final ArrayList<Contact> contacts;

    public ContactManager() {
        window = new JFrame("Contact Manager");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        contacts = new ArrayList<>();

        createContactList();
        createContactDetails();
        createContactForm();

        window.add(mainPanel);
        window.setVisible(true);
    }
    private void createContactList() {
        JPanel panel = new JPanel(new BorderLayout());
        listModel = new DefaultListModel<>();
        contactList = new JList<>(listModel);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Add New Contact");
        JButton viewButton = new JButton("View Details");
        JButton deleteButton = new JButton("Delete Contact");
        addButton.addActionListener(e -> cardLayout.show(mainPanel, "Form"));
        viewButton.addActionListener(e -> showDetails());
        deleteButton.addActionListener(e -> deleteContact());
        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(deleteButton);
        panel.add(new JScrollPane(contactList), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.add(panel, "List");
    }
    private void createContactDetails() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(169, 223, 191));
        detailsLabel = new JLabel("Select a contact to view details.");
        JButton backButton = new JButton("Back to List");
        JButton editButton = new JButton("Edit Contact");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "List"));
        editButton.addActionListener(e -> editContact());
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(editButton);
        buttonPanel.add(backButton);
        panel.add(detailsLabel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(panel, "Details");
    }
    private void createContactForm() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.setBackground(new Color(169, 223, 191));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);
        panel.add(new JLabel("Phone:"));
        phoneField = new JTextField();
        panel.add(phoneField);
        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);

        JButton saveButton = new JButton("Save Contact");
        JButton cancelButton = new JButton("Cancel");
        saveButton.addActionListener(e -> saveContact());
        cancelButton.addActionListener(e -> cardLayout.show(mainPanel, "List"));
        panel.add(saveButton);
        panel.add(cancelButton);
        mainPanel.add(panel, "Form");
    }
    private void saveContact() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();

        if (!name.isEmpty() && !phone.isEmpty() && !email.isEmpty()) {
            Contact contact = new Contact(name, phone, email);
            contacts.add(contact);
            listModel.addElement(name);
            cardLayout.show(mainPanel, "List");
            nameField.setText("");
            phoneField.setText("");
            emailField.setText("");
        } else {
            JOptionPane.showMessageDialog(window, "please fill all the details .", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void showDetails() {
        int selection = contactList.getSelectedIndex();
        if (selection != -1) {
            Contact contact = contacts.get(selection);
            detailsLabel.setText("<html>Name: " + contact.name + "<br>Phone: " + contact.phone + "<br>Email: " + contact.email + "</html>");
            cardLayout.show(mainPanel, "Details");
        } else {
            JOptionPane.showMessageDialog(window, "Please select a contact.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

 void editContact() {
        int selection = contactList.getSelectedIndex();
        if (selection != -1) {
            Contact contact = contacts.get(selection);
            nameField.setText(contact.name);
            phoneField.setText(contact.phone);
            emailField.setText(contact.email);
            cardLayout.show(mainPanel, "Form");
            contacts.remove(selection);
            listModel.remove(selection);
        }
    }

    private void deleteContact() {
        int selection = contactList.getSelectedIndex();
        if (selection != -1) {
            contacts.remove(selection);
            listModel.remove(selection);
        } else {
            JOptionPane.showMessageDialog(window, "Please select a contact to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    static class Contact {
        String name, phone, email;
        Contact(String name, String phone, String email) {
            this.name = name;
            this.phone = phone;
            this.email = email;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ContactManager::new);
    }
}
