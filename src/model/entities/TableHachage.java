public class TableHachage {
    private static final int TAILLE = 128; // Puissance de 2 recommandée
    private Maillon[] table;
    private int nombreElements;

    private static class Maillon {
        String cle;      // Nom du schème (ex: "NomAgent")
        Scheme valeur;   // Objet Scheme (contenant la règle "1A2i3")
        Maillon suivant;

        Maillon(String cle, Scheme valeur) {
            this.cle = cle;
            this.valeur = valeur;
        }
    }

    public TableHachage() {
        this.table = new Maillon[TAILLE];
        this.nombreElements = 0;
    }

    /**
     * Algorithme de hachage DJB2
     * Il utilise le nombre magique 5381 et un décalage de bits (hash << 5) + hash
     */
    private int djb2(String cle) {
        long hash = 5381;
        for (int i = 0; i < cle.length(); i++) {
            // hash * 33 + c
            hash = ((hash << 5) + hash) + cle.charAt(i);
        }
        // On s'assure que l'indice est positif et tient dans la table
        return (int) (Math.abs(hash) % TAILLE);
    }

    public void inserer(String nom, Scheme scheme) {
        int indice = djb2(nom);
        Maillon courant = table[indice];

        while (courant != null) {
            if (courant.cle.equals(nom)) {
                courant.valeur = scheme; // Mise à jour si existe déjà
                return;
            }
            courant = courant.suivant;
        }

        // Ajout en tête de liste
        Maillon nouveau = new Maillon(nom, scheme);
        nouveau.suivant = table[indice];
        table[indice] = nouveau;
        nombreElements++;
    }

    public Scheme rechercher(String nom) {
        int indice = djb2(nom);
        Maillon courant = table[indice];

        while (courant != null) {
            if (courant.cle.equals(nom)) {
                return courant.valeur;
            }
            courant = courant.suivant;
        }
        return null; // Non trouvé
    }

    // Getters pour la gestion
    public int getNombreElements() { return nombreElements; }
}