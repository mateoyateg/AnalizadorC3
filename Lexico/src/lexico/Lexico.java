
package lexico;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import sintactico.*;

public class Lexico extends JFrame implements ActionListener{

    JButton botonAnalisis = new JButton("Analizar desde procedimiento.txt");
    JTextField campoIngreso = new JTextField();
    JScrollPane panel = new JScrollPane();
    JScrollPane panelInterno = new JScrollPane();
    
    JScrollPane panelProcedimiento = new JScrollPane();
    JScrollPane panelProcedimientoInterno = new JScrollPane();
    JTextArea areaProcedimiento = new JTextArea();
    
    JTextArea areaSalida = new JTextArea();
    JLabel lbNombres = new JLabel("Analizador Lexico y Sintactico / Daniela A. Martinez - Mateo Yate G. - Daniel A. Roa / Ciencias III");
    
    File archivo = null;
    FileReader fr = null;
    BufferedReader br = null;
    ArrayList<String> procedimientoAlmacenado = new ArrayList<String>();
    
    Sintactico analizadorSintactico = new Sintactico();
    
    public static void main(String[] args) {
        
        String ruta = "src/lexico/Lexer.flex";
        generarLexer(ruta);
        
        Lexico lex = new Lexico();
        lex.setBounds(0, 0, 970, 500);
        lex.setTitle("Analizador Léxico, Sintáctico y Semantico de Procedimientos Almacenados SQL - Ciencias III");
        lex.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        lex.setVisible(true);   
        
    }

    public static void generarLexer(String ruta) {
        
        File archivo = new File(ruta);
        JFlex.Main.generate(archivo);
    
    }
    
    public Lexico(){
        
        Container c = getContentPane();
        c.setLayout(null);
        this.getContentPane().setBackground(Color.LIGHT_GRAY);
        
        c.add(botonAnalisis);
       // c.add(campoIngreso);
        c.add(panel);
        c.add(panelProcedimiento);
        //c.add(panelProcedimientoInterno);
        c.add(areaSalida);
        c.add(areaProcedimiento);
        c.add(lbNombres);
        
        botonAnalisis.addActionListener(this);
        botonAnalisis.setBounds(280, 30, 400, 30);
        botonAnalisis.setBackground(Color.GRAY);
        
        //campoIngreso.setBounds(50, 25, 250, 30);
        
        lbNombres.setBounds(220, 5, 700, 20);
        
        panelInterno.setBounds(0, 250, 400, 2000);
        panelInterno.setPreferredSize(new Dimension(400, 2000));  
        panelInterno.setBackground(Color.LIGHT_GRAY);
        
        panel.setBounds(50, 70, 400, 350);
        panel.setPreferredSize(new Dimension(400, 350));
        panel.setBackground(Color.LIGHT_GRAY);
        
        panelProcedimientoInterno.setBounds(0, 250, 400, 2000);
        panelProcedimientoInterno.setPreferredSize(new Dimension(400, 2000));  
        panelProcedimientoInterno.setBackground(Color.LIGHT_GRAY);
       
        panelProcedimiento.setBounds(500, 70, 400, 350);
        panelProcedimiento.setPreferredSize(new Dimension(400, 350));
        panelProcedimiento.setBackground(Color.LIGHT_GRAY);
        
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

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == botonAnalisis) {
            //Limpiar el panel del lexico
            panelInterno.removeAll();

            //Almacenar el procedimiento almacenado en un text area
            procedimientoAlmacenado = new ArrayList<String>();
            cargarArchivo();

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
                }
            }
            
            if (procedimientoAlmacenado.contains("END")){
                if (procedimientoAlmacenado.contains("BEGIN")){
                    //do nothing
                } else {
                    JOptionPane.showMessageDialog(null,"ERROR: Se encontró la sentencia END sin BEGIN");
                }
            }
            
            

            //Analizador léxico
            try {

                Reader lector = new BufferedReader(new FileReader("procedimiento.txt"));
                
                Lexer lexer = new Lexer(lector);
                
                String resultado = "";
                String procedimientoTexto = "";
                while (true) {

                    Tokens tokens = lexer.yylex();
                    if (tokens == null) {
                        resultado += "El ultimo token fue analizado...";
                        areaSalida.setText(resultado);

                        areaSalida.setBounds(0, 0, 400, 2000);

                        panelInterno.add(areaSalida);

                        panelInterno.repaint();

                        panel.setViewportView(panelInterno);

                        for (int i = 0; i < procedimientoAlmacenado.size(); i++) {
                            procedimientoTexto += procedimientoAlmacenado.get(i) + "\n";
                        }

                        //Pintar el procedimiento almacenado en el panel de la derecha
                        System.out.println(procedimientoTexto);
                        panelProcedimientoInterno.removeAll();
                        areaProcedimiento.setText(procedimientoTexto);
                        areaProcedimiento.setBounds(0, 0, 400, 2000);
                        panelProcedimientoInterno.add(areaProcedimiento);
                        panelProcedimientoInterno.repaint();

                        panelProcedimiento.setViewportView(panelProcedimientoInterno);

                        return;
                    }
                    switch (tokens) {
                        case ERROR:
                            resultado += "Simbolo no identificado...";
                            break;
                        case Identificador:
                        case Numero:
                        case Reservada:
                        case Delimitador:
                        case Statement:
                        case Cierre:
                        case Operador_o_Identificador:
                        case Operador:
                            resultado += lexer.lecturaLexica + ": es: " + tokens + "\n";
                            System.out.println(lexer.lecturaLexica);
                            break;
                        default:
                            resultado += "Token: " + tokens + "\n";
                            break;

                    }

                }

            } catch (FileNotFoundException ex) {

                Logger.getLogger(Lexico.class.getName()).log(Level.SEVERE, null, ex);

            } catch (IOException ex) {

                Logger.getLogger(Lexico.class.getName()).log(Level.SEVERE, null, ex);

            }

            /*try {
                
                escribir = new PrintWriter(archivo);
                escribir.print(campoIngreso.getText());
                escribir.close();
                
            } catch (FileNotFoundException ex) {
                
                Logger.getLogger(Lexico.class.getName()).log(Level.SEVERE, null, ex);
                
            }*/
        }

    }

}
