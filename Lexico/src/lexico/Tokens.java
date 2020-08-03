/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexico;

//CREATE PROCEDURE GetAllProducts() BEGIN SELECT *  FROM products; END
//UPDATE Customers SET ContactName='Juan' WHERE Country='Mexico';
/**
 *
 * @author danbr
 */
public enum Tokens {
    Reservada,
    Statement,
    Separador,
    Cierre,
    Operador_o_Identificador,
    Operador,
    Igual,
    Suma,
    Resta,
    Multiplicacion,
    Division,
    Identificador,
    Delimitador,
    Numero,
    ERROR
}
