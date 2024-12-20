package cabrel.diffo;

import java.io.*;
import java.util.*;

public class GestionFichiers {
    private static final String FILE_NAME = "C:\\instalation\\java eclipse\\Nouveau dossier1\\cabrel_diffo\\bikes.txt";

    public void ajouterBike(Bike bike) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(bike.getNom() + "," + bike.getCouleur());
            writer.newLine();
        }
    }

    public List<Bike> lireBikes() throws IOException {
        List<Bike> bikes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    bikes.add(new Bike(parts[0], parts[1]));
                }
            }
        }
        return bikes;
    }

    public void supprimerBike(String nom) throws IOException {
        List<Bike> bikes = lireBikes();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Bike bike : bikes) {
                if (!bike.getNom().equals(nom)) {
                    writer.write(bike.getNom() + "," + bike.getCouleur());
                    writer.newLine();
                }
            }
        }
    }
}
