
package projeto;

import java.util.ArrayList;

public class Cromossomo implements Comparable<Cromossomo>{
    private float custo;
    private float chanceEscolha;
    private float[] cromossomo=new float[Projeto.qtdeVeiculo+Projeto.qtdeCliente];
    private int[] cromossomoDecodificado=new int[Projeto.qtdeVeiculo+Projeto.qtdeCliente];
    ArrayList<DecodificarCliente> decodificar_cliente =new ArrayList<DecodificarCliente>();
    ArrayList<DecodificarRota> veiculo_rota=new ArrayList<DecodificarRota>();
    
    private float aptidao;
    private float custo_total=99900099;
    private float custo_emissao;
    private float custo_variavel;
    private float distancia_total;
    private float trading;
    private String dados="";

    public float getCusto() {
        return custo;
    }
    public void setCusto(float custo) {
        this.custo = custo;
    }

    public float getChanceEscolha() {
        return chanceEscolha;
    }
    public void setChanceEscolha(float chanceEscolha) {
        this.chanceEscolha = chanceEscolha;
    }

    public float[] getCromossomo() {
        return cromossomo;
    }
    public void setCromossomo(float[] cromossomo) {
        this.cromossomo = cromossomo;
    }

    public int[] getCromossomoDecodificado() {
        return cromossomoDecodificado;
    }
    public void setCromossomoDecodificado(int[] cromossomoDecodificado) {
        this.cromossomoDecodificado = cromossomoDecodificado;
    }

    public ArrayList<DecodificarCliente> getDecodificar_cliente() {
        return decodificar_cliente;
    }
    public void setDecodificar_cliente(ArrayList<DecodificarCliente> decodificar_cliente) {
        this.decodificar_cliente = decodificar_cliente;
    }

    public ArrayList<DecodificarRota> getVeiculo_rota() {
        return veiculo_rota;
    }
    public void setVeiculo_rota(ArrayList<DecodificarRota> veiculo_rota) {
        this.veiculo_rota = veiculo_rota;
    }

    public float getAptidao() {
        return aptidao;
    }
    public void setAptidao(float aptidao) {
        this.aptidao = aptidao;
    }

    public float getCusto_total() {
        return custo_total;
    }
    public void setCusto_total(float custo_total) {
        this.custo_total = custo_total;
    }

    public float getCusto_emissao() {
        return custo_emissao;
    }
    public void setCusto_emissao(float custo_emissao) {
        this.custo_emissao = custo_emissao;
    }

    public float getCusto_variavel() {
        return custo_variavel;
    }
    public void setCusto_variavel(float custo_variavel) {
        this.custo_variavel = custo_variavel;
    }

    public float getDistancia_total() {
        return distancia_total;
    }
    public void setDistancia_total(float distancia_total) {
        this.distancia_total = distancia_total;
    }

    public float getTrading() {
        return trading;
    }
    public void setTrading(float trading) {
        this.trading = trading;
    }
    
    public String getDados() {
        return dados;
    }
    public void setDados(String dados) {
        this.dados = dados;
    }

    //@Override
    public int compareTo(Cromossomo c) {
         if (this.custo_total < c.getCusto_total()) {
            return -1;
        }
        if (this.custo_total > c.getCusto_total()) {
            return 1;
        }
        return 0; 
    }
} 
    