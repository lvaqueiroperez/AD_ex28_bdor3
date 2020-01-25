package ex28_bdor3;

import java.sql.Struct;
import java.awt.Cursor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Ex28_bdor3 {

    /*
     desenvolve un proxecto java chamado bdor3 que  amose todos 
     os datos de todos
     os empregados da taboa empregados
     */
    //PRIMERO TENDREMOS QUE OBTENER EN UN RESSULTSET
    //LOS DATOS DE LA CONSULTA COMO HEMOS HECHO SIEMPRE
    //ACORDARSE DE IMPORTAR EL DRIVER E INICIAR EL LISTENER
    Connection conn;

    public void Conexion() throws SQLException {

        String driver = "jdbc:oracle:thin:";
        String host = "localhost.localdomain"; // tambien puede ser una ip como "192.168.1.14"
        String porto = "1521";
        String sid = "orcl";
        String usuario = "hr";
        String password = "hr";
        String url = driver + usuario + "/" + password + "@" + host + ":" + porto + ":" + sid;

        conn = DriverManager.getConnection(url);

    }

    public void Cerrar() throws SQLException {

        conn.close();
    }

    public void mostrarEmpregados() {

        try {
            //NO PONER ";" AL FINAL CUANDO HAGAMOS CONSULTAS DE ESTA MANERA
            PreparedStatement pstm = conn.prepareStatement("select * from empregadosbdor");

            ResultSet rs = pstm.executeQuery();

            System.out.println("ID\tTIPO_EMP\tSALARIO\n");

            //RECORREMOS EL RS
            while (rs.next()) {
                //SABEMOS DE ANTEMANO EL TIPO DE LOS CAMPOS??
                //ID
                System.out.print(rs.getInt(1) + "\t");

                //TIPO_EMP (objeto)
                java.sql.Struct objSQL = (java.sql.Struct) rs.getObject(2);
                //CREAMOS ARRAY CON LOS CAMPOS DEL OBJETO:
                Object[] campos = objSQL.getAttributes();
                //OBTENEMOS LOS DATOS DEL ARRAY:
                String nome = (String) campos[0];
                //no se puede poner int simplemente?
                java.math.BigDecimal edade = (java.math.BigDecimal) campos[1];

                System.out.print(nome + " " + edade + "\t");

                //SALARIO
                System.out.print(rs.getInt(3));

                System.out.println("\n");

            }

        } catch (SQLException ex) {
            Logger.getLogger(Ex28_bdor3.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) throws SQLException {

        Ex28_bdor3 obj = new Ex28_bdor3();

        obj.Conexion();

        obj.mostrarEmpregados();

        obj.Cerrar();

    }

}
