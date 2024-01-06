package mancala;

import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;


public class Saver {

    public void saveObject(final Serializable toSave, final String filename) {
        try (ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream(getPath(filename)))) {
            objectOut.writeObject(toSave);
        } catch (IOException e) {
        }
    }

    public Serializable loadObject(final String filename) {
        Serializable loadedGame;
        
        try (ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(getPath(filename)))) {
            loadedGame = (Serializable) objectIn.readObject();
            return loadedGame;
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    private String getPath(final String filename) {
        final Path currentPath = Paths.get(System.getProperty("user.dir"));
        final Path assetsPath = currentPath.resolve("assets");
        try {
            Files.createDirectories(assetsPath);
        } catch (IOException e) {
        }
        final Path returnPath = assetsPath.resolve(filename);
        return returnPath.toString(); 
    }

}