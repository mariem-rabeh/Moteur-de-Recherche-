public class Scheme {
    private String nom;            // ex: "فَاعِل" ou "Forme I"
    private String regle;          // ex: "fA'iL" (où f, ', L sont les positions des lettres)
    private String description;    // ex: "Nom d'agent"

    public Scheme(String nom, String regle, String description) {
        this.nom = nom;
        this.regle = regle;
        this.description = description;
    }

    /**
     * Applique le schème à une racine trilitère.
     * @param racine La racine (ex: "كتب")
     * @return Le mot généré (ex: "كاتب")
     */
    public String appliquer(String racine) {
        if (racine == null || racine.length() != 3) {
            return "Erreur : La racine doit être trilitère (3 lettres).";
        }

        char r1 = racine.charAt(0);
        char r2 = racine.charAt(1);
        char r3 = racine.charAt(2);

        // Exemple simplifié de remplacement basé sur des balises
        // Imaginons que la règle soit : "1A2i3" pour fA'iL
        return regle.replace('1', r1)
                    .replace('2', r2)
                    .replace('3', r3);
    }

    // Getters et Setters
    public String getNom() { return nom; }
    public String getRegle() { return regle; }
}