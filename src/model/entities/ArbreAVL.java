package model.structures;

import model.entities.NoeudAVL;
import java.util.ArrayList;
import java.util.List;

public class ArbreAVL {
    private NoeudAVL noeud;        // Le noeud contenu dans cet arbre
    private ArbreAVL gauche;       // Sous-arbre gauche
    private ArbreAVL droit;        // Sous-arbre droit
    
    // Constructeur pour un arbre vide
    public ArbreAVL() {
        this.noeud = null;
        this.gauche = null;
        this.droit = null;
    }
    
    // Constructeur pour un arbre avec un noeud
    public ArbreAVL(NoeudAVL noeud) {
        this.noeud = noeud;
        this.gauche = new ArbreAVL(); // Sous-arbre vide
        this.droit = new ArbreAVL();  // Sous-arbre vide
    }
    
    // ==================== GETTERS ET SETTERS ====================
    
    public NoeudAVL getNoeud() {
        return noeud;
    }
    
    public void setNoeud(NoeudAVL noeud) {
        this.noeud = noeud;
    }
    
    public ArbreAVL getGauche() {
        return gauche;
    }
    
    public void setGauche(ArbreAVL gauche) {
        this.gauche = gauche;
    }
    
    public ArbreAVL getDroit() {
        return droit;
    }
    
    public void setDroit(ArbreAVL droit) {
        this.droit = droit;
    }
    
    // ==================== MÉTHODES UTILITAIRES ====================
    
    public boolean estVide() {
        return noeud == null;
    }
    
    public boolean estFeuille() {
        return !estVide() && 
               (gauche == null || gauche.estVide()) && 
               (droit == null || droit.estVide());
    }
    
    private int hauteur() {
        if (estVide()) {
            return 0;
        }
        return noeud.getHauteur();
    }
    
    private int getBalance() {
        if (estVide()) {
            return 0;
        }
        int hauteurGauche = (gauche != null) ? gauche.hauteur() : 0;
        int hauteurDroit = (droit != null) ? droit.hauteur() : 0;
        return hauteurGauche - hauteurDroit;
    }
    
    private void updateHauteur() {
        if (!estVide()) {
            int hauteurGauche = (gauche != null) ? gauche.hauteur() : 0;
            int hauteurDroit = (droit != null) ? droit.hauteur() : 0;
            noeud.setHauteur(1 + Math.max(hauteurGauche, hauteurDroit));
        }
    }
    
    // ==================== ROTATIONS ====================
    
    private void rotationDroite() {
        if (estVide() || gauche == null || gauche.estVide()) {
            return;
        }
        
        NoeudAVL ancienNoeud = this.noeud;
        ArbreAVL ancienDroit = this.droit;
        
        this.noeud = this.gauche.noeud;
        ArbreAVL nouveauDroit = new ArbreAVL(ancienNoeud);
        nouveauDroit.gauche = this.gauche.droit;
        nouveauDroit.droit = ancienDroit;
        
        this.droit = nouveauDroit;
        this.gauche = this.gauche.gauche;
        
        if (this.droit != null) {
            this.droit.updateHauteur();
        }
        this.updateHauteur();
    }
    
    private void rotationGauche() {
        if (estVide() || droit == null || droit.estVide()) {
            return;
        }
        
        NoeudAVL ancienNoeud = this.noeud;
        ArbreAVL ancienGauche = this.gauche;
        
        this.noeud = this.droit.noeud;
        ArbreAVL nouveauGauche = new ArbreAVL(ancienNoeud);
        nouveauGauche.droit = this.droit.gauche;
        nouveauGauche.gauche = ancienGauche;
        
        this.gauche = nouveauGauche;
        this.droit = this.droit.droit;
        
        if (this.gauche != null) {
            this.gauche.updateHauteur();
        }
        this.updateHauteur();
    }
    
    private void equilibrer() {
        int balance = getBalance();
        
        // Cas Gauche-Gauche
        if (balance > 1 && gauche != null && gauche.getBalance() >= 0) {
            rotationDroite();
        }
        // Cas Gauche-Droit
        else if (balance > 1 && gauche != null && gauche.getBalance() < 0) {
            gauche.rotationGauche();
            rotationDroite();
        }
        // Cas Droit-Droit
        else if (balance < -1 && droit != null && droit.getBalance() <= 0) {
            rotationGauche();
        }
        // Cas Droit-Gauche
        else if (balance < -1 && droit != null && droit.getBalance() > 0) {
            droit.rotationDroite();
            rotationGauche();
        }
    }
    
    // ==================== INSERTION ====================
    
    public boolean inserer(String racine) {
        if (estVide()) {
            this.noeud = new NoeudAVL(racine);
            this.gauche = new ArbreAVL();
            this.droit = new ArbreAVL();
            return true;
        }
        
        int comparaison = racine.compareTo(this.noeud.getRacine());
        
        if (comparaison < 0) {
            boolean resultat = gauche.inserer(racine);
            updateHauteur();
            equilibrer();
            return resultat;
        } else if (comparaison > 0) {
            boolean resultat = droit.inserer(racine);
            updateHauteur();
            equilibrer();
            return resultat;
        } else {
            return false; // Racine déjà existante
        }
    }
    
    // ==================== RECHERCHE ====================
    
    public NoeudAVL rechercher(String racine) {
        if (estVide()) {
            return null;
        }
        
        if (this.noeud.getRacine().equals(racine)) {
            return this.noeud;
        }
        
        if (racine.compareTo(this.noeud.getRacine()) < 0) {
            return gauche.rechercher(racine);
        } else {
            return droit.rechercher(racine);
        }
    }
    
    public boolean existe(String racine) {
        return rechercher(racine) != null;
    }
    
    // ==================== SUPPRESSION ====================
    
    public boolean supprimer(String racine) {
        if (estVide()) {
            return false;
        }
        
        int comparaison = racine.compareTo(this.noeud.getRacine());
        
        if (comparaison < 0) {
            boolean resultat = gauche.supprimer(racine);
            updateHauteur();
            equilibrer();
            return resultat;
        } else if (comparaison > 0) {
            boolean resultat = droit.supprimer(racine);
            updateHauteur();
            equilibrer();
            return resultat;
        } else {
            // Noeud trouvé
            
            // Cas 1: Feuille ou noeud avec un seul enfant
            if (gauche.estVide() && droit.estVide()) {
                this.noeud = null;
                this.gauche = null;
                this.droit = null;
            } else if (gauche.estVide()) {
                this.noeud = droit.noeud;
                this.gauche = droit.gauche;
                this.droit = droit.droit;
            } else if (droit.estVide()) {
                this.noeud = gauche.noeud;
                this.droit = gauche.droit;
                this.gauche = gauche.gauche;
            } 
            // Cas 2: Noeud avec deux enfants
            else {
                NoeudAVL successeur = droit.trouverMin();
                this.noeud.setRacine(successeur.getRacine());
                this.noeud.setListeDerives(successeur.getListeDerives());
                this.noeud.setFrequenceRacine(successeur.getFrequenceRacine());
                droit.supprimer(successeur.getRacine());
            }
            
            updateHauteur();
            equilibrer();
            return true;
        }
    }
    
    private NoeudAVL trouverMin() {
        if (gauche == null || gauche.estVide()) {
            return this.noeud;
        }
        return gauche.trouverMin();
    }
    
    // ==================== PARCOURS ====================
    
    public void parcourirInfixe(List<String> resultat) {
        if (estVide()) {
            return;
        }
        
        if (gauche != null) {
            gauche.parcourirInfixe(resultat);
        }
        
        resultat.add(noeud.getRacine());
        
        if (droit != null) {
            droit.parcourirInfixe(resultat);
        }
    }
    
    public List<String> parcourirInfixe() {
        List<String> resultat = new ArrayList<>();
        parcourirInfixe(resultat);
        return resultat;
    }
    
    public void obtenirTousLesNoeuds(List<NoeudAVL> noeuds) {
        if (estVide()) {
            return;
        }
        
        if (gauche != null) {
            gauche.obtenirTousLesNoeuds(noeuds);
        }
        
        noeuds.add(noeud);
        
        if (droit != null) {
            droit.obtenirTousLesNoeuds(noeuds);
        }
    }
    
    public List<NoeudAVL> obtenirTousLesNoeuds() {
        List<NoeudAVL> noeuds = new ArrayList<>();
        obtenirTousLesNoeuds(noeuds);
        return noeuds;
    }
    
    // ==================== STATISTIQUES ====================
    
    public int getNombreRacines() {
        if (estVide()) {
            return 0;
        }
        
        int count = 1;
        if (gauche != null) {
            count += gauche.getNombreRacines();
        }
        if (droit != null) {
            count += droit.getNombreRacines();
        }
        
        return count;
    }
    
    @Override
    public String toString() {
        if (estVide()) {
            return "[]";
        }
        return "[" + noeud.getRacine() + 
               ", G:" + (gauche != null && !gauche.estVide() ? gauche.noeud.getRacine() : "∅") +
               ", D:" + (droit != null && !droit.estVide() ? droit.noeud.getRacine() : "∅") + "]";
    }
}

