
package projeto;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Arquivo {

    public int[] obter(String resposta,int qtdeClientes, int qtdeMaxEmissao, int custo) {        
        try {
            FileInputStream arquivo = new FileInputStream(resposta);// resposta contém o nome do arquivo
            InputStreamReader input = new InputStreamReader(arquivo);
            BufferedReader br = new BufferedReader(input);

            String linha = null;
            //_________________________________________
            qtdeClientes = Integer.parseInt(br.readLine().trim()); // quantidade de Clientes
            qtdeMaxEmissao = Integer.parseInt(br.readLine().trim()); // quantidade maxima de emissão de carbono
            custo = Integer.parseInt(br.readLine().trim()); // custo de emissão de carbono por tonelada
            arquivo.close();
        } catch (Exception e1) {
            System.err.println("ERRO ao ler o arquivo:" + e1);
        }
        
        int[] valor = new int[3];
        valor[0] = qtdeClientes;
        valor[1] = qtdeMaxEmissao;
        valor[2] = custo;
        return valor;
    }

    public ArrayList<Cliente> obterCliente(String resposta, int n, ArrayList<Cliente> cliente) {
        try {          
            FileInputStream arquivo = new FileInputStream(resposta);
            InputStreamReader input = new InputStreamReader(arquivo);
            BufferedReader br = new BufferedReader(input);

            String linha;
            do {
                br.readLine();
                br.readLine();
                br.readLine();
                for (int i = 0; i <= n; i++) {
                    Cliente novoCliente = new Cliente();
                    linha = br.readLine();
                    String palavra[] = linha.split(" ");
                    
                    novoCliente.setId(Integer.parseInt(palavra[1].trim()));
                    novoCliente.setX(Integer.parseInt(palavra[2].trim()));
                    novoCliente.setY(Integer.parseInt(palavra[3].trim()));
                    novoCliente.setQtde_recursos(Integer.parseInt(palavra[4].trim()));
                    cliente.add(novoCliente);
                }
                linha = null;
            } while (linha != null);
            arquivo.close();
        } catch (Exception e1) {
            System.err.println("ERRO ao ler o arquivo:" + e1);
        }
        return cliente;
    }

    public ArrayList<Veiculo> obterVeiculos(String resposta, int n, ArrayList<Veiculo> veiculo) {
        try {
            FileInputStream arquivo = new FileInputStream(resposta);
            InputStreamReader input = new InputStreamReader(arquivo);
            BufferedReader br = new BufferedReader(input);

            String linha = "";
            do {
                br.readLine();
                br.readLine();
                br.readLine();
                for (int i = 0; i <= n; i++) {
                    br.readLine();
                }
                br.readLine();
                br.readLine();
                br.readLine();
                br.readLine();

                linha = br.readLine();
                while (linha.isEmpty() == false) {
                    Veiculo novoVeiculo = new Veiculo();
                    String palavra[] = linha.split(" ");
                    
                    novoVeiculo.setCapacidade(Integer.parseInt(palavra[0].trim()));
                    novoVeiculo.setCusto_operacional(Float.parseFloat(palavra[1].trim()));
                    novoVeiculo.setQtde_veiculo(Integer.parseInt(palavra[2].trim()));
                    novoVeiculo.setQtde_emissao(Float.parseFloat(palavra[3].trim()));
                    veiculo.add(novoVeiculo);
                    linha = br.readLine();
                    
                    if (linha == null) {
                        break;
                    }
                }
                linha = null;
            } while (linha != null);
            arquivo.close();
        } catch (Exception e1) {
            e1.printStackTrace();
            System.err.println("ERRO:" + e1);
            System.err.println("Erro ao ler o arquivooooo");
        }
        return veiculo;
    }
}
