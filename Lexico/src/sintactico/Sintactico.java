/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sintactico;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author mateo
 */
public class Sintactico {
    private int numeroColumnasActual;
    private String lineaActual;
    public String resultado = "";
    int columna = 0;
    
    public void analizarLinea(String linea, int numeroDeLinea) {
        
        System.out.println("Metodo de analizar linea del Sintactico");
        
        columna = 0;
        
        if (linea.length() != 0) {
            
            System.out.println("El campo de texto no está vacio.");
            lineaActual = "Linea " + Integer.toString(numeroDeLinea) + ": ";
            String[] cadenaSeparada = linea.split(" ");

            for (int i = 0; i < cadenaSeparada.length; i++) {
                System.out.println(cadenaSeparada[i]);
            }

            String opc = cadenaSeparada[0];
            
            if (cadenaSeparada.length > 1 && cadenaSeparada[1].equals("PROCEDURE")){
                opc += " " + cadenaSeparada[1];
            }

            switch (opc) {
                case "INSERT":
                    columna = columna + opc.length() + 1;
                    insert(cadenaSeparada);
                    break;
                case "DELETE":
                    columna = columna + opc.length() + 1;
                    delete(cadenaSeparada);
                    break;
                case "UPDATE":
                    columna = columna + opc.length() + 1;
                    update(cadenaSeparada);
                    break;
                case "SELECT":
                    columna = columna + opc.length() + 1;
                    select(cadenaSeparada);
                    break;
                case "CREATE":
                    columna = columna + opc.length() + 1;
                    create(cadenaSeparada);
                    break;
                case "CREATE PROCEDURE":
                    columna = columna + opc.length() + 1;
                    createProcedure(cadenaSeparada);
                    break;
                case "BEGIN":
                    resultado += lineaActual + " La expresion es valida \n";
                    break;
                case "END":
                    resultado += lineaActual + " La expresion es valida \n";
                    break;
                default:
                    resultado += lineaActual + " Columna: " + columna + " '" + opc + "'" +  ", Expresión errada: La primera palabra no es un statement \n";
            }

        } else {

            resultado += "Linea " + Integer.toString(numeroDeLinea) + ": " + "La linea está vacia \n";

        }
    }

    public void createProcedure(String[] cadenaSeparada){
        boolean error = false;
        int i = 0;
        if(cadenaSeparada.length > 2 && verificarNombre(cadenaSeparada[2])) {
            columna = columna = columna + cadenaSeparada[2].length() + 1;
            if (cadenaSeparada.length > 3) {           
                if(cadenaSeparada[3].startsWith("@")) {
                    columna = columna = columna + cadenaSeparada[3].length() + 1;
                    if(cadenaSeparada.length > 4) {
                        do{                      
                            if(i == 0) {
                                i= 4;                                                                                 
                            } 
                            error = !verficarTipos(cadenaSeparada[i]);

                            i = i+2; 

                        }while(!error && i < cadenaSeparada.length);
                        if(error) {
                            
                            resultado += lineaActual + " Columna: " + columna + " '" + cadenaSeparada[3] + "'" +  ", Expresión errada: Hay algun parametro que no le estas asignando el tipo correcto \n";
            
                        } else {
                            resultado += lineaActual + " La expresion es valida \n";
                        }

                    }
                } else {
                    resultado += lineaActual + " Columna: " + columna + " '" + cadenaSeparada[3] + "'" +  ", No contiene el token @ para referenciar el/los parametros \n";
                }
            }                
        } else {

            resultado += lineaActual + " Columna: " + columna + " '" + cadenaSeparada[2] + "'" +  ", El nombre de la funcion contiene nombres reservados \n";
                
        }
       
    }
    public void select(String[] cadenaSeparada) {
        String valorTabla;
        int valorFrom = 0;
        int j = 0;
        Boolean error = false;
        if (cadenaSeparada.length > 1 && cadenaSeparada[0].equals("SELECT")) {
            System.out.println(lineaActual + "El nombre de la sentencia es correcto y hay algo después");
            for (int i = 1; i < cadenaSeparada.length; i++) {
                if (cadenaSeparada[i].equals("FROM")) {
                    valorFrom = i;
                }
            }
            if (cadenaSeparada[valorFrom].equals("FROM") && cadenaSeparada.length > valorFrom) {
                
                columna = columna = columna + cadenaSeparada[valorFrom].length() + 1;
                
                if (cadenaSeparada.length > valorFrom + 1 && verificarNombre(cadenaSeparada[valorFrom + 1])) {
                    
                    columna = columna = columna + cadenaSeparada[valorFrom + 1].length() + 1;
                    
                    valorTabla = cadenaSeparada[valorFrom + 1];
                    do{
                        if(j == 0) {
                            j = 1;
                        }
                        System.out.println("el valor del campo "+ cadenaSeparada[j]);
                        error = !verificarCamposTabla(cadenaSeparada[j], valorTabla);
                        j++;
                    }while(!error && j<valorFrom);
                    
                    if (!error || cadenaSeparada[valorFrom - 1].equals("*")) {

                        columna = columna = columna + cadenaSeparada[valorFrom].length() + 1;
                        
                        if (cadenaSeparada.length > valorFrom + 2) {
                            if (cadenaSeparada[valorFrom + 2].equals("WHERE")) {
                                
                                columna = columna = columna + cadenaSeparada[valorFrom + 2].length() + 1;
                                
                                if (cadenaSeparada.length > valorFrom + 3) {
                                    if (verificarCamposTabla(cadenaSeparada[valorFrom + 3], valorTabla)) {
                                        
                                        columna = columna = columna + cadenaSeparada[valorFrom + 3].length() + 1;
                                        
                                        if (cadenaSeparada.length > valorFrom + 4 && cadenaSeparada[valorFrom + 4].equals("=")) {
                                            
                                            columna = columna = columna + cadenaSeparada[valorFrom + 4].length() + 1;
                                            
                                            if (cadenaSeparada.length > valorFrom + 5 && verificarNombre(cadenaSeparada[valorFrom + 5])) {
                                                                                            
                                                resultado += lineaActual + " La expresion es valida \n";
                                                
                                            } else {
                                                resultado += lineaActual + " Columna: " + columna + " '" + cadenaSeparada[valorFrom + 5] + "'" + ", Falta valor de condicion \n";             
                                            }
                                        } else {

                                            resultado += lineaActual + " Columna: " + columna + " '" + cadenaSeparada[valorFrom + 4] + "'" + ", Falta operador de condicion \n";

                                        }
                                    } else {
                                        
                                        resultado += lineaActual + " Columna: " + columna + " '" + cadenaSeparada[valorFrom + 3] + "'" + ", No hay referencia a la tabla a consultar \n";

                                    }

                                } else {
                                    resultado += lineaActual + " Columna: " + columna + " '" + cadenaSeparada[valorFrom + 2] + "'" + ", Falta condicion \n";

                                }
                            } else {
                                resultado += lineaActual + " Columna: " + columna + " '" + cadenaSeparada[valorFrom + 2] + "'" + ", Sentencia erronea..¿Estas intentado escribir WHERE? \n";
                            }
                        } else {
                            resultado += lineaActual + " La expresion es valida \n";
                        }
                    } else {
                        resultado += lineaActual + " Columna: " + columna + " '" + cadenaSeparada[valorFrom - 1] + "'" + ", Mala sintaxis de columnas requeridas \n";
                            
                    }
                } else {
                    resultado += lineaActual + " Columna: " + columna + " '" + cadenaSeparada[valorFrom + 1] + "'" + ", Sentencia reservada \n";
                        
                }
            }

        } else {

            resultado += lineaActual + " Columna: " + columna + " '" + cadenaSeparada[0] + "'" + ", Solo hay una palabra reservada, continue con la expresion \n";

        }
    }
    
    public void create(String[] cadenaSeparada) {
        boolean error = false;
        int i = 0;
        if (cadenaSeparada.length > 1 && cadenaSeparada[0].equals("CREATE")) {
            if (cadenaSeparada[1].equals("TABLE")) {
                columna = columna = columna + cadenaSeparada[1].length() + 1;
                if (cadenaSeparada.length > 2 && verificarNombre(cadenaSeparada[2])) {
                    columna = columna = columna + cadenaSeparada[2].length() + 1;
                    if (cadenaSeparada.length > 3 && cadenaSeparada[3].startsWith("{")) {
                        
                        do{                      
                            if(i == 0) {
                                i= 3;                                                                                 
                            } 
                            error = !verficarTipos(cadenaSeparada[i]);

                            i = i+2; 

                        }while(!error && i < cadenaSeparada.length);
                        if(error) {
                            resultado += lineaActual + " Columna: " + columna + " '" + cadenaSeparada[3] + "'" + ", Expresión errada: Hay algun parametro que no le estas asignando el tipo correcto \n";

                        } else {
                            resultado += lineaActual + " La expresion es valida \n";
                        }
                        
                    } else {
                        resultado += lineaActual + " Columna: " + columna + " '" + cadenaSeparada[3] + "'" + ", No ha declarado bien los parametros \n";

                    }

                } else {
                    resultado += lineaActual + " Columna: " + columna + " '" + cadenaSeparada[2] + "'" + ", Palabra reservada \n";

                }
            } else {

                resultado += lineaActual + " Columna: " + columna + " '" + cadenaSeparada[1] + "'" + ", No contiene la palabra TABLE \n";

            }
        } else {
            resultado += lineaActual + " Columna: " + columna + " '" + cadenaSeparada[0] + "'" + ", Solo hay una palabra reservada, continue con la expresion \n";

        }
    }
    
    public boolean verficarTipos(String tipo) {
        tipo = tipo.toUpperCase();
        Pattern pat = Pattern.compile("BIT|TINYINT|SMALLINT|BIGINT|DECIMAL|NUMERIC|FLOAT|REAL|DATE|TIME|DATETIME|TIMESTAMP|YEAR|CHAR|VARCHAR|TEXT|NCHAR|NVARCHAR|NTEXT|BINARY|VARBINARY|IMAGE|CLOB|JSON");
        Matcher mat = pat.matcher(tipo);
        
        if(mat.find()) {
            System.out.println("entro");
            return true;
        } else {
                        System.out.println("entro falso");
            return false;
        }
        
    }

    public void update(String[] cadenaSeparada) {
        if (verificarNombre(cadenaSeparada[1]) && cadenaSeparada.length > 2) {
            columna = columna = columna + cadenaSeparada[1].length() + 1;
            System.out.println(lineaActual +"El nombre de la tabla es correcto y hay algo después");

            if (cadenaSeparada[2].equals("SET") && cadenaSeparada.length > 3) {
                columna = columna = columna + cadenaSeparada[2].length() + 1;
                System.out.println(lineaActual +"La expresión contiene SET y hay algo después");

                if (verificarIngresos(cadenaSeparada[3]) && cadenaSeparada.length > 4) {
                    columna = columna = columna + cadenaSeparada[3].length() + 1;
                    System.out.println(lineaActual +"Los ingresos estan bien escritos");

                    if (cadenaSeparada[4].equals("WHERE") && cadenaSeparada.length > 4) {
                        columna = columna = columna + cadenaSeparada[4].length() + 1;
                        System.out.println(lineaActual +"La expresión contiene WHERE y hay algo después");

                        if (cadenaSeparada.length > 6) {
                            cadenaSeparada[5] = cadenaSeparada[5] + cadenaSeparada[6];
                            System.out.println(lineaActual +"Concatene YEI");
                        }

                        if (verificarCondicion(cadenaSeparada[5]) && cadenaSeparada[5].contains(";")) {
                            columna = columna = columna + cadenaSeparada[5].length() + 1;
                            System.out.println(lineaActual +"La expresion es valida");
                            resultado += lineaActual + " La expresion es valida \n";
                        } else {

                            resultado += lineaActual + " Columna: " + columna + " '" + cadenaSeparada[5] + "'" + ", La condicion NO ES VALIDA \n";

                        }

                    } else {
                        
                        resultado += lineaActual + " Columna: " + columna + " '" + cadenaSeparada[4] + "'" + ", La expresion no contiene WHERE o no tiene nada despues de este \n";

                        }

                } else if(verificarIngresos(cadenaSeparada[3]) && cadenaSeparada.length == 4 && cadenaSeparada[3].contains(";")){
                
                    System.out.println(lineaActual +"La expresion es valida");
                    resultado += lineaActual + " La expresion es valida \n";
                    
                } else {                    
                    resultado += lineaActual + " Columna: " + columna + " '" + cadenaSeparada[3] + "'" + ", Expresion invalida por condiciones \n";
                }

            } else {
                resultado += lineaActual + " Columna: " + columna + " '" + cadenaSeparada[2] + "'" + ", Expresion sin un SET \n";   
            }
        } else {
            resultado += lineaActual + " Columna: " + columna + " '" + cadenaSeparada[1] + "'" + ", Expresion errada en el nombre de la tabla \n";             
        }
    }

    public void insert(String[] cadenaSeparada) {
        
        if (cadenaSeparada[1].equals("INTO") && cadenaSeparada.length > 2) {
            System.out.println(lineaActual +"La expresión contiene INTO y hay algo después");
            columna = columna = columna + cadenaSeparada[1].length() + 1;
            
            if (verificarNombre(cadenaSeparada[2]) && cadenaSeparada.length > 3) {
                System.out.println(lineaActual +"El nombre de la tabla es correcto y hay algo después");
                columna = columna = columna + cadenaSeparada[2].length() + 1;
                
                if (verificarColumnas(cadenaSeparada[3]) && cadenaSeparada.length > 4 && !cadenaSeparada[3].endsWith("VALUES")) {
                    System.out.println(lineaActual +"Las columnas son correctas y hay algo después");
                    columna = columna = columna + cadenaSeparada[3].length() + 1;
                    
                    if (cadenaSeparada[4].equals("VALUES") && cadenaSeparada.length > 5) {
                        System.out.println(lineaActual +"La expresión contiene VALUES y hay algo después");
                        columna = columna = columna + cadenaSeparada[4].length() + 1;
                        
                        if (verificarValores(cadenaSeparada[5]) && cadenaSeparada[5].contains(";")) {
                            System.out.println(lineaActual +"La expresion es valida");
                            columna = columna = columna + cadenaSeparada[5].length() + 1;
                            resultado += lineaActual + " La expresion es valida \n";
                        } else {
                            resultado += lineaActual + " Columna: " + columna + " '" + cadenaSeparada[5] + "'" + ", Expresion errada en la expresion de los valores a insertar \n";
                        }

                    } else {
                        
                        resultado += lineaActual + " Columna: " + columna + " '" + cadenaSeparada[4] + "'" + ", Expresion errada en la expresion VALUES \n";
                        
                    }

                } else if (cadenaSeparada[3].endsWith("VALUES") && cadenaSeparada.length > 4) {
                    
                    if (verificarValores(cadenaSeparada[4])) {
                            System.out.println(lineaActual +"La expresion es valida");
                            columna = columna = columna + cadenaSeparada[4].length() + 1;
                            resultado += lineaActual + " La expresion es valida \n";
                        } else {
                            resultado += lineaActual + " Columna: " + columna + " '" + cadenaSeparada[5] + "'" + ", Expresion errada en la expresion de los valores a insertar \n";
                        }
                    
                } else {
                    resultado += lineaActual + " Columna: " + columna + " '" + cadenaSeparada[3] + "'" + ", Expresion errada en las columnas de la table \n";
                }

            } else {
                resultado += lineaActual + " Columna: " + columna + " '" + cadenaSeparada[2] + "'" + ", Expresion errada en el nombre de la tabla \n";
            }
        } else {
            resultado += lineaActual + " Columna: " + columna + " '" + cadenaSeparada[1] + "'" + ", Expresion errada en la expresion INTO \n";
        }
    }

    public void delete(String[] cadenaSeparada) {
        if (cadenaSeparada[1].equals("FROM") && cadenaSeparada.length > 2) {
            System.out.println(lineaActual +"La expresión contiene FROM y hay algo después");
            columna = columna = columna + cadenaSeparada[1].length() + 1;
            
            if (verificarNombre(cadenaSeparada[2]) && cadenaSeparada.length > 3) {
                System.out.println(lineaActual +"Hay algo más, la expresion no para aca");
                columna = columna = columna + cadenaSeparada[2].length() + 1;
                
                if (cadenaSeparada[3].equals("WHERE") && cadenaSeparada.length > 4) {
                    System.out.println(lineaActual +"La expresión contiene WHERE y hay algo después");
                    columna = columna = columna + cadenaSeparada[3].length() + 1;
                    
                    if (cadenaSeparada.length > 5) {
                        cadenaSeparada[4] = cadenaSeparada[4] + cadenaSeparada[5];
                        System.out.println(lineaActual +"Concatene YEI");
                    }

                    if (verificarCondicion(cadenaSeparada[4]) && cadenaSeparada[4].contains(";")) {
                        System.out.println(lineaActual +"La expresion es valida");
                        columna = columna = columna + cadenaSeparada[4].length() + 1;
                        resultado += lineaActual + " La expresion es valida \n";
                    } else {
                        resultado += lineaActual + " Columna: " + columna + " '" + cadenaSeparada[4] + "'" + ", La condicion NO ES VALIDA \n";
                    }

                } else {
                    resultado += lineaActual + " Columna: " + columna + " '" + cadenaSeparada[3] + "'" + ", La expresion no contiene WHERE o no tiene nada despues de este \n";

                }

            } else if (verificarDeleteCorto(cadenaSeparada[2])) {
                resultado += lineaActual + " La expresion es valida \n";
            } else {

                resultado += lineaActual + " Columna: " + columna + " '" + cadenaSeparada[2] + "'" + ", El nombre de la tabla no es valido o faltan mas datos... \n";

            }

        } else {
            
            resultado += lineaActual + " Columna: " + columna + " '" + cadenaSeparada[1] + "'" + ", Expresion errada en la expresion FROM \n";

        }
    }

    public boolean verificarIngresos(String tbSet) {
        String[] separaComas = tbSet.split(",");

        for (int i = 0; i < separaComas.length; i++) {
            return verificarSeteada(separaComas[i]);
        }

        return true;

    }

    public boolean verificarSeteada(String tbCondicion) {
        System.out.println(lineaActual +"Condicion a verificar: " + tbCondicion);
        System.out.println("");
        Pattern pat = Pattern.compile("^[^\\d]*.*=[\"']*.*[\"']*");
        Matcher mat = pat.matcher(tbCondicion);

        if (mat.matches()) {
            System.out.println(lineaActual +"La estructura de la condicion es correcta");

            pat = Pattern.compile("UPDATE|CREATE|SELECT|INSERT|DELETE|"
                    + "\\(|\\)|/|&|#|>|<|\\{|\\}");
            mat = pat.matcher(tbCondicion);

            if (!mat.find()) {
                System.out.println(lineaActual +tbCondicion + ": No contiene palabras reservadas");
                return true;

            } else {
                System.out.println(lineaActual +"ERROR: " + tbCondicion + " contiene palabras reservadas");
                return false;
            }

        } else {
            System.out.println(lineaActual +"La estructura de la condicion no es correcta");
            return false;
        }
    }

    public boolean verificarCamposTabla(String valores, String tabla) {
        
        String[] columnas = new String[]{};
        columnas = valores.split("\\,");
        String[] nombreTabla = columnas;
        Boolean error = false;
        int j = 0;
        for (int i = 0; i < columnas.length; i++) {
            nombreTabla[i] = columnas[i].split("\\.")[0];
        }

        if (nombreTabla.length < columnas.length) {
            System.out.println("error 1");
            return false;
        } else {
            do {
                System.out.println("este es el nombre de la tabla del campo 1 "+nombreTabla[j]);
                if (!nombreTabla[j].equals(tabla)) {
                    error = true;
                }
                j++;
            }while(!error && j<nombreTabla.length);
            
            System.out.println(error);
            return !error;
        }

    }

    public boolean verificarCondicion(String tbCondicion) {
        System.out.println(lineaActual +"Condicion a verificar: " + tbCondicion);
        System.out.println("");
        Pattern pat = Pattern.compile("^[^\\d].*=[\"']*.*[\"']*;$");
        Matcher mat = pat.matcher(tbCondicion);

        if (mat.matches()) {
            System.out.println(lineaActual +"La estructura de la condicion es correcta");

            pat = Pattern.compile("UPDATE|CREATE|SELECT|INSERT|DELETE|"
                    + "\\(|\\)|/|&|#|>|<|\\{|\\}");
            mat = pat.matcher(tbCondicion);

            if (!mat.find()) {
                System.out.println(lineaActual +tbCondicion + ": No contiene palabras reservadas");
                return true;

            } else {
                System.out.println(lineaActual +"ERROR: " + tbCondicion + " contiene palabras reservadas");
                return false;
            }

        } else {
            System.out.println(lineaActual +"La estructura de la condicion no es correcta");
            return false;
        }
    }

    public boolean verificarDeleteCorto(String tbName) {

        //Verifica que el nombre no contenta palabras reservadas
        Pattern pat = Pattern.compile("UPDATE|CREATE|SELECT|INSERT|DELETE|"
                + "\\(|\\)|\\+|\\-|/|\\*|\\\"|=|&|#|>|<|\\^|'|\\{|\\}|%");
        //Pattern pat = Pattern.compile("^[^\\d]{0,}.[^INSERT,CREATE,DELETE,if,else,for].");
        Matcher mat = pat.matcher(tbName);
        //Esta bien
        if (!mat.find()) {
            System.out.println(lineaActual +tbName + ": No contiene palabras reservadas");
            pat = Pattern.compile("^[^\\d]*.*[;]$");
            mat = pat.matcher(tbName);
            if (mat.matches()) {
                System.out.println(lineaActual +tbName + ": No empieza por un digito y termina en punto y coma");
                return true;

            } else {
                System.out.println(lineaActual +tbName + ": Empieza por un digito o no tiene punto y coma final");
                return false;
            }

        } else {
            System.out.println(lineaActual +"ERROR: " + tbName + " contiene palabras reservadas");
            return false;
        }

    }

    public boolean verificarNombre(String tbName) {

        //Verifica que el nombre no contenta palabras reservadas
        Pattern pat = Pattern.compile("UPDATE|CREATE|SELECT|INSERT|DELETE|"
                + "\\(|\\)|\\+|\\-|/|\\*|\\\"|=|&|#|>|<|\\^|'|\\{|\\}|%");
        //Pattern pat = Pattern.compile("^[^\\d]{0,}.[^INSERT,CREATE,DELETE,if,else,for].");
        Matcher mat = pat.matcher(tbName);
        //Esta bien
        if (!mat.find()) {
            System.out.println(lineaActual +tbName + ": No contiene palabras reservadas");
            pat = Pattern.compile("^[^\\d]*.*");
            mat = pat.matcher(tbName);
            if (mat.matches()) {
                System.out.println(lineaActual +tbName + ": No empieza por un digito");
                return true;

            } else {
                System.out.println(lineaActual +tbName + ": Empieza por un digito");
                return false;
            }

        } else {
            System.out.println(lineaActual +"ERROR: " + tbName + " contiene palabras reservadas o contiene caracteres especiales");
            return false;
        }

    }

    public boolean verificarValores(String tbValores) {
        Pattern pat = Pattern.compile("^[(]*.*[^,]+[)][;]$");
        Matcher mat = pat.matcher(tbValores);

        tbValores = tbValores.replaceAll("\\(", "");
        tbValores = tbValores.replaceAll("\\)", "");
        String[] separaComas = tbValores.split(",");

        if (separaComas.length == numeroColumnasActual) {
            System.out.println(lineaActual +"El numero de columnas de los valores coincide...");

            pat = Pattern.compile("UPDATE|CREATE|SELECT|INSERT|DELETE|"
                    + "\\(|\\)|/|\\\"|&|#|>|<|\\{|\\}");
            mat = pat.matcher(tbValores);

            if (!mat.find()) {
                System.out.println(lineaActual +tbValores + ": No contiene palabras reservadas");
                return true;

            } else {
                System.out.println(lineaActual +"ERROR: " + tbValores + " contiene palabras reservadas");
                return false;
            }

        } else {
            System.out.println(lineaActual +"NO coinciden el numero de valores con el numero de columnas");
            return false;
        }

    }

    public boolean verificarColumnas(String tbColumnas) {
        Pattern pat = Pattern.compile("^[(]*.*[^,]+[)]$");
        Matcher mat = pat.matcher(tbColumnas);
        //Esta bien
        if (mat.matches()) {
            System.out.println(lineaActual +"La estructura de parentesis de columnas esta bien");
            tbColumnas = tbColumnas.replaceAll("\\(", "");
            tbColumnas = tbColumnas.replaceAll("\\)", "");
            String[] separaComas = tbColumnas.split(",");

            for (int i = 0; i < separaComas.length; i++) {
                if (verificarNombre(separaComas[i])) {
                    System.out.println(lineaActual +"Columa " + i + " esta bien");
                    //do nothing
                } else {
                    System.out.println(lineaActual +"Error: La columna " + i + " tiene un formato invalido");
                    return false;
                }
            }

            if (separaComas.length == 1 && separaComas[0].equals("")) {

                numeroColumnasActual = 0;
            } else {

                numeroColumnasActual = separaComas.length;
            }

            return true;
        } else {
            System.out.println(lineaActual +"Error: la estructura de parentesis de columnas esta mal ");
            return false;
        }
    }
    
}
