package es.ceu.gisi.modcomp.webcrawler.jflex;

import es.ceu.gisi.modcomp.webcrawler.jflex.lexico.*;

/**
 * Analizador léxico de páginas
 * @author Sergio Saugar García <sergio.saugargarcia@ceu.es>
 */

%%

%class HTMLParser
%unicode
%public
%line
%column
%type Token
%function nextToken

%{
   StringBuffer string = new StringBuffer();
%}

LineTerminator = \r|\n|\r\n
WhiteSpace     = {LineTerminator} | [ \t\f]
Identificador = [:jletter:] [:jletterdigit:]*

%state STRING

%%

/* Definiciones */
<YYINITIAL> {

   {Identificador} { return new Token(Tipo.PALABRA, yytext()); }
   "=" { return new Token(Tipo.IGUAL, yytext()); }

   "<" { return new Token(Tipo.OPEN, yytext()); }

   ">" { return new Token(Tipo.CLOSE, yytext()); }

   "/" { return new Token(Tipo.SLASH, yytext()); }

   /* Cadenas de caracteres */
   \" { string.setLength(0); yybegin(STRING); }

   /* Espacios en blanco */
   {WhiteSpace} {}
}

<STRING> {
   \" { yybegin(YYINITIAL); return new Token(Tipo.VALOR, string.toString());}

   [^\n\r\"\\]+ { string.append( yytext() ); }

   \\t { string.append('\t'); }

   \\n { string.append('\n'); }

   \\r { string.append('\r'); }

   \\\" { string.append('\"'); }

   \\ { string.append('\\'); }
}

/* error fallback */
[^] {
   //throw new Error("Illegal character <"+ yytext()+">");
}