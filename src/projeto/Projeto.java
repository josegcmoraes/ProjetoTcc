
package projeto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

/*
quantidade de clientes ex: 50
limite de emissao ex: 510755
custo por tonelada $ ex: 25
0 40 40 0 armazem 0 localização x=40 y=40 quantidade de que necessita 0
1 22 22 18 cliente 1
2 36 26 26 cliente 2 localizacao x=36 y=26 quantidade de que necessita 26
50 15 56 22 cliente 50 localizacao x=15 y=56 quantidade de que necessita 22

20 1.0 4 4.13 capacidade do veiculo 20 custo operacionais 1.0 quantidade
              deste veiculo utilizado 4 quantidade de emissao de co2 4.13

Parametros
0 nome da instância ex.: c50_30mix.txt
1 quantidade de soluções ex.: 150
2 número máximo de gerações ex.: 100
3 tipo de algoritmo brkga ou normal(rkga)
4 semente de número aleatório ex.: 0-9
*/

public class Projeto {
    static int qtdeCliente = 0; // quantidade de clientes
    static int qtdeVeiculo=0;
    static int qtdeMaxEmissao = 0;
    static int custo = 0;
    static int qtdeSolucao=0;
    static int maximoGeracoes=0;
    static double probMutacao = 0.1;
    static double probCruzamento = 0.9;
    
    public static void main(String[] args) {
        // TODO code application logic here
        long tempoInicio = System.currentTimeMillis(); 
        String nome_instancia = args[0];// nome da instâncias
        qtdeSolucao = Integer.parseInt(args[1]);// quantidade de soluções // instancias 50cliente=100sol.   75cliente=150sol.   100cliente=200sol.
        maximoGeracoes = Integer.parseInt(args[2]);// número maximo de gerações// 100
        String tipo_escolha = args[3];// (brkga)     rkga (normal)
        Random generator = new Random(Long.parseLong(args[4]));// semente números aleatórios// 0-9      
        
        
        
        ArrayList<Cromossomo> solucao = new ArrayList<Cromossomo>();
        ArrayList<Cliente> cliente = new ArrayList<Cliente>();
        ArrayList<Veiculo> veiculo = new ArrayList<Veiculo>();
        Arquivo arquivo = new Arquivo();
        Mudancas md = new Mudancas(generator);
        Melhor melhor=new Melhor();
        
        
        int[] valorRetorno = new int[3];
        valorRetorno = arquivo.obter(nome_instancia,qtdeCliente, qtdeMaxEmissao, custo);
        qtdeCliente = valorRetorno[0];
        qtdeMaxEmissao = valorRetorno[1];
        custo = valorRetorno[2];
        float[] custo_total = new float[qtdeSolucao];
        
	cliente = arquivo.obterCliente(nome_instancia,qtdeCliente, cliente);
        veiculo = arquivo.obterVeiculos(nome_instancia,qtdeCliente, veiculo);
        
        float distancia[][] = new float[qtdeCliente + 1][qtdeCliente + 1];
        for (int i = 0; i < qtdeCliente + 1; i++) {
            for (int j = 0; j < qtdeCliente + 1; j++) {
                distancia[i][j] = (float) Math.sqrt(Math.pow((cliente.get(i).getX() - cliente.get(j).getX()), 2) + Math.pow((cliente.get(i).getY() - cliente.get(j).getY()), 2));         
            }
        }

        int totalVeiculos=0;
        for(int i=0;i<veiculo.size();i++){
            totalVeiculos+=veiculo.get(i).getQtde_veiculo();
        }
        qtdeVeiculo = totalVeiculos;
        
        
        
        // CRIAR POPULAÇÃO INICIAL..............................................
        for(int i=0;i<qtdeSolucao;i++){
            solucao.add(md.criarSolucao(veiculo,qtdeVeiculo,qtdeCliente,cliente,distancia,qtdeMaxEmissao,custo));
            custo_total[i]=md.geraCustoIndividuo(melhor, custo_total[i], solucao.get(i), veiculo, qtdeVeiculo, qtdeCliente, cliente, distancia, qtdeMaxEmissao, custo);
        }
        
        for(int i=0;i<qtdeSolucao;i++){         
            if(solucao.get(i).getCusto_total()<melhor.getCusto_total()){  
                md.verificar_rota_Melhor(melhor, solucao.get(i), veiculo, qtdeVeiculo, qtdeCliente, cliente, distancia, qtdeMaxEmissao, custo);
                System.out.println("Melhor:"+melhor.getCusto_total()+"   distancia:"+melhor.getDistancia_total()+"   CV:"+melhor.getCusto_variavel()+"   CE:"+melhor.getCusto_emissao()+"   trad:"+melhor.getTrading());
            }
        }
        System.out.println("\n");
        
        
       
       
        // REALIZAÇÂO DAS GERAÇÔES______________________________________________
        for(int i=0;i<maximoGeracoes;i++){
            System.out.println("GERAÇÃO "+i+"..........");
            ArrayList<Cromossomo> filho = new ArrayList<Cromossomo>(qtdeSolucao);          
            float[] custo_filho = null;//new float[qtdeSolucao];
            ArrayList<Cromossomo> solucao_top = null;
            ArrayList<Cromossomo> solucao_normal = null;
            float[] custo_top = null;
            float[] custo_normal = null;
            // Selecionar os pais. Realizar Cruzamento. Realizar Mutação
            int tamanho=0;
            
            if(tipo_escolha.equals("brkga")){
                tamanho=(int) (solucao.size()*0.7);
                solucao_top = new ArrayList<Cromossomo>((int) (qtdeSolucao*0.2));
                solucao_normal = new ArrayList<Cromossomo>((int) (qtdeSolucao*0.8));
                Collections.sort(solucao);
                
                for(int j=0;j<qtdeSolucao;j++){
                    if(j<(int) (qtdeSolucao*0.2)){
                        solucao_top.add(solucao.get(j));
                        //System.out.println("j:"+j+" top custo:"+solucao_top.get(j).getCusto_total());
                    }else{
                        solucao_normal.add(solucao.get(j));
                        //System.out.println("j:"+j+"normal custo:"+solucao_normal.get(j-(int) (qtdeSolucao*0.15)).getCusto_total());                 
                    }
                }
            }else{
                tamanho = (int) (qtdeSolucao*0.85);
            }
            
            custo_filho= new float[tamanho];
            for(int j=0;j<tamanho;j++){
                // seleção
                Cromossomo[] pais = new Cromossomo[2];
                
                // ESCOLHA DOS PAIS
                if(tipo_escolha.equals("brkga")){int rtop = generator.nextInt(solucao_top.size()), rrest = generator.nextInt(solucao_normal.size());
                    pais[0] = solucao_top.get(rtop);
                    pais[1] = solucao_normal.get(rrest);
                }else{
                    pais[0]=md.selecaoRoleta(solucao, custo_total,qtdeSolucao);
                    pais[1]=md.selecaoRoleta(solucao, custo_total,qtdeSolucao);                       
                }
                
                //ETAPA DE CRUZAMENTO...........................................
                Cromossomo filho_criado=new Cromossomo();

		/* Controlar probabilidade de cruzamento*/
		double cr1=generator.nextDouble();                
                float valor_escolhido;
                ArrayList<Float> c=new ArrayList<Float>();
                // pega os valores escolhidos do cromossomos do pai
                for(int l=0;l<qtdeVeiculo;l++){
                    double cescolhav=generator.nextDouble();

                    //Controlar probabilidade de cruzamento
                    if(tipo_escolha.equals("normal")){
                        if(cr1 > probCruzamento){
                            cescolhav = 1; //nao tem cruzamento, entao escolhe sempre do pai[0].
                        } 
                    } 

                    if(cescolhav>0.5){
                        valor_escolhido=pais[0].getCromossomo()[l];
                    }else{
                        valor_escolhido=pais[1].getCromossomo()[l];
                    }
                    c.add(valor_escolhido);
                }
                        
                double probmut=generator.nextDouble();
		int local_mutacao=0;
                float novo_valor=0;
                if(probmut<probMutacao){
                    local_mutacao=generator.nextInt(qtdeCliente+qtdeVeiculo);// ocorrerá apenas nos clientes
                    novo_valor= generator.nextFloat();
                    if(local_mutacao<qtdeVeiculo){
                        c.remove(local_mutacao);
                        c.add(local_mutacao, novo_valor);
                    }
                }
                
                ArrayList<Pares> mapa_veiculo=new ArrayList<Pares>();
                for(int l=0;l<veiculo.size();l++){
                    Pares par=new Pares();
                    par.setChave(l);
                    par.setQuantidade(veiculo.get(l).getQtde_veiculo());
                    mapa_veiculo.add(par);
                }
                                
                for(int k=0;k<pais[0].getCromossomo().length;k++){   
                    // armazena o valor de um indice do cromossomo de acordo com o pai escolhido
                    double cescolha=generator.nextDouble();

		    /* Controlar probabilidade de cruzamento*/
		    if(tipo_escolha.equals("normal")){
                        if(cr1 > probCruzamento){
                            cescolha = 1; //nao tem cruzamento, entao escolhe sempre do pai[0].
                        }
		    }

                    // Cria a parte dos veiculos no cromossomo do filho
                    if(k<qtdeVeiculo){
                        int indice=0;
                        float valor=(c.get(k)*mapa_veiculo.size());
                        indice=(int) valor;

                        int tipo=mapa_veiculo.get(indice).getChave();
                        int quantidade_veiculo=mapa_veiculo.get(indice).getQuantidade();
                        int capacidade=veiculo.get(mapa_veiculo.get(indice).getChave()).getCapacidade();
                        mapa_veiculo.get(indice).setQuantidade(mapa_veiculo.get(indice).getQuantidade()-1);
                        if(mapa_veiculo.get(indice).getQuantidade()==0){
                            mapa_veiculo.remove(indice);
                        }
                        filho_criado.getCromossomo()[k] = c.get(k);
                        filho_criado.getCromossomoDecodificado()[k]=tipo;
                             
                    }else{// cria a parte dos clientes no cromossomo do filho
                        if(cescolha>0.5){
                            filho_criado.getCromossomo()[k]=pais[0].getCromossomo()[k];
                        }else{
                            filho_criado.getCromossomo()[k]=pais[1].getCromossomo()[k];
                        }
                    }
                }
                            
                // Mutação na parte dos clientes no cromossomo do filho
                if(probmut<probMutacao && local_mutacao>=qtdeVeiculo){
                    filho_criado.getCromossomo()[local_mutacao]=novo_valor;
                }        
                
                filho.add(filho_criado);
                
                // gerar custo dos filhos
                custo_filho[j]=md.geraCustoIndividuo(melhor, custo_filho[j], filho.get(j), veiculo, qtdeVeiculo, qtdeCliente, cliente, distancia, qtdeMaxEmissao, custo);
            }
            //..................................................................
            for(int j=0;j<filho.size();j++){
                if(filho.get(j).getCusto_total()<melhor.getCusto_total()){
                    md.verificar_rota_Melhor(melhor,filho.get(j), veiculo, qtdeVeiculo, qtdeCliente, cliente, distancia, qtdeMaxEmissao, custo);
		    System.out.println("Melhor:"+melhor.getCusto_total()+"   distancia:"+melhor.getDistancia_total()+"   CV:"+melhor.getCusto_variavel()+"   CE:"+melhor.getCusto_emissao()+"   trad:"+melhor.getTrading()+"   ...... Geracao: "+i);
        	}
            }
            
            //SELEÇÃO PARA A SOBREVIVÊNCIA
            if(tipo_escolha.equals("brkga")){
                Collections.sort(solucao);
                //definindo um valor inteiro, sendo teto para definir a população topo
                BigDecimal topo = new BigDecimal(qtdeSolucao*0.2);
                topo = topo.setScale(0, BigDecimal.ROUND_HALF_UP);

                //definindo as soluções para a próxima geração
                for(int j=0;j<qtdeSolucao;j++){
                    if(j<topo.intValue()){// 20% vem das melhores soluções da geração anterior.
                        custo_total[j]=solucao.get(j).getCusto_total();
                    }else{
                        if(j<(topo.intValue()+filho.size())){// 70% da soluções vem dos filhos.
                            solucao.remove(j);
                            solucao.add(j,filho.get(j-topo.intValue()) );
                            custo_total[j]=solucao.get(j).getCusto_total();
                        }else{
                            solucao.remove(j);
                            solucao.add(j,md.criarSolucao(veiculo, qtdeVeiculo, qtdeCliente, cliente, distancia, qtdeMaxEmissao, custo));
                            ArrayList<Cromossomo> e=new ArrayList<Cromossomo>();
                            e.add(solucao.get(j));
                            float ce=0;
                            custo_total[j]=md.geraCustoIndividuo(melhor, ce,solucao.get(j), veiculo, qtdeVeiculo, qtdeCliente, cliente, distancia, qtdeMaxEmissao, custo);
                            if(solucao.get(j).getCusto_total()<melhor.getCusto_total()){
                                md.verificar_rota_Melhor(melhor, solucao.get(j), veiculo, qtdeVeiculo, qtdeCliente, cliente, distancia, qtdeMaxEmissao, custo);
                                System.out.println("Melhor:"+melhor.getCusto_total()+"   distancia:"+melhor.getDistancia_total()+"   CV:"+melhor.getCusto_variavel()+"   CE:"+melhor.getCusto_emissao()+"   trad:"+melhor.getTrading()+"   ...... Geracao: "+i);
                            }
                        }
                    }
                }
            }else{
                Collections.sort(solucao);
                ArrayList<Cromossomo> melhores = new ArrayList<Cromossomo>();
                melhores.addAll(solucao);
                
                for(int j=0;j<qtdeSolucao;j++){//adiciona filhos 85% de filhos
                    if(j<filho.size()){
                        solucao.remove(j);
                        solucao.add(j,filho.get(j));
                        custo_total[j]=solucao.get(j).getCusto_total();
                    }else{// adiciona 15% das melhores soluções
                        solucao.remove(j);
                        solucao.add(j,melhores.get(j-filho.size()));
                        custo_total[j]=solucao.get(j).getCusto_total();
                    }
                    
                }
            }
            
        }
        
        //_______________________________________________________________________________________
        System.out.println("\n\n\nMelhor Solução...............................................");
        System.out.println(melhor.getDados_melhor());
        System.out.println("\n\n\n...............................................");
        System.out.println("custo total:"+melhor.getCusto_total());
        System.out.println("custo variavel:"+melhor.getCusto_variavel());
        System.out.println("custo emissao:"+melhor.getCusto_emissao());
        System.out.println("distancia total:"+melhor.getDistancia_total());
        System.out.println("...............................................");
        //_______________________________________________________________________________________
        System.out.println("Tempo Total: "+((float)(System.currentTimeMillis()-tempoInicio)/1000));
    }
}
