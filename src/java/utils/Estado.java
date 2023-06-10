/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author mahom
 */
public class Estado {
    private final int codigo;
    private final String mensagem;
    private Object anexo;
    private TipoDeAnexo tipoDeAnexo;

    public Estado(int codigo, String mensagem, Object anexo, TipoDeAnexo tipoDeAnexo) {
        this.codigo = codigo;
        this.mensagem = mensagem;
        this.anexo = anexo;
        this.tipoDeAnexo = tipoDeAnexo;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public Object getAnexo() {
        return anexo;
    }

    public TipoDeAnexo getTipoDeAnexo() {
        return tipoDeAnexo;
    }
}
