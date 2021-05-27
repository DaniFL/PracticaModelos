package es.ceu.gisi.modcomp.webcrawler.jflex;

import es.ceu.gisi.modcomp.webcrawler.jflex.lexico.Tipo;
import es.ceu.gisi.modcomp.webcrawler.jflex.lexico.Token;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Esta clase encapsula toda la lógica de interacción con el parser HTML.
 *
 * @author Sergio Saugar García <sergio.saugargarcia@ceu.es>
 */
public class JFlexScraper {
    
    ArrayList<String> enlacesA = new ArrayList();
    ArrayList<String> enlacesImg = new ArrayList();
    Stack<String> etiquetasAbiertas = new Stack();  
    HTMLParser analizador;

    public JFlexScraper(File fichero) throws FileNotFoundException, IOException {
        Reader reader = new BufferedReader(new FileReader(fichero));
        analizador = new HTMLParser(reader);
        
        int estado = 0; 
        Token key = analizador.nextToken();
        boolean marcadorA = false; 
        boolean marcadorImg = false;
        boolean valueHref = false; 
        boolean valueSrc = false; 
        
        while(key!= null){
            switch(estado){
                case 0: 
                    if (key.getTipo() == Tipo.OPEN){
                        estado = 1; 
                    }
                    break; 
                    
                case 1: 
                    if (key.getTipo() == Tipo.PALABRA){
                        estado = 2;
                        etiquetasAbiertas.push(key.getValor().toLowerCase());
                      if (key.getValor().equalsIgnoreCase("a")){
                          marcadorA = true; 
                      } else if (key.getValor().equalsIgnoreCase("img")){
                          marcadorImg = true; 
                        }                     
                      } else if (key.getTipo() == Tipo.SLASH){
                        estado = 6;
                        etiquetasAbiertas.pop();
                        }                   
                    break;
                    
                case 2: 
                    if (key.getTipo() == Tipo.PALABRA) {
                        estado = 3;
                        etiquetasAbiertas.push(key.getValor().toLowerCase());
                        if (marcadorA){
                            if (key.getValor().equalsIgnoreCase("href")){
                                valueHref = true;                             
                            }
                        } else if (marcadorImg){
                            if (key.getValor().equalsIgnoreCase("src")){
                                valueSrc = true;                                
                            }
                        }
                    } if (key.getTipo() == Tipo.CLOSE){
                        estado = 0;                        
                    } if (key.getTipo() == Tipo.SLASH){
                        estado = 5; 
                        etiquetasAbiertas.pop();
                    }                   
                    break;
                    
                case 3:
                    if (key.getTipo() == Tipo.IGUAL){
                        estado = 4; 
                        break;
                    }
                     
                case 4: 
                    if (key.getTipo() == Tipo.VALOR){
                        estado = 2;
                        etiquetasAbiertas.push(key.getValor().toLowerCase());
                        if (valueHref){
                            enlacesA.add(key.getValor()); 
                        }   else if (valueSrc){
                            enlacesImg.add(key.getValor()); 
                            }
                    }
                    break; 
                    
                case 5: 
                    if (key.getTipo() == Tipo.CLOSE){
                        estado = 0; 
                    }
                    break; 
                    
                case 6: 
                    if (key.getTipo() == Tipo.PALABRA){
                        estado = 7;
                        if (key.getValor().equalsIgnoreCase("a")){
                            marcadorA = true;                         
                        }   else if (key.getValor().equalsIgnoreCase("img")){
                            marcadorImg = true; 
                            }                     
                    }
                    break; 
                    
                case 7:
                    if (key.getTipo() == Tipo.CLOSE){
                        estado = 0; 
                        etiquetasAbiertas.pop(); 
                    }
                    break;                                             
            }          
            key = analizador.nextToken(); 
        }         
    }

    // Esta clase debe contener tu automata programado...
    public ArrayList<String> obtenerHiperenlaces() {
        
        return new ArrayList<String>();
    }

    public ArrayList<String> obtenerHiperenlacesImagenes() {
        // Habrá que programarlo..
        return new ArrayList<String>();
    }

    public boolean esDocumentoHTMLBienBalanceado() {
        // Habrá que programarlo..
        return false;
    }
}
