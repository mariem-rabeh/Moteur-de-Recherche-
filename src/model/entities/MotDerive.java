package model.entities;

public class MotDerive {
    private String mot;
    private int frequence;
    
    public MotDerive(String mot) {
        this.mot = mot;
        this.frequence = 1; // Initialisé à 1 lors de la première création/validation
    }
    
    public void incrementerFrequence() {
        this.frequence++;
    }
    
    // Getters et setters
    public String getMot() {
        return mot;
    }
    
    public void setMot(String mot) {
        this.mot = mot;
    }
    
    public int getFrequence() {
        return frequence;
    }
    
    public void setFrequence(int frequence) {
        this.frequence = frequence;
    }
    
    @Override
    public String toString() {
        return mot + " (fréquence: " + frequence + ")";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MotDerive that = (MotDerive) obj;
        return mot.equals(that.mot);
    }
    
    @Override
    public int hashCode() {
        return mot.hashCode();
    }
}