package modelo;

import controller.*;
import controller.mejora.ControllerMenuLateralEmpresa;
import controller.mejora.ControllerMenuSuperiorAdmin;
import controller.mejora.ControllerMenuSuperiorEmpresa;

public class ListaControladores {
    private ControllerContenedor controllerContenedor;
    private ControllerMenuLateral controllerMenuLateral;
    private ControllerMenuSuperior controllerMenuSuperior;
    private ControllerProyectos controllerProyectos;
    private ControllerMenuLateralEmpresa controllerMenuLateralEmpresa;
    private ControllerNotas controllerNotas;
    private ControllerMenuSuperiorEmpresa controllerMenuSuperiorEmpresa;
    private ControllerMenuSuperiorAdmin controllerMenuSuperiorAdmin;

    public ControllerNotas getControllerNotas() {
        return controllerNotas;
    }

    public ControllerMenuSuperiorAdmin getControllerMenuSuperiorAdmin() {
        return controllerMenuSuperiorAdmin;
    }

    public void setControllerMenuSuperiorAdmin(ControllerMenuSuperiorAdmin controllerMenuSuperiorAdmin) {
        this.controllerMenuSuperiorAdmin = controllerMenuSuperiorAdmin;
    }

    public void setControllerNotas(ControllerNotas controllerNotas) {
        this.controllerNotas = controllerNotas;
    }

    public ControllerMenuSuperiorEmpresa getControllerMenuSuperiorEmpresa() {
        return controllerMenuSuperiorEmpresa;
    }

    public void setControllerMenuSuperiorEmpresa(ControllerMenuSuperiorEmpresa controllerMenuSuperiorEmpresa) {
        this.controllerMenuSuperiorEmpresa = controllerMenuSuperiorEmpresa;
    }

    public ControllerContenedor getControllerContenedor() {
        return controllerContenedor;
    }

    public ControllerMenuLateral getControllerMenuLateral() {
        return controllerMenuLateral;
    }

    public ControllerMenuLateralEmpresa getControllerMenuLateralEmpresa() {
        return controllerMenuLateralEmpresa;
    }

    public void setControllerMenuLateralEmpresa(ControllerMenuLateralEmpresa controllerMenuLateralEmpresa) {
        this.controllerMenuLateralEmpresa = controllerMenuLateralEmpresa;
    }

    public void setControllerContenedor(ControllerContenedor controllerContenedor) {
        this.controllerContenedor = controllerContenedor;
    }

    public void setControllerMenuLateral(ControllerMenuLateral controllerMenuLateral) {
        this.controllerMenuLateral = controllerMenuLateral;
    }

    public void setControllerMenuSuperior(ControllerMenuSuperior controllerMenuSuperior) {
        this.controllerMenuSuperior = controllerMenuSuperior;
    }

    public ControllerMenuSuperior getControllerMenuSuperior() {
        return controllerMenuSuperior;
    }

    public ControllerProyectos getControllerProyectos() {
        return controllerProyectos;
    }

    public void setControllerProyectos(ControllerProyectos controllerProyectos) {
        this.controllerProyectos = controllerProyectos;
    }
}
