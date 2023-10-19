public class Libero extends Jugador {
    // Atributo específico de la clase Libero
    private int recibosEfectivos;

    // Constructor de la clase Libero
    public Libero(String nombre, String pais) {
        // Llama al constructor de la superclase (Jugador) con nombre y país
        super(nombre, pais);
        this.recibosEfectivos = 0;
    }

    // Métodos getters y setters para el atributo específico de Libero
    public int getRecibosEfectivos() {
        return recibosEfectivos;
    }

    public void setRecibosEfectivos(int recibosEfectivos) {
        this.recibosEfectivos = recibosEfectivos;
    }

    // Sobrescribe el método calcularEfectividad de la superclase Jugador
    @Override
    public double calcularEfectividad() {
        // Verifica si el total de servicios es igual a 0 para evitar divisiones por 0
        if (getTotalServicios() == 0) {
            return 0;
        }
        // Calcula la efectividad según la fórmula dada
        return (((recibosEfectivos - getErrores()) * 100.0) / (recibosEfectivos + getErrores())) + ((getAces() * 100.0) / getTotalServicios());
    }
}
