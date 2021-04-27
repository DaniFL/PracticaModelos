package es.ceu.gisi.modcomp.webcrawler.jsoup;

import es.ceu.gisi.modcomp.webcrawler.jsoup.JsoupScraper;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Clase que testea y muestra el uso del analizador de árboles DOM Jsoup.
 *
 * @author Sergio Saugar García <sergio.saugargarcia@ceu.es>
 */
public class JsoupScraperTest {

    private static final String HTML = ""
                                       + "<HTML>"
                                       + "    <HEAD>"
                                       + "        <TITLE>My first webpage</TITLE>"
                                       + "    </HEAD>"
                                       + "    <BODY>"
                                       + "        <P>"
                                       + "            This is"
                                       + "            some text<BR />"
                                       + "            and a"
                                       + "            <A href=\"http://www.bbc.co.uk\">link</A>"
                                       + "        </P>"
                                       + "        <IMG src=\"brushedsteel.jpg\"/>"
                                       + "    </BODY>"
                                       + "</HTML>";
    private final JsoupScraper scraper;

    /**
     * Se va a crear un analizador léxico, a partir de uno de los ficheros de
     * prueba.
     */
    public JsoupScraperTest() throws IOException {
        scraper = new JsoupScraper(HTML);
    }

    /**
     * El test recupera el nombre de la primera imagen insertada con la etiqueta
     * IMG .
     */
    @Test
    public void recuperaNombrePrimeraImagen() {
        assertEquals(scraper.obtenerContenidoImg(), "brushedsteel.jpg");
    }
}
