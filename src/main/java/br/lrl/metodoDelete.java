package br.lrl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

@WebServlet("/doPostDelete")
public class metodoDelete extends ServletProduto{

private static final long serialVersionUID = 1L;
	
	private Connection connection;
	private Statement statement;
	ResultSet rs;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public metodoDelete() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String descricao = request.getParameter("descricao");
		// ID1 = Integer.parseInt(ID); 
		conectar();
		
		System.out.println(descricao);

		String query = "DELETE FROM produto Where descricao='"+descricao+"' ";
        int status = executeUpdate(query);
		
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
		out.println("<html lang=\"pt-br\r\n");
		out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		out.println("<body>");
		if (status == 1) {
			out.println("<h3>O produto "+ descricao + " foi deletado com sucesso!</h3>");
		}
		
		
		try {
			out.println("<h2>Lista de Produtos cadastrados no banco de dados</h2>");
            rs = statement.executeQuery("SELECT * FROM produto");
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
		

		if (rs != null) {
            try {
                while (rs.next()) {
                	out.println("<p>================================</p>");
                    out.println("<strong>Descrição: </strong>" + rs.getString("descricao") + "<br>");
                    out.println("<strong>Código de Barras: </strong>" + rs.getString("codigodebarras") + "<br>");
                    out.println("<strong>Quantidade: </strong>" + rs.getString("quantidade") + "<br>");
                    out.println("<p>================================</p>");
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
		
		out.println("<a href=\"index.html\">\r\n"
				+ "        <input type=\"button\" value=\"Página Inicial\">\r\n"
				+ "    </a>");
		out.println("</body>\r\n");
		out.println("</html>");
		
		
	} 
	
	private void conectar() {
    	try {
			String address = "localhost";
			String port = "3306";
			String dataBaseName = "cortereal2";
			String user = "root";
			String password = "root";
			
			Class.forName("com.mysql.jdbc.Driver");
			connection = (Connection) DriverManager.getConnection("jdbc:mysql://"+ address + ":" + port +"/"+ dataBaseName + "?user=" + user + "&password=" + password + "&useTimezone=true&serverTimezone=UTC"); 
			statement = (Statement) connection.createStatement();
			
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
    	
    }
	
	// Para inserções, alterações e exclusões   
    public int executeUpdate(String query) {     
        int status = 0;
        try {
        	statement = (Statement) connection.createStatement();           
            status = statement.executeUpdate(query);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return status;
    }
    
    // Para consultas
    public ResultSet executeQuery(String query) {
        try {
        	statement = (Statement) connection.createStatement();
            rs = statement.executeQuery(query);
        } catch (SQLException ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return rs;   
    }

}
