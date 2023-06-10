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
    public static String alunosToJSON(List<Aluno> alunos) 
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
    
    public static String alunoToJSON(Aluno aluno) 
    {
        StringBuilder str = new StringBuilder();
        str.append("{\"id\":\"").append(aluno.getAlunoID()).append("\",");
        str.append("\"nome\":\"").append(aluno.getNome()).append("\",");
        str.append("\"email\":\"").append(aluno.getEmail()).append("\",");
        str.append("\"foto\":\"").append(aluno.getFotoUrl()).append("\"}");
        return str.toString();
    }
    
    public static String alunoWithMatriculaToJSON(Aluno aluno, List<Matricula> matriculas) 
    {
        StringBuilder str = new StringBuilder();
        str.append("{\"id\":\"").append(aluno.getAlunoID()).append("\",");
        str.append("\"nome\":\"").append(aluno.getNome()).append("\",");
        str.append("\"email\":\"").append(aluno.getEmail()).append("\",");
        str.append("\"data\":\"").append(formatarData(aluno.getDataNascimento())).append("\",");
        str.append("\"foto\":\"").append(aluno.getFotoUrl()).append("\",");
        str.append("\"matricula\":").append(matriculasToJSON(aluno.getMatriculaList())).append("}");
        return str.toString();
    }
    
    public static String matriculasToJSON(List<Matricula> matriculas) 
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
    
    public static String matriculaToJSON(Matricula matricula) 
    {
        StringBuilder str = new StringBuilder();
        str.append("{\"matriculaID\":\"").append(matricula.getMatriculaID()).append("\",");
        str.append("\"estado\":\"").append(matricula.getEstado()).append("\",");
        str.append("\"atividade\":").append(atividadeToJSON(matricula.getAtividadeID())).append(",");
        str.append("\"data\":\"").append(formatarData(matricula.getData())).append("\", ");
        str.append("\"cobrancas\":").append(cobrancasToJSON(matricula.getCobrancaList())).append(",");
        str.append("\"pagamentos\":").append(pagamentosToJSON(matricula.getPagamentoList())).append("}");
        
        return str.toString();
    }
    
    public static String atividadesToJSON(List<Atividade> atividades) 
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
    
    public static String atividadeToJSON(Atividade atividade) 
    {
        StringBuilder str = new StringBuilder();
        str.append("{\"AtividadeID\":\"").append(atividade.getAtividadeID()).append("\",");
        str.append("\"nome\":\"").append(atividade.getNome()).append("\",");
        str.append("\"descricao\":\"").append(atividade.getDescricao()).append("\"}");
        return str.toString();
    }
    
    public static String pagamentosToJSON(List<Pagamento> pagamentos) 
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
    
    public static String pagamentoToJSON(Pagamento pagamento) 
    {
        StringBuilder str = new StringBuilder();
        str.append("{\"pagamentoID\":\"").append(pagamento.getPagamentoID()).append("\",");
        str.append("\"data\":\"").append(pagamento.getData().toLocaleString()).append("\",");
        str.append("\"secretaria\":\"").append(pagamento.getSecretáriaID().getNome()).append("\"}");
        return str.toString();
    }
    
    public static String cobrancasToJSON(List<Cobranca> cobrancas) 
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
    
    public static String cobrancaToJSON(Cobranca cobranca) 
    {
        StringBuilder str = new StringBuilder();
        str.append("{\"cobrancaID\":\"").append(cobranca.getCobrancaID()).append("\",");
        str.append("\"dataInicio\":\"").append(cobranca.getDataInicio().toLocaleString()).append("\",");
        str.append("\"dataTermino\":\"").append(cobranca.getDataTermino().toLocaleString()).append("\",");
        str.append("\"dataTermino\":\"").append(cobranca.getDataTermino()).append("\",");
        str.append("\"valor\":\"").append(cobranca.getValor()).append("\",");
        str.append("\"pago\":").append(cobranca.getPago()).append("}");
        return str.toString();
    }
    
    public static String secretáriasToJSON(List<Secretária> secretária) 
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
    
    public static String secretáriaToJSON(Secretária secretária) 
    {
        StringBuilder str = new StringBuilder();
        str.append("{\"secretáriaID\":\"").append(secretária.getSecretáriaID()).append("\",");
        str.append("\"nome\":\"").append(secretária.getNome()).append("\"}");
        return str.toString();
    }
    
    public static String formatarData(Date data) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(data);
        
        return String.format("%02d de %s de %02d", cl.get( Calendar.DAY_OF_MONTH ), 
                                getNameMonth(cl.get( Calendar.MONTH )), cl.get( Calendar.YEAR ));
    }
    
    public static String getNameMonth(int month) {
        switch(month) {
            case 0:
                return "Janeiro";
            case 1:
                return "Fevereiro";
            case 2:
                return "Março";
            case 3:
                return "Abri";
            case 4:
                return "Maio";
            case 5:
                return "Junho";
            case 6:
                return "Julho";
            case 7:
                return "Agosto";
            case 8:
                return "Setembro";
            case 9:
                return "Outubro";
            case 10:
                return "Novembro";
            case 11:
                return "Desembro";
        }
        
        return "Desconhecido";
    }
}

