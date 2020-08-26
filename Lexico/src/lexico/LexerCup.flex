
package lexico;
import java_cup.runtime.Symbol;
%%
%class LexerCup
%type java_cup.runtime.Symbol
%cup
%full
%line
%char
L=[a-zA-Z_]+
D=[0-9]+

espacio=[ ,\t,\r,\n]+
%{
    private Symbol symbol(int type, Object value){
        return new Symbol(type, yyline, yycolumn, value);
    }
    private Symbol symbol(int type){
        return new Symbol(type, yyline, yycolumn);
    }
%}
%%

/* Espacios en blanco */
{espacio} {/*Ignore*/}

/* Comentarios */
( "//"(.)* ) {/*Ignore*/}

/* Comillas */
( "\"" ) {return new Symbol(sym.Comillas, yychar, yyline, yytext());}

/* Tipos de datos */
( int | varchar ) {return new Symbol(sym.T_dato, yychar, yyline, yytext());}

/* Palabra reservada INSERT */
( INSERT ) {return new Symbol(sym.INSERT, yychar, yyline, yytext());}

/* Palabra reservada DELETE */
( DELETE ) {return new Symbol(sym.DELETE, yychar, yyline, yytext());}

/* Palabra reservada UPDATE */
( UPDATE ) {return new Symbol(sym.UPDATE, yychar, yyline, yytext());}

/* Palabra reservada SELECT */
( SELECT ) {return new Symbol(sym.SELECT, yychar, yyline, yytext());}

/* Palabra reservada CREATE */
( CREATE ) {return new Symbol(sym.CREATE, yychar, yyline, yytext());}

/* Palabra reservada CREATE_PROCEDURE */
( CREATE_PROCEDURE ) {return new Symbol(sym.CREATE_PROCEDURE, yychar, yyline, yytext());}

/* Palabra reservada BEGIN */
( BEGIN ) {return new Symbol(sym.BEGIN, yychar, yyline, yytext());}

/* Palabra reservada END */
( END ) {return new Symbol(sym.END, yychar, yyline, yytext());}

/* Palabra reservada INTO */
( INTO ) {return new Symbol(sym.INTO, yychar, yyline, yytext());}

/* Palabra reservada VALUES */
( VALUES ) {return new Symbol(sym.VALUES, yychar, yyline, yytext());}

/* Palabra reservada FROM */
( FROM ) {return new Symbol(sym.FROM, yychar, yyline, yytext());}

/* Palabra reservada WHERE */
( WHERE ) {return new Symbol(sym.WHERE, yychar, yyline, yytext());}

/* Palabra reservada SET */
( SET ) {return new Symbol(sym.SET, yychar, yyline, yytext());}

/* Palabra reservada TABLE */
( TABLE ) {return new Symbol(sym.TABLE, yychar, yyline, yytext());}

/* Palabra reservada function */
( function ) {return new Symbol(sym.function, yychar, yyline, yytext());}

/* Operador Igual */
( "=" ) {return new Symbol(sym.Igual, yychar, yyline, yytext());}

/* Simbolo asterisco */
( "*" ) {return new Symbol(sym.Asterisco, yychar, yyline, yytext());}

/* Parentesis de apertura */
( "(" ) {return new Symbol(sym.Parentesis_a, yychar, yyline, yytext());}

/* Parentesis de cierre */
( ")" ) {return new Symbol(sym.Parentesis_c, yychar, yyline, yytext());}

/* Punto y coma */
( ";" ) {return new Symbol(sym.P_coma, yychar, yyline, yytext());}

/* Identificador */
{L}({L}|{D}) {return new Symbol(sym.Identificador, yychar, yyline, yytext());}

/* Identificador procedure */
( "@" ) {return new Symbol(sym.Id_procedure, yychar, yyline, yytext());}

/* Datos */
[a-zA-Z_]+ | [0-9]+ {return new Symbol(sym.Datos, yychar, yyline, yytext());}

/* Numero */
("(-"{D}+")")|{D}+ {return new Symbol(sym.Numero, yychar, yyline, yytext());}

/* Error de analisis */
 . {return new Symbol(sym.ERROR, yychar, yyline, yytext());}
