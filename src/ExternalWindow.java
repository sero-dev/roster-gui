import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**************************************************************************************************
 * Project Name:    Student Management System<br>
 * File Name:       ExternalWindow.java<br>
 * Purpose:         Sets a generic window for addWindow, modifyWindow, and deleteWindow
 *
 * @author          Sean Rodriguez
 * @since           1.0
 */
public abstract class ExternalWindow extends JDialog {
    protected JLabel rowIDLabel;
    protected JLabel firstNameLabel;
    protected JLabel lastNameLabel;
    protected JLabel cunyIDLabel;
    protected JLabel gpaLabel;
    protected JLabel venusLoginLabel;

    protected JTextField rowIDTextBox;
    protected JTextField firstNameTextBox;
    protected JTextField lastNameTextBox;
    protected JTextField cunyIDTextBox;
    protected JTextField gpaTextBox;
    protected JTextField venusLoginTextBox;

    protected JButton acceptButton;
    protected JButton cancelButton;

    private final int MAX_COLUMN = 15;                              // Column size for JTextFields
    private final Dimension labelSize = new Dimension(80, 15);      // Dimension for JLabels
    protected boolean isChanged = false;                            // Flag if information is changed

    /**************************************************************************************************
     * Constructor that takes a JFrame argument
     *
     * @param parent    Parent of this window
     */
    public ExternalWindow(JFrame parent) {
        super(parent);
        setLayout(new FlowLayout());
        setSize(300,250);
        setResizable(false);
        setModalityType(ModalityType.APPLICATION_MODAL);

        // Sets position of window to be the middle of it's parent
        setLocationRelativeTo(parent);

        setUI(); // Sets UI elements for Window

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    /**************************************************************************************************
     * Sets UI Elements for Window
     */
    private void setUI() {
        // Sets label Text
        rowIDLabel = new JLabel("Row ID:");
        firstNameLabel = new JLabel("First Name:");
        lastNameLabel = new JLabel("Last Name:");
        cunyIDLabel = new JLabel("CUNY ID:");
        gpaLabel = new JLabel("GPA:");
        venusLoginLabel = new JLabel("Venus Login:");

        // Sets label size and alignment
        rowIDLabel.setPreferredSize(labelSize);
        rowIDLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        firstNameLabel.setPreferredSize(labelSize);
        firstNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        lastNameLabel.setPreferredSize(labelSize);
        lastNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        cunyIDLabel.setPreferredSize(labelSize);
        cunyIDLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        gpaLabel.setPreferredSize(labelSize);
        gpaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        venusLoginLabel.setPreferredSize(labelSize);
        venusLoginLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        // Sets text boxes with MAX_COLUMN size;
        rowIDTextBox = new JTextField(MAX_COLUMN);
        firstNameTextBox = new JTextField(MAX_COLUMN);
        lastNameTextBox = new JTextField(MAX_COLUMN);
        cunyIDTextBox = new JTextField(MAX_COLUMN);
        gpaTextBox = new JTextField(MAX_COLUMN);
        venusLoginTextBox = new JTextField(MAX_COLUMN);

        // Sets button text
        acceptButton = new JButton("Accept");
        cancelButton = new JButton("Cancel");

        // Sets the default button
        getRootPane().setDefaultButton(acceptButton);

        // Add element to the window
        add(firstNameLabel);
        add(firstNameTextBox);
        add(lastNameLabel);
        add(lastNameTextBox);
        add(cunyIDLabel);
        add(cunyIDTextBox);
        add(gpaLabel);
        add(gpaTextBox);
        add(venusLoginLabel);
        add(venusLoginTextBox);
        add(acceptButton);
        add(cancelButton);

        // Add actionlistener for cancel button to dispose of window
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });


    }

    /**************************************************************************************************
     * Sets all text for text boxes to student information
     *
     * @param info  Object array of student information
     */
    protected void setInformation(Object[] info) {
        rowIDTextBox.setText(info[0].toString());
        firstNameTextBox.setText(info[1].toString());
        lastNameTextBox.setText(info[2].toString());
        cunyIDTextBox.setText(info[3].toString());
        gpaTextBox.setText(info[4].toString());
        venusLoginTextBox.setText(info[5].toString());
    }

}
