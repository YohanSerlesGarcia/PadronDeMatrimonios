package pe.edu.upeu.sysventas.model;

import java.time.LocalDate;

/**
 * Acta de unión civil del registro civil.
 */
public class Matrimonio {

    private Long id;
    private String numeroActa;
    private String contrayente1;
    private String contrayente2;
    private LocalDate fechaCelebracion;
    private String lugar;

    public Matrimonio() {
    }

    public Matrimonio(Long id, String numeroActa, String contrayente1, String contrayente2,
                      LocalDate fechaCelebracion, String lugar) {
        this.id = id;
        this.numeroActa = numeroActa;
        this.contrayente1 = contrayente1;
        this.contrayente2 = contrayente2;
        this.fechaCelebracion = fechaCelebracion;
        this.lugar = lugar;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroActa() {
        return numeroActa;
    }

    public void setNumeroActa(String numeroActa) {
        this.numeroActa = numeroActa;
    }

    public String getContrayente1() {
        return contrayente1;
    }

    public void setContrayente1(String contrayente1) {
        this.contrayente1 = contrayente1;
    }

    public String getContrayente2() {
        return contrayente2;
    }

    public void setContrayente2(String contrayente2) {
        this.contrayente2 = contrayente2;
    }

    public LocalDate getFechaCelebracion() {
        return fechaCelebracion;
    }

    public void setFechaCelebracion(LocalDate fechaCelebracion) {
        this.fechaCelebracion = fechaCelebracion;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }
}
