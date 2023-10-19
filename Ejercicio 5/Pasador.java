public class Pasador extends Jugador {
    // Atributos específicos de la clase Pasador
    private int pases;
    private int fintas;

    // Constructor de la clase Pasador
    public Pasador(String nombre, String pais) {
        // Llama al constructor de la superclase (Jugador) con nombre y país
        super(nombre, pais);
        this.pases = 0;
        this.fintas = 0;
    }

    // Métodos getters y setters para los atributos específicos de Pasador
    public int getPases() {
        return pases;
    }

    public void setPases(int pases) {
        this.pases = pases;
    }

    public int getFintas() {
        return fintas;
    }

    public void setFintas(int fintas) {
        this.fintas = fintas;
    }

    // Sobrescribe el método calcularEfectividad de la superclase Jugador
    @Override
    public double calcularEfectividad() {
        // Verifica si el total de servicios es igual a 0 para evitar divisiones por 0
        if (getTotalServicios() == 0) {
            return 0;
        }
        // Calcula la efectividad según la fórmula dada
        return ((((pases + fintas - getErrores()) * 100.0) / (pases + fintas + getErrores())) + ((getAces() * 100.0) / getTotalServicios()));
    }
}