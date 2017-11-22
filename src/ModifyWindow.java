import javax.swing.*;

/**************************************************************************************************
 * Project Name:    Student Management System<br>
 * File Name:       ModifyWindow.java<br>
 * Purpose:         Creates a modify window to update a Student's information
 *
 * @author          Sean Rodriguez
 * @since           1.0
 */
public class ModifyWindow extends AddWindow {

    /**************************************************************************************************
     * Constructor that takes a JFrame parent and an Object array for Student information
     *
     * @param parent    Parent of this window
     * @param row      Object array of student's information
     */
    public ModifyWindow(JFrame parent, Object[] row) {
        super(parent);

        setTitle("Modify Current Student Record");

        add(rowIDTextBox, 0);
        add(rowIDLabel, 0);

        rowIDTextBox.setEditable(false);
        venusLoginTextBox.setEditable(false);

        setInformation(row);

        acceptButton.setText("Update Student Record");
    }
}
