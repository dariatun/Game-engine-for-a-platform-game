/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import java.io.File;

/**
 *
 * @author dariatunina
 */
public class SaveFileManager {

    private final File mainSavingFile;
    private final File temporarilyFile;
    private File currFile;

    /**
     * Creates a new instance of SaveFileManager.
     */
    public SaveFileManager() {
        mainSavingFile = new File("savedPositions.txt");
        temporarilyFile = new File("temporarilySaved.txt");
        temporarilyFile.deleteOnExit();
        currFile = mainSavingFile;
    }

    /**
     *
     * @return True if current file exists, false otherwise
     */
    public boolean currFileExist() {
        return currFile.length() != 0;
    }

    /**
     * Delete the main saving file.
     */
    public void deleteMainFile() {
        mainSavingFile.delete();
    }

    /**
     * Sets current file to requested file.
     *
     * @param str the specific name of file
     */
    public void setCurrFile(String str) {
        currFile = str.equals("main") ? mainSavingFile : temporarilyFile;
    }

    /**
     *
     * @return The file that currently is used for saving parameters.
     */
    public File getFile() {
        return currFile;
    }
}
