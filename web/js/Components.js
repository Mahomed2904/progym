class Aluno extends React.Component {
    constructor(props) {
        super(props);
        this.detalhes = this.detalhes.bind(this);
    }

    detalhes() {
        jQuery.ajax({
            type: "POST",
            url: "matricula",
            data: {
                'op': 'str',
                'alunoID': this.props.aluno.id
            },
            dataType: "json",
            success: function(data) {
                window.location = "info_aluno.html";
            },
            error: function(e) {
                alert("Erro");
            }
        });
    }

    render() {
        return (
        <div className="aluno" onClick={this.detalhes}>
            <div className="imagem">
                <figure>
                    <img src={this.props.aluno.foto}/>
                </figure>
            </div>
            <div className="aluno-detalhes">
                <div className="nome-item">
                    <span>{this.props.aluno.nome}</span>
                </div>
                <div className="email-item">
                    <span>{this.props.aluno.email}</span>
                </div>
            </div>
        </div>)
    }
  }

  class Alunos extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        let other = this;
        return (
            <div>
               {
               this.props.alunos.map( function(aluno) {
                    console.log(aluno)
                    return (<Aluno key={aluno.id} aluno={aluno}></Aluno>);
               })}
            </div>
        )
    }
  }

class Matricula extends React.Component {
    
    constructor(props) {
        super(props);
        this.pagamento = this.pagamento.bind(this);
    }
    
    pagamento() {
        jQuery.ajax({
            type: "POST",
            url: "matricula",
            data: {
                'op': 'str',
                'cobrancaID': this.props.matricula.cobrancas[0].cobrancaID
            },
            dataType: "json",
            success: function(data) {
                window.location = 'pagamento_mensalidade.html';
            },
            error: function(e) {
                alert("Erro");
            }
        });
    }
    
    render() {
        return (
        <div className="matricula">
            <div className="lado-esquerdo">
                <div className="imagem">
                    <figure>
                        <img src="images/pexels-andrea-piacquadio-3757941.jpg"/>
                    </figure>
                </div>
                <div className="matricula-detalhes">
                    <div className="nome-item">
                        <span>Aividade: </span>
                    </div>
                    <div className="info-item">
                        <span>{this.props.matricula.atividade.nome}</span>
                    </div>
                    <div className="nome-item">
                        <span>Estado da matricula: </span>
                    </div>
                    <div className="info-item">
                        <span>{this.props.matricula.estado == 1 ? 'Em funcionamento' : 'Cancelada'}</span>
                    </div>
                </div>
            </div>
            <div className="lado-direito">
                <div className="cima-1">
                <div className="group-line">
                    <div className="dinheiro">
                        <div className="valor">
                            <h3>Valor:</h3>
                        </div>
                        <div className="">
                            <h2>{this.props.matricula.cobrancas[0].valor}</h2>
                        </div>
                        <div className="">
                            <h3>MZN</h3>
                        </div>
                    </div>
                    <div className="matricula-detalhes">
                        <div className="nome-item">
                            <span>Data de emissão: </span>
                        </div>
                        <div className="info-item">
                            <span>{this.props.matricula.cobrancas[0].dataInicio}</span>
                        </div>
                        <div className="nome-item">
                            <span>Estado da mensalidade: </span>
                        </div>
                        <div className="info-item">
                            <span>{this.props.matricula.cobrancas[0].pago ? 'pago' : 'Não pago'}</span>
                        </div>
                    </div>
                </div>
                { !this.props.matricula.cobrancas[0].pago ?
                    <div className="info-botao">
                        <button className="paga" onClick={this.pagamento}>Pagar</button>
                    </div>
                : 
                    <div className="info-botao">
                        <button className="paga" onClick={this.pagamento}>Informações do pagamento</button>
                    </div>
                }
                </div>
                <div className="baixo-1">
                    <div><a>Ver mensalidades antigas...</a></div>
                </div>
            </div>
        </div>
        );
    }
  }

class Matriculas extends React.Component {
    constructor(props) {
        super(props);
        this.total = 0;
    }
    
    render() {
        let other = this;
        return (
            <div>
                { this.props.matriculas.map( function(matricula) {
                    let len = matricula.cobrancas.length;
                    if(!matricula.cobrancas[len-1].pago)
                        other.total += Number.parseFloat(matricula.cobrancas[len-1].valor);
                    return <Matricula matricula={matricula}/>
                })}
                <div id="total" class="total">
                    <Total total={this.total}></Total>  
                </div>
            </div>
        );
    }
}

class Atividades extends React.Component {
    render() {
        return (
            this.props.atividades.map( function(atividade) {
                
                return <option value={atividade.AtividadeID}>{atividade.nome}</option>
            })
        )
    }
}

class Total extends React.Component {
    
    constructor(props) {
        super(props);
        this.pagamentoTudo = this.pagamentoTudo.bind(this);
    }
    
    pagamentoTudo() {
        jQuery.ajax({
            type: "POST",
            url: "matricula",
            data: {
                'op': 'str',
            },
            dataType: "json",
            success: function(data) {
                window.location = 'pagamento_mensalidade.html';
            },
            error: function(e) {
                alert("Erro");
            }
        });
    }
    
    render() {
        return (
            <div>
                <div class="texto">
                {this.props.total !== 0 ? <h2>Total a pagar: <span>{this.props.total}</span> <apan>MZN</apan></h2> : <h2>Já está tudo pago</h2>} 
                </div>
                {
                    this.props.total !== 0 ?
                    <div class="pagar-tudo">
                        <button id="pagar-tudo" onClick={this.pagamentoTudo} class="detalhes-botao pagar">Pagar Tudo</button>
                    </div> : ''
                }
            </div>
        )
    }
}

function Direita(props) {
    
    return (
    <div className="form-direito">
        <div className="foto-1">
            <div>
                <img src={props.imagem}/>
            </div>                    
        </div>
    </div> 
    )
    
}

function Esquerda(props) {
    
    return( <div className="form-esquerdo">
             <div className="campo espaco">
                <h3>Nome</h3>
                <span>{props.dados.nome}</span>
             </div>
             <div className="campo espaco">
                <h3>Data de nascimento</h3>
                <span>{props.dados.data}</span>
             </div>
             <div className="campo espaco">
                <h3>Email</h3>
                <span>{props.dados.email}</span>
            </div>
         </div>
        )
    
}

class DadosPessoais extends React.Component {
    constructor(props) {
        super(props)
    }
    
    render() {
        return (
            <div className="horizontal">
                <Esquerda dados={this.props.aluno}></Esquerda>
                <Direita imagem={this.props.aluno.foto}></Direita>
             </div> 
        )
    }
}

function exibirMatriculas() 
{
   jQuery.ajax({
        type: "GET",
        url: "matricula",
        data: "op=fnd",
        dataType: "json",
        success: function(data) {
            if(data.matricula.length > 0) {
                console.log(data)
                let container = document.getElementById("dados-pessoais");
                let root = ReactDOM.createRoot(container);
                root.render(<DadosPessoais aluno={data}></DadosPessoais>);
                
                container = document.getElementById("lista-matriculas");
                root = ReactDOM.createRoot(container);
                root.render(<Matriculas matriculas={data.matricula}></Matriculas>);
            }
        },
        error: function(e) {
            alert("erro na formatacao do json");
        }
    }); 
}

function exibirListAlunos() {
    jQuery.ajax({
        type: "GET",
        url: "matricula",
        data: "op=lst",
        dataType: "json",
        success: function(data) {
            if(data.length > 0) {
                console.log(data)
                const container = document.getElementById("lista-alunos");
                const root = ReactDOM.createRoot(container);
                root.render(<Alunos alunos={data}></Alunos>);
            } else {
                window.location = '/ProGym/';
            }
        },
        error: function(e) {
            alert("erro na formatacao do json");
        }
    });
}

function exibeAtividades() { 
    jQuery.ajax({
        type: "GET",
        url: "matricula",
        data: "op=act",
        dataType: "json",
        success: function(data) {
            
            if(data.length > 0) {
                const container = document.getElementById("acts");
                const root = ReactDOM.createRoot(container);
                root.render(<Atividades atividades={data}></Atividades>);
            }
        },
        error: function(e) {
            alert("erro");
        }
    })
}

function exibeAtividades2() { 
    jQuery.ajax({
        type: "GET",
        url: "matricula",
        data: "op=act",
        dataType: "json",
        success: function(data) {
            data.unshift({AtividadeID: 0, nome: "Todas"});
            if(data.length > 0) {
                const container = document.getElementById("atividades");
                const root = ReactDOM.createRoot(container);
                root.render(<Atividades atividades={data}></Atividades>);
            }
        },
        error: function(e) {
            alert("erro");
        }
    })
}

function pesquisaEFiltro() {
    jQuery.ajax({
        type: "GET",
        url: "matricula",
        data: {
            op: "pes",
            pesquisa: $("#pesquisa").val(),
            mensalidade: $("#mensalidades").val(),
            atividade: $("#atividades").val(),
            matricula: $("#matriculas").val()
        },
        dataType: "json",
        success: function(data) {
            const container = document.getElementById("lista-alunos");
            const root = ReactDOM.createRoot(container);
            if(data.length > 0) {
                console.log(data);
                root.render(<Alunos alunos={data}></Alunos>);
            } else {
                root.render(<div class="alinhadorVertical"><div class="alinhadorHorizontal"><h1>Nenhum Aluno encontrado</h1></div></div>);
            }
        },
        error: function(e) {
            const container = document.getElementById("lista-alunos");
            const root = ReactDOM.createRoot(container);
            root.render(<h1>Nenhum Aluno encontrado</h1>);
        }
    })
}

$(function() {
    console.log(window.location.pathname);
    console.log("Aqui");
    switch(window.location.pathname) {
        case '/ProGym/menu_secretaria.html':
            //exibirListAlunos();
            exibeAtividades2();
            exibirListAlunos()  ;
            $("#pesquisa").keyup(pesquisaEFiltro);
            $("#mensalidades").change(pesquisaEFiltro),
            $("#atividades").change(pesquisaEFiltro),
            $("#matriculas").change(pesquisaEFiltro);
            break;
        case '/ProGym/matricular_alunos.html':
            exibeAtividades();
            break;
        case '/ProGym/info_aluno.html':
            exibirMatriculas();
            break;
    }       
});