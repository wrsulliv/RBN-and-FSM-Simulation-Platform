import java.awt.Color;
import java.awt.Paint;

import org.apache.commons.collections15.Transformer;


public class VertexPaint_SingleAttractor implements Transformer<Integer,Paint>{

	 public Attractor atr;
	 public VertexPaint_SingleAttractor(Attractor atr)
	 {
		 this.atr = atr;
	 }
	 public Paint transform(Integer i) 
	 {
		 if(atr.subToParentStateMap.containsKey(i))
		 {
			 return Color.RED;
		 }
		 else
		 {
			 return Color.GREEN; 
		 }
	 }
}

