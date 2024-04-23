package ficherosBinarios;

import java.util.InputMismatchException;

public class Persona {
	
	private String nombre;
	private int edad;
	private char sexo;
	
	public Persona(String nombre, int edad, char sexo) {
		this.nombre=nombre;
		this.edad=edad;
		this.sexo=sexo;
	}
	public Persona() {}
	
	public String getNombre() {
		return nombre;
	}
	public boolean setNombre(String nombre) {
		if(nombre.equals("**"))
			return false;
		else
			this.nombre = nombre;
			return true;
	}
	public int getEdad() {
		return edad;
	}
	public boolean setEdad(String edad) {
		int edadNum=0;
		try {
			edadNum=Integer.parseInt(edad);
		}catch(InputMismatchException e) {
			System.out.println("Valor no numérico");
			return false;
		}
		this.edad = edadNum;
		return true;
	}
	public char getSexo() {
		return sexo;
	}
	public boolean setSexo(String sexo) {
		if(!sexo.equals("H") && !sexo.equals("M")) {
			System.out.println("escoja entre H para hombre y M para mujer ");
			return false;
		}
		else {
			this.sexo = sexo.charAt(0);
			return true;
		}
	}
	
	
}
