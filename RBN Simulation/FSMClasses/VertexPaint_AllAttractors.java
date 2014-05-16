import java.awt.Color;
import java.awt.Paint;
import java.util.ArrayList;

import org.apache.commons.collections15.Transformer;


public class VertexPaint_AllAttractors implements Transformer<Integer,Paint>{

	 public ArrayList<Attractor> atrList;
	 public VertexPaint_AllAttractors(ArrayList<Attractor> atrList)
	 {
		 this.atrList = atrList;
	 }
	 public Paint transform(Integer i) 
	 {
		 for(Attractor atr : atrList)
		 {
			 if(atr.subToParentStateMap.containsKey(i))
			 {
				 return Color.RED;
			 }
		 }
		 
		 return Color.GREEN; 
	 }
}

