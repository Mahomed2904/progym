/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicos;

import com.google.gson.Gson;
import controlador.AlunoJpaController;
import controlador.AtividadeJpaController;
import controlador.CobrancaJpaController;
import controlador.MatriculaJpaController;
import controlador.PagamentoJpaController;
import controlador.SecretáriaJpaController;
import controlador.exceptions.IllegalOrphanException;
import controlador.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Pagamento;
import modelo.Aluno;
import modelo.Atividade;
import modelo.Cobranca;
import modelo.Matricula;
import modelo.Secretária;
import utils.Converter;
import utils.Estado;
import utils.Pesquisa;
import static utils.TipoDeAnexo.*;
import utils.Utils;

/**
 *
 * @author mahomed
 */
public class GestaoAluno 
{
    private final AlunoJpaController alunoCtrl;
    private final MatriculaJpaController matriculaCtrl;
    private final AtividadeJpaController atividadeCtrl;
    private final SecretáriaJpaController secretáriaCtrl;
    private final CobrancaJpaController cobrancaCtrl;
    PagamentoJpaController pagamentoCtrl;
    private final Gson gson;

    public GestaoAluno() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProGymPU");
        this.alunoCtrl = new AlunoJpaController(emf);
        this.matriculaCtrl = new MatriculaJpaController(emf);
        this.atividadeCtrl = new AtividadeJpaController(emf);
        this.secretáriaCtrl = new SecretáriaJpaController(emf);
        this.cobrancaCtrl = new CobrancaJpaController(emf);
        this.pagamentoCtrl = new PagamentoJpaController(emf);
        this.gson = new Gson();
    }
    
    public Estado autenticarSecretária(String email, String senha) {
        
        if(!Utils.validarEmail(email))
            return new Estado(1,"Email inválido. Insira um email válido.", null, NENHUM);
        
        if(!Utils.validarSenha(senha))
            return new Estado(1,"Senha inválida, insira uma senha válida.", null, NENHUM);
        
        Secretária secretária  = Utils.findSecretáriaWidthEmailAndPassword(email, senha, secretáriaCtrl.findSecretáriaEntities());
        if(secretária == null) {
            return new Estado(1, "Utilizador e/ou senha inválido (os)", null, NENHUM);
        }
        return new Estado(0, "Cadastro efetuado com sucesso", secretária, SECRETÁRIA);
    }
    
    public Estado informacoesDaSecretária(int secretáriaID) {
        Secretária secretária = secretáriaCtrl.findSecretária(secretáriaID);
        if(secretária == null)
            return new Estado(1, "A secretária não foi encontrada", null, NENHUM);
        
        return new Estado(0, "Informações da secretária encontradas", secretária, SECRETÁRIA);
    }
    
    public Estado terminarSessão() {
        return new Estado(0, "Sessão terminada com sucesso", null, NENHUM);
    }
    
    public Estado pesquisarAlunos(String nome, String atividade, 
            String mensalidade, String matricula) {
        if(nome == null || atividade == null || mensalidade == null || matricula == null)
            return new Estado(1, "Existe(m) prâmentro(s) de pesquisa inválido(s)", null, NENHUM);
        
        List<Aluno> alunos = Utils.searchAluno(nome, atividade, mensalidade, matricula,  alunoCtrl.findAlunoEntities());
        
        if(alunos.isEmpty())
            return new Estado(1, "Nehum aluno foi encontrado", alunos, ALUNOS);
        
        return new Estado(0, "Alunos encontrados com sucesso", alunos, ALUNOS);
    }
    
    public Estado adicionarMatricula(String nome, String email, String caminhoDaFoto, 
            String dataNascimento, String[] atividades) {
        if(nome == null || email == null || caminhoDaFoto == null || dataNascimento == null || atividades == null || atividades.length == 0)
            return new Estado(1, "Existe(m) parâmetro(s) de matrícula inválido(s)", null, NENHUM);
        
        if(!Utils.validarNome(nome) && !Utils.validarEmail(email))
            return new Estado(1, "Email ou senha inválidos", null, NENHUM);
        
        Aluno aluno  = new Aluno();
        boolean status =  true;
        if(!Utils.manageAluno(aluno, nome, email, caminhoDaFoto, dataNascimento, 
                alunoCtrl, 0)) {
            return new Estado(1, "O cadastro não foi bem sucedido, É possível que já exista um aluno com o email inserido", null, NENHUM); 
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
               
                cobranca.setValor( Utils.definirValor(atividade) );

                if(!Utils.manageMatricula(matricula, new Date(), 
                        atividadeCtrl.findAtividade(Integer.parseInt(atividade)), 1, 
                        aluno, secretáriaCtrl.findSecretária(1), matriculaCtrl, 0)) {
                    apagarMatricula(aluno.getAlunoID());
                    return new Estado(1, "Não foi possível eftuar uma matrícula", null, NENHUM);
                }

                try {
                    cobranca.setMatriculaID(matricula);
                    cobrancaCtrl.create(cobranca);
                } catch (Exception ex) {
                    status = false;
                }
            } catch(NumberFormatException e) {
                status = false;
            }
        }

        if(status) {
            return new Estado(0, "Cadastro do aluno e matrícula(s) efetuado(s) com sucesso", aluno, ALUNO);
        }
        else {
            apagarMatricula(aluno.getAlunoID());
            return new Estado(1, "Ocorreu um erro no cadastro do aluno e matrícula(s)", aluno, ALUNO);
        }
    }
    
    public Estado apagarMatricula(int alunoID) {                
        try {
            Aluno aluno = alunoCtrl.findAluno(alunoID);

            for(Matricula matricula : aluno.getMatriculaList()) {
                for(Cobranca cobranca : matricula.getCobrancaList()) {
                    cobrancaCtrl.destroy(cobranca.getCobrancaID());
                    
                    if(cobranca.getPago())
                        pagamentoCtrl.destroy(cobranca.getPagamento().getPagamentoID());
                }
                
                matriculaCtrl.destroy(matricula.getMatriculaID());
            }
            
            alunoCtrl.destroy(alunoID);
            return new Estado(0, "Aluno apagado com sucesso", null, NENHUM);
        } catch (IllegalOrphanException | NonexistentEntityException | NullPointerException ex) {
            return new Estado(1, "O ID do aluno fornecido não existe.", null, NENHUM);
        }
    }
    
    public Estado listaDeAlunos() {
        List<Aluno> alunos = alunoCtrl.findAlunoEntities();
        
        if(alunos.isEmpty())
            return new Estado(1, "Nenhum aluno foi encontrado", alunos, ALUNOS);
        
        return new Estado(0, "Lista de alunos", alunos, ALUNOS);
    }
    
    public Estado editarAluno(int alunoID, String nome, String email, String caminhoDaFoto, 
            String dataNascimento) {
        Aluno aluno = alunoCtrl.findAluno(alunoID);
        
        if(aluno == null)
            return new Estado(1, "O aluno com ID fornecido não existe.", null, NENHUM);
        
        if(!Utils.manageAluno(aluno, nome, email, caminhoDaFoto, 
                dataNascimento, alunoCtrl, 1)) {
            return new Estado(1, "Não é possível alterar os dados do aluno", null, NENHUM);
        }
        
        return new Estado(0, "Dados do aluno editados com sucesso", aluno, ALUNO);
    }
    
    public Estado editarMatricula() {
        return null;
    }
    
    public Estado informacoesAluno(int alunoID) {
        Aluno aluno = alunoCtrl.findAluno(alunoID);

        if(aluno == null)
            return new Estado(1, "O aluno com ID fornecido não existe.", null, NENHUM);
        
        return new Estado(0, "Informações do aluno fornecido", aluno, ALUNO_WITH_MATRICULA);
    }
    
    public Estado listaDeAtividades() {
        List<Atividade> atividades = atividadeCtrl.findAtividadeEntities();
        
        if(atividades.isEmpty())
            return new Estado(1, "Nenhuma atividade foi encontrada", atividades, ATIVIDADES);
        
        return new Estado(0, "Lista de atividades encontradas", atividades, ATIVIDADES);
    }
    
    public Estado getEstadoSucesso() {
        return new Estado(0, "Informação armazenada com sucesso", null, NENHUM);
    }
    
    public Estado getJEstadoFracasso() {
        return new Estado(1, "Não foi possível armazenar a informação", null, NENHUM);
    }
    
    public Estado cobrancasPorID(int cobrancaID) {
        Cobranca cobranca = cobrancaCtrl.findCobranca(cobrancaID);
        if(cobranca == null)
            return new Estado(1, "A cobranca com o ID fornecido não encontrada", null, NENHUM);
        
        return new Estado(0, "Cobrança", cobranca, COBRANCA);
    }
    
    public Estado listaDeCobrancasPorAluno(int alunoID) {
        List<Cobranca> cobrancas = Utils.findCobrancasOf(alunoID, cobrancaCtrl.findCobrancaEntities());
        
        if(cobrancas.isEmpty())
            return new Estado(1, "Nenhuma cobrança foi encontrada", cobrancas, COBRANCAS);
        
        return new Estado(0, "Cobranças encontradas", cobrancas, COBRANCAS);
    }
    
    public Estado getErroDeAutenticacao() {
        return new Estado(2, "Autenticação necessária", null, NENHUM);
    }
    
    public Estado pagarTodasCobrancas(int alunoID, int secretáriaID, double valor, String banco, String codigoRecibo) {
        List<Cobranca> cobrancas =  Utils.findCobrancasOf(alunoID, cobrancaCtrl.findCobrancaEntities());
        List<Pagamento> pagamentos = new ArrayList<>();
        
        for(Cobranca cobranca : cobrancas) {
            Pagamento pagamento = new Pagamento();
            pagamento.setAlunoID(alunoCtrl.findAluno(alunoID) );
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
                pagamentos.add(pagamento);
            } catch (Exception ex) {
                return new Estado(1, "Erro ao fazer o pagamento: " + ex.getMessage(), pagamentos, PAGAMENTOS);
            }
        }
        
        if(cobrancas.isEmpty())
            return new Estado(1, "Não há mensalidades por pagar", null, NENHUM);
        
        return new Estado(0, "Pagagemnto efetuado com sucesso", pagamentos, PAGAMENTOS);
    }
    
    public Estado pagarACobranca(int alunoID, int secretáriaID, int cobrancaID,
            double valor, String banco, String codigoRecibo) {
        Cobranca cobranca = cobrancaCtrl.findCobranca(cobrancaID);
        Pagamento pagamento = new Pagamento();
        
        if(cobranca.getPago()) {
           return new Estado(1, "Esta mensalidade já foi paga.", null, NENHUM);
        }

        pagamento.setAlunoID(alunoCtrl.findAluno(alunoID));
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
            return new Estado(0, "Papagemnto efetuado com sucesso", pagamento, PAGAMENTO);
        } catch (Exception ex) {
            return new Estado(1, "Erro ao fazer o pagamento", null, NENHUM);
        }
    }
    
    public Estado listaDePagamentosPorAluno(int alunoID) {
        List<Pagamento> pagamentosFeitos = alunoCtrl.findAluno(alunoID).getPagamentoList();
        
        if(pagamentosFeitos.isEmpty())
            return new Estado(1, "Nenhum paagmento foi encontrado", pagamentosFeitos, PAGAMENTOS);
        
        return new Estado(0, "Pagamentos encontrados", pagamentosFeitos, PAGAMENTOS);
    }
    
    public Estado existePesquisa(Object nome, Object atividade, 
            Object matricula, Object mensalidade) {
        
        if(nome == null)
            return new Estado(1, "Não existe uma pesquisa em cache", null, NENHUM);
        
        return new Estado(0, "Existe uma pesquisa em cache", 
                new Pesquisa((String) nome,(String) atividade,(String) matricula,(String) mensalidade), PESQUISA);
    }
    
    public Estado cancelarMatricula(int alunoID, int atividadeID) {
        Aluno aluno = alunoCtrl.findAluno(alunoID);
        
        if(aluno == null)
            return new Estado(1, "Matrícula não existente", null, NENHUM);
        Matricula matricula = null;
        for(Matricula m: aluno.getMatriculaList()) {
            if( m.getAtividadeID().getAtividadeID() == atividadeID) {
                try {
                    m.setEstado(0);
                    matriculaCtrl.edit(m);
                    matricula = m;
                } catch (NonexistentEntityException ex) {
                    System.out.println("Erro 2");
                } catch (Exception ex) {
                    System.out.println("Erro 2");
                }
            }
        }
        
        if(matricula == null)
            return new Estado(1, "Não foi possível alterar a matrícula", null, NENHUM);
        
        return new Estado(0, "Matricula cancelada com sucesso", matricula, MATRICULA);
    }
}
