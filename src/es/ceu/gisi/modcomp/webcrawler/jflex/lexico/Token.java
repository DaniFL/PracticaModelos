package es.ceu.gisi.modcomp.webcrawler.jflex.lexico;

/**
 * Clase que almacena la información de los tokens devueltos por el analizador
 * léxico.
 *
 * @author Sergio Saugar García <sergio.saugargarcia@ceu.es>
 */
public class Token {

    /**
     * Tipo de token.
     */
    private final Tipo tipo;
    /**
     * Valor leído.
     */
    private final String valor;

    /**
     * Crea un nuevo token
     *
     * @param tipo  Tipo determinado del nuevo token
     * @param valor Cadena leída para este token
     */
    public Token(Tipo tipo, String valor) {
        this.tipo = tipo;
        this.valor = valor;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public String getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return "* Token --> Tipo: " + this.tipo + ", Valor: " + this.valor;
    }
}
