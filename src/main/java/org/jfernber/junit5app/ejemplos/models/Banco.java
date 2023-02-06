package org.jfernber.junit5app.ejemplos.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Banco {
	private String nombre;
	private List<Cuenta> cuentas;
	
	public Banco() {
		cuentas = new ArrayList<Cuenta>();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public List<Cuenta> getCuentas() {
		return cuentas;
	}

	public void setCuentas(List<Cuenta> cuentas) {
		this.cuentas = cuentas;
	}

	public void transferir(Cuenta origen, Cuenta destino, BigDecimal cantidad) {
		origen.debito(cantidad);
		destino.credito(cantidad);
	}
	
	public void addCuenta(Cuenta cuenta) {
		cuentas.add(cuenta);
		cuenta.setBanco(this);
	}
}
