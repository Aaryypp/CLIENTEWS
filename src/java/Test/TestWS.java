
package Test;

import javax.swing.JOptionPane;
import ws.Usuario;
import ws.WSOperaciones;
import ws.WSOperaciones_Service;

/**
 *
 * @author ariel
 */
public class TestWS {
    public static Login log = new Login();
    public static Registro reg = new Registro();
    public static Cuenta cuenta = new Cuenta();
    public static Usuario usu = new Usuario();
    public static WSOperaciones_Service servicio =new WSOperaciones_Service();
    public static WSOperaciones cliente=servicio.getWSOperacionesPort();
    public static void main(String[] args) {

        
        log.setLocationRelativeTo(null);
        log.setVisible(true);
        /////acciones de login
        log.getBtnreg().addActionListener(l -> registar());
        log.getTxtingresar().addActionListener(l->entraracuenta());
     
        
        ////acciones de registro
        reg.getBtnregresar().addActionListener(l->regresar());
        reg.getBtnregistrar().addActionListener(l->registarusuario());
        
        
        //////acciones de cuenta
        cuenta.getBtnretirodepo().addActionListener(l->seleccion());
        cuenta.getBtnregresar().addActionListener(l->regresardesdec());
      
        

    }

    private static void registar() {
      log.dispose();
      reg.setLocationRelativeTo(null);
      reg.setVisible(true);

    }

    private static void regresar() {
        reg.dispose();
        log.setLocationRelativeTo(null);
        log.setVisible(true);
    }
    
     private static void regresardesdec() {
        cuenta.dispose();
        log.setLocationRelativeTo(null);
        log.setVisible(true);
    }
    
 
    private static void ingreusuario() {
        reg.dispose();
        log.setLocationRelativeTo(null);
        log.setVisible(true);
    }
    

    
    
    private static void retiro() {
        // Obtener el monto de retiro ingresado
        String retiroText = cuenta.getRetirodepo().getText().trim();

        // Verificar si el campo de retiro está vacío
        if (retiroText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese el monto de retiro", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Salir del método sin realizar el retiro
        }

        // Convertir el monto de retiro a double
        double retiro;
        try {
            retiro = Double.parseDouble(retiroText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Ingrese un valor válido para el retiro", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Salir del método sin realizar el retiro
        }

        // Verificar si el saldo es suficiente para el retiro
        if (retiro <= usu.getSaldo()) {
            // Realizar el retiro
            JOptionPane.showMessageDialog(null, "Retiro correcto", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            usu.setSaldo(usu.getSaldo() - retiro);
            cliente.actualizardinero(usu);
            double saldo = Double.parseDouble(cuenta.getTxtsaldo().getText());
            cuenta.getTxtsaldo().setText(String.valueOf(saldo - retiro));
        } else {
            JOptionPane.showMessageDialog(null, "Saldo insuficiente", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private static void deposito() {
    String depositoText = cuenta.getRetirodepo().getText().trim();
    if (depositoText.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Por favor, ingrese el monto de depósito", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    double deposito;
    try {
        deposito = Double.parseDouble(depositoText);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Ingrese un valor válido para el depósito", "Error", JOptionPane.ERROR_MESSAGE);
        return; 
    }
    if (deposito <= 0) {
        JOptionPane.showMessageDialog(null, "El monto de depósito debe ser mayor que cero", "Error", JOptionPane.ERROR_MESSAGE);
        return; 
    }

    JOptionPane.showMessageDialog(null, "Depósito correcto", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
    usu.setSaldo(usu.getSaldo() + deposito);
    cliente.actualizardinero(usu);
    double saldo = Double.parseDouble(cuenta.getTxtsaldo().getText());
    cuenta.getTxtsaldo().setText(String.valueOf(saldo + deposito));
}
     private static void seleccion() { 
   
         if (cuenta.getRadiodepo().isSelected() || cuenta.getRadioretiro().isSelected()) {
             if (cuenta.getRadiodepo().isSelected()) {
                 deposito();
             }
             if (cuenta.getRadioretiro().isSelected()) {
                 retiro();
             }

         } else {
              JOptionPane.showMessageDialog(null, "Seleccione el tipo de transaccion", "Mensaje", JOptionPane.INFORMATION_MESSAGE);

         }
         
         
                
            
    }   
      
      
    

    private static void entraracuenta() {
        usu = cliente.buscarUsuario(log.getTxtlogusu().getText(), log.getTxtlogclave().getText());
        if (usu != null) {
            JOptionPane.showMessageDialog(null, "Ingreso correcto", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            log.dispose();
            cuenta.setLocationRelativeTo(null);
            cuenta.setVisible(true);
            cuenta.getTxtusuario().setText(usu.getUsername());
            cuenta.getTxtsaldo().setText(usu.getSaldo().toString());
           
        } else {
           JOptionPane.showMessageDialog(null, "Acceso incorrecto", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

private static void registarusuario() {
    String username = reg.getTxtusuario().getText().trim();
    String saldoText = reg.getTxtsaldo().getText().trim();
    String password = reg.getTxtcontra().getText().trim();
    if (username.isEmpty() || saldoText.isEmpty() || password.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
        return; 
    }
    double saldo;
    try {
        saldo = Double.parseDouble(saldoText);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Ingrese un valor válido para el saldo", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    usu.setUsername(username);
    usu.setSaldo(saldo);
    usu.setPassword(password);
    cliente.ingresoUsuarios(usu); 
    JOptionPane.showMessageDialog(null, "Usuario registrado correctamente", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
    regresar();
}
    
}
