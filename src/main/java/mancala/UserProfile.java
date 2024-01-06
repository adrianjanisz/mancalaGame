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


public class UserProfile {
    
    public void savePlayers(final Serializable toSave1, final Serializable toSave2, final String filename1, final String filename2) {
        try (ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream(getPath(filename1)))) {
            objectOut.writeObject(toSave1);
        } catch (IOException e) {
        }
        try (ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream(getPath(filename2)))) {
            objectOut.writeObject(toSave2);
        } catch (IOException e) {
        }
    }

    public Serializable loadPlayer1(final String filename) {
        Serializable loadedPlayer1;
        
        try (ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(getPath(filename)))) {
            loadedPlayer1 = (Serializable) objectIn.readObject();
            return loadedPlayer1;
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    public Serializable loadPlayer2(final String filename) {
        Serializable loadedPlayer2;
        
        try (ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(getPath(filename)))) {
            loadedPlayer2 = (Serializable) objectIn.readObject();
            return loadedPlayer2;
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