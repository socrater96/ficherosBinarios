//
package ficherosBinarios;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class FicherosBinarios {
	static int menu(Scanner in) {
		int opcion=0;
		do {
			System.out.println("\tMENÚ");
			System.out.println("1.Alta");
			System.out.println("2.Listado");
			System.out.println("3.Búsqueda");
			System.out.println("4.Baja");
			System.out.println("5.Fin");
			System.out.println("Seleccione opción: (1-5)");
			try {
				opcion=Integer.parseInt(in.nextLine());
			}catch(NumberFormatException nfe) {
				System.out.println("Valor no numérico");
			}
		}while(opcion<1||opcion>4);
		return opcion;
	}
	static boolean yaEsta(String nombre) {
		boolean esta=false;
		int edad=0;
		char sexo=' ';
		String nombreB="";
		try {
			DataInputStream dis = new DataInputStream(new FileInputStream("todos.dat"));
			nombreB=dis.readUTF();
			if(nombre.equals(nombreB))
				return true;
			while(!nombreB.equals(null)) {
				edad=dis.readInt();
				sexo=dis.readChar();
				nombreB=dis.readUTF();
				if(nombre.equals(nombreB))
					return true;
			}
			dis.close();
		}catch(IOException ioe) {}
		return esta;
	}
	static void alta(Scanner in){
		Persona persona = new Persona();
		try {
			DataOutputStream esc = new DataOutputStream(new FileOutputStream("todos.dat",true));		
			do {
				System.out.println("Nombre (** para finalizar)");
				if(!persona.setNombre(in.nextLine()))
					break;
				else {
					do {
						System.out.println("Edad: ");
					}while(!persona.setEdad(in.nextLine()));
					do {
						System.out.println("Sexo: (H/M)");
					}while(!persona.setSexo(in.nextLine().toUpperCase()));
					esc.writeUTF(persona.getNombre());
					esc.writeInt(persona.getEdad());
					esc.writeChar(persona.getSexo());
				}	
			}while(true);
			esc.close();
		}catch(IOException ioe) {}
	}
	static void listadoPersonas() {
		Persona persona = new Persona();
		try {
			DataInputStream leer =new DataInputStream (new FileInputStream("todos.dat"));
			System.out.println();
			persona.setNombre(leer.readUTF());
			while(!persona.getNombre().equals(null)) {
				persona.setEdad(String.valueOf(leer.readInt()));
				persona.setSexo(String.valueOf(leer.readChar()));
				System.out.println(persona.getNombre()+"\t"+persona.getEdad()+"\t"+ persona.getSexo());
				persona.setNombre(leer.readUTF());
			}
			leer.close();
		}catch(IOException ioe) {}
		
	}
/*	private static String tabular(String nombre) {
		String tabulado="\t";
		if(nombre.length()<8) {
			tabulado="\t\t";
		}
		
		return tabulado;
	}
*/
	static int menuBusqueda(Scanner in) {
		int opcion=0;
		do {
			System.out.println("\tMENÚ DE BÚSQUEDA");
			System.out.println("1.Búsqueda por nombre");
			System.out.println("2.Búsqueda por sexo");
			System.out.println("3.Búsqueda por nombre y edad");
			System.out.println("4.Volver al menú principal");
			System.out.println("Seleccione opción: (1-4)");
			try {
				opcion=Integer.parseInt(in.nextLine());
			}catch(NumberFormatException nfe) {
				System.out.println("Valor no numérico");
			}
		}while(opcion<1||opcion>4);
		return opcion;
	}
	static void busquedaNombre(Scanner in){
		System.out.println("Nombre a buscar: ");
		String nombreBus=in.nextLine();
		boolean esta=false;
		try {
			DataInputStream dis = new DataInputStream (new FileInputStream("todos.dat"));
			while(dis.available()>0) {
				Persona persona = new Persona(dis.readUTF(),dis.readInt(),dis.readChar());
				if(nombreBus.equalsIgnoreCase(persona.getNombre())) {
					System.out.println(persona.toString());
					esta=true;
				}
			}
			if(!esta)
				System.out.println("No hay nadie con ese nombre");
			dis.close();
		}catch(IOException ioe) {}
	}
	static void busquedaSexo(Scanner in) {
		System.out.println("Sexo a buscar: ");
		char sexoBus=in.nextLine().charAt(0);
		boolean esta=false;
		try {
			DataInputStream dis = new DataInputStream (new FileInputStream("todos.dat"));
			while(dis.available()>0) {
				Persona persona = new Persona(dis.readUTF(),dis.readInt(),dis.readChar());
				if(sexoBus==persona.getSexo()) {
					System.out.println(persona.toString());
					esta=true;
				}
			}
			if(!esta)
				System.out.println("No hay nadie con ese sexo");
			dis.close();
		}catch(IOException ioe) {}
	}
	static void busquedaNombreEdad (Scanner in) {
		System.out.println("Sexo a buscar: ");
		char sexoBus=in.nextLine().charAt(0);
		boolean esta=false;
		try {
			DataInputStream dis = new DataInputStream (new FileInputStream("todos.dat"));
			while(dis.available()>0) {
				Persona persona = new Persona(dis.readUTF(),dis.readInt(),dis.readChar());
				if(sexoBus==persona.getSexo()) {
					System.out.println(persona.toString());
					esta=true;
				}
			}
			if(!esta)
				System.out.println("No hay nadie con ese sexo");
			dis.close();
		}catch(IOException ioe) {}
	}
	
	static void busqueda(Scanner in) {
		int opcion=0;
		do {
			opcion=menuBusqueda(in);
			switch(opcion) {
			case 1:
				busquedaNombre(in);
				break;
			case 2:
				busquedaSexo(in);
				break;
			case 3:
				busquedaNombreEdad(in);
				break;
			case 4:
				System.out.println();
			}
		}while(opcion!=4);
		
	}
	static void baja(Scanner in) {
		String nombre="";
		System.out.println("Nombre a borrar: ");
		String nombreBorrar=in.nextLine();
		boolean borrado=false;
		try {
            // Archivo original y archivo temporal
            File archivoOriginal = new File("todos.dat");
            File archivoTemporal = new File("todosAux.dat");

            // Entrada de datos desde el archivo original y salida de datos al archivo temporal
            DataInputStream dis = new DataInputStream(new FileInputStream(archivoOriginal));
            DataOutputStream dos = new DataOutputStream(new FileOutputStream(archivoTemporal));

            // Leer datos del archivo original y escribir al archivo temporal, excluyendo el usuario a eliminar
            while (dis.available() > 0) {
                nombre = dis.readUTF();
                int edad = dis.readInt();
                char sexo = dis.readChar();
                
                // Si el nombre no coincide con el nombre a borrar, escribir al archivo temporal
                if (!nombre.equals(nombreBorrar) || borrado) {
                    dos.writeUTF(nombre);
                    dos.writeInt(edad);
                    dos.writeChar(sexo);
                    borrado=true;
                }
            }

            // Cerrar flujos de entrada y salida
            dis.close();
            dos.close();

            // Eliminar el archivo original
            if (archivoOriginal.delete()) {
                System.out.println("Usuario dado de baja con éxito.");
            } else {
                System.out.println("Error al dar de baja al usuario.");
                return;
            }

            // Renombrar el archivo temporal al nombre original
            if (archivoTemporal.renameTo(archivoOriginal)) {
                System.out.println("Archivo actualizado con éxito.");
            } else {
                System.out.println("Error al actualizar el archivo.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
					baja(in);
					break;
				case 5:
					System.out.println("Fin de programa");
			}
		}while(opcion!=5);
		
		
		
	}
}
