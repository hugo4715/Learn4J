package tk.hugo4715.learn4j.util;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class WeightedDistribution<T> {
	@Data
	@AllArgsConstructor
	public static class WeightedItem<T>{
		private double weight;
		private T value;
	}
	
	
	@Getter
	private List<WeightedItem<T>> items = new ArrayList<>();
	
	
	public T nextItem(){
		if(items.isEmpty())throw new IllegalStateException("Cannot call WeightedDistribution#nextItem if there is no items!");
		if(items.size() == 1)return items.get(0).getValue();
		double totalWeight = 0;
		for(WeightedItem<T> i : items)totalWeight += i.getWeight();
		
		double selected = Math.random() * totalWeight;
		
		for (int i = 0; i < items.size(); i++) {
			selected -= items.get(i).getWeight();
			if(selected <= 0)return items.get(i).getValue();
		}
		return null;
	}
}
