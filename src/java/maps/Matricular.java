///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
// */
//package maps;
//
//import com.google.gson.Gson;
//import controlador.AlunoJpaController;
//import controlador.AtividadeJpaController;
//import controlador.CobrancaJpaController;
//import controlador.MatriculaJpaController;
//import controlador.SecretáriaJpaController;
//import controlador.exceptions.RollbackFailureException;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.Enumeration;
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.naming.InitialContext;
//import javax.naming.NamingException;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.MultipartConfig;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import javax.servlet.http.Part;
//import javax.transaction.UserTransaction;
//import modelo.Aluno;
//import modelo.Atividade;
//import modelo.Cobranca;
//import modelo.Matricula;
//import servicos.GestaoAluno;
//import utils.Converter;
//import utils.Status;
//import utils.Utils;
//
///**
// *
// * @author mahom
// */
//
//@WebServlet(name = "matricula", urlPatterns = {"/matricula"})
//@MultipartConfig(fileSizeThreshold=1024*1024*10,    // 10 MB 
//                 maxFileSize=1024*1024*50,          // 50 MB
//                 maxRequestSize=1024*1024*100,      // 100 MB
//                 location="/")
//
//
//public class Matricular extends HttpServlet {
//    /**
//     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
//     * methods.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("application/json;charset=UTF-8");
//        try ( PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            
//            GestaoAluno gestaoAluno = new GestaoAluno();
//            
//            HttpSession sessao = request.getSession();
//            if(sessao.getAttribute("secretariaID") == null) {
//                System.out.println("Matricula lst");
//                out.print( gestaoAluno.getErroDeAutenticacao() );
//                return;
//            }
//            
//            String operation = request.getParameter("op");
//            
//            if(operation.equals("pes")) 
//            {
//                String nome = request.getParameter("pesquisa");
//                String matricula = request.getParameter("matricula");
//                String mensalidade = request.getParameter("mensalidade");
//                String atividade = request.getParameter("atividade");
//                out.print( gestaoAluno.pesquisarAlunos(nome, atividade, mensalidade, matricula) );
//                return;
//            }
//            
//            switch(operation) 
//            {
//                case "add":
//                    {
//                        
//                        String nome = request.getParameter("nome");
//                        String email = request.getParameter("email");
//                        String caminhoDaFoto = Utils.getImage(request, nome);
//                        String dataNascimento = request.getParameter("data");
//                        String[] atividades = request.getParameterValues("atividades");
//                        
//                        out.print( gestaoAluno.adicionarMatricula(nome, email, caminhoDaFoto, dataNascimento, atividades));
//                    }
//                    break;
//                case "del":
//                    {
//                        int alunoID = Integer.parseInt(request.getParameter("alunoID"));
//                        out.print(gestaoAluno.apagarMatricula(alunoID));
//                    }
//                    break;
//                case "lst":
//                    {
//                        out.print(gestaoAluno.listaDeEstudantes());
//                    }
//                    break;
//                case "edt":
//                    {
//                        String opcao = request.getParameter("opc");
//                        
//                        switch(opcao) {
//                            case "aluno":
//                                {
//                                    int alunoID = Integer.parseInt(request.getParameter("alunoID"));
//                                    String nome = request.getParameter("nome");
//                                    String email = request.getParameter("email");
//                                    String caminhoDaFoto = Utils.getImage(request, nome);
//                                    String dataNascimento = request.getParameter("datanascimento");
//                                    out.print(gestaoAluno.editarAluno(alunoID, nome, email, caminhoDaFoto, dataNascimento));
//                                }
//                                break;
//                            case "matricula":
//                                {
//                                    
//                                }
//                        }
//                    }
//                    break;
//                case "fnd":
//                    {
//                        int alunoID = (int) sessao.getAttribute("alunoID");
//                        out.print(gestaoAluno.informacoesAluno(alunoID));
//                    }
//                    break;
//                case "act":
//                    {
//                       out.print( gestaoAluno.listaDeAtividades() );
//                    }
//                    break;
//                case "str": // Store
//                    {
//                        String alunoIDStr = request.getParameter("alunoID");
//                        String cobrancaIDStr = request.getParameter("cobrancaID");
//                        
//                        if(alunoIDStr != null) {
//                            int alunoID = Integer.parseInt(alunoIDStr);
//                            sessao.setAttribute("alunoID", alunoID);
//                            out.print( gestaoAluno.getJSONSucesso() );
//                        } else {
//                            if(cobrancaIDStr != null) {
//                                int cobrancaID = Integer.parseInt(cobrancaIDStr);
//                                sessao.setAttribute("cobrancaID", cobrancaID);
//                                out.print( gestaoAluno.getJSONSucesso() );
//                            } else {
//                                sessao.removeAttribute("cobrancaID");
//                                out.print(gestaoAluno.getJSONSucesso());
//                            }
//                        }
//                    }
//                    break;
//                case "rec":
//                     {
//                        Object object = sessao.getAttribute("cobrancaID");
//                        
//                        if(object != null) {
//                            int cobrancaID = (int) object;
//                            out.print(gestaoAluno.cobrancasPorID(cobrancaID));
//                        } else {
//                            object = sessao.getAttribute("alunoID");
//                            if(object != null) {
//                                int alunoID = (int) object;
//                                out.print( gestaoAluno.listaDeCobrancasPorAluno(alunoID));
//                            }
//                        }
//                        
//                        
//                    }
//                    break;
//            }
//        }
//    }
//
//    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
//    /**
//     * Handles the HTTP <code>GET</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    /**
//     * Handles the HTTP <code>POST</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    /**
//     * Returns a short description of the servlet.
//     *
//     * @return a String containing servlet description
//     */
//    @Override
//    public String getServletInfo() {
//        return "Short description";
//    }// </editor-fold>
//
//}
