package com.uenpjpa1;
import java.io.IOException;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainHandler extends HttpServlet {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

@Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
	  
	  //1. Carregar variáveis necessárias para o benchmark
	  int operationcount = Integer.parseInt(req.getParameter("operationcount"));
	  int schema = Integer.parseInt(req.getParameter("schema"));
	  String workload = req.getParameter("workload");
	  int writes = Integer.parseInt(req.getParameter("writes"));
	  int reads = Integer.parseInt(req.getParameter("reads"));
	  
	  //2. Uma instância do objeto EntityManager é necessária para a inserção com JPA
	  EntityManager entityManager = JPAEntityManager.get().createEntityManager();
	  
	  //3. Armazena tempo atual
	  long start = System.currentTimeMillis();
	  
	  //4. Realiza as operações de inserção e leitura, de acordo com o esquema escolhido
	  /*
	   * No JPA, os grupos de entidades são definidos por anotações (ler as classes de entidades)
	   * O JPA não é totalmente compatível com o arquitetura do datastore e FORÇA transações
	   * A transação realiza um commit quando se chama EntityManager.close()
	   * Cada transação tem um limite de 25 grupos de entidade diferentes
	   * Para realizar este benchmark, foi necessário dividir as inserções de entidades
	   * em pequenas transações, o que NÃO TORNA OS RESULTADOS INVÁLIDOS, pois isto simula
	   * uma ambiente real. Por exemplo: quando um usuário insere um artigo, a inserção ocorrerá
	   * numa única transação. Em um ambiente real, um usuário dificilmente inserirá múltiplas
	   * entidades Usuario e Artigo ao mesmo tempo
	   */
	  int sucessWrites = 0;
	  
	 
	  
	  /*No caso do esquema 1, */
	  if(schema==1)
	  {
		  User user = new User();
		  
		  try{
			  entityManager.persist(user);  
		  
		  for(int i = 0; i < writes-1; i++){
				  Article article = new Article();
				  article.setParent(user);
				  entityManager.persist(article); 
			  }
		  }
		  finally {
		        entityManager.close();
		  }
		  
	  }
	  
	  /*  No caso do esquema 2, a cada interação são inseridas 2 entidades: User e Article, sendo
	  	que article é filha de User, simulando o que aconteceria com um aplicativo que utiliza
	  	o esquema 2*/
	  
	  if(schema==2)
	  {
		  for(int i = 0; i < writes; i+=2){
		
			    EntityTransaction t = entityManager.getTransaction();
			    try{
			        t.begin();
			        User user = new User();
				  	entityManager.persist(user);
			  		Article article = new Article();
				  	article.setParent(user);
			        entityManager.persist(article);
			        t.commit();
			    }finally {
			        if (t.isActive())
			            t.rollback();
			    }
			
		  }
	  }
	  
	  //5. Exibe os resultados
	  long elapsedTimeMillis = System.currentTimeMillis() - start;
	  
	  PrintResults(operationcount, reads, writes, elapsedTimeMillis/1000F, resp);
  }

  private void PrintResults(int operationCount, int reads, int writes, float elapsedTime, HttpServletResponse resp) throws IOException
  {
	 String results = "<html><head></head><body>" +
    "<a><big style='font-family: Calibri;'>"+
    "<big><big><big>Resultados<br><br></big></big></big>" +
	"</big>"+
	"</a><a1 style='font-family: Calibri;''>"+
	"<big>Leituras esperadas: "+reads+
	"<br>Leituras realizadas: "+reads+
	"<br>Escritas esperadas: "+writes+
	"<br>Escritas realizadas: "+writes+
	"<br>"+
	"Tempo total: "+elapsedTime+
	"</big>"+
	"</a1>"+
	"</body></html>";
	  
	 resp.getWriter().println(results);
  }
  
}















