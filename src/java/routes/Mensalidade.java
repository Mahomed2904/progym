/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package routes;

import com.google.gson.Gson;
import controlador.AlunoJpaController;
import controlador.AtividadeJpaController;
import controlador.CobrancaJpaController;
import controlador.MatriculaJpaController;
import controlador.PagamentoJpaController;
import controlador.SecretáriaJpaController;
import java.io.IOException;
import java.io.PrintWriter;
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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import modelo.Cobranca;
import modelo.Matricula;
import modelo.Pagamento;
import utils.Converter;
import utils.Status;
import utils.Utils;

/**
 *
 * @author mahom
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
        response.setContentType("application/json;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProGymPU");
            boolean status = true;
            
            UserTransaction ut = null;
            try {
                ut = InitialContext.doLookup("java:comp/UserTransaction");
            } catch (NamingException ex) {
                Logger.getLogger(Autenticar.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            AlunoJpaController alunoCtrl = new AlunoJpaController(ut, emf);
            MatriculaJpaController matriculaCtrl = new MatriculaJpaController(ut, emf);
            AtividadeJpaController atividadeCtrl = new AtividadeJpaController(ut, emf);
            PagamentoJpaController pagamentoCtrl = new PagamentoJpaController(ut, emf);
            SecretáriaJpaController secretáriaCtrl = new SecretáriaJpaController(ut, emf);
            CobrancaJpaController cobrancaCtrl = new CobrancaJpaController(ut, emf);
            
            Gson gson = new Gson();
            
            HttpSession sessao = request.getSession();
            if(sessao.getAttribute("secretariaID") == null) {
                System.out.println("Aqui");
                out.print(gson.toJson(new Status(1, "Autenticação necessária")));
                return;
            }
            
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
            
            String operation = request.getParameter("op");
            switch(operation) 
            {
                case "add":
                    {
                        int cobrancaID = (int) (sessao.getAttribute("cobrancaID") != null ? sessao.getAttribute("cobrancaID") : -1); 
                        
                        int secretáriaID = (int) sessao.getAttribute("secretariaID");
                        double valor = Double.parseDouble(request.getParameter("valor"));
                        String codigoRecibo = request.getParameter("recibo");
                        String banco = request.getParameter("banco");
                        
                        if(cobrancaID == -1) {
                            int alunoID = (int) sessao.getAttribute("alunoID");
                            List<Cobranca> cobrancas = Utils.findCobrancasOf(alunoID, cobrancaCtrl.findCobrancaEntities());
                            
                            for(Cobranca cobranca : cobrancas) {
                                if(!cobranca.getPago()) 
                                {
                                    Pagamento pagamento = new Pagamento();
                                    pagamento.setAlunoID((alunoCtrl.findAluno(alunoID)));
                                    pagamento.setData(new Date());
                                    pagamento.setMatriculaID(cobranca.getMatriculaID());
                                    pagamento.setSecretáriaID(secretáriaCtrl.findSecretária(secretáriaID));
                                    pagamento.setTaxa((double) cobranca.getTaxa());
                                    pagamento.setBanco(banco);
                                    pagamento.setValor((double) cobranca.getValor());
                                    valor -= cobranca.getValor();
                                    pagamento.setCodigoRecibo(codigoRecibo);
                                    pagamento.setCobrancaID(cobranca);
                                    
                                    try {
                                        cobranca.setPago(true);
                                        cobrancaCtrl.edit(cobranca);
                                        pagamentoCtrl.create(pagamento);
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                        out.print(gson.toJson(new Status(1, "Erro ao fazer o pagamento: " + ex.getMessage())));
                                        return;
                                    }
                                }
                            }
                            out.print(gson.toJson(new Status(0, "Papagemnto efetuado com sucesso")));
                            return;
                        }
                        
                        Cobranca cobranca = cobrancaCtrl.findCobranca(cobrancaID);
                        Pagamento pagamento = new Pagamento();
                        
                        if(cobranca.getPago()) {
                            out.print("A cobranca já foi paga");
                            return;
                        }
                        
                        pagamento.setAlunoID((alunoCtrl.findAluno((int) sessao.getAttribute("alunoID"))));
                        pagamento.setData(new Date());
                        pagamento.setMatriculaID(cobranca.getMatriculaID());
                        pagamento.setSecretáriaID(secretáriaCtrl.findSecretária(secretáriaID));
                        pagamento.setTaxa((double) cobranca.getTaxa());
                        pagamento.setBanco(banco);
                        pagamento.setValor(valor);
                        pagamento.setCodigoRecibo(codigoRecibo);
                        pagamento.setCobrancaID(cobranca);
                        
          
                        
                        try {
                            cobranca.setPago(true);
                            cobrancaCtrl.edit(cobranca);
                            pagamentoCtrl.create(pagamento);
                            out.print(gson.toJson(new Status(0, "Papagemnto efetuado com sucesso")));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            out.print(gson.toJson(new Status(1, "Erro ao fazer o pagamento" + ex.getMessage())));
                            return;
                        }
                    }
                    break;
                case "lst":
                    {
                        int alunoID = Integer.parseInt( request.getParameter("alunoID") );
                        
                        List<Pagamento> pagamentos = pagamentoCtrl.findPagamentoEntities();
                        List<Pagamento> pagamentosFeitos = Utils.findPagamentosOf(alunoID, pagamentos);
                        
                        out.append( Converter.pagamentosToJSON(pagamentosFeitos) );
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
