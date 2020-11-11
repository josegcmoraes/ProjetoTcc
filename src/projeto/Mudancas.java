
package projeto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Mudancas {
    Random generator;
    Mudancas(Random s) {
        generator = s;
    }
    
    public Cromossomo criarSolucao(ArrayList<Veiculo> veiculo, int qtdeVeiculo, int qtdeCliente, ArrayList<Cliente> cliente, float distancia[][], int qtdeMaxEmissao, int custo) {
        Cromossomo s=new Cromossomo();
        ArrayList<Float> c=new ArrayList<Float>();   
        
        // sortea um valor para cada veiculo
        for(int i=0;i<qtdeVeiculo;i++){
            float valor_veiculo=generator.nextFloat();
            c.add(valor_veiculo);
        }
        // define a chave e a quantidade para cada tipo de veiculo
        ArrayList<Pares> mapa_veiculo=new ArrayList<Pares>();
        for(int i=0;i<veiculo.size();i++){
            Pares par=new Pares();
            par.setChave(i);
            par.setQuantidade(veiculo.get(i).getQtde_veiculo());
            mapa_veiculo.add(par);
        }
                 
        for (int j = 0; j < s.getCromossomo().length; j++) {
            if(j<qtdeVeiculo){
                int indice=0;
                float valor=(c.get(j)*mapa_veiculo.size());
                indice=(int) valor;
                int tipo=mapa_veiculo.get(indice).getChave();
                int quantidade=mapa_veiculo.get(indice).getQuantidade();
                int capacidade=veiculo.get(mapa_veiculo.get(indice).getChave()).getCapacidade();
                mapa_veiculo.get(indice).setQuantidade(mapa_veiculo.get(indice).getQuantidade()-1);
                if(mapa_veiculo.get(indice).getQuantidade()==0){
                    mapa_veiculo.remove(indice);
                }               
                s.getCromossomo()[j]=c.get(j);
                s.getCromossomoDecodificado()[j]=tipo;
            }else{
                float valor = generator.nextFloat();
                s.getCromossomo()[j] = valor;
            }              
        }       
        return s;
    }
    
    public float verificar_rota(Melhor melhor,Cromossomo solucao,ArrayList<Veiculo> veiculo, int qtdeVeiculo, int qtdeCliente, ArrayList<Cliente> cliente, float distancia[][], int qtdeMaxEmissao, int custo){
        // Geração das rotas e cálculo do custo da solução
        int[] demanda_rota=new int[qtdeVeiculo];
        ArrayList[] veiculos_rota=new ArrayList[qtdeVeiculo];
        ArrayList[] veiculos_na_rota=new ArrayList[qtdeVeiculo];
        for(int j=0;j<veiculos_rota.length;j++) {
            veiculos_rota[j]=new ArrayList<Integer>();
            veiculos_na_rota[j]=new ArrayList<Integer>();
        }
        
        int quantidade=0;
        boolean[] verifica_rota=new boolean[qtdeVeiculo];
        for(int j=0;j<solucao.getDecodificar_cliente().size();j++){
            boolean adicionado=false;
          
            for(int k=0;k<qtdeVeiculo;k++){
                if(demanda_rota[k]+cliente.get(solucao.getDecodificar_cliente().get(j).getNumeroDoCliente()).getQtde_recursos()<=veiculo.get((solucao.getCromossomoDecodificado()[k])).getCapacidade() && verifica_rota[k] == false && adicionado == false){                      
                    //adiciona o cliente na rota, incrementa a demanda atendida da rota
                    
                    demanda_rota[k]+=cliente.get(solucao.getDecodificar_cliente().get(j).getNumeroDoCliente()).getQtde_recursos();                   
                    veiculos_rota[k].add(solucao.getDecodificar_cliente().get(j).getNumeroDoCliente());
                    veiculos_na_rota[k].add(solucao.getCromossomoDecodificado()[k]);
                    adicionado=true; 
                    quantidade+=1;
                    break;
                }
            }
        }
        
        
        float custo_variavel=0;
        float custo_emissao=0;
        float distancia_total=0;
        for(int j=0;j<veiculos_rota.length;j++){// aqui o j representa as rotas.
            for(int k=0;k<veiculos_rota[j].size();k++){
                if(k==0){
                   custo_variavel+=(veiculo.get((int) veiculos_na_rota[j].get(k)).getCusto_operacional()*distancia[0][(int)veiculos_rota[j].get(k)]);
                   custo_emissao+=(veiculo.get((int) veiculos_na_rota[j].get(k)).getQtde_emissao()*distancia[0][(int)veiculos_rota[j].get(k)]*10); 
                   distancia_total+=distancia[0][(int)veiculos_rota[j].get(k)];
                }
                if(k==veiculos_rota[j].size()-1){
                    custo_variavel+=(veiculo.get((int) veiculos_na_rota[j].get(k)).getCusto_operacional()*distancia[(int)veiculos_rota[j].get(k)][0]);
                    custo_emissao+=(veiculo.get((int) veiculos_na_rota[j].get(k)).getQtde_emissao()*distancia[(int)veiculos_rota[j].get(k)][0]*10); 
                    distancia_total+=distancia[(int)veiculos_rota[j].get(k)][0];
                }else{
                    custo_variavel+=(veiculo.get((int) veiculos_na_rota[j].get(k)).getCusto_operacional()*distancia[(int)veiculos_rota[j].get(k)][(int)veiculos_rota[j].get(k+1)]);
                    custo_emissao+=(veiculo.get((int) veiculos_na_rota[j].get(k)).getQtde_emissao()*distancia[(int)veiculos_rota[j].get(k)][(int)veiculos_rota[j].get(k+1)]*10); 
                    distancia_total+=distancia[(int)veiculos_rota[j].get(k)][(int)veiculos_rota[j].get(k+1)];
                }                       
            }

        }
        
        float trading=(float)(0.0001*custo*(custo_emissao-qtdeMaxEmissao));
        // calcula o custo total
        float custo_total= (float) (custo_variavel+(0.0001*custo*(custo_emissao-qtdeMaxEmissao)));
        if(quantidade<qtdeCliente){
            custo_total*=10;
        }
        
        solucao.setCusto_total(custo_total);
        solucao.setCusto_emissao(custo_emissao);           
        solucao.setCusto_variavel(custo_variavel);
        solucao.setDistancia_total(distancia_total);
        solucao.setTrading(trading);
        
        return custo_total;
    }
    
    
    
    public float geraCustoIndividuo(Melhor melhor,float custo_total,Cromossomo solucao,ArrayList<Veiculo> veiculo, int qtdeVeiculo, int qtdeCliente, ArrayList<Cliente> cliente, float distancia[][], int qtdeMaxEmissao, int custo) {       
        for(int j=qtdeVeiculo;j<solucao.getCromossomo().length;j++){
            DecodificarCliente d_cliente=new DecodificarCliente();
            d_cliente.setNumeroDoCliente(j-qtdeVeiculo+1);
            d_cliente.setClienteDecodificado(solucao.getCromossomo()[j]);
            solucao.decodificar_cliente.add(d_cliente);
        }
        
        // Realizar a ordenação dos clientes em ordem Crescente
        Collections.sort(solucao.getDecodificar_cliente());

        // chama a função verificar_rota que determina as rotas da solução faz o cálculo dos custos.
        custo_total=verificar_rota(melhor,solucao,veiculo,qtdeVeiculo,qtdeCliente,cliente,distancia,qtdeMaxEmissao,custo);      
        return custo_total;
    }
    
    
    
    
    public Melhor verificar_rota_Melhor(Melhor melhor,Cromossomo solucao,ArrayList<Veiculo> veiculo, int qtdeVeiculo, int qtdeCliente, ArrayList<Cliente> cliente, float distancia[][], int qtdeMaxEmissao, int custo){
        String dados="________Melhor________";
        // Geração das rotas e cálculo do custo da solução
        int[] demanda_rota=new int[qtdeVeiculo];
        ArrayList[] veiculos_rota=new ArrayList[qtdeVeiculo];
        ArrayList[] veiculos_na_rota=new ArrayList[qtdeVeiculo];
        for(int j=0;j<veiculos_rota.length;j++) {
            veiculos_rota[j]=new ArrayList<Integer>();
            veiculos_na_rota[j]=new ArrayList<Integer>();
        }
        
        boolean[] verifica_rota=new boolean[qtdeVeiculo];
        for(int j=0;j<solucao.getDecodificar_cliente().size();j++){
            boolean adicionado=false;
            
            for(int k=0;k<qtdeVeiculo;k++){
                if(demanda_rota[k]+cliente.get(solucao.getDecodificar_cliente().get(j).getNumeroDoCliente()).getQtde_recursos()<=veiculo.get((solucao.getCromossomoDecodificado()[k])).getCapacidade() && verifica_rota[k] == false && adicionado == false){                      
                    //adiciona o cliente na rota, incrementa a demanda atendida da rota
                    
                    demanda_rota[k]+=cliente.get(solucao.getDecodificar_cliente().get(j).getNumeroDoCliente()).getQtde_recursos();                   
                    veiculos_rota[k].add(solucao.getDecodificar_cliente().get(j).getNumeroDoCliente());
                    veiculos_na_rota[k].add(solucao.getCromossomoDecodificado()[k]);
                    adicionado=true; 
                    break;
                } 
            }
        }
        dados+="\nRotas solução\n";
        // Este for está apenas para exibir as rotas.
        for(int j=0;j<veiculos_rota.length;j++){
            dados+="\nRota:"+j;
            for(int k=0;k<veiculos_rota[j].size();k++){
                dados+="\tcliente:"+veiculos_rota[j].get(k)+"  veiculo:"+veiculo.get((int) veiculos_na_rota[j].get(k)).getCapacidade()+"emissão:"+veiculo.get((int) veiculos_na_rota[j].get(k)).getQtde_emissao()+"\n";
            }
        }
        dados+="\n\n";
        
        float custo_variavel=0, custo_emissao=0, distancia_total=0;
        for(int j=0;j<veiculos_rota.length;j++){// aqui o j representa as rotas.
            dados+="\nRota:"+j+"  Clientes:\n";
            for(int k=0;k<veiculos_rota[j].size();k++){
                if(k==0){
                   custo_variavel+=(veiculo.get((int) veiculos_na_rota[j].get(k)).getCusto_operacional()*distancia[0][(int)veiculos_rota[j].get(k)]);
                   custo_emissao+=(veiculo.get((int) veiculos_na_rota[j].get(k)).getQtde_emissao()*distancia[0][(int)veiculos_rota[j].get(k)]*10); 
                   distancia_total+=distancia[0][(int)veiculos_rota[j].get(k)];
                   dados+="\tdistancia [0]["+(int)veiculos_rota[j].get(k)+"] "+distancia[0][(int)veiculos_rota[j].get(k)]+" custo op. veic."+veiculo.get((int) veiculos_na_rota[j].get(k)).getCusto_operacional()+" custo_variavel:"+custo_variavel+" custo emissao"+custo_emissao+" distancia_total:"+distancia_total+"\n";
                }
                if(k==veiculos_rota[j].size()-1){
                    custo_variavel+=(veiculo.get((int) veiculos_na_rota[j].get(k)).getCusto_operacional()*distancia[(int)veiculos_rota[j].get(k)][0]);
                    custo_emissao+=(veiculo.get((int) veiculos_na_rota[j].get(k)).getQtde_emissao()*distancia[(int)veiculos_rota[j].get(k)][0]*10); 
                    distancia_total+=distancia[(int)veiculos_rota[j].get(k)][0];
                    dados+="\tdistancia ["+(int)veiculos_rota[j].get(k)+"][0] "+distancia[(int)veiculos_rota[j].get(k)][0]+" custo op. veic."+veiculo.get((int) veiculos_na_rota[j].get(k)).getCusto_operacional()+" custo_variavel:"+custo_variavel+" custo emissao"+custo_emissao+" distancia_total:"+distancia_total+"\n";
                }else{
                    custo_variavel+=(veiculo.get((int) veiculos_na_rota[j].get(k)).getCusto_operacional()*distancia[(int)veiculos_rota[j].get(k)][(int)veiculos_rota[j].get(k+1)]);
                    custo_emissao+=(veiculo.get((int) veiculos_na_rota[j].get(k)).getQtde_emissao()*distancia[(int)veiculos_rota[j].get(k)][(int)veiculos_rota[j].get(k+1)]*10); 
                    distancia_total+=distancia[(int)veiculos_rota[j].get(k)][(int)veiculos_rota[j].get(k+1)];
                    dados+="\tdistancia ["+(int)veiculos_rota[j].get(k)+"]["+(int)veiculos_rota[j].get(k+1)+"] "+distancia[(int)veiculos_rota[j].get(k)][(int)veiculos_rota[j].get(k+1)]+" custo op. veic."+veiculo.get((int) veiculos_na_rota[j].get(k)).getCusto_operacional()+" custo_variavel:"+custo_variavel+" custo emissao"+custo_emissao+" distancia_total:"+distancia_total+"\n";
                }                       
                dados+="veiculo:"+veiculo.get((int) veiculos_na_rota[j].get(k)).getCapacidade()+"emissão:"+veiculo.get((int) veiculos_na_rota[j].get(k)).getQtde_emissao()+"\n";
                dados+="\t  "+veiculos_rota[j].get(k);
            }
        }      
        float trading=(float)(0.0001*custo*(custo_emissao-qtdeMaxEmissao));
        // calcula o custo total
        float custo_total= (float) (custo_variavel+(0.0001*custo*(custo_emissao-qtdeMaxEmissao)));
        
        dados+="\ncusto_total= (custo_variavel+(0.0001*custo*(custo_emissao-qtdeMaxEmissao)))"+" trading benefit:"+(0.0001*custo*(custo_emissao-qtdeMaxEmissao))+"\n";
        dados+=("\nCUSTO TOTAL:"+custo_total+"CUSTO VARIAVEL:"+(custo_variavel)+"  CUSTO DE EMISSAO:"+custo_emissao+"  qtdeMaxEmissao"+qtdeMaxEmissao+"  custo"+custo+" distancia_total:"+distancia_total+"\n");
        melhor.setCusto_total(custo_total);
        melhor.setCusto_emissao(custo_emissao);           
        melhor.setCusto_variavel(custo_variavel);
        melhor.setDistancia_total(distancia_total);
        melhor.setTrading(trading);
        melhor.setDados_melhor(dados);
        
        return melhor;
    }
    public float geraCustoIndividuoMelhor(Melhor melhor,float custo_total,Cromossomo solucao,ArrayList<Veiculo> veiculo, int qtdeVeiculo, int qtdeCliente, ArrayList<Cliente> cliente, float distancia[][], int qtdeMaxEmissao, int custo) {               
        for(int j=qtdeVeiculo;j<solucao.getCromossomo().length;j++){
            DecodificarCliente d_cliente=new DecodificarCliente();
            d_cliente.setNumeroDoCliente(j-qtdeVeiculo+1);
            d_cliente.setClienteDecodificado(solucao.getCromossomo()[j]);
            solucao.decodificar_cliente.add(d_cliente);
        }
       Collections.sort(solucao.getDecodificar_cliente());
        custo_total=verificar_rota(melhor,solucao,veiculo,qtdeVeiculo,qtdeCliente,cliente,distancia,qtdeMaxEmissao,custo);      
        return custo_total;
    } 
    
    public Cromossomo escolher_pai(int indice,ArrayList<Cromossomo> solucao){
        Cromossomo pai =new Cromossomo();
        for(int i=0;i<solucao.get(indice).getCromossomo().length;i++){
            pai.getCromossomo()[i]=solucao.get(indice).getCromossomo()[i];
            pai.getCromossomoDecodificado()[i]=solucao.get(indice).getCromossomoDecodificado()[i];
        }
        pai.setCusto(solucao.get(indice).getCusto());
        pai.getDecodificar_cliente().addAll(solucao.get(indice).getDecodificar_cliente());
        pai.getVeiculo_rota().addAll(solucao.get(indice).getVeiculo_rota());
       
        return pai; 
    }
    
    public Cromossomo selecaoRoleta(ArrayList<Cromossomo> solucao, float[] custo_total,int qtdeSolucao) {
        Cromossomo pai=new Cromossomo();
        float[] chance_Escolha = new float[qtdeSolucao];    
        float somaAptidao=0, ct1=0, soma1=0;
        int indice=0, ind=0;
        Random t = generator;
        ct1=t.nextFloat();
        
        // SOMA DE TODA A APTIDÂO
        for(int k=0;k<custo_total.length;k++){
            somaAptidao=somaAptidao+(1/custo_total[k]);
        }

        // DEFINICAO DAS CHANCES DE ESCOLHA PARA CADA INDIVIDUOS
        float soma=0;
        for(int k=0;k<solucao.size();k++){
            solucao.get(k).setChanceEscolha((1/custo_total[k])/somaAptidao);                         
            chance_Escolha[k]=((1/custo_total[k])/somaAptidao)+soma;
            soma=soma+((1/custo_total[k])/somaAptidao);
        }

        //ESCOLHA PAI
        do{ 
            soma1=chance_Escolha[ind];
            indice=ind;
            ind=ind+1;
        }while(ct1>=soma1);
            
        pai=escolher_pai(indice,solucao);
        pai.setChanceEscolha(soma1);
        return pai;
    }
}
