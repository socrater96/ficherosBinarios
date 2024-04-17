//
package ficherosBinarios;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class FicherosBinarios {
	static int menu(Scanner in) {
		int opcion=0;
		do {
			System.out.println("\tMENÚ");
			System.out.println("1.Altas");
			System.out.println("2.Listado");
			System.out.println("3.Búsqueda");
			System.out.println("4.Fin");
			System.out.println("Seleccione opción: (1-4)");
			try {
				opcion=in.nextInt();
			}catch(NumberFormatException nfe) {
				System.out.println("Valor no numérico");
			}
		}while(opcion<1||opcion>4);
		return opcion;
	}
	static void alta(Scanner in){
		String nombre=" ";
		int edad=0;
		char sexo=' ';
		try {
			DataOutputStream esc = new DataOutputStream(new FileOutputStream("todos.dat",true));
			
			System.out.println("Nombre (** para finalizar)");
			nombre=in.nextLine();
			while(!nombre.equals("**")) {
				System.out.println("Edad: ");
				edad=in.nextInt();
				do {
					System.out.print("Sexo: (H/M)");
					sexo=Character.toUpperCase((char) System.in.read());
					while(System.in.read()!='\n');
				}while(sexo!='V' && sexo!='M');
				in.nextLine();
				esc.writeUTF(nombre);
				esc.writeInt(edad);
				esc.writeChar(sexo);
				System.out.print("\nNombre (** para finalizar)? ");
				nombre = in.nextLine();
			}
			esc.close();
		}catch(IOException ioe) {}
	}
	static void listadoPersonas() {
		String nombre= null;
		int edad=0;
		char sexo=' ';
		try {
			DataInputStream leer =new DataInputStream (new FileInputStream("todos.dat"));
			System.out.println();
			nombre=leer.readUTF();
			while(!nombre.equals(null)) {
				edad=leer.readInt();
				sexo=leer.readChar();
				System.out.println(nombre+tabular(nombre)+edad+"\t"+ sexo);
				nombre=leer.readUTF();
			}
			leer.close();
		}catch(IOException ioe) {}
		
	}
	private static String tabular(String nombre) {
		String tabulado="\t";
		if(nombre.length()<8) {
			tabulado="\t\t";
		}
		
		return tabulado;
	}
	static int menuBusqueda(Scanner in) {
		int opcion=0;
		do {
			System.out.println("\tMENÚ DE BÚSQUEDA");
			System.out.println("1.Búsqueda por nombre");
			System.out.println("2.Búsqueda por sexo");
			System.out.println("3.Búsqueda por nombre y edad");
			System.out.println("4.Volver al menú principal");
			System.out.println("Seleccione opción: (1-3)");
			try {
				opcion=in.nextInt();
			}catch(NumberFormatException nfe) {
				System.out.println("Valor no numérico");
			}
		}while(opcion<1||opcion>4);
		return opcion;
	}
	static void busquedaNombre(Scanner in) {
		System.out.println("Nombre a buscar: ");
		String nombreBus=in.nextLine();
		boolean esta=false;
		try {
			DataInputStream dis =new DataInputStream (new FileInputStream("todos.dat"));
			nombreBus= dis.readUTF();
			while(!nombreBus.equals(null)) {
				String nombre=dis.readUTF();
				int edad=dis.readInt();
				char sexo=dis.readChar();
				if(nombreBus.equalsIgnoreCase(nombre)) {
					System.out.print(nombre+tabular(nombre)+edad+"\t"+sexo);
					esta=true;
				}
			}
			if(!esta)
				System.out.println("nonono ese nombre no lo tenemos");
		}catch(IOException ioe) {}
	}
	static void busquedaSexo(Scanner in) throws IOException {
		System.out.println("Sexo a buscar: ");
		char sexo=Character.toUpperCase((char) System.in.read());
		while(System.in.read()!='\n');
		
	}
	static void busqueda(Scanner in) {
		int opcion=0;
		do {
			opcion=menu(in);
			switch(opcion) {
			case 1:
				busquedaNombre(in);
				break;
			case 2:
				busquedaSexo(in);
				break;
			case 3:
				System.out.println();
			}
		}while(opcion!=3);
		
	}
	public static void main(String []args) {
		Scanner in= new Scanner(System.in);
		int opcion=0;
		do {
			opcion=menu(in);
			switch(opcion) {
			case 1:
				alta(in);
				break;
			case 2:
				listadoPersonas();
				break;
			case 3:
				busqueda(in);
				break;
			case 4:
				System.out.println("Fin de programa");
			}
		}while(opcion!=4);
		
		
		
	}
}
