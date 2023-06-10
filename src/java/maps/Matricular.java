/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package maps;

import com.google.gson.Gson;
import controlador.AlunoJpaController;
import controlador.AtividadeJpaController;
import controlador.CobrancaJpaController;
import controlador.MatriculaJpaController;
import controlador.SecretáriaJpaController;
import controlador.exceptions.RollbackFailureException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.transaction.UserTransaction;
import modelo.Aluno;
import modelo.Atividade;
import modelo.Cobranca;
import modelo.Matricula;
import utils.Converter;
import utils.Status;
import utils.Utils;

/**
 *
 * @author mahom
 */

@WebServlet(name = "matricula", urlPatterns = {"/matricula"})
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
            
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProGymPU");
            boolean status = true;
            
            UserTransaction ut = null;
            try {
                ut = InitialContext.doLookup("java:comp/UserTransaction");
            } catch (NamingException ex) {
                Logger.getLogger(Autenticacao.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            AlunoJpaController alunoCtrl = new AlunoJpaController(ut, emf);
            MatriculaJpaController matriculaCtrl = new MatriculaJpaController(ut, emf);
            AtividadeJpaController atividadeCtrl = new AtividadeJpaController(ut, emf);
            SecretáriaJpaController secretáriaCtrl = new SecretáriaJpaController(ut, emf);
            CobrancaJpaController cobrancaCtrl = new CobrancaJpaController(ut, emf);
            
            Gson gson = new Gson();
            HttpSession sessao = request.getSession();
            if(sessao.getAttribute("secretariaID") == null) {
                System.out.println("Matricula lst");
                out.print(gson.toJson(new Status(1, "Autenticação necessária")));
                return;
            }
            
            String operation = request.getParameter("op");
            Enumeration enumParams = request.getParameterNames();
            for (; enumParams.hasMoreElements(); ) {
                // Get the name of the request parameter
                String name = (String)enumParams.nextElement();

                // Get the value of the request parameters

                // If the request parameter can appear more than once 
                //   in the query string, get all values
                String[] values = request.getParameterValues(name);
                System.out.println(name);
                String sValues = "";
                for(int i=0;i<values.length;i++){
                    if(0<i) {
                        sValues+=",";
                    }
                    sValues +=values[i];
                }
                System.out.println("Param " + name + ": " + sValues);
            }
            
            if(operation.equals("pes")) 
            {
                String nome = request.getParameter("pesquisa");
                String matricula = request.getParameter("matricula");
                String mensalidade = request.getParameter("mensalidade");
                String atividade = request.getParameter("atividade");
                
                List<Aluno> alunos = alunoCtrl.findAlunoEntities();
                String js = Converter.alunosToJSON( Utils.searchAluno(nome, atividade, mensalidade, matricula,  alunos) );
                System.out.println(js);
                System.out.println(alunos.size());
                out.print( js );
                return;
            }
            
            switch(operation) 
            {
                case "add":
                    {
                        
                        String nome = request.getParameter("nome");
                        String email = request.getParameter("email");
                        String caminhoDaFoto = Utils.getImage(request, nome);
                        String dataNascimento = request.getParameter("data");
                        String[] atividades = request.getParameterValues("atividades");
                        System.out.println("Caminho da foto: " + caminhoDaFoto);
                        Aluno aluno  = new Aluno();
                        
                        if(!Utils.manageAluno(aluno, nome, email, caminhoDaFoto, dataNascimento, 
                                alunoCtrl, 0)) {
                            out.print(gson.toJson(new Status(1, "Erro ao cadastrar o aluno: É possível que já exista um aluno com o email inserido")));
                            return;
                        }
                        
                        for(String atividade : atividades) {
                            
                            try {
                                Matricula matricula = new Matricula();
                                Cobranca cobranca = new Cobranca();
                                Calendar dataInicio = Calendar.getInstance();
                                cobranca.setDataInicio(dataInicio.getTime());
                                Calendar dataTermino = Calendar.getInstance();
                                dataTermino.set(dataTermino.get(Calendar.YEAR), dataTermino.get(Calendar.MONTH), dataTermino.get(Calendar.DAY_OF_MONTH), dataTermino.get(Calendar.HOUR_OF_DAY)+5, dataTermino.get(Calendar.MINUTE), dataTermino.get(Calendar.SECOND));
                                cobranca.setDataTermino(dataTermino.getTime());
                                cobranca.setPago(false);
                                cobranca.setTaxa(0);
                                System.out.println("Valor: " + Utils.definirValor(atividade));
                                
                                System.out.printf("%f", Utils.definirValor(atividade));
                                cobranca.setValor( Utils.definirValor(atividade) );
                                System.out.printf("%f", cobranca.getValor());
                                
                                if(!Utils.manageMatricula(matricula, new Date(), 
                                        atividadeCtrl.findAtividade(Integer.parseInt(atividade)), 1, 
                                        aluno, secretáriaCtrl.findSecretária(1), matriculaCtrl, 0)) {
                                    out.print(gson.toJson(new Status(1, "Não foi possível eftuar a matrícula")));
                                    return;
                                }
                                
                                try {
                                    cobranca.setMatriculaID(matricula);
                                    cobrancaCtrl.create(cobranca);
                                } catch (Exception ex) {
                                    System.out.println("Não foi possível atricuir cobrança a matrícula.");
                                    status = false;
                                }
                            } catch(Exception e) {
                                status = false;
                            }
                        }
                        
                        if(status) {
                            out.print(gson.toJson(new Status(0, "Sucesso: ID#" + aluno.getAlunoID())));
                        }
                        else
                            out.print(gson.toJson(new Status(0, "Não foi possível matricula em alguma(s) disciplina(s)")));
                    }
                    
                    break;
                case "del":
                    {
                        int alunoID = Integer.parseInt(request.getParameter("alunoID"));
                        
                        try {
                            alunoCtrl.destroy(alunoID);
                            out.print(gson.toJson(new Status(1, "Aluno apagado com sucesso")));
                        } catch (RollbackFailureException ex) {
                            out.print(gson.toJson(new Status(1, "Erro ao apagar o aluno: " + ex.getMessage())));
                        } catch (Exception ex) {
                            out.print(gson.toJson(new Status(1, "Erro ao apagar aluno, aluno não existe.")));
                        }
                    }
                    break;
                case "lst":
                    {
                        System.out.println("List");
                        List<Aluno> alunos = alunoCtrl.findAlunoEntities();
                        
                        String alunosJSON =  Converter.alunosToJSON(alunos);
                        out.print(alunosJSON);
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

                                    Aluno aluno = alunoCtrl.findAluno(alunoID);
                                    if(!Utils.manageAluno(aluno, nome, email, caminhoDaFoto, 
                                            dataNascimento, alunoCtrl, 1)) {
                                        out.print(gson.toJson(new Status(1, "Erro ao editar o aluno")));
                                    }

                                    out.print(gson.toJson(new Status(0, "Conta editada com sucesso")));
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
                        Aluno aluno = alunoCtrl.findAluno(alunoID);
                        List<Matricula> matriculas = matriculaCtrl.findMatriculaEntities();
                        
                        if(aluno != null)
                            out.print(Converter.alunoWithMatriculaToJSON(aluno, matriculas));
                        else
                          out.print(gson.toJson(new Status(0, "O aluno não foi encontrado")));   
                    }
                    break;
                case "act":
                    {
                        List<Atividade> atividades = atividadeCtrl.findAtividadeEntities();
                        out.print(Converter.atividadesToJSON(atividades));
                    }
                    break;
                case "str": // Store
                    {
                        String alunoIDStr = request.getParameter("alunoID");
                        String cobrancaIDStr = request.getParameter("cobrancaID");
                        
                        if(alunoIDStr != null) {
                            int alunoID = Integer.parseInt(alunoIDStr);
                            sessao.setAttribute("alunoID", alunoID);
                            out.print(gson.toJson(new Status(0, "Armazenado")));
                        } else {
                            if(cobrancaIDStr != null) {
                                int cobrancaID = Integer.parseInt(cobrancaIDStr);
                                sessao.setAttribute("cobrancaID", cobrancaID);
                                out.print(gson.toJson(new Status(0, "Armazenado")));
                            } else {
                                sessao.removeAttribute("cobrancaID");
                                out.print(gson.toJson(new Status(1, "Já está lá")));
                            }
                        }
                    }
                    break;
                case "rec":
                     {
                        Object object = sessao.getAttribute("cobrancaID");
                        
                        if(object != null) {
                            int cobrancaID = (int) object;
                            out.print(Converter.cobrancaToJSON(cobrancaCtrl.findCobranca(cobrancaID)));
                        } else {
                            object = sessao.getAttribute("alunoID");
                            if(object != null) {
                                int alunoID = (int) object;
                                System.out.println("Chegou até aqui\n");
                                out.print(Converter.cobrancasToJSON( Utils.findCobrancasOf(alunoID, cobrancaCtrl.findCobrancaEntities())));
                            }
                        }
                        
                        
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
