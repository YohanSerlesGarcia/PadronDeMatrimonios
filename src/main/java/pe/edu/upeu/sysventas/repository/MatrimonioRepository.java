package pe.edu.upeu.sysventas.repository;

import pe.edu.upeu.sysventas.model.Matrimonio;

import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio en memoria para las operaciones CRUD del ejercicio.
 */
public class MatrimonioRepository {

    private final List<Matrimonio> datos = new ArrayList<>();
    private long secuencia = 1;

    public Matrimonio guardar(Matrimonio m) {
        if (m.getId() == null) {
            m.setId(secuencia++);
        }
        datos.add(m);
        return m;
    }

    public Matrimonio actualizar(Matrimonio m) {
        for (int i = 0; i < datos.size(); i++) {
            if (datos.get(i).getId().equals(m.getId())) {
                datos.set(i, m);
                return m;
            }
        }
        throw new IllegalArgumentException("No se encontró el registro seleccionado.");
    }

    public List<Matrimonio> listar() {
        return new ArrayList<>(datos);
    }

    public void eliminar(Long id) {
        datos.removeIf(m -> m.getId().equals(id));
    }

    /**
     * Indica si ya existe otra acta con ese número. Al modificar, se excluye el propio registro.
     */
    public boolean existeNumeroActa(String numeroActa, Long idExcluido) {
        return datos.stream()
                .filter(m -> idExcluido == null || !m.getId().equals(idExcluido))
                .anyMatch(m -> m.getNumeroActa().equalsIgnoreCase(numeroActa));
    }
}
