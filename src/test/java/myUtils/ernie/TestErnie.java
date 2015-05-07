package myUtils.ernie;

import java.util.LinkedHashMap;
import java.util.Map;

import com.common.modules.ernie.ErnieCenter;
import com.common.modules.ernie.ProbEntity;

public class TestErnie {
	public static void main(String[] args) {
		
		ProbEntity<Integer> prob1 = new ProbEntity<Integer>(15.8F,Integer.valueOf(1));
		ProbEntity<Integer> prob2 = new ProbEntity<Integer>(83F,Integer.valueOf(2));
		
		ErnieCenter ernieCenter = new ErnieCenter();
		ernieCenter.addProbEntity(prob1);
		ernieCenter.addProbEntity(prob2);
		
		ernieCenter.buid();
		
		for(int k=0;k<10;k++){
			Map map = new LinkedHashMap();
			map.put(prob1, 0);
			map.put(prob2, 0);
			for(int i=0;i<1000000;i++){
				ProbEntity x = ernieCenter.ernie();
				
				
				if(map.get(x)!=null){
					int count = (Integer)map.get(x);
					map.put(x, count+1);
				}
				
			}
			System.out.println(map);
		}
		
	}
}
