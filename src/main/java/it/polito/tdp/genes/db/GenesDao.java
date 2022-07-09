package it.polito.tdp.genes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.genes.model.Coppia;
import it.polito.tdp.genes.model.Genes;
import it.polito.tdp.genes.model.Interactions;


public class GenesDao {
	
	public void getAllGenes(Map<String, Genes> idMap){
		String sql = "SELECT DISTINCT GeneID, Essential, Chromosome FROM Genes";
		//List<Genes> result = new ArrayList<Genes>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				if(!idMap.containsKey(res.getShort("GeneID"))) {
					Genes genes = new Genes(res.getString("GeneID"), 
							res.getString("Essential"), 
							res.getInt("Chromosome"));
					idMap.put(res.getString("GeneID"), genes);
				}
			}
			res.close();
			st.close();
			conn.close();
			//return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}
	
	public List<Integer> getVertici(){
		String sql = "SELECT DIstinct g.Chromosome as c "
				+ "FROM genes g "
				+ "WHERE g.Chromosome<>0 ";
		List<Integer> result = new ArrayList<>();
				Connection conn = DBConnect.getConnection();

				try {
					PreparedStatement st = conn.prepareStatement(sql);
					ResultSet res = st.executeQuery();
					while (res.next()) {
						result.add((res.getInt("c")));
					}
					res.close();
					st.close();
					conn.close();
					return result;
					
				} catch (SQLException e) {
					throw new RuntimeException("Database error", e) ;
				}
	}

	public List<Coppia> getArchi(){
		String sql ="SELECT g1.Chromosome AS c1, g2.Chromosome AS c2,g1.GeneID,g2.GeneID, SUM(DISTINCT i.Expression_Corr) AS peso "
				+ "FROM interactions i, genes g1, genes g2 "
				+ "WHERE g1.Chromosome<>g2.Chromosome "
				+ "		AND g1.GeneID=i.GeneID1 "
				+ "		AND g2.GeneID = i.GeneID2 "
				+ "		AND g1.Chromosome<>0 "
				+ "		AND g2.Chromosome<>0 "
				+ "		AND g1.GeneID<>g2.GeneID "
				+ "GROUP BY g1.Chromosome, g2.Chromosome ";
		List<Coppia> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				result.add(new Coppia(res.getInt("c1"), res.getInt("c2"), res.getDouble("peso")));
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}

	
}
