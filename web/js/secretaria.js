$(function() {
    console.log(window.location.pathname);
    registarEvento($("#lista"), "click", function(){
        window.location = "menu_secretaria.html";
    });
    registarEvento($("#matricula"), "click", function(){
        window.location = "matricular_alunos.html";
    }); 

    switch(window.location.pathname) {
        case '/ProGym/':
            {
                registarEvento($("#email"), "keyup", validacaoEmailCallback);
                registarEvento($("#senha"), "keyup", validacaoSenhaCollback);
                registarEvento($("#login"), "submit", validarFormulario);
            }
            break;
        case '/ProGym/menu_secretaria.html':
            {
                
            }
            break;
            case '/ProGym/matricular_alunos.html':
                {
                    registarEvento($("#email"), "keyup", validacaoEmailCallback);
                    registarEvento($("#submeter-matricula"), "click", submeterMatricula);
                    registarEvento($("#nome"), "keyup", validacaoNomeCollback);
                    registarEvento($("#email"), "keyup", validacaoEmailCallback);
                    registarEvento($("#email"), "change", validacaoEmailCallback);
                    registarEvento($("#passar"), "click", passar);
                    registarEvento($("#tirar"), "click", tirar);
                    registarEvento($("#passar-todos"), "click", passarTodos);
                    registarEvento($("#tirar-todos"), "click", tirarTodos);
                    
                    registarEvento($("#foto"), "change", function() {
                        validarElemento(this, null, $("#foto-status")[0], "", "Insira uma foto valida");
                        //console.log(this.file);
                        if(this.files.length > 0) {
                            //console.log(this.files[0]);
                            $("#div-foto").css({
                                "background-image": "url(" + URL.createObjectURL(this.files[0]) + ")",
                                "background-size": "100% 100%",
                            });
                        }
                    });

                    registarEvento($("#data"), "change", function() {
                        validarElemento(this, null, $("#data-status")[0], "", "Insira uma data valida");
                    })

                   
                }
                break;
            case '/ProGym/info_aluno.html':
                {
                    registarEvento($("#pagar-tudo"), "click", function() {
                        window.location = "pagamento_mensalidade.html";
                    })
                }
                break;
            case '/ProGym/pagamento_mensalidade.html':
                {
                    registarEvento($("#valor"), "keyup", function() {
                        validarElemento($("#valor")[0], validaMontante, $("#valor-status")[0], "", "O montate inserido é não é igual a dívida");
                    })
                    registarEvento($("#recibo"), "keyup", function() {
                        validarElemento($("#recibo")[0], validaRecibo, $("#recibo-status")[0], "", "O número deve ter 9 dígitos");
                    })
                    registarEvento($("#salvar"), "click", salvarPagamento);
                    registarEvento($("#voltar"), "click", voltar);
                    registarEvento($("#foto"), "change", function() {
                        validarElemento(this, null, $("#foto-status")[0], "", "Insira uma foto valida");
                    })
                    
                    mostrarValorDePagmento();
                }
    }
})

function registarEvento(elemento, nome, callback) {
    elemento[nome](callback);
}

function validarElemento(elemento, valido, container, sucessMessage, errorMessage)
{
    if(valido == null) {
        if(elemento.validity.valid && $(elemento).val() !== '') {
            $(container).text(sucessMessage).addClass("status-message");
            return true;
        } else {
            $(container).text(errorMessage).addClass("status-message active");
            return false;
        }
    }
    
    
    if(valido(elemento)) {
        $(container).text(sucessMessage).addClass("status-message");
        return true;
    } else {
        $(container).text(errorMessage).addClass("status-message active");
        return false;
    }
    
}

function isvalidElement(element, valido) {
    if(element.validity.valid && $(element).val() !== '') {
        $(element).css({"border-color": "white"});
        return true;
    } else {
        $(element).css({"border-color": "red"});
        return false;
    }  
}

function validacaoEmailCallback() {
    validarElemento(this, null, $("#email-status")[0], "", "Insira um email valido");
    $("#login-status").text("")
}

function validacaoSenhaCollback() {
    validarElemento(this, null, $("#senha-status")[0], "", "Insira uma senha valida");
    $("#login-status").text("")
}

function validacaoNomeCollback() {
    validarElemento(this, null, $("#nome-status")[0], "", "Insira uma nome valido");
    $("#login-status").text("")
}

function validaMontante(elemento) {
    return elemento.validity.valid && (Number.parseFloat($(elemento).val()) === Number.parseFloat($("#montante").text()));
}

function validaNome(elemento) {
    
}

function validaRecibo(elemento) {
    return elemento.validity.valid && $(elemento).val().length === 9;
}

function validarFormulario() {
    var bool = true;
    bool = validarElemento($("#email")[0], null, $("#email-status"), "", "Insira um email valido" ) && bool;
    bool = validarElemento($("#senha")[0], null, $("#senha-status"), "","Insira uma senha valida" ) && bool;

    if(bool)
    {
        event.preventDefault();
        jQuery.ajax({
            type: "GET",
            url: this.action,
            data: $(this).serialize(),
            dataType: "json",
            success: function(data) {
                if(data["statusCode"] === 0)
                    window.location = "menu_secretaria.html";
                else {
                    $("#login-status").text("Email e/ou senha invalidos").addClass("status-message active")
                    .css({"margin": "10px 0px"});
                }
            },
            error: function(e) {
                $("#login-status").text(e.toString()).addClass("status-message active")
                    .css({"margin": "10px 0px"});
            }
        })
    } else {
        $("#login-status").text("E obrigatorio preenher o formulario").addClass("status-message active")
        .css({"margin": "10px 0px"});
    }
    
    //event.preventDefault();
}

function submeterMatricula() {
    let bool = true;
    
    event.preventDefault();
    bool = validarElemento($("#nome")[0], null, $("#nome-status")[0], "", "Insira um nome valido") && bool;
    bool = validarElemento($("#data")[0], null, $("#data-status")[0], "", "Insira uma data valida") && bool;
    bool = validarElemento($("#email")[0], null, $("#email-status")[0], "", "Insira um email valido") && bool;
    bool = validarElemento($("#foto")[0], null, $("#foto-status")[0], "", "Insira uma foto valida") && bool;
    bool = validarElemento($("#atividades")[0], function(element) { return element.options.length !== 0}, $("#atividades-status")[0], "", "Selecione as ativiaddes a matricular") && bool;
    
    for(var z of $("#atividades")[0].options) {
        z.selected = true;
    }
    
    if(bool) {
        let formData = new FormData($("#matricula-form")[0]);
        
        $.ajax({
            type: "POST",
            url: $("#matricula-form")[0].action,
            data: formData,
            async: false,
            dataType: "json",
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            success: function(data) {
                if(data['statusCode'] === 0) {
                    let id = data.message.split('#')[1];
                    console.log(id);
                    // Guarda na sessão o código do aluno a exibir suas informacoes
                    jQuery.ajax({
                        type: "POST",
                        url: "matricula",
                        data: {
                            'op': 'str',
                            'alunoID': id
                        },
                        dataType: "json",
                        success: function(data) {
                            alert(id);
                            window.location = "info_aluno.html";
                        },
                        error: function(e) {
                            alert("Erro");
                        }
                    });
                } else {
                    alert(data['message']);
                }
            },
            error: function(e) {
               alert("Já existe um aluno cadastrado com o email fonecido. Forneca outro email")
            },
            cache: false
        })
    }
}

function salvarPagamento() {
    var bool = true;
    
    event.preventDefault();
    bool = validarElemento($("#valor")[0], validaMontante, $("#valor-status")[0], "", "O montate inserido é não é igual a dívida") && bool;
    bool = validarElemento($("#recibo")[0], validaRecibo, $("#recibo-status")[0], "", "Insira um número válido") && bool;
    bool = validarElemento($("#banco")[0], null, $("#banco-status")[0], "", "Insira um nome de banco válido") && bool;
    bool = validarElemento($("#foto")[0], null, $("#foto-status")[0], "", "Insira uma foto valida") && bool;
    
    if(bool) {
        let formData = new FormData($("#pagamento-form")[0]);
        
        $.ajax({
            type: "POST",
            url: $("#pagamento-form")[0].action,
            data: formData,
            async: false,
            dataType: "json",
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            success: function(data) {
                if(data['statusCode'] === 0) {
                    alert("Pagamento efetuado com sucesso");
                    window.location = "info_aluno.html";
                } else {
                    alert(data['message']);
                    window.location = "info_aluno.html";
                }
            },
            error: function(e) {
               alert("O pagamento para esta conta já foi feito.");
               window.location = "info_aluno.html";
            },
            cache: false
        })
    }

    
}

function voltar() {
    window.history.go(-1);
}

function passar() {
    event.preventDefault();
    for(var act of $("#acts")[0].options) {
        if(act.selected)
            $("#atividades").append(act);
    }
    
    for(var z of $("#atividades")[0].options) {
        z.selected = true;
    }
}

function tirar() {
    for(var act of $("#atividades")[0].options) {
        if(act.selected)
            $("#acts").append(act);
    }
    
    for(var z of $("#acts")[0].values) {
        z.selected = true;
    }
    
    event.preventDefault();
}

function passarTodos() {
    var x = $("#acts")[0].options;
    
    $("#atividades").append(x);
    event.preventDefault();
}

function tirarTodos() {    
    var x = $("#atividades")[0].options;
    $("#acts").append(x);
    event.preventDefault();
}

function mostrarValorDePagmento() {
     jQuery.ajax({
        type: "POST",
        url: "matricula",
        data: {
            'op': 'rec'
        },
        dataType: "json",
        success: function(data) {
            if(data.cobrancaID) {
                $("#montante").text(data.valor + " MZN");
                $("#valor").val(data.valor);
                return;
            } else {
                alert(data);
                let valor = 0;
                for(let val of data) {
                    valor += Number.parseFloat(val.valor);
                }
                $("#montante").text(valor + " MZN");
                $("#valor").val(valor);
            }
            
        },
        error: function(e) {
            alert("Erro");
        }
    });
}
