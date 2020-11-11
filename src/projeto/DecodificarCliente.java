
package projeto;

public class DecodificarCliente implements Comparable<DecodificarCliente>{
    private float clienteDecodificado;
    private int numeroDoCliente;
    
    private float distancia;
    private int qtde_recursos;

    public float getClienteDecodificado() {
        return clienteDecodificado;
    }
    public void setClienteDecodificado(float clienteDecodificado) {
        this.clienteDecodificado = clienteDecodificado;
    }

    public int getNumeroDoCliente() {
        return numeroDoCliente;
    }
    public void setNumeroDoCliente(int numeroDoCliente) {
        this.numeroDoCliente = numeroDoCliente;
    }

    public float getDistancia() {
        return distancia;
    }
    public void setDistancia(float distancia) {
        this.distancia = distancia;
    }

    public int getQtde_recursos() {
        return qtde_recursos;
    }
    public void setQtde_recursos(int qtde_recursos) {
        this.qtde_recursos = qtde_recursos;
    }

    @Override
    public int compareTo(DecodificarCliente d) {
        if(this.getClienteDecodificado()>d.getClienteDecodificado()){
            return 1;
        }
        if(this.getClienteDecodificado()<d.getClienteDecodificado()){
            return -1;
        }
        return 0;
    }
}
