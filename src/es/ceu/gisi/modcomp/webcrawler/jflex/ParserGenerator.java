package es.ceu.gisi.modcomp.webcrawler.jflex;

import java.io.File;
import jflex.Main;

/**
 * Esta clase genera un analizador léxico con JFlex a partir de la descripción
 * existente en el fichero "lexico-html.jflex".
 *
 * @author Sergio Saugar García <sergio.saugargarcia@ceu.es>
 */
public class ParserGenerator {

    private final static String FICHERO_LEXICO = new java.io.File("").getAbsolutePath()
                                                 + "/src/es/ceu/gisi/modcomp/webcrawler/jflex/lexico-html.jflex";

    /**
     * La ejecución del método main hace que se genere la clase HTMLParser a
     * partir del fichero "lexico-html.jflex".
     *
     * @param args
     */
    public static void main(String[] args) {
        File especificacionesLexico = new File(FICHERO_LEXICO);
        System.out.println(especificacionesLexico.getAbsolutePath());
        Main.generate(especificacionesLexico);
    }
}
