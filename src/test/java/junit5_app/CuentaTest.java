package junit5_app;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.jfernber.junit5app.ejemplos.exceptions.DineroInsuficienteException;
import org.jfernber.junit5app.ejemplos.models.Banco;
import org.jfernber.junit5app.ejemplos.models.Cuenta;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class CuentaTest {

	@Test
	@DisplayName("Probando nombre de la cuenta")
	void testNombreCuenta(TestInfo testInfo, TestReporter testReporter) {
		System.out.println("Ejecutando " + testInfo.getDisplayName() + " " + testInfo.getTestMethod().get().getName());
		Cuenta cuenta = new Cuenta("José", new BigDecimal("1515.12"));
		//cuenta.setPersona("José");
		String esperado = "José";
		String real = cuenta.getPersona();
		assertEquals(esperado, real);
	}
	
	@Test
	void testSaldoCuenta() {
		Cuenta cuenta = new Cuenta("José", new BigDecimal("1515.12"));
		assertNotNull(cuenta.getSaldo());
		assertEquals(1515.12, cuenta.getSaldo().doubleValue());
		assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
		assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
	}
	
	@Test
	void testReferenciaCuenta() {
		Cuenta cuenta1 = new Cuenta("José", new BigDecimal("1515.12"));
		Cuenta cuenta2 = new Cuenta("José", new BigDecimal("1515.12"));
//		assertNotEquals(cuenta1, cuenta2);
		assertEquals(cuenta1, cuenta2);
		
	}
	
	@Test
	void testDebitoCuenta() {
		Cuenta cuenta = new Cuenta("José", new BigDecimal("1515.12"));
		cuenta.debito(new BigDecimal(100));
		assertNotNull(cuenta.getSaldo());
		assertEquals(1415, cuenta.getSaldo().intValue());
	}
	
	@Test
	void testCerditoCuenta() {
		Cuenta cuenta = new Cuenta("José", new BigDecimal("1515.12"));
		cuenta.credito(new BigDecimal(100));
		assertNotNull(cuenta.getSaldo());
		assertEquals(1615, cuenta.getSaldo().intValue());
	}
	
	@Test
	void testDineroInsuficienteExceptionCuenta() {
		Cuenta cuenta = new Cuenta("José", new BigDecimal("1515.12"));
		Exception exception = assertThrows(DineroInsuficienteException.class, ()->{
			cuenta.debito(new BigDecimal(2000));
		});
		String actual = exception.getMessage();
		String esperado = "Dinero insuficiente";
		assertEquals(esperado, actual);
	}
	
	@Test void testTransferirDineroCuentas() {
		Cuenta origen = new Cuenta("José", new BigDecimal("1515.12"));
		Cuenta destino = new Cuenta("José", new BigDecimal("1515.12"));
		Banco banco = new Banco();
		banco.setNombre("La Caixa");
		banco.transferir(origen, destino, new BigDecimal("500"));
		assertEquals("1015.12", origen.getSaldo().toPlainString());
		assertEquals("2015.12", destino.getSaldo().toPlainString());
	}
	
	@Test void testRelaccionBancoCuentas() {
		Cuenta origen = new Cuenta("José", new BigDecimal("1515.12"));
		Cuenta destino = new Cuenta("José", new BigDecimal("1515.12"));
		Banco banco = new Banco();
		banco.setNombre("La Caixa");
		banco.addCuenta(origen);
		banco.addCuenta(destino);
		banco.transferir(origen, destino, new BigDecimal("500"));
		assertEquals("1015.12", origen.getSaldo().toPlainString());
		assertEquals("2015.12", destino.getSaldo().toPlainString());
		assertEquals(2, banco.getCuentas().size());
		assertEquals("La Caixa", origen.getBanco().getNombre());
		assertEquals("La Caixa", destino.getBanco().getNombre());
		boolean contiene = false;
		for (Cuenta cuenta:banco.getCuentas()) {
			if (cuenta.getPersona().equals("José")){
				contiene = true;
			}
		}
		assertTrue(contiene);
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"100", "200", "300", "500", "700", "1000"})
	void testDebitoCuentaValueSource(String cantidad) {
		Cuenta cuenta = new Cuenta("José", new BigDecimal("1515.12"));
		cuenta.debito(new BigDecimal(cantidad));
		assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
	}
	
	@ParameterizedTest
	@CsvSource({"1,100", "2,200", "3,300", "4,500", "5,700", "6,1000"})
	void testDebitoCuentaCsvSource(String index, String cantidad) {
		Cuenta cuenta = new Cuenta("José", new BigDecimal("1515.12"));
		cuenta.debito(new BigDecimal(cantidad));
		assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/data.csv")
	void testDebitoCuentaCsvFileSource(String cantidad) {
		Cuenta cuenta = new Cuenta("José", new BigDecimal("1515.12"));
		cuenta.debito(new BigDecimal(cantidad));
		assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
	}
	
	@ParameterizedTest
	@MethodSource("cantidadesList")
	void testDebitoCuentaMethodSource(String cantidad) {
		Cuenta cuenta = new Cuenta("José", new BigDecimal("1515.12"));
		cuenta.debito(new BigDecimal(cantidad));
		assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
	}
	
	static List<String> cantidadesList(){
		return (List<String>) Arrays.asList("100", "200", "300", "500", "700", "1000");
	}

}
