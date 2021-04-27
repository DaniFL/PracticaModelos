package es.ceu.gisi.modcomp.webcrawler.jflex.test;

import es.ceu.gisi.modcomp.webcrawler.jflex.HTMLParser;
import es.ceu.gisi.modcomp.webcrawler.jflex.lexico.Tipo;
import es.ceu.gisi.modcomp.webcrawler.jflex.lexico.Token;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Clase que testea y muestra el uso del analizador léxico creado con JFlex.
 *
 * @author Sergio Saugar García <sergio.saugargarcia@ceu.es>
 */
public class HTMLParserTest {

    private final static String PATH_PRUEBAS = new java.io.File("").getAbsolutePath()
            + "/test/es/ceu/gisi/modcomp/webcrawler/jflex/test/";

    private File ficheroPrueba1 = new File(PATH_PRUEBAS + "prueba1.html");
    private File ficheroPrueba2 = new File(PATH_PRUEBAS + "prueba2.html");
    private Reader reader1;
    private Reader reader2;
    HTMLParser analizador;

    /**
     * Se va a crear un analizador léxico, a partir de uno de los ficheros de
     * prueba.
     */
    public HTMLParserTest() {
        try {
            reader1 = new BufferedReader(new FileReader(ficheroPrueba1));
            reader2 = new BufferedReader(new FileReader(ficheroPrueba2));
            analizador = new HTMLParser(reader1);
        } catch (FileNotFoundException fnfe) {
            System.out.println("No se pudo abrir alguno de los ficheros");
            fnfe.printStackTrace(System.out);
            throw new RuntimeException();
        }
    }

    /**
     * El test comprueba que el analizador léxico reconoce los tres primeros
     * tokens de un fichero HTML y que corresponden con "<HTML>".
     */
    @Test
    public void compruebaEtiquetaInicioHTML() {
        try {
            Token token1 = analizador.nextToken();
            Token token2 = analizador.nextToken();
            Token token3 = analizador.nextToken();
            assertEquals(token1.getTipo(), Tipo.OPEN);
            assertEquals(token2.getValor().toLowerCase(), "html");
            assertEquals(token3.getTipo(), Tipo.CLOSE);
        } catch (IOException ex) {
            Logger.getLogger(HTMLParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * El test comprueba que el analizador léxico reconoce los tres primeros
     * tokens de un fichero HTML y que corresponden con "<HTML>".
     */
    @Test
    public void compruebaInicioYFinEtiquetaHTML() {
        try {
            analizador.yyreset(reader1);
            //El inicio de una etiqueta HTML es: <NOMBREETIQUETA>
            Token token1 = analizador.nextToken();
            Token token2 = analizador.nextToken();
            Token token3 = analizador.nextToken();
            assertEquals(token1.getTipo(), Tipo.OPEN);
            assertEquals(token2.getValor().toLowerCase(), "html");
            assertEquals(token3.getTipo(), Tipo.CLOSE);

            // El final de una etiqueta HTML es: </NOMBREETIQUETA>
            Token token4 = analizador.nextToken();
            Token token5 = analizador.nextToken();
            Token token6 = analizador.nextToken();
            Token token7 = analizador.nextToken();
            assertEquals(token4.getTipo(), Tipo.OPEN);
            assertEquals(token5.getTipo(), Tipo.SLASH);
            assertEquals(token6.getValor().toLowerCase(), "html");
            assertEquals(token7.getTipo(), Tipo.CLOSE);
        } catch (IOException ex) {
            Logger.getLogger(HTMLParserTest.class.getName()).log(Level.SEVERE, null, ex);
            assertTrue(false);
        }
    }

    /**
     * El test comprueba que existe una etiqueta BR y que es una etiqueta sin
     * contenido, que se abren y cierran en una misma sentencia: <BR/>
     */
    @Test
    public void compruebaInicioYFinEtiquetaBR() {
        try {
            analizador.yyreset(reader2);
            //Una etiqueta BR tiene la forma: <BR /> (incluye inicio y fin de etiqueta)
            boolean encontradoBR = false;
            while (!encontradoBR) {
                Token token1 = analizador.nextToken();
                while (token1.getTipo() == Tipo.OPEN) {
                    //Si encuentro un token OPEN puede ser el inicio de una etiqueta BR...
                    Token token2 = analizador.nextToken();
                    if (token2.getValor().toLowerCase().equals("br")) {
                        //Es una etiqueta BR:
                        encontradoBR = true;
                        Token token4 = analizador.nextToken();
                        Token token5 = analizador.nextToken();
                        assertEquals(token4.getTipo(), Tipo.SLASH);
                        assertEquals(token5.getTipo(), Tipo.CLOSE);
                        break;
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(HTMLParserTest.class.getName()).log(Level.SEVERE, null, ex);
            assertTrue(false);
        }
    }
}
