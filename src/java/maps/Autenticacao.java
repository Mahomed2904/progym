/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package maps;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Secretária;
import servicos.GestaoAluno;
import utils.Converter;
import utils.Estado;

/**
 *
 * @author mahomed
 */
@WebServlet(name = "autenticacao", urlPatterns = {"/autenticacao"})
public class Autenticacao extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            
            GestaoAluno gestaoAluno = new GestaoAluno();
            String operacao = request.getParameter("op");
            
            switch(operacao) {
                case "aut":
                    {
                        String email = request.getParameter("email");
                        String senha = request.getParameter("senha");
                        Estado estado = gestaoAluno.autenticarSecretária(email, senha);
                        
                        if(estado.getCodigo() == 0) {
                            HttpSession sessao = request.getSession();
                            Secretária secretaria = (Secretária) estado.getAnexo();
                            sessao.setAttribute("secretariaID", secretaria.getSecretáriaID());
                            sessao.setAttribute("nome", secretaria.getNome());
                        }
                        
                        out.print(Converter.partialEstadoToJSON(estado));
                        System.out.println(Converter.partialEstadoToJSON(estado));
                    }
                    break;
                case "inf":
                    {
                        HttpSession sessao = request.getSession();
                        if( sessao.getAttribute("secretariaID") == null ) {
                            out.print(gestaoAluno.getErroDeAutenticacao());
                            return;
                        }
                        
                        int secretáriaID = (int) sessao.getAttribute("secretariaID");
                        Estado estado = gestaoAluno.informacoesDaSecretária(secretáriaID);
                        out.print( Converter.allEstadoToJSON(estado) );
                    }
                    break;
                case "sir":
                    {
                        HttpSession sessao = request.getSession();
                        sessao.removeAttribute("secretariaID");
                        sessao.removeAttribute("pesquisa");
                        sessao.removeAttribute("matricula");
                        sessao.removeAttribute("mensalidade");
                        sessao.removeAttribute("atividade");
                        
                        Estado estado = gestaoAluno.terminarSessão();
                        out.print( Converter.partialEstadoToJSON(estado) );
                    }
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
