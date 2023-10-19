public class Jugador {
    // Atributos de la clase Jugador
    private String nombre;
    private String pais;
    private int errores;
    private int aces;
    private int totalServicios;

    // Constructor de la clase Jugador
    public Jugador(String nombre, String pais) {
        this.nombre = nombre;
        this.pais = pais;
        this.errores = 0;
        this.aces = 0;
        this.totalServicios = 0;
    }

    // Métodos getters y setters para los atributos de la clase Jugador
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public int getErrores() {
        return errores;
    }

    public void setErrores(int errores) {
        this.errores = errores;
    }

    public int getAces() {
        return aces;
    }

    public void setAces(int aces) {
        this.aces = aces;
    }

    public int getTotalServicios() {
        return totalServicios;
    }

    public void setTotalServicios(int totalServicios) {
        this.totalServicios = totalServicios;
    }

    // Método para calcular la efectividad del jugador
    public double calcularEfectividad() {
        // Verifica si el total de servicios es igual a 0 para evitar divisiones por 0
        if (totalServicios == 0) {
            return 0;
        }
        // Calcula la efectividad según la fórmula dada
        return ((double) aces - errores) / totalServicios;
    }
}