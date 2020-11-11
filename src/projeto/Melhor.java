
package projeto;

/**
 *
 * @author jose
 */
public class Melhor {
    private int indice;
    private float aptidao;
    private float custo_total=99900099;
    private float custo_emissao;
    private float custo_variavel;
    private float distancia_total;
    private float trading;
    private String dados_melhor="";
    
    public int getIndice() {
        return indice;
    }
    public void setIndice(int indice) {
        this.indice = indice;
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
    
    public String getDados_melhor() {
        return dados_melhor;
    }
    public void setDados_melhor(String dados_melhor) {
        this.dados_melhor = dados_melhor;
    }
}
