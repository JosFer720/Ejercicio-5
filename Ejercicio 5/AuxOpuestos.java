//Combine las clases Auxiliar y Opuestos en uno solo, ya que ambos hacen lo mismo

public class AuxOpuestos extends Jugador {
    private int ataques;
    private int bloqueosEfectivos;
    private int bloqueosFallidos;

    // Constructor de la clase AuxOpuestos
    public AuxOpuestos(String nombre, String pais) {
        // Llama al constructor de la superclase (Jugador) con nombre y país
        super(nombre, pais);
        this.ataques = 0;
        this.bloqueosEfectivos = 0;
        this.bloqueosFallidos = 0;
    }

    // Métodos getters y setters para los atributos específicos de AuxOpuestos
    public int getAtaques() {
        return ataques;
    }

    public void setAtaques(int ataques) {
        this.ataques = ataques;
    }

    public int getBloqueosEfectivos() {
        return bloqueosEfectivos;
    }

    public void setBloqueosEfectivos(int bloqueosEfectivos) {
        this.bloqueosEfectivos = bloqueosEfectivos;
    }

    public int getBloqueosFallidos() {
        return bloqueosFallidos;
    }

    public void setBloqueosFallidos(int bloqueosFallidos) {
        this.bloqueosFallidos = bloqueosFallidos;
    }

    // Sobrescribe el método calcularEfectividad de la superclase Jugador
    @Override
    public double calcularEfectividad() {
        // Verifica si el total de servicios es igual a 0 para evitar divisiones por 0
        if (getTotalServicios() == 0) {
            return 0;
        }
        // Calcula la efectividad según la fórmula dada
        return ((((ataques + bloqueosEfectivos - bloqueosFallidos - getErrores()) * 100.0) /
                (ataques + bloqueosEfectivos + bloqueosFallidos + getErrores())) + ((getAces() * 100.0) / getTotalServicios()));
    }
}