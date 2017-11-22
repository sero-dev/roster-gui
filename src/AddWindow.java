import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**************************************************************************************************
 * Project Name:    Student Management System<br>
 * File Name:       AddWindow.java<br>
 * Purpose:         Creates an add window to create a new Student to be added to JTable
 *
 * @author          Sean Rodriguez
 * @since           1.0
 */
public class AddWindow extends ExternalWindow {
    Object[] info = new Object[5];

    /**************************************************************************************************
     * Constructor that takes a JFrame as an argument
     * @param parent    Parent of this window
     */
    public AddWindow(JFrame parent) {
        super(parent);
        setTitle("Create New Student Record");

        acceptButton.setText("Add New Student");    // Set text for accept button
        venusLoginTextBox.setEditable(false);       // Set VenusLoginTextBox to immutable

        // Add focus listeners for firstName, lastName, and CUNY ID boxes to change VenusLogin dynamically
        firstNameTextBox.addFocusListener(new VenusFocusListener());
        lastNameTextBox.addFocusListener(new VenusFocusListener());
        cunyIDTextBox.addFocusListener(new VenusFocusListener());

        // Add actionlistener for accept button to add information, if valid, to the object array
        acceptButton.addActionListener(new ActionListener() {
            int cunyID;
            double gpa;

            @Override
            public void actionPerformed(ActionEvent e) {
                if(JOptionPane.showConfirmDialog(AddWindow.this, "Are you sure you want to Add this student?",
                        "Add Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    // Checks if CUNYID is an integer
                    try {
                        cunyID = Integer.parseInt(cunyIDTextBox.getText());
                    } catch (NumberFormatException ex) {
                        displayErrorMessage(2);
                        return;
                    }

                    // Checks if GPA is a double
                    try {
                        gpa = Double.parseDouble(gpaTextBox.getText());
                    } catch (NumberFormatException ex) {
                        displayErrorMessage(3);
                        return;
                    }
                    // Checks if first name is proper format
                    if(firstNameTextBox.getText().isEmpty() || firstNameTextBox.getText().length() < 2)
                        displayErrorMessage(0);
                    // Checks if last name is proper format
                    else if(lastNameTextBox.getText().isEmpty() || lastNameTextBox.getText().length() < 2)
                        displayErrorMessage(1);
                    // Checks if CUNY ID is proper format
                    else if(cunyIDTextBox.getText().isEmpty() || cunyID < 9999999 || cunyID > 99999999)
                        displayErrorMessage(2);
                    // Checks if GPA is proper format
                    else if(gpaTextBox.getText().isEmpty() || gpa > 4.0 || gpa < 0.0)
                        displayErrorMessage(3);
                    // If everything is valid, import information into the object array
                    else {
                        info[0] = firstNameTextBox.getText();
                        info[1] = lastNameTextBox.getText();
                        info[2] = cunyIDTextBox.getText();
                        info[3] = gpaTextBox.getText();
                        info[4] = venusLoginTextBox.getText();
                        isChanged = true;
                        dispose();
                    }
                }
            }
        });
    }

    /**************************************************************************************************
     * Returns the object array of student's information
     *
     * @return  Object array of student's information
     */
    public Object[] getInfo() {
        return info;
    }

    /**************************************************************************************************
     * Checks if the object array has information inside
     *
     * @return  true, if there is information stored
     */
    public boolean hasInfo() {
        return isChanged;
    }

    /**************************************************************************************************
     * Displays an error message to the user
     *
     * @param errorCode     Error code to be displayed in dialog box
     */
    protected void displayErrorMessage(int errorCode) {
        String[] errors = new String[] { "First Name cannot be blank and must be atleast two letters",
                "Last Name cannot be blank and must be atleast two letters",
                "Invalid Format for CUNY ID. CUNY ID must be an 8 digit number",
                "Invalid Format for GPA. GPA must be a value between 0.0-4.0"
        };

        JOptionPane.showMessageDialog(AddWindow.this, errors[errorCode], "Missing Fields", JOptionPane.ERROR_MESSAGE);
    }

    /**************************************************************************************************
     * FocusLister class that changes the VenusLogin text based FirstName, LastName and CUNY ID text boxes
     */
    private class VenusFocusListener implements FocusListener {
        @Override
        public void focusGained(FocusEvent e) {}

        @Override
        public void focusLost(FocusEvent e) {
            String firstInitial = null;
            String lastInitial = null;
            int last4Digits;

            // Checks if last name has correct length
            if (!(lastNameTextBox.getText().length() < 2)) {
                lastInitial = lastNameTextBox.getText().substring(0, 2);
                venusLoginTextBox.setText(lastInitial);
            }

            // Checks if first name has correct length
            if (!(firstNameTextBox.getText().length() < 2)) {
                firstInitial = firstNameTextBox.getText().substring(0, 2);
                venusLoginTextBox.setText(venusLoginTextBox.getText() + firstInitial);
            }

            // Checks if CUNY ID is correct length
            if (!(cunyIDTextBox.getText().length() < 4)) {
                try {
                    last4Digits = Integer.parseInt(cunyIDTextBox.getText()) % 10000;
                } catch (NumberFormatException ex) {
                    return;
                }

                venusLoginTextBox.setText(venusLoginTextBox.getText() + last4Digits);
            }
        }
    }
}
