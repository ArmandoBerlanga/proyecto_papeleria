package complementos;

import java.util.Scanner;

public class Lectura {

    public static String inputString(String pregunta) // Lectura de Strings con espacios sin salto de lineas
    {
        Scanner s = new Scanner(System.in);
        System.out.println("\n" + pregunta);

        return s.nextLine();
    }

    public static String inputNombre(String pregunta) // Lectura de objetos tipo Nombre
    {
        boolean valido = false;
        String str = "";
        Scanner s = new Scanner(System.in);

        do {
            System.out.println("\n" + pregunta);

            str = s.nextLine();

            if (str.split(" ").length != 3) {
                System.out.println(
                        "\nPor favor ingresa un nombre válido en el siguiente formato: primer_nombre apellido_paterno apellido_materno");
            } else
                valido = true;
        } while (!valido);

        return str;
    }

    public static long inputLong(String pregunta) // Lectura de doubles validados a errores del usuario
    {
        Scanner s = new Scanner(System.in);

        boolean salida = false;
        long num = 0;
        do {

            try {

                System.out.println("\n" + pregunta);
                num = Long.parseLong(s.next());
                salida = false;

            } catch (NumberFormatException e) {
                System.out.println("\nNo has ingresado un número válido");
                salida = true;
            }

        } while (salida);

        return num;
    }

    public static byte inputByte(String pregunta) // Lectura de bytes validados a errores del usuario
    {
        Scanner s = new Scanner(System.in);

        boolean salida = false;
        byte num = 0;
        do {

            try {

                System.out.println("\n" + pregunta);
                num = Byte.parseByte(s.next());
                salida = false;

            } catch (NumberFormatException e) {
                System.out.println("\nNo has ingresado un número válido");
                salida = true;
            }

        } while (salida);

        return num;
    }

    public static Double inputDouble(String pregunta) // Lectura de doubles validados a errores del usuario
    {
        Scanner s = new Scanner(System.in);

        boolean salida = false;
        double num = 0;
        do {

            try {

                System.out.println("\n" + pregunta);
                num = Double.parseDouble(s.next());
                salida = false;

            } catch (NumberFormatException e) {
                System.out.println("\nNo has ingresado un número válido");
                salida = true;
            }

        } while (salida);

        return num;
    }

    public static int inputInteger(String pregunta) // Lectura de integeres validado a errores del usuario
    {
        Scanner s = new Scanner(System.in);

        boolean salida = false;
        int num = 0;
        do {

            try {

                System.out.println("\n" + pregunta);
                num = Integer.parseInt(s.next());
                salida = false;

            } catch (NumberFormatException e) {
                System.out.println("\nNo has ingresado un número válido");
                salida = true;
            }

        } while (salida);

        return num;
    }

    public static int inputInteger(String pregunta, String comparador, int numComparado) // "" pero con validacion de
    // comparados y mayor a 0
    {
        Scanner s = new Scanner(System.in);

        boolean salida = false;
        int num = 0;
        do {

            try {

                System.out.println("\n" + pregunta);
                num = Integer.parseInt(s.next());
                salida = false;

                boolean expresionComparadora = true;
                switch (comparador) {
                    case "=":
                        expresionComparadora = (num == numComparado);
                        break;
                    case ">":
                        expresionComparadora = (num > numComparado);
                        break;
                    case "<":
                        expresionComparadora = (num < numComparado);
                        break;
                    case ">=":
                        expresionComparadora = (num >= numComparado);
                        break;
                    case "<=":
                        expresionComparadora = (num <= numComparado);
                        break;
                    default:
                        expresionComparadora = true;
                }

                if (expresionComparadora && num > 0)
                    salida = false;
                else {
                    System.out.println("\nNo has ingresado un número válido, vuelve a intentarlo");
                    salida = true;
                }

            } catch (NumberFormatException e) {
                System.out.println("\nNo has ingresado un número válido");
                salida = true;
            }

        } while (salida);

        return num;
    }

    public static byte inputByteOpcion(String mensaje) {
        byte variable = 0;
        boolean valid = false;

        while (!valid) {
                variable = inputByte(mensaje);
                if (variable == 1 || variable == 2)
                    valid = true;
                else
                    System.out.println("\nDebes ingresar solamente 1 o 2");
        }
        return variable;
    }

    public static char inputCharLowercase(String mensaje, char[] validos) {
        Scanner s = new Scanner(System.in);
        char variable = ' ';
        boolean valid = false;
        int cont = 0;
        String charsValidos = "";

        while (!valid) {
            charsValidos = "";

            System.out.println("\n" + mensaje);
            variable = s.next().toLowerCase().charAt(0);

            for (int i = 0; i < validos.length; i++) {
                charsValidos += Character.toString(validos[i]).toUpperCase() + " ";
                if (Character.toString(validos[i]).equalsIgnoreCase(Character.toString(variable)))
                    cont++;
            }

            if (cont > 0)
                valid = true;
            else
                System.out.println("\nDebes ingresar solamente uno de los siguientes caracteres: " + charsValidos);

        }
        return variable;
    }

    public static String inputMail(String mensaje) {
        boolean valid = false;
        String str = "";

        while (!valid) {
            Scanner s = new Scanner(System.in);
            System.out.println("\n" + mensaje);
            str = s.nextLine();

            if (str.contains("@") && str.contains("."))
                valid = true;
            else
                System.out.println("Debes ingresar una dirección de correo válida");
        }

        return str;
    }

    public static long inputTelefono(String mensaje) {
        Scanner s = new Scanner(System.in);
        boolean salida = false;
        long num = 0L;

        do {

            try {

                System.out.println("\n" + mensaje);
                num = Long.parseLong(s.next());
                salida = false;

                if (Long.toString(num).length() != 10) {
                    System.out.println("Debes ingresar un teléfono válido");
                    salida = true;
                }

            } catch (NumberFormatException e) {
                System.out.println("\nNo has ingresado un número válido");
                salida = true;
            }

        } while (salida);

        return num;
    }
}