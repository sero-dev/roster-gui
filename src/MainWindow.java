import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
/**************************************************************************************************
 * Project Name:    Student Management System<br>
 * File Name:       MainWindow.java<br>
 * Purpose:         Creates the main window from the application
 *
 * @author          Sean Rodriguez
 * @since           1.0
 */
public class MainWindow extends JFrame {
    private JTable recordTable;
    private int rowCount = 1;

    private final String aboutMessage = "This is an application developed by me to demostrate " +
            "how to implement a desktop \napplication using the 3 Tier Architecture as a " +
            "guideline.\nTo start, click on File->Open to start importing data.\nThen, " +
            "Double click on table row to View/Change it or click on Add/Delete buttons.";

    // Array of items inside searchCombo JComboBox
    private final String[] comboList = {"Row ID", "First Name", "Last Name",
        "CUNY ID", "GPA", "Venus Login"};

    // Array of headers for JTable
    private final String[] columnList = {"Row ID", "First Name", "Last Name",
        "CUNY ID", "GPA", "Venus Login"};

    /**************************************************************************************************
     * Default Constructor
     */
    public MainWindow() {
        super("Student Management System");
        setLayout(new FlowLayout());
        setSize(550,580);
        setResizable(false);

        // Auto-center window to center screen
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(size.width / 2 - this.getSize().width / 2,
                size.height / 2 - this.getSize().height / 2);

        // Set UI Elements
        setJMenuBar(setMenuBar(new JMenuBar()));
        setMainContentWindow();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**************************************************************************************************
     * Sets up JMenuBar with JMenu's and JMenuItem's
     *
     * @param menu      Menu that is being setup
     * @return menu     Menu after setup is complete
     */
    private JMenuBar setMenuBar(JMenuBar menu) {
        // Setup all keystrokes for all menu items
        KeyStroke ctrlO = KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK);
        KeyStroke ctrlS = KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK);
        KeyStroke ctrlQ = KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK);
        KeyStroke altA = KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.ALT_DOWN_MASK);

        // File Menu Column
        JMenu file = new JMenu("File");
        JMenuItem open = new JMenuItem("Open...", KeyEvent.VK_O);
        JMenuItem save = new JMenuItem("Save As...", KeyEvent.VK_S);
        JMenuItem quit = new JMenuItem("Quit", KeyEvent.VK_Q);

        file.setMnemonic(KeyEvent.VK_F);

        // Sets keystrokes for File Menu items
        open.setAccelerator(ctrlO);
        save.setAccelerator(ctrlS);
        quit.setAccelerator(ctrlQ);

        // Sets Open menu item ActionListener
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                FileFilter filter = new FileNameExtensionFilter("Text Files", "txt");
                JFileChooser file = new JFileChooser();

                file.setFileFilter(filter);
                file.setCurrentDirectory(new File(System.getProperty("user.dir")));

                if(file.showOpenDialog(MainWindow.this) == JFileChooser.APPROVE_OPTION) {
                    appendDataToTable((new FileReader(file.getSelectedFile())).getRecordArray());
                }
            }
        });

        // Sets Export menu item ActionListener
        save.addActionListener(new ExportAction());

        // Sets Quit menu item ActionListener
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(1);
            }
        });

        // Add Menu Items to File Menu
        file.add(open);
        file.add(save);
        file.addSeparator();
        file.add(quit);

        // Edit Menu Column
        JMenu edit = new JMenu("Edit");
        JMenuItem add = new JMenuItem("Add Record...", KeyEvent.VK_A);
        JMenuItem delete = new JMenuItem("Delete Record...", KeyEvent.VK_D);
        JMenuItem modify = new JMenuItem("Modify Record...", KeyEvent.VK_M);
        JMenuItem clear = new JMenuItem("Clear Table", KeyEvent.VK_C);

        edit.setMnemonic(KeyEvent.VK_E);

        // Set ActionListeners for all Edit Menu items
        add.addActionListener(new AddAction());
        delete.addActionListener(new DeleteAction());
        modify.addActionListener(new ModifyAction());
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(JOptionPane.showConfirmDialog(MainWindow.this, "Are you sure that you want to Clear the table?",
                        "Clear Confirmation", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                    clearDataFromTable();
                }
            }
        });

        // Add menu items to Edit Menu
        edit.add(add);
        edit.add(modify);
        edit.add(delete);
        edit.addSeparator();
        edit.add(clear);

        // About Menu Column
        JMenu help = new JMenu("Help");
        JMenuItem about = new JMenuItem("About", KeyEvent.VK_A);
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, aboutMessage, "About this application",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        help.setMnemonic(KeyEvent.VK_H);
        about.setAccelerator(altA);
        help.add(about);

        // Add Menu Items to Help Menu
        menu.add(file);
        menu.add(edit);
        menu.add(help);

        return menu;
    }

    /**************************************************************************************************
     * Sets recordTable to an empty table
     */
    private void setDefaultJTable() {
        DefaultTableModel model = new DefaultTableModel(0, columnList.length) {
            // Set Table records to be immutable
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            // Sets all column headers to sort based on data type
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return Integer.class;
                    case 3:
                        return Integer.class;
                    case 4:
                        return Double.class;
                    default:
                        return String.class;
                }
            }
        };

        // Set column headers for JTable using String[]
        model.setColumnIdentifiers(columnList);

        recordTable = new JTable(model);

        // Set Right Alignment for Row ID, CUNY ID, GPA columns
        DefaultTableCellRenderer rightAlign = new DefaultTableCellRenderer();
        rightAlign.setHorizontalAlignment(JLabel.RIGHT);
        recordTable.getColumnModel().getColumn(0).setCellRenderer(rightAlign);
        recordTable.getColumnModel().getColumn(3).setCellRenderer(rightAlign);
        recordTable.getColumnModel().getColumn(4).setCellRenderer(rightAlign);

        // Enable auto-sorter for JTable columns
        recordTable.setAutoCreateRowSorter(true);

        // Listens for clicks on a specific row in the JTable
        recordTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2) {
                    Object[] record = new Object[6];
                    int rowNumber = recordTable.getSelectedRow();
                    DefaultTableModel model = (DefaultTableModel) recordTable.getModel();

                    // If no row is selected, give a warning message
                    if (rowNumber == -1) JOptionPane.showMessageDialog(MainWindow.this, "You did not select a row",
                            "Error during Row Modification", JOptionPane.WARNING_MESSAGE);
                    else {  // Open window for record deletion
                        for (int i = 0; i < 6; i++)
                            record[i] = recordTable.getValueAt(recordTable.getSelectedRow(), i);

                        ModifyWindow modifyWindow = new ModifyWindow(MainWindow.this, record);
                        modifyWindow.setVisible(true);

                        if (modifyWindow.hasInfo()) {
                            record = modifyWindow.getInfo();

                            for (int i = 0; i < 5; i++) {
                                model.setValueAt(record[i], rowNumber, i + 1);
                            }
                        }
                    }
                }
            }
        });
    }

    /**************************************************************************************************
     * Sets up all UI elements inside the main window
     */
    private void setMainContentWindow() {
        // Set up elements to be added to window
        JLabel searchLabel = new JLabel("Search by:");
        JComboBox searchCombo = new JComboBox(comboList);
        JTextField searchBox = new JTextField(10);
        JButton addButton = new JButton("Add");
        JButton deleteButton = new JButton("Delete");
        setDefaultJTable();

        JButton exportButton = new JButton("Export Data");

        // Add all UI Elements to Window
        add(searchLabel);
        add(searchCombo);
        add(searchBox);
        add(addButton);
        add(deleteButton);
        add(new JScrollPane(recordTable));
        add(exportButton);

        // Add SearchBox actionLister to filter JTable
        searchBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterTable(searchBox.getText(), searchCombo);
            }
        });

        // Add actionlisteners for all buttons
        exportButton.addActionListener(new ExportAction());
        addButton.addActionListener(new AddAction());
        deleteButton.addActionListener(new DeleteAction());
    }

    /**************************************************************************************************
     * Clears data from the table and sets row count to 0
     */
    private void clearDataFromTable() {
        DefaultTableModel model = (DefaultTableModel) recordTable.getModel();
        model.setRowCount(0);

        rowCount = 1;
    }

    /**************************************************************************************************
     * Append records to the record table
     *
     * @param object    Records to be added to table
     */
    private void appendDataToTable(Object object[]) {
        // Grabs model from record table
        DefaultTableModel model = (DefaultTableModel) recordTable.getModel();

        for(int i = 0; i < object.length; i+=5)
            model.addRow(new Object[]{rowCount++, object[i], object[i + 1], object[i + 2], object[i + 3], object[i + 4]});
    }

    /**************************************************************************************************
     * Filters table based on query inside textfield and comboList
     *
     * @param query     Text that is to filter for in the table
     */
    private void filterTable(String query, JComboBox type) {

        // TODO Fix search criteria for specific rules

        TableRowSorter<DefaultTableModel> model =  new TableRowSorter<DefaultTableModel>(
                (DefaultTableModel) recordTable.getModel());

        recordTable.setRowSorter(model);

        // Set filter to the correct column and based on the query
        model.setRowFilter(RowFilter.regexFilter(query, type.getSelectedIndex()));
    }

    /**************************************************************************************************
     * Exports table's current model to a file
     *
     * @param file      File where the records are to be stored
     */
    private boolean export(File file) {
        DefaultTableModel model = (DefaultTableModel) recordTable.getModel();
        int rowNum = model.getRowCount();
        int colNum = model.getColumnCount();

        // Output JTable to the specified file in a compatible format
        try {
            BufferedWriter output = new BufferedWriter(new FileWriter(file));

            for(int i = 0; i < rowNum; i++) {
                for (int j = 1; j < colNum; j++) {
                    output.write(model.getValueAt(i, j).toString());
                    if(j % 5 != 0) output.write(",");
                    if(j % 5 != 3 && j % 5 != 0) output.write(" ");
                }
                output.write("\r\n");
            }

            output.flush();
            output.close();

        } catch(IOException e) {
            JOptionPane.showMessageDialog(this, "File could not be exported.", "File IO Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        JOptionPane.showMessageDialog(MainWindow.this, "File successfully exported to " + file.getPath());
        return true;
    }

    /**************************************************************************************************
     * ActionListener class for exporting out to the user selected destination
     */
    private class ExportAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser file = new JFileChooser();
            FileFilter filter = new FileNameExtensionFilter("Text Files", "txt");

            file.setCurrentDirectory(new File(System.getProperty("user.dir")));
            file.setFileFilter(filter);

            if (recordTable.getRowCount() == 0) {
                JOptionPane.showMessageDialog(MainWindow.this, "No data to export... Action cancelled.");
                return;
            }

            do {    // While file cannot be exported
                if (file.showSaveDialog(MainWindow.this) != JFileChooser.APPROVE_OPTION) {
                    JOptionPane.showMessageDialog(MainWindow.this, "No file selected.");
                    break;
                }
            } while(!export(file.getSelectedFile()));

        }
    }

    /**************************************************************************************************
     * ActionListener class for opening the Add Window
     */
    private class AddAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AddWindow addWindow = new AddWindow(MainWindow.this);
            addWindow.setVisible(true);

            if(addWindow.hasInfo()) {
                appendDataToTable(addWindow.getInfo());

                // Move scroll pane to the last index added
                recordTable.getSelectionModel().setSelectionInterval(
                        recordTable.getRowCount() - 1, recordTable.getRowCount() - 1);
                recordTable.scrollRectToVisible(new Rectangle(
                        recordTable.getCellRect(recordTable.getRowCount() - 1, 0, true)));
            }
        }
    }

    /**************************************************************************************************
     * ActionListener class for opening the Delete Window
     */
    private class DeleteAction implements  ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object[] record = new Object[6];
            int rowNumber = recordTable.getSelectedRow();
            DefaultTableModel model = (DefaultTableModel) recordTable.getModel();

            // If no row is selected, give a warning message
            if(rowNumber == -1) {
                JOptionPane.showMessageDialog(MainWindow.this, "You did not select a row",
                        "Error during Row Deletion", JOptionPane.WARNING_MESSAGE);
            }
            else {  // Open window for record deletion
                for (int i = 0; i < 6; i++)
                    record[i] = recordTable.getValueAt(recordTable.getSelectedRow(), i);

                DeleteWindow deleteWindow = new DeleteWindow(MainWindow.this, record);
                deleteWindow.setVisible(true);

                // Check if row can be deleted
                if(deleteWindow.canDelete()) {
                    model.removeRow(rowNumber);
                }
            }
        }
    }

    /**************************************************************************************************
     * ActionListener class for opening the Modify Window
     */
    private class ModifyAction implements  ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object[] record = new Object[6];
            int rowNumber = recordTable.getSelectedRow();
            DefaultTableModel model = (DefaultTableModel) recordTable.getModel();

            // If no row is selected, give a warning message
            if(rowNumber == -1) {
                JOptionPane.showMessageDialog(MainWindow.this, "You did not select a row",
                        "Error during Row Modification", JOptionPane.WARNING_MESSAGE);
            }
            else {  // Open window for record deletion
                for (int i = 0; i < 6; i++)
                    record[i] = recordTable.getValueAt(recordTable.getSelectedRow(), i);

                ModifyWindow modifyWindow = new ModifyWindow(MainWindow.this, record);
                modifyWindow.setVisible(true);

                if (modifyWindow.hasInfo()) {
                    record = modifyWindow.getInfo();

                    for (int i = 0; i < 5; i++) {
                        model.setValueAt(record[i], rowNumber, i + 1);
                    }
                }
            }
        }
    }
}
