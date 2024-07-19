import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.*;
import java.util.HashMap;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
class Produit {
    protected String nom;
    protected double prix;
    protected int quantite;

    public Produit(String nom, double prix, int quantite) {
        this.nom = nom;
        this.prix = prix;
        this.quantite = quantite;
    }

    @Override
    public String toString() {
        return "Nom: " + nom + ", Prix: " + prix + ", Quantité: " + quantite;
    }
}

// Classes dérivées
class Electronique extends Produit {
    private String marque;

    public Electronique(String nom, double prix, int quantite, String marque) {
        super(nom, prix, quantite);
        this.marque = marque;
    }

    @Override
    public String toString() {
        return super.toString() + ", Marque: " + marque;
    }
}

class Alimentaire extends Produit {
    private String dateExpiration;

    public Alimentaire(String nom, double prix, int quantite, String dateExpiration) {
        super(nom, prix, quantite);
        this.dateExpiration = dateExpiration;
    }

    @Override
    public String toString() {
        return super.toString() + ", Date d'expiration: " + dateExpiration;
    }
}

class Vestimentaire extends Produit {
    private String taille;

    public Vestimentaire(String nom, double prix, int quantite, String taille) {
        super(nom, prix, quantite);
        this.taille = taille;
    }

    @Override
    public String toString() {
        return super.toString() + ", Taille: " + taille;
    }
}

class ProduitNonTrouveException extends Exception {
    public ProduitNonTrouveException(String message) {
        super(message);
    }
}

class Magasin {
    private HashMap<Integer, Produit> produits = new HashMap<>();
    private int idCounter = 1;

    public void ajouterProduit(Produit produit) {
        produits.put(idCounter++, produit);
    }

    public void supprimerProduit(int id) throws ProduitNonTrouveException {
        if (produits.remove(id) == null) {
            throw new ProduitNonTrouveException("Produit avec ID " + id + " non trouvé.");
        }
    }

    public void modifierProduit(int id, Produit produit) throws ProduitNonTrouveException {
        if (!produits.containsKey(id)) {
            throw new ProduitNonTrouveException("Produit avec ID " + id + " non trouvé.");
        }
        produits.put(id, produit);
    }

    public Produit rechercherProduitParNom(String nom) throws ProduitNonTrouveException {
        for (Produit produit : produits.values()) {
            if (produit.nom.equalsIgnoreCase(nom)) {
                return produit;
            }
        }
        throw new ProduitNonTrouveException("Produit avec nom " + nom + " non trouvé.");
    }

    public void listerProduitsParLettre(char lettre) {
        for (Produit produit : produits.values()) {
            if (produit.nom.charAt(0) == lettre) {
                System.out.println(produit);
            }
        }
    }

    public int nombreDeProduitsEnStock() {
        return produits.size();
    }

    public void sauvegarderProduits(String fichier) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichier))) {
            for (Produit produit : produits.values()) {
                writer.write(produit.toString());
                writer.newLine();
            }
        }
    }

    public void chargerProduits(String fichier) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                System.out.println(ligne);
            }
        }
    }
}

// Classe principale
public class GestionDeProduits {
    public static void main(String[] args) {
        String BDD = "BDD.txt";
        String PRODUITS= "nos produits.";

        try {
            FileWriter writer = new FileWriter(BDD, true); // 'true' pour ajouter à la fin du fichier
            writer.write(PRODUITS + "\n");
            writer.close();
            System.out.println("Le produit a été écrit avec succès dans " + BDD);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Magasin magasin = new Magasin();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Ajouter un produit");
            System.out.println("2. Supprimer un produit");
            System.out.

                    println("3. Modifier un produit par son identifiant");
            System.out.println("4. Rechercher un produit par nom");
            System.out.println("5. Lister les produits en saisissant une lettre alphabétique");
            System.out.println("6. Afficher le nombre de produits en stock");
            System.out.println("7. Sauvegarder les produits dans un fichier");
            System.out.println("8. Charger les produits depuis un fichier");
            System.out.println("9. Quitter");
            System.out.print("Entrez une option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consommer la nouvelle ligne

            try {
                switch (option) {
                    case 1:
                        System.out.print("Entrez le type de produit (1: Electronique, 2: Alimentaire, 3: Vestimentaire): ");
                        int type = scanner.nextInt();
                        scanner.nextLine(); // Consommer la nouvelle ligne
                        System.out.print("Entrez le nom: ");
                        String nom = scanner.nextLine();
                        System.out.print("Entrez le prix: ");
                        double prix = scanner.nextDouble();
                        System.out.print("Entrez la quantité: ");
                        int quantite = scanner.nextInt();
                        scanner.nextLine(); // Consommer la nouvelle ligne

                        Produit produit = null;
                        if (type == 1) {
                            System.out.print("Entrez la marque: ");
                            String marque = scanner.nextLine();
                            produit = new Electronique(nom, prix, quantite, marque);
                        } else if (type == 2) {
                            System.out.print("Entrez la date d'expiration: ");
                            String dateExpiration = scanner.nextLine();
                            produit = new Alimentaire(nom, prix, quantite, dateExpiration);
                        } else if (type == 3) {
                            System.out.print("Entrez la taille: ");
                            String taille = scanner.nextLine();
                            produit = new Vestimentaire(nom, prix, quantite, taille);
                        }

                        magasin.ajouterProduit(produit);
                        break;
                    case 2:
                        System.out.print("Entrez l'identifiant du produit à supprimer: ");
                        int idSupprimer = scanner.nextInt();
                        magasin.supprimerProduit(idSupprimer);
                        break;
                    case 3:
                        System.out.print("Entrez l'identifiant du produit à modifier: ");
                        int idModifier = scanner.nextInt();
                        scanner.nextLine(); // Consommer la nouvelle ligne
                        System.out.print("Entrez le nom: ");
                        nom = scanner.nextLine();
                        System.out.print("Entrez le prix: ");
                        prix = scanner.nextDouble();
                        System.out.print("Entrez la quantité: ");
                        quantite = scanner.nextInt();
                        scanner.nextLine(); // Consommer la nouvelle ligne

                        produit = new Produit(nom, prix, quantite);
                        magasin.modifierProduit(idModifier, produit);
                        break;
                    case 4:
                        System.out.print("Entrez le nom du produit à rechercher: ");
                        nom = scanner.nextLine();
                        Produit produitTrouve = magasin.rechercherProduitParNom(nom);
                        System.out.println(produitTrouve);
                        break;
                    case 5:
                        System.out.print("Entrez une lettre alphabétique: ");
                        char lettre = scanner.nextLine().charAt(0);
                        magasin.

                                listerProduitsParLettre(lettre);
                        break;
                    case 6:
                        System.out.println("Nombre de produits en stock: " + magasin.nombreDeProduitsEnStock());
                        break;
                    case 7:
                        System.out.print("Entrez le nom du fichier pour sauvegarder: ");
                        String fichierSauvegarde = scanner.nextLine();
                        magasin.sauvegarderProduits(fichierSauvegarde);
                        break;
                    case 8:
                        System.out.print("Entrez le nom du fichier pour charger: ");
                        String fichierChargement = scanner.nextLine();
                        magasin.chargerProduits(fichierChargement);
                        break;
                    case 9:
                        System.out.println("Au revoir!");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Option invalide. Veuillez réessayer.");
                }
            } catch (ProduitNonTrouveException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println("Erreur d'entrée/sortie: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Une erreur est survenue: " + e.getMessage());
            }
        }
    }
}

