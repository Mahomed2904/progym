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
import utils.Utils;

/**
 *
 * @author mahomed
 */

@MultipartConfig(fileSizeThreshold=1024*1024*10,    // 10 MB 
                 maxFileSize=1024*1024*50,          // 50 MB
                 maxRequestSize=1024*1024*100,      // 100 MB
                 location="/")
public class Matricular extends HttpServlet {

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
                        
                        String nome = request.getParameter("nome");
                        String email = request.getParameter("email");
                        String caminhoDaFoto = Utils.getImage(request, nome);
                        String dataNascimento = request.getParameter("data");
                        String[] atividades = request.getParameterValues("atividades");
                        
                        Estado estado = gestaoAluno.adicionarMatricula(nome, email, caminhoDaFoto, dataNascimento, atividades);
                        out.print(Converter.allEstadoToJSON(estado));
                    }
                    break;
                case "del":
                    {
                        int alunoID = Integer.parseInt(request.getParameter("alunoID"));
                        
                        Estado estado = gestaoAluno.apagarMatricula(alunoID);
                        out.print( Converter.allEstadoToJSON(estado) );
                    }
                    break;
                case "lst":
                    {
                        Estado estado = gestaoAluno.listaDeAlunos();
                        out.print( Converter.allEstadoToJSON(estado) );
                    }
                    break;
                case "edt":
                    {
                        String opcao = request.getParameter("opc");
                        
                        switch(opcao) {
                            case "aluno":
                                {
                                    int alunoID = Integer.parseInt(request.getParameter("alunoID"));
                                    String nome = request.getParameter("nome");
                                    String email = request.getParameter("email");
                                    String caminhoDaFoto = Utils.getImage(request, nome);
                                    String dataNascimento = request.getParameter("datanascimento");
                                    
                                    Estado estado = gestaoAluno.editarAluno(alunoID, nome, email, caminhoDaFoto, dataNascimento);
                                    out.print( Converter.allEstadoToJSON(estado) );
                                }
                                break;
                            case "matricula":
                                {
                                    
                                }
                        }
                    }
                    break;
                case "fnd":
                    {
                        int alunoID = (int) sessao.getAttribute("alunoID");
                        
                        Estado estado = gestaoAluno.informacoesAluno(alunoID);
                        out.print( Converter.allEstadoToJSON(estado) );
                    }
                    break;
                case "act":
                    {
                        Estado estado = gestaoAluno.listaDeAtividades();
                        out.print( Converter.allEstadoToJSON(estado) );
                    }
                    break;
                case "str": // Store
                    {
                        String alunoIDStr = request.getParameter("alunoID");
                        String cobrancaIDStr = request.getParameter("cobrancaID");
                        
                        if(alunoIDStr != null) {
                            int alunoID = Integer.parseInt(alunoIDStr);
                            sessao.setAttribute("alunoID", alunoID);
                            out.print( Converter.partialEstadoToJSON(gestaoAluno.getEstadoSucesso()) );
                        } else {
                            if(cobrancaIDStr != null) {
                                int cobrancaID = Integer.parseInt(cobrancaIDStr);
                                sessao.setAttribute("cobrancaID", cobrancaID);
                                out.print( Converter.partialEstadoToJSON(gestaoAluno.getEstadoSucesso()) );
                            } else {
                                sessao.removeAttribute("cobrancaID");
                                out.print( Converter.partialEstadoToJSON(gestaoAluno.getEstadoSucesso()) );
                            }
                        }
                    }
                    break;
                case "rec":
                     {
                        Object object = sessao.getAttribute("cobrancaID");
                        
                        if(object != null) {
                            int cobrancaID = (int) object;
                            Estado estado = gestaoAluno.cobrancasPorID(cobrancaID);
                            out.print( Converter.allEstadoToJSON(estado) );
                        } else {
                            object = sessao.getAttribute("alunoID");
                            if(object != null) {
                                int alunoID = (int) object;
                                
                                Estado estado =  gestaoAluno.listaDeCobrancasPorAluno(alunoID);
                                out.print( Converter.allEstadoToJSON(estado) );
                            }
                        }
                        
                        
                    }
                    break;
                case "pes":
                    {
                        String nome = request.getParameter("pesquisa");
                        String matricula = request.getParameter("matricula");
                        String mensalidade = request.getParameter("mensalidade");
                        String atividade = request.getParameter("atividade");
                        
                        sessao.setAttribute("pesquisa", nome);
                        sessao.setAttribute("matricula", matricula);
                        sessao.setAttribute("mensalidade", mensalidade);
                        sessao.setAttribute("atividade", atividade);
                        
                        Estado estado = gestaoAluno.pesquisarAlunos(nome, atividade, mensalidade, matricula);
                        out.print( Converter.allEstadoToJSON(estado) );
                    }
                    break;
                case "eps":
                    {
                        Estado estado = gestaoAluno.existePesquisa(sessao.getAttribute("pesquisa"),
                                sessao.getAttribute("atividade"), 
                                sessao.getAttribute("matricula"), 
                                sessao.getAttribute("mensalidade"));

                        out.print( Converter.allEstadoToJSON(estado) );  
                    }
                    break;
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
