/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexico;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java_cup.runtime.Symbol;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import sintactico.Sintactico;

/**
 *
 * @author mateo
 */
public class GUI implements ActionListener{
    
    JFrame ventana;
    JButton btCargarArchivo;
    JTextArea taProcedimiento, taLexico, taSintactico, taSemantico;
    
    File archivo = null;
    FileReader fr = null;
    BufferedReader br = null;
    ArrayList<String> procedimientoAlmacenado = new ArrayList<String>();
    
    Sintactico analizadorSintactico = new Sintactico();
    
    public String resultadoSintactico = "";
    
    public GUI() throws Exception {
        
        //Creación de la ventana
        ventana = new JFrame();
        
        //Añadir los paneles
        ventana.add(paTitulo());
        ventana.add(paAnalisis());
        ventana.add(paBotonera());
        
        //Propiedades de la ventana
        ventana.setSize(1265, 500);
        ventana.setTitle("Analizador & Compilador - Ciencias III");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);
        ventana.setLayout(null);
        ventana.setVisible(true);
        
        //Crear los analizadores
        crearAnalizadorSintactico();
        crearAnalizadorLexico();
        
    }
    
    public void generarLexer(String ruta) {
        
        File archivo = new File(ruta);
        JFlex.Main.generate(archivo);
    
    }
    
    public JPanel paTitulo(){
        //Definición del panel y sus priopiedades
        JPanel pa = new JPanel();
        pa.setBounds(0, 0, 1250, 50);
        pa.setBackground(new java.awt.Color(204, 166, 166));
        pa.setFont(new java.awt.Font("Cambria", 2, 11));
        
        //Elementos del panel
        JLabel lbTitulo = new JLabel("Analizador & Compilador - Ciencias III", SwingConstants.CENTER);
        lbTitulo.setVisible(true);
        lbTitulo.setFont(new java.awt.Font("Cambria", 0, 29));
        pa.add(lbTitulo);
        
        //Retornar el panel a la ventana
        pa.setVisible(true);
        return pa;
    }
    
    public JPanel paAnalisis(){
        //Definición del panel y sus priopiedades
        JPanel pa = new JPanel();
        pa.setBounds(0, 50, 1250, 340);
        pa.setBackground(Color.LIGHT_GRAY);
        pa.setFont(new java.awt.Font("Cambria", 2, 11));
                
        //Componentes del panel
        //Elementos del panel
        taProcedimiento = new JTextArea();
        taProcedimiento.setEditable(false);
        taProcedimiento.setFont(new java.awt.Font("Cambria", 0, 15));
        taProcedimiento.setBorder(BorderFactory.createTitledBorder("Informacion"));
        taProcedimiento.setForeground(Color.DARK_GRAY);
        taProcedimiento.setBounds(0, 0, 300, 320);
        taProcedimiento.setVisible(true);
        JScrollPane scrollProcedimiento = new JScrollPane (taProcedimiento);
        scrollProcedimiento.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollProcedimiento.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollProcedimiento.setPreferredSize(new Dimension(300,320));
        pa.add(scrollProcedimiento);
        
        taLexico = new JTextArea();
        taLexico.setEditable(false);
        taLexico.setFont(new java.awt.Font("Cambria", 0, 15));
        taLexico.setBorder(BorderFactory.createTitledBorder("Lexico"));
        taLexico.setForeground(Color.DARK_GRAY);
        taLexico.setBounds(0, 0, 300, 320);
        taLexico.setVisible(true);
        JScrollPane scrollLexico = new JScrollPane (taLexico);
        scrollLexico.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollLexico.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollLexico.setPreferredSize(new Dimension(300,320));
        pa.add(scrollLexico);
        
        taSintactico = new JTextArea();
        taSintactico.setEditable(false);
        taSintactico.setFont(new java.awt.Font("Cambria", 0, 15));
        taSintactico.setBorder(BorderFactory.createTitledBorder("Sintactico"));
        taSintactico.setForeground(Color.DARK_GRAY);
        taSintactico.setBounds(0, 0, 300, 320);
        taSintactico.setVisible(true);
        JScrollPane scrollSintactico = new JScrollPane (taSintactico);
        scrollSintactico.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollSintactico.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollSintactico.setPreferredSize(new Dimension(300,320));
        pa.add(scrollSintactico);
        
        /*taSemantico = new JTextArea();
        taSemantico.setEditable(false);
        taSemantico.setFont(new java.awt.Font("Cambria", 0, 15));
        taSemantico.setBorder(BorderFactory.createTitledBorder("Semantico"));
        taSemantico.setForeground(Color.DARK_GRAY);
        taSemantico.setBounds(0, 0, 300, 320);
        taSemantico.setVisible(true);
        JScrollPane scrollSemantico = new JScrollPane (taSemantico);
        scrollSemantico.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollSemantico.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollSemantico.setPreferredSize(new Dimension(300,320));
        pa.add(scrollSemantico);*/
        
        return pa;
    }
    
    public JPanel paBotonera(){
        //Definición del panel y sus priopiedades
        JPanel pa = new JPanel();
        pa.setBounds(0, 390, 1250, 75);
        pa.setBackground(Color.DARK_GRAY);
        pa.setFont(new java.awt.Font("Cambria", 2, 11));
        pa.setLayout(new BoxLayout(pa, BoxLayout.Y_AXIS));
        pa.add((Box.createRigidArea(new Dimension(0,10))));
                
        btCargarArchivo = new JButton("Analizar desde procedimiento.txt");
        btCargarArchivo.setAlignmentX(Component.CENTER_ALIGNMENT);
        btCargarArchivo.setFont(new java.awt.Font("Cambria", 0, 20));
        btCargarArchivo.setForeground(Color.white);
        btCargarArchivo.setBorder(new LineBorder(Color.LIGHT_GRAY));
        btCargarArchivo.setBackground(Color.GRAY);
        btCargarArchivo.setVisible(true);
        btCargarArchivo.addActionListener(this);
        
        pa.add(btCargarArchivo);
        
        JLabel lbNombres = new JLabel("Daniela A. Martinez - Mateo Yate G. - Daniel A. Roa / Ciencias III");
        lbNombres.setFont(new java.awt.Font("Cambria", 0, 20));
        lbNombres.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbNombres.setForeground(Color.white);
        pa.add(lbNombres);
        
        
        return pa;
    }
    
    public void cargarArchivo(){
        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            
            System.out.println("Se está cargando el archivo");
            archivo = new File("procedimiento.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
                procedimientoAlmacenado.add(linea);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta 
            // una excepcion.
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
    
    public void crearAnalizadorSintactico() throws Exception{
        String ruta2 = "src/lexico/LexerCup.flex";
        String[] rutaS = {"-parser", "Sintax", "src/lexico/Sintax.cup"};
        generar(ruta2, rutaS);
    }
    
    public void crearAnalizadorLexico(){
        String ruta = "src/lexico/Lexer.flex";
        generarLexer(ruta);
    }
    
    public void generar(String ruta2, String[] rutaS) throws IOException, Exception{

        File archivo;
        archivo = new File(ruta2);
        JFlex.Main.generate(archivo);
        java_cup.Main.main(rutaS);
        
        Path rutaSym = Paths.get("src/lexico/sym.java");
        
        if (Files.exists(rutaSym)) {
            Files.delete(rutaSym);
        }
        Files.move(
                Paths.get("sym.java"), 
                Paths.get("src/lexico/sym.java")
        );        
        
        Path rutaSin = Paths.get("src/lexico/Sintax.java");
        if (Files.exists(rutaSin)) {
            Files.delete(rutaSin);
        }
        Files.move(
                Paths.get("Sintax.java"), 
                Paths.get("src/lexico/Sintax.java")
        );
        
        
        
    }
    
    public void analizar(Sintax s, int linea){
        
        try {
            s.parse();
            System.out.println("Linea: " + linea + ": Analisis realizado correctamente");
            resultadoSintactico += "Analisis realizado correctamente \n";

        } catch (Exception ex) {
            Symbol sym = s.getS();
            System.out.println("Error de sintaxis. Linea: " + (linea + 1) + " Columna: " + (sym.left + 1) + ", Texto: \"" + sym.value + "\"");
            resultadoSintactico += "Error de sintaxis. Linea: " + (linea + 1) + " Columna: " + (sym.left + 1) + ", Texto: \"" + sym.value + "\"\n";

        }
        
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == btCargarArchivo) {
            System.out.println("---------- BOTON PRESIONADO ------------");
            //Limpiar los paneles y el resultado del sintactico
            taProcedimiento.removeAll();
            taLexico.removeAll();
            taSintactico.removeAll();
            //taSemantico.removeAll();
            resultadoSintactico = "";

            //Almacenar el procedimiento almacenado en un text area
            procedimientoAlmacenado = new ArrayList<String>();
            cargarArchivo();
            
            Sintactico sintax = new Sintactico();
            
            //Analizador sintactico
            for(int i=0; i < procedimientoAlmacenado.size(); i++){

                System.out.println("Linea por analizar: " + procedimientoAlmacenado.get(i) + ", numero de linea: " + i);
                sintax.analizarLinea(procedimientoAlmacenado.get(i), i);

            }
            
            taSintactico.setText(sintax.resultado);
            
            //Analizador simtactico - Jcup
            
            for(int i=0; i < procedimientoAlmacenado.size(); i++){
                System.out.println("Linea por analizar: " + procedimientoAlmacenado.get(i) + ", numero de linea: " + i);
                Sintax s = new Sintax(new lexico.LexerCup(new StringReader(procedimientoAlmacenado.get(i))));
                analizar(s,i);
            }
            
//            taSemantico.setText(resultadoSintactico);
            
            /*

            //Analizador sintáctico
            for (int i = 0; i < procedimientoAlmacenado.size(); i++) {
                System.out.println("Linea por analizar: " + procedimientoAlmacenado.get(i) + ", numero de linea: " + i);
                analizadorSintactico.analizarLinea(procedimientoAlmacenado.get(i), i);
                
            }
            
            if (procedimientoAlmacenado.contains("BEGIN")){
                if (procedimientoAlmacenado.contains("END")){
                    //do nothing
                } else {
                    JOptionPane.showMessageDialog(null,"ERROR: Se encontró la sentencia BEGIN sin END");
                    resultadoSintactico += "ERROR: Se encontró la sentencia BEGIN sin END \n";
                }
            }
            
            if (procedimientoAlmacenado.contains("END")){
                if (procedimientoAlmacenado.contains("BEGIN")){
                    //do nothing
                } else {
                    JOptionPane.showMessageDialog(null,"ERROR: Se encontró la sentencia END sin BEGIN");
                    resultadoSintactico += "ERROR: Se encontró la sentencia BEGIN sin END \n";
                }
            }
            
            //Imprimir resultado del sintactico
            taSintactico.setText(resultadoSintactico);
            */
            //Analizador léxico
            try {
                
                //Leer el archivo para el lexico
                Reader lector = new BufferedReader(new FileReader("procedimiento.txt"));
                System.out.println();
                //Inicializar el analizador lexico
                Lexer lexer = new Lexer(lector);
                
                //Limpiar variables locales
                String resultado = "";
                String procedimientoTexto = "";
                
                while (true) {

                    Tokens tokens = lexer.yylex();
                    
                    if (tokens == null) {
                        resultado += "El ultimo token fue analizado...";
                        
                        taLexico.setText(resultado);

                        for (int i = 0; i < procedimientoAlmacenado.size(); i++) {
                            procedimientoTexto += procedimientoAlmacenado.get(i) + "\n";
                        }

                        //Pintar el procedimiento almacenado en el panel de la derecha
                        taProcedimiento.setText(procedimientoTexto);

                        return;
                    }
                    
                    switch (tokens) {
                        case ERROR:
                            
                            resultado += "Simbolo no identificado...\n";
                            break;
                            
                        case Identificador: case Numero: case Reservada: case Delimitador:
                        case Statement: case Cierre: case Operador_o_Identificador: case Operador: case Arroba:
                        case Tipo: case Punto: case Coma:
                            
                            resultado += "\"" + lexer.lecturaLexica + "\" : es un " + tokens + "\n";
                            break;
                            
                        default:
                            resultado += "Token: " + tokens + "\n";
                            break;

                    }

                }

            } catch (FileNotFoundException ex) {

                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);

            } catch (IOException ex) {

                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);

            }
            
        }
    }
    
}
