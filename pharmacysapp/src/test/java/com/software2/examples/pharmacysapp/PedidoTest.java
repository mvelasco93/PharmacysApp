/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.software2.examples.pharmacysapp;
import com.software2.examples.pharmacysapp.Producto;
import com.software2.examples.pharmacysapp.DetallePedido;
import com.software2.examples.pharmacysapp.Pago;  
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Stephany
 */
public class PedidoTest {
    
    private static ArrayList<Producto> catalogo;
    private static ArrayList<DetallePedido> carrito;
    
    public static void initCatalogo() {
        
       
        catalogo = new ArrayList<Producto>();
        carrito = new ArrayList<DetallePedido>();
        
        Producto producto1 = new Producto("Dicloflenaco","Medicina",4,1,10.5);
        Producto producto2 = new Producto("Buscapina","Medicina",20,5,5.6);
        Producto producto3 = new Producto("Analgan","Medicina",10,2,2.50);
        Producto producto4 = new Producto("Redoxon","Medicina",30,5,7.80);
        
        catalogo.add(producto1);
        catalogo.add(producto2);
        catalogo.add(producto3);
        catalogo.add(producto4);
    }
    
    
    public Producto obtener_producto_catalogo(ArrayList<Producto> productos, String clave){
        Producto encontrado = null;
        
        for (Producto producto : productos) {
             if(producto.getNombre().equals(clave))
                 encontrado = producto;
            }
        return encontrado;
    
    }
    
    @Before
    public void beforeEachTest() {
        initCatalogo();
    }

    @After
    public void afterEachTest() {
      
    }

    @Test
    public void testIntegracion_producto_catalogo() {
        System.out.println("----Test 1----"); 
        System.out.println("\n(INICIAL) El catálogo de la farmacia es: " + catalogo.size());
        System.out.println(catalogo.toString());
        Producto producto5 = new Producto("Ciprofloxacina","Medicina",50,15,11.42);
        String resultado = producto5.crear_producto();
        catalogo.add(producto5);
        System.out.println("\n(FIN) El catálogo de la farmacia es: " + catalogo.size());
        System.out.println(catalogo.toString()); 
        
        assertEquals("Se creo el producto exitosamente!", resultado); //experado,obtenido
        
        System.out.println("----Test 1----\n"); 
    }
    
    @Test  
    public void testIntegracion_catalogo_carrito() {
        System.out.println("----Test 2----");        
        Producto p = obtener_producto_catalogo(catalogo,"Redoxon"); //simulo que lo agrego al carrito
        DetallePedido detalle1 = new DetallePedido(p,5); //Simulo que selecciono 5
        carrito.add(detalle1);
        //visualizar el pedido con los productos seleccionados.
        System.out.println("*******Carrito*****");
        System.out.println(carrito.toString());
        assertEquals(1, carrito.size()); //experado,obtenido
        System.out.println("----Test 2----\n"); 
    }
    
   
    
    @Test  
    public void testIntegracion_PagoSubtotal() {
        System.out.println("----Test 3----");      
        Producto p1 = obtener_producto_catalogo(catalogo,"Analgan");
        Producto p2 = obtener_producto_catalogo(catalogo,"Buscapina");
        DetallePedido detalle1 = new DetallePedido(p1,1);
        DetallePedido detalle2 = new DetallePedido(p2,1);
        carrito.add(detalle1);
        carrito.add(detalle2);
        //visualizar el pedido con los productos seleccionados.
        System.out.println(carrito.toString());
        Pago pa=new Pago();
        //Ingresa el tipo de pago
        pa.crear_pago(true,"");
        Cliente client=new Cliente("Juan", 1, pa);
        System.out.println(client.InfoPer());
        System.out.println( pa.validar_pago(pa));
        double subt=detalle1.subtotal +detalle2.subtotal;
        System.out.println("El subtotal a pagar es: " + subt);
        assertEquals(true, pa.getTipo());//experado,obtenido
        System.out.println("----Test 3----\n");      
    }
    
    @Test
    public void testIntegracion_ConfirmarPedido(){
    	System.out.println("----Test 4----");
    	Producto p1 = obtener_producto_catalogo(catalogo,"Analgan");
        Producto p2 = obtener_producto_catalogo(catalogo,"Dicloflenaco");
        DetallePedido detalle1 = new DetallePedido(p1,1);
        DetallePedido detalle2 = new DetallePedido(p2,1);
        
        //Crea lista de detalle pedido
        ArrayList<DetallePedido> det = new ArrayList<DetallePedido>();
        det.add(detalle1);
        det.add(detalle2);
        
        Pago pa=new Pago();
        pa.crear_pago(true,"");
        
        //Ingresa nombre,sector,tipo cliente
        Cliente client=new Cliente("Kerly", 2, pa);
        Pedido pedid = new Pedido(det, new Date(), client);
        
        assertEquals("Horario disponible",pedid.ValidHora()); //Prueba si es valido o no
        
        System.out.println("----Test 4----\n");
    }
    
    @Test
    public void totalPagar(){
    	System.out.println("----Test 5----");
    	
    	/*
    	 * Funcionalidades y módulos:
    	 * Confirmar el pedido, Zona de entrega, Recargo por la zona
    	 * 
    	 * */
    	
    	 /*
         * Descripción:
         * El usuario ingresa al sistema, agrega productos al carrito, visualiza el pedido. 
         * Después da clic en siguiente donde ingresa la información del cliente (nombre, sector,
         * tipo de pago) y confirma el pedido. 
         * El sistema devolverá la cantidad a pagar y si existe o no un recargo por el precio y la zona
         * */
    	
    	Producto p1 = obtener_producto_catalogo(catalogo,"Analgan");
        Producto p2 = obtener_producto_catalogo(catalogo,"Dicloflenaco");
        DetallePedido detalle1 = new DetallePedido(p1,1);
        DetallePedido detalle2 = new DetallePedido(p2,1);
        
        //Crea lista de detalle pedido
        ArrayList<DetallePedido> det = new ArrayList<DetallePedido>();
        det.add(detalle1);
        det.add(detalle2);
        
        Pago pa=new Pago();
        pa.crear_pago(true,"");
        
        //Ingresa nombre,sector,tipo cliente
        Cliente client=new Cliente("Kerly", 2, pa);
        Pedido pedid = new Pedido(det, new Date(), client);
        
       
        
        /*
         * Datos de prueba:
         * Productos seleccionados: Analgan y Dicloflenaco
         * Cantidad de cada uno: 1
         * Método de pago: efectivo
         * Nombre del cliente: Kerly
         * Sector: Centro
         * Horario: Hora actual
         * */
        
        /*
         * Resultado esperado
         * El usuario da clic en confirmar pedido donde se le mostrará si obtuvo un recargo y el precio
         * total
         * */
        
        // 1 Analgan = 2.50 + 1 Dicloflenaco = 10.5 Hacen 13 de subtotal a pagar
        // La zona en la que está es el centro por lo que tendría un recargo de 2 en caso de 
        // que el subtotal sea menor a 10
        
        double subt=detalle1.subtotal +detalle2.subtotal;
        System.out.println("El subtotal a pagar es: " + subt);
        
        assertEquals(0.0, pedid.GetRecargo(client, pedid.TotalPedido()));
        assertEquals(13.0, pedid.TotalPedido());
        
        /* 
         * Resultado obtenido
         * El usuario da clic en confirmar pedido donde se le mostrará si obtuvo un recargo y el precio
         * total
         * */
    	
    	System.out.println("----Test 5----\n");
    }
    
    /*
     * Mejoras al código de la app
     * No tiene un estándar tan estilizado y los comentarios están mezclados en la misma línea del
     * código o están antes. No hay unidad en esa parte. Sería bueno que tuvieran JavaDocs las 
     * funciones para no estar revisando el archivo y sus comentarios seguidos. 
     * */

}
