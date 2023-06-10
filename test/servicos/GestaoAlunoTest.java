/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package servicos;

import modelo.Secretária;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import utils.Estado;

/**
 *
 * @author mahomed
 */
public class GestaoAlunoTest {
    
    public GestaoAlunoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of autenticarSecretária method, of class GestaoAluno.
     */
    @Test
    public void testAutenticarSecretária() {
        System.out.println("autenticarSecretária");
        String email = "atija@gmail.com";
        String senha = "Atija";
        GestaoAluno instance = new GestaoAluno();
        int expResult = 0;
        Estado result = instance.autenticarSecretária(email, senha);
        System.out.println(result.getMensagem());
        assertEquals(expResult, result.getCodigo());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of informacoesDaSecretária method, of class GestaoAluno.
     */
    @Test
    public void testInformacoesDaSecretária() {
        System.out.println("informacoesDaSecretária");
        int secretáriaID = 1;
        GestaoAluno instance = new GestaoAluno();
        int expResult = 0;
        Estado result = instance.informacoesDaSecretária(secretáriaID);
        Secretária s = (Secretária) result.getAnexo();
        System.out.println(s.getSenha());
        assertEquals(expResult, result.getCodigo());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of terminarSessão method, of class GestaoAluno.
     */
    @Test
    public void testTerminarSessão() {
        System.out.println("terminarSess\u00e3o");
        GestaoAluno instance = new GestaoAluno();
        int expResult = 0;
        Estado result = instance.terminarSessão();
        assertEquals(expResult, result.getCodigo());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of pesquisarAlunos method, of class GestaoAluno.
     */
    @Test
    public void testPesquisarAlunos() {
        System.out.println("pesquisarAlunos");
        String nome = "Vani";
        String atividade = "0"; // 0 - Todas,
        String mensalidade = "0"; // 0 - Todas, 1 - paga, 2 - atrasada
        String matricula = "0"; // 0 - Todas, 1 - em funcionamento, 2 - cancelada
        GestaoAluno instance = new GestaoAluno();
        int expResult = 0;
        Estado result = instance.pesquisarAlunos(nome, atividade, mensalidade, matricula);
        assertEquals(expResult, result.getCodigo());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of adicionarMatricula method, of class GestaoAluno.
     */
    @Test
    public void testAdicionarMatricula() {
        System.out.println("adicionarMatricula");
        String nome = "Esmeralda José";
        String email = "esmeraldais@gmail.com";
        String caminhoDaFoto = "images/pexels-pixabay-414029.jpg";
        String dataNascimento = "22-10-1999";
        String[] atividades = {"5"};
        GestaoAluno instance = new GestaoAluno();
        int expResult = 0;
        Estado result = instance.adicionarMatricula(nome, email, caminhoDaFoto, dataNascimento, atividades);
        //System.out.println(result.getMensagem());
        assertEquals(expResult, result.getCodigo());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of apagarMatricula method, of class GestaoAluno.
     */
    @Test
    public void testApagarMatricula() {
        System.out.println("apagarMatricula");
        int alunoID = 78;
        GestaoAluno instance = new GestaoAluno();
        int expResult = 0;
        Estado result = instance.apagarMatricula(alunoID);
        System.out.println(result.getMensagem());
        assertEquals(expResult, result.getCodigo());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of listaDeAlunos method, of class GestaoAluno.
     */
    @Test
    public void testListaDeAlunos() {
        System.out.println("listaDeAlunos");
        GestaoAluno instance = new GestaoAluno();
        int expResult = 0;
        Estado result = instance.listaDeAlunos();
        assertEquals(expResult, result.getCodigo());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of editarAluno method, of class GestaoAluno.
     */
    @Test
    public void testEditarAluno() {
        System.out.println("editarAluno");
        int alunoID = 99;
        String nome = "Mahomed Aly Baba";
        String email = "root@gmail.com";
        String caminhoDaFoto = "images/pexels-pixabay-414029.jpg";
        String dataNascimento = "12-15-1997";
        GestaoAluno instance = new GestaoAluno();
        int expResult = 0;
        Estado result = instance.editarAluno(alunoID, nome, email, caminhoDaFoto, dataNascimento);
        //System.out.println(result.getMensagem());
        assertEquals(expResult, result.getCodigo());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of editarMatricula method, of class GestaoAluno.
     */
    @Test
    public void testEditarMatricula() {
        System.out.println("editarMatricula");
        GestaoAluno instance = new GestaoAluno();
        int expResult = 0;
        Estado result = instance.editarMatricula();
        assertEquals(null, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of informacoesAluno method, of class GestaoAluno.
     */
    @Test
    public void testInformacoesAluno() {
        System.out.println("informacoesAluno");
        int alunoID = 78;
        GestaoAluno instance = new GestaoAluno();
        int expResult = 0;
        Estado result = instance.informacoesAluno(alunoID);
        assertEquals(expResult, result.getCodigo());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of listaDeAtividades method, of class GestaoAluno.
     */
    @Test
    public void testListaDeAtividades() {
        System.out.println("listaDeAtividades");
        GestaoAluno instance = new GestaoAluno();
        int expResult = 0;
        Estado result = instance.listaDeAtividades();
        assertEquals(expResult, result.getCodigo());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getEstadoSucesso method, of class GestaoAluno.
     */
    @Test
    public void testGetEstadoSucesso() {
        System.out.println("getEstadoSucesso");
        GestaoAluno instance = new GestaoAluno();
        int expResult = 0;
        Estado result = instance.getEstadoSucesso();
        assertEquals(expResult, result.getCodigo());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getJEstadoFracasso method, of class GestaoAluno.
     */
    @Test
    public void testGetJEstadoFracasso() {
        System.out.println("getJEstadoFracasso");
        GestaoAluno instance = new GestaoAluno();
        int expResult = 1;
        Estado result = instance.getJEstadoFracasso();
        assertEquals(expResult, result.getCodigo());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of cobrancasPorID method, of class GestaoAluno.
     */
    @Test
    public void testCobrancasPorID() {
        System.out.println("cobrancasPorID");
        int cobrancaID = 20;
        GestaoAluno instance = new GestaoAluno();
        int expResult = 0;
        Estado result = instance.cobrancasPorID(cobrancaID);
        System.out.println(result.getMensagem());
        assertEquals(expResult, result.getCodigo());
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    /**
     * Test of listaDeCobrancasPorAluno method, of class GestaoAluno.
     */
    @Test
    public void testListaDeCobrancasPorAluno() {
        System.out.println("listaDeCobrancasPorAluno");
        int alunoID = 98;
        GestaoAluno instance = new GestaoAluno();
        int expResult = 0;
        Estado result = instance.listaDeCobrancasPorAluno(alunoID);
        System.out.println(result.getMensagem());
        assertEquals(expResult, result.getCodigo());
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    /**
     * Test of getErroDeAutenticacao method, of class GestaoAluno.
     */
    @Test
    public void testGetErroDeAutenticacao() {
        System.out.println("getErroDeAutenticacao");
        GestaoAluno instance = new GestaoAluno();
        int expResult = 2;
        Estado result = instance.getErroDeAutenticacao();
        assertEquals(expResult, result.getCodigo());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of pagarTodasCobrancas method, of class GestaoAluno.
     */
    @Test
    public void testPagarTodasCobrancas() {
        System.out.println("pagarTodasCobrancas");
        int alunoID = 97;
        int secretáriaID = 1;
        double valor = 1000.0;
        String banco = "BCI";
        String codigoRecibo = "123456789";
        GestaoAluno instance = new GestaoAluno();
        int expResult = 0;
        Estado result = instance.pagarTodasCobrancas(alunoID, secretáriaID, valor, banco, codigoRecibo);
        System.out.println(result.getMensagem());
        assertEquals(expResult, result.getCodigo());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of pagarACobranca method, of class GestaoAluno.
     */
    @Test
    public void testPagarACobranca() {
        System.out.println("pagarACobranca");
        int alunoID = 73;
        int secretáriaID = 1;
        int cobrancaID = 89;
        double valor = 1600;
        String banco = "BCI";
        String codigoRecibo = "123456789";
        GestaoAluno instance = new GestaoAluno();
        int expResult = 0;
        Estado result = instance.pagarACobranca(alunoID, secretáriaID, cobrancaID, valor, banco, codigoRecibo);
        assertEquals(expResult, result.getCodigo());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of listaDePagamentosPorAluno method, of class GestaoAluno.
     */
    @Test
    public void testListaDePagamentosPorAluno() {
        System.out.println("listaDePagamentosPorAluno");
        int alunoID = 76;
        GestaoAluno instance = new GestaoAluno();
        int expResult = 0;
        Estado result = instance.listaDePagamentosPorAluno(alunoID);
        assertEquals(expResult, result.getCodigo());
        // TODO review the generated test code and remove the default call to fail.
       // fail("The test case is a prototype.");
    }
}
