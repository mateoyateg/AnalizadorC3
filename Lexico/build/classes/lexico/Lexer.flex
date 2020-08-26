package lexico;
import static lexico.Tokens.*;
%%
%class Lexer
%type Tokens
L=[a-zA-Z_]+
D=[0-9]+
espacio=[ ,\t,\r,\n]+
%{
    public String lecturaLexica;
%}
%%
int |
if |
else |
while {lecturaLexica=yytext(); return Reservada;}
{espacio} {/*Ignore*/}
"//".* {/*Ignore*/}
"INSERT" {lecturaLexica=yytext();return Statement;}
"SELECT" {lecturaLexica=yytext();return Statement;}
"DELETE" {lecturaLexica=yytext();return Statement;}
"UPDATE" {lecturaLexica=yytext();return Statement;}
"CREATE" {lecturaLexica=yytext();return Statement;}
"'" {lecturaLexica=yytext();return Delimitador;}
"(" {lecturaLexica=yytext();return Delimitador;}
")" {lecturaLexica=yytext();return Delimitador;}
"TABLE" {lecturaLexica=yytext();return Reservada;}
"FROM" {lecturaLexica=yytext();return Reservada;}
"WHERE" {lecturaLexica=yytext();return Reservada;}
"INTO" {lecturaLexica=yytext();return Reservada;}
"VALUES" {lecturaLexica=yytext();return Reservada;}
"PROCEDURE" {lecturaLexica=yytext();return Reservada;}
"BEGIN" {lecturaLexica=yytext();return Reservada;}
"END" {lecturaLexica=yytext();return Reservada;}
"=" {lecturaLexica=yytext();return Operador;}
"*" {lecturaLexica=yytext();return Operador_o_Identificador;}
"," {lecturaLexica=yytext();return Separador;}
";" {lecturaLexica=yytext();return Cierre;}
{L}({L}|{D})* {lecturaLexica=yytext(); return Identificador;}
("(-"{D}+")")|{D}+ {lecturaLexica=yytext(); return Numero;}
 . {return ERROR;}
