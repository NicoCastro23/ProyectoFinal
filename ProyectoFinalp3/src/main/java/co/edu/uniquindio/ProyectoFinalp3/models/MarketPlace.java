package co.edu.uniquindio.ProyectoFinalp3.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import jakarta.persistence.*;
import java.io.Serializable;

import co.edu.uniquindio.ProyectoFinalp3.exceptions.VendedorNoExistenteException;
@Entity
public class MarketPlace implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public MarketPlace() {
    }

    @Column(nullable = false, unique = true) // El nombre es obligatorio y único
    private String nombre;

    @ManyToMany
    @JoinTable(name = "publicacion_vendedores", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "publicacion_id"), // Clave de la tabla actual
            inverseJoinColumns = @JoinColumn(name = "vendedor_id") // Clave de la tabla "Vendedor"
    )
    private List<Vendedor> vendedores = new ArrayList<>();

    public MarketPlace(String nombre) {
        this.nombre = nombre;
        this.vendedores = new ArrayList<>();
    }

    public void registrar(String nombre, String contrasena, String ciudad, String telefono, String direccion,
            String correoElectronico) {
        Vendedor nuevoVendedor = new Vendedor(nombre, telefono, contrasena, correoElectronico, ciudad, direccion, null);
        vendedores.add(nuevoVendedor);
        System.out.println("Vendedor registrado exitosamente: " + nombre);
    }

    public void login(String nombre, String contrasena) {
        Optional<Vendedor> vendedor = buscarVendedorPorNombre(nombre);
        if (vendedor.isPresent() && vendedor.get().getContrasena().equals(contrasena)) {
            System.out.println("Login exitoso para el vendedor: " + nombre);
        } else {
            System.out.println("Credenciales incorrectas.");
        }
    }

    // Método para buscar un vendedor por su nombre
    public Optional<Vendedor> buscarVendedorPorNombre(String nombre) {
        return vendedores.stream()
                .filter(v -> v.getNombre().equals(nombre))
                .findAny();
    }

    public Vendedor buscarVendedor(String nombre) throws VendedorNoExistenteException {
        Vendedor vendedor = buscarVendedor(nombre);
        if (vendedor == null) {
            throw new VendedorNoExistenteException("El vendedor con NOMBRE " + nombre + " no existe.");
        }
        return vendedor;
    }

    // Método para sugerir un vendedor
    public void sugerirVendedor() {
        if (!vendedores.isEmpty()) {
            Vendedor vendedorSugerido = vendedores.get(0);
            System.out.println("Sugerencia de vendedor: " + vendedorSugerido.getNombre());
        } else {
            System.out.println("No hay vendedores registrados para sugerir.");
        }
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Vendedor> getVendedores() {
        return vendedores;
    }
}
