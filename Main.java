import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		int[] food_times = {3,1,2};
		long k = 5; // return 1
		System.out.println(new Solution().solution(food_times, k));
	}

}

class Solution {
	class Food {
		int order;
		int amount;
		public Food(int order, int amount) {
			this.order = order;
			this.amount = amount;
		}
		
		@Override
		public String toString() {
			return this.order + " : " + this.amount;
		}
	}
	
	Comparator<Food> compByAmount = new Comparator<Solution.Food>() {
		public int compare(Food f1, Food f2) {
			return f1.amount - f2.amount;
		}
	};
	
	Comparator<Food> compByOrder = new Comparator<Solution.Food>() {
		public int compare(Food f1, Food f2) {
			return f1.order - f2.order;
		}
	};
	
    public int solution(int[] food_times, long k) {
    	List<Food> foodList = new LinkedList<Food>();
    	int n = food_times.length;
    	for (int i = 0; i < food_times.length; i++) {
    		foodList.add(new Food(i+1, food_times[i]));
    	}
    	
    	foodList.sort(compByAmount);
    	
    	int preAmount = 0;
    	int foodListIdx = 0;
    	
    	for (Food food : foodList) {
    		long diff = food.amount - preAmount;
    		if (diff != 0) {
    			long spend = diff * n;
    			if (spend <= k) {
    				k -= spend;
    				preAmount = food.amount;
    			} else {
    				k %= n;
    				foodList.subList(foodListIdx, food_times.length).sort(compByOrder);
    				return foodList.get(foodListIdx+(int)k).order;
    			}
    		}
    		foodListIdx++;
			n--;
    	}

    	return -1;
    }

}



// 정확도 100% 효율성 X
//class Solution {
//	class Food implements Comparable<Food>{
//		int id;
//		int remains;
//		public Food(int id, int remains) {
//			this.id = id;
//			this.remains = remains;
//		}
//		@Override
//		public int compareTo(Food f) {
//			return this.remains - f.remains;
//		}
//	}
//    public int solution(int[] food_times, long k) {
//        int foodCount = food_times.length;
//        PriorityQueue<Food> q = new PriorityQueue<>();
//        for (int i = 0; i < foodCount; i++)
//        	q.add(new Food(i, food_times[i]));
//        
//        while (q.size() <= k) {
//        	int min = q.peek().remains;
//        	k -= q.size();
//        	q.forEach(f->f.remains-=min);
//        	Stream<Food> stream = q.stream().filter(f -> f.remains > 0);
//        	List<Food> foodList = stream.collect(Collectors.toList());
//        	q.clear();
//        	q.addAll(foodList);
//        }
//        
//        List<Food> list = q.stream().collect(Collectors.toList());
//        list.sort(new Comparator<Food>() {
//        	@Override
//        	public int compare(Food o1, Food o2) {
//        		return o1.id - o2.id;
//        	}
//		});
//
//        return q.remove().id+1;
//    }
//}

//class Solution_failed {
//	// 효율성 문제에서 실패
//    public int solution(int[] food_times, long k) {
//        int foodCount = food_times.length, foodIdx = 0;
//        for (long i = 0; i < k; i++) {
//        	if (food_times[foodIdx] == 0) {
//        		int zeroIdx = foodIdx;
//        		foodIdx = (foodIdx+1)%foodCount;
//        		while (food_times[foodIdx] == 0) {
//        			foodIdx = (foodIdx+1)%foodCount;
//        			if (zeroIdx == foodIdx)
//        				return -1;
//        		}
//        	}
//        	food_times[foodIdx]--;
//        	foodIdx = (foodIdx+1)%foodCount;
//        }
//        
//        if (food_times[foodIdx] == 0) {
//    		int zeroIdx = foodIdx;
//    		foodIdx = (foodIdx+1)%foodCount;
//    		while (food_times[foodIdx] == 0) {
//    			foodIdx = (foodIdx+1)%foodCount;
//    			if (zeroIdx == foodIdx)
//    				return -1;
//    		}
//    	}
//        return foodIdx+1;
//    }
//}