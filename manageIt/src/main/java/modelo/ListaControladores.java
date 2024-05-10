package modelo;

import controller.*;

public class ListaControladores {
    private ControllerContenedor controllerContenedor;
    private ControllerMenuLateral controllerMenuLateral;
    private ControllerMenuSuperior controllerMenuSuperior;
    private ControllerProyectos controllerProyectos;
    private ControllerNotas controllerNotas;

    public ControllerNotas getControllerNotas() {
        return controllerNotas;
    }

    public void setControllerNotas(ControllerNotas controllerNotas) {
        this.controllerNotas = controllerNotas;
    }

    public ControllerContenedor getControllerContenedor() {
        return controllerContenedor;
    }

    public ControllerMenuLateral getControllerMenuLateral() {
        return controllerMenuLateral;
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
