import java.util.HashMap;


public class Attractor {

	FSM parentFSM;
	FSM subFSM;
	HashMap<Integer, Integer> subToParentStateMap;
	public Attractor(FSM parentFSM, FSM subFSM, HashMap<Integer, Integer> subToParentStateMap)
	{
		this.parentFSM = parentFSM;
		this.subFSM = subFSM;
		this.subToParentStateMap = subToParentStateMap;
	}
}
