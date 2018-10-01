package modelo;
import java.sql.*;

public class Database {
  /* DATOS PARA LA CONEXION */
  private String bd = "proyectolittle";
  private String login = "root";
  private String password = "";
  private String url = "jdbc:mysql://localhost:3306/"+bd;
  private Connection conn = null;
//___________________________________________________________________________________ 
   public Database(){
      try{
         //obtenemos el driver de para mysql
         Class.forName("com.mysql.jdbc.Driver");
         //obtenemos la conexión
         conn = DriverManager.getConnection(url,login,password);
         if (conn!=null){
            System.out.println("OK base de datos "+bd+" listo");
         }
      }catch(SQLException e){
         System.out.println(e);
      }catch(ClassNotFoundException e){
         System.out.println(e);
      }
    }
//___________________________________________________________________________________ 
    public Connection getConnection()
    {
        return this.conn;
    }
//___________________________________________________________________________________ 
/* METODO PARA REALIZAR UNA CONSULTA A LA BASE DE DATOS
 * INPUT:  
 *      table => nombre de la tabla donde se realizara la consulta, puede utilizarse tambien INNER JOIN
 *      fields => String con los nombres de los campos a devolver Ej.: campo1,campo2campo_n
 *      where => condicion para la consulta
 * OUTPUT: un object[][] con los datos resultantes, sino retorna NULL
 */
    public Object [][] select(String table, String fields, String where){
      int registros = 0;      
      String colname[] = fields.split(",");

      //Consultas SQL
      String q ="SELECT " + fields + " FROM " + table;
      String q2 = "SELECT count(*) as total FROM " + table;
      if(where!=null)
      {
          q+= " WHERE " + where;
          q2+= " WHERE " + where;
      }
      //obtenemos la cantidad de registros existentes en la tabla
      try{
         PreparedStatement pstm = conn.prepareStatement(q2);
         ResultSet res = pstm.executeQuery();
         res.next();
         registros = res.getInt("total");
         res.close();
      }catch(SQLException e){
         System.out.println(e);
      }
    //se crea una matriz con tantas filas y columnas que necesite
    Object[][] data = new String[registros][fields.split(",").length];
    //realizamos la consulta sql y llenamos los datos en la matriz "Object"
      try{
         PreparedStatement pstm = conn.prepareStatement(q);
         ResultSet res = pstm.executeQuery();
         int i = 0;
         while(res.next()){
            for(int j=0; j<=fields.split(",").length-1;j++){
                data[i][j] = res.getString( colname[j].trim() );
            }
            i++;         }
         res.close();
          }catch(SQLException e){
         System.out.println(e);
    }
    return data;
 }
//___________________________________________________________________________________ 
/* METODO PARA INSERTAR UN REGISTRO EN LA BASE DE DATOS
 * INPUT:
	table = Nombre de la tabla
	fields = String con los nombres de los campos donde insertar Ej.: campo1,campo2campo_n
	values = String con los datos de los campos a insertar Ej.: valor1, valor2, valor_n
*/
//___________________________________________________________________________________ 
    public boolean insert(String table, String fields, String values)
    {
        boolean res=false;
        //Se arma la consulta
        String q=" INSERT INTO " + table + " ( " + fields + " ) VALUES ( " + values + " ) ";
        //se ejecuta la consulta
        try {
            PreparedStatement pstm = conn.prepareStatement(q);
            pstm.execute();
            pstm.close();
            res=true;
         }catch(SQLException e){
         System.out.println(e);
      }
      return res;
    }
//___________________________________________________________________________________ 
    public void desconectar(){
      conn = null;
      System.out.println("La conexion a la  base de datos "+bd+" a terminado. PROBLEM?");
   }
//___________________________________________________________________________________ 
}
