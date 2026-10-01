package pe.edu.upeu.sysventas.controller;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import pe.edu.upeu.sysventas.model.Matrimonio;
import pe.edu.upeu.sysventas.repository.MatrimonioRepository;
import pe.edu.upeu.sysventas.service.MatrimonioService;

import java.time.LocalDate;

public class MatrimonioController {

    private final MatrimonioService service = new MatrimonioService(new MatrimonioRepository());

    @FXML
    private TextField txtActa, txtContrayente1, txtContrayente2, txtLugar, txtBuscar;
    @FXML
    private DatePicker dpFecha;
    @FXML
    private TableView<Matrimonio> tabla;
    @FXML
    private TableColumn<Matrimonio, Number> colId;
    @FXML
    private TableColumn<Matrimonio, String> colActa, colContrayente1, colContrayente2, colLugar;
    @FXML
    private TableColumn<Matrimonio, LocalDate> colFecha;
    @FXML
    private Label lblEstado;

    private Long idSeleccionado = null;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(c -> new SimpleLongProperty(c.getValue().getId()));
        colActa.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNumeroActa()));
        colContrayente1.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getContrayente1()));
        colContrayente2.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getContrayente2()));
        colFecha.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getFechaCelebracion()));
        colLugar.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getLugar()));

        tabla.getSelectionModel().selectedItemProperty().addListener((obs, anterior, m) -> {
            if (m != null) {
                cargarFormulario(m);
            }
        });
        listar();
    }

    @FXML
    public void guardar() {
        try {
            service.guardar(leerFormulario());
            listar();
            limpiar();
            estado("Acta guardada correctamente.");
        } catch (Exception e) {
            alerta(e.getMessage());
        }
    }

    @FXML
    public void actualizar() {
        if (idSeleccionado == null) {
            alerta("Selecciona un acta de la tabla para modificarla.");
            return;
        }
        try {
            Matrimonio m = leerFormulario();
            m.setId(idSeleccionado);
            service.actualizar(m);
            listar();
            limpiar();
            estado("Acta actualizada correctamente.");
        } catch (Exception e) {
            alerta(e.getMessage());
        }
    }

    @FXML
    public void eliminar() {
        Matrimonio m = tabla.getSelectionModel().getSelectedItem();
        if (m == null) {
            alerta("Selecciona un acta para eliminar.");
            return;
        }
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "¿Eliminar el acta seleccionada?",
                ButtonType.YES, ButtonType.NO);
        a.setHeaderText("Confirmar eliminación");
        if (a.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            service.eliminar(m.getId());
            listar();
            limpiar();
            estado("Acta eliminada.");
        }
    }

    @FXML
    public void buscar() {
        var resultados = service.buscar(txtBuscar.getText());
        idSeleccionado = null;
        tabla.setItems(FXCollections.observableArrayList(resultados));
        estado(resultados.size() + " resultado(s) encontrado(s).");
    }

    @FXML
    public void listar() {
        tabla.setItems(FXCollections.observableArrayList(service.listar()));
    }

    @FXML
    public void limpiar() {
        idSeleccionado = null;
        txtActa.clear();
        txtContrayente1.clear();
        txtContrayente2.clear();
        txtLugar.clear();
        dpFecha.setValue(null);
        tabla.getSelectionModel().clearSelection();
        estado("Formulario listo para una nueva acta.");
    }

    private Matrimonio leerFormulario() {
        Matrimonio m = new Matrimonio();
        m.setNumeroActa(txtActa.getText());
        m.setContrayente1(txtContrayente1.getText());
        m.setContrayente2(txtContrayente2.getText());
        m.setFechaCelebracion(dpFecha.getValue());
        m.setLugar(txtLugar.getText());
        return m;
    }

    private void cargarFormulario(Matrimonio m) {
        idSeleccionado = m.getId();
        txtActa.setText(m.getNumeroActa());
        txtContrayente1.setText(m.getContrayente1());
        txtContrayente2.setText(m.getContrayente2());
        dpFecha.setValue(m.getFechaCelebracion());
        txtLugar.setText(m.getLugar());
        estado("Editando el acta N.º " + m.getNumeroActa());
    }

    private void alerta(String mensaje) {
        new Alert(Alert.AlertType.WARNING, "No se pudo completar la operación.\n" + mensaje, ButtonType.OK)
                .showAndWait();
    }

    private void estado(String mensaje) {
        lblEstado.setText(mensaje);
    }
}
