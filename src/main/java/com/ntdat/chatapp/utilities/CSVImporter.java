package com.ntdat.chatapp.utilities;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Vector;

public class CSVImporter {
    private String fileName;
    private Vector table = new Vector();

    public String getFileName() {
        return fileName;
    }

    public Vector getTable() {
        return table;
    }

    public void importCSV(Component parent) {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.showDialog(parent, "Chọn");
        File selectedFile = jFileChooser.getSelectedFile();
        if (selectedFile == null) return;
        String[] fileComponents = selectedFile.getAbsolutePath().split("\\\\");
        fileName = fileComponents[fileComponents.length - 1].replace(".csv", "");
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(selectedFile));
            String line;
            while (true) {
                line = bufferedReader.readLine();
                if (line == null) break;
                String[] tokens = line.split(",");
                Vector record = new Vector();
                for (String token : tokens) {
                    record.add(token);
                }
                table.add(record);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
