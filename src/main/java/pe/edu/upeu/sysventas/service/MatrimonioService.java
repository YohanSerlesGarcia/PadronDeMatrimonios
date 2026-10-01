package pe.edu.upeu.sysventas.service;

import pe.edu.upeu.sysventas.model.Matrimonio;
import pe.edu.upeu.sysventas.repository.MatrimonioRepository;

import java.time.LocalDate;
import java.util.List;

public class MatrimonioService {

    private final MatrimonioRepository repository;

    public MatrimonioService(MatrimonioRepository repository) {
        this.repository = repository;
    }

    public Matrimonio guardar(Matrimonio m) {
        validar(m);
        return repository.guardar(m);
    }

    public Matrimonio actualizar(Matrimonio m) {
        validar(m);
        return repository.actualizar(m);
    }

    public List<Matrimonio> listar() {
        return repository.listar();
    }

    /**
     * Busca por nombre de cualquiera de los contrayentes (o por número de acta).
     */
    public List<Matrimonio> buscar(String texto) {
        String q = texto == null ? "" : texto.trim().toLowerCase();
        return repository.listar().stream()
                .filter(m -> m.getContrayente1().toLowerCase().contains(q)
                        || m.getContrayente2().toLowerCase().contains(q)
                        || m.getNumeroActa().toLowerCase().contains(q))
                .toList();
    }

    public void eliminar(Long id) {
        repository.eliminar(id);
    }

    private void validar(Matrimonio m) {
        if (vacio(m.getNumeroActa())
                || vacio(m.getContrayente1())
                || vacio(m.getContrayente2())
                || m.getFechaCelebracion() == null
                || vacio(m.getLugar())) {
            throw new IllegalArgumentException("Completa todos los campos antes de guardar.");
        }
        m.setNumeroActa(m.getNumeroActa().trim());
        if (m.getContrayente1().trim().equalsIgnoreCase(m.getContrayente2().trim())) {
            throw new IllegalArgumentException("Los dos contrayentes deben ser personas distintas.");
        }
        if (m.getFechaCelebracion().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de celebración no puede ser futura.");
        }
        if (repository.existeNumeroActa(m.getNumeroActa(), m.getId())) {
            throw new IllegalArgumentException("El número de acta " + m.getNumeroActa() + " ya está registrado.");
        }
    }

    private boolean vacio(String s) {
        return s == null || s.trim().isEmpty();
    }
}
