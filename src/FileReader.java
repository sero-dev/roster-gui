import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

/**************************************************************************************************
 * Project Name:    Student Management System<br>
 * File Name:       FileReader.java<br>
 * Purpose:         Reads file and outputs to array of student information
 *
 * @author          Sean Rodriguez
 * @since           1.0
 */
public class FileReader {
    private ArrayList<Object> recordArray;

    /**************************************************************************************************
     * Imports information from file and stores it into an ArrayList
     *
     * @param file  File that the student information comes from
     */
    public FileReader(File file) {
        BufferedReader input = null;
        String line;
        Object[] info;

        recordArray = new ArrayList<>();

        // Read file by line and split value using "," delimiter
        try {
            input = new BufferedReader(new java.io.FileReader(file));

            while((line = input.readLine()) != null) {
                info = line.split(", *");
                for(int i = 0; i < 5; i++)
                    recordArray.add(info[i]);
            }

            input.close();

        } catch(IOException e) {
            JOptionPane.showMessageDialog(null, "File cannot be opened.", "File IO Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**************************************************************************************************
     * Returns the ArrayList as an Array object
     *
     * @return      Array of values collected from the specified file
     */
    public Object[] getRecordArray() {
        return recordArray.toArray();
    }
}
