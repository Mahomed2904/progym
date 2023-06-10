///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package utils;
//
//import com.google.gson.Gson;
//import controlador.AlunoJpaController;
//import controlador.MatriculaJpaController;
//import controlador.SecretáriaJpaController;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.Iterator;
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.Part;
//import modelo.Aluno;
//import modelo.Atividade;
//import modelo.Cobranca;
//import modelo.Matricula;
//import modelo.Pagamento;
//import modelo.Secretária;
//import org.apache.commons.fileupload.FileItem;
//import org.apache.commons.fileupload.FileItemFactory;
//import org.apache.commons.fileupload.FileUploadException;
//import org.apache.commons.fileupload.disk.DiskFileItemFactory;
//import org.apache.commons.fileupload.servlet.ServletFileUpload;
//
///**
// *
// * @author mahom
// */
//public class Utils {
//    public static String getImage(HttpServletRequest request, String nome)
//    {
//        Part filePart = null;
//        
//        try 
//        {
//            System.out.println("Tentanto manejar o arquivo");
//            filePart = request.getPart("foto");
//            
//            if(filePart == null) {
//                System.out.println("Nao ha foto");
//                return null;
//            }
//            
//            System.out.println("Size: " + filePart.getSize());
//            String fileName = filePart.getSubmittedFileName();
//            //System.out.println(filePart.getSubmittedFileName());
//
//            int index = fileName.indexOf('.');
//            String data = new Date().toLocaleString().replace("/", "_").replace(":", "_");
//
//            fileName = "imagem_" + nome + "_" + data + fileName.substring(index);
//            fileName = fileName.replace(" ", "_").toLowerCase();
//
//            File file = new File("//home//mahomed//NetBeansProjects//ProGym//web/images//" + fileName);
//
//            //System.out.println("Caminho da imagem: " + file.getAbsolutePath());
//
//            if( !file.isFile() ) {
//                try {
//                    if(file.createNewFile())
//                        System.out.println("\nArquivo criado com sucesso.\n");
//                } catch (IOException ex) {
//                    System.out.println("Ocorreu um erro ao criar a foto");
//                }
//
//                try {
//                    FileOutputStream fileo = new FileOutputStream(file);
//
//                    byte[] buffer = new byte[ (int) (filePart.getSize()) ];
//                    filePart.getInputStream().read(buffer);
//                    fileo.write( buffer );
//                    fileo.close();
//
//                } catch (FileNotFoundException ex) {
//                    System.out.println("Arquivo não foi encontrado");
//                }
//            }
//            System.out.printf("\n--Gravou o livro %s de tamanho %d -----------\n\n", fileName, filePart.getSize());
//            //livro.setUrlFoto("images/" + fileName);
//            return ("images/" + fileName);
//        } catch (IOException ex) {
//            System.out.println("Erro 1");
//        } catch (ServletException ex) {
//            System.out.println("Erro 2: ");
//            ex.printStackTrace();
//        }
//        
//        return null;
//    }
//    
//    public static List<Matricula> findMatriculasOf(int alunoID, List<Matricula> matriculas) {
//       List<Matricula> resultado = new ArrayList<Matricula>();
//       
//       for(Matricula matricula : matriculas) {
//           if(matricula.getAlunoID().getAlunoID() == alunoID)
//               resultado.add(matricula);
//       }
//       
//       return resultado;
//    }
//    
//    public static List<Pagamento> findPagamentosOf(int alunoID, List<Pagamento> pagamentos) {
//       List<Pagamento> resultado = new ArrayList<>();
//       
//       for(Pagamento pagamento : pagamentos) {
//           if(pagamento.getAlunoID().getAlunoID() == alunoID)
//               resultado.add(pagamento);
//       }
//       
//       return resultado;
//    }
//    
//    public static List<Cobranca> findCobrancasOf(int alunoID, List<Cobranca> cobrancas) {
//       List<Cobranca> resultado = new ArrayList<>();
//       
//       for(Cobranca cobranca : cobrancas) {
//           if(cobranca.getMatriculaID().getAlunoID().getAlunoID() == alunoID && !cobranca.getPago())
//               resultado.add(cobranca);
//       }
//       
//       return resultado;
//    }
//    
//    public static Aluno findAlunoWidthEmail(String email, List<Aluno> alunos) {
//        for(Aluno aluno : alunos) {
//            if(email.equals(aluno.getEmail()))
//                return aluno;
//        }
//        return null;
//    }
//    
//    public static List<Aluno> searchAlunoByEmail(String email, List<Aluno> alunos) {
//        List<Aluno> resultado = new ArrayList<>();
//        for(Aluno aluno : alunos) {
//            if(email.contains(aluno.getEmail()))
//                resultado.add(aluno);
//        }
//        return resultado;
//    }
//    
//    public static List<Aluno> searchAlunoByNome(String nome, List<Aluno> alunos) {
//        List<Aluno> resultado = new ArrayList<>();
//        for(Aluno aluno : alunos) {
//            System.out.println("Aluno: " + aluno.getNome() + "  Chave: " + nome);
//            if(aluno.getNome().toLowerCase().contains(nome.toLowerCase()))
//                resultado.add(aluno);
//        }
//        return resultado;
//    }
//    
//    public static List<Aluno> searchAluno(String nome, String atividade, String estadoMensalidade,
//            String estadoMatricula, List<Aluno> alunos) {
//        List<Aluno> resultado = new ArrayList<>();
//        for(Aluno aluno : alunos) {
//            System.out.println("Aluno: " + aluno.getNome() + "  Está matriculado: " + estaMatriculado(atividade, aluno) 
//            + " , atividade: ");
//            if(aluno.getNome().toLowerCase().contains(nome.toLowerCase())
//                    && estaMatriculado(atividade, aluno) 
//                    && eEstadoMensalidade(estadoMensalidade, aluno)
//                    && eEstadoMatriculado(estadoMatricula, aluno)) {
//                System.out.println("Adicionou...");
//                resultado.add(aluno);
//            }
//        }
//        return resultado;
//    }
//    
//    public static boolean estaMatriculado(String atividade, Aluno aluno) {
//        int acts = Integer.parseInt(atividade);
//        
//        if(acts == 0)
//            return true;
//        
//        for(Matricula m : aluno.getMatriculaList()) {
//            if(m.getAtividadeID().getAtividadeID() == acts)
//                return true;
//        }
//        
//        return false;
//    }
//    
//    public static boolean eEstadoMensalidade(String estadoStr, Aluno aluno) {
//        int estado = Integer.parseInt(estadoStr);
//        
//        if(estado == 0)
//            return true;
//        
//        boolean decisao = estado == 1;
//        
//        for(Matricula m : aluno.getMatriculaList()) {
//            for(Cobranca c : m.getCobrancaList()) {
//                if(c.getPago() == decisao)
//                    return true;
//            }
//        }
//
//        return false;
//    }
//    
//    public static boolean eEstadoMatriculado(String estadoStr, Aluno aluno) {
//        int estado = Integer.parseInt(estadoStr);
//        
//        if(estado == 0)
//            return true;
//        
//        
//        for(Matricula m : aluno.getMatriculaList()) {
//            if( m.getEstado() == estado )
//                return true;
//        }
//            
//        return false;
//    }
//    
//    public static Secretária findSecretáriaWidthEmailAndPassword(String email, String senha, List<Secretária> secretárias) {
//        for(Secretária secretária : secretárias) {
//            if(email.equals(secretária.getEmail()) && senha.equals(secretária.getSenha()))
//                return secretária;
//        }
//        return null;
//    }
//    
//    public static boolean manageAluno(Aluno aluno, String nome, String email, 
//        String caminhoDaFoto, String dataNascimento, AlunoJpaController alunoCtrl,
//        int opcao) {
//        
//        if(nome != null)
//            aluno.setNome(nome);
//        
//        if(email != null)
//            aluno.setEmail(email);
//        
//        if(caminhoDaFoto != null)
//            aluno.setFotoUrl(caminhoDaFoto);
//        
//        if(dataNascimento != null)
//            try {
//                aluno.setDataNascimento(new SimpleDateFormat("YYYY-MM-dd").parse(dataNascimento) );
//            } catch (ParseException ex) {
//                System.out.println("Erro ao formatar a data de nascimento");
//            }
//
//        try {
//            if(opcao == 0)
//                alunoCtrl.create(aluno);
//            else
//                alunoCtrl.edit(aluno);
//        } catch (Exception ex) { 
//            return false;
//        }
//        
//        return true;
//    }
//    
//    public static boolean manageMatricula(Matricula matricula, Date data, Atividade atividade, 
//        int estado, Aluno aluno, Secretária secretária, MatriculaJpaController matriculaCtrl, 
//        int opcao) {
//        
//        if(data != null)
//            matricula.setData(data);
//        
//        if(atividade != null)
//            matricula.setAtividadeID(atividade);
//        
//        if(estado != -1)
//            matricula.setEstado(estado);
//        
//        if(aluno != null)
//            matricula.setAlunoID(aluno);
//        
//        if(secretária != null)
//            matricula.setSecretáriaID(secretária);
//        
//        
//        try {
//            if(opcao == 0)
//                matriculaCtrl.create(matricula);
//            else
//                matriculaCtrl.edit(matricula);
//        } catch(Exception ex) {
//            return false;
//        }
//        
//        return true;
//    }
//    
//    
//    
//    public static float definirValor(String nomeAtividade) {
//        switch(nomeAtividade) {
//            case "1":
//                return 1500.0f;
//            case "2":
//                return 1380.0f;
//            case "3":
//                return 1500.0f;
//            case "4":
//                return 1658.0f;
//        }
//        
//        return 1600.0f;
//    }
//}
//
//
