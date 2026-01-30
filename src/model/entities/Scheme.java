public class Scheme {
    private String nom;   // ex: "فَعَّلَ"
    private String regle; // ex: "12ّ3"

    public Scheme(String nom, String regle) {
        this.nom = nom;
        this.regle = regle;
    }

    /**
     * Génère un mot à partir d'une racine trilitère.
     * Remplace '1' par la 1ère lettre, '2' par la 2ème, et '3' par la 3ème.
     */
    public String appliquer(String racine) {
        if (racine == null || racine.length() != 3) {
            return "Erreur : La racine doit contenir exactement 3 lettres.";
        }

        StringBuilder motGenere = new StringBuilder();
        char r1 = racine.charAt(0);
        char r2 = racine.charAt(1);
        char r3 = racine.charAt(2);

        // On parcourt la règle caractère par caractère
        for (int i = 0; i < regle.length(); i++) {
            char c = regle.charAt(i);

            switch (c) {
                case '1':
                    motGenere.append(r1);
                    break;
                case '2':
                    motGenere.append(r2);
                    break;
                case '3':
                    motGenere.append(r3);
                    break;
                default:
                    // Si c'est un caractère fixe (ا, م, ّ, etc.), on l'ajoute tel quel
                    motGenere.append(c);
                    break;
            }
        }

        return motGenere.toString();
    }

    // Getters
    public String getNom() { return nom; }
    public String getRegle() { return regle; }

    @Override
    public String toString() {
        return "Schème: " + nom + " | Structure: " + regle;
    }
}