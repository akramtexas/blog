package blog.engine.pbvi;

import java.util.HashMap;
import java.util.Map;

import blog.model.Evidence;

public class FiniteStatePolicyEvaluator {
	private OUPBVI pbvi; //TODO: remove dependence
	private double gamma;
	private Map<Evidence, Integer> numMissingObs;
	
	public FiniteStatePolicyEvaluator(OUPBVI pbvi, double gamma) {
		this.pbvi = pbvi;
		this.gamma = gamma;
	}
	
	public Map<Evidence, Integer> getLastMissingObs() {
		return numMissingObs;
	}

	private void addMissingObs(Evidence nextObs) {
		if (!numMissingObs.containsKey(nextObs))
			numMissingObs.put(nextObs, 0);
		numMissingObs.put(nextObs, numMissingObs.get(nextObs) + 1);
	}
	
	public Double eval(Belief b, FiniteStatePolicy p, int numTrials) {
		Double value = 0D;
		int totalCount = 0;
		for (State s : b.getStates()) {
			Double v = eval(s, p, numTrials);
			if (v == null) return v;
			value += v * b.getCount(s);
			totalCount += b.getCount(s);
		}
		
		return value/totalCount;
	}
	
	public String getMissingObs() {
		String result = "";
		for (Evidence o : numMissingObs.keySet()) {
			result += o + " " + numMissingObs.get(o) + "\n";
		}
		return result;
	}
	
	public Double eval(State state, FiniteStatePolicy p, int numTrials) {
		numMissingObs = new HashMap<Evidence, Integer>();
		Belief initState = Belief.getSingletonBelief(state, 1, pbvi);
		double accumulatedValue = 0;
		
		for (int i = 0; i < numTrials; i++) {
			Belief curState = initState;
			FiniteStatePolicy curPolicy = p;
			double curValue = 0D;
			double discount = 1;
			
			while (curPolicy != null) {
				if (curState.ended()) break;
				Evidence nextAction = curPolicy.getAction();
				curState = curState.sampleNextBelief(nextAction);		
				Evidence nextObs = curState.getLatestEvidence();
				FiniteStatePolicy nextPolicy = curPolicy.getNextPolicy(nextObs);
				if (nextPolicy == null && !curState.ended()) { 
					nextPolicy = curPolicy.getApplicableNextPolicy(nextObs, curState);
					if (nextPolicy != null) {
						addMissingObs(nextObs);
					}
				}
				curPolicy = nextPolicy;
				
				curValue += discount * curState.getLatestReward();
				discount = discount * gamma;
			}
			accumulatedValue += curValue;
		}
		return accumulatedValue/numTrials;
	}

}
