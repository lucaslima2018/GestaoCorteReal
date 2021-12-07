package br.lrl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ServletProduto")
public class ServletProduto extends HttpServlet{

private static final long serialVersionUID = 1L;
	
	private Connection connection;
	private Statement statement;
	ResultSet rs;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletProduto() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		conectar();
		
		
		PrintWriter out = response.getWriter();
		
		out.println("<html>\r\n");
		out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		out.println("<body>");

		//EXIBIR TODOS OS PRODUTOS CADASTRADOS NO BD
		
		try {
			out.println("<h2>Lista de Produtos cadastrados no banco de dados</h2>");
            rs = statement.executeQuery("SELECT * FROM produto");
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
		

		if (rs != null) {
            try {
                while (rs.next()) {
                	out.println("<strong>ID: </strong>" + rs.getString("id") + "<br>");
                    out.println("<strong>Descrição: </strong>" + rs.getString("descricao") + "<br>");
                    out.println("<strong>Código de Barras: </strong>" + rs.getString("codigoproduto") + "<br>");
                    out.println("<strong>Quantidade: </strong>" + rs.getString("quantidade") + "<br><br>");
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
		
		
		out.println("</body>\r\n");
		out.println("</html>");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String descricao = request.getParameter("descricao");
		String codigoproduto = request.getParameter("codigoproduto");
		String quantidade = request.getParameter("quantidade");
		
		conectar();
		
		// INSERIR O PRODUTO
		String query = "INSERT INTO produto(descricao, codigoproduto, quantidade) "
                + "values ('"+descricao+"', '"+codigoproduto+"', '"+quantidade+"')";
        int status = executeUpdate(query);
		
		
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
		out.println("<html>\r\n");
		out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		out.println("<body>");
		if (status == 1) {
			out.println("<h3>O Produto "+ descricao + " foi cadastrado com sucesso!</h3>");
		}
		
		//EXIBIR TODAS OS PRODUTOS CADASTRADOS NO BD
		
		try {
			out.println("<h2>Lista de Produtos cadastrados no banco de dados</h2>");
            rs = statement.executeQuery("SELECT * FROM produto");
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
		

		if (rs != null) {
            try {
                while (rs.next()) {
                	out.println("<strong>ID: </strong>" + rs.getString("id") + "<br>");
                    out.println("<strong>Descrição: </strong>" + rs.getString("descricao") + "<br>");
                    out.println("<strong>Código de Barras: </strong>" + rs.getString("codigoproduto") + "<br>");
                    out.println("<strong>Quantidade: </strong>" + rs.getString("quantidade") + "<br><br>");
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
		
		out.println("<a href=\"index.html\">\r\n"
				+ "        <input type=\"button\" value=\"Cadastrar novo produto\"/>\r\n"
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
			connection = DriverManager.getConnection("jdbc:mysql://"+ address + ":" + port +"/"+ dataBaseName + "?user=" + user + "&password=" + password + "&useTimezone=true&serverTimezone=UTC"); 
			statement = connection.createStatement();
			
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
    	
    }
	
	// Para inserção alteração e exclusão   
    public int executeUpdate(String query) {     
        int status = 0;
        try {
        	statement = connection.createStatement();           
            status = statement.executeUpdate(query);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return status;
    }
    
    // Para consultas
    public ResultSet executeQuery(String query) {
        try {
        	statement = connection.createStatement();
            rs = statement.executeQuery(query);
        } catch (SQLException ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return rs;   
    }

}

