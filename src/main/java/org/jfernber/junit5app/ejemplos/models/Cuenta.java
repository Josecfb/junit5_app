package org.jfernber.junit5app.ejemplos.models;

import java.math.BigDecimal;
import java.util.Objects;

import org.jfernber.junit5app.ejemplos.exceptions.DineroInsuficienteException;

public class Cuenta {
	private String persona;
	private BigDecimal saldo;
	private Banco banco;
	
	
	public Cuenta(String persona, BigDecimal saldo) {
		this.saldo = saldo;
		this.persona = persona;
	}
	public String getPersona() {
		return persona;
	}
	public void setPersona(String persona) {
		this.persona = persona;
	}
	public BigDecimal getSaldo() {
		return saldo;
	}
	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}
	public Banco getBanco() {
		return banco;
	}
	public void setBanco(Banco banco) {
		this.banco = banco;
	}
	
	public void debito(BigDecimal monto) {
		BigDecimal nuevoSaldo = this.saldo.subtract(monto);
		if (nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
			throw new DineroInsuficienteException("Dinero insuficiente");
		}
		this.saldo = nuevoSaldo;
	}
	
	public void credito(BigDecimal monto) {
		this.saldo = this.saldo.add(monto);
	}

	@Override
	public boolean equals(Object obj) {
		Cuenta c = (Cuenta) obj;
		return c.getPersona().equals(this.persona) && c.getSaldo().equals(this.saldo);
	}
	
}
