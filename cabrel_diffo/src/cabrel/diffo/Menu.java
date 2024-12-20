package cabrel.diffo;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Menu extends JFrame {
    private JTextField nomField;
    private JTextField couleurField;
    private JTable table;
    private GestionFichiers gestionFichiers;

    public Menu() {
        gestionFichiers = new GestionFichiers();
        initialiser();
    }

    private void initialiser() {
        setTitle("Gestion de Location de Bikes");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel des champs de texte
        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Nom:"));
        nomField = new JTextField();
        panel.add(nomField);
        panel.add(new JLabel("Couleur:"));
        couleurField = new JTextField();
        panel.add(couleurField);

        JButton ajouterButton = new JButton("Ajouter");
        ajouterButton.addActionListener(e -> ajouterBike());
        panel.add(ajouterButton);

        add(panel, BorderLayout.NORTH);

        // Table des bikes
        table = new JTable();
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Panel pour les boutons de suppression, modification et fermeture
        JPanel buttonPanel = new JPanel();
        JButton supprimerButton = new JButton("Supprimer");
        supprimerButton.addActionListener(e -> supprimerBike());
        buttonPanel.add(supprimerButton);

        JButton modifierButton = new JButton("Modifier");
        modifierButton.addActionListener(e -> chargerBikePourModification());
        buttonPanel.add(modifierButton);

        JButton fermerButton = new JButton("Fermer");
        fermerButton.addActionListener(e -> System.exit(0));
        buttonPanel.add(fermerButton);

        add(buttonPanel, BorderLayout.SOUTH);

        mettreAJourTable();
    }

    private void ajouterBike() {
        try {
            String nom = nomField.getText();
            String couleur = couleurField.getText();
            Bike bike = new Bike(nom, couleur);
            gestionFichiers.ajouterBike(bike);
            mettreAJourTable();
            nomField.setText("");
            couleurField.setText("");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout du bike.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void chargerBikePourModification() {
        int row = table.getSelectedRow();
        if (row != -1) {
            String nom = table.getValueAt(row, 0).toString();
            String couleur = table.getValueAt(row, 1).toString();
            nomField.setText(nom);
            couleurField.setText(couleur);
        }
    }

    private void modifierBike() {
        try {
            int row = table.getSelectedRow();
            if (row != -1) {
                String ancienNom = table.getValueAt(row, 0).toString();
                String nouveauNom = nomField.getText();
                String nouvelleCouleur = couleurField.getText();
                gestionFichiers.supprimerBike(ancienNom);
                gestionFichiers.ajouterBike(new Bike(nouveauNom, nouvelleCouleur));
                mettreAJourTable();
                nomField.setText("");
                couleurField.setText("");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la modification du bike.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void supprimerBike() {
        try {
            int row = table.getSelectedRow();
            if (row != -1) {
                String nom = table.getValueAt(row, 0).toString();
                gestionFichiers.supprimerBike(nom);
                mettreAJourTable();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la suppression du bike.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mettreAJourTable() {
        try {
            java.util.List<Bike> bikes = gestionFichiers.lireBikes();
            String[][] data = new String[bikes.size()][2];
            for (int i = 0; i < bikes.size(); i++) {
                data[i][0] = bikes.get(i).getNom();
                data[i][1] = bikes.get(i).getCouleur();
            }
            String[] columnNames = {"Nom", "Couleur"};
            table.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la lecture des bikes.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Menu menu = new Menu();
            menu.setVisible(true);
        });
    }
}
