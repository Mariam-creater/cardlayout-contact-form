
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ContactManager {
    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private DefaultListModel<String> listModel;
    private JList<String> contactList;
    private JLabel detailsLabel;
    private JTextField nameField, phoneField, emailField;
    private ArrayList<Contact> contacts;

    public ContactManager() {
        frame = new JFrame("Contact Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        contacts = new ArrayList<>();

        createContactListView();
        createContactDetailsView();
        createContactFormView();

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void createContactListView() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.LIGHT_GRAY);

        listModel = new DefaultListModel<>();
        contactList = new JList<>(listModel);

        JButton addButton = new JButton("Add New Contact");
        JButton viewButton = new JButton("View Details");

        addButton.addActionListener(e -> cardLayout.show(mainPanel, "Form"));
        viewButton.addActionListener(e -> showContactDetails());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);

        panel.add(new JScrollPane(contactList), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(panel, "List");
    }

    private void createContactDetailsView() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.CYAN);

        detailsLabel = new JLabel("Select a contact to view details.");

        JButton backButton = new JButton("Back to List");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "List"));

        panel.add(detailsLabel, BorderLayout.CENTER);
        panel.add(backButton, BorderLayout.SOUTH);

        mainPanel.add(panel, "Details");
    }

    private void createContactFormView() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.setBackground(Color.PINK);

        nameField = new JTextField();
        phoneField = new JTextField();
        emailField = new JTextField();

        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);
        panel.add(new JLabel("Email:"));
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
            contacts.add(new Contact(name, phone, email));
            listModel.addElement(name);
            cardLayout.show(mainPanel, "List");
            nameField.setText("");
            phoneField.setText("");
            emailField.setText("");
        } else {
            JOptionPane.showMessageDialog(frame, "All fields must be filled.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showContactDetails() {
        int selectedIndex = contactList.getSelectedIndex();
        if (selectedIndex != -1) {
            Contact contact = contacts.get(selectedIndex);
            detailsLabel.setText("Name: " + contact.getName() + " | Phone: " + contact.getPhone() + " | Email: " + contact.getEmail());
            cardLayout.show(mainPanel, "Details");
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a contact to view details.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ContactManager::new);
    }
}

class Contact {
    private String name;
    private String phone;
    private String email;

    public Contact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
}
