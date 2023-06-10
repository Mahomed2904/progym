///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package servicos;
//
//import com.google.gson.Gson;
//import controlador.AlunoJpaController;
//import controlador.AtividadeJpaController;
//import controlador.CobrancaJpaController;
//import controlador.MatriculaJpaController;
//import controlador.SecretáriaJpaController;
//import controlador.exceptions.RollbackFailureException;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.naming.InitialContext;
//import javax.naming.NamingException;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//import javax.transaction.UserTransaction;
//import maps.Autenticacao;
//import modelo.Aluno;
//import modelo.Atividade;
//import modelo.Cobranca;
//import modelo.Matricula;
//import utils.Converter;
//import utils.Status;
//import utils.Utils;
//
///**
// *
// * @author mahomed
// */
//public class GestaoAluno 
//{
//    private AlunoJpaController alunoCtrl;
//    private MatriculaJpaController matriculaCtrl;
//    private AtividadeJpaController atividadeCtrl;
//    private SecretáriaJpaController secretáriaCtrl;
//    private CobrancaJpaController cobrancaCtrl;
//    private UserTransaction ut;
//    private Gson gson;
//
//    public GestaoAluno() {
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProGymPU");
//        try {
//            ut = InitialContext.doLookup("java:comp/UserTransaction");
//        } catch (NamingException ex) {
//            Logger.getLogger(GestaoAluno.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        this.alunoCtrl = new AlunoJpaController(ut, emf);
//        this.matriculaCtrl = new MatriculaJpaController(ut, emf);
//        this.atividadeCtrl = new AtividadeJpaController(ut, emf);
//        this.secretáriaCtrl = new SecretáriaJpaController(ut, emf);
//        this.cobrancaCtrl = new CobrancaJpaController(ut, emf);
//        this.gson = new Gson();
//    }
//    
//    public String pesquisarAlunos(String nome, String atividade, 
//            String mensalidade, String matricula) {
//        List<Aluno> alunos = alunoCtrl.findAlunoEntities();
//        return Converter.alunosToJSON( Utils.searchAluno(nome, atividade, mensalidade, matricula,  alunos) );
//    }
//    
//    public String adicionarMatricula(String nome, String email, String caminhoDaFoto, 
//            String dataNascimento, String[] atividades) {
//        Aluno aluno  = new Aluno();
//        boolean status =  true;
//        System.out.println("Tentando adicionar matricula");
//        if(!Utils.manageAluno(aluno, nome, email, caminhoDaFoto, dataNascimento, 
//                alunoCtrl, 0)) {
//            return (gson.toJson(new Status(1, "Erro ao cadastrar o aluno: É possível que já exista um aluno com o email inserido"))); 
//        }
//
//        for(String atividade : atividades) {
//            try {
//                Matricula matricula = new Matricula();
//                Cobranca cobranca = new Cobranca();
//                Calendar dataInicio = Calendar.getInstance();
//                cobranca.setDataInicio(dataInicio.getTime());
//                Calendar dataTermino = Calendar.getInstance();
//                dataTermino.set(dataTermino.get(Calendar.YEAR), dataTermino.get(Calendar.MONTH), dataTermino.get(Calendar.DAY_OF_MONTH), dataTermino.get(Calendar.HOUR_OF_DAY)+5, dataTermino.get(Calendar.MINUTE), dataTermino.get(Calendar.SECOND));
//                cobranca.setDataTermino(dataTermino.getTime());
//                cobranca.setPago(false);
//                cobranca.setTaxa(0);
//                System.out.println("Valor: " + Utils.definirValor(atividade));
//
//                System.out.printf("%f", Utils.definirValor(atividade));
//                cobranca.setValor( Utils.definirValor(atividade) );
//                System.out.printf("%f", cobranca.getValor());
//
//                if(!Utils.manageMatricula(matricula, new Date(), 
//                        atividadeCtrl.findAtividade(Integer.parseInt(atividade)), 1, 
//                        aluno, secretáriaCtrl.findSecretária(1), matriculaCtrl, 0)) {
//                    return gson.toJson(new Status(1, "Não foi possível eftuar a matrícula"));
//                }
//
//                try {
//                    cobranca.setMatriculaID(matricula);
//                    cobrancaCtrl.create(cobranca);
//                } catch (Exception ex) {
//                    System.out.println("Não foi possível atricuir cobrança a matrícula.");
//                    status = false;
//                }
//            } catch(Exception e) {
//                status = false;
//            }
//        }
//
//        if(status) {
//            return gson.toJson(new Status(0, "Sucesso: ID#" + aluno.getAlunoID()));
//        }
//        else
//            return gson.toJson(new Status(0, "Não foi possível matricula em alguma(s) disciplina(s)"));
//
//    }
//    
//    public String apagarMatricula(int alunoID) {                
//        try {
//            alunoCtrl.destroy(alunoID);
//            return gson.toJson(new Status(0, "Aluno apagado com sucesso"));
//        } catch (RollbackFailureException ex) {
//            return gson.toJson(new Status(1, "Erro ao apagar o aluno: " + ex.getMessage()));
//        } catch (Exception ex) {
//            return gson.toJson(new Status(1, "Erro ao apagar aluno, aluno não existe."));
//        }
//    }
//    
//    public String listaDeEstudantes() {
//        List<Aluno> alunos = alunoCtrl.findAlunoEntities();
//        return Converter.alunosToJSON(alunos);
//    }
//    
//    public String editarAluno(int alunoID, String nome, String email, String caminhoDaFoto, 
//            String dataNascimento) {
//        Aluno aluno = alunoCtrl.findAluno(alunoID);
//        if(!Utils.manageAluno(aluno, nome, email, caminhoDaFoto, 
//                dataNascimento, alunoCtrl, 1)) {
//            return gson.toJson(new Status(1, "Erro ao editar o aluno"));
//        }
//
//        return gson.toJson(new Status(0, "Conta editada com sucesso"));
//    }
//    
//    public String editarMatricula() {
//        return null;
//    }
//    
//    public String informacoesAluno(int alunoID) {
//        Aluno aluno = alunoCtrl.findAluno(alunoID);
//        List<Matricula> matriculas = matriculaCtrl.findMatriculaEntities();
//
//        if(aluno != null)
//            return Converter.alunoWithMatriculaToJSON(aluno, matriculas);
//        else
//            return gson.toJson(new Status(0, "O aluno não foi encontrado")); 
//    }
//    
//    public String listaDeAtividades() {
//        List<Atividade> atividades = atividadeCtrl.findAtividadeEntities();
//        return Converter.atividadesToJSON(atividades);
//    }
//    
//    public String getJSONSucesso() {
//        return gson.toJson(new Status(0, "Armazenado"));
//    }
//    
//    public String getJSONFracasso() {
//        return gson.toJson(new Status(0, "Erro no armazenamento"));
//    }
//    
//    public String cobrancasPorID(int cobrancaID) {
//        Cobranca cobranca = cobrancaCtrl.findCobranca(cobrancaID);
//        if(cobranca == null)
//            return gson.toJson(new Status(0, "Cobranca não encontrada"));
//        
//        return Converter.cobrancaToJSON(cobranca);
//    }
//    
//    public String listaDeCobrancasPorAluno(int alunoID) {
//        return Converter.cobrancasToJSON( Utils.findCobrancasOf(alunoID, cobrancaCtrl.findCobrancaEntities()));
//    }
//    
//    public String getErroDeAutenticacao() {
//        return gson.toJson(new Status(1, "Autenticação necessária"));
//    }
//}
