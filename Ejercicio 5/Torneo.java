import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Clase que representa un torneo de voleibol con funcionalidad para cargar datos, agregar jugadores
 * y realizar consultas sobre los jugadores inscritos.
 */
public class Torneo {
    private List<Jugador> jugadores;
    private String archivoCSV;
    private Scanner scanner;

    /**
     * Constructor de la clase Torneo.
     *
     * @param archivoCSV Nombre del archivo CSV que contiene los datos de los jugadores.
     */
    public Torneo(String archivoCSV) {
        this.jugadores = new ArrayList<>();
        this.archivoCSV = archivoCSV;
        this.scanner = new Scanner(System.in);
        cargarCatalogo();
    }

    /**
     * Metodo que muestra un menu interactivo y permite al usuario realizar diferentes acciones.
     */
    public void menu() {
        boolean salir = false;
        while (!salir) {
            System.out.println("Menu:");
            System.out.println("1. Cargar datos'");
            System.out.println("2. Agregar Jugador");
            System.out.println("3. Mostrar Pasadores con mas del 80% de efectividad");
            System.out.println("4. Mostrar 3 Mejores Liberos");
            System.out.println("5. Mostrar todos los jugadores inscritos");
            System.out.println("6. Salir");
            System.out.print("Elije una opci0n: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    cargarCatalogo();
                    break;
                case 2:
                    agregarJugadorMenu();
                    break;
                case 3:
                    pasadoresEfectivos();
                    break;
                case 4:
                    mejLibero();
                    break;
                case 5:
                    mostrarJugadores();
                    break;
                case 6:
                    salir = true;
                    break;
                default:
                    System.out.println("Opci0n no valida. Intentalo de nuevo.");
            }
        }
        scanner.close();
    }

    /**
     * Metodo para agregar un jugador al torneo.
     *
     * @param jugador Jugador a agregar.
     */
    public void agregarJugador(Jugador jugador) {
        jugadores.add(jugador);
        guardarCatalogo();
    }

    /**
     * Metodo que muestra la informaci0n de todos los jugadores inscritos en el torneo.
     */
    public void mostrarJugadores() {
        for (Jugador jugador : jugadores) {
            System.out.println("Nombre: " + jugador.getNombre());
            System.out.println("Pais: " + jugador.getPais());
            System.out.println("Efectividad: " + jugador.calcularEfectividad());
            System.out.println("---------------");
        }
    }

    /**
     * Metodo que muestra los 3 mejores liberos en el torneo, ordenados por efectividad.
     */
    public void mejLibero() {
        List<Libero> liberos = jugadores.stream()
                .filter(jugador -> jugador instanceof Libero)
                .map(jugador -> (Libero) jugador)
                .sorted(Comparator.comparing(Libero::calcularEfectividad).reversed())
                .limit(3)
                .collect(Collectors.toList());

        System.out.println("Mejores Liberos:");
        for (Libero libero : liberos) {
            System.out.println("Nombre: " + libero.getNombre());
            System.out.println("Pais: " + libero.getPais());
            System.out.println("Efectividad: " + libero.calcularEfectividad());
            System.out.println("---------------");
        }
    }

    /**
     * Metodo que muestra los pasadores con mas del 80% de efectividad en el torneo.
     * Si no hay pasadores con efectividad superior al 80%, se muestra un mensaje.
     */
    public void pasadoresEfectivos() {
        List<Pasador> pasadores = jugadores.stream()
                .filter(jugador -> jugador instanceof Pasador)
                .map(jugador -> (Pasador) jugador)
                .filter(pasador -> pasador.calcularEfectividad() > 0.8)
                .collect(Collectors.toList());

        if (pasadores.isEmpty()) {
            System.out.println("No hay Pasadores con mas del 80% de efectividad.");
        } else {
            System.out.println("Pasadores efectivos con mas del 80% de efectividad:");
            for (Pasador pasador : pasadores) {
                System.out.println("Nombre: " + pasador.getNombre());
                System.out.println("Pais: " + pasador.getPais());
                System.out.println("Efectividad: " + pasador.calcularEfectividad());
                System.out.println("---------------");
            }
        }
    }

    /**
     * Metodo para guardar la informaci0n de los jugadores en un archivo CSV.
     */
    public void guardarCatalogo() {
        try (PrintWriter writer = new PrintWriter(new File(archivoCSV))) {
            for (Jugador jugador : jugadores) {
                StringBuilder line = new StringBuilder();
                line.append(jugador.getNombre()).append(",");
                line.append(jugador.getPais()).append(",");
                line.append(jugador.getErrores()).append(",");
                line.append(jugador.getAces()).append(",");
                line.append(jugador.getTotalServicios()).append(",");

                if (jugador instanceof Libero) {
                    Libero libero = (Libero) jugador;
                    line.append(libero.getRecibosEfectivos());
                } else if (jugador instanceof Pasador) {
                    Pasador pasador = (Pasador) jugador;
                    line.append(pasador.getPases()).append(",");
                    line.append(pasador.getFintas());
                } else if (jugador instanceof AuxOpuestos) {
                    AuxOpuestos auxOpuestos = (AuxOpuestos) jugador;
                    line.append(auxOpuestos.getAtaques()).append(",");
                    line.append(auxOpuestos.getBloqueosEfectivos()).append(",");
                    line.append(auxOpuestos.getBloqueosFallidos());
                }

                writer.println(line.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo para cargar la informaci0n de los jugadores desde un archivo CSV.
     */
    public void cargarCatalogo() {
        jugadores.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(archivoCSV))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String nombre = parts[0].trim();
                    String pais = parts[1].trim();
                    int errores = tryParse(parts[2].trim());
                    int aces = tryParse(parts[3].trim());
                    int totalServicios = tryParse(parts[4].trim());

                    Jugador jugador;

                    if (parts.length == 6) {
                        int recibosEfectivos = tryParse(parts[5].trim());
                        jugador = new Libero(nombre, pais);
                        ((Libero) jugador).setRecibosEfectivos(recibosEfectivos);
                    } else if (parts.length == 7) {
                        int pases = tryParse(parts[5].trim());
                        int fintas = tryParse(parts[6].trim());
                        jugador = new Pasador(nombre, pais);
                        ((Pasador) jugador).setPases(pases);
                        ((Pasador) jugador).setFintas(fintas);
                    } else {
                        int ataques = tryParse(parts[5].trim());
                        int bloqueosEfectivos = tryParse(parts[6].trim());
                        int bloqueosFallidos = tryParse(parts[7].trim());
                        jugador = new AuxOpuestos(nombre, pais);
                        ((AuxOpuestos) jugador).setAtaques(ataques);
                        ((AuxOpuestos) jugador).setBloqueosEfectivos(bloqueosEfectivos);
                        ((AuxOpuestos) jugador).setBloqueosFallidos(bloqueosFallidos);
                    }

                    jugador.setErrores(errores);
                    jugador.setAces(aces);
                    jugador.setTotalServicios(totalServicios);

                    jugadores.add(jugador);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo para intentar analizar y convertir una cadena a un entero.
     *
     * @param text Cadena a analizar.
     * @return Valor entero o 0 si no se puede analizar.
     */
    private int tryParse(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Metodo principal que crea una instancia de Torneo y muestra el menu.
     *
     * @param args Argumentos de linea de comandos (no se utilizan en este caso).
     */
    public static void main(String[] args) {
        Torneo torneo = new Torneo("jugadores.csv");
        torneo.menu();
    }

    /**
     * Metodo para agregar un jugador al torneo a traves de la consola.
     */
    private void agregarJugadorMenu() {
        System.out.println("Elige el tipo de jugador:");
        System.out.println("1. Libero");
        System.out.println("2. Pasador");
        System.out.println("3. AuxOpuestos");
        int tipoJugador = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Pais: ");
        String pais = scanner.nextLine();

        switch (tipoJugador) {
            case 1:
                System.out.print("Recibos efectivos: ");
                int recibosEfectivos = scanner.nextInt();
                scanner.nextLine();
                Libero libero = new Libero(nombre, pais);
                libero.setRecibosEfectivos(recibosEfectivos);
                agregarJugador(libero);
                break;
            case 2:
                System.out.print("Pases: ");
                int pases = scanner.nextInt();
                System.out.print("Fintas: ");
                int fintas = scanner.nextInt();
                scanner.nextLine();
                Pasador pasador = new Pasador(nombre, pais);
                pasador.setPases(pases);
                pasador.setFintas(fintas);
                agregarJugador(pasador);
                break;
            case 3:
                System.out.print("Ataques: ");
                int ataques = scanner.nextInt();
                System.out.print("Bloqueos efectivos: ");
                int bloqueosEfectivos = scanner.nextInt();
                System.out.print("Bloqueos fallidos: ");
                int bloqueosFallidos = scanner.nextInt();
                scanner.nextLine();
                AuxOpuestos auxOpuestos = new AuxOpuestos(nombre, pais);
                auxOpuestos.setAtaques(ataques);
                auxOpuestos.setBloqueosEfectivos(bloqueosEfectivos);
                auxOpuestos.setBloqueosFallidos(bloqueosFallidos);
                agregarJugador(auxOpuestos);
                break;
            default:
                System.out.println("Tipo de jugador no valido.");
        }
    }
}