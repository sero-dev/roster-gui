import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**************************************************************************************************
 * Project Name:    Student Management System<br>
 * File Name:       DeleteWindow.java<br>
 * Purpose:         Creates a delete window to purge a Student from the JTable
 *
 * @author          Sean Rodriguez
 * @since           1.0
 */
public class DeleteWindow extends ExternalWindow {

    private boolean _delete = false;    // Can the row be delete?

    /**************************************************************************************************
     * Constructor that takes a JFrame parent and an Object array for Student information
     *
     * @param parent    Parent of this window
     * @param info      Array of student information
     */
    public DeleteWindow(JFrame parent, Object[] info) {
        super(parent);

        setTitle("Delete A Student Record");

        // Adds the index row to the top of the window
        add(rowIDTextBox, 0);
        add(rowIDLabel, 0);

        setInformation(info);   // Sets text inside text boxes from the provided object array

        // Sets all text boxes to immutable
        rowIDTextBox.setEditable(false);
        firstNameTextBox.setEditable(false);
        lastNameTextBox.setEditable(false);
        cunyIDTextBox.setEditable(false);
        gpaTextBox.setEditable(false);
        venusLoginTextBox.setEditable(false);

        // Sets text for accept button
        acceptButton.setText("Purge Student Record");

        // Add actionlistener for accept button, to prompt user if they want to delete the record
        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(JOptionPane.showConfirmDialog(DeleteWindow.this, "Are you sure that you want to Delete this student?", "Delete Confirmation", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                    _delete = true; // Sets row deletion to true;
                    dispose();
                }
            }
        });
    }

    /**************************************************************************************************
     * Returns whether the row can be deleted
     *
     * @return      true, if row can be deleted
     */
    public boolean canDelete() {
        return _delete;
    }
}
