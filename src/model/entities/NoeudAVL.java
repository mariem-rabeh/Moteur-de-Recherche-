package model.entities;

import java.util.ArrayList;
import java.util.List;

public class NoeudAVL {
    private String racine; // ex: "كتب"
    private int hauteur;
    private List<MotDerive> listeDerives; 
    private int frequenceRacine;
    
    // PLUS de gauche et droit ici !
    
    public NoeudAVL(String racine) {
        this.racine = racine;
        this.hauteur = 1;
        this.listeDerives = new ArrayList<>();
        this.frequenceRacine = 0;
    }
    
    // ==================== GETTERS ET SETTERS ====================
    
    public String getRacine() {
        return racine;
    }
    
    public void setRacine(String racine) {
        this.racine = racine;
    }
    
    public int getHauteur() {
        return hauteur;
    }
    
    public void setHauteur(int hauteur) {
        this.hauteur = hauteur;
    }
    
    public List<MotDerive> getListeDerives() {
        return listeDerives;
    }
    
    public void setListeDerives(List<MotDerive> listeDerives) {
        this.listeDerives = listeDerives;
    }
    
    public int getFrequenceRacine() {
        return frequenceRacine;
    }
    
    public void setFrequenceRacine(int frequenceRacine) {
        this.frequenceRacine = frequenceRacine;
    }
    
    public void incrementerFrequenceRacine() {
        this.frequenceRacine++;
    }
    
    // ==================== MÉTHODES MÉTIER POUR LES DÉRIVÉS ====================
    
    /**
     * Ajoute un mot dérivé ou incrémente sa fréquence s'il existe déjà
     */
    public void ajouterDerive(String mot) {
        for (MotDerive derive : listeDerives) {
            if (derive.getMot().equals(mot)) {
                derive.incrementerFrequence();
                return;
            }
        }
        listeDerives.add(new MotDerive(mot));
    }
    
    /**
     * Recherche un dérivé spécifique
     */
    public MotDerive rechercherDerive(String mot) {
        for (MotDerive derive : listeDerives) {
            if (derive.getMot().equals(mot)) {
                return derive;
            }
        }
        return null;
    }
    
    /**
     * Vérifie si un mot dérivé existe
     */
    public boolean contientDerive(String mot) {
        return rechercherDerive(mot) != null;
    }
    
    /**
     * Retourne le nombre total de dérivés distincts
     */
    public int getNombreDerives() {
        return listeDerives.size();
    }
    
    /**
     * Retourne la somme des fréquences de tous les dérivés
     */
    public int getFrequenceTotaleDerives() {
        int total = 0;
        for (MotDerive derive : listeDerives) {
            total += derive.getFrequence();
        }
        return total;
    }
    
    /**
     * Valide que la racine est une racine arabe trilitère valide
     */
    public boolean estRacineValide() {
        return racine != null && 
               racine.length() == 3 && 
               racine.matches("[\\u0600-\\u06FF]{3}");
    }
    
    /**
     * Retourne le dérivé le plus fréquent
     */
    public MotDerive getDeriveLesPlusFrequent() {
        if (listeDerives.isEmpty()) {
            return null;
        }
        
        MotDerive plusFrequent = listeDerives.get(0);
        for (MotDerive derive : listeDerives) {
            if (derive.getFrequence() > plusFrequent.getFrequence()) {
                plusFrequent = derive;
            }
        }
        return plusFrequent;
    }
    
    @Override
    public String toString() {
        return "Racine: " + racine + 
               " | Fréquence: " + frequenceRacine + 
               " | Dérivés: " + listeDerives.size() +
               " | Hauteur: " + hauteur;
    }
}