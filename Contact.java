import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class Contact {
    String name, phone, email;

    public Contact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }
}

class ContactManager {
    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private DefaultListModel<String> contactListModel;
    private JList<String> contactList;
    private ArrayList<Contact> contacts;
    private JTextField nameField, phoneField, emailField;
    private JLabel nameLabel, phoneLabel, emailLabel;

    public ContactManager() {
        contacts = new ArrayList<>();
        frame = new JFrame("Contact Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        setupContactListView();
        setupContactDetailsView();
        setupContactCreationForm();

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void setupContactListView() {
        JPanel listPanel = new JPanel(new BorderLayout());
        contactListModel = new DefaultListModel<>();
        contactList = new JList<>(contactListModel);

        JButton addButton = new JButton("Add New Contact");
        JButton viewButton = new JButton("View Details");

        addButton.addActionListener(e -> cardLayout.show(mainPanel, "Create"));
        viewButton.addActionListener(e -> showContactDetails());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);

        listPanel.add(new JScrollPane(contactList), BorderLayout.CENTER);
        listPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(listPanel, "List");
    }

    private void setupContactDetailsView() {
        JPanel detailsPanel = new JPanel(new GridLayout(4, 1));
        nameLabel = new JLabel();
        phoneLabel = new JLabel();
        emailLabel = new JLabel();
        JButton backButton = new JButton("Back to List");

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "List"));

        detailsPanel.add(nameLabel);
        detailsPanel.add(phoneLabel);
        detailsPanel.add(emailLabel);
        detailsPanel.add(backButton);

        mainPanel.add(detailsPanel, "Details");
    }

    private void setupContactCreationForm() {
        JPanel formPanel = new JPanel(new GridLayout(4, 2));
        nameField = new JTextField();
        phoneField = new JTextField();
        emailField = new JTextField();

        JButton saveButton = new JButton("Save Contact");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(e -> saveContact());
        cancelButton.addActionListener(e -> cardLayout.show(mainPanel, "List"));

        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Phone:"));
        formPanel.add(phoneField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(saveButton);
        formPanel.add(cancelButton);

        mainPanel.add(formPanel, "Create");
    }

    private void saveContact() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();

        if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "All fields must be filled out!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Contact contact = new Contact(name, phone, email);
        contacts.add(contact);
        contactListModel.addElement(name);

        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");

        cardLayout.show(mainPanel, "List");
    }

    private void showContactDetails() {
        int index = contactList.getSelectedIndex();
        if (index == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a contact to view details.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Contact contact = contacts.get(index);
        nameLabel.setText("Name: " + contact.name);
        phoneLabel.setText("Phone: " + contact.phone);
        emailLabel.setText("Email: " + contact.email);

        cardLayout.show(mainPanel, "Details");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ContactManager::new);
    }
}
