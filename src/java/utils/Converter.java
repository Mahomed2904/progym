/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import modelo.Aluno;
import modelo.Atividade;
import modelo.Cobranca;
import modelo.Matricula;
import modelo.Pagamento;
import modelo.Secretária;

/**
 *
 * @author mahom
 */
public class Converter 
{
    private static String alunosToJSON(List<Aluno> alunos) 
    {
        int size = alunos.size() - 1;
        if(size < 0)
           return "[]";
         
        StringBuilder str = new StringBuilder();
        
        str.append("[");
            for(int i=0; i < size; ++i) {
                 str.append(alunoToJSON(alunos.get(i))).append(",");
            }
            str.append(alunoToJSON(alunos.get(size)));
        str.append("]");
        
        return str.toString();
    }
    
    private static String alunoToJSON(Aluno aluno) 
    {
        StringBuilder str = new StringBuilder();
        str.append("{\"id\":").append(aluno.getAlunoID()).append(",");
        str.append("\"nome\":\"").append(aluno.getNome()).append("\",");
        str.append("\"email\":\"").append(aluno.getEmail()).append("\",");
        str.append("\"foto\":\"").append(aluno.getFotoUrl()).append("\"}");
        return str.toString();
    }
    
    private static String alunoWithMatriculaToJSON(Aluno aluno) 
    {
        StringBuilder str = new StringBuilder();
        str.append("{\"id\":").append(aluno.getAlunoID()).append(",");
        str.append("\"nome\":\"").append(aluno.getNome()).append("\",");
        str.append("\"email\":\"").append(aluno.getEmail()).append("\",");
        str.append("\"data\":\"").append(Utils.formatarData(aluno.getDataNascimento())).append("\",");
        str.append("\"foto\":\"").append(aluno.getFotoUrl()).append("\",");
        str.append("\"matriculas\":").append(matriculasToJSON(aluno.getMatriculaList())).append("}");
        return str.toString();
    }
    
    private static String matriculasToJSON(List<Matricula> matriculas) 
    {
        int size = matriculas.size() - 1;
        if(size < 0)
            return "[]";
        
        StringBuilder str = new StringBuilder();
        
        str.append("[");
            for(int i=0; i < size; ++i) {
                 str.append(matriculaToJSON(matriculas.get(i))).append(",");
            }
            
            str.append(matriculaToJSON(matriculas.get(size)));
        str.append("]");
        
        return str.toString();
    }
    
    private static String matriculaToJSON(Matricula matricula) 
    {
        StringBuilder str = new StringBuilder();
        str.append("{\"matriculaID\":").append(matricula.getMatriculaID()).append(",");
        str.append("\"estado\":\"").append(matricula.getEstado()).append("\",");
        str.append("\"atividade\":").append(atividadeToJSON(matricula.getAtividadeID())).append(",");
        str.append("\"data\":\"").append(Utils.formatarData(matricula.getData())).append("\", ");
        str.append("\"cobrancas\":").append(cobrancasToJSON(matricula.getCobrancaList())).append(",");
        str.append("\"pagamentos\":").append(pagamentosToJSON(matricula.getPagamentoList())).append("}");
        
        return str.toString();
    }
    
    private static String atividadesToJSON(List<Atividade> atividades) 
    {
        int size = atividades.size() - 1;
        if(size < 0)
            return "[]";
        
        StringBuilder str = new StringBuilder();
        
        str.append("[");
            for(int i=0; i < size; ++i) {
                 str.append(atividadeToJSON(atividades.get(i))).append(",");
            }
            str.append(atividadeToJSON(atividades.get(size)));
        str.append("]");
        
        return str.toString();
    }
    
    private static String atividadeToJSON(Atividade atividade) 
    {
        StringBuilder str = new StringBuilder();
        str.append("{\"AtividadeID\":").append(atividade.getAtividadeID()).append(",");
        str.append("\"nome\":\"").append(atividade.getNome()).append("\",");
        str.append("\"descricao\":\"").append(atividade.getDescricao()).append("\"}");
        return str.toString();
    }
    
    private static String pagamentosToJSON(List<Pagamento> pagamentos) 
    {
        int size = pagamentos.size() - 1;
        if(size < 0)
            return "[]";
        
        StringBuilder str = new StringBuilder();
        
        str.append("[");
            for(int i=0; i < size; ++i) {
                 str.append(pagamentoToJSON(pagamentos.get(i))).append(",");
            }
            str.append(pagamentoToJSON(pagamentos.get(size)));
        str.append("]");
        
        return str.toString();
    }
    
    private static String pagamentoToJSON(Pagamento pagamento) 
    {
        StringBuilder str = new StringBuilder();
        str.append("{\"pagamentoID\":").append(pagamento.getPagamentoID()).append(",");
        str.append("\"data\":\"").append(pagamento.getData().toLocaleString()).append("\",");
        str.append("\"secretaria\":\"").append(pagamento.getSecretáriaID().getNome()).append("\"}");
        return str.toString();
    }
    
    private static String cobrancasToJSON(List<Cobranca> cobrancas) 
    {
        int size = cobrancas.size() - 1;
        if(size < 0)
            return "[]";
        
        StringBuilder str = new StringBuilder();
        
        str.append("[");
            for(int i=0; i < size; ++i) {
                 str.append(cobrancaToJSON(cobrancas.get(i))).append(",");
            }
            str.append(cobrancaToJSON(cobrancas.get(size)));
        str.append("]");
        
        return str.toString();
    }
    
    private static String cobrancaToJSON(Cobranca cobranca) 
    {
        StringBuilder str = new StringBuilder();
        str.append("{\"cobrancaID\":").append(cobranca.getCobrancaID()).append(",");
        str.append("\"dataInicio\":\"").append(cobranca.getDataInicio().toLocaleString()).append("\",");
        str.append("\"dataTermino\":\"").append(cobranca.getDataTermino().toLocaleString()).append("\",");
        str.append("\"dataTermino\":\"").append(cobranca.getDataTermino()).append("\",");
        str.append("\"valor\":\"").append(cobranca.getValor()).append("\",");
        str.append("\"pago\":").append(cobranca.getPago()).append("}");
        return str.toString();
    }
    
    private static String secretáriasToJSON(List<Secretária> secretária) 
    {
        int size = secretária.size() - 1;
        if(size < 0)
            return "[]";
        
        StringBuilder str = new StringBuilder();
        
        str.append("[");
            for(int i=0; i < size; ++i) {
                 str.append(secretáriaToJSON(secretária.get(i))).append(",");
            }
            str.append(secretáriaToJSON(secretária.get(size)));
        str.append("]");
        
        return str.toString();
    }
    
    private static String secretáriaToJSON(Secretária secretária) 
    {
        StringBuilder str = new StringBuilder();
        str.append("{\"secretariaID\":").append(secretária.getSecretáriaID()).append(",");
        str.append("\"nome\":\"").append(secretária.getNome()).append("\"}");
        return str.toString();
    }
    
    private static String pesquisaToJSON(Pesquisa pesquisa) 
    {
        StringBuilder str = new StringBuilder();
        str.append("{\"nome\":\"").append(pesquisa.getNome()).append("\",");
        str.append("\"atividade\":\"").append(pesquisa.getAtividade()).append("\",");
        str.append("\"matricula\":\"").append(pesquisa.getMatricula()).append("\",");
        str.append("\"mensalidade\":\"").append(pesquisa.getMensalidade()).append("\"}");
        return str.toString();
    }
    
    public static String partialEstadoToJSON(Estado estado) {
        StringBuilder str = new StringBuilder();
        str.append("{\"codigo\":").append(estado.getCodigo()).append(",");
        str.append("\"mensagem\":\"").append(estado.getMensagem()).append("\"}");
        return str.toString();
    }
    
    public static String allEstadoToJSON(Estado estado) {
        StringBuilder str = new StringBuilder();
        str.append("{\"codigo\":").append(estado.getCodigo()).append(",");
        str.append("\"mensagem\":\"").append(estado.getMensagem()).append("\",");
        
        switch(estado.getTipoDeAnexo()) {
            case NENHUM:
                str.append("\"anexo\":{}}");
                break;
            case ALUNO:
                str.append("\"aluno\":").append(alunoToJSON((Aluno) estado.getAnexo())).append("}");
                break;
            case ALUNO_WITH_MATRICULA:
                str.append("\"aluno\":").append(alunoWithMatriculaToJSON((Aluno) estado.getAnexo())).append("}");
                break;
            case ALUNOS:
                str.append("\"alunos\":").append(alunosToJSON((List<Aluno>) estado.getAnexo())).append("}");
                break;
            case MATRICULA:
                str.append("\"matricula\":").append(matriculaToJSON((Matricula) estado.getAnexo())).append("}");
                break;
            case MATRICULAS:
                str.append("\"matriculas\":").append(matriculasToJSON((List<Matricula>) estado.getAnexo())).append("}");
                break;
            case ATIVIDADE:
                str.append("\"atividade\":").append(atividadeToJSON((Atividade) estado.getAnexo())).append("}");
                break;
            case ATIVIDADES:
                str.append("\"atividades\":").append(atividadesToJSON((List<Atividade>) estado.getAnexo())).append("}");
                break;
            case SECRETÁRIA:
                str.append("\"secretaria\":").append(secretáriaToJSON((Secretária) estado.getAnexo())).append("}");
                break;
            case COBRANCA:
                str.append("\"cobranca\":").append(cobrancaToJSON((Cobranca) estado.getAnexo())).append("}");
                break;
            case COBRANCAS:
                str.append("\"cobrancas\":").append(cobrancasToJSON((List<Cobranca>) estado.getAnexo())).append("}");
                break;
            case PAGAMENTO:
                str.append("\"pagamento\":").append(pagamentoToJSON((Pagamento) estado.getAnexo())).append("}");
                break;
            case PAGAMENTOS:
                str.append("\"pagamentos\":").append(pagamentosToJSON((List<Pagamento>) estado.getAnexo())).append("}");
                break;
            case PESQUISA:
                str.append("\"pesquisa\":").append(pesquisaToJSON((Pesquisa) estado.getAnexo())).append("}");
                break;
        }
        
        return str.toString();
    }
}

