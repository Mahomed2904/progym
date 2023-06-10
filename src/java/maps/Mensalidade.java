/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package maps;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import servicos.GestaoAluno;
import utils.Converter;
import utils.Estado;

/**
 *
 * @author mahomed
 */

@MultipartConfig(fileSizeThreshold=1024*1024*10,    // 10 MB 
                 maxFileSize=1024*1024*50,          // 50 MB
                 maxRequestSize=1024*1024*100,      // 100 MB
                 location="/")
public class Mensalidade extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            
            GestaoAluno gestaoAluno = new GestaoAluno();
            HttpSession sessao = request.getSession();
            
            if(sessao.getAttribute("secretariaID") == null) {
                out.print( Converter.partialEstadoToJSON( gestaoAluno.getErroDeAutenticacao()) );
                return;
            }
            
            String operation = request.getParameter("op");
            switch(operation) 
            {
                case "add":
                    {
                        int cobrancaID = (int) (sessao.getAttribute("cobrancaID") != null ? sessao.getAttribute("cobrancaID") : -1); 
                        int alunoID = (int) sessao.getAttribute("alunoID");
                        int secretáriaID = (int) sessao.getAttribute("secretariaID");
                        
                        double valor = Double.parseDouble(request.getParameter("valor"));
                        String codigoRecibo = request.getParameter("recibo");
                        String banco = request.getParameter("banco");
                        
                        if(cobrancaID == -1) {
                            Estado estado = gestaoAluno.pagarTodasCobrancas(alunoID, secretáriaID, valor, banco, codigoRecibo);
                            out.print( Converter.allEstadoToJSON(estado) );
                            return;
                        }
                        
                        Estado estado = gestaoAluno.pagarACobranca(alunoID, secretáriaID, cobrancaID, valor, banco, codigoRecibo);
                        out.print( Converter.allEstadoToJSON(estado) );
                    }
                    break;
                case "lst":
                    {
                        int alunoID = Integer.parseInt( request.getParameter("alunoID") );
                        
                        Estado estado = gestaoAluno.listaDeCobrancasPorAluno(alunoID);
                        out.append( Converter.allEstadoToJSON(estado) );
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
