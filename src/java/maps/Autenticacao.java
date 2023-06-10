///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
// */
//package maps;
//
//import com.google.gson.Gson;
//import controlador.SecretáriaJpaController;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.naming.InitialContext;
//import javax.naming.NamingException;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import javax.transaction.UserTransaction;
//import modelo.Secretária;
//import utils.Converter;
//import utils.Status;
//import utils.Utils;
//
///**
// *
// * @author mahom
// */
//public class Autenticacao extends HttpServlet {
//
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
//            EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProGymPU");
//            
//            UserTransaction ut = null;
//            try {
//                ut = InitialContext.doLookup("java:comp/UserTransaction");
//            } catch (NamingException ex) {
//                Logger.getLogger(Autenticacao.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            SecretáriaJpaController secretáriaCtrl = new SecretáriaJpaController(ut, emf);
//            
//            String operacao = request.getParameter("op");
//            
//            switch(operacao) {
//                case "aut":
//                    {
//                        String email = request.getParameter("email");
//                        String senha = request.getParameter("senha");
//
//                        Secretária secretária  = Utils.findSecretáriaWidthEmailAndPassword(email, senha, secretáriaCtrl.findSecretáriaEntities());
//
//                        if(secretária == null) {
//                            out.print(new Gson().toJson(new Status(1, "A secretária com as credenciais digitadas não foi encontrada")));
//                            return;
//                        }
//
//                        out.print(new Gson().toJson(new Status(0, "Cadastro efetuado com sucesso")));
//
//                        HttpSession sessao = request.getSession();
//                        sessao.setAttribute("secretariaID", secretária.getSecretáriaID());
//                        sessao.setAttribute("nome", secretária.getNome());
//                    }
//                    break;
//                case "inf":
//                    {
//                        HttpSession sessao = request.getSession();
//                        if( sessao.getAttribute("secretariaID") == null ) {
//                            out.print(new Gson().toJson(new Status(1, "Autenticação necessária")));
//                            return;
//                        }
//                        
//                        int secretáriaID = (int) sessao.getAttribute("secretariaID");
//                        Secretária secretária = secretáriaCtrl.findSecretária(secretáriaID);
//                        out.print(Converter.secretáriaToJSON(secretária));
//                    }
//                    break;
//                case "sir":
//                    {
//                        HttpSession sessao = request.getSession();
//                        sessao.removeAttribute("secretariaID");
//                        
//                        out.print((new Gson()).toJson(new Status(0, "Sessão terminaad com sucesso")));
//                    }
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
