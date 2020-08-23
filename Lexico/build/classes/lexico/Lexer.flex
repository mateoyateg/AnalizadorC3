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
if | else | while {lecturaLexica=yytext(); return Reservada;}
{espacio} {/*Ignore*/}
"//".* {/*Ignore*/}

/* Statements */
"INSERT" {lecturaLexica=yytext();return Statement;}
"SELECT" {lecturaLexica=yytext();return Statement;}
"DELETE" {lecturaLexica=yytext();return Statement;}
"UPDATE" {lecturaLexica=yytext();return Statement;}
"CREATE" {lecturaLexica=yytext();return Statement;}

/* Tipos de datos (extraido de: https://sites.google.com/site/basdededatosrelacionales/home/contenido/tipos-de-datos-en-sql-server) */
"BigInt" {lecturaLexica=yytext();return Tipo;}
"bigint" {lecturaLexica=yytext();return Tipo;}
"varchar" {lecturaLexica=yytext();return Tipo;}
"nvarchar" {lecturaLexica=yytext();return Tipo;}
"int" {lecturaLexica=yytext();return Tipo;}
"smallint" {lecturaLexica=yytext();return Tipo;}
"tinyint" {lecturaLexica=yytext();return Tipo;}
"bit" {lecturaLexica=yytext();return Tipo;}
"decimal" {lecturaLexica=yytext();return Tipo;}
"numeric" {lecturaLexica=yytext();return Tipo;}
"money" {lecturaLexica=yytext();return Tipo;}
"smallmoney" {lecturaLexica=yytext();return Tipo;}
"float" {lecturaLexica=yytext();return Tipo;}
"real" {lecturaLexica=yytext();return Tipo;}
"datetime" {lecturaLexica=yytext();return Tipo;}
"smalldatetime" {lecturaLexica=yytext();return Tipo;}
"char" {lecturaLexica=yytext();return Tipo;}
"text" {lecturaLexica=yytext();return Tipo;}
"nchar" {lecturaLexica=yytext();return Tipo;}
"ntext" {lecturaLexica=yytext();return Tipo;}
"binary" {lecturaLexica=yytext();return Tipo;}
"varbinary" {lecturaLexica=yytext();return Tipo;}
"image" {lecturaLexica=yytext();return Tipo;}
"cursor" {lecturaLexica=yytext();return Tipo;}
"timestamp" {lecturaLexica=yytext();return Tipo;}
"sql_variant" {lecturaLexica=yytext();return Tipo;}
"uniqueidentifier" {lecturaLexica=yytext();return Tipo;}
"table" {lecturaLexica=yytext();return Tipo;}
"xml" {lecturaLexica=yytext();return Tipo;}

/* Delimitadores */
"'" {lecturaLexica=yytext();return Delimitador;}
"(" {lecturaLexica=yytext();return Delimitador;}
"\{" {lecturaLexica=yytext();return Delimitador;}
"\}" {lecturaLexica=yytext();return Delimitador;}
")" {lecturaLexica=yytext();return Delimitador;}

/* Palabras reservadas */
"TABLE" {lecturaLexica=yytext();return Reservada;}
"FROM" {lecturaLexica=yytext();return Reservada;}
"WHERE" {lecturaLexica=yytext();return Reservada;}
"INTO" {lecturaLexica=yytext();return Reservada;}
"VALUES" {lecturaLexica=yytext();return Reservada;}
"PROCEDURE" {lecturaLexica=yytext();return Reservada;}
"BEGIN" {lecturaLexica=yytext();return Reservada;}
"END" {lecturaLexica=yytext();return Reservada;}

/* Operadores y varios */
"=" {lecturaLexica=yytext();return Operador;}
"*" {lecturaLexica=yytext();return Operador_o_Identificador;}
"," {lecturaLexica=yytext();return Separador;}
"\." {lecturaLexica=yytext();return Punto;}
"\," {lecturaLexica=yytext();return Coma;}
";" {lecturaLexica=yytext();return Cierre;}
"@" {lecturaLexica=yytext();return Arroba;}

/* Identificadores y numeros */
{L}({L}|{D})* {lecturaLexica=yytext(); return Identificador;}
("(-"{D}+")")|{D}+ {lecturaLexica=yytext(); return Numero;}
 . {return ERROR;}
