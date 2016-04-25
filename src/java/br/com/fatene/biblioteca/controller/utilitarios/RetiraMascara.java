package br.com.fatene.biblioteca.controller.utilitarios;

public class RetiraMascara {
    
    public static String campoCNPJ(String cnpj){
        String retorno = cnpj.replace(".", "").replace("/", "").replace("-", "");
        return retorno;
    }
    
    public static String campoCPF(String cpf){
        String retorno = cpf.replace(".", "").replace("-", "");
        return retorno;
    }
    
    public static String campoTelefone(String telefone){
        String retorno = telefone.replace("(", "").replace(")", "").replace("-", "");
        return retorno;
    }
    
    public static String campoCep(String cep){
        String retorno = cep.replace(".", "").replace("-", "");
        return retorno;
    }
    
}
